# Spec: F-05 — Kartu info budaya tiap alat

**Feature ID:** F-05
**Status:** draft
**Owner:** product + engineering

---

## Problem

Setelah F-04, enam alat bisa dimunculkan dan dimainkan. Tetapi pengguna hanya
mendapat bunyi dan bentuk 3D: tidak ada penjelasan asal alat, bahan, cara main,
atau perannya dalam gamelan. Tombol **Info** di layar AR masih nonaktif, sehingga
pengalaman “mengenal alat musik” berhenti di mencoba nada.

## Why now

F-04 melengkapi semua alat yang bisa dimainkan. Langkah berikutnya yang paling
murah dan berdampak adalah memberi konteks budaya singkat untuk alat yang sedang
aktif — tanpa keluar dari AR dan tanpa menambah alur baru yang berat.

## Referensi desain (wajib)

F-05 **tidak membuat bahasa visual baru**. Panel mengikuti aset UI yang sudah
dibuat:

| Berkas | Bagian yang dipakai |
|---|---|
| `docs/design/UI/exports/S5-kartu-budaya.png` | Acuan visual utama panel kanan: plakat **Info**, judul alat, subjudul asal, body rata kanan, tombol tutup, badge **Kartu Budaya** |
| `docs/design/SCREENS.md` | Baris S5, catatan S5 (panel kanan, body scroll, close tanpa memutus sesi), navigasi butir 5 |
| `docs/design/DESIGN.md` | `Body Info` 18/28 Nunito 700 rata kanan; `Script` 20/26 Caveat 700 untuk nama alat; panel sisi kanan 34% lebar (maks 420dp), tinggi ±80%; padding 24; radius 20; shadow lembut; motion panel 180ms ease-out |
| `docs/design/COMPONENTS.md` | Komponen `InfoPanel` (side-panel, scroll), `OrnatePanel`, `PlaqueTitle`, `IconButton` varian info/close |

### Elemen panel (dari desain S5)

| Elemen | Isi | Gaya acuan |
|---|---|---|
| Plakat atas | teks **Info** | `PlaqueTitle`, emas di plakat kayu |
| Judul panel | nama alat aktif (mis. “Bonang”) | `Script` Caveat 20/26, emas |
| Subjudul | asal alat (mis. “Jawa Tengah — gamelan Jawa”) | caption muted, rata kanan |
| Body | empat topik wajib | `Body Info` Nunito 18/28, rata kanan, dapat digulir |
| Badge bawah | **Kartu Budaya** | pill kecil, ikon pin, muted |
| Tombol tutup | X di kanan atas | `IconButton`, target sentuh ≥48dp |

### Yang sengaja tidak diambil dari gambar S5

Gambar S5 juga menampilkan chip **Pelog/Slendro** di sisi kanan. Chip itu
**tidak dibangun di F-05** karena pemilih laras sudah dihapus dari MVP pada
F-04. Panel Info hanya berisi teks budaya dan tombol tutup.

## User stories

### US1 — Membuka kartu budaya tanpa keluar AR (Priority: P1)

Pengguna sedang memainkan alat di AR, lalu membuka panel **Info** untuk membaca
konteks alat itu, dan menutupnya kembali ke AR.

- **Why this priority:** inti fitur; AR harus tetap berjalan agar pengguna bisa
  langsung kembali memainkan alat.
- **Independent test:** di AR dengan Bonang aktif, tekan tombol Info; panel
  menampilkan “Bonang”; tekan X; panel tertutup dan AR tetap aktif.

**Acceptance scenarios:**

1. **Given** layar AR aktif dan satu alat dipilih, **When** pengguna menekan
   tombol **Info**, **Then** panel **Kartu Budaya** terbuka dan menampilkan nama
   alat yang sedang aktif.
2. **Given** panel terbuka, **When** pengguna menekan tombol tutup, **Then**
   panel tertutup dan pengguna kembali ke AR tanpa memuat ulang model atau
   memindai marker.
3. **Given** panel terbuka, **When** pengguna menekan tombol kembali, **Then**
   panel tertutup lebih dulu, bukan keluar dari AR.
4. **Given** marker hilang saat panel terbuka, **Then** panel tetap terbaca dan
   aplikasi tidak berhenti.

### US2 — Membaca isi budaya yang benar dan ringkas (Priority: P1)

Pengguna membaca penjelasan singkat tentang asal, bentuk/bahan, cara main, dan
peran alat dalam gamelan.

- **Why this priority:** isi adalah nilai utama fitur; panel kosong atau isi
  yang salah lebih buruk daripada tidak ada panel.
- **Independent test:** buka kartu untuk keenam alat; setiap kartu memuat empat
  topik wajib dan tidak kosong.

**Acceptance scenarios:**

