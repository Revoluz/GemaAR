# Tasks: F-05 — Kartu info budaya tiap alat

**Input:** `spec.md`, `plan.md`

## Keputusan

- Panel mengikuti `docs/design/UI/exports/S5-kartu-budaya.png`; tidak ada
  bahasa visual baru.
- Isi budaya untuk **6 alat**, disimpan di satu peta data.
- Tombol Info aktif kapan saja selama layar AR hidup.
- Chip Pelog/Slendro pada gambar acuan **tidak** dibangun.

## Phase 1: Data & naskah

- [x] **T001** Model `CultureCard` + isi 6 alat
      blocked by: none
      files: `data/CultureCard.kt`, `data/CultureCardMap.kt`,
             `app/src/test/java/com/gemaar/data/CultureCardMapTest.kt`
      verify: 6 kartu (saron, bonang, slenthem, gender_barung, gong_kempul,
              kendang); semua field tidak kosong; `sourceCredit` ada; id tidak
              dikenal → null; 4 unit test baru lulus

## Phase 2: Panel & wiring

- [x] **T002** `InfoPanel` mengikuti S5
      blocked by: T001
      files: `ui/components/InfoPanel.kt`
      verify: panel kanan 34% (maks 420dp), tinggi ±80%; plakat Info; judul
              script Caveat; body rata kanan + scroll; kredit sumber; badge
              Kartu Budaya; tombol tutup X ≥48dp; tanpa chip laras

- [x] **T003** Aktifkan tombol Info + state + BackHandler
      blocked by: T002
      files: `ui/ar/ArScreen.kt`
      verify: tombol Info membuka panel alat aktif; X menutup; Back menutup
              panel dulu; buka Katalog/Petunjuk menutup panel; audio/model tidak
              berubah; panel tetap bisa dibuka saat marker hilang/audio gagal

## Phase 3: Bukti & converge

- [x] **T004** Uji device 6 alat + screenshot
      blocked by: T003
      verify: Tab S9 — panel Bonang/Slenthem/Gong/Kendang; scroll; tombol X;
              Back menutup panel; panel terbuka saat marker hilang; ganti alat →
              isi berganti; screenshot `F05-Bonang.png`, `F05-Gong.png`,
              `F05-Kendang.png`

- [x] **T005** Update docs + converge
      blocked by: T004
      files: `converge.md`, `tasks.md`, `feature_list.json`, `progress.md`,
             `docs/design/UI/exports/README.md`
      verify: 45 unit test; lint 0; `init.sh --check`; `verify-docs`;
              F-05 `passes: true`

## Rules

- Panel **tidak** menghentikan audio, mengganti alat, atau memuat ulang model.
- Tidak ada chip Pelog/Slendro atau pemilih oktaf.
- Tidak ada konten dari jaringan saat panel dibuka.
- Semua fakta harus bisa ditelusuri ke **Sumber** di `spec.md`.
- Jangan menambah komponen baru jika `InfoPanel`/`PlaqueTitle`/`IconButton`
  sudah cukup.
- Jangan mengubah `ar/`, `audio/`, atau model 3D untuk fitur ini.
- Verifikasi gagal → perbaiki, jangan lanjut.
