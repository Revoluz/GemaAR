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

## Marker cetak

| File | Isi |
|---|---|
| `marker-A4-21cm.pdf` | marker A4, lebar area 21 cm, siap cetak 100% |
| `marker-A4-21cm.png` | preview/alternatif tampil di layar |

## Verifikasi

| File | Isi |
|---|---|
| `verification/T001-S1-implemented.png` | S1 Beranda hasil implementasi di Tab S9 |
| `verification/T001-mulai-AR.png` | hasil tap Mulai → AR |
| `verification/T001a-saron-tanpa-tabuh.png` | model Saron F-02 tanpa tabuh |
| `verification/T002-petunjuk-overlay.png` | S3 Petunjuk tampil otomatis pertama kali |
| `verification/T002-petunjuk-dibuka-lagi.png` | “?” membuka Petunjuk dari AR |
| `verification/T003-unduh-marker-share.png` | share sheet PNG marker |
| `verification/T004-ar-scanning.png` | S4 AR, hint + status “Mencari marker” |
| `verification/T004-konfirmasi-keluar-ar.png` | konfirmasi keluar dari AR |
| `verification/T005-izin-kamera-ditolak.png` | error izin kamera + Buka Pengaturan |
| `verification/T005-buka-pengaturan.png` | tombol membuka Info aplikasi |
| `verification/T005-arcore-tidak-tersedia.png` | gate ARCore di emulator tanpa ARCore |
| `verification/T006-marker-terdeteksi.png` | Saron tampil di marker |
| `verification/T007-marker-hilang.png` | Saron hilang + hint + status “Marker hilang” |
| `verification/T007-log-siklus.txt` | log 4 siklus deteksi/hilang |
| `verification/T006-note-bar.png` | note bar 1–7 F-02 |
| `verification/T007-note-feedback.png` | tombol 4 menyala + label “Nada 4” |
| `verification/T008-saron-tanpa-tabuh-ar.png` | Saron tanpa tabuh di AR |
| `verification/T008-marker-hilang-notebar.png` | marker hilang → note bar hilang |
| `verification/T008-audio-play-log.txt` | log nada 1/4/7 dan tap beruntun |

## Catatan implementasi

- **S3 Petunjuk:** kartu marker di Pen masih kosong. Di aplikasi, tampilkan
  `app/src/main/assets/markers/marker_placeholder.png` (atau marker final).
- **S4 AR:** frame Pen memakai Bonang sebagai contoh. F-01 memakai **Saron**;
  NoteBar sudah ada di F-02. Pemilih Pelog/Slendro dan oktaf **dihapus dari
  MVP**; F-04 berfokus pada audio semua alat selain Saron.
- **S6 Alat Musik:** badge “Sudah/Belum dimainkan” adalah bagian dari desain.
- **S2/S3** adalah overlay di atas S4, bukan layar penuh terpisah.
- Jangan mengubah layout, warna, font, atau ukuran dari export tanpa update
  `docs/design/*` dan screenshot baru.
