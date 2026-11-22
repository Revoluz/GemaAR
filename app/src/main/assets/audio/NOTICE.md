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
