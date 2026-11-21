# SCREENS — peta layar (GemaAR)

> Menjawab: **layar apa saja, butuh data apa, fitur mana yang memilikinya.**
> Bukan spec. Perilaku detail ada di `docs/specs/F-XX/spec.md`.
>
> Referensi visual: `docs/design/UI/image*.png`. Orientasi **landscape**.

---

## Peta layar

| ID | Layar | Tujuan | Data dibutuhkan | Fitur | Status |
|---|---|---|---|---|---|
| S1 | **Beranda** | Pintu masuk ke AR, katalog, dan koleksi | ringkasan progres (x/6) | F-01, F-03, F-06 | draft |
| S2 | **Katalog** | Memilih atau mengganti alat | daftar 6 instrument (nama, asal, ikon, status dimainkan) | F-03 | draft |
| S3 | **Petunjuk** | Menjelaskan cara memakai satu marker sebelum masuk AR | langkah marker, aksi unduh marker, status izin kamera | F-01 | draft |
| S4 | **AR** | Menampilkan dan memainkan alat di atas marker | instrument aktif, model, note map, scale aktif, status marker | F-01, F-02, F-04 | draft |
| S5 | **Kartu Budaya** | Menampilkan asal & cara main tanpa keluar dari AR | teks budaya instrument | F-05 | draft |
| S6 | **Koleksi** | Menunjukkan progres alat yang sudah dimainkan | daftar instrument + status dimainkan | F-06 | draft |
| S7 | **Pengaturan** | Mengatur volume musik | nilai volume (0–100) tersimpan lokal | F-07 | draft |

**Satu layar boleh dimiliki beberapa fitur.** Itu normal.
Yang penting: setiap fitur tetap bisa didemokan sendiri.

### Pemetaan fitur → layar

| Fitur (`feature_list.json`) | Layar utama | Bukti demo |
|---|---|---|
| F-01 Scaffold + tempatkan alat | S3, S4 | alat muncul dan menempel di marker |
| F-02 Ketuk → nada | S4 | tap bilah → sample berbunyi |
| F-03 Katalog + pilih | S1, S2 | pilih alat → model di marker berganti; marker tidak discan ulang |
| F-04 Tangga nada & oktaf | S4 | ganti scale → note map berubah |
| F-05 Kartu budaya | S5 | buka panel Info dari S4; AR tetap aktif |
| F-06 Progres koleksi | S1, S6 | mainkan alat → status di S6 berubah |
| F-07 Volume musik | S7 | geser slider → volume berubah & tersimpan |

## Matriks state

Setiap layar wajib punya empat state. Isi bagaimana tampilannya:

| Layar | Loading | Empty | Error | Success |
|---|---|---|---|---|
| S1 Beranda | ilustrasi + logo fade-in | n/a | aset gagal dimuat → "Muat ulang" | tombol Mulai, Katalog, Koleksi aktif |
| S2 Katalog | skeleton 6 kartu | "Belum ada alat" | "Katalog gagal dimuat" + Coba lagi | grid 6 kartu alat |
| S3 Petunjuk | n/a | n/a | izin kamera ditolak / AR tidak didukung → pesan + buka pengaturan | 3 langkah marker + tombol Unduh/Mulai |
| S4 AR | progress memuat model di plakat | marker belum terdeteksi → hint arahkan kamera | izin kamera / ARCore tidak tersedia | model tampil di atas marker + HUD + note bar |
| S5 Kartu Budaya | teks memuat | n/a | teks gagal dimuat → Coba lagi | panel Info terbuka; AR tetap jalan |
| S6 Koleksi | skeleton grid | "Belum ada alat dimainkan" | "Koleksi gagal dimuat" + Coba lagi | 6 kartu dengan status |
| S7 Pengaturan | nilai volume tersimpan dimuat | n/a | gagal menyimpan → toast + nilai kembali | slider volume musik aktif |

Catatan S5: panel sisi kanan, body discroll bila teks panjang; teks rata kanan
sesuai DESIGN; tombol close (X) mengembalikan fokus ke AR tanpa memutus sesi.

State yang belum dirancang **akan** menjadi bug. Isi sekarang.

## Navigasi

```
S1 Beranda ──► S2 Katalog ──► S3 Petunjuk ──► S4 AR ──► S5 Kartu Budaya
    │              ▲                             │
    │              └────── ganti alat ◄──────────┘
    ├──────────────► S3 Petunjuk
    ├──────────────► S6 Koleksi ──► S2 Katalog
    ├──────────────► S7 Pengaturan ◄─── dari S1 & S4
    └──────────────► S4 AR (alat terakhir)
```

Aturan:
- Setiap layar bisa dicapai dalam ≤ 3 klik dari Beranda.
- Back selalu bekerja; keluar dari S4 AR kembali ke S1 (dengan konfirmasi bila sesi aktif).
- Tidak ada layar yang hanya bisa dicapai lewat state tersembunyi.
- S5 tampil sebagai panel sisi kanan di atas S4; AR tetap aktif dan tidak membuat
  sesi baru.
- S7 dibuka dari S1 (ikon gear) atau S4 (overlay); sesi AR di-pause selama S7
  terbuka.

## Catatan referensi UI

Referensi di `docs/design/UI/` dipakai untuk bahasa visual. Keputusan yang sudah
diambil:

1. **Alat** — katalog memakai 6 instrument `docs/GLOSSARY.md`: Gong & Kempul,
   Bonang, Saron, Gender Barung, Slenthem, Kendang. Menu di referensi
   (Bonang Penerus, Bonang Barung, Kempul Gong, Kenong, …) hanya acuan visual.
2. **Satu marker** — semua alat memakai satu marker yang sama (cetak/unduh sekali).
   Alat dipilih atau diganti dari S2; model di marker berganti tanpa scan ulang.
   Judul F-01 masih "di permukaan"; spec F-01 yang menetapkan perilaku marker.
3. **Pengaturan** — hanya volume musik (F-07). SFX dan pemilih bahasa dihapus
   dari scope; panel mengikuti `image-1.png` tanpa baris SFX/bendera.
4. **Ikon profil** di Beranda dipetakan ke S6 Koleksi (F-06), bukan profil akun.
5. **Panel Info** (S5) mengikuti `image-5.png`: sisi kanan, 34% lebar, body
   scroll, AR tetap aktif.
6. **Pengaturan** (S7) mengikuti `image-1.png`: panel modal, satu slider volume
   musik. Baris SFX dan pemilih bahasa tidak dipakai.

## Kapan layar dibuat

| Kondisi | Aksi |
|---|---|
| Layar dipakai fitur pertama | dibuat di fitur itu |
| Layar dipakai 3 fitur | dibuat sebagian di fitur pertama, dilengkapi di fitur berikutnya |
| Layar hanya untuk fitur yang belum masuk daftar | **jangan dibuat** |

## Aturan

- Satu layar = satu baris di tabel ini.
- Tambahkan layar saat fiturnya masuk `feature_list.json`, bukan sebelumnya.
- Jangan menaruh acceptance criteria di sini.
- Status: `draft` → `built` → `verified`.
