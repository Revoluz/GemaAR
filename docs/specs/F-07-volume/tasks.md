# Tasks: F-07 — Pengaturan volume musik

**Input:** `spec.md`, `plan.md`

## Keputusan

- Hanya **satu slider Musik**; volume berlaku untuk semua nada/pukulan alat.
- Default **100%**; langkah 1%; simpan otomatis saat slider dilepas.
- Panel mengikuti `S7-pengaturan.png`; X di dalam panel kanan atas.
- Panel dari AR mem-pause sesi AR; tutup → lanjut tanpa scan ulang.
- Tidak ada SFX, bahasa, equalizer, atau tombol mute terpisah.

## Phase 1: Nilai & penyimpanan

- [x] **T001** `MusicVolume` + `AudioSettingsStore`
      blocked by: none
      files: `data/MusicVolume.kt`, `data/AudioSettingsStore.kt`,
             `app/src/test/java/com/gemaar/data/MusicVolumeTest.kt`
      verify: default 100; clamp <0 dan >100; 0 = muted; fraction 0..1;
              data rusak → 100; simpan mengembalikan berhasil/gagal; unit test

## Phase 2: Audio & panel

- [ ] **T002** Volume di `NotePlayer` + `SoundPoolNotePlayer`
      blocked by: T001
      files: `audio/NotePlayer.kt`, `audio/SoundPoolNotePlayer.kt`
      verify: `setVolume(0f..1f)`; `play` memakai volume; 0% senyap;
              100% penuh; nada berikutnya mengikuti volume baru < 1 detik
      catatan: kode selesai 2026-11-27; centang menunggu bukti bunyi 0%/100%
              di T004 (pemanggil) dan T006 (uji device)

- [ ] **T003** `SettingsPanel` mengikuti S7
      blocked by: T001
      files: `ui/components/SettingsPanel.kt`
      verify: modal tengah maks 720dp; plakat Pengaturan; satu baris Musik;
              slider emas 0–100; persentase; pill “tersimpan otomatis”;
              X dalam panel ≥48dp; tanpa SFX/bahasa; motion 180ms
      catatan: kode selesai 2026-11-27; file tambahan `ui/theme/Tokens.kt` dan
              `res/values/strings.xml` (COMPONENTS #6/#7); centang menunggu
              motion terpasang di T004 dan bukti device di T006 (opsi 1)

## Phase 3: Navigasi & AR

- [ ] **T004** Aktifkan Pengaturan dari Beranda + gear AR + state global
      blocked by: T002, T003
      files: `MainActivity.kt`, `ui/HomeScreen.kt`, `ui/ar/ArScreen.kt`
      verify: Beranda → Pengaturan; AR gear → Pengaturan; volume live;
              X/Back menutup panel; nilai tersimpan; layer kembali ke asal

- [ ] **T005** Pause AR saat panel dari AR
      blocked by: T004
      files: `ar/ArController.kt`, `ui/ar/ArScreen.kt`
      verify: panel dari AR → sesi AR berhenti sementara; tutup → AR lanjut
              tanpa scan ulang; buka/tutup 5× tanpa kamera hitam/crash

## Phase 4: Bukti & converge

- [ ] **T006** Uji device + screenshot
      blocked by: T005
      verify: Tab S9 — 100%/40%/0%; bunyi berubah; persist setelah restart;
              ganti alat; geser cepat; nilai rusak 150 → 100%; screenshot
              `F07-100.png`, `F07-40.png`, `F07-0.png`, `F07-AR.png`

- [ ] **T007** Update docs + converge
      blocked by: T006
      files: `converge.md`, `tasks.md`, `feature_list.json`, `progress.md`,
             `docs/design/SCREENS.md`, `docs/design/UI/exports/README.md`
      verify: unit test lulus; lint 0; `init.sh --check`; `verify-docs`;
              F-07 `passes: true`

## Rules

- Hanya satu baris pengaturan: **Musik**.
- Volume berlaku untuk semua alat; jangan per-alat.
- Jangan menambah SFX, bahasa, equalizer, mute terpisah, atau backsound.
- Jangan memanggil `session.pause()` mentah tanpa penanganan frame loop.
- Simpan saat slider dilepas, bukan setiap piksel gerakan.
- Jangan mengubah katalog, koleksi, kartu budaya, atau kontrol nada.
- Verifikasi gagal → perbaiki, jangan lanjut.
