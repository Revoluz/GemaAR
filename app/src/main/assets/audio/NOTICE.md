# Audio Notice — Saron Pelog

The seven Saron audio samples in `app/src/main/res/raw/saron_pelog_*.ogg`
are derived from the **CDM Gamelan Sample Library**.

## Source

- Repository: https://github.com/Digitopia/CDM-GAMELAN-SAMPLE-LIBRARY
- Collection: *Gamelão da Casa Da Música — Porto, Portugal*
- Instrument set: **Saron Barung Pelog**
- Original files used:
  - `SARON/SBPL1.wav`
  - `SARON/SBPL2.wav`
  - `SARON/SBPL3.wav`
  - `SARON/SBPL4.wav`
  - `SARON/SBPL5.wav`
  - `SARON/SBPL6.wav`
  - `SARON/SBPL7.wav`

## Copyright and license

> Copyright (c) 2014 Digitopia — Casa da Música
>
> Licensed under the Artistic License 2.0.
>
> The full license text is included in
> `app/src/main/assets/audio/LICENSE-ARTISTIC-2.0.txt`.

## Changes made

The original WAV recordings were modified for use in this application:

- trimmed to 2.5 seconds from the attack,
- mixed from stereo to mono,
- peak-normalized to −1 dBFS,
- given a 100 ms fade-out,
- encoded as OGG Vorbis (quality 4, 44.1 kHz, mono).

The derived files are:

```
app/src/main/res/raw/saron_pelog_1.ogg
app/src/main/res/raw/saron_pelog_2.ogg
app/src/main/res/raw/saron_pelog_3.ogg
app/src/main/res/raw/saron_pelog_4.ogg
app/src/main/res/raw/saron_pelog_5.ogg
app/src/main/res/raw/saron_pelog_6.ogg
app/src/main/res/raw/saron_pelog_7.ogg
```

The processing is reproducible with
`scripts/process_saron_audio.py`. Raw WAV files are not committed to this
repository.

## Naming

`SBPL1` is the lowest note and `SBPL7` the highest, matching the left-to-right
key order of the playable Saron model.

---

# F-04 — Instrument audio

The following samples are also derived from the same CDM collection, with the
same processing (2.5 s trim, mono, −1 dBFS, 100 ms fade, OGG Vorbis quality 4,
44.1 kHz).

| App files | Original CDM files | Instrument |
|---|---|---|
| `slenthem_1..7.ogg` | `GENDER/GSPL1..7.wav` | Gender Slenthem (Pelog) |
| `bonang_1..6.ogg` | `BONANG/BBPL1..6.wav` | Bonang barung (low row) |
| `bonang_1h..6h.ogg` | `BONANG/BBPL1h..6h.wav` | Bonang barung (high row) |
| `gender_1,2,3,5,6,7.ogg` | `GENDER/GBPL1,2,3,5,6,7.wav` | Gender Barung (low row) |
| `gender_1h,2h,3h,5h,6h,7h.ogg` | `GENDER/GBPL1h,2h,3h,5h,6h,7h.wav` | Gender Barung (high row) |
| `gong_ageng.ogg` | `GONG/GAf.wav` | Gong ageng |
| `kempul_1,2,3,5,6,7.ogg` | `GONG/GKPL1f,2f,3f,5f,6f,7f.wav` | Kempul |
| `kendang_dha.ogg` | `DRUMS/KENDHANG AGENG/DHA.wav` | Kendang ageng |
| `kendang_dlang,lung,tak,thung,tong.ogg` | `DRUMS/CIBLON_TAMBOR_MEDIO/*.wav` | Kendang ciblon |
| `kendang_dhung.ogg` | `DRUMS/KETIPUNG_TAMBOR PEQUENO/DHUNG.wav` | Kendang ketipung |

## Known sample limitation

CDM does not provide `GBPL4` (Gender Barung Pelog note 4). The app maps Gender
bar 4 to the note-3 sample (`gender_3` / `gender_3h`) as the nearest neighbor.
This is a temporary limitation and is documented in
`docs/specs/F-04-instrument-audio/`.

Kendang uses seven available strokes; it is not a complete two-head kendang
simulation.

Processing is reproducible with `scripts/process_instrument_audio.py`.