1. **Given** kartu alat terbuka, **Then** kartu memuat: (a) asal/daerah,
   (b) bentuk & bahan, (c) cara memainkan, dan (d) peran dalam gamelan.
2. **Given** kartu alat terbuka, **Then** teks memakai bahasa Indonesia yang
   mudah dipahami pelajar, bukan istilah teknis tanpa penjelasan.
3. **Given** teks lebih panjang dari tinggi panel, **When** pengguna menggulir,
   **Then** seluruh teks bisa dibaca dan tombol tutup tetap terjangkau.
4. **Given** kartu alat terbuka, **Then** tercantum kredit sumber singkat
   (nama lembaga/ensiklopedia) di bagian bawah kartu.
5. **Given** pengguna mengganti alat dari katalog, **When** panel Info dibuka
   lagi, **Then** isi kartu mengikuti alat yang baru.

### US3 — Kartu tetap tersedia saat AR berubah state (Priority: P2)

Kartu tidak bergantung pada marker, audio, atau model yang sedang dimuat.

- **Why this priority:** Info adalah konten bacaan; pengguna mungkin membukanya
  saat marker sulit terlihat.
- **Independent test:** buka Info saat marker hilang; panel tetap terbuka dan
  bisa ditutup.

**Acceptance scenarios:**

1. **Given** marker belum terdeteksi, **When** pengguna menekan Info, **Then**
   panel tetap terbuka dengan isi alat aktif.
2. **Given** audio alat gagal dimuat, **When** pengguna menekan Info, **Then**
   panel budaya tetap bisa dibuka.
3. **Given** model alat sedang dimuat ulang, **When** panel Info dibuka,
   **Then** panel tidak menampilkan layar kosong atau error.

---

## Edge cases

- Alat tidak punya data budaya → panel menampilkan pesan “Info belum tersedia
  untuk alat ini” dan tombol tutup; aplikasi tidak berhenti.
- Pengguna membuka/menutup panel berulang cepat → tidak ada panel ganda.
- Pengguna menekan Info saat panel sudah terbuka → panel tetap satu, tidak
  menumpuk.
- Teks sangat panjang pada layar pendek → body dapat digulir; judul dan tombol
  tutup tetap terlihat.
- Perangkat tanpa koneksi internet → kartu tetap terbuka (isi sudah tersedia
  bersama aplikasi).
- Rotasi layar saat panel terbuka → panel tetap terbaca dan posisi gulir tidak
  wajib dipertahankan.

## Functional requirements

- **FR-001**: Tombol **Info** di layar AR MUST aktif dan membuka panel Kartu
  Budaya.
- **FR-002**: Panel MUST menampilkan nama alat aktif sebagai judul.
- **FR-003**: Panel MUST memuat empat topik: asal/daerah, bentuk & bahan, cara
  memainkan, dan peran dalam gamelan.
- **FR-004**: Isi MUST tersedia untuk keenam alat: Saron, Bonang, Slenthem,
  Gender Barung, Gong & Kempul, dan Kendang.
- **FR-005**: Panel MUST memakai bahasa Indonesia yang dapat dipahami pelajar.
- **FR-006**: Panel MUST menampilkan kredit sumber singkat.
- **FR-007**: Panel MUST bisa ditutup dengan tombol tutup dan tombol kembali.
- **FR-008**: Membuka/menutup panel MUST NOT menghentikan audio, mengganti alat,
  atau memuat ulang model.
- **FR-009**: Panel MUST tetap bisa dibuka saat marker tidak terlihat atau audio
  gagal dimuat.
- **FR-010**: Isi MUST terbaca tanpa koneksi internet.
- **FR-011**: Teks yang melebihi tinggi panel MUST dapat digulir; tombol tutup
  MUST tetap terjangkau.
- **FR-012**: Jika data alat tidak ditemukan, panel MUST menampilkan pesan yang
  jelas, bukan panel kosong atau berhenti.

## Key entities

- **Kartu Budaya**: satu kumpulan teks budaya untuk satu alat.
  - identitas alat — alat yang memiliki kartu.
  - asal — daerah/konteks asal (mis. Jawa Tengah, gamelan Jawa).
  - bentuk & bahan — bagian alat dan bahan utamanya.
  - cara main — teknik dan alat pukul/tangan yang dipakai.
  - peran — fungsi dalam ansambel gamelan.
  - kredit sumber — nama sumber singkat yang ditampilkan.
  - rujukan — daftar sumber lengkap (lihat bagian Sumber).

## Content requirements (fakta yang wajib benar)

> Ini bukan naskah final. Naskah final boleh lebih pendek, tetapi fakta di
> bawah tidak boleh bertentangan. Semua rujukan ada di bagian **Sumber**.

### Konteks gamelan (dipakai di semua kartu)

