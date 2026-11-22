# Panduan Kerja F-03 — untuk pemula

Panduan ini menjelaskan **urutan kerja**, **file yang disentuh**, dan **cara
memeriksa** setiap langkah. Kerjakan satu langkah, build, lalu lanjut.

> Aturan: jangan mengerjakan bagian temanmu. Kalau perlu mengubah file yang sama
> (`Catalog.kt`, `ArScreen.kt`), lakukan **berurutan**, bukan bersamaan.

---

## Persiapan

Dari root project:

```bash
export JAVA_HOME=/usr/lib/jvm/temurin-25-jdk-amd64
./gradlew assembleDebug
./gradlew testDebugUnitTest
```

Kalau dua perintah ini hijau, kamu siap mulai.

---

# Bagian A — kamu

## A1. Siapkan 3 ikon

Ambil file dari `docs/design/UI/exports/assets/`:

```
alat-saron.png          → ikon_saron.png
alat-bonang.png         → ikon_bonang.png
alat-slenthem.png       → ikon_slenthem.png
```

Perkecil ke ±256 px supaya APK tidak besar. Contoh perintah:

```bash
python3 - <<'PY'
from PIL import Image
from pathlib import Path

src = Path('docs/design/UI/exports/assets')
dst = Path('app/src/main/res/drawable-nodpi')
for source, target in [
    ('alat-saron.png', 'ikon_saron.png'),
    ('alat-bonang.png', 'ikon_bonang.png'),
    ('alat-slenthem.png', 'ikon_slenthem.png'),
]:
    image = Image.open(src / source).convert('RGBA')
    image.thumbnail((256, 256))
    image.save(dst / target)
    print(target, image.size)
PY
```

Cek: nama file **huruf kecil, garis bawah**, bukan `ikon-saron.png`.

## A2. Buat `Instrument.kt`

File: `app/src/main/java/com/gemaar/data/Instrument.kt`

Buat data class:

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

Import `androidx.annotation.DrawableRes`.

## A3. Buat `Catalog.kt`

File: `app/src/main/java/com/gemaar/data/Catalog.kt`

Isi dengan **3 alat dulu**:

```kotlin
object Catalog {
    val instruments = listOf(
        Instrument("saron", "Saron", "Jawa", "models/saron.glb", R.drawable.ikon_saron, true),
        Instrument("bonang", "Bonang", "Jawa", "models/bonang.glb", R.drawable.ikon_bonang, false),
        Instrument("slenthem", "Slenthem", "Jawa", "models/slenthem.glb", R.drawable.ikon_slenthem, false),
    )

    val saron: Instrument = instruments.first { it.id == "saron" }

    fun byId(id: String?): Instrument =
        instruments.firstOrNull { it.id == id } ?: saron
}
```

Cek: `audioAvailable` hanya `true` untuk Saron.

## A4. Buat `CatalogOverlay.kt`

File: `app/src/main/java/com/gemaar/ui/ar/CatalogOverlay.kt`

Struktur yang disarankan:

```
Box (scrim gelap, isi layar)
└── Box/Column panel
    ├── PlaqueTitle("Katalog")
    ├── IconButton close (X)
    └── Column
        ├── Row: 3 kartu
        └── Row: 3 kartu
```

Kartu (`InstrumentCard`) berisi:

- `Image(painterResource(instrument.iconRes))`
- nama alat
- asal (“Jawa”)
- border emas kalau `selected`
- `clickable { onSelect(instrument) }`

Jangan tampilkan badge “Sudah/Belum dimainkan”.

Parameter yang disarankan:

```kotlin
@Composable
fun CatalogOverlay(
    instruments: List<Instrument>,
    selectedId: String,
    onSelect: (Instrument) -> Unit,
    onClose: () -> Unit,
)
```

## A5. Ubah `ArController` agar menerima alat

File: `app/src/main/java/com/gemaar/ar/ArController.kt`

Sekarang model masih tetap `SARON_MODEL`. Ubah:

```kotlin
fun ArController(
    instrument: Instrument,
    // parameter lain tetap
)
```

Lalu:

```kotlin
val modelState = key(instrument.id, retryToken) {
    rememberModelLoadState(modelLoader, instrument.modelAsset)
}
```

Cek: ganti `instrument` → model berganti, marker tidak discan ulang.

## A6. Wiring `ArScreen`

File: `app/src/main/java/com/gemaar/ui/ar/ArScreen.kt`

Tambahkan state:

```kotlin
var catalogVisible by remember { mutableStateOf(false) }
var selectedInstrument by remember { mutableStateOf(Catalog.saron) }
```

Yang harus dilakukan:

1. Tombol **Katalog** di HUD: `enabled = true`, `onClick = { catalogVisible = true }`.
2. Tampilkan `CatalogOverlay` di atas AR.
3. Saat kartu dipilih:
   ```kotlin
   selectedInstrument = instrument
   catalogVisible = false
   notePlayer.stopAll()
   ```
4. Judul HUD memakai `selectedInstrument.name` (kecuali saat
   `ModelLoading`, tetap “Memuat model”).
5. Note bar hanya jika `selectedInstrument.audioAvailable`; kalau tidak,
   jangan tampilkan `NoteBarSection`.
6. Jika tidak ada audio:
   ```kotlin
   Text("Audio belum tersedia untuk alat ini")
   ```
7. `BackHandler` urutan:
   - kalau `catalogVisible` → tutup katalog
   - kalau `petunjukVisible` → tutup petunjuk
   - kalau `arStarted` → dialog keluar
