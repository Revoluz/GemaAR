# Tasks: F-01 — Tempatkan satu alat musik di marker

**Input:** `spec.md`, `plan.md`

## Phase 1: Setup

- [x] **T001** S1 Beranda versi F-01: 3 tombol tampil, hanya Mulai aktif
      blocked by: none
      design: `docs/design/UI/exports/S1-beranda.png`
      files: `MainActivity.kt`, `ui/HomeScreen.kt`
      verify: `docs/design/UI/exports/verification/T001-S1-implemented.png`;
              Mulai membuka AR placeholder `T001-mulai-AR.png`

- [ ] **T002** S3 Petunjuk overlay: 3 langkah, close, Mulai AR, “?”
      blocked by: T001
      design: `docs/design/UI/exports/S3-petunjuk.png`
      files: `ui/ar/PetunjukOverlay.kt`, `ui/ar/ArScreen.kt`
      verify: overlay muncul; close kembali ke AR; “?” membuka lagi

- [ ] **T003** Flag Petunjuk sekali + share/save marker
      blocked by: T002
      files: `data/PetunjukStore.kt`, `AndroidManifest.xml`, `res/xml/file_paths.xml`
      verify: Petunjuk tidak muncul otomatis setelah pernah ditutup; share sheet terbuka

## Phase 2: AR

- [ ] **T004** S4 AR shell: plakat Saron, hint, status marker, HUD lengkap
      blocked by: T001
      design: `docs/design/UI/exports/S4-ar.png`
      files: `ui/ar/ArScreen.kt`
      verify: HUD home + “?” aktif; Katalog/Info/Settings nonaktif; note bar/scale belum tampil

- [ ] **T005** Izin kamera + ketersediaan AR + error state
      blocked by: T004
      design: `docs/design/UI/exports/states-S1-S7.png`
      files: `ar/ArController.kt`, `ar/ArUiState.kt`, `ArScreen.kt`
      verify: izin ditolak → error + Settings; AR unsupported → error

- [ ] **T006** Marker database + deteksi + anchor + model Saron
      blocked by: T005
      files: `ar/MarkerRepository.kt`, `ar/ArController.kt`, `assets/markers/`
      verify: marker terdeteksi → Saron muncul menempel

- [ ] **T007** Marker hilang → hide + hint; marker kembali → muncul lagi
      blocked by: T006
      files: `ar/ArController.kt`, `ArScreen.kt`
      verify: 3× hilang/muncul tanpa crash

## Phase 3: Polish & Converge

- [ ] **T008** Uji device + rekam bukti
      blocked by: T007
      verify: video/screenshot marker → model; izin ditolak; marker hilang

- [ ] **T009** Update `feature_list.json`, `progress.md`, `ROADMAP.md`
      blocked by: T008
      verify: `bash init.sh --check`; converge F-01

## Rules

- Satu task per iterasi.
- **UI wajib mengikuti export Pen di `docs/design/UI/exports/`.** Jangan mengubah
  layout, warna, font, atau ukuran tanpa update desain.
- Jangan menambah katalog/audio/scale/info/settings di F-01.
- Jangan ubah desain Pen; implementasi mengikuti frame S1/S3/S4.
- Verifikasi gagal → perbaiki, jangan lanjut.
