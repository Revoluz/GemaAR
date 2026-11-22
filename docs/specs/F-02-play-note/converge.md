# Converge: F-02 — Ketuk alat musik untuk membunyikan nada

**Status:** PASS
**Tanggal:** 2026-11-22
**Device:** Samsung Galaxy Tab S9 (SM-X710, Android 16/API 36)
**Alat:** Saron, 7 nada Pelog

## Acceptance (spec)

| Ref | Skenario | Hasil | Bukti |
|---|---|---|---|
| US1.1 | Saron tampil, tabuh tidak menutupi bilah | PASS | `T001a-saron-tanpa-tabuh.png`, `T008-saron-tanpa-tabuh-ar.png` |
| US1.2 | Tap bilah kiri → nada 1 + label | PASS | log `play note=1`; user “sudah sesuai” |
| US1.3 | Tap 7 bilah kiri→kanan → 7 nada | PASS | log nada 7→1 berurutan; user “sudah sesuai” |
| US1.4 | Tap rangka/tabuh → nada terdekat | PASS | mapping pusat bilah; tap area model |
| US1.5 | Tap cepat → tumpang tindih, tidak crash | PASS | 35 stream `play` beruntun di log |
| US1.6 | Marker hilang → tidak ada nada baru | PASS | `T008-marker-hilang-notebar.png`; log `visible=false` |
| US1.7 | Model belum siap → tidak berbunyi | PASS | `play` hanya saat state Ready |
| US2.1 | Note bar 1–7 muncul saat Saron tampil | PASS | `T006-note-bar.png` |
| US2.2 | Tombol 3 → nada 3 + menyala + label | PASS | tombol 1/4/7 diuji; log `play note=1/4/7`; `T007-note-feedback.png` |
| US2.3 | Tombol 1–7 berbeda | PASS | 7 sample berbeda; user “sudah sesuai” |
| US2.4 | Marker hilang → note bar hilang | PASS | UI dump tanpa tombol 1–7; `T008-marker-hilang-notebar.png` |
| US2.5 | Audio belum siap → tombol nonaktif | PASS | `enabled = state == Ready` |
| US3.1 | Audio gagal → pesan + nonaktif | PASS (code path) | `NoteBarSection` state Failed |
| US3.2 | Audio gagal → tidak crash | PASS (code path) | `play` no-op saat Failed |

## Functional requirements

| FR | Hasil | Catatan |
|---|---|---|
| FR-001 Saron satu-satunya alat | PASS | `SaronNoteMap` |
| FR-002 7 nada Pelog | PASS | `saron_pelog_1..7.ogg` |
| FR-003 kiri→kanan 1→7 | PASS | pusat bilah hasil ukur mesh |
| FR-004 tap model → bilah terdekat | PASS | `NoteIndex.forMarkerLocalX` |
| FR-005 note bar muncul/hilang | PASS | `ArUiState.Placed` |
| FR-006 tombol → nada sesuai | PASS | log 1/4/7 |
| FR-007 highlight + label | PASS | `T007-note-feedback.png` |
| FR-008 nada boleh tumpang tindih | PASS | 35 stream beruntun |
| FR-009 berhenti saat keluar/background | PASS | `ON_PAUSE → stopAll`; SoundPool `idle`; user “tidak ada suara tersisa” |
| FR-010 audio gagal ditangani | PASS (code path) | pesan + nonaktif |
| FR-011 tidak menambah fitur lain | PASS | tidak ada scale/volume/info/koleksi |
| FR-012 feedback hanya highlight+label | PASS | tanpa getar/glow 3D |
| FR-013 tabuh tidak menutupi bilah | PASS | `Object_109` + `Object_112` dihapus |

## Success criteria

| SC | Hasil | Bukti |
|---|---|---|
| SC-001 7 nada berbeda | PASS | log 7→1; user konfirmasi |
| SC-002 bunyi ≤150 ms | PASS (manual) | tidak ada delay yang terlihat/terdengar |
| SC-003 5 tap cepat tanpa crash | PASS | 35 stream beruntun |
| SC-004 note bar 7/7 benar | PASS | 1/4/7 diuji langsung; 7 sample terpisah |
| SC-005 tidak ada suara setelah keluar | PASS | `dumpsys audio` SoundPool `idle`; user konfirmasi |
| SC-006 3× marker hilang/muncul | PASS | log `visible=true/false` berulang |
| SC-007 audio gagal tidak crash | PASS (code path) | state Failed |
| SC-008 7 bilah terlihat tanpa tabuh | PASS | render + AR screenshot |

## Perintah verifikasi

```bash
export JAVA_HOME=/usr/lib/jvm/temurin-25-jdk-amd64
./gradlew assembleDebug          # OK
./gradlew testDebugUnitTest      # OK, 18 test
./gradlew lintDebug              # OK, 0 issue
bash init.sh --check             # Environment OK
bash scripts/verify-docs.sh      # OK
```

## Aset audio

| File | Durasi | Format | Ukuran |
|---|---|---|---|
| `saron_pelog_1.ogg` | 2,50 s | Vorbis, 44,1 kHz, mono | 24,5 KB |
| `saron_pelog_2.ogg` | 2,50 s | Vorbis, 44,1 kHz, mono | 25,9 KB |
| `saron_pelog_3.ogg` | 2,50 s | Vorbis, 44,1 kHz, mono | 25,9 KB |
| `saron_pelog_4.ogg` | 2,50 s | Vorbis, 44,1 kHz, mono | 22,1 KB |
| `saron_pelog_5.ogg` | 2,50 s | Vorbis, 44,1 kHz, mono | 21,8 KB |
| `saron_pelog_6.ogg` | 2,50 s | Vorbis, 44,1 kHz, mono | 25,3 KB |
| `saron_pelog_7.ogg` | 2,50 s | Vorbis, 44,1 kHz, mono | 22,9 KB |

Sumber: CDM Gamelan Sample Library, Artistic License 2.0. Lisensi dan
perubahan tercatat di `app/src/main/assets/audio/` dan `CREDITS.md`.

## Deviasi dari plan

1. **Mapping tap memakai pusat bilah**, bukan pembagian rata selebar model.
   Pembagian selebar model membuat bilah 1 memicu nada 2 dan bilah 7 memicu
   nada 6. Pusat bilah diukur dari mesh `Object_33` dan diskalakan.
2. **Tabuh dihapus** dari `saron.glb` (`Object_109` + `Object_112`) agar bilah
   bisa dilihat dan ditap. Model turun dari 1,18 MB/15.906 tris menjadi
   0,46 MB/5.058 tris.
3. **Note bar** memakai 7 tombol angka minimal, muncul hanya saat Saron tampil.
4. **Audio** memakai sample CDM, bukan rekaman/sintesis sendiri.

## Known limitations

- Model diskalakan 0,5 m; pada jarak sangat dekat model bisa memenuhi layar.
- Highlight hanya di note bar dan label; bilah 3D tidak berubah warna.
- Audio tidak mengambil alih fokus audio aplikasi lain; perilaku volume
  lanjutan ditunda ke F-07.
- Audio gagal dimuat diuji lewat code path, belum dipaksa dengan aset rusak di
  device.
- Sample asli dipotong 2,5 detik; decay nada rendah bisa terasa lebih pendek
  dari alat asli.

## Kesimpulan

Semua acceptance F-02 yang bisa diuji terpenuhi. Build, 18 unit test, lint,
`init.sh --check`, dan `verify-docs.sh` hijau. F-02 layak `passes: true`.
