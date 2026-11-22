# SCREENS — peta layar (GemaAR)

> Menjawab: **layar apa saja, butuh data apa, fitur mana yang memilikinya.**
> Bukan spec. Perilaku detail ada di `docs/specs/F-XX/spec.md`.
>
> Referensi visual: `docs/design/UI/image*.png`. Orientasi **landscape**.
> **Export terbaru:** `docs/design/UI/exports/` — implementasi UI wajib mengikuti
> file PNG di folder itu.
> **S2, S3, S5, S7 adalah overlay di atas S4**, bukan layar penuh terpisah.

---

## Peta layar

| ID | Layar | Bentuk | Tujuan | Data dibutuhkan | Fitur | Status |
|---|---|---|---|---|---|---|
| S1 | **Beranda** | layar penuh | Pintu masuk: Mulai, Alat Musik, Pengaturan | alat terakhir (opsional) | F-01, F-06, F-07 | draft |
| S2 | **Katalog** | overlay di S4 | Memilih / mengganti alat saat AR | daftar 6 instrument (nama, ikon) | F-03 | draft |
| S3 | **Petunjuk** | overlay di S4 | Menjelaskan cara memakai satu marker | langkah marker, aksi unduh/cetak, status izin kamera | F-01 | draft |
| S4 | **AR** | layar penuh | Menampilkan & memainkan alat di atas marker | instrument aktif, model, kontrol nada per alat, status marker | F-01, F-02, F-04 | draft |
| S5 | **Kartu Budaya** | panel kanan di S4 | Info asal & cara main tanpa keluar AR | teks budaya instrument | F-05 | draft |
| S6 | **Alat Musik** | layar penuh | Melihat daftar 6 alat + status sudah/belum dimainkan | daftar instrument + status dimainkan | F-06 | draft |
| S7 | **Pengaturan** | overlay / modal | Mengatur volume musik | nilai volume (0–100) tersimpan lokal | F-07 | draft |

**Satu layar boleh dimiliki beberapa fitur.** Itu normal.
Yang penting: setiap fitur tetap bisa didemokan sendiri.

### Pemetaan fitur → layar

| Fitur (`feature_list.json`) | Layar utama | Bukti demo |
|---|---|---|
| F-01 Tempatkan alat di marker | S3, S4 | alat muncul dan menempel di marker |
| F-02 Ketuk → nada | S4 | tap bilah → sample berbunyi |
| F-03 Katalog + pilih | S2 | pilih alat di overlay AR → model di marker berganti |
| F-04 Audio semua alat selain Saron | S4 | pilih Bonang/Gender/Slenthem/Gong/Kendang → kontrol dan audio alat itu aktif |
| F-05 Kartu budaya | S5 | buka panel Info dari S4; AR tetap aktif |
| F-06 Progres koleksi | S6 | mainkan alat → badge di S6 berubah |
| F-07 Volume musik | S7 | geser slider → volume berubah & tersimpan |

## Matriks state

Setiap layar wajib punya empat state. Isi bagaimana tampilannya:

| Layar | Loading | Empty | Error | Success |
|---|---|---|---|---|
| S1 Beranda | ilustrasi + logo fade-in | n/a | aset gagal dimuat → "Muat ulang" | tombol Mulai, Alat Musik, Pengaturan |
| S2 Katalog | n/a (data statis) | n/a (data statis) | n/a (data statis) | grid 6 kartu, satu selected |
| S3 Petunjuk | n/a | n/a | izin kamera ditolak / AR tidak didukung → pesan + buka pengaturan | 3 langkah marker + tombol Mulai AR |
| S4 AR | progress memuat model di plakat | marker belum terdeteksi → hint arahkan kamera | izin kamera / ARCore tidak tersedia | model tampil di atas marker + HUD + kontrol nada/pad |
| S5 Kartu Budaya | teks memuat | n/a | teks gagal dimuat → Coba lagi | panel Info terbuka; AR tetap jalan |
| S6 Alat Musik | skeleton grid | "Belum ada alat" | "Daftar alat gagal dimuat" + Coba lagi | grid 6 kartu + status sudah/belum dimainkan |
| S7 Pengaturan | nilai volume tersimpan dimuat | n/a | gagal menyimpan → toast + nilai kembali | slider volume musik aktif |

Catatan S5: panel sisi kanan, body discroll bila teks panjang; teks rata kanan
sesuai DESIGN; tombol close (X) mengembalikan fokus ke AR tanpa memutus sesi.

State yang belum dirancang **akan** menjadi bug. Isi sekarang.

## Navigasi

