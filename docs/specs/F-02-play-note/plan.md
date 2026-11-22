# Plan: F-02 — Ketuk alat musik untuk membunyikan nada

**Input:** `spec.md`
**Status:** draft
**Scope:** S4 (tambahan), audio Saron

## Scope teknis

| Bagian | F-02 | Ditunda |
|---|---|---|
| S4 AR | tap model → nada, note bar 1–7, label/highlight | scale selector (F-04) |
| Audio | 7 sampel Saron Pelog, pemutar audio pendek | volume app (F-07) |
| Model | `saron.glb` tanpa tabuh (Object_109 + Object_112 dihapus) | animasi tabuh |
| Data | Note + NoteMap Saron di kode | `catalog.json` multi-alat (F-03) |
| Umpan balik | highlight tombol + label | getar, glow bilah 3D |

## Arsitektur

```
ui/ar/ArScreen
├── ArController ──► tap model → normalizedX
│                      └── SaronNoteMap.noteFor(x) → Note
├── NoteBar ────────► NoteKey 1..7
└── NotePlayer (interface)
      └── SoundPoolNotePlayer ──► res/raw/saron_pelog_*.ogg

data/
├── Note.kt
├── SaronNoteMap.kt
└── NotePlaybackState.kt (Loading/Ready/Failed)
```

Boundary:
- `ui/` tidak menyentuh `SoundPool`; hanya `NotePlayer`.
- `ar/` tidak menyentuh audio; hanya mengirim posisi tap.
- `audio/` tidak menyentuh UI/AR.
- Satu arah: `ui → ar/audio → data`.

## Teknis

| Bagian | Keputusan |
|---|---|
| Model playable | `saron.glb` diekspor ulang tanpa tabuh; bilah tetap utuh |
| Sampel | CDM Gamelan Sample Library `SBPL1–7.wav`, Artistic 2.0 |
| Pemrosesan | potong ±2,5 s, mono, normalize −1 dBFS, fade-out 80 ms, OGG q4, target <100 KB |
| Nama aset | `res/raw/saron_pelog_1.ogg` … `saron_pelog_7.ogg` |
| Pemutar | `SoundPool`, maxStreams 8, `USAGE_GAME`, `CONTENT_TYPE_SONIFICATION` |
| Overlap | biarkan sampel sebelumnya berdecay; tidak `stop()` saat nada baru |
| Tap | `ARSceneView.onTouchEvent` + `HitResult.worldPosition`; ACTION_UP, gerak < 12 dp |
| Mapping | `markerPose.inverse().transformPoint(hit.worldPosition).x` dinormalisasi ke 0..6 |
| Batas model | setengah lebar model setelah `scaleToUnits = 0.5f` → ±0,25 m |
| Note bar | 7 tombol, tampil saat `ArUiState.Placed`; nonaktif saat audio Loading/Failed |
| Feedback | tombol menyala ±220 ms, label “Nada N” ±900 ms; tanpa getar |
| Error audio | state `Failed` → pesan “Audio tidak bisa dimuat”, note bar nonaktif |
| Lifecycle | `ON_PAUSE` → `stopAll()`; keluar AR → `release()` |
| Data | `SaronNoteMap` 7 note; F-03 memindahkan ke `catalog.json` |

## Kontrak

```kotlin
data class Note(
    val id: Int,          // 1..7
    val label: String,    // "Nada 1"
    val sampleRes: Int,   // R.raw.saron_pelog_1
)

interface NotePlayer {
    val state: NotePlayerState   // Loading, Ready, Failed
    fun play(note: Note)
    fun stopAll()
    fun release()
}

// pure, mudah diuji
fun noteIndexFor(normalizedX: Float): Int   // 0..6

// ArController
onModelTap: (normalizedX: Float) -> Unit
```

## File yang disentuh

