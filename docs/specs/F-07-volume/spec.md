# Spec: F-07 — Pengaturan volume musik

**Feature ID:** F-07
**Status:** draft
**Owner:** product + engineering

---

## Referensi desain (wajib)

F-07 memakai layar S7 yang sudah dibuat. Tidak ada bahasa visual baru.

| Berkas | Bagian yang dipakai |
|---|---|
| `docs/design/UI/exports/S7-pengaturan.png` | Acuan visual utama: latar ilustrasi, panel modal tengah, plakat **Pengaturan**, tombol tutup X di dalam panel, baris **Musik**, slider emas, persentase, pill “Volume musik tersimpan otomatis” |
| `docs/design/SCREENS.md` | Baris S7, alur Beranda/AR → Pengaturan, aturan “hanya volume musik”, “S7 overlay; jika dari S4 sesi AR di-pause”, state loading/error/success |
| `docs/design/DESIGN.md` | Panel maks 720dp, token warna `Primary`/`Surface`/`Muted`, Body, angka persentase tabular, target sentuh ≥48dp |
| `docs/design/COMPONENTS.md` | `SettingsPanel` (music-only), `SliderRow`, `PlaqueTitle`, `IconButton` close |

### Elemen layar (dari S7)

| Elemen | Isi | Gaya acuan |
|---|---|---|
| Latar | ilustrasi gamelan yang sama dengan Beranda | `ilustrasi-latar` |
| Panel | modal tengah, lebar maks 720dp | `SettingsPanel`, radius 20 |
| Plakat | teks **Pengaturan** di atas panel | `PlaqueTitle` |
| Baris musik | ikon not, label **Musik**, slider, persentase | `SliderRow` |
| Slider | 0–100%, jalur emas, thumb jelas | `Primary` |
| Persentase | angka besar, mis. “100%” | Nunito tabular |
| Pill simpan | “✓ Volume musik tersimpan otomatis” | caption muted |
| Tombol tutup | X di kanan atas **di dalam panel** | `IconButton`, ≥48dp |

Hanya ada **satu** baris pengaturan. Tidak ada SFX, bahasa, atau equalizer.

## Problem

Semua nada dan pukulan selalu berbunyi pada volume penuh. Tombol
**Pengaturan** di Beranda dan ikon gear di AR masih nonaktif, sehingga pengguna
tidak bisa mengecilkan suara di kelas, perpustakaan, atau ruang bersama.

## Why now

F-04 memberi audio ke semua alat dan F-06 melengkapi koleksi. Volume adalah
kontrol terakhir yang dibutuhkan agar aplikasi nyaman dipakai di sekolah sebelum
MVP dinyatakan lengkap.

## User stories

### US1 — Membuka pengaturan dari Beranda dan AR (Priority: P1)

Pengguna membuka panel **Pengaturan** dari Beranda atau dari layar AR, melihat
nilai volume saat ini, lalu menutupnya kembali ke layar asal.

- **Why this priority:** tanpa akses, slider tidak bisa dipakai.
- **Independent test:** dari Beranda tekan Pengaturan; panel muncul; tutup;
  Beranda kembali. Ulangi dari ikon gear di AR.

**Acceptance scenarios:**

1. **Given** Beranda aktif, **When** pengguna menekan **Pengaturan**,
   **Then** panel **Pengaturan** terbuka dengan slider volume.
2. **Given** layar AR aktif, **When** pengguna menekan ikon gear,
   **Then** panel **Pengaturan** terbuka di atas AR.
3. **Given** panel terbuka, **When** pengguna menekan tombol tutup,
   **Then** panel tertutup dan pengguna kembali ke layar asal (Beranda atau AR).
4. **Given** panel terbuka dari AR, **When** panel ditutup, **Then** AR lanjut
   tanpa memindai marker lagi.
5. **Given** panel terbuka, **When** pengguna menekan tombol kembali sistem,
   **Then** panel tertutup lebih dulu.

### US2 — Mengatur volume dan mendengar efeknya (Priority: P1)

Pengguna menggeser slider dan langsung mendengar perubahan volume.

- **Why this priority:** inti fitur.
- **Independent test:** mainkan nada, buka pengaturan, geser ke 0%; nada
  berikutnya tidak terdengar; geser ke 100%; nada berikutnya keras lagi.

**Acceptance scenarios:**

1. **Given** panel terbuka, **Then** slider menampilkan nilai volume saat ini
   dalam persen.
2. **Given** pengguna menggeser slider, **Then** angka persentase berubah
   mengikuti posisi slider.
3. **Given** volume diubah, **When** pengguna memainkan nada/pukulan berikutnya,
   **Then** bunyi mengikuti volume baru dalam waktu kurang dari 1 detik.
4. **Given** slider di 0%, **Then** nada/pukulan tidak terdengar dan angka
   menunjukkan “0%”.
5. **Given** slider di 100%, **Then** nada/pukulan terdengar pada volume penuh.

### US3 — Volume tersimpan dan konsisten (Priority: P2)

Nilai volume diingat setelah aplikasi ditutup dan berlaku untuk semua alat.

- **Why this priority:** pengguna tidak mau mengatur ulang setiap membuka app.
- **Independent test:** atur 40%, tutup aplikasi, buka lagi, buka Pengaturan;
  nilai tetap 40%.

**Acceptance scenarios:**

1. **Given** pengguna mengubah volume, **Then** nilai tersimpan otomatis tanpa
   tombol simpan.
2. **Given** aplikasi ditutup dan dibuka lagi, **When** Pengaturan dibuka,
   **Then** nilai terakhir ditampilkan.
3. **Given** volume diubah saat alat A aktif, **When** pengguna berganti ke
   alat B, **Then** volume yang sama berlaku.
