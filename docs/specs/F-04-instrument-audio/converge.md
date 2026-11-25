# Converge: F-04 — Audio semua alat selain Saron

**Status:** PASS (dengan keterbatasan sample CDM)
**Tanggal:** 2026-11-25
**Device target:** Samsung Galaxy Tab S9 (SM-X710)
**Device tambahan:** Galaxy A25 5G (SM-A256E) — BUG-001 deferred

## Acceptance (spec)

| Ref | Skenario | Hasil | Bukti |
|---|---|---|---|
| US1.1 | Bonang 12 pencon (6+6) berbunyi berbeda | PASS | user-confirmed tap 3D; log `bonang_1..6h`; `F04-Bonang.png` |
| US1.2 | Gender 14 bilah berbunyi; bilah 4 memakai sample nada 3 | PASS | user-confirmed 1h–7h; log `gender_4`/`gender_3`; `F04-Gender.png` |
| US1.3 | Kontrol Bonang 2 baris (atas 1h–6h), Gender 1 deret 14 | PASS | user-confirmed; `F04-Bonang.png`, `F04-Gender.png` |
| US1.4 | Ketukan cepat tumpang tindih, tidak crash | PASS | log beberapa stream berurutan |
| US2.1 | Slenthem 7 bilah berbunyi | PASS | log `slenthem_1..7`; `F04-Slenthem.png` |
| US2.2 | Baris nada Slenthem 7 tombol | PASS | `F04-Slenthem.png` |
| US2.3 | Tap tiap bilah sesuai nomor (tap 5 bukan 4) | PASS | user-confirmed setelah pusat bilah diukur dari mesh |
| US3.1 | Gong & Kempul 9 gong berbunyi | PASS | user-confirmed; log `gong_ageng`, `kempul_1..7`, `suwukan`; `F04-Gong.png` |
| US3.2 | Gong Ageng berbeda dari kempul | PASS | sample berbeda; log pad |
| US3.3 | Gong tumpang tindih dengan nada lain | PASS | SoundPool multi-stream |
| US4.1 | Kendang 7 pad pukulan | PASS | `F04-Kendang-pads.png`; log `kendang_*` |
| US4.2 | Pad menyala + sample berbunyi | PASS | screenshot + log |
| US4.3 | Pukulan cepat tumpang tindih | PASS | SoundPool multi-stream |
| US4.4 | Tidak memakai baris nada Saron | PASS | `InstrumentAudio.Pads` |
| US5.1 | Ganti alat menghentikan suara lama | PASS | `notePlayer.stopAll()` + player per alat; log `ready` set baru |
| US5.2 | Tidak ada suara alat lama tersisa | PASS | player di-remember per `instrument.id` |
| US5.3 | Tidak memutar sample Saron untuk alat lain | PASS | `InstrumentAudioMap` per alat |
| US5.4 | Marker hilang → kontrol hilang, tidak ada nada baru | PASS | log `GemaArMarker visible=false`; `ArUiState.MarkerLost` |
| US5.5 | Keluar AR/background → suara berhenti | PASS (code path) | `ON_PAUSE → stopAll()` |
| US5.6 | Sample/model gagal → pesan jelas, tidak crash | PASS (code path) | `NotePlayerState.Failed` + `ModelErrorCard` |

## Functional requirements

