# DESIGN — token & alasan (GemaAR)

> **Hanya keputusan visual.** Tidak ada perilaku, tidak ada data.
> Perilaku ada di `spec.md`. Peta layar ada di `SCREENS.md`.
>
> Referensi visual: `docs/design/UI/image*.png` (MNi Gamelan Java).
> Sumber token mesin: `design-tokens.json`. Kalau dokumen dan JSON berbeda,
> **JSON menang**.

---

## Arah visual

Ilustrasi garis tangan bergaya wayang di atas papan gamelan: krem, kayu, dan
emas, dengan panel cokelat gelap bertepi ukir. Kesan yang dituju: **hangat,
tradisional, playful, edukatif** — bukan flat Material default.

- Orientasi **landscape**, immersive; kamera AR full-bleed.
- Latar boleh ramai; konten utama selalu di dalam panel/plakat agar terbaca.
- Tidak ada gradient dekoratif, glassmorphism, atau ungu-biru default.
- Ornamen (ukiran, bayangan keras) adalah bagian identitas, bukan hiasan acak.

## Color

| Token | Nilai | Dipakai untuk | Alasan |
|---|---|---|---|
| `--color-bg` | `#F8F4E8` | latar ilustrasi | krem kertas, hangat, kontras dengan panel |
| `--color-surface` | `#3D382C` | panel, modal | cokelat zaitun gelap; teks krem terbaca |
| `--color-surface-2` | `#4E3D35` | plakat judul, header | satu tingkat lebih terang dari panel |
| `--color-wood` | `#6C543C` | tombol sekunder, bingkai | kayu, netral untuk aksi non-utama |
| `--color-wood-light` | `#806247` | rak, aksen | pemisah bidang kayu |
| `--color-fg` | `#FFF6E3` | teks di panel gelap | krem, tidak menyilaukan |
| `--color-fg-dark` | `#3B2C1C` | teks di emas/latar terang | cokelat, lebih lembut dari hitam |
| `--color-muted` | `#C9B99F` | teks sekunder | turun satu tingkat dari `fg` |
| `--color-border` | `#2E2418` | tepi panel, ukiran | batas tegas di atas ilustrasi |
| `--color-primary` | `#F8C32A` | aksi utama (Mulai) | emas gamelan; titik fokus layar |
| `--color-primary-fg` | `#3B2C1C` | teks di atas emas | kontras ±8:1 |
| `--color-accent` | `#641F4F` | logo, aksen marun | identitas "Gamelan"; dipakai hemat |
| `--color-success` | `#8BAC26` | Unduh marker | hijau daun, bukan hijau status generik |
| `--color-danger` | `#70302F` | error, hapus | merah bata, selaras palet kayu |
| `--color-metal` | `#EAB76A` | perunggu, ikon alat | logam gamelan |
| `--color-line` | `#A9C3D1` | garis ilustrasi | biru pucat, tidak bersaing dengan emas |

Aturan: **tidak ada hex di komponen.** Selalu token. Kontras teks minimal 4.5:1
(teks krem di `surface` ±10.5:1).

## Typography

| Peran | Size / line-height | Weight | Font | Alasan |
|---|---|---|---|---|
| Display (plakat) | 32 / 40 | 700 | Alfa Slab One | tebal, terbaca di atas ilustrasi ramai |
| H1 | 24 / 32 | 700 | Alfa Slab One | judul layar |
| H2 | 20 / 28 | 700 | Nunito | sub-judul, nama panel |
| Body | 16 / 24 | 600 | Nunito | teks utama; rounded, ramah |
| Body Info | 18 / 28 | 700 | Nunito | panel Info di AR; teks panjang, rata kanan mengikuti referensi |
| Caption | 12 / 16 | 600 | Nunito | label kecil, status |
| Script (nama alat) | 20 / 26 | 700 | Caveat | nama alat di kartu, kesan tulisan tangan |
| Angka | 20 / 28 | 700 | Nunito (tabular) | persentase slider, progres |

Font stack: `Alfa Slab One` (display), `Nunito` (body), `Caveat` (script).
Ketiganya dipilih dari referensi dan tersedia di Google Fonts (OFL); bundel
sebagai aset aplikasi. Tidak ada font keempat.

Aturan: teks di atas ilustrasi wajib di dalam plakat **atau** diberi outline
gelap + shadow. Jangan menaruh body text langsung di atas gambar.
Rata kanan hanya untuk panel Info (mengikuti referensi); layar lain rata kiri.

## Spacing

Skala: `4 · 8 · 12 · 16 · 24 · 32 · 48`

| Konteks | Nilai |
|---|---|
| Dalam satu grup (ikon + label) | 8 |
| Antar kartu / antar baris | 16 |
| Padding panel | 24 |
| Padding layar (landscape) | 24 horizontal, 16 vertikal |
| Jarak panel ke tepi ilustrasi | 32 |

