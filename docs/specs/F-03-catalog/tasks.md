# Tasks: F-03 — Katalog alat musik + pilih dari daftar

**Input:** `spec.md`, `plan.md`

## Phase 1: Data

- [ ] **T001** `catalog.json` + Instrument + CatalogRepository + fallback test
      blocked by: none
      files: `app/src/main/assets/catalog.json`,
             `data/Instrument.kt`, `data/CatalogRepository.kt`,
             `app/src/test/java/com/gemaar/data/InstrumentSelectionTest.kt`,
             `docs/ARCHITECTURE.md`
      verify: 6 alat terbaca; id unik; `audioAvailable` hanya Saron;
              id tak dikenal → Saron; JSON rusak → Error

- [ ] **T002** InstrumentStore: alat terakhir + flag katalog first run
      blocked by: T001
      files: `data/InstrumentStore.kt`
      verify: pilihan tersimpan; default Saron; flag auto-open tersimpan

## Phase 2: UI katalog

- [ ] **T003** Ikon alat + kartu + overlay S2
      blocked by: T001
      design: `docs/design/UI/exports/S2-katalog.png`
      files: `ui/ar/InstrumentIcon.kt`, `ui/ar/CatalogOverlay.kt`
      verify: grid 3×2, 6 kartu, nama + asal + ikon, alat aktif ditandai,
              close X; tanpa badge progres

## Phase 3: AR & wiring

- [ ] **T004** `ArController` memuat model sesuai Instrument
      blocked by: T001
      files: `ar/ArController.kt`
      verify: ganti alat → model berganti tanpa scan ulang; loading/error;
              marker tetap; model Saron tetap bottom-aligned

- [ ] **T005** Wiring `ArScreen`: tombol Katalog, pilih, judul, note bar gating
      blocked by: T002, T003, T004
      files: `ui/ar/ArScreen.kt`
      verify: Katalog aktif; pilih → tutup + simpan; judul berubah;
              Saron → note bar; non-Saron → pesan audio + tanpa note bar;
              pindah dari Saron menghentikan nada; Back urutan benar

- [ ] **T006** First-run katalog + state memuat/kosong/error
      blocked by: T002, T005
      files: `ui/ar/ArScreen.kt`, `ui/ar/CatalogOverlay.kt`
      verify: after Petunjuk pertama → Katalog sekali; berikutnya tidak;
              skeleton/“Belum ada alat”/“Katalog gagal dimuat” + Coba lagi

## Phase 4: Bukti & converge

- [ ] **T007** Uji device: 6 kartu, ganti alat, marker tetap, last instrument
      blocked by: T006
      verify: screenshot katalog + model Bonang + pesan audio; pilih 6 alat;
              tutup/buka app → alat terakhir; marker hilang saat memilih;
              tidak crash; Saron audio kembali

- [ ] **T008** Update `feature_list.json`, `progress.md`, `ROADMAP.md`,
      `converge.md`
      blocked by: T007
      verify: `bash scripts/verify-docs.sh`; F-03 `passes: true`

## Rules

- UI katalog wajib mengikuti `S2-katalog.png`; badge progres tidak ditampilkan.
- Jangan menambah audio alat lain (F-04), badge/F-06, Info/F-05, volume/F-07.
- Home **Alat Musik** tetap nonaktif.
- Marker tidak boleh di-scan ulang saat ganti alat.
- Satu task per iterasi; verifikasi gagal → perbaiki.