```
S1 Beranda
├── Mulai ──────────► S4 AR
│                      ├── S3 Petunjuk (overlay; otomatis saat pertama kali)
│                      ├── S2 Katalog (overlay; tombol katalog)
│                      ├── S5 Info (panel kanan)
│                      └── S7 Pengaturan (overlay; AR pause)
├── Alat Musik ─────► S6 Alat Musik (daftar + status; tidak masuk AR)
└── Pengaturan ─────► S7
```

### Alur utama

1. **Pertama kali:** S1 → Mulai → S4 AR → S3 Petunjuk muncul otomatis →
   tombol **Mulai AR** → S2 Katalog → pilih alat (mis. Bonang) → model muncul
   saat marker terdeteksi.
2. **Berikutnya:** S1 → Mulai → S4 AR dengan alat terakhir. Petunjuk dibuka
   lewat tombol **“?”**.
3. **Ganti alat:** di S4 tekan tombol **Katalog** → S2 overlay → pilih alat →
   model di marker **berganti**, marker tidak discan ulang.
4. **Dari Beranda:** Alat Musik → S6 Alat Musik, hanya melihat daftar + status
   sudah/belum dimainkan. Tidak ada tombol/navigasi ke AR dari halaman ini.
5. **Info:** di S4 tekan tombol Info → S5 panel → close → kembali ke AR.
6. **Pengaturan:** dari S1 atau ikon gear di S4 → S7 → atur volume musik.

Aturan:
- Setiap layar bisa dicapai dalam ≤ 3 klik dari Beranda.
- Back selalu bekerja; keluar dari S4 AR kembali ke S1 (dengan konfirmasi bila sesi aktif).
- Tidak ada layar yang hanya bisa dicapai lewat state tersembunyi.
- S2, S3, S5 tampil di atas S4; sesi AR tidak dibuat ulang.
- S7 membuka overlay; jika dibuka dari S4, sesi AR **di-pause**.
- S6 menampilkan status **Sudah dimainkan / Belum dimainkan**; status dianggap
  “sudah” setelah minimal satu nada dimainkan (detail di spec F-06).
- S6 **tidak** memilih alat dan **tidak** membuka AR. Pemilihan/ganti alat hanya
  lewat S2 overlay di S4.
- Status dimainkan disimpan lokal (DataStore), bukan server.

## Catatan referensi UI

Referensi di `docs/design/UI/` dipakai untuk bahasa visual. Keputusan yang sudah
diambil:

1. **Alat** — 6 instrument `docs/GLOSSARY.md`: Gong & Kempul, Bonang, Saron,
   Gender Barung, Slenthem, Kendang. Menu di referensi hanya acuan visual.
2. **Satu marker** — semua alat memakai satu marker yang sama (cetak/unduh sekali).
   Alat dipilih/diganti dari S2; model di marker berganti tanpa scan ulang.
3. **Pengaturan** — hanya volume musik (F-07). SFX dan pemilih bahasa dihapus
   dari scope; panel mengikuti `image-1.png` tanpa baris SFX/bendera.
4. **Ikon profil** di Beranda dipetakan ke **S6 Alat Musik** (F-06), bukan
   profil akun.
5. **Panel Info** (S5) mengikuti `image-5.png`: sisi kanan, 34% lebar, body
   scroll, AR tetap aktif.
6. **Pengaturan** (S7) mengikuti `image-1.png`: panel modal, satu slider volume
   musik. Baris SFX dan pemilih bahasa tidak dipakai.
7. **Tangga nada/oktaf** — pemilih Pelog/Slendro dan pindah oktaf dihapus dari
   MVP. Tiap alat memakai satu tangga nada default; chip di desain tidak
   ditampilkan sampai fitur pasca-MVP.
7. **Alat Musik** — S6 hanya menampilkan daftar alat + status “sudah/belum
   dimainkan”. Tidak ada navigasi ke AR dan tidak ada layar progres terpisah.

## Kapan layar dibuat

| Layar | Dibuat di fitur |
|---|---|
| S1 Beranda | F-01 |
| S3 Petunjuk | F-01 |
| S4 AR | F-01/F-02/F-04 |
| S2 Katalog | F-03 |
| S6 Alat Musik | F-06 |
| S5 Kartu Budaya | F-05 |
| S7 Pengaturan | F-07 |

## Aturan

- Satu layar = satu baris di tabel ini.
- Tambahkan layar saat fiturnya masuk `feature_list.json`, bukan sebelumnya.
- Jangan menaruh acceptance criteria di sini.
- Status: `draft` → `built` → `verified`.
