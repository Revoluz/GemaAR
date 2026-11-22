# ARCHITECTURE

> Isi sesuai project. Target ≤ 120 baris.
> Boundary dan alur data saja — bukan tutorial.

## Shape

```
MainActivity (Compose)
   ├── CatalogOverlay ─► data/Catalog.kt (daftar alat statis)
   └── ArScreen ──────► ArController (SceneView + ARCore)
                            ├── MarkerDatabase ─► assets/markers/marker_placeholder.png (sementara)
                            ├── ModelLoader ──► assets/models/*.glb
                            └── NotePlayer ───► SoundPool ──► res/raw/*.ogg
                                   ▲
                            Tap → NoteMap (dari data alat)
```

## Dependency direction

| From | May depend on | Must NOT depend on |
|---|---|---|
| `ui/` | `data/`, interface `ar/` & `audio/` | ARCore/SceneView, `SoundPool` |
| `ar/` | `data/` | `ui/`, `audio/` |
| `audio/` | `data/` | `ui/`, `ar/` |
| `data/` | Android SDK minimal | `ui/`, `ar/`, `audio/` |

Aturan:
- Dependency hanya satu arah: `ui → ar/audio → data`.
- `ui/` berbicara ke AR lewat interface (`ArSessionHandle`), bukan tipe SceneView.
- `ar/` dan `audio/` tidak saling impor; pemicu nada datang dari `ui/` (tap callback).
- Enforce dengan `internal` Kotlin + review, bukan hanya prosa.

## Runtime

| Komponen | Cara jalan | Entry |
|---|---|---|
| App debug | `./gradlew installDebug` (compileSdk 37.2, minSdk 26) | `MainActivity` |
| Unit test | `./gradlew testDebugUnitTest` | `app/src/test/` |
| AR | HP fisik ARCore-supported | `ArScreen` |

## Data flow

1. `Catalog.kt` menyimpan daftar alat, model, dan ikon (statis, di kode).
2. `CatalogScreen` menampilkan daftar; user memilih satu `Instrument`.
3. `ArScreen` membuat `ArController`; ARCore mengenali satu `Marker` dari kamera.
4. Marker terdeteksi → `Anchor` pada pose marker → `ModelLoader` menempelkan GLB
   milik `Instrument` yang sedang dipilih.
5. Ganti alat dari katalog → model di marker diganti; marker tidak perlu discan ulang.
6. Tap pada model → hit-test node → `NoteMap` → `NotePlayer.play(note)`.
7. `NotePlayer` memutar sampel dari `res/raw/` lewat `SoundPool`.
8. (Opsional) progres koleksi dan volume musik disimpan di `DataStore`; tanpa database.

## Data katalog (F-03)

Untuk MVP, katalog ditulis langsung di `data/Catalog.kt` sebagai
`List<Instrument>`; tidak ada `catalog.json`, parser, atau state loading/error.

```kotlin
data class Instrument(
    val id: String,
    val name: String,
    val origin: String,
    val modelAsset: String,
    @DrawableRes val iconRes: Int,
    val audioAvailable: Boolean,
)
```

- `id` unik dan stabil.
- `modelAsset` relatif terhadap `assets/`.
- `audioAvailable` hanya `true` untuk alat yang punya sample (F-03: Saron).
- Jika nanti data perlu diubah tanpa rebuild, migrasi ke JSON adalah pekerjaan
  terpisah dan harus menaikkan versi skema.


## Where knowledge lives

| Jenis | Lokasi | Manual / generated |
|---|---|---|
| Intent & non-goals | `docs/PRODUCT.md` | manual |
| Prinsip | `docs/constitution.md` | manual |
| Token, layar, komponen UI | `docs/design/` | manual |
| Aset 3D (sumber desain) | `docs/design/assets/` | manual |
| Kriteria fitur | `docs/specs/<feature>/spec.md` | manual |
| Status fitur | `feature_list.json` | manual (agent: `passes` saja) |
| Keputusan | `docs/decisions/` | manual |
| Memori sesi | `progress.md` | manual |
| Peta repo | `AGENTS.md` | manual |
| Daftar alat & nada | `app/src/main/java/com/gemaar/data/Catalog.kt` | manual |
| Marker AR | `app/src/main/assets/markers/` — satu marker (placeholder di F-00) | manual |
| Versi dependency | `gradle/libs.versions.toml` | manual (bukan generated) |

## Environment

| Var | Dipakai oleh | Wajib |
|---|---|---|
| `sdk.dir` (`local.properties`) | Gradle/Android SDK | ya, mesin-lokal |
| `JAVA_HOME` | Gradle | wajib JDK 21 |

Tidak ada environment variable runtime. `local.properties` tidak pernah di-commit.
Semua var baru wajib masuk `.env.example` di commit yang sama (bila nanti ada).

## Failure modes

- Kode berubah tanpa spec → `verify-docs.sh`
- Dependency tanpa alasan → ADR check
- Model GLB terlalu berat → batas ukuran di `AGENTS.md` + review
- Audio telat/putus → `SoundPool` dimuat sekali di awal sesi, bukan per tap
- Marker tidak dikenali (gelap/blur/terlalu miring) → hint arahkan ulang + contoh marker
- HP tanpa ARCore → tampilkan pesan jelas, jangan crash
- Agent one-shot satu fitur → `feature_list.json` + satu task per iterasi
- Test lulus tapi fitur salah → converge dua axis

## Deployment

| Target | Apa |
|---|---|
| HP penguji | APK debug via `adb`/`installDebug` |
| Rilis awal | APK release / internal testing Play Console (manual, butuh approval) |

Tidak ada deploy manual dari agent tanpa approval manusia.
