# Spec: F-06 — Progres koleksi alat yang sudah dimainkan

**Feature ID:** F-06
**Status:** approved
**Owner:** product + engineering

---

## Referensi desain (wajib)

F-06 memakai layar S6 yang sudah dibuat. Tidak ada bahasa visual baru.

| Berkas | Bagian yang dipakai |
|---|---|
| `docs/design/UI/exports/S6-alat-musik.png` | Acuan visual utama: latar ilustrasi, panel tengah, plakat **Alat Musik**, subjudul, grid 3×2, badge status. Tombol panah kembali pada export diganti tombol tutup X di dalam panel sesuai keputusan produk |
| `docs/design/SCREENS.md` | Baris S6, alur Beranda → Alat Musik, aturan S6 (read-only, status “sudah” setelah minimal satu nada, penyimpanan lokal) |
| `docs/design/DESIGN.md` | Panel maks 720dp, token warna `Success`/`Background`/`Surface`, font script Caveat untuk nama alat, caption untuk asal, target sentuh 48dp |
| `docs/design/COMPONENTS.md` | `InstrumentCard` varian played/belum, `ProgressBadge`, `OrnatePanel`, `PlaqueTitle`, `IconButton` tutup |

### Elemen layar (dari S6)

| Elemen | Isi | Gaya acuan |
|---|---|---|
| Latar | ilustrasi gamelan yang sama dengan Beranda | `ilustrasi-latar` |
| Tombol tutup | X di pojok kanan atas **di dalam panel** | `IconButton`, target ≥48dp |
| Panel | panel gelap tengah, lebar maks 720dp | `OrnatePanel`, radius 20 |
| Plakat | teks **Alat Musik** di atas panel | `PlaqueTitle` |
| Subjudul | “Enam alat gamelan — badge hijau berarti sudah kamu mainkan di AR” | caption muted |
| Kartu | ikon alat di bidang krem, nama script, “Jawa”, badge | 3 kolom × 2 baris |
| Badge sudah | pill hijau + centang, teks **Sudah dimainkan** | `Success` |
| Badge belum | pill terang + ikon putar, teks **Belum dimainkan** | `Background` |

Kartu **tidak** bisa ditekan untuk memilih alat atau masuk AR.

## Problem

Pengguna bisa memainkan enam alat, tetapi aplikasi tidak mengingat alat mana
yang sudah dicoba. Tombol **Alat Musik** di Beranda masih nonaktif, sehingga
tidak ada gambaran koleksi yang sudah dimainkan dan tidak ada alasan untuk
mencoba alat lain.

## Why now

F-04 membuat semua alat bisa dimainkan dan F-05 memberi konteks budaya. Setelah
itu, progres koleksi adalah langkah termurah untuk mendorong pengguna mencoba
keenam alat sebelum masuk ke fitur pengaturan volume (F-07).

## User stories

### US1 — Membuka daftar koleksi dari Beranda (Priority: P1)

Pengguna membuka **Alat Musik** dari Beranda, melihat enam alat beserta status
sudah/belum dimainkan, lalu kembali ke Beranda.

- **Why this priority:** tanpa layar ini, progres tidak bisa dilihat.
- **Independent test:** dari Beranda tekan Alat Musik; layar S6 muncul dengan
  enam kartu; tekan tombol tutup; Beranda muncul lagi.

**Acceptance scenarios:**

1. **Given** Beranda aktif, **When** pengguna menekan **Alat Musik**,
   **Then** layar **Alat Musik** terbuka dengan enam kartu alat.
2. **Given** layar Alat Musik terbuka, **When** pengguna menekan tombol
   tutup, **Then** pengguna kembali ke Beranda.
3. **Given** layar Alat Musik terbuka, **When** pengguna menekan tombol
   kembali sistem, **Then** pengguna kembali ke Beranda, bukan keluar aplikasi.
