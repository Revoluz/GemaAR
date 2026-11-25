# Plan: F-07 — Pengaturan volume musik

**Input:** `spec.md`
**Status:** approved
**Scope:** panel S7 + slider volume + penyimpanan lokal + pause AR saat panel dibuka dari S4

## Prinsip

- **Tidak ada design baru.** Panel mengikuti `S7-pengaturan.png`.
- **Satu slider.** Hanya volume bunyi alat; tidak ada SFX/bahasa/equalizer.
- **Satu sumber nilai.** Volume disimpan di satu tempat dan diteruskan ke
  pemutar audio; UI tidak menyimpan salinan permanen.
- **Tanpa dependency baru.** Penyimpanan memakai preferensi lokal yang sudah ada.

## Arsitektur

```
data/
├── MusicVolume.kt          # nilai 0–100, sanitasi, default 100 (murni)
└── AudioSettingsStore.kt   # baca/tulis volume di preferensi lokal
audio/
├── NotePlayer.kt           # tambah setVolume(fraction)
└── SoundPoolNotePlayer.kt  # pakai volume saat play()
ui/
├── components/
│   └── SettingsPanel.kt    # S7: plakat, baris Musik, slider, persen, pill, X
├── HomeScreen.kt           # aktifkan tombol Pengaturan
└── ar/
    └── ArScreen.kt         # gear membuka panel; terima paused + volume
ar/
└── ArController.kt         # paused → pause sesi AR, resume tanpa scan ulang
MainActivity.kt             # overlay Pengaturan global + state volume
app/src/test/.../MusicVolumeTest.kt
```

Boundary tetap: `ui → data/audio`; `ar/` tidak menyentuh UI panel.

## Model nilai

- `MusicVolume(percent: Int)`:
  - default `100`;
  - `percent` selalu `coerceIn(0, 100)`;
  - `fraction` = `percent / 100f` untuk pemutar audio;
  - `isMuted` = `percent == 0`.
- `AudioSettingsStore`:
  - `musicVolume: MusicVolume` dibaca dari preferensi; data rusak/kosong →
    `MusicVolume()`.
  - `save(volume): Boolean` menulis dan mengembalikan berhasil/gagal.
  - Tidak menyimpan nilai sementara saat slider masih digeser; simpan saat
    `onValueChangeFinished`.

## Audio

- `NotePlayer` mendapat `fun setVolume(volume: Float)` (0..1).
- `SoundPoolNotePlayer` menyimpan `volume` dan memakai nilai itu pada
  `soundPool.play(...)`.
- Nada yang sedang berbunyi boleh lanjut; perubahan berlaku untuk nada
  berikutnya (sesuai spec).
- Volume awal diberikan saat player dibuat dari nilai tersimpan, lalu
  diperbarui lewat `LaunchedEffect(volume)`.

## Panel S7

Mengikuti `S7-pengaturan.png`:

- Overlay penuh dengan scrim gelap tipis; panel modal tengah maks 720dp.
- Plakat **Pengaturan** di atas panel.
- Baris: ikon not, label **Musik**, slider emas 0–100, persentase tabular.
- Slider Material 3 dengan warna `Primary`; langkah 1%.
- Pill “✓ Volume musik tersimpan otomatis” di bawah slider.
- Tombol tutup X di dalam panel, kanan atas (≥48dp).
- Motion masuk/keluar 180ms.
- Tidak ada baris SFX/bahasa/equalizer.

## Navigasi & state

- `settingsVisible` dan `volumePercent` di `MainActivity` (level aplikasi),
  sehingga panel bisa muncul di atas Beranda maupun AR.
- `HomeScreen(onOpenSettings)` mengaktifkan tombol **Pengaturan**.
- `ArScreen(onOpenSettings, paused, volume)` mengaktifkan ikon gear.
- `SettingsPanel` memakai state lokal saat slider digeser; nilai global
  diperbarui live agar AR di belakang ikut berubah.
- Saat slider dilepas: simpan; jika gagal → kembalikan nilai terakhir yang
  valid + pesan singkat.
- BackHandler: jika panel terbuka, tutup panel; jangan keluar layar.

## Pause AR

- `MainActivity` meneruskan `paused = settingsVisible` ke `ArScreen`.
- `ArController` menerima `paused` dan memakai lifecycle yang bisa dijeda
  untuk `ARSceneView` (bukan memanggil `session.pause()` mentah):
  - lifecycle host tetap diikuti;
  - saat `paused`, state ditahan di `STARTED`/`CREATED` sehingga SceneView
    mem-pause sesi AR;
  - saat panel ditutup, state kembali `RESUMED`; marker yang sudah dikenali
    tidak perlu dipindai ulang.
- Jika pendekatan lifecycle menimbulkan regresi, fallback: hentikan audio dan
  biarkan kamera hidup, lalu catat deviasi di converge. Jangan memanggil
  `session.pause()` langsung tanpa penanganan frame loop.

## File yang disentuh

```
app/src/main/java/com/gemaar/data/MusicVolume.kt
app/src/main/java/com/gemaar/data/AudioSettingsStore.kt
app/src/main/java/com/gemaar/audio/NotePlayer.kt
app/src/main/java/com/gemaar/audio/SoundPoolNotePlayer.kt
app/src/main/java/com/gemaar/ui/components/SettingsPanel.kt
app/src/main/java/com/gemaar/ui/HomeScreen.kt
app/src/main/java/com/gemaar/ui/ar/ArScreen.kt
app/src/main/java/com/gemaar/ar/ArController.kt
app/src/main/java/com/gemaar/MainActivity.kt
app/src/test/java/com/gemaar/data/MusicVolumeTest.kt
docs/design/SCREENS.md
docs/design/UI/exports/README.md
docs/design/UI/exports/verification/F07-*.png
docs/specs/F-07-volume/tasks.md
docs/specs/F-07-volume/converge.md
feature_list.json
progress.md
```

## Verifikasi

```bash
export JAVA_HOME=/usr/lib/jvm/temurin-25-jdk-amd64
./gradlew assembleDebug
./gradlew testDebugUnitTest
./gradlew lintDebug
./gradlew installDebug
bash init.sh --check
bash scripts/verify-docs.sh
```

Manual di Tab S9:

1. Beranda → Pengaturan → panel S7, slider di 100%.
2. Geser ke 40% → angka ikut; tutup; mainkan alat → bunyi lebih pelan.
3. Geser ke 0% → mainkan nada → tidak terdengar.
4. Geser ke 100% → bunyi penuh.
5. Tutup aplikasi, buka lagi → Pengaturan menunjukkan nilai terakhir.
6. Dari AR, tekan gear → panel muncul, AR berhenti sementara; tutup → AR
   lanjut tanpa scan ulang.
7. Ganti alat → volume sama.
8. Geser cepat 20× → tidak crash; nilai akhir tersimpan.
9. Isi preferensi dengan nilai rusak (mis. 150) → aplikasi memakai 100%.

## Risiko

- Pause AR lewat lifecycle harus mengikuti siklus host; salah kelola bisa
  membuat kamera hitam saat resume. Uji buka/tutup 5×.
- Menyimpan setiap perubahan slider bisa menulis terlalu sering; simpan saat
  slider dilepas.
- Ikon not mungkin tidak ada di set ikon inti; pakai ikon inti terdekat atau
  gambar sederhana, jangan menambah dependency ikon besar.
- Panel dari AR menutupi sebagian layar; pastikan scrim tidak menangkap tap
  slider.
- Data lama tanpa nilai volume harus tetap kompatibel (default 100).
- Jangan mengubah alur katalog, koleksi, atau kartu budaya.