- Gamelan adalah ansambel musik perkusi tradisional Indonesia; instrumennya
  terutama logam tempa tangan, ditambah gong, gong-kecil, drum, simbal, alat
  gesek/petik, dan suling bambu.
- Gamelan diakui UNESCO sebagai Warisan Budaya Takbenda pada 2021.
- Bukti praktik gamelan ditemukan pada relief Borobudur abad ke-8.

### Saron

- Tujuh bilah perunggu di atas rangka kayu beresonansi (`rancak`); dimainkan
  duduk di lantai dengan tabuh kayu, tangan lain menahan bilah sebelumnya.
- Memainkan melodi inti (`balungan`). Varian: saron panerus/peking, saron
  barung, dan demung — masing-masing satu oktaf berbeda.
- Relief saron satu oktaf paling awal ditemukan di Borobudur (abad ke-9).

### Bonang

- Kumpulan gong kecil berpencon (`pencon`) yang diletakkan mendatar di atas
  tali dalam rangka kayu, satu atau dua baris.
- Pencon rendah berkepala datar, pencon tinggi berkepala melengkung; dipukul
  dengan tabuh berlapis kain.
- Di Jawa Tengah ada bonang panerus (tertinggi), bonang barung (satu oktaf di
  bawah panerus; memberi isyarat), dan bonang panembung (terendah).

### Slenthem

- Metalofon keluarga gendèr; satu oktaf: enam bilah untuk laras slendro, tujuh
  bilah untuk laras pelog.
- Bilah perunggu digantung dengan tali kulit di atas tabung resonansi bambu
  dalam rangka kayu; dipukul dengan tabuh pendek, satu tangan menahan nada.
- Bersuara rendah dan lembut; memainkan `balungan`; lazim di ansambel halus
  seperti Gamelan Gadhon.

### Gender Barung

- Metalofon 10–14 bilah logam bergantung di atas resonator bambu/logam; dipukul
  dengan tabuh berdisk kayu berlapis.
- Satu oktaf berisi lima nada; pada laras pelog tujuh nada, sebagian nada
  dihilangkan mengikuti `pathet`.
- Gender barung memainkan pola lebih lambat dan kompleks, dengan garis tangan
  kanan/kiri yang bertemu pada interval `kempyung` (kuint) dan `gembyang`
  (oktaf); pola semi-improvisasi disebut `cengkok`.

### Gong & Kempul

- Gong ageng adalah gong perunggu terbesar, bernada tetap, dengan pencon pusat
  yang dipukul tabuh berlapis; garis tengah (gongan) ditandai oleh bunyinya.
- Gong ageng dianggap instrumen terpenting; “roh” gamelan dipercaya ada pada
  gong. Gong suwukan/siyem lebih kecil dan lebih tinggi untuk frasa pendek.
- Kempul adalah gong gantung kecil (diameter ±19–25 cm) berpencon; membagi
  `nongan` di antara pukulan kenong. Satu rak dapat berisi 2–10 kempul.

### Kendang

- Drum dua sisi; di Jawa sisi yang lebih besar biasanya di kanan dan bernada
  lebih rendah; dimainkan dengan tangan sambil duduk, drum diletakkan mendatar.
- Menjaga tempo (`laya`), mengubah kerapatan (`irama`), dan memberi isyarat
  peralihan (`paralihan`) serta akhir bagian (`suwuk`).
- Jenis Jawa: kendhang ageng/gendhing (terbesar), kendhang wayang, kendhang
  ciblon/batangan (pola rumit), dan kendhang ketipung (terkecil).
- Bahan umum: kayu nangka/kelapa/cempedak; kulit kerbau untuk sisi rendah dan
  kulit kambing untuk sisi tinggi.

## Success criteria

- **SC-001**: Keenam alat memiliki kartu dengan empat topik wajib; tidak ada
  kartu kosong.
- **SC-002**: Panel terbuka dalam waktu yang terasa seketika (< 1 detik) setelah
  tombol Info ditekan.
- **SC-003**: Pengguna dapat membuka dan menutup panel tanpa keluar dari AR
  pada 5 dari 5 percobaan.
- **SC-004**: Setiap fakta pada kartu dapat ditelusuri ke salah satu rujukan di
  bagian Sumber.
- **SC-005**: Pada uji baca 5 pengguna, minimal 4 dapat menyebutkan cara main
  alat setelah membaca kartu sekali.
- **SC-006**: Tidak ada crash saat panel dibuka berulang, saat marker hilang,
  atau saat audio gagal dimuat.
- **SC-007**: Panel saat dibuka cocok dengan acuan S5 pada empat hal yang bisa
  diamati: posisi kanan, judul alat, body rata kanan, dan tombol tutup di kanan
  atas; tanpa chip Pelog/Slendro.

## Non-goals

