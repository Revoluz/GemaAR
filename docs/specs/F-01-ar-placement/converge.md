# Converge: F-01 — Tempatkan satu alat musik di marker

**Status:** PASS
**Tanggal:** 2026-11-22
**Device:** Samsung Galaxy Tab S9 (SM-X710, Android 16/API 36, ARCore 1.57)
**Emulator:** Pixel Fold API 35 (tanpa ARCore) untuk state error

## Acceptance (spec)

| Ref | Skenario | Hasil | Bukti |
|---|---|---|---|
| US1.1 | Mulai pertama kali → S3 Petunjuk | PASS | `verification/T002-petunjuk-overlay.png` |
| US1.2 | Mulai AR → Petunjuk tutup, kamera aktif | PASS | device test; `T004-ar-scanning.png` |
| US1.3 | Setelah ditutup, Mulai langsung ke AR; “?” buka lagi | PASS | device test; `T002-petunjuk-dibuka-lagi.png` |
| US1.4 | Tutup Petunjuk kembali ke AR tanpa restart | PASS | device test |
| US2.1 | Marker belum terlihat → hint | PASS | `T004-ar-scanning.png`, `T007-marker-hilang.png` |
| US2.2 | Marker dikenali → Saron muncul | PASS | `T006-marker-terdeteksi.png` |
| US2.3 | Marker digeser → model mengikuti | PASS | device test (model bergerak mengikuti marker) |
| US2.4 | Marker keluar kamera → model hilang + hint | PASS | `T007-marker-hilang.png` |
| US2.5 | Marker kembali → model muncul lagi | PASS | 4 siklus di `T007-log-siklus.txt` |
| US3.1 | Unduh Marker → share sheet PNG | PASS | `T003-unduh-marker-share.png` |
| US3.2 | File bisa dibuka/dicetak | PASS | share sheet menampilkan preview + opsi Cetak |
| US4.1 | Izin kamera ditolak → pesan + Buka Pengaturan | PASS | `T005-izin-kamera-ditolak.png`, `T005-buka-pengaturan.png` |
| US4.2 | Perangkat tanpa ARCore → pesan jelas, tidak crash | PASS | `T005-arcore-tidak-tersedia.png` (emulator) |
| US4.3 | Model gagal dimuat → pesan + Coba lagi | PASS (code path) | `ModelLoadState.Failed` → `ModelErrorCard`; retry me-rekey model |

## Functional requirements

| FR | Hasil | Catatan |
|---|---|---|
| FR-001 satu marker A4 21 cm | PASS | `MarkerRepository.PHYSICAL_WIDTH_METERS = 0.21f`; `marker-A4-21cm.pdf` |
| FR-002 Saron default | PASS | `models/saron.glb` |
| FR-003 model menempel & mengikuti marker | PASS | `AugmentedImageNode` + `ModelNode` |
| FR-004 hilang → sembunyi, kembali → muncul | PASS | `isMarkerVisible` + 4 siklus log |
| FR-005 Petunjuk sekali + “?” | PASS | `PetunjukStore`, tombol “?” |
| FR-006 share/save PNG marker | PASS | `MarkerSharer` + FileProvider |
| FR-007 izin / ARCore / model error | PASS | overlay izin, gate ARCore, kartu model error |
| FR-008 fitur lain tidak aktif | PASS | Katalog/Info/Settings nonaktif; note bar/scale tidak tampil |
| FR-009 kembali ke Beranda dengan konfirmasi | PASS | `T004-konfirmasi-keluar-ar.png`; BackHandler + dialog |

## Perintah verifikasi

```bash
export JAVA_HOME=/usr/lib/jvm/temurin-25-jdk-amd64
./gradlew assembleDebug          # OK
./gradlew testDebugUnitTest      # OK (7 test, termasuk MarkerVisibilityTest)
./gradlew lintDebug              # OK, 0 issue
bash init.sh --check             # Environment OK
bash scripts/verify-docs.sh      # OK
```

## Deviasi dari plan (disetujui saat implementasi)

1. **Deteksi marker** memakai `trackingState == TRACKING` **dan**
   `trackingMethod == FULL_TRACKING`. ARCore tetap melaporkan `TRACKING` +
   `LAST_KNOWN_POSE` saat marker keluar kamera; tanpa syarat ini model tidak
   pernah disembunyikan dan hint tidak muncul. Diuji lewat `MarkerVisibilityTest`.
2. **Gate ARCore** ditambahkan sebelum sesi dibuat (`ArCoreApk.awaitAvailability`).
   Tanpa gate, perangkat tanpa ARCore bisa berhenti di layar “Mencari marker”
   tanpa pesan error. Gate menampilkan pesan + opsi Pasang/Perbarui ARCore.
3. **Posisi model** bottom-aligned ke marker (`centerOrigin = (0,-1,0)`) supaya
   duduk di permukaan marker, bukan tenggelam setengah.
4. **Marker artwork** masih placeholder; penggantian artwork tidak mengubah
   perilaku. File cetak A4 21 cm ditambahkan di `docs/design/UI/exports/`.

## Known limitations

- Model Saron diskalakan ke 0,5 m (2,4× lebar marker 21 cm); pada sudut kamera
  tertentu model bisa keluar layar. Ini sesuai plan F-01; penyempurnaan skala
  bisa dilakukan di F-02/F-03.
- PDF marker dicetak 21 cm penuh (borderless). Printer dengan margin akan
  mengecilkan sedikit; deteksi tetap bekerja, skala bisa berbeda ±5%.
- Uji ARCore-unavailable memakai emulator tanpa ARCore. Emulator mengembalikan
  status “unknown”, sehingga yang tampil adalah pesan generik + Buka Play Store.
  Di perangkat tanpa ARCore dengan Play Store, status `SUPPORTED_NOT_INSTALLED`
  menampilkan tombol **Pasang ARCore**.
- Screenshot sesi AR kadang menangkap feed kamera gelap saat ruangan redup;
  status/HUD tetap terbaca.

## Kesimpulan

Semua acceptance F-01 yang bisa diuji terpenuhi. Build, unit test, lint,
`init.sh --check`, dan `verify-docs.sh` hijau. F-01 layak `passes: true`.
