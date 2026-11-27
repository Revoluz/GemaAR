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

- [x] **T002** Volume di `NotePlayer` + `SoundPoolNotePlayer`
      blocked by: T001
      files: `audio/NotePlayer.kt`, `audio/SoundPoolNotePlayer.kt`
      verify: `setVolume(0f..1f)`; `play` memakai volume; 0% senyap;
              100% penuh; nada berikutnya mengikuti volume baru < 1 detik
      catatan: dicentang 2026-11-27 setelah T004; log A35 menunjukkan volume
              0.0 / 0.55 / 0.99 mengikuti slider; bukti dengar penuh di T006

- [x] **T003** `SettingsPanel` mengikuti S7
      blocked by: T001
      files: `ui/components/SettingsPanel.kt`
      verify: modal tengah maks 720dp; plakat Pengaturan; satu baris Musik;
              slider emas 0–100; persentase; pill “tersimpan otomatis”;
              X dalam panel ≥48dp; tanpa SFX/bahasa; motion 180ms
      catatan: dicentang 2026-11-27; file tambahan `ui/theme/Tokens.kt` dan
              `res/values/strings.xml` (COMPONENTS #6/#7); motion 180ms
              terpasang di T004; screenshot S7 menyusul di T006

## Phase 3: Navigasi & AR

- [x] **T004** Aktifkan Pengaturan dari Beranda + gear AR + state global
      blocked by: T002, T003
      files: `MainActivity.kt`, `ui/HomeScreen.kt`, `ui/ar/ArScreen.kt`
      verify: Beranda → Pengaturan; AR gear → Pengaturan; volume live;
              X/Back menutup panel; nilai tersimpan; layer kembali ke asal
      catatan: dicentang 2026-11-27 (A35). Beranda & gear AR membuka panel;
              log audio 0.0 / 0.55 / 0.99 mengikuti slider; X dan Back menutup;
              restart app mempertahankan nilai; dari AR marker tetap terdeteksi
              tanpa scan ulang. Bug slider (mirroring + thumb diam) ditemukan
              di uji ini dan sudah diperbaiki sebelum commit

- [x] **T005** Pause AR saat panel dari AR
      blocked by: T004
      files: `ar/ArController.kt`, `ui/ar/ArScreen.kt`
      verify: panel dari AR → sesi AR berhenti sementara; tutup → AR lanjut
              tanpa scan ulang; buka/tutup 5× tanpa kamera hitam/crash
      catatan: dicentang 2026-11-27 (SM-A356E). `ARSceneView(lifecycle = ...)`
              memakai `LifecycleRegistry` milik `ArController`, ditahan di
              STARTED selama panel terbuka; tanpa `session.pause()` mentah.
              `MainActivity` juga disentuh untuk `paused = settingsVisible`
              (plan.md "Pause AR"; di luar daftar file di sini). Bukti: 6×
              pause + 6× resume sesi dan tidak ada sesi baru dibuat; layar
              100% identik setelah 3 detik saat panel terbuka vs 647–780 rb
              piksel berubah saat panel ditutup; status tetap "Marker
              terdeteksi"; tutup 5× tanpa crash (pid sama)

## Phase 4: Bukti & converge

- [x] **T006** Uji device + screenshot
      blocked by: T005
      verify: Tab S9 — 100%/40%/0%; bunyi berubah; persist setelah restart;
              ganti alat; geser cepat; nilai rusak 150 → 100%; screenshot
              `F07-100.png`, `F07-40.png`, `F07-0.png`, `F07-AR.png`
      catatan: dicentang 2026-11-27 di SM-A356E (A35) landscape, bukan Tab S9
              — deviasi device, tugas menyebut Tab S9. Dengar: 100=keras,
              40=agak keras, 2=pelan banget, 0=senyap. Persist 40 setelah app
              ditutup total; A/B di Bonang 100 vs 40 beda jelas; geser cepat
              ±20× final 31% tersimpan tanpa crash; 150 di preferensi → tampil
              100% dan app jalan. Screenshot tersimpan di
              `docs/design/UI/exports/verification/`. Dua catatan: latar di
              belakang panel tampak hitam di screenshot (di HP frame kamera
              beku normal — `screencap` tidak menangkap buffer terakhir), dan
              preferensi tetap menyimpan 150 sampai slider dilepas walau UI
              selalu menampilkan 100%

- [x] **T007** Update docs + converge
      blocked by: T006
      files: `converge.md`, `tasks.md`, `feature_list.json`, `progress.md`,
             `docs/design/SCREENS.md`, `docs/design/UI/exports/README.md`
      verify: unit test lulus; lint 0; `init.sh --check`; `verify-docs`;
              F-07 `passes: true`
      catatan: dicentang 2026-11-27. `converge.md` dibuat (Spec + Standards
              PASS). Verifikasi: 56 unit test 0 gagal (`--rerun-tasks`), lint
              OK (2 informational soal versi plugin/dependency), `init.sh
              --check` OK, `verify-docs` OK. `feature_list.json` F-07
              `passes: true` + path spec diperbaiki (`F-07-audio-settings` →
              `F-07-volume`). `SCREENS.md` S7 `draft` → `built` (status
              `verified` tidak dipakai layar lain mana pun). Matriks state S7 di
              `SCREENS.md` disesuaikan: "toast" → "pesan singkat di dalam
              panel"; `spec.md` FR-012 tidak diubah

## Rules

- Hanya satu baris pengaturan: **Musik**.
- Volume berlaku untuk semua alat; jangan per-alat.
- Jangan menambah SFX, bahasa, equalizer, mute terpisah, atau backsound.
- Jangan memanggil `session.pause()` mentah tanpa penanganan frame loop.
- Simpan saat slider dilepas, bukan setiap piksel gerakan.
- Jangan mengubah katalog, koleksi, kartu budaya, atau kontrol nada.
- Verifikasi gagal → perbaiki, jangan lanjut.
