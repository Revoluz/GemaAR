#!/usr/bin/env python3
"""Download and process the seven Saron Pelog samples for GemaAR F-02.

Source: CDM Gamelan Sample Library (Digitopia / Casa da Música)
License: Artistic License 2.0
https://github.com/Digitopia/CDM-GAMELAN-SAMPLE-LIBRARY

Only the seven used WAV files are downloaded to a temporary directory. The
repository keeps the processed OGG files and the license/notice, not the raw
recordings.
"""

from __future__ import annotations

import math
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
ASSET_DIR = ROOT / "app/src/main/assets/audio"
RAW_BASE = (
    "https://raw.githubusercontent.com/Digitopia/"
    "CDM-GAMELAN-SAMPLE-LIBRARY/master/"
)
SOURCE_DIR = (
    "Gamelao_CdM/WAV/Gamela\u0303o da Casa Da Mu\u0301sica - Porto, Portugal/"
    "SARON"
)
LICENSE_URL = (
    "https://raw.githubusercontent.com/Digitopia/"
    "CDM-GAMELAN-SAMPLE-LIBRARY/master/LICENSE"
)
DURATION_SECONDS = 2.5
FADE_SECONDS = 0.10
TARGET_PEAK = 10 ** (-1 / 20)  # -1 dBFS
OGG_QUALITY = "4"


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
    max_samples = int(DURATION_SECONDS * sample_rate)
    samples = samples[:max_samples].copy()

    peak = float(np.max(np.abs(samples))) if samples.size else 0.0
    if peak > 0:
        samples *= TARGET_PEAK / peak

    fade_samples = min(int(FADE_SECONDS * sample_rate), samples.size)
    if fade_samples > 0:
        fade = np.linspace(1.0, 0.0, fade_samples, dtype=np.float32)
        samples[-fade_samples:] *= fade

    with tempfile.NamedTemporaryFile(suffix=".wav", delete=False) as tmp:
        temp_path = Path(tmp.name)
    try:
        write_wave(temp_path, samples, sample_rate)
        destination.parent.mkdir(parents=True, exist_ok=True)
        subprocess.run(
            [
                "ffmpeg",
                "-y",
                "-loglevel",
                "error",
                "-i",
                str(temp_path),
                "-c:a",
                "libvorbis",
                "-q:a",
                OGG_QUALITY,
                str(destination),
            ],
            check=True,
        )
    finally:
        temp_path.unlink(missing_ok=True)

    seconds = samples.size / sample_rate
    size_kb = destination.stat().st_size / 1024
    print(f"{destination.name}: {seconds:.2f}s, {size_kb:.1f} KB")


def main() -> None:
    if shutil.which("ffmpeg") is None:
        raise SystemExit("ffmpeg is required")

    ASSET_DIR.mkdir(parents=True, exist_ok=True)
    download(LICENSE_URL, ASSET_DIR / "LICENSE-ARTISTIC-2.0.txt")

    with tempfile.TemporaryDirectory(prefix="gemaar-saron-") as tmp:
        tmp_dir = Path(tmp)
        for note in range(1, 8):
            name = f"SBPL{note}.wav"
            source_url = RAW_BASE + urllib.parse.quote(f"{SOURCE_DIR}/{name}")
            source = tmp_dir / name
            print(f"downloading {name}")
            download(source_url, source)
            process(source, OUTPUT_DIR / f"saron_pelog_{note}.ogg")


if __name__ == "__main__":
    main()