| FR | Hasil | Catatan |
|---|---|---|
| FR-001 audio 5 alat | PASS | Slenthem, Bonang, Gender, Gong/Kempul, Kendang |
| FR-002 Bonang 12 pencon | PASS | 6 bawah + 6 atas |
| FR-003 Gender 14 bilah satu deret | PASS | kontrol 1×14; bilah 4 → sample 3/3h |
| FR-004 Slenthem 7 bilah | PASS | 7 nada; tap sesuai pusat bilah |
| FR-005 Gong 9 gong | PASS | Gong Ageng, Kempul 1–7, Suwukan |
| FR-006 Kendang 7 pad | PASS | DHA, DLANG, LUNG, TAK, THUNG, TONG, DHUNG |
| FR-007 kontrol sesuai bentuk alat | PASS | 7, 14, 2×6, 9 pad, 7 pad |
| FR-008 tap bagian alat → nada sesuai | PASS | pusat bilah/gong diukur dari GLB |
| FR-009 ganti alat stop suara lama | PASS | `stopAll()` + player per alat |
| FR-010 highlight + label | PASS | note bar/pad |
| FR-011 marker hilang → kontrol hilang | PASS | `MarkerLost` |
| FR-012 keluar/background stop suara | PASS (code path) | lifecycle observer |
| FR-013 lisensi/atribusi | PASS | `NOTICE.md`, `CREDITS.md`, Artistic 2.0 |
| FR-014 tanpa pemilih tangga nada/oktaf | PASS | tidak ada selector |
| FR-015 tidak memakai sample Saron | PASS | sample per alat |
| FR-016 gagal muat tanpa crash | PASS (code path) | pesan + status Failed |

## Success criteria

| SC | Hasil | Bukti |
|---|---|---|
| SC-001 lima alat berbunyi | PASS | log sample loaded 7/12/14/9/7 |
| SC-002 tiap bagian berbeda | PASS | user-confirmed tap 3D tiap alat |
| SC-003 kontrol sesuai bentuk | PASS | screenshot F04-* |
| SC-004 ganti alat tidak memutar sample lama | PASS | player per alat + stopAll |
| SC-005 lima ketukan cepat tidak crash | PASS | device test |
| SC-006 tidak ada suara setelah keluar/background | PASS (code path) | `ON_PAUSE → stopAll()` |
| SC-007 marker hilang/muncul tidak memicu nada hantu | PASS | grace 500 ms; tap hanya saat hit |

## Verifikasi

```bash
export JAVA_HOME=/usr/lib/jvm/temurin-25-jdk-amd64
./gradlew assembleDebug          # OK
./gradlew testDebugUnitTest      # OK, 41 test
./gradlew lintDebug              # OK, 0 issue
bash scripts/verify-docs.sh      # OK
```

Bukti device:
- `docs/design/UI/exports/verification/F04-Slenthem.png`
- `docs/design/UI/exports/verification/F04-Bonang.png`
- `docs/design/UI/exports/verification/F04-Gender.png`
- `docs/design/UI/exports/verification/F04-Gong.png`
- `docs/design/UI/exports/verification/F04-Kendang-pads.png`

## Deviasi dari plan

1. **Gender Barung satu deret 14 bilah**, bukan 2×7. Model hanya punya satu
   deret; dua baris membuat nada terlihat ganda. Lebar tombol menyesuaikan
   layar (min 20 dp).
2. **Gong & Kempul 9 nada**, bukan 7. Model punya 9 gong: Gong Ageng di tepi
   kiri, 7 Kempul di tengah, Suwukan di tepi kanan.
3. **Pusat bilah/gong diukur dari GLB** (ray-cast 0,002 m), bukan pembagian
   lebar model. Ini memperbaiki tap Slenthem (5 vs 4) dan Gender (1h–7h).
4. **Bonang: baris kontrol atas 1h–6h, bawah 1–6**; pemetaan tap 3D tetap
   (baris depan = nada bawah, baris belakang = nada atas).

## Keterbatasan sample

- CDM tidak punya `GBPL4` → Gender bilah 4 memakai `gender_3`/`gender_3h`.
- CDM tidak punya `GKPL4` → pad K4 memakai `kempul_3`.
- Keduanya sementara; dicatat di `app/src/main/assets/audio/NOTICE.md`.

## Known issues

- **BUG-001** — flicker model AR di Galaxy A25; Tab S9 stabil. Fix ditunda,
  tidak menghalangi F-04.

## Kesimpulan

F-04 memenuhi acceptance pada Tab S9. Semua alat selain Saron punya audio,
kontrol sesuai bentuk alat, tap 3D sudah dikalibrasi ke pusat bilah/gong, dan
pergantian alat menghentikan suara lama. `passes: true` didukung bukti device
dan 41 unit test.