Alasan skala 4: konsisten di layar landscape dengan kepadatan tinggi.
Tidak ada nilai di luar skala.

## Radius & shadow

| Token | Nilai | Dipakai |
|---|---|---|
| `--radius-sm` | 8px | chip, badge |
| `--radius-md` | 12px | tombol, kartu |
| `--radius-lg` | 20px | panel, modal |
| `--radius-full` | 9999px | pill, avatar |
| `--shadow-hard` | `0 4px 0 #2E2418` | tombol (kesan timbul) |
| `--shadow-panel` | `0 8px 24px rgba(0,0,0,.35)` | panel di atas ilustrasi |
| `--shadow-soft` | `0 2px 8px rgba(0,0,0,.20)` | badge, elemen kecil |

Tepi ukir panel dibuat sebagai drawable 9-patch, bukan border tipis. Shadow keras
hanya untuk elemen interaktif; panel memakai shadow lembut.

## Layout & orientasi

| Aturan | Nilai |
|---|---|
| Orientasi | landscape (referensi 2.22:1), edge-to-edge |
| Touch target | minimal 48dp |
| Rail HUD AR | 56dp |
| Lebar panel maksimum | 720dp, center |
| Panel di layar compact | 92% lebar |
| Ilustrasi latar | crop/scale, tidak pernah menyembunyikan panel |
| Panel Info (S5) | sisi kanan, 34% lebar (maks 420dp), tinggi ±80%, body scroll |

Window class: `<600dp` compact, `600–839dp` medium, `≥840dp` expanded.
Panel tidak melebar melewati 720dp; ruang lebih dipakai ilustrasi.

## Motion

| Kejadian | Durasi | Easing |
|---|---|---|
| Tekan tombol | 100ms (scale 0.96) | ease-out |
| Panel masuk/keluar | 180ms | ease-out |
| Alat muncul di AR | 260ms (pop + fade) | ease-out |
| Hint berdenyut | 1200ms loop (opacity 0.6→1) | ease-in-out |
| Perpindahan layar | 180ms | ease-out |

Animasi hanya untuk umpan balik. Tidak ada parallax atau animasi dekoratif saat
scroll.

---

## Audit visual (10 dimensi)

Nilai 0–10. Diisi setelah S1–S7 jadi; target setiap dimensi ≥ 7.

| # | Dimensi | Skor | Catatan |
|---|---|---|---|
| 1 | Color consistency | — | token sudah ditetapkan, belum diterapkan |
| 2 | Typography hierarchy | — | 3 font, cek rendering di device |
| 3 | Spacing rhythm | — | skala 4, belum diterapkan |
| 4 | Component consistency | — | inventaris di `COMPONENTS.md` |
| 5 | Responsive behavior | — | landscape; cek tablet |
| 6 | Dark mode | — | panel sudah gelap; mode terang belum diputuskan |
| 7 | Animation | — | spesifikasi di atas |
| 8 | Accessibility | — | target 48dp; cek kontras teks script |
| 9 | Information density | — | panel max 720dp |
| 10 | Polish | — | loading/empty/error belum dibuat |

Aturan: skor < 7 pada salah satu dimensi → masuk `feature_list.json` sebagai
perbaikan, bukan dikerjakan diam-diam.

## AI slop — tanda bahaya

- Gradient di semua tempat tanpa alasan
- Default ungu-ke-biru
- Glassmorphism tanpa fungsi
- Sudut membulat pada elemen yang tidak seharusnya
- Animasi berlebihan saat scroll
- Hero generik: teks tengah di atas gradient stok
- Font sans-serif tanpa karakter

Kalau salah satu muncul, perbaiki sebelum menambah fitur.

---

## Aturan implementasi

1. Token didefinisikan **sekali** di `design-tokens.json`, lalu dipetakan ke
   `ui/theme/Color.kt`, `Type.kt`, dan `Dimens.kt`. Tidak ada hex di Composable.
2. Komponen memakai token, bukan nilai mentah; varian bukan komponen baru.
3. Ornamen panel/plakat = drawable (9-patch/vector), bukan susunan border manual.
4. Semua teks dari `strings.xml` (MVP bahasa Indonesia; jangan hardcode).
5. Screenshot layar baru masuk `converge.md` sebagai bukti visual.
6. Perubahan token yang menyentuh banyak layar dicatat di `docs/decisions/`.

## Yang tidak masuk sini

- Teks tombol, label, pesan error → `spec.md`
- Data yang ditampilkan → `SCREENS.md`
- Perilaku (tap, pilih alat, mainkan nada) → `spec.md`
- Daftar komponen → `COMPONENTS.md`
