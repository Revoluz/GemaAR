# Tasks: F-06 — Progres koleksi alat yang sudah dimainkan

**Input:** `spec.md`, `plan.md`

## Keputusan

- Layar S6 mengikuti `docs/design/UI/exports/S6-alat-musik.png`; grid 3×2.
- “Sudah dimainkan” = minimal satu nada/pukulan diputar di AR.
- Progres disimpan lokal bersama preferensi alat terakhir.
- S6 read-only; tidak ada navigasi ke AR.

## Phase 1: Data progres

- [x] **T001** `CollectionProgress` + simpan di `InstrumentStore`
      blocked by: none
      files: `data/CollectionProgress.kt`, `data/InstrumentStore.kt`,
             `app/src/test/java/com/gemaar/data/CollectionProgressTest.kt`
      verify: `markPlayed` idempoten; alat lain tidak berubah; id asing
              diabaikan; unit test lulus

## Phase 2: Layar & navigasi

- [x] **T002** `CollectionScreen` mengikuti S6
      blocked by: T001
      files: `ui/CollectionScreen.kt`
      verify: latar ilustrasi; tombol tutup X kanan atas; panel + plakat Alat Musik;
              subjudul; grid 3×2; kartu ikon/nama script/asal; badge hijau
              “Sudah dimainkan” + centang; badge terang “Belum dimainkan” +
              ikon putar; kartu tidak clickable; scroll di layar pendek

- [x] **T003** Navigasi Beranda → S6 → Beranda
      blocked by: T002
      files: `ui/HomeScreen.kt`, `MainActivity.kt`
      verify: tombol Alat Musik aktif; S6 terbuka; X/Back kembali ke Beranda;
              tombol AR/Pengaturan tetap seperti sebelumnya

- [x] **T004** Tandai alat saat nada diputar
      blocked by: T001
      files: `ui/ar/ArScreen.kt`
      verify: `playNote` menandai alat aktif setelah audio siap; pilih alat/
              buka AR/muat model/buka Info tidak menandai

## Phase 3: Bukti & converge

- [x] **T005** Uji device 6 alat + persistence + screenshot
      blocked by: T002–T004
      verify: Tab S9 — S6 kosong/sebagian/semua; Back; buka-tutup 5× tanpa
              crash; persistence dibaca setelah restart; badge sesuai desain;
              screenshot `F06-belum.png`, `F06-sebagian.png`, `F06-semua.png`

- [x] **T006** Update docs + converge
      blocked by: T005
      files: `converge.md`, `tasks.md`, `feature_list.json`, `progress.md`,
             `docs/design/SCREENS.md`, `docs/design/UI/exports/README.md`
      verify: 50 unit test; lint 0; `init.sh --check`; `verify-docs`;
              F-06 `passes: true`

## Rules

- S6 tidak memilih alat dan tidak membuka AR.
- Status hanya ditandai di jalur bunyi, bukan saat memilih/memuat.
- Jangan mengubah alur katalog AR atau audio.
- Jangan menambah dependency penyimpanan baru.
- Jangan menambah persentase/achievement/reset (non-goals).
- Verifikasi gagal → perbaiki, jangan lanjut.
