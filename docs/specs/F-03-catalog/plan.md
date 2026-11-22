# Plan: F-03 — Katalog alat musik + pilih dari daftar

**Input:** `spec.md`
**Status:** draft
**Scope:** S2 Katalog (overlay), S4 model swap, persistence

## Arsitektur

```
data/
├── Instrument.kt
├── CatalogRepository.kt   ──► assets/catalog.json
└── InstrumentStore.kt     ──► SharedPreferences
ui/ar/
├── CatalogOverlay.kt      # S2
├── InstrumentIcon.kt      # ikon kartu (Canvas)
└── ArScreen.kt            # pilih, first-run, note-bar gating
ar/
└── ArController.kt        # model mengikuti Instrument
```

Boundary:
- `ui/` tidak memuat JSON; lewat `CatalogRepository`.
- `ar/` menerima `Instrument`, tidak membaca katalog.
- `data/` tidak bergantung UI/AR.

## Data katalog

`app/src/main/assets/catalog.json` — skema versi 1:

```json
{
  "schemaVersion": 1,
  "instruments": [
    {
      "id": "saron",
      "name": "Saron",
      "origin": "Jawa",
      "model": "models/saron.glb",
      "icon": "saron",
      "audioAvailable": true
    }
  ]
}
```

Enam entri: `gong_kempul`, `bonang`, `saron`, `gender_barung`, `slenthem`,
`kendang`. `audioAvailable` hanya `true` untuk Saron sampai F-04.

`docs/ARCHITECTURE.md` diperbarui dengan versi skema ini.

## Teknis

| Bagian | Keputusan |
|---|---|
| Parser | `org.json` bawaan Android; `runCatching` → state Error |
| Repository | `CatalogRepository(context).load(): CatalogResult` (Success/Empty/Error) |
| Persistence | `InstrumentStore`: `selected_instrument_id`, `catalog_auto_shown` |
| Default | `saron`; id tak dikenal → fallback Saron |
| First run | Setelah Petunjuk ditutup, buka katalog sekali; set flag |
| Judul AR | `selectedInstrument.name` |
| Model | `ArController(instrument)`, `key(instrument.id, retryToken)` |
| Note bar | Hanya jika `instrument.audioAvailable`; selain itu pesan “Audio belum tersedia” |
| Tap model | Hanya memanggil audio jika `audioAvailable` |
| Ikon | Compose Canvas per `icon` key; tidak memakai ilustrasi besar |
| Katalog overlay | S2: scrim, plaque “Katalog”, close X, grid 3×2 |
| Pilih | Kartu ditekan → simpan → tutup overlay |
| Back | Katalog → Petunjuk → konfirmasi keluar |
| Home | Tombol **Alat Musik** tetap nonaktif (F-06) |

## File yang disentuh

```
app/src/main/assets/catalog.json
app/src/main/java/com/gemaar/data/Instrument.kt
app/src/main/java/com/gemaar/data/CatalogRepository.kt
app/src/main/java/com/gemaar/data/InstrumentStore.kt
app/src/main/java/com/gemaar/ui/ar/CatalogOverlay.kt
app/src/main/java/com/gemaar/ui/ar/InstrumentIcon.kt
app/src/main/java/com/gemaar/ui/ar/ArScreen.kt
app/src/main/java/com/gemaar/ar/ArController.kt
app/src/test/java/com/gemaar/data/InstrumentSelectionTest.kt
docs/ARCHITECTURE.md
```

## Kontrak

```kotlin
data class Instrument(
    val id: String,
    val name: String,
    val origin: String,
    val modelAsset: String,
    val icon: String,
    val audioAvailable: Boolean,
)

sealed interface CatalogResult {
    data class Success(val instruments: List<Instrument>) : CatalogResult
    data object Empty : CatalogResult
    data class Error(val message: String) : CatalogResult
}

class InstrumentStore(context: Context) {
    var selectedInstrumentId: String
    var catalogAutoShown: Boolean
}

// fallback murni, mudah diuji
fun resolveInstrument(instruments: List<Instrument>, storedId: String?): Instrument
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
3. Pilih Gender/Slenthem/Gong/Kendang → model berganti tanpa scan ulang.
4. Pilih Saron kembali → note bar dan audio Saron muncul.
5. Alat non-Saron → pesan “Audio belum tersedia”, tap tidak memutar Saron.
6. Tutup aplikasi → buka → Mulai → alat terakhir aktif.
7. Buka katalog saat marker hilang → tetap bisa dipilih.
8. Back saat katalog terbuka → katalog tertutup, AR tetap.

## Risiko

- Model Gong 3,8 MB dan Slenthem 1,7 MB → loading lebih lama; tampilkan progress.
- Model lain mungkin punya tabuh/geometri yang menutupi; audio belum aktif,
  jadi tidak memblokir F-03. Rapikan di F-04 bila perlu.
- `org.json` tidak diuji di JVM; parser dibundel dan error state menangkap
  kegagalan. Uji device memakai data nyata.
- First-run flag bisa terbuka dua kali jika proses mati sebelum flag tersimpan →
  simpan segera saat katalog dibuka.
- `ArScreen` sudah besar; jangan menambah logika audio alat lain di F-03.

## Constitution check

- [x] Simplicity: tanpa dependency baru; JSON + SharedPreferences.
- [x] Boundary: UI → repository/controller, AR tidak baca katalog.
- [x] Satu sumber data katalog: `assets/catalog.json`.
- [x] Tidak ada backend.
- [x] Tidak ada fitur F-04–F-07 di F-03.
