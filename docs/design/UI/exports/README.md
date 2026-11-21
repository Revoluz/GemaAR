# Pen.dev Exports — GemaAR

Hasil export dari `docs/design/UI/ui.pen` (frame 1912×862, scale 1x).

> **Ini referensi visual.** Implementasi Android tetap Compose; jangan memakai
> screenshot layar sebagai background penuh. Gunakan aset bersih di `assets/`.

## Layar

| File | Frame Pen | Dipakai di |
|---|---|---|
| `S1-beranda.png` | S1 Beranda | F-01 (Mulai) |
| `S2-katalog.png` | S2 Katalog | F-03 |
| `S3-petunjuk.png` | S3 Petunjuk | F-01 |
| `S4-ar.png` | S4 AR | F-01/F-02/F-04 |
| `S5-kartu-budaya.png` | S5 Kartu Budaya | F-05 |
| `S6-alat-musik.png` | S6 Alat Musik | F-03/F-06 |
| `S7-pengaturan.png` | S7 Pengaturan | F-07 |
| `states-S1-S7.png` | Papan State S1–S7 | semua state |

## Aset bersih

| File | Isi |
|---|---|
| `assets/ilustrasi-latar.png` | background ilustrasi tanpa teks/tombol |
| `assets/alat-gong-kempul.png` | ilustrasi alat |
| `assets/alat-bonang.png` | ilustrasi alat |
| `assets/alat-saron.png` | ilustrasi alat |
| `assets/alat-gender-barung.png` | ilustrasi alat |
| `assets/alat-slenthem.png` | ilustrasi alat |
| `assets/alat-kendang.png` | ilustrasi alat |

## Catatan implementasi

- **S3 Petunjuk:** kartu marker di Pen masih kosong. Di aplikasi, tampilkan
  `app/src/main/assets/markers/marker_placeholder.png` (atau marker final).
- **S4 AR:** frame Pen memakai Bonang sebagai contoh. F-01 memakai **Saron**;
  NoteBar dan pemilih Pelog/Slendro ditunda ke F-02/F-04.
- **S6 Alat Musik:** badge “Sudah/Belum dimainkan” adalah bagian dari desain.
- **S2/S3** adalah overlay di atas S4, bukan layar penuh terpisah.
- Jangan mengubah layout, warna, font, atau ukuran dari export tanpa update
  `docs/design/*` dan screenshot baru.
