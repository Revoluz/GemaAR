# BUG-001 — Model AR hilang/berkedip di Galaxy A25

**Status:** Open / deferred (fix nanti)
**Severity:** Medium — device-specific; device target Tab S9 stabil
**Ditemukan:** 2026-11-25
**Device:** Samsung Galaxy A25 5G (`SM-A256E`, `RRCX207JYBA`)
**Fitur:** F-03 katalog/model swap (AR display)

## Gejala

Di A25, model tiga dimensi **hilang beberapa frame lalu muncul lagi** saat
marker masih terdeteksi. Status di layar tetap **“Marker terdeteksi”**, jadi
bukan `MarkerLost`.

Tab S9 (device target) tidak mengalami flicker.

## Cara mereproduksi

1. Install APK debug di A25.
2. Buka AR → arahkan kamera ke marker 21 cm.
3. Pilih Bonang (atau Gender/Gong).
4. Dekatkan HP ke marker sampai model memenuhi layar.
5. Rekam layar: model terlihat di beberapa frame, hilang di frame lain.

## Bukti

Di `evidence/`:

- `BUG-001-A25-model-visible.jpg` — model terlihat, status “Marker terdeteksi”.
- `BUG-001-A25-model-missing.jpg` — frame berikutnya model hilang, status masih
  “Marker terdeteksi”.
- `BUG-001-A25-scale-03-stable.jpg` — model stabil saat skala diturunkan 0,3 m.

Log A25:

```
GemaArCamera: camera=0 size=4080x3060 fps=[30,30] facing=BACK   (sebelum mitigasi)
GemaArCamera: camera=0 size=1920x1080 fps=[30,30] facing=BACK   (setelah mitigasi)
GemaArMarker: visible=true
```

## Analisis

- Kamera yang dipakai **bukan salah lensa**: A25 memakai kamera belakang utama
  `cameraId=0`.
- Default SceneView memilih **resolusi kamera tertinggi** (4080×3060). A25
  kewalahan dengan stream sebesar itu.
- Model diskalakan 0,5 m. Di A25 yang dipegang dekat marker, model masuk/keluar
  frustum/near-plane kamera saat pose sedikit bergerak. Tab S9 punya layar dan
  jarak pandang berbeda sehingga stabil.
- A25 juga memakai layar OLED dengan PWM ~120 Hz; pada brightness rendah bisa
  menambah kesan berkedip. Ini bukan penyebab utama karena rekaman layar juga
  menunjukkan model hilang.

## Mitigasi yang sudah diterapkan

- Kamera dibatasi ke **1920×1080, 30 FPS, kamera belakang**.
- Grace period **500 ms** sebelum model dianggap hilang saat ARCore melaporkan
  `LAST_KNOWN_POSE`.
- Eksperimen skala 0,3 m stabil di A25, tetapi produk memilih **0,5 m seragam**
  untuk tablet dan HP; perbaikan ukuran ditunda bersama bug ini.
- Katalog dibuat compact untuk layar HP landscape.

Rekaman 0,3 m menunjukkan model stabil, tetapi keputusan produk: ukuran model
tetap 0,5 m di semua device. Dengan 0,5 m, flicker A25 masih terjadi dan
diperbaiki nanti.

## Rencana perbaikan berikutnya

1. Minta pengguna menguji build adaptif di A25; konfirmasi flicker hilang.
2. Jika masih:
   - uji hanya Saron vs hanya Gender (model-specific?);
   - uji brightness 100% (PWM layar);
   - coba kamera 1280×720;
   - periksa z-fighting/double-sided pada model;
   - pertimbangkan skala 0,25 khusus A25.
3. Jangan mengorbankan Tab S9: device target tetap 0,5 m.

## Dampak ke acceptance

F-03 tetap **PASS** untuk device target Tab S9. BUG-001 dicatat sebagai
keterbatasan device tambahan, bukan blocker F-03.
