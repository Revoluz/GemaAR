#!/usr/bin/env python3
"""Download and process F-04 instrument samples from CDM.

Source: CDM Gamelan Sample Library (Digitopia / Casa da Música)
License: Artistic License 2.0

Only the used WAV files are downloaded to a temporary directory. The app keeps
the processed OGG files plus the existing license/notice.
"""

from __future__ import annotations

import shutil
import subprocess
import tempfile
import urllib.parse
import urllib.request
import wave
from pathlib import Path

import numpy as np

ROOT = Path(__file__).resolve().parents[1]
OUTPUT_DIR = ROOT / "app/src/main/res/raw"
BASE = (
    "https://raw.githubusercontent.com/Digitopia/"
    "CDM-GAMELAN-SAMPLE-LIBRARY/master/"
)
WAV_ROOT = (
    "Gamelao_CdM/WAV/Gamela\u0303o da Casa Da Mu\u0301sica - Porto, Portugal"
)
DURATION_SECONDS = 2.5
FADE_SECONDS = 0.10
TARGET_PEAK = 10 ** (-1 / 20)
OGG_QUALITY = "4"

# (folder, source filename, output resource name)
SAMPLES = [
    # Slenthem (Gender Slenthem, Pelog)
    *[("GENDER", f"GSPL{n}.wav", f"slenthem_{n}") for n in range(1, 8)],
    # Bonang: 6 low + 6 high
    *[("BONANG", f"BBPL{n}.wav", f"bonang_{n}") for n in range(1, 7)],
    *[("BONANG", f"BBPL{n}h.wav", f"bonang_{n}h") for n in range(1, 7)],
    # Gender Barung: 6 sampled notes x 2 octaves.
    # Note 4 is not in CDM; the app maps bar 4 to note 3.
    *[("GENDER", f"GBPL{n}.wav", f"gender_{n}") for n in (1, 2, 3, 5, 6, 7)],
    *[("GENDER", f"GBPL{n}h.wav", f"gender_{n}h") for n in (1, 2, 3, 5, 6, 7)],
    # Gong: ageng + 6 sampled kempul + suwukan (9 gongs in the model).
    # Kempul 4 is not in CDM; the app maps K4 to the kempul-3 sample.
    ("GONG", "GAf.wav", "gong_ageng"),
    *[("GONG", f"GKPL{n}f.wav", f"kempul_{n}") for n in (1, 2, 3, 5, 6, 7)],
    ("GONG", "GSf.wav", "suwukan"),
    # Kendang: seven unique strokes
    ("DRUMS/KENDHANG AGENG", "DHA.wav", "kendang_dha"),
    ("DRUMS/CIBLON_TAMBOR_MEDIO", "DLANG.wav", "kendang_dlang"),
    ("DRUMS/CIBLON_TAMBOR_MEDIO", "LUNG.wav", "kendang_lung"),
    ("DRUMS/CIBLON_TAMBOR_MEDIO", "TAK.wav", "kendang_tak"),
    ("DRUMS/CIBLON_TAMBOR_MEDIO", "THUNG.wav", "kendang_thung"),
    ("DRUMS/CIBLON_TAMBOR_MEDIO", "TONG.wav", "kendang_tong"),
    ("DRUMS/KETIPUNG_TAMBOR PEQUENO", "DHUNG.wav", "kendang_dhung"),
]


def download(url: str, destination: Path) -> None:
    with urllib.request.urlopen(url, timeout=120) as response:
        destination.write_bytes(response.read())


def read_wave(path: Path) -> tuple[np.ndarray, int]:
    with wave.open(str(path), "rb") as source:
        if source.getsampwidth() != 2:
            raise ValueError(f"{path.name}: only 16-bit PCM is supported")
        channels = source.getnchannels()
        sample_rate = source.getframerate()
        frames = source.readframes(source.getnframes())
    data = np.frombuffer(frames, dtype=np.int16).astype(np.float32) / 32768.0
    if channels > 1:
        data = data.reshape(-1, channels).mean(axis=1)
    return data, sample_rate


def write_wave(path: Path, samples: np.ndarray, sample_rate: int) -> None:
    pcm = np.clip(samples, -1.0, 1.0)
    pcm = (pcm * 32767.0).astype(np.int16)
    with wave.open(str(path), "wb") as target:
        target.setnchannels(1)
        target.setsampwidth(2)
        target.setframerate(sample_rate)
        target.writeframes(pcm.tobytes())


def process(source: Path, destination: Path) -> None:
    samples, sample_rate = read_wave(source)
    samples = samples[: int(DURATION_SECONDS * sample_rate)].copy()
    peak = float(np.max(np.abs(samples))) if samples.size else 0.0
    if peak > 0:
        samples *= TARGET_PEAK / peak
    fade_samples = min(int(FADE_SECONDS * sample_rate), samples.size)
    if fade_samples > 0:
        samples[-fade_samples:] *= np.linspace(
            1.0, 0.0, fade_samples, dtype=np.float32
        )

    with tempfile.NamedTemporaryFile(suffix=".wav", delete=False) as tmp:
        temp_path = Path(tmp.name)
    try:
        write_wave(temp_path, samples, sample_rate)
        destination.parent.mkdir(parents=True, exist_ok=True)
        subprocess.run(
            [
                "ffmpeg", "-y", "-loglevel", "error",
                "-i", str(temp_path),
                "-c:a", "libvorbis", "-q:a", OGG_QUALITY,
                str(destination),
            ],
            check=True,
        )
    finally:
        temp_path.unlink(missing_ok=True)
    print(f"{destination.name}: {destination.stat().st_size / 1024:.1f} KB")


def main() -> None:
    if shutil.which("ffmpeg") is None:
        raise SystemExit("ffmpeg is required")

    with tempfile.TemporaryDirectory(prefix="gemaar-audio-") as tmp:
        tmp_dir = Path(tmp)
        for folder, source_name, output_name in SAMPLES:
            source_path = f"{WAV_ROOT}/{folder}/{source_name}"
            url = BASE + urllib.parse.quote(source_path)
            destination = OUTPUT_DIR / f"{output_name}.ogg"
            if destination.exists():
                print(f"exists {destination.name}")
                continue
            source = tmp_dir / f"{folder.replace(' ', '_').replace('/', '_')}_{source_name}"
            print(f"downloading {folder}/{source_name}")
            download(url, source)
            process(source, destination)


if __name__ == "__main__":
    main()
