# Plan: F-05 — Kartu info budaya tiap alat

**Input:** `spec.md`
**Status:** approved
**Scope:** panel Info sisi kanan + isi budaya 6 alat; AR tetap aktif

## Prinsip

- **Tidak ada design baru.** Panel mengikuti `S5-kartu-budaya.png`, `DESIGN.md`,
  `SCREENS.md`, dan `COMPONENTS.md`.
- **Tidak ada jaringan.** Naskah budaya disimpan bersama aplikasi; panel tidak
  menunggu unduhan.
- **Tidak mengubah audio/AR.** Membuka panel tidak boleh mengganti alat,
  memuat ulang model, atau menghentikan suara.
- **Satu sumber teks.** Naskah 6 alat hidup di satu peta data, bukan tersebar
  di composable.

## Arsitektur

```
data/
├── CultureCard.kt          # model teks budaya satu alat
└── CultureCardMap.kt       # 6 kartu, key = instrument id
ui/
├── components/
│   └── InfoPanel.kt        # panel kanan: judul, subjudul, body scroll, kredit, close
└── ar/
    └── ArScreen.kt         # state infoVisible, aktifkan tombol Info, BackHandler
```

Boundary tetap: `ui → data`; panel tidak menyentuh `ar/` atau `audio/`.

## Model data

`CultureCard`:

| Field | Isi | Contoh |
|---|---|---|
| `instrumentId` | id alat, sama dengan `Catalog` | `bonang` |
| `origin` | subjudul asal | `Jawa Tengah — gamelan Jawa` |
| `formAndMaterial` | paragraf bentuk & bahan | gong kecil berpencon di rangka kayu |
| `playing` | paragraf cara main | dipukul tabuh berlapis kain |
| `role` | paragraf peran | memberi isyarat dan pola imbal |
| `sourceCredit` | kredit singkat | `Sumber: UNESCO 2021; Wikipedia` |

`CultureCardMap.forInstrument(id): CultureCard?` mengembalikan `null` untuk id
yang tidak dikenal; UI menampilkan pesan “Info belum tersedia”.

Naskah mengikuti **Content requirements** di `spec.md`; fakta tidak boleh
bertentangan dengan rujukan di bagian **Sumber**.

## Panel Info (S5)

Mengikuti export `S5-kartu-budaya.png`:

- Posisi: sisi kanan, lebar 34% (maks 420dp), tinggi ±80%, margin 32 ke tepi.
- Latar `surface`, tepi ukir, radius 20, shadow lembut, padding 24.
- Judul: nama alat, font script Caveat 20/26 emas.
- Subjudul: asal alat, caption muted, rata kanan.
- Body: tiga paragraf (bentuk & bahan, cara main, peran), Nunito 18/28 weight
  700, rata kanan, `verticalScroll`.
- Kredit sumber kecil di bawah body.
- Badge **Kartu Budaya** dan tombol tutup X di kanan atas (target ≥48dp).
- Motion masuk/keluar 180ms ease-out.
- **Tidak** menampilkan chip Pelog/Slendro dari gambar acuan.

## State & navigasi

- `infoVisible` state lokal di `ArScreen`; default `false`.
- Tombol Info di rail kiri diaktifkan; `contentDescription` “Info alat”.
- `BackHandler` urutan: Info → Katalog → Petunjuk → dialog keluar → back.
- Membuka Katalog atau Petunjuk menutup panel Info lebih dulu.
- Panel tidak mengubah `chromeVisible`; note bar/pad tetap terlihat dan AR tetap
  berjalan di belakang panel.
- Panel bisa dibuka saat marker hilang, model loading, atau audio gagal.

## File yang disentuh

```
app/src/main/java/com/gemaar/data/CultureCard.kt
app/src/main/java/com/gemaar/data/CultureCardMap.kt
app/src/main/java/com/gemaar/ui/components/InfoPanel.kt
app/src/main/java/com/gemaar/ui/ar/ArScreen.kt
app/src/test/java/com/gemaar/data/CultureCardMapTest.kt
docs/design/UI/exports/README.md
docs/design/UI/exports/verification/F05-*.png
docs/specs/F-05-culture-card/tasks.md
docs/specs/F-05-culture-card/converge.md
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

1. AR dengan Bonang → tekan Info → panel kanan terbuka, judul “Bonang”.
2. Baca 4 topik; body bisa digulir; tombol X tetap terlihat.
3. Tutup X → kembali ke AR; audio/nada tetap seperti sebelum panel dibuka.
4. Back saat panel terbuka → panel tertutup, AR tidak keluar.
5. Ulangi untuk Saron, Slenthem, Gender, Gong/Kempul, Kendang.
6. Buka Info saat marker hilang → panel tetap terbuka.
7. Mainkan nada, buka Info → nada berhenti? **Tidak boleh**; panel tidak
   menyentuh player.
8. Ganti alat lewat Katalog → buka Info → isi mengikuti alat baru.
9. Buka-tutup panel 10× cepat → tidak ada panel ganda/crash.

## Risiko

- Teks panjang di layar pendek → body scroll; pastikan tombol tutup tidak ikut
  tergulir.
- Rata kanan wajib menurut desain, tetapi paragraf panjang bisa sulit dibaca;
  jaga kalimat pendek.
- Panel 34% di HP landscape bisa sempit; jangan memaksa teks 18/28 jika
  membuat 2–3 kata per baris — verifikasi di Tab S9 dan HP.
- Konten budaya rawan salah kutip; setiap paragraf harus bisa ditelusuri ke
  sumber di `spec.md`.
- Panel dan note bar sama-sama di bawah; cek tidak saling menutup di layar
  compact.
- Tombol Info saat marker hilang: HUD tetap ada, tetapi pastikan tidak ada
  regresi pada overlay izin kamera/ARCore.