4. **Given** layar Alat Musik terbuka, **Then** tidak ada cara memilih alat
   atau membuka AR dari layar itu.

### US2 — Mencatat alat yang sudah dimainkan (Priority: P1)

Setelah pengguna memainkan minimal satu nada/pukulan di AR, alat itu ditandai
sudah dimainkan dan statusnya tersimpan.

- **Why this priority:** inti fitur; badge harus benar.
- **Independent test:** mainkan satu nada Saron, keluar AR, buka Alat Musik;
  kartu Saron berstatus “Sudah dimainkan”.

**Acceptance scenarios:**

1. **Given** pengguna memainkan satu nada pada alat aktif di AR, **Then** alat
   itu ditandai sudah dimainkan.
2. **Given** satu alat sudah ditandai, **When** pengguna membuka Alat Musik,
   **Then** kartu alat itu menampilkan badge **Sudah dimainkan**.
3. **Given** pengguna menutup dan membuka lagi aplikasi, **When** membuka Alat
   Musik, **Then** status yang sudah dimainkan tetap ada.
4. **Given** pengguna hanya memilih alat atau membuka AR tanpa memainkan nada,
   **Then** alat itu tetap **Belum dimainkan**.
5. **Given** audio gagal dimuat atau model gagal dimuat, **Then** alat itu tidak
   ditandai sudah dimainkan.

### US3 — Status koleksi yang konsisten (Priority: P2)

Status tiap alat independen dan tidak berubah karena alat lain.

- **Why this priority:** koleksi hanya berguna kalau badge tidak salah.
- **Independent test:** mainkan Bonang, buka Alat Musik; hanya Bonang yang
  berubah; lima alat lain tetap “Belum dimainkan”.

**Acceptance scenarios:**

1. **Given** pengguna memainkan Bonang, **Then** hanya Bonang yang berubah;
   alat lain tidak terpengaruh.
2. **Given** pengguna memainkan alat yang sama berkali-kali, **Then** status
   tetap satu penanda dan tidak berubah-ubah.
3. **Given** semua enam alat sudah dimainkan, **Then** keenam kartu menampilkan
   **Sudah dimainkan**.
4. **Given** penyimpanan berisi id alat yang tidak dikenal, **Then** id itu
   diabaikan dan layar tetap menampilkan enam alat katalog.
5. **Given** data aplikasi dihapus, **Then** semua kartu kembali **Belum
   dimainkan**.

---

## Edge cases

- Mengetuk alat di S6 tidak melakukan apa-apa (tidak ada navigasi).
- Menekan kembali saat S6 terbuka → Beranda.
- Layar pendek/HP landscape → grid tetap 3 kolom dan panel dapat digulir bila
  tidak muat.
- Nama alat panjang (“Gong & Kempul”, “Gender Barung”) tetap terbaca.
- Status alat ditandai saat nada benar-benar diputar, bukan saat model muncul.
- Mengetuk cepat beberapa nada → penandaan tetap idempoten.
- Penyimpanan gagal ditulis → aplikasi tidak berhenti; status bisa kembali
  “Belum dimainkan” setelah dibuka ulang, dan itu dicatat sebagai keterbatasan.
- Katalog kosong (tidak terjadi pada data statis) → pesan “Belum ada alat”.

## Functional requirements

- **FR-001**: Tombol **Alat Musik** di Beranda MUST aktif dan membuka layar
  koleksi.
- **FR-002**: Layar koleksi MUST menampilkan keenam alat katalog dengan ikon,
  nama, dan asal.
- **FR-003**: Setiap kartu MUST menampilkan status **Sudah dimainkan** atau
  **Belum dimainkan**.
- **FR-004**: Alat MUST ditandai sudah dimainkan setelah minimal satu nada atau
  pukulan berhasil diputar di AR.
- **FR-005**: Memilih alat, membuka AR, memuat model, atau menampilkan kartu
  budaya MUST NOT menandai alat sudah dimainkan.
