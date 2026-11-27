# Converge: F-07 — Pengaturan volume musik

**Status:** PASS
**Tanggal:** 2026-11-27
**Device target:** Samsung Galaxy A35 (SM-A356E) — bukan Tab S9 seperti di `spec.md`/`tasks.md`

> Dua sumbu: **Spec** (apa yang diminta) dan **Standards** (aturan repo).
> Dinilai terpisah; nilai dulu, perbaiki kemudian.

## Axis 1 — Spec

### Acceptance (spec)

| Ref | Skenario | Hasil | Bukti |
|---|---|---|---|
| US1.1 | Beranda → Pengaturan membuka panel S7 | PASS | device test T004; `F07-100.png` (panel + plakat + baris Musik) |
| US1.2 | Gear di AR membuka panel di atas AR | PASS | `F07-AR.png`; status "Marker terdeteksi" masih terlihat di belakang panel |
| US1.3 | Tutup panel → kembali ke layar asal | PASS | device test; X dan Back sama-sama menutup panel dulu |
| US1.4 | Panel dari AR ditutup → AR lanjut tanpa scan ulang | PASS | T005: sesi AR dijeda lewat lifecycle; `session=pause`/`session=resume` 6×, 0 sesi baru dibuat, status tetap Placed |
| US1.5 | Back sistem menutup panel lebih dulu | PASS | `MainActivity` `BackHandler` di dalam overlay; device test T004 |
| US2.1 | Slider menampilkan nilai saat ini | PASS | `F07-100.png` (100%), `F07-40.png` (40%), `F07-0.png` (0%) |
| US2.2 | Geser slider → persentase ikut | PASS | label mengikuti jari; geser cepat ±20× berakhir 31% |
| US2.3 | Nada berikutnya mengikuti volume baru < 1 detik | PASS | log volume saat `play`: 0.0 / 0.55 / 0.99 (T002/T004); dengar di T006 |
| US2.4 | 0% tidak ada bunyi | PASS | dengar di device: senyap; prefs `music_volume=0` |
| US2.5 | 100% bunyi penuh | PASS | dengar di device: keras; prefs `music_volume=100` |
| US3.1 | Tersimpan otomatis tanpa tombol simpan | PASS | prefs berubah saat slider dilepas; pill "tersimpan otomatis" |
| US3.2 | App ditutup & dibuka lagi → nilai terakhir | PASS | 40% → tutup total → buka → 40% |
| US3.3 | Volume sama untuk semua alat | PASS | A/B di Bonang: 100% vs 40% beda jelas; satu nilai di `MainActivity` |
| US3.4 | Nilai rusak/ di luar 0–100 → 100% | PASS | `music_volume=150` di prefs → label 100%, app jalan |
| US3.5 | Gagal menyimpan → pesan singkat + nilai kembali | PASS (code path) | `SettingsPanel(message=)` + `volumePercent = lastSavedPercent`; jalur gagal tidak bisa dipaksa di device |

### Functional requirements

| FR | Hasil | Catatan |
|---|---|---|
| FR-001 Pengaturan di Beranda aktif | PASS | `HomeScreen(onOpenSettings)` |
| FR-002 gear AR aktif | PASS | `HudButton` gear `enabled = true` |
| FR-003 satu slider Musik 0–100 | PASS | `SettingsPanel` + `SliderRow` |
| FR-004 persentase tampil | PASS | `settings_percent`, tabular |
| FR-005 geser → persen + volume < 1 detik | PASS | `LaunchedEffect(volumePercent)` → `setVolume` |
| FR-006 berlaku semua nada/alat | PASS | satu nilai global; player dibuat per alat memakai nilai itu |
| FR-007 tersimpan otomatis & dipulihkan | PASS | `AudioSettingsStore.saveVolume` saat `onValueChangeFinished` |
| FR-008 0% senyap, 100% penuh | PASS | `MusicVolume.fraction` → `soundPool.play(..., volume)` |
| FR-009 X dan Back menutup panel | PASS | `onClose` + `BackHandler` |
| FR-010 panel dari AR mem-pause sesi AR | PASS | `ARSceneView(lifecycle = ...)` ditahan di STARTED |
| FR-011 nilai rusak → 100% | PASS | `MusicVolume.of(percent).coerceIn(0,100)` |
| FR-012 gagal simpan → pesan, app tidak berhenti | PASS (code path) | pesan di dalam panel + nilai dikembalikan |
| FR-013 mengikuti desain S7 | PASS | `S7-pengaturan.png` vs `F07-100/40/0/AR.png` |
| FR-014 tanpa SFX/bahasa/equalizer | PASS | satu-satunya baris: Musik |

### Success criteria

| SC | Hasil | Bukti |
|---|---|---|
| SC-001 panel terbuka dari Beranda & AR 5/5 | PASS | Beranda & gear berhasil berulang di T004/T006 (satu tap meleset sekali di awal uji T005, tidak berulang) |
| SC-002 geser slider mengubah persen & bunyi 5/5 | PASS | T006: 100/40/2/0 semuanya terasa bedanya |
| SC-003 nilai bertahan setelah app ditutup | PASS | 40% → 40% |
| SC-004 0% senyap, 100% penuh | PASS | device test |
| SC-005 volume sama untuk keenam alat | PASS (kode + spot-check) | satu nilai di app-level; A/B di Saron & Bonang |
| SC-006 tidak crash saat geser cepat/data rusak/panel berulang | PASS | geser ±20×, prefs 150, 6× buka-tutup panel |
| SC-007 cocok S7 pada empat hal teramati | PASS | panel modal, plakat Pengaturan, satu baris Musik + slider, X di dalam panel |

