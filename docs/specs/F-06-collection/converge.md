# Converge: F-06 — Progres koleksi alat yang sudah dimainkan

**Status:** PASS
**Tanggal:** 2026-11-25
**Device target:** Samsung Galaxy Tab S9 (SM-X710)

## Acceptance (spec)

| Ref | Skenario | Hasil | Bukti |
|---|---|---|---|
| US1.1 | Beranda → Alat Musik membuka S6 | PASS | device test; `F06-belum.png` |
| US1.2 | Tombol tutup S6 → Beranda | PASS | device test |
| US1.3 | Back sistem S6 → Beranda | PASS | device test |
| US1.4 | S6 read-only (tanpa pilih alat/AR) | PASS | kartu tidak clickable |
| US2.1 | Satu nada dimainkan → alat ditandai | PASS | log `slenthem_6/7`; prefs berisi `played_instruments=slenthem`; badge hijau |
| US2.2 | Badge “Sudah dimainkan” tampil | PASS | `F06-sebagian.png`, `F06-semua.png` |
| US2.3 | Status bertahan setelah aplikasi ditutup | PASS | baca ulang setelah restart |
| US2.4 | Hanya memilih alat tanpa main → tetap belum | PASS | setelah prefs kosong, semua kartu “Belum dimainkan” |
| US2.5 | Audio/model gagal → tidak ditandai | PASS (code path) | `playNote` berhenti sebelum `markPlayed` |
| US3.1 | Satu alat tidak mengubah alat lain | PASS | `F06-sebagian.png`; unit test |
| US3.2 | Main berulang tetap satu penanda | PASS | unit test idempoten |
| US3.3 | Enam alat bisa semuanya hijau | PASS | `F06-semua.png` |
| US3.4 | Id asing diabaikan | PASS | unit test; UI hanya membaca id katalog |
| US3.5 | Data aplikasi dihapus → semua belum | PASS | device test setelah prefs dikosongkan |

## Functional requirements

| FR | Hasil | Catatan |
|---|---|---|
| FR-001 tombol Alat Musik aktif | PASS | `HomeScreen` |
| FR-002 enam kartu ikon/nama/asal | PASS | `F06-*` |
| FR-003 badge sudah/belum | PASS | `ProgressBadge` |
| FR-004 tandai setelah satu nada | PASS | device: Slenthem main → prefs + badge |
| FR-005 pilih/muat/Info tidak menandai | PASS (code path) | hanya `playNote` yang menandai |
| FR-006 tersimpan lokal | PASS | dibaca lagi setelah restart |
| FR-007 status independen | PASS | unit test + screenshot |
| FR-008 S6 read-only | PASS | kartu tidak clickable |
| FR-009 tutup/back ke Beranda | PASS | device test |
| FR-010 id asing diabaikan | PASS | unit test |
| FR-011 mengikuti desain S6 | PASS | panel, plakat, grid 3×2, badge |
| FR-012 katalog kosong → pesan | PASS (code path) | teks “Belum ada alat” |

## Success criteria

| SC | Hasil | Bukti |
|---|---|---|
| SC-001 satu tekan dari Beranda | PASS | device test |
| SC-002 enam kartu status benar | PASS | `F06-*` |
| SC-003 bertahan setelah restart | PASS | device test |
| SC-004 pilih tanpa main tidak menandai | PASS | device: prefs kosong → semua belum |
| SC-005 satu alat tidak mengubah lain | PASS | `F06-sebagian.png` |
| SC-006 tidak crash saat kosong/asing/berulang | PASS | 5× buka-tutup; unit test |
| SC-007 tutup/back ke Beranda 5/5 | PASS | device test |
| SC-008 cocok S6 (latar, panel, grid 3×2, badge) | PASS | screenshot |

## Kesesuaian desain (S6)

| Elemen acuan | Implementasi | Hasil |
|---|---|---|
| Latar ilustrasi Beranda | `ilustrasi_latar` | PASS |
| Tombol tutup kanan atas di dalam panel | `CloseButton` 48dp | PASS |
| Panel tengah maks 720dp | `CollectionScreen` | PASS |
| Plakat **Alat Musik** | `PlaqueTitle` | PASS |
| Subjudul badge hijau | teks sesuai desain | PASS |
| Grid 3×2 | `chunked(3)` | PASS |
| Kartu ikon krem + nama script + “Jawa” | `CollectionCard` | PASS |
| Badge hijau + centang | `Success` + `Check` | PASS |
| Badge terang + ikon putar | `Background` + `Refresh` | PASS |
| Kartu tidak clickable | tanpa `clickable` | PASS |

## Verifikasi

```bash
export JAVA_HOME=/usr/lib/jvm/temurin-25-jdk-amd64
./gradlew assembleDebug          # OK
./gradlew testDebugUnitTest      # OK, 50 test
./gradlew lintDebug              # OK, 0 issue
bash init.sh --check             # OK
bash scripts/verify-docs.sh      # OK
```

Bukti device: `docs/design/UI/exports/verification/F06-belum.png`,
`F06-sebagian.png`, `F06-semua.png`.

## Catatan bukti

- Badge kosong/sebagian/semua dan pembacaan status setelah restart diuji di
  Tab S9.
- Jalur **tulis** sudah terkonfirmasi di device: marker terdeteksi, nada
  `slenthem_6/7` diputar, lalu `gemaar_preferences.xml` berisi
  `<set name="played_instruments"><string>slenthem</string></set>` dan kartu
  Slenthem berubah hijau.
- Tombol tutup X berada di dalam panel, pojok kanan atas; menekannya kembali
  ke Beranda. Back sistem juga kembali ke Beranda.
- Data uji di perangkat sudah dikembalikan ke kondisi bersih (semua belum
  dimainkan).

## Deviasi dari plan

1. Progres memakai penyimpanan lokal yang sudah ada (`InstrumentStore`),
   bukan dependency penyimpanan baru.
2. Kartu S6 dibuat khusus di `CollectionScreen`; kartu katalog S2 tidak diubah
   agar F-03 tidak regresi.
3. `CollectionProgress` dipisah sebagai nilai murni agar semantik “tandai
   idempoten” bisa diuji tanpa device.
4. Tombol panah kembali pada export S6 diganti tombol tutup X di dalam panel
   (pojok kanan atas) sesuai permintaan produk; perilakunya tetap kembali ke
   Beranda.

## Kesimpulan

F-06 memenuhi acceptance pada Tab S9 untuk tampilan, navigasi, status, dan
persistence, termasuk jalur tulis main-nada → badge hijau. `passes: true`
didukung 50 unit test, lint 0, dan tiga bukti screenshot.