```
app/src/main/java/com/gemaar/
├── ar/ArController.kt          # onTouchEvent + callback tap
├── audio/NotePlayer.kt         # interface + state
├── audio/SoundPoolNotePlayer.kt
├── data/Note.kt
├── data/SaronNoteMap.kt
├── ui/ar/ArScreen.kt           # wiring player + note bar
├── ui/ar/NoteBar.kt            # 7 tombol + highlight
├── assets/models/saron.glb     # diekspor ulang tanpa tabuh
└── res/raw/saron_pelog_1..7.ogg
app/src/test/java/com/gemaar/
├── data/SaronNoteMapTest.kt
└── ar/NoteIndexTest.kt
docs/decisions/0003-gamelan-audio-samples.md
app/src/main/assets/audio/LICENSE-ARTISTIC-2.0.txt
app/src/main/assets/audio/NOTICE.md
```

Catatan: `res/raw` tidak bisa berisi `LICENSE`/`NOTICE` non-audio dengan nama
sembarang? File teks di `res/raw` diperbolehkan, tetapi lebih rapi menaruh
lisensi di `assets/audio/`.

## Model Saron tanpa tabuh

- Objek tabuh: `Object_109` (kepala) dan `Object_112` (gagang).
- Hapus keduanya di Blender, ekspor ulang `saron.glb`; jangan mengubah bilah,
  rangka, atau skala.
- Perbarui juga `docs/design/assets/models/saron.glb` agar sumber dan aset app
  tetap sama.
- Verifikasi: render top view, 7 bilah terlihat; bounding box dan posisi model
  tidak berubah.

## Pemrosesan audio

1. Unduh `SBPL1–7.wav` dari repo CDM.
2. Ambil dari onset, potong 2,5 detik pertama (atau sebelum decay habis).
3. Rata-rata kanal kiri/kanan → mono.
4. Normalisasi peak ke −1 dBFS.
5. Fade-out 80 ms di akhir.
6. Encode OGG Vorbis quality 4, 44,1 kHz, mono.
7. Cek ukuran <100 KB dan durasi ±2,5 s.
8. Simpan `NOTICE.md` + lisensi Artistic 2.0.

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
1. Saron tampil → note bar 1–7 muncul.
2. Tap tiap bilah kiri→kanan → 7 nada berbeda, rendah→tinggi.
3. Tap tombol 1–7 → nada sesuai, tombol menyala, label muncul.
4. Tap cepat 5× → suara tumpang tindih, tidak crash.
5. Marker hilang → note bar hilang, tap tidak berbunyi.
6. Marker kembali → note bar muncul, nada bisa dimainkan.
7. Keluar AR → tidak ada suara tersisa.
8. Audio gagal (uji dengan aset rusak sementara) → pesan + note bar nonaktif.

## Risiko

- Tap mapping meleset kalau model dilihat dari sudut ekstrem → uji 3 sudut;
  kalau perlu, kalibrasi batas X atau tambah 7 hitbox.
- Latency audio pada tablet → ukur; SoundPool seharusnya <100 ms.
- Sampel 7,7–12 detik jika tidak dipotong → file besar; wajib trim.
- Lisensi Artistic 2.0 → wajib sertakan lisensi/atribusi dan tandai modifikasi.
- Model tidak punya node per bilah → highlight 3D tidak dilakukan; hanya note bar.
- Menghapus tabuh mengubah tampilan F-01; pastikan screenshot/preview Saron
  diperbarui dan tidak ada bagian model yang ikut terhapus.
- Audio fokus dengan app lain → F-02 tidak mengambil alih fokus audio; volume
  dan perilaku lanjutan ditunda ke F-07.

## Constitution check

- [x] Simplicity: tanpa dependency baru; SoundPool bawaan Android.
- [x] Boundary: UI → `NotePlayer`/`ArController`; audio/ar tidak saling impor.
- [x] Satu model data: Note + SaronNoteMap.
- [x] Tidak ada backend/database.
- [x] Aset baru berlisensi dan terdokumentasi.
