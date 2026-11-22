# Tasks: F-03 — Katalog alat musik + pilih dari daftar

**Input:** `spec.md`, `plan.md`
**Panduan langkah demi langkah:** `team-guide.md`

## Pembagian

- **A = kamu** — fondasi + overlay + 2 alat (Bonang, Slenthem)
- **B = temanmu** — 3 alat (Gender, Gong & Kempul, Kendang) + persistence
- **R = agent** — review, test, converge

Kerjakan **A dulu sampai build hijau**, lalu handoff ke B.

---

## A — kamu

- [x] **A1** Siapkan ikon Saron, Bonang, Slenthem
      files: `app/src/main/res/drawable-nodpi/ikon_saron.png`,
             `ikon_bonang.png`, `ikon_slenthem.png`
      cara: ambil `docs/design/UI/exports/assets/alat-*.png`, perkecil ke ±256 px
      verify: 3 file ada, ukuran <100 KB, nama resource huruf kecil

- [x] **A2** Buat `data/Instrument.kt`
      files: `data/Instrument.kt`
      verify: data class berisi id, name, origin, modelAsset, iconRes,
              audioAvailable

- [x] **A3** Buat `data/Catalog.kt` dengan 3 alat
      files: `data/Catalog.kt`
      verify: Saron, Bonang, Slenthem; `byId()` fallback Saron;
              `audioAvailable` true hanya Saron

- [x] **A4** Buat `ui/ar/CatalogOverlay.kt`
      design: `docs/design/UI/exports/S2-katalog.png`
      files: `ui/ar/CatalogOverlay.kt`
      verify: overlay + scrim + plaque “Katalog” + close X;
              kartu 2 baris × 3 kolom; nama + asal + ikon;
              kartu aktif bergaris emas; klik kartu memanggil callback

- [x] **A5** `ArController` menerima `Instrument`
      files: `ar/ArController.kt`
      verify: model mengikuti `instrument.modelAsset`; ganti alat tidak
              membuat ulang sesi/marker; loading/error tetap seperti F-01

- [x] **A6** Wiring `ArScreen` (bagian A)
      files: `ui/ar/ArScreen.kt`
      verify: tombol Katalog aktif; buka/tutup overlay;
              pilih alat → overlay tutup, judul berubah, model berganti;
              note bar hanya Saron; pindah dari Saron menghentikan nada;
              non-Saron → pesan “Audio belum tersedia”;
              Back: Katalog → Petunjuk → keluar

- [x] **A7** Build + tes 3 alat + handoff
      verify: `assembleDebug`, `testDebugUnitTest`, `lintDebug` hijau;
              katalog 3 kartu tampil; pilih Bonang → judul berubah;
              tes model di marker dilakukan ulang oleh agent di R3;
              handoff dicatat di `progress.md`

---

## B — temanmu

- [ ] **B1** Siapkan ikon Gender, Gong & Kempul, Kendang
      files: `app/src/main/res/drawable-nodpi/ikon_gender_barung.png`,
             `ikon_gong_kempul.png`, `ikon_kendang.png`
      verify: 3 file ada, <100 KB, nama resource benar

- [ ] **B2** Tambah 3 alat ke `data/Catalog.kt`
      files: `data/Catalog.kt`
      verify: total 6 alat; id unik; model path benar;
              `audioAvailable = false` untuk 3 alat ini

- [ ] **B3** Buat `data/InstrumentStore.kt`
      files: `data/InstrumentStore.kt`
      verify: simpan/baca `selected_instrument`;
              simpan/baca `catalog_auto_opened`;
              default null/false

- [ ] **B4** Wiring persistence + first-run di `ArScreen`
      files: `ui/ar/ArScreen.kt`
      verify: saat mulai, baca alat terakhir; fallback Saron;
              pilih alat → simpan;
              setelah Petunjuk pertama → Katalog terbuka sekali;
              flag disimpan segera agar tidak terbuka dua kali

- [ ] **B5** Cek 6 alat + audio non-Saron + kembali ke Saron
      verify: 6 kartu tampil; pilih tiap alat; non-Saron pesan audio;
              Saron kembali → note bar + audio; tutup/buka app → alat terakhir

- [ ] **B6** Handoff ke agent
      verify: build hijau; catat file yang diubah dan yang belum diuji

---

## R — agent

- [ ] **R1** Review kode: boundary, kontrak, tidak ada fitur F-04+
- [ ] **R2** `assembleDebug`, `testDebugUnitTest`, `lintDebug`,
      `init.sh --check`, `verify-docs.sh`
- [ ] **R3** Uji device: 6 alat, model swap, last instrument, first-run,
      non-Saron, marker hilang, Back, tidak crash
- [ ] **R4** Perbaiki temuan, converge F-03, `passes: true`, commit/push

## Rules

- Jangan memakai JSON/parser; data katalog ditulis di `Catalog.kt`.
- Jangan menambah audio alat lain (F-04), badge/F-06, Info/F-05, volume/F-07.
- Home **Alat Musik** tetap nonaktif.
- Marker tidak boleh di-scan ulang saat ganti alat.
- Satu langkah → build → lanjut. Jangan menumpuk banyak perubahan.
