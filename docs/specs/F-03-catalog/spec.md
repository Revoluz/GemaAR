# Spec: F-03 — Katalog alat musik + pilih dari daftar

**Feature ID:** F-03
**Status:** draft
**Owner:** product + engineering

---

## Problem

Setelah F-02, pengguna hanya bisa memakai Saron. Model lima alat lain sudah
tersedia, tetapi tidak bisa dijangkau dari layar AR. Pengguna belum bisa memilih
alat sesuai minat, dan aplikasi belum terasa seperti katalog gamelan.

## Why now

Katalog adalah pintu masuk ke enam alat. Tanpa katalog, F-04 (audio semua alat)
tidak punya cara memilih alat, dan pengalaman “mengenal alat musik” berhenti di
satu instrumen.

## User stories

### US1 — Membuka dan menutup katalog (Priority: P1)

Pengguna membuka katalog dari layar AR, melihat enam alat, lalu menutupnya
kembali ke AR tanpa kehilangan sesi.

- **Why this priority:** katalog harus bisa dibuka tanpa mengganggu marker.
- **Independent test:** di AR, tekan tombol Katalog; overlay muncul; tekan X;
  AR kembali seperti semula.

**Acceptance scenarios:**

1. **Given** layar AR aktif, **When** pengguna menekan tombol **Katalog**,
   **Then** overlay **Katalog** muncul dengan enam kartu alat.
2. **Given** katalog terbuka, **When** pengguna menutupnya, **Then** pengguna
   kembali ke AR dan alat yang aktif tidak berubah.
3. **Given** katalog terbuka, **When** pengguna menekan tombol kembali,
   **Then** katalog tertutup lebih dulu, bukan keluar dari AR.
4. **Given** marker sedang tidak terlihat, **When** pengguna membuka katalog,
   **Then** katalog tetap bisa dibuka dan ditutup tanpa crash.

### US2 — Memilih alat (Priority: P1)

Pengguna memilih alat dari katalog dan model di marker langsung berganti tanpa
memindai marker lagi.

- **Why this priority:** ini tujuan utama F-03.
- **Independent test:** pilih Bonang; model Bonang muncul di marker yang sama.

**Acceptance scenarios:**

1. **Given** katalog terbuka dan Saron aktif, **When** pengguna memilih
   **Bonang**, **Then** katalog tertutup dan model Bonang tampil di marker.
2. **Given** model alat baru sedang dimuat, **Then** muncul indikator memuat,
   bukan layar kosong.
3. **Given** marker hilang saat alat dipilih, **When** marker kembali terlihat,
   **Then** model alat baru muncul di marker.
4. **Given** pengguna memilih alat yang sama, **When** kartu ditekan,
   **Then** katalog tertutup tanpa memuat ulang model.
5. **Given** model alat gagal dimuat, **Then** muncul pesan + tombol coba lagi;
   aplikasi tidak berhenti.
6. **Given** alat baru dipilih, **Then** judul di layar AR berubah sesuai nama
   alat.

### US3 — Mengingat alat terakhir dan first run (Priority: P2)

Pilihan alat diingat, dan pemakaian pertama mengarahkan pengguna ke katalog
sekali.

- **Why this priority:** mengurangi langkah berulang dan memperkenalkan pilihan.
- **Independent test:** pilih Bonang, tutup aplikasi, buka lagi, tekan Mulai;
  Bonang langsung aktif.

**Acceptance scenarios:**

1. **Given** pengguna memilih Bonang, **When** aplikasi ditutup dan dibuka lagi,
   **Then** Mulai membuka AR dengan Bonang aktif.
2. **Given** aplikasi baru dipasang, **When** pengguna menutup Petunjuk pertama
   kali, **Then** katalog terbuka otomatis satu kali.
3. **Given** katalog first run sudah pernah tampil, **When** pengguna membuka AR
   lagi, **Then** katalog tidak terbuka otomatis.
4. **Given** alat terakhir tidak dikenal, **Then** aplikasi memakai Saron.

### US4 — Alat tanpa audio (Priority: P2)

Pengguna tetap bisa melihat model alat lain, tetapi diberi tahu bahwa audio
alat itu belum tersedia.

- **Why this priority:** F-03 hanya mengganti model; audio alat lain masuk F-04.
- **Independent test:** pilih Bonang; model tampil dan muncul pesan audio.

**Acceptance scenarios:**

1. **Given** Bonang aktif, **When** model tampil, **Then** baris nada
   disembunyikan dan muncul pesan **“Audio belum tersedia untuk alat ini”**.
2. **Given** Bonang aktif, **When** pengguna mengetuk model, **Then** tidak ada
   suara Saron yang diputar.
