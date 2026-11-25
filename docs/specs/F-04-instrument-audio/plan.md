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
| Gender Barung | `GENDER/GBPL*` | model 14 bilah, 2 baris; bilah 4 memakai `GBPL3`/`GBPL3h` |
| Slenthem | `GENDER/GSPL1–7` | 7 bilah, oktaf rendah |
| Gong & Kempul | `GONG/GA`, `GKPL1–7`, `GS` | gong ageng, kempul, suwukan |
| Kendang | `KENDHANG AGENG/DHA`, `CIBLON/*`, `KETIPUNG/*` | 7 pukulan unik |

Pemrosesan sama seperti F-02: potong ±2,5 s, mono 44,1 kHz, normalisasi,
fade-out, OGG <300 KB. Script diperluas menjadi
`scripts/process_instrument_audio.py` dengan manifest sumber per alat.

## Pemetaan nada

| Alat | Kontrol | Pemetaan tap |
|---|---|---|
| Bonang | 2 baris × 6 pencon (1–6, 1h–6h) | pusat 2D pencon dari mesh |
| Gender Barung | 2 baris × 7 bilah | pusat 2D bilah; bilah 4 → sample nada 3 |
| Slenthem | 1 baris × 7 bilah | pusat X bilah dari mesh |
| Gong & Kempul | pad per gong/kempul | pusat X tiap gong |
| Kendang | 7 pad pukulan | pad UI; tap model tidak wajib |

Keputusan Gender: CDM tidak punya `GBPL4` (subset pathet Pelog). Bilah nada 4
memakai sample nada 3 (`GBPL3` untuk oktaf bawah, `GBPL3h` untuk oktaf atas)
sebagai tetangga terdekat. Label UI tetap “4”; keterbatasan dicatat di NOTICE
dan converge. Ini keputusan sementara, bukan sample asli nada 4.

## Kontrol UI

- `InstrumentControls` memilih bentuk:
  - 1 baris 7 nada (Saron, Slenthem),
  - 2 baris 6/7 nada (Bonang, Gender),
  - pad berlabel (Gong/Kempul, Kendang).
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
- Bonang/Gender 2 baris → pusat pencon/bilah harus diukur; jangan pakai lebar
  model seperti bug Saron.
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