4. **Given** nilai tersimpan rusak/di luar 0–100, **Then** aplikasi memakai
   100% dan tetap berjalan.
5. **Given** penyimpanan gagal, **Then** muncul pesan singkat dan nilai
   dikembalikan ke nilai terakhir yang valid; aplikasi tidak berhenti.

---

## Edge cases

- Volume 0% bukan berarti audio dimatikan permanen; menaikkan slider langsung
  mengembalikan bunyi.
- Membuka Pengaturan saat nada masih berbunyi → nada boleh lanjut; perubahan
  volume berlaku untuk nada berikutnya.
- Membuka Pengaturan dari AR saat marker hilang → panel tetap terbuka; AR tetap
  di belakang.
- Menutup panel dengan X, Back, atau tap di luar panel (jika diizinkan desain)
  → kembali ke layar asal.
- Menggeser slider cepat berkali-kali → tidak crash, nilai akhir yang dipakai.
- Rotasi layar saat panel terbuka → panel tetap terbaca.
- Tidak ada baris SFX/bahasa; jangan menampilkannya.
- Penyimpanan kosong → default 100%.

## Functional requirements

- **FR-001**: Tombol **Pengaturan** di Beranda MUST aktif dan membuka panel S7.
- **FR-002**: Ikon gear di AR MUST aktif dan membuka panel S7.
- **FR-003**: Panel MUST memuat satu slider **Musik** dengan rentang 0–100.
- **FR-004**: Panel MUST menampilkan persentase volume saat ini.
- **FR-005**: Menggeser slider MUST mengubah persentase dan volume bunyi alat
  berikutnya dalam < 1 detik.
- **FR-006**: Volume MUST berlaku untuk semua nada/pukulan di semua alat.
- **FR-007**: Volume MUST tersimpan otomatis dan dipulihkan saat aplikasi
  dibuka lagi.
- **FR-008**: Nilai 0% MUST membuat bunyi tidak terdengar; 100% MUST bunyi
  penuh.
- **FR-009**: Tombol tutup dan tombol kembali sistem MUST menutup panel dan
  kembali ke layar asal.
- **FR-010**: Panel yang dibuka dari AR MUST mem-pause sesi AR dan melanjutkannya
  tanpa scan ulang setelah ditutup.
- **FR-011**: Nilai di luar 0–100 atau data rusak MUST dikoreksi ke 100%.
- **FR-012**: Gagal menyimpan MUST menampilkan pesan singkat dan tidak
  menghentikan aplikasi.
- **FR-013**: Panel MUST mengikuti desain S7: modal tengah, plakat, satu baris
  musik, slider emas, persentase, pill simpan otomatis, X di dalam panel.
- **FR-014**: F-07 MUST NOT menambah baris SFX, pemilih bahasa, atau equalizer.

## Key entities

- **MusicVolume**: nilai 0–100 yang berlaku untuk semua bunyi alat.
  - `percent` — bilangan bulat 0–100.
  - `default` — 100.
  - `lastValid` — nilai terakhir yang berhasil disimpan.

## Success criteria

- **SC-001**: Panel Pengaturan terbuka dari Beranda dan AR pada 5 dari 5
  percobaan.
- **SC-002**: Menggeser slider mengubah persentase dan bunyi berikutnya pada 5
  dari 5 percobaan.
- **SC-003**: Nilai bertahan setelah aplikasi ditutup dan dibuka lagi.
- **SC-004**: 0% tidak menghasilkan bunyi; 100% menghasilkan bunyi penuh.
- **SC-005**: Volume berlaku sama untuk keenam alat.
- **SC-006**: Tidak ada crash saat slider digeser cepat, data rusak, atau panel
  dibuka berulang.
- **SC-007**: Tampilan cocok dengan S7 pada empat hal yang bisa diamati: panel
  modal, plakat Pengaturan, satu baris Musik + slider, X di dalam panel.

## Non-goals

- Volume SFX terpisah (tidak ada SFX terpisah di MVP).
- Pemilih bahasa.
- Equalizer, bass/treble, atau pengaturan audio lanjutan.
- Volume per alat atau per nada.
- Tombol mute global terpisah.
- Musik latar/backsound.
- Reset ke default di dalam UI (default hanya saat data kosong/rusak).
- Sinkronisasi volume ke akun/cloud.
- Getaran/haptic.

## Assumptions

- “Volume musik” berarti volume seluruh sample alat (nada dan pukulan); tidak
  ada trek musik latar atau SFX terpisah di MVP.
- Default 100%; slider memakai langkah 1%.
- Perubahan disimpan otomatis saat slider dilepas atau berubah.
- Panel dari AR mem-pause kamera/sesi AR; setelah ditutup sesi lanjut tanpa
  scan ulang.
- Panel dari Beranda hanya overlay di atas Beranda.
- Data lama tanpa nilai volume dianggap 100%.

## Open questions

- Tidak ada pertanyaan yang menghalangi implementasi. Keputusan produk:
  1. Label tetap **Musik** mengikuti desain, walaupun yang dikontrol adalah
     bunyi alat.
  2. Tidak ada tombol simpan; pill “tersimpan otomatis” adalah umpan balik.
  3. Tombol tutup berada di dalam panel, kanan atas.

## Checklist

- [x] Tidak ada nama teknologi/library
- [x] Setiap acceptance scenario bisa diamati dari luar
- [x] Setiap FR punya cara verifikasi
- [x] Tidak ada `[NEEDS CLARIFICATION]` tersisa
- [x] Non-goals eksplisit
- [x] Tidak ada fitur spekulatif
- [x] Referensi desain S7 tercatat