3. **Given** Saron sedang berbunyi, **When** pengguna memilih alat lain,
   **Then** suara Saron berhenti.
4. **Given** pengguna kembali memilih Saron, **When** model siap,
   **Then** baris nada dan audio Saron berfungsi lagi.

---

## Edge cases

- Katalog dibuka saat marker hilang → tetap terbuka; alat bisa dipilih; model
  muncul saat marker kembali.
- Memilih alat saat model lama masih dimuat → pilihan terbaru menang; tidak ada
  dua model di marker.
- Alat terakhir tidak ada di katalog → fallback ke Saron.
- Model alat gagal dimuat → pesan + coba lagi; katalog masih bisa dibuka.
- Katalog dan Petunjuk tidak boleh tampil bersamaan.
- Keluar dari AR saat katalog terbuka → sesi berhenti seperti biasa.
- Badge “Sudah/Belum dimainkan” tidak ditampilkan di F-03.
- Tombol **Alat Musik** di Beranda tetap nonaktif sampai F-06.

## Functional requirements

- **FR-001**: Katalog MUST menampilkan enam alat: Gong & Kempul, Bonang, Saron,
  Gender Barung, Slenthem, Kendang.
- **FR-002**: Setiap kartu MUST menampilkan nama, asal, dan ikon alat.
- **FR-003**: Alat yang sedang aktif MUST ditandai di katalog.
- **FR-004**: Membuka/menutup katalog MUST NOT membuat ulang sesi AR.
- **FR-005**: Memilih alat MUST mengganti model di marker tanpa memindai ulang.
- **FR-006**: Setelah memilih alat, katalog MUST tertutup otomatis.
- **FR-007**: Judul di layar AR MUST mengikuti nama alat aktif.
- **FR-008**: Pilihan alat MUST disimpan dan dipakai lagi saat aplikasi dibuka.
- **FR-009**: First run MUST membuka katalog otomatis satu kali setelah Petunjuk.
- **FR-010**: Alat tanpa audio MUST menampilkan pesan “Audio belum tersedia”
  dan MUST NOT membunyikan suara Saron.
- **FR-011**: Berpindah dari Saron MUST menghentikan nada Saron yang sedang
  berbunyi.
- **FR-013**: Model yang gagal dimuat MUST menampilkan pesan + coba lagi.
- **FR-014**: F-03 MUST NOT menambah audio alat lain, badge progres, layar
  Alat Musik, panel Info, pengaturan volume, atau pemilih tangga nada/oktaf.
- **FR-015**: Back MUST menutup katalog sebelum aksi lain.

## Key entities

- **Instrument**: alat dengan id, nama, asal, model, ikon, dan status audio.
- **Catalog**: daftar enam Instrument.
- **Selection**: Instrument yang sedang aktif dan disimpan.
- **First-run catalog**: penanda bahwa katalog otomatis sudah pernah tampil.

## Success criteria

- **SC-001**: Enam kartu tampil dan alat aktif ditandai.
- **SC-002**: Memilih alat mengganti model tanpa memindai ulang marker.
- **SC-003**: Sesi AR tidak dibuat ulang saat katalog dibuka/ditutup.
- **SC-004**: Alat terakhir dipulihkan setelah aplikasi dibuka lagi.
- **SC-005**: Katalog first run terbuka tepat satu kali.
- **SC-006**: Alat non-Saron menampilkan pesan audio dan tidak memutar Saron.
- **SC-007**: Model alat gagal dimuat tidak membuat aplikasi berhenti.
- **SC-008**: Setelah kembali ke Saron, audio Saron berfungsi lagi.

## Non-goals

- Audio Bonang, Gender, Slenthem, Gong & Kempul, Kendang (F-04).
- Badge “Sudah/Belum dimainkan” dan layar Alat Musik (F-06).
- Kartu budaya (F-05).
- Volume musik (F-07).
- Pemilih Pelog/Slendro dan oktaf (dihapus dari MVP).
- Marker berbeda per alat.
- Mengubah data katalog dari dalam aplikasi.

## Assumptions

- Enam model tiga dimensi sudah tersedia.
- Data katalog ditulis langsung di aplikasi dan selalu berisi enam alat; tidak ada
  server, JSON, proses memuat, atau state kosong/error katalog.
- Orientasi landscape.
- Audio hanya Saron pada F-03.
- Pengguna memakai satu marker yang sama untuk semua alat.

## Open questions

- Tidak ada.

---

## Checklist

- [x] Tidak ada nama teknologi/library
- [x] Setiap acceptance scenario bisa diamati dari luar
- [x] Setiap FR punya cara verifikasi
- [x] Tidak ada `[NEEDS CLARIFICATION]` tersisa
- [x] Non-goals eksplisit
- [x] Tidak ada fitur spekulatif
