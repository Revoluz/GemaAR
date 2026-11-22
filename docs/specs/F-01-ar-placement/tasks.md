# Tasks: F-01 — Tempatkan satu alat musik di marker

**Input:** `spec.md`, `plan.md`

## Phase 1: Setup

- [x] **T001** S1 Beranda versi F-01: 3 tombol tampil, hanya Mulai aktif
      blocked by: none
      design: `docs/design/UI/exports/S1-beranda.png`
      files: `MainActivity.kt`, `ui/HomeScreen.kt`
      verify: `docs/design/UI/exports/verification/T001-S1-implemented.png`;
              Mulai membuka AR `T001-mulai-AR.png`

- [x] **T002** S3 Petunjuk overlay: 3 langkah, close, Mulai AR, “?”
      blocked by: T001
      design: `docs/design/UI/exports/S3-petunjuk.png`
      files: `ui/ar/PetunjukOverlay.kt`, `ui/ar/ArScreen.kt`
      verify: overlay muncul saat pertama kali;
              `verification/T002-petunjuk-overlay.png`;
              close/Mulai AR menutup overlay;
              “?” membuka lagi `verification/T002-petunjuk-dibuka-lagi.png`

- [x] **T003** Flag Petunjuk sekali + share/save marker
      blocked by: T002
      files: `data/PetunjukStore.kt`, `data/MarkerSharer.kt`,
             `AndroidManifest.xml`, `res/xml/file_paths.xml`
      verify: setelah ditutup, Mulai berikutnya langsung ke AR;
              share sheet terbuka dengan PNG marker
              `verification/T003-unduh-marker-share.png`

## Phase 2: AR

- [x] **T004** S4 AR shell: plakat Saron, hint, status marker, HUD lengkap
      blocked by: T001
      design: `docs/design/UI/exports/S4-ar.png`
      files: `ui/ar/ArScreen.kt`
      verify: HUD home + “?” aktif; Katalog/Info/Settings nonaktif;
              note bar/scale belum tampil `verification/T004-ar-scanning.png`;
              konfirmasi keluar `verification/T004-konfirmasi-keluar-ar.png`

- [x] **T005** Izin kamera + ketersediaan AR + error state
      blocked by: T004
      design: `docs/design/UI/exports/states-S1-S7.png`
      files: `ar/ArController.kt`, `ar/ArUiState.kt`, `ui/ar/ArErrorOverlays.kt`
      verify: izin ditolak → error + Buka Pengaturan
              `verification/T005-izin-kamera-ditolak.png`;
              Settings terbuka `verification/T005-buka-pengaturan.png`;
              kembali dari Settings dengan izin aktif → AR restart tanpa overlay;
              ARCore tidak tersedia → overlay `Coba Lagi`

- [x] **T006** Marker database + deteksi + anchor + model Saron
      blocked by: T005
      files: `ar/MarkerRepository.kt`, `ar/ArController.kt`, `assets/markers/`
      verify: marker terdeteksi → Saron menempel
              `verification/T006-marker-terdeteksi.png`

- [x] **T007** Marker hilang → hide + hint; marker kembali → muncul lagi
      blocked by: T006
      files: `ar/ArController.kt`, `ar/MarkerVisibility.kt`, `ArScreen.kt`
      verify: 4 siklus `visible=true → visible=false` tanpa crash;
              `verification/T007-marker-hilang.png`,
              `verification/T007-log-siklus.txt`;
              unit test `MarkerVisibilityTest`

## Phase 3: Polish & Converge

- [x] **T008** Uji device + rekam bukti
      blocked by: T007
      verify: screenshot di `docs/design/UI/exports/verification/`;
              unit test, lint, dan `init.sh --check` hijau

- [x] **T009** Update `feature_list.json`, `progress.md`, `ROADMAP.md`
      blocked by: T008
      verify: `bash scripts/verify-docs.sh`; converge F-01

## Rules

- Satu task per iterasi.
- **UI wajib mengikuti export Pen di `docs/design/UI/exports/`.** Jangan mengubah
  layout, warna, font, atau ukuran tanpa update desain.
- Jangan menambah katalog/audio/scale/info/settings di F-01.
- Jangan ubah desain Pen; implementasi mengikuti frame S1/S3/S4.
- Verifikasi gagal → perbaiki, jangan lanjut.
