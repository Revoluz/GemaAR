# Plan: F-03 — Katalog alat musik + pilih dari daftar

**Input:** `spec.md`
**Status:** draft
**Scope:** S2 Katalog (overlay), S4 model swap, persistence

## Prinsip

F-03 dibuat sederhana untuk tim pemula:

- **tanpa JSON / parser** — daftar alat ditulis langsung di Kotlin;
- **tanpa repository berlapis** — satu object `Catalog`;
- **ikon PNG** — pakai ilustrasi alat yang diperkecil, bukan Canvas rumit;
- **satu file per topik** — mudah dibaca dan di-review.

## Arsitektur

```
data/
├── Instrument.kt       # data class
├── Catalog.kt          # listOf 6 alat (ditulis manual)
└── InstrumentStore.kt  # SharedPreferences: alat terakhir + flag first-run
ui/ar/
├── CatalogOverlay.kt   # S2: 6 kartu, pilih, close
└── ArScreen.kt         # state katalog + wiring
ar/
└── ArController.kt     # model mengikuti Instrument
```

Tidak ada `CatalogRepository`, `CatalogResult`, `catalog.json`, atau state
loading/error katalog. Data statis, jadi tidak ada proses memuat.

## Data

`data/Instrument.kt`:

```kotlin
data class Instrument(
    val id: String,
    val name: String,
    val origin: String,
    val modelAsset: String,
    @DrawableRes val iconRes: Int,
    val audioAvailable: Boolean,
)
```

`data/Catalog.kt`:

```kotlin
object Catalog {
    val instruments = listOf(
        Instrument("saron", "Saron", "Jawa", "models/saron.glb", R.drawable.ikon_saron, true),
        // 5 alat lain ditambahkan di sini
    )

    val saron = instruments.first { it.id == "saron" }

    fun byId(id: String?): Instrument =
        instruments.firstOrNull { it.id == id } ?: saron
}
```

Enam id: `gong_kempul`, `bonang`, `saron`, `gender_barung`, `slenthem`, `kendang`.
`audioAvailable = true` hanya Saron sampai F-04.

## Ikon

Gunakan `docs/design/UI/exports/assets/alat-*.png`, perkecil ke ±256 px, simpan
sebagai:

```
app/src/main/res/drawable-nodpi/ikon_gong_kempul.png
app/src/main/res/drawable-nodpi/ikon_bonang.png
app/src/main/res/drawable-nodpi/ikon_saron.png
app/src/main/res/drawable-nodpi/ikon_gender_barung.png
app/src/main/res/drawable-nodpi/ikon_slenthem.png
app/src/main/res/drawable-nodpi/ikon_kendang.png
```

Kartu katalog memakai `Image(painterResource(iconRes), ContentScale.Fit)`.

## Teknis

| Bagian | Keputusan |
|---|---|
| Data | `Catalog` statis; tidak ada loading/error |
| Persistence | `InstrumentStore` SharedPreferences: `selected_instrument`, `catalog_auto_opened` |
| Default | `Catalog.saron`; id tak dikenal → Saron |
| First run | Setelah Petunjuk ditutup, buka katalog sekali; set flag |
| Judul AR | `selectedInstrument.name` |
| Model | `ArController(instrument)`, `key(instrument.id, retryToken)` |
| Note bar | Hanya jika `instrument.audioAvailable` |
| Tap model | Hanya audio jika `audioAvailable` |
| Katalog overlay | grid 2 baris × 3 kartu; selected border emas; close X |
| Pilih | Simpan id → tutup overlay |
| Back | Katalog → Petunjuk → keluar |
| Home | **Alat Musik** tetap nonaktif (F-06) |

## File yang disentuh

```
app/src/main/java/com/gemaar/data/Instrument.kt
app/src/main/java/com/gemaar/data/Catalog.kt
app/src/main/java/com/gemaar/data/InstrumentStore.kt
app/src/main/java/com/gemaar/ui/ar/CatalogOverlay.kt
app/src/main/java/com/gemaar/ui/ar/ArScreen.kt
app/src/main/java/com/gemaar/ar/ArController.kt
app/src/main/res/drawable-nodpi/ikon_*.png
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
1. First run → Petunjuk → tutup → Katalog terbuka otomatis.
2. Pilih Bonang → overlay tutup, model Bonang di marker, judul “Bonang”.
3. Pilih alat lain → model berganti tanpa scan ulang.
4. Pilih Saron kembali → note bar dan audio Saron muncul.
5. Alat non-Saron → pesan “Audio belum tersedia”, tap tidak memutar Saron.
6. Tutup aplikasi → buka → Mulai → alat terakhir aktif.
7. Buka katalog saat marker hilang → tetap bisa dipilih.
8. Back saat katalog terbuka → katalog tertutup, AR tetap.

## Risiko

- Model Gong 3,8 MB dan Slenthem 1,7 MB → loading lebih lama; progress F-01
  sudah ada, tidak perlu state baru.
- Model lain mungkin punya tabuh yang menutupi; audio belum aktif, jadi tidak
  memblokir F-03.
- Data statis selalu berisi 6 alat; tidak ada state kosong/error katalog.
- `ArScreen` sudah besar; katalog overlay dipisah ke file sendiri.
- Ikon PNG harus diperkecil agar APK tidak membengkak.

## Constitution check

- [x] Simplicity: tanpa dependency baru, tanpa JSON/parser.
- [x] Boundary: UI → data/ar; AR tidak baca katalog.
- [x] Satu sumber data: `Catalog.kt`.
- [x] Tidak ada backend.
- [x] Tidak ada fitur F-04–F-07 di F-03.