- **FR-006**: Status MUST tersimpan lokal dan bertahan setelah aplikasi ditutup.
- **FR-007**: Status tiap alat MUST independen; menandai satu alat tidak
  mengubah alat lain.
- **FR-008**: Layar koleksi MUST read-only: tidak ada pemilihan alat, tombol
  AR, atau perubahan status dari layar itu.
- **FR-009**: Tombol tutup dan tombol kembali sistem MUST mengembalikan
  pengguna ke Beranda.
- **FR-010**: Id alat yang tidak dikenal di penyimpanan MUST diabaikan tanpa
  crash.
- **FR-011**: Layar koleksi MUST mengikuti desain S6: panel tengah, plakat,
  grid 3×2, badge hijau/terang, latar ilustrasi, tombol tutup.
- **FR-012**: Saat data katalog kosong, layar MUST menampilkan pesan “Belum ada
  alat”, bukan layar kosong.

## Key entities

- **Instrument**: alat katalog (sudah ada).
- **PlayedInstrument**: penanda bahwa satu instrument pernah dimainkan.
  - `instrumentId` — id alat yang pernah dimainkan.
  - Status hanya dua: sudah / belum.
- **CollectionProgress**: kumpulan id alat yang sudah dimainkan, tersimpan
  lokal dan dibaca saat layar koleksi dibuka.

## Success criteria

- **SC-001**: Layar koleksi terbuka dalam satu tekan dari Beranda.
- **SC-002**: Keenam kartu tampil dengan status yang benar pada 6 dari 6 alat.
- **SC-003**: Status “sudah dimainkan” bertahan setelah aplikasi ditutup dan
  dibuka lagi.
- **SC-004**: Memilih alat tanpa memainkan nada tidak menandai alat itu.
- **SC-005**: Menandai satu alat tidak mengubah status alat lain (uji 6 alat).
- **SC-006**: Tidak ada crash saat penyimpanan kosong, berisi id asing, atau
  dibuka berulang 10 kali.
- **SC-007**: Tombol tutup mengembalikan ke Beranda pada 5 dari 5 percobaan.
- **SC-008**: Tampilan cocok dengan S6 pada empat hal yang bisa diamati: latar
  ilustrasi, panel tengah, grid 3×2, badge status.

## Non-goals

- Persentase koleksi, jumlah alat, atau grafik progres.
- Achievement, hadiah, streak, atau notifikasi.
- Tombol reset progres di dalam aplikasi.
- Sinkronisasi akun/cloud.
- Statistik per nada, durasi main, atau waktu terakhir dimainkan.
- Urutan/filter/sortir kartu.
- Navigasi dari S6 ke AR atau pemilihan alat dari S6.
- Mengubah isi katalog atau menambah alat baru.

## Assumptions

- “Sudah dimainkan” berarti minimal satu nada/pukulan berhasil diminta untuk
  diputar setelah audio siap; bukan sekadar membuka AR.
- Progres disimpan lokal bersama preferensi alat terakhir; tidak ada server.
- Data katalog statis selalu berisi enam alat.
- Layar koleksi hanya dibuka dari Beranda.
- Tidak ada tombol reset; menghapus data aplikasi mengosongkan progres.
- Kartu S6 tidak interaktif.

## Open questions

- Tidak ada pertanyaan yang menghalangi implementasi. Keputusan yang diambil:
  1. Penandaan terjadi di titik putar nada yang sama untuk note bar, pad, dan
     tap model.
  2. Layar koleksi memakai grid 3×2 dan dapat digulir pada layar pendek.

## Checklist

- [x] Tidak ada nama teknologi/library
- [x] Setiap acceptance scenario bisa diamati dari luar
- [x] Setiap FR punya cara verifikasi
- [x] Tidak ada `[NEEDS CLARIFICATION]` tersisa
- [x] Non-goals eksplisit
- [x] Tidak ada fitur spekulatif
- [x] Referensi desain S6 tercatat
