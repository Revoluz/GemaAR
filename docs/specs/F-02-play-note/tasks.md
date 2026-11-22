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

- [ ] **T001** Sampel audio Saron Pelog 1–7 + lisensi
      blocked by: none
      files: `app/src/main/res/raw/saron_pelog_1..7.ogg`,
             `app/src/main/assets/audio/LICENSE-ARTISTIC-2.0.txt`,
             `app/src/main/assets/audio/NOTICE.md`,
             `docs/decisions/0003-gamelan-audio-samples.md`
      verify: 7 file ada; durasi ±2,5 s; mono 44,1 kHz; tiap file <100 KB;
              lisensi/atribusi CDM tercatat

- [ ] **T002** Model Note + NoteMap Saron + unit test
      blocked by: T001
      files: `data/Note.kt`, `data/SaronNoteMap.kt`,
             `app/src/test/java/com/gemaar/data/SaronNoteMapTest.kt`
      verify: 7 note unik, id 1–7, label “Nada N”, sampel 1–7 terpetakan;
              test hijau

- [ ] **T003** Pemutar nada + state Loading/Ready/Failed
      blocked by: T001
      files: `audio/NotePlayer.kt`, `audio/SoundPoolNotePlayer.kt`
      verify: 7 sampel dimuat; `play` tidak memotong nada sebelumnya;
              `stopAll`/`release` aman; state Failed saat aset rusak

## Phase 2: Tap & UI

- [ ] **T004** Fungsi murni tap → indeks nada + unit test
      blocked by: T002
      files: `ar/NoteIndex.kt`,
             `app/src/test/java/com/gemaar/ar/NoteIndexTest.kt`
      verify: x = −0,25 → 0; x = 0 → 3; x = +0,25 → 6; di luar batas di-clamp

- [ ] **T005** Tap model di `ArController` → `onModelTap(normalizedX)`
      blocked by: T004, T001a
      files: `ar/ArController.kt`
      verify: tap model memanggil callback; tap background/null tidak;
              gerak >12 dp bukan tap; marker hilang tidak memanggil

- [ ] **T006** Note bar 1–7 + highlight + label
      blocked by: T002, T003
      design: `docs/design/UI/exports/S4-ar.png`
      files: `ui/ar/NoteBar.kt`
      verify: 7 tombol tampil; tombol aktif menyala; label “Nada N” muncul;
              tombol nonaktif saat audio Loading/Failed

- [ ] **T007** Wiring `ArScreen`: tap → NotePlayer, note bar visibility, error
      blocked by: T005, T006
      files: `ui/ar/ArScreen.kt`, `ar/ArUiState.kt`
      verify: Saron tampil → note bar muncul; marker hilang → hilang;
              tap bilah/tombol → nada sesuai; audio gagal → pesan + nonaktif;
              keluar AR/background → suara berhenti

## Phase 3: Bukti & converge

- [ ] **T008** Uji device: 7 nada, tap cepat, marker hilang/muncul, background
      blocked by: T007
      verify: screenshot note bar + label; catatan manual 7/7 nada;
              5 tap cepat tanpa crash; 3× marker hilang/muncul;
              tidak ada suara setelah keluar AR

- [ ] **T009** Update `feature_list.json`, `progress.md`, `ROADMAP.md`,
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
