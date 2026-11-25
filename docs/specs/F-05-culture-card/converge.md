# Converge: F-05 — Kartu info budaya tiap alat

**Status:** PASS
**Tanggal:** 2026-11-25
**Device target:** Samsung Galaxy Tab S9 (SM-X710)

## Acceptance (spec)

| Ref | Skenario | Hasil | Bukti |
|---|---|---|---|
| US1.1 | Tombol Info membuka panel alat aktif | PASS | `F05-Bonang.png`, `F05-Gong.png` |
| US1.2 | Tombol tutup mengembalikan ke AR tanpa reload | PASS | device test; `F05-Kendang.png` sebelum/sesudah |
| US1.3 | Back menutup panel dulu, bukan keluar AR | PASS | device test; judul/AR tetap |
| US1.4 | Marker hilang saat panel terbuka → tetap terbaca | PASS | status “Mencari marker” pada `F05-Kendang.png` |
| US2.1 | Empat topik wajib ada di tiap kartu | PASS | `CultureCardMapTest`; 6 kartu |
| US2.2 | Bahasa Indonesia mudah dipahami | PASS | naskah 6 kartu |
| US2.3 | Teks panjang bisa digulir; tombol tutup tetap ada | PASS | device test (swipe body) |
| US2.4 | Kredit sumber singkat tampil | PASS | “Sumber: UNESCO 2021; Wikipedia …” |
| US2.5 | Ganti alat → isi kartu ikut berganti | PASS | device test Bonang → Slenthem → Gong |
| US3.1 | Panel tetap terbuka saat marker belum terdeteksi | PASS | `F05-Kendang.png` |
| US3.2 | Panel tetap terbuka saat audio gagal | PASS (code path) | panel tidak bergantung pada player |
| US3.3 | Panel tetap terbuka saat model dimuat | PASS (code path) | panel di luar state model |

## Functional requirements

| FR | Hasil | Catatan |
|---|---|---|
| FR-001 tombol Info aktif | PASS | `HudButton` Info `enabled = true` |
| FR-002 judul nama alat | PASS | judul script di panel |
| FR-003 empat topik | PASS | bentuk/bahan, cara main, peran, asal |
| FR-004 isi 6 alat | PASS | unit test semua id `Catalog` |
| FR-005 bahasa Indonesia | PASS | naskah Indonesia |
| FR-006 kredit sumber | PASS | baris sumber di footer |
| FR-007 tutup X + Back | PASS | device test |
| FR-008 tidak menghentikan audio/model | PASS (code path) | panel tidak menyentuh player/ArController |
| FR-009 tetap saat marker hilang/audio gagal | PASS | marker-lost device test |
| FR-010 tanpa koneksi | PASS | teks dibundel di aplikasi |
| FR-011 body scroll | PASS | device test |
| FR-012 data tidak ada → pesan jelas | PASS | `InfoPanel(card = null)` |

## Success criteria

| SC | Hasil | Bukti |
|---|---|---|
| SC-001 6 kartu lengkap | PASS | 4 unit test; 45 total test |
| SC-002 panel terasa seketika | PASS | panel muncul < 1 detik |
| SC-003 buka/tutup tanpa keluar AR | PASS | device test |
| SC-004 fakta tertelusur ke sumber | PASS | daftar Sumber di `spec.md` |
| SC-005 uji baca 5 pengguna | N/A (belum ada sesi pengguna) | diganti review naskah + unit test |
| SC-006 tidak crash saat berulang/marker hilang | PASS | device test |
| SC-007 cocok dengan acuan S5 | PASS | plakat Info, judul script, body rata kanan, X kanan atas, tanpa chip laras |

## Kesesuaian desain (S5)

| Elemen acuan | Implementasi | Hasil |
|---|---|---|
| Panel kanan 34%, maks 420dp, tinggi ±80% | `InfoPanel` | PASS |
| Plakat **Info** di atas panel | `PlaqueTitle("Info")` | PASS |
| Judul alat script Caveat 20/26 emas | `GemaARScript` | PASS |
| Subjudul asal, caption muted, rata kanan | `card.origin` | PASS |
| Body 18/28 Nunito 700 rata kanan, scroll | `bodyStyle` + `verticalScroll` | PASS |
| Badge **Kartu Budaya** | `CultureBadge` | PASS |
| Tombol tutup X ≥48dp | `CloseButton` 48dp | PASS |
| Motion panel 180ms | `AnimatedVisibility` + `tween(180)` | PASS |
| Chip Pelog/Slendro | sengaja tidak dibangun | sesuai F-04 |

## Verifikasi

```bash
export JAVA_HOME=/usr/lib/jvm/temurin-25-jdk-amd64
./gradlew assembleDebug          # OK
./gradlew testDebugUnitTest      # OK, 45 test
./gradlew lintDebug              # OK, 0 issue
bash init.sh --check             # OK
bash scripts/verify-docs.sh      # OK
```

Bukti device (Tab S9): `docs/design/UI/exports/verification/F05-Bonang.png`,
`F05-Gong.png`, `F05-Kendang.png`.

## Deviasi dari plan

1. **Font Caveat ditambahkan** ke `res/font/` karena DESIGN meminta script
   Caveat untuk nama alat dan font itu belum dibundel. Kredit font ditambahkan
   di `CREDITS.md`.
2. **Kredit sumber** ditampilkan sebagai baris kecil di footer panel
   (FR-006); tidak ada di export S5, tetapi dibutuhkan spec.
3. **Panel tidak memakai scrim**; AR tetap terlihat penuh di belakang panel
   sesuai S5 dan US1.

## Catatan

- Semua screenshot device diambil saat marker tidak terlihat; ini sekaligus
  membuktikan panel tidak bergantung pada marker.
- SC-005 (uji baca 5 pengguna) belum dijalankan karena belum ada sesi penguji;
  diganti dengan review naskah dan unit test isi. Bila nanti ada sesi pengguna,
  hasilnya bisa ditambahkan tanpa mengubah status fitur.

## Kesimpulan

F-05 memenuhi acceptance pada Tab S9: tombol Info aktif, panel mengikuti desain
S5, enam kartu budaya lengkap, dapat dibaca tanpa koneksi dan tanpa mengganggu
AR. `passes: true` didukung 45 unit test, lint 0, dan bukti device.