- Memutar audio dari dalam kartu atau menautkan ke video.
- Galeri foto, ilustrasi baru, atau model 3D tambahan di dalam panel.
- Sejarah panjang, silsilah, atau analisis musikologis mendalam.
- Pemilih laras Pelog/Slendro atau oktaf (sudah dihapus di F-04).
- Pencarian, bookmark, atau berbagi kartu.
- Mengubah isi kartu dari dalam aplikasi.
- Multi-bahasa; F-05 hanya bahasa Indonesia.
- Kuis, skor, atau gamifikasi.
- Kredit sumber berupa tautan yang bisa diklik.

## Assumptions

- Naskah budaya disimpan bersama aplikasi, bukan diambil dari jaringan saat
  dibuka.
- Satu kartu cukup 2–4 paragraf pendek; jika lebih, body digulir.
- Nama alat, asal, dan istilah mengikuti `docs/GLOSSARY.md`.
- Kredit sumber cukup nama lembaga/ensiklopedia; daftar URL lengkap hidup di
  dokumen ini.
- Tombol Info boleh dipakai kapan saja selama layar AR aktif, termasuk saat
  marker belum terdeteksi.
- Panel mengikuti persis `docs/design/UI/exports/S5-kartu-budaya.png`: sisi
  kanan, AR tetap terlihat, judul alat bergaya script, body rata kanan, tombol
  tutup di kanan atas.
- Chip Pelog/Slendro pada gambar S5 tidak dibangun (pemilih laras dihapus di
  F-04).

## Sumber (diakses 2026-11-25)

Rujukan utama:

1. **UNESCO Intangible Cultural Heritage — Gamelan (Ref. 01607)**
   https://ich.unesco.org/en/RL/gamelan-01607
   Dipakai untuk: definisi gamelan, bahan logam tempa, aturan tuning/layout,
   konteks ritual/festival, candi Borobudur abad ke-8, inskripsi 2021.
2. **Wikipedia — Gamelan**
   https://en.wikipedia.org/wiki/Gamelan
   Dipakai untuk: konteks umum, istilah `balungan`, `pathet`, `irama`, dan
   pengakuan nasional 2014.
3. **Wikipedia — Saron (instrument)**
   https://en.wikipedia.org/wiki/Saron_(instrument)
   Rujukan di dalamnya: Hood (1977); Sorrell (1990).
4. **Wikipedia — Bonang**
   https://en.wikipedia.org/wiki/Bonang
   Rujukan di dalamnya: Spiller, *Focus: Gamelan Music of Indonesia* (2010).
5. **Wikipedia — Slenthem**
   https://en.wikipedia.org/wiki/Slenthem
   Rujukan di dalamnya: Hood & Maceda (1972); Gaetano (1978); Kartomi (1990).
6. **Wikipedia — Gendèr**
   https://en.wikipedia.org/wiki/Gend%C3%A8r
   Rujukan di dalamnya: Sumarsam (1975); Kartomi, Ornstein & Montagu (2015);
   Miller & Williams (2017).
7. **Wikipedia — Gong ageng**
   https://en.wikipedia.org/wiki/Gong_ageng
   Rujukan di dalamnya: Lindsay, *Javanese Gamelan* (1992); Sutton (1991);
   Hood (1980).
8. **Wikipedia — Kempul**
   https://en.wikipedia.org/wiki/Kempul
   Rujukan di dalamnya: Sutton (1991); Da Costa, *Virtual Instrument Museum*;
   Megill.
9. **Wikipedia — Kendang**
   https://en.wikipedia.org/wiki/Kendang
   Rujukan di dalamnya: Kartomi/Heins/Ornstein, *Grove Music Online* (2001);
   Lindsay (1992); Sunarto (2015).

Catatan: Wikipedia dipakai sebagai ringkasan sekunder. Fakta yang masuk kartu
harus konsisten dengan rujukan primer yang tercantum di artikel tersebut dan
dengan deskripsi resmi UNESCO. Jika terjadi pertentangan, deskripsi UNESCO dan
buku rujukan primer yang menang.

## Open questions

- Tidak ada pertanyaan yang menghalangi implementasi. Dua keputusan produk yang
  sudah diambil sebagai asumsi:
  1. Kredit sumber ditampilkan sebagai teks singkat, bukan tautan.
  2. Kartu memakai empat topik wajib, bukan satu paragraf bebas.

## Checklist

- [x] Tidak ada nama teknologi/library
- [x] Setiap acceptance scenario bisa diamati dari luar
- [x] Setiap FR punya cara verifikasi
- [x] Tidak ada `[NEEDS CLARIFICATION]` tersisa
- [x] Non-goals eksplisit
- [x] Tidak ada fitur spekulatif
- [x] Sumber daring tercatat dan relevan
