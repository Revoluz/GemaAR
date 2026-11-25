# Tasks: F-04 — Audio semua alat selain Saron

**Input:** `spec.md`, `plan.md`

## Keputusan grill

- Urutan: **Slenthem → Bonang → Gender → Gong/Kempul → Kendang**.
- Bonang: **6 nada bawah + 6 nada atas** (12 pencon).
- Gender: **14 bilah**; bilah nada 4 memakai sample nada 3
  (`GBPL3`/`GBPL3h`) sebagai tetangga terdekat.
- Kendang: **7 pad pukulan**, bukan simulasi dua kepala penuh.
- Kontrol: **tap 3D + note bar/pad** untuk semua alat.

## Phase 1: Fondasi audio

- [x] **T001** Generalisasi model audio + kontrol + player
      blocked by: none
      files: `data/Stroke.kt`, `data/InstrumentAudio.kt`,
             `audio/SoundPoolNotePlayer.kt`,
             `ui/ar/InstrumentControls.kt`, `ui/ar/NoteBar.kt`
      verify: `Rows`/`Pads`, `InstrumentControls`, player per alat; 35 unit test

- [x] **T002** Proses sample 5 alat + lisensi/atribusi
      blocked by: none
      files: `scripts/process_instrument_audio.py`,
             `app/src/main/res/raw/<instrument>_*.ogg`,
             `app/src/main/assets/audio/NOTICE.md`, `CREDITS.md`
      verify: 45 OGG baru; mono 44,1 kHz; 2,5 s; <30 KB per file;
              NOTICE/CREDITS diperbarui; Gender 4 memakai sample 3

## Phase 2: Per alat

- [x] **T003** Slenthem: 7 bilah + oktaf rendah
      blocked by: T001, T002
      files: `data/InstrumentAudioMap.kt`, `ar/InstrumentTapMap.kt`
      verify: 7 sample loaded; kontrol 1 baris; model scale 0,3;
              screenshot `F04-Slenthem.png`

- [x] **T004** Bonang: 12 pencon + 2 baris kontrol + tap mapping
      blocked by: T001, T002
      files: `data/InstrumentAudioMap.kt`, `ar/InstrumentTapMap.kt`,
             `ui/ar/InstrumentControls.kt`
      verify: 12 sample loaded; kontrol 2×6; screenshot `F04-Bonang.png`;
              log note bonang_5

- [x] **T005** Gender Barung: 14 bilah satu deret + tap mapping
      blocked by: T001, T002
      files: `data/InstrumentAudioMap.kt`, `ar/InstrumentTapMap.kt`
      verify: 14 sample loaded; bilah 4 memakai sample 3/3h; label “4”;
              kontrol 1×14; screenshot `F04-Gender.png`; log gender_4

- [x] **T006** Gong & Kempul: gong ageng + kempul 1–7 + suwukan
      blocked by: T001, T002
      files: `data/InstrumentAudioMap.kt`, `ar/InstrumentTapMap.kt`
      verify: 9 pad; K4 memakai sample K3; screenshot `F04-Gong.png`;
              log gong_ageng/kempul/suwukan

- [x] **T007** Kendang: 7 pad pukulan
      blocked by: T001, T002
      files: `data/InstrumentAudioMap.kt`, `ui/ar/InstrumentControls.kt`
      verify: 7 pad/7 sample loaded; screenshot `F04-Kendang-pads.png`;
              log kendang_dhung/dlang

## Phase 3: Bukti & converge

- [ ] **T008** Uji device semua alat + ganti alat + background
      blocked by: T003–T007
      verify: screenshot tiap kontrol; semua bagian/pad berbunyi; tap cepat;
              ganti alat menghentikan suara lama; marker hilang; keluar AR
              tidak ada suara; tidak crash

- [ ] **T009** Update `feature_list.json`, `progress.md`, `ROADMAP.md`,
      `converge.md`
      blocked by: T008
      verify: `bash scripts/verify-docs.sh`; F-04 `passes: true`

## Rules

- Tidak ada pemilih Pelog/Slendro atau oktaf.
- Jangan memakai sample Saron untuk alat lain.
- Jangan menambah Info (F-05), koleksi (F-06), atau volume (F-07).
- Semua sample baru wajib punya lisensi/atribusi.
- Tap mapping per alat harus diukur dari mesh, bukan lebar model.
- Gender bilah 4 memakai sample nada 3; jangan menyebutnya sample asli nada 4.
- Verifikasi gagal → perbaiki, jangan lanjut.
