# Tasks: F-02 — Ketuk alat musik untuk membunyikan nada

**Input:** `spec.md`, `plan.md`

## Phase 1: Aset & fondasi

- [x] **T001a** Saron tanpa tabuh
      blocked by: none
      files: `docs/design/assets/models/saron.glb`,
             `app/src/main/assets/models/saron.glb`
      verify: `Object_109` + `Object_112` hilang; 7 bilah terlihat;
              bbox X/Y tetap, tinggi turun karena kepala tabuh hilang;
              render `verification/T001a-saron-tanpa-tabuh.png`;
              GLB lolos `gltf-transform inspect`; model load di Tab S9 tanpa error

- [x] **T001** Sampel audio Saron Pelog 1–7 + lisensi
      blocked by: none
      files: `app/src/main/res/raw/saron_pelog_1..7.ogg`,
             `app/src/main/assets/audio/LICENSE-ARTISTIC-2.0.txt`,
             `app/src/main/assets/audio/NOTICE.md`,
             `docs/decisions/0003-gamelan-audio-samples.md`,
             `scripts/process_saron_audio.py`
      verify: 7 file ada, 2,50 s, mono 44,1 kHz, 21,8–25,9 KB;
              lisensi Artistic 2.0 + NOTICE + atribusi `CREDITS.md`

- [x] **T002** Model Note + NoteMap Saron + unit test
      blocked by: T001
      files: `data/Note.kt`, `data/SaronNoteMap.kt`,
             `app/src/test/java/com/gemaar/data/SaronNoteMapTest.kt`
      verify: 7 note unik, id 1–7, label “Nada N”, sampel 1–7 terpetakan;
              `SaronNoteMapTest` 6/6 hijau

- [x] **T003** Pemutar nada + state Loading/Ready/Failed
      blocked by: T001
      files: `audio/NotePlayer.kt`, `audio/SoundPoolNotePlayer.kt`
      verify: log `ready: 7 samples loaded`; 35 stream beruntun tanpa crash;
              nada tumpang tindih; `stopAll` saat background; `release` saat keluar

## Phase 2: Tap & UI

- [x] **T004** Fungsi murni tap → indeks nada + unit test
      blocked by: T002
      files: `ar/NoteIndex.kt`,
             `app/src/test/java/com/gemaar/ar/NoteIndexTest.kt`
      verify: pusat bilah −0,1321 → 0; −0,0836 → 1; …; +0,1373 → 6;
              bilah 1 tidak lagi memicu nada 2 dan bilah 7 tidak memicu nada 6;
              `NoteIndexTest` 5/5 hijau

- [x] **T005** Tap model di `ArController` → `onModelTap(normalizedX)`
      blocked by: T004, T001a
      files: `ar/ArController.kt`
      verify: tap bilah memanggil callback; log memutar nada 7→1 berurutan;
              tap background/null dan marker hilang tidak memanggil

- [x] **T006** Note bar 1–7 + highlight + label
      blocked by: T002, T003
      design: `docs/design/UI/exports/S4-ar.png`
      files: `ui/ar/NoteBar.kt`
      verify: 7 tombol tampil; tombol 4 menyala + label “Nada 4”;
              tombol nonaktif saat audio Loading/Failed
              → `verification/T006-note-bar.png`,
                `verification/T007-note-feedback.png`

- [x] **T007** Wiring `ArScreen`: tap → NotePlayer, note bar visibility, error
      blocked by: T005, T006
      files: `ui/ar/ArScreen.kt`, `ar/ArUiState.kt`
      verify: Saron tampil → note bar muncul; marker hilang → note bar hilang
              (`verification/T008-marker-hilang-notebar.png`);
              tombol 1/4/7 memutar nada 1/4/7; Home → SoundPool idle;
              pengguna konfirmasi “tidak ada suara tersisa”

## Phase 3: Bukti & converge

- [x] **T008** Uji device: 7 nada, tap cepat, marker hilang/muncul, background
      blocked by: T007
      verify: screenshot note bar + label; log 35 nada beruntun;
              3× marker hilang/muncul; user konfirmasi mapping benar;
              user konfirmasi tidak ada suara setelah Home;
              bukti di `verification/T006/T007/T008*`

- [x] **T009** Update `feature_list.json`, `progress.md`, `ROADMAP.md`,
      `converge.md`
      blocked by: T008
      verify: `bash scripts/verify-docs.sh`; F-02 `passes: true` dengan evidence

## Rules

- Satu task per iterasi.
- **UI note bar wajib mengikuti desain S4** (`docs/design/UI/exports/S4-ar.png`);
  jangan mengubah layout/token tanpa update desain.
- Jangan menambah alat lain, scale selector, volume, Info, atau koleksi.
- `saron.glb` hanya diubah untuk menghapus tabuh; jangan memisah/mengubah bilah.
- Mapping tap memakai posisi, bukan node bilah.
- Semua audio harus lolos batas AGENTS: OGG mono 44,1 kHz, <300 KB per file.
- Verifikasi gagal → perbaiki, jangan lanjut.
