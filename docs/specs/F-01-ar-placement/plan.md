# Plan: F-01 — Tempatkan satu alat musik di marker

**Input:** `spec.md`
**Status:** draft
**Scope:** S1 (sebagian), S3, S4 (sebagian)

## Scope layar

| Layar | F-01 | Ditunda |
|---|---|---|
| S1 Beranda | tombol **Mulai** aktif; Alat Musik & Pengaturan tampil nonaktif | fungsi Alat Musik (F-06), Pengaturan (F-07) |
| S3 Petunjuk | overlay, 3 langkah, Unduh Marker, Mulai AR | — |
| S4 AR | marker, Saron, hint, status, HUD home + “?” aktif; Katalog/Info/Settings nonaktif | katalog (F-03), Info (F-05), note bar (F-02), scale (F-04), settings (F-07) |

Layar dipakai beberapa fitur; F-01 hanya mengisi bagian yang dibutuhkan.

## Arsitektur

```
MainActivity
└── GemaARApp (Compose)
    ├── HomeScreen (S1, partial)
    ├── PetunjukOverlay (S3)
    └── ArScreen (S4, partial)
          └── ArController (interface)
                ├── MarkerRepository ──► assets/markers/marker_placeholder.png
                ├── ModelRepository  ──► assets/models/saron.glb
                └── ArUiState (Scanning, Loading, Placed, Lost, Error)
```

Boundary:
- `ui/` tidak menyentuh `ARSceneView` langsung; lewat `ArController`.
- `ar/` tidak menyentuh Compose.
- Satu marker untuk semua alat.

## Teknis

| Bagian | Keputusan |
|---|---|
| AR | SceneView 4.70 `ARSceneView` + ARCore `AugmentedImageDatabase` |
| Marker | satu gambar, `physicalWidth = 0.21f` (A4) |
| Model | `saron.glb`, `scaleToUnits = 0.5f`, bottom-aligned ke marker (`centerOrigin = (0,-1,0)`) |
| Detection | `trackingState == TRACKING` **dan** `trackingMethod == FULL_TRACKING` → anchor di center pose |
| Lost | `FULL_TRACKING` berhenti (`LAST_KNOWN_POSE`/`PAUSED`) → model disembunyikan, hint muncul |
| Permission | Activity Result `RequestPermission`; ditolak → error + Settings |
| ARCore | `ArCoreApk.checkAvailability`; unsupported → error; not installed → prompt install |
| Model loading | `rememberModelInstance` + `rememberModelLoadState` |
| Petunjuk first-run | DataStore/Preferences flag `petunjuk_seen` |
| Share marker | `FileProvider` + `Intent.ACTION_SEND`, MIME `image/png` |
| Lifecycle | SceneView menangani resume/pause; sesi dibuat ulang saat kembali |

## Referensi visual (wajib)

| File | Dipakai untuk |
|---|---|
| `docs/design/UI/exports/S1-beranda.png` | S1 Beranda |
| `docs/design/UI/exports/S3-petunjuk.png` | S3 Petunjuk |
| `docs/design/UI/exports/S4-ar.png` | S4 AR |
| `docs/design/UI/exports/states-S1-S7.png` | state S1–S7 |
| `docs/design/UI/exports/assets/ilustrasi-latar.png` | background |
| `docs/design/UI/exports/assets/alat-saron.png` | ilustrasi Saron |

Catatan:
- S4 export memakai Bonang + NoteBar/scale. F-01 memakai **Saron** dan belum
  menampilkan NoteBar/scale.
- S3 export kartu marker masih kosong; implementasi memakai
  `app/src/main/assets/markers/marker_placeholder.png`.
- Jangan mengubah layout/token dari export tanpa update desain.

## File yang disentuh

```
app/src/main/java/com/gemaar/
├── MainActivity.kt
├── ar/ArController.kt
├── ar/ArUiState.kt
├── ar/MarkerRepository.kt
├── data/PetunjukStore.kt
├── ui/HomeScreen.kt
├── ui/ar/ArScreen.kt
├── ui/ar/PetunjukOverlay.kt
└── ui/components/...
app/src/main/assets/markers/marker_placeholder.png
app/src/main/assets/models/saron.glb
app/src/main/AndroidManifest.xml
app/src/main/res/xml/file_paths.xml
```

## Contract

- `ArUiState`: `Scanning`, `ModelLoading(progress)`, `Placed`, `MarkerLost`,
  `ModelError(message)`.
- Izin kamera & ketersediaan ARCore ditangani `ARSceneView` lewat slot overlay;
  error-nya tidak masuk `ArUiState`.
- `ArController(...)` composable membungkus sesi; UI tidak menyentuh ARCore.
- `PetunjukStore.hasSeenPetunjuk: Boolean`, `markPetunjukSeen()`.

## Verifikasi

```bash
export JAVA_HOME=/usr/lib/jvm/temurin-25-jdk-amd64
./gradlew assembleDebug
./gradlew testDebugUnitTest
./gradlew installDebug
bash init.sh --check
```

Manual:
1. Cetak marker A4 21×21 cm.
2. Buka app → Mulai → Petunjuk → Mulai AR.
3. Arahkan kamera → Saron muncul ≤2 detik.
4. Geser marker → model ikut.
5. Tutup marker → model hilang + hint.
6. Marker kembali → model muncul.
7. Tolak izin kamera → error + Settings.

## Risiko

- Marker placeholder mungkin kurang stabil → siapkan marker final jika perlu.
- ARCore belum tentu terpasang di tablet → uji `ArCoreApk` dan alur install.
- Model 1.2 MB / 15k tris → aman.
- Kompas/lighting ruangan memengaruhi tracking → uji di dua kondisi cahaya.
- AR tidak bisa diuji di emulator → wajib device fisik.

## Constitution check

- [x] Simplicity: tanpa DI framework/database.
- [x] Boundary: UI → ar/data; ARCore hanya di `ar/`.
- [x] Satu marker, satu model default.
- [x] Tidak ada dependency baru (SceneView/DataStore sudah ada).
