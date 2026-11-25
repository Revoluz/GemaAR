# Plan: F-06 — Progres koleksi alat yang sudah dimainkan

**Input:** `spec.md`
**Status:** approved
**Scope:** layar S6 + penyimpanan status “sudah dimainkan” + penandaan saat nada diputar

## Prinsip

- **Tidak ada design baru.** Layar mengikuti `S6-alat-musik.png`.
- **Read-only.** S6 hanya menampilkan; pemilihan alat tetap di katalog AR.
- **Satu titik tandai.** Semua jalur bunyi (note bar, pad, tap 3D) lewat satu
  fungsi yang sama, jadi status tidak bisa “setengah” tercatat.
- **Tanpa dependency baru.** Progres memakai penyimpanan lokal yang sudah
  dipakai untuk alat terakhir.

## Arsitektur

```
data/
├── CollectionProgress.kt   # nilai + operasi tandai (murni, mudah diuji)
└── InstrumentStore.kt      # simpan/baca id yang sudah dimainkan
ui/
├── CollectionScreen.kt     # S6: panel, grid 3×2, badge
├── HomeScreen.kt           # aktifkan tombol “Alat Musik”
└── ar/
    └── ArScreen.kt         # tandai saat playNote berhasil
MainActivity.kt             # navigasi Home ↔ Collection ↔ AR
```

Boundary tetap: `ui → data`; layar koleksi tidak menyentuh `ar/` atau `audio/`.

## Model progres

- `CollectionProgress(playedIds: Set<String>)`
  - `markPlayed(id)` mengembalikan nilai baru; idempoten.
  - `isPlayed(id)` untuk badge.
- `InstrumentStore`:
  - `playedInstrumentIds: Set<String>` dibaca dari penyimpanan lokal.
  - `markPlayed(id)` menambah id tanpa menghapus yang lain.
  - StringSet selalu disalin (`toSet()`) sebelum diubah.

Penandaan hanya dilakukan di `ArScreen.playNote()` setelah
`notePlayer.play(note)` dipanggil dan state player `Ready`. Memilih alat,
membuka katalog, membuka Info, atau memuat model tidak memanggil `playNote`.

## Layar S6

Mengikuti `S6-alat-musik.png`:

- Latar ilustrasi yang sama dengan Beranda.
- Tombol tutup X di pojok kanan atas **di dalam panel** (≥48dp), kembali ke Beranda.
- Panel tengah maks 720dp, tinggi ±84%, radius 20, padding 24.
- Plakat **Alat Musik** di atas panel.
- Subjudul: “Enam alat gamelan — badge hijau berarti sudah kamu mainkan di AR”.
- Grid 3 kolom × 2 baris:
  - kartu: bidang krem berisi ikon alat, nama script Caveat, asal “Jawa”,
    badge status;
  - badge sudah: hijau `Success` + centang;
  - badge belum: terang `Background` + ikon putar.
- Kartu tidak clickable.
- Pada layar pendek, isi grid dapat digulir di dalam panel; header tetap.
- Tidak ada chip laras, tombol AR, atau aksi lain.

## Navigasi

- `HomeScreen(onStart, onOpenCollection)`.
- `MainActivity`: `Screen.Home`, `Screen.Ar`, `Screen.Collection`.
- Beranda → Alat Musik → S6; Back S6 → Beranda.
- S6 tidak mengubah alat terakhir dan tidak membuka AR.

## File yang disentuh

```
app/src/main/java/com/gemaar/data/CollectionProgress.kt
app/src/main/java/com/gemaar/data/InstrumentStore.kt
app/src/main/java/com/gemaar/ui/CollectionScreen.kt
app/src/main/java/com/gemaar/ui/HomeScreen.kt
app/src/main/java/com/gemaar/MainActivity.kt
app/src/main/java/com/gemaar/ui/ar/ArScreen.kt
app/src/test/java/com/gemaar/data/CollectionProgressTest.kt
docs/design/SCREENS.md
docs/design/UI/exports/README.md
docs/design/UI/exports/verification/F06-*.png
docs/specs/F-06-collection/tasks.md
docs/specs/F-06-collection/converge.md
feature_list.json
progress.md
```

## Verifikasi

```bash
export JAVA_HOME=/usr/lib/jvm/temurin-25-jdk-amd64
./gradlew assembleDebug
./gradlew testDebugUnitTest
./gradlew lintDebug
./gradlew installDebug
bash init.sh --check
bash scripts/verify-docs.sh
```

Manual di Tab S9:

1. Beranda → Alat Musik → S6 tampil 6 kartu.
2. Back → Beranda.
3. Mainkan satu nada Saron di AR → keluar → Alat Musik: Saron hijau.
4. Hanya Saron yang berubah; 5 alat lain tetap “Belum dimainkan”.
5. Tutup aplikasi, buka lagi → status Saron tetap.
6. Pilih alat lain tanpa memainkan nada → statusnya tetap belum.
7. Mainkan alat kedua → dua kartu hijau.
8. Mainkan semua alat → enam kartu hijau.
9. Buka-tutup S6 10× → tidak crash.
10. Layar pendek/HP → grid tetap terbaca dan bisa digulir.

## Risiko

- StringSet di penyimpanan lokal bersifat mutable; wajib salin sebelum diubah.
- Menandai terlalu awal (mis. saat pilih alat) membuat badge bohong; tandai
  hanya di `playNote` setelah audio siap.
- Nama “Gong & Kempul” dan “Gender Barung” panjang; cek script tidak terpotong.
- Grid 3×2 di HP landscape bisa lebih tinggi dari layar; sediakan scroll.
- Penyimpanan gagal tulis tidak boleh membuat crash; status bisa kembali belum.
- Jangan mengubah alur katalog/AR; F-06 hanya menambah navigasi Beranda dan
  satu panggilan tandai di `playNote`.
