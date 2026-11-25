# Plan: F-04 — Audio semua alat selain Saron

**Input:** `spec.md`
**Status:** draft
**Scope:** audio + kontrol main untuk Bonang, Gender Barung, Slenthem,
Gong & Kempul, Kendang

## Arsitektur

```
data/
├── Note.kt / Stroke.kt
├── InstrumentAudio.kt        # Notes(rows) atau Strokes
└── InstrumentAudioMap.kt     # per alat
audio/
├── NotePlayer.kt             # kontrak (sudah ada)
└── SoundPoolNotePlayer.kt    # memuat set sample alat aktif
ui/ar/
├── InstrumentControls.kt     # pilih NoteBar / dua baris / pad
├── NoteBar.kt                # generalisasi jumlah nada
└── ArScreen.kt               # gating audio per alat
ar/
├── InstrumentTapMap.kt       # pusat bagian alat → Note/Stroke
└── ArController.kt           # tap → callback
```

Boundary tetap: `ui → audio/ar → data`; audio dan AR tidak saling impor.

## Sumber sample (CDM)

| Alat | Sumber | Catatan |
|---|---|---|
| Bonang | `BONANG/BBPL1–6`, `BBPL1h–6h` | model 12 pencon, 2 baris; 6 bawah + 6 atas |
| Gender Barung | `GENDER/GBPL*` | model 14 bilah satu deret; bilah 4 memakai `GBPL3`/`GBPL3h` |
| Slenthem | `GENDER/GSPL1–7` | 7 bilah, oktaf rendah |
| Gong & Kempul | `GONG/GA`, `GKPL1–7`, `GS` | 9 gong: ageng, kempul 1–7, suwukan; K4 memakai sample K3 |
| Kendang | `KENDHANG AGENG/DHA`, `CIBLON/*`, `KETIPUNG/*` | 7 pukulan unik |

Pemrosesan sama seperti F-02: potong ±2,5 s, mono 44,1 kHz, normalisasi,
fade-out, OGG <300 KB. Script diperluas menjadi
`scripts/process_instrument_audio.py` dengan manifest sumber per alat.

## Pemetaan nada

| Alat | Kontrol | Pemetaan tap |
|---|---|---|
| Bonang | 2 baris × 6 pencon (1–6, 1h–6h) | pusat 2D pencon dari mesh |
| Gender Barung | 1 deret × 14 bilah (1–7, 1h–7h) | pusat X 14 bilah dari mesh; bilah 4 → sample nada 3 |
| Slenthem | 1 baris × 7 bilah | pusat X 7 bilah terukur dari mesh |
| Gong & Kempul | 9 pad (Gong, K1–K7, Suwukan) | pusat X 9 gong dari mesh |
| Kendang | 7 pad pukulan | pad UI; tap model tidak wajib |

Pusat bilah/gong diukur dari GLB dengan ray-cast permukaan atas (resolusi
0,002 m), lalu di-scale dengan `modelScale / dimensiTerbesar`. Pemetaan memakai
pusat terdekat, bukan pembagian lebar model rata. Slenthem: 7 pusat
`-0.322 … 0.310`; Gender: 14 pusat `-0.535 … 0.688`; Gong: 9 pusat
`-1.100 … 1.042`.

Keputusan Gender: CDM tidak punya `GBPL4` (subset pathet Pelog). Bilah nada 4
memakai sample nada 3 (`GBPL3` untuk oktaf bawah, `GBPL3h` untuk oktaf atas)
sebagai tetangga terdekat. Label UI tetap “4”; keterbatasan dicatat di NOTICE
dan converge. Ini keputusan sementara, bukan sample asli nada 4.

Keputusan kontrol Gender: model hanya punya satu deret 14 bilah. Dua baris
7+7 membuat nada terlihat ganda, jadi kontrolnya satu deret 14 tombol. Di HP
lebar tombol mengecil otomatis (min 20 dp), bukan dipecah dua baris.

Keputusan Gong: model punya 9 gong (Gong Ageng di tepi kiri, 7 kempul di
tengah, Suwukan di tepi kanan). CDM tidak punya `GKPL4`, jadi K4 memakai
sample Kempul 3 dan dicatat sebagai keterbatasan sementara.

## Kontrol UI

- `InstrumentControls` memilih bentuk:
  - 1 baris 7 nada (Saron, Slenthem),
  - 1 baris 14 nada (Gender Barung),
  - 2 baris 6 nada (Bonang),
  - pad berlabel (Gong/Kempul 9, Kendang 7).
- Lebar tombol menyesuaikan lebar layar supaya 14 bilah tetap muat di HP.
- Highlight + label tetap seperti F-02.
- Tidak ada pemilih Pelog/Slendro/oktaf.

## Audio player

- `SoundPoolNotePlayer` diperluas untuk memuat `InstrumentAudio` alat aktif.
- Saat ganti alat: `stopAll()` + release set lama + muat set baru.
- State Loading/Ready/Failed per alat.
- Maksimal 8 stream; nada boleh tumpang tindih.

## File yang disentuh

```
app/src/main/java/com/gemaar/data/Stroke.kt
app/src/main/java/com/gemaar/data/InstrumentAudio.kt
app/src/main/java/com/gemaar/data/InstrumentAudioMap.kt
app/src/main/java/com/gemaar/ar/InstrumentTapMap.kt
app/src/main/java/com/gemaar/audio/SoundPoolNotePlayer.kt
app/src/main/java/com/gemaar/ui/ar/InstrumentControls.kt
app/src/main/java/com/gemaar/ui/ar/NoteBar.kt
app/src/main/java/com/gemaar/ui/ar/ArScreen.kt
app/src/main/res/raw/<instrument>_*.ogg
scripts/process_instrument_audio.py
app/src/main/assets/audio/NOTICE.md
CREDITS.md
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

Manual di Tab S9, untuk tiap alat:
1. Pilih alat → model + kontrol muncul.
2. Ketuk semua bagian/pad → bunyi berbeda.
3. Tap cepat 5× → tumpang tindih, tidak crash.
4. Ganti alat saat nada berbunyi → suara lama berhenti.
5. Marker hilang → kontrol hilang, tidak ada nada baru.
6. Keluar AR/background → tidak ada suara tersisa.

## Risiko

- Gender `GBPL4` tidak ada → bilah 4 memakai sample nada 3; bisa terdengar
  tidak tepat dan harus dicatat.
- Kempul `GKPL4` tidak ada → pad K4 memakai sample Kempul 3.
- Pusat bilah/gong harus diukur dari mesh; pembagian lebar model rata membuat
  tap Slenthem meleset (tap bilah 5 berbunyi 4).
- Banyak sample (49+) → waktu muat dan memori; muat per alat, bukan semua.
- Gong/Kempul jumlah dan posisi perlu dihitung dari mesh.
- Kendang bukan alat bernada; pad lebih tepat daripada note bar.
- Model alat lain mungkin punya tabuh yang menutupi bagian; pertimbangkan
  menghapus tabuh seperti Saron jika perlu.
- Urutan implementasi: Slenthem → Bonang → Gender → Gong/Kempul → Kendang.

## Constitution check

- [x] Simplicity: perluas `NotePlayer`/`NoteBar`, bukan framework audio baru.
- [x] Boundary: UI → audio/ar → data.
- [x] Satu sumber sample: CDM + NOTICE/lisensi.
- [x] Tidak ada backend.
- [x] Tidak menambah scale/octave selector yang sudah dihapus.