## Axis 2 — Standards

| # | Aturan | Hasil | Bukti |
|---|---|---|---|
| S1 | `ui/` tidak memanggil ARCore/SceneView langsung | PASS | jeda AR lewat parameter `paused` → `ar/ArController` |
| S2 | `ar/` dan `audio/` tidak saling bergantung | PASS | volume masuk lewat parameter; `ar/` tidak menyentuh player |
| S3 | Teks user-visible via `strings.xml` | PASS | `settings_title`, `settings_music_label`, `settings_percent`, `settings_autosave`, `settings_close`, `settings_save_failed` |
| S4 | Ukuran/warna via token, bukan angka mentah | PASS | `ui/theme/Tokens.kt` (space, radius, motion, font size, shadow) |
| S5 | Tanpa dependency baru | PASS | `AudioSettingsStore` memakai `SharedPreferences` yang sudah ada |
| S6 | Tidak ada `session.pause()` mentah | PASS | jeda lewat `LifecycleRegistry`; frame loop tetap milik SceneView |
| S7 | Tidak ada perubahan di luar scope | PASS | hanya F-07: data/audio/ui/ar + docs F-07 |
| S8 | Generated code tidak diedit | PASS | `app/build/` tidak disentuh |

## Verifikasi

```bash
./gradlew assembleDebug --rerun-tasks   # OK
./gradlew testDebugUnitTest             # OK, 56 test, 0 gagal
./gradlew lintDebug                     # OK; SARIF 2 informational (versi plugin/dependency), 0 error di kode
bash init.sh --check                    # OK
bash scripts/verify-docs.sh             # OK
```

Bukti device: `docs/design/UI/exports/verification/F07-100.png`,
`F07-40.png`, `F07-0.png`, `F07-AR.png`.

## Catatan bukti

- Uji T006 dijalankan di **SM-A356E (A35) landscape**, bukan Tab S9. Semua
  perilaku yang diminta spec teramati di device ini.
- Bunyi: 100% = keras, 40% = agak keras, 2% = pelan banget, 0% = senyap.
- Jalur simpan terkonfirmasi dari isi `shared_prefs/gemaar_preferences.xml`
  (`music_volume` = 100 / 40 / 0 / 31 sesuai nilai terakhir tiap uji).
- Latar di belakang panel tampak **hitam pada screenshot** (F07-100/40/0/AR).
  Di HP frame kamera terakhir **beku seperti seharusnya**; `screencap` tidak
  menangkap buffer terakhir surface AR setelah sesi dijeda. Bukan bug.
- Nilai 150 tetap tersimpan di prefs sampai slider digeser/dilepas; aplikasi
  selalu menampilkan 100% saat dibuka (FR-011 terpenuhi secara perilaku,
  penyimpanan tidak ditulis ulang).
- Selisih 1% (label 32 saat jari masih menyeret → nilai final 31) adalah
  pembulatan pada pelepasan slider, bukan nilai yang hilang.

## Deviasi dari plan/spec

1. **Device:** `spec.md` dan `tasks.md` menyebut Tab S9; uji dilakukan di A35
   karena itu perangkat yang tersedia. Tidak ada perubahan dokumen aturan.
2. **T005 file list:** `tasks.md` T005 hanya menyebut `ar/ArController.kt` dan
   `ui/ar/ArScreen.kt`; `plan.md` ("Pause AR") menyebut `MainActivity` juga,
   dan plan menang menurut precedence. `MainActivity` ikut diubah.
3. **Pesan gagal simpan** tampil sebagai teks di dalam panel. Matriks state S7
   di `SCREENS.md` semula menulis "toast"; baris itu diperbaiki menjadi "pesan
   singkat di dalam panel + nilai kembali" karena `spec.md` (FR-012) hanya
   mewajibkan "pesan singkat" dan `SCREENS.md` sendiri menyatakan bukan sumber
   acceptance criteria. Aturan penerimaan tidak berubah.
4. **`feature_list.json`** entri F-07 menunjuk `docs/specs/F-07-audio-settings/`
   yang tidak ada; diperbaiki ke `docs/specs/F-07-volume/` saat converge.
5. **Slider visual** memakai stop indicator bawaan Material 3 (titik di ujung
   track). Bukan elemen baru, bukan penyimpangan dari S7.

## Kesimpulan

Kedua sumbu pass. F-07 memenuhi acceptance spec untuk panel, volume, persistence,
dan jeda sesi AR tanpa melewati aturan repo (boundary modul, teks di
`strings.xml`, token ukuran/warna, tanpa dependency baru). `passes: true`
didukung 56 unit test, lint bersih, dan empat bukti screenshot di device.

**Keputusan yang diambil:** baris "toast" di matriks state S7 (`SCREENS.md`)
disesuaikan menjadi "pesan singkat di dalam panel + nilai kembali", mengikuti
`spec.md` FR-012. Tidak ada acceptance criteria yang berubah.