8. `ArController(instrument = selectedInstrument)`.

Sementara persistence belum ada; pakai state di atas dulu.

## A7. Build dan tes

```bash
./gradlew assembleDebug
```

Uji di tablet:

1. Mulai → AR.
2. Buka Katalog → 3 kartu.
3. Pilih Bonang → model Bonang muncul, judul “Bonang”.
4. Pilih Slenthem → model Slenthem muncul.
5. Pilih Saron → note bar + audio kembali.
6. Pilih Bonang → pesan audio, tidak ada suara Saron.

Kalau hijau, tulis handoff di `progress.md`:

```markdown
## Handoff — A → B, F-03
- Selesai: Instrument, Catalog (3 alat), CatalogOverlay, ArController(instrument), wiring A
- Belum: 3 alat, ikon, InstrumentStore, first-run
- File: data/Instrument.kt, data/Catalog.kt, ui/ar/CatalogOverlay.kt, ui/ar/ArScreen.kt, ar/ArController.kt
- Catatan: selectedInstrument masih state sementara
```

---

# Bagian B — temanmu

## B1. Siapkan 3 ikon

Sama seperti A1, untuk:

```
alat-gender-barung.png → ikon_gender_barung.png
alat-gong-kempul.png   → ikon_gong_kempul.png
alat-kendang.png       → ikon_kendang.png
```

## B2. Tambah 3 alat ke `Catalog.kt`

Tambahkan ke `listOf`:

```kotlin
Instrument("gong_kempul", "Gong & Kempul", "Jawa", "models/gong_kempul.glb", R.drawable.ikon_gong_kempul, false),
Instrument("gender_barung", "Gender Barung", "Jawa", "models/gender_barung.glb", R.drawable.ikon_gender_barung, false),
Instrument("kendang", "Kendang", "Jawa", "models/kendang.glb", R.drawable.ikon_kendang, false),
```

Total harus **6 alat**. Cek id unik.

## B3. Buat `InstrumentStore.kt`

File: `app/src/main/java/com/gemaar/data/InstrumentStore.kt`

Gunakan SharedPreferences yang sama dengan `PetunjukStore`:

```kotlin
class InstrumentStore(context: Context) {
    private val prefs =
        context.getSharedPreferences("gemaar_preferences", Context.MODE_PRIVATE)

    var selectedInstrumentId: String?
        get() = prefs.getString("selected_instrument", null)
        set(value) = prefs.edit().putString("selected_instrument", value).apply()

    var catalogAutoOpened: Boolean
        get() = prefs.getBoolean("catalog_auto_opened", false)
        set(value) = prefs.edit().putBoolean("catalog_auto_opened", value).apply()
}
```

## B4. Persistence + first-run di `ArScreen`

File: `app/src/main/java/com/gemaar/ui/ar/ArScreen.kt`

1. Buat `val instrumentStore = remember(context) { InstrumentStore(context) }`.
2. Inisialisasi:
   ```kotlin
   var selectedInstrument by remember {
       mutableStateOf(Catalog.byId(instrumentStore.selectedInstrumentId))
   }
   ```
3. Saat memilih alat:
   ```kotlin
   selectedInstrument = instrument
   instrumentStore.selectedInstrumentId = instrument.id
   catalogVisible = false
   notePlayer.stopAll()
   ```
4. Setelah Petunjuk ditutup:
   ```kotlin
   if (!instrumentStore.catalogAutoOpened) {
       catalogVisible = true
       instrumentStore.catalogAutoOpened = true
   }
   ```
   Simpan flag **segera saat katalog dibuka**, supaya tidak terbuka dua kali.

## B5. Tes 6 alat

1. First run: hapus data aplikasi → Mulai → Petunjuk → tutup → Katalog otomatis.
2. Pilih tiap alat → model berganti tanpa scan ulang.
3. Tutup/buka aplikasi → Mulai → alat terakhir.
4. Alat non-Saron → pesan audio, tidak ada suara Saron.
5. Kembali ke Saron → note bar + audio.
6. Buka katalog saat marker hilang → tetap bisa pilih.
7. Back saat katalog terbuka → katalog tutup, AR tetap.

## B6. Handoff ke agent

Tulis di `progress.md`:

```markdown
## Handoff — B → agent, F-03
- Selesai: 3 alat, ikon, InstrumentStore, first-run
- Sudah diuji: 6 kartu, last instrument, first-run, non-Saron
- Belum/meragukan: ...
- File: ...
```

---

# Bagian R — agent

Agent akan:

1. Review kode dan boundary.
2. Jalankan build/test/lint/init/verify-docs.
3. Uji device lengkap.
4. Perbaiki temuan.
5. Converge F-03, `passes: true`, commit/push.

---

## Kesalahan yang sering terjadi

| Jangan | Lakukan |
|---|---|
| Memakai JSON/parser | Tulis daftar di `Catalog.kt` |
| Menampilkan badge progres | Tidak ada badge di F-03 |
| Note bar untuk Bonang | Note bar hanya jika `audioAvailable` |
| Memakai sample Saron untuk alat lain | Hanya Saron yang bersuara di F-03 |
| Mengganti model tanpa `key(instrument.id)` | Pakai `key` agar model dimuat ulang |
| Katalog terbuka dua kali | Simpan flag first-run segera |
| Lupa `stopAll()` saat ganti alat | Hentikan suara lama |
| Mengaktifkan tombol Alat Musik di Beranda | Tetap nonaktif sampai F-06 |
| Scan ulang marker | Sesi AR tidak boleh dibuat ulang |
