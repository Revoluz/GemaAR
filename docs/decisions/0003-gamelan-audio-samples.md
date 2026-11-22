# 0003 — Sampel audio Saron dari CDM Gamelan Sample Library

**Status:** accepted
**Date:** 2026-11-22
**Deciders:** product + engineering

---

## Context

F-02 membutuhkan tujuh sampel nada Saron Pelog. Project belum punya aset audio,
tidak ada backend, dan target MVP memakai suara alat musik asli — bukan sintesis.

Kandidat sumber:
- rekam sendiri (butuh alat Saron fisik dan waktu),
- sampel sintetis (cepat, tetapi tidak otentik),
- library sampel gamelan berlisensi.

## Decision

Pakai **CDM Gamelan Sample Library** karya Digitopia / Casa da Música
(Porto, Portugal), khusus set **Saron Barung Pelog**:

```
SARON/SBPL1.wav … SBPL7.wav
```

Lisensi repo: **Artistic License 2.0**. Sampel akan:
- dipotong menjadi ±2,5 detik,
- dijadikan mono 44,1 kHz,
- dinormalisasi,
- di-encode OGG,
- disertai `LICENSE-ARTISTIC-2.0.txt` dan `NOTICE.md` di aplikasi.

## Alternatives considered

| Option | Pros | Cons | Why rejected |
|---|---|---|---|
| Rekam sendiri | Paling otentik, lisensi milik sendiri | Butuh Saron fisik, mikrofon, waktu | Tidak tersedia untuk sesi ini |
| Sintesis metalik | Tanpa lisensi, cepat | Tidak terdengar seperti gamelan asli | Bertentangan dengan tujuan “mengenal alat musik” |
| Library lain | Mungkin CC0 | Lisensi/kualitas belum diverifikasi | CDM sudah jelas sumber, set, dan lisensinya |

## Consequences

### Positive
- Tujuh nada Pelog asli dari satu gamelan, konsisten satu sama lain.
- Ukuran file kecil setelah diproses; cocok untuk aplikasi offline.
- Tidak ada dependency audio baru.

### Negative
- Wajib mematuhi Artistic 2.0: sertakan lisensi, atribusi, dan tandai modifikasi.
- Sampel asli stereo dan panjang; proses trim/normalisasi harus konsisten.
- Nada Saron asli tidak sama persis dengan tinggi nada model; F-02 hanya
  memetakan urutan, bukan tuning ilmiah.

### Follow-ups
- [ ] Simpan `LICENSE-ARTISTIC-2.0.txt` + `NOTICE.md` di `assets/audio/`
- [ ] Catat perubahan file (trim, mono, OGG) di NOTICE
- [ ] Uji 7 nada di Tab S9
- [ ] F-04 menyiapkan set Slendro dari folder `SBSL*` bila dibutuhkan

## Verification

- Tujuh file audio ada, masing-masing <100 KB.
- Aplikasi memutar nada 1–7 tanpa crash.
- Teks lisensi dan atribusi ikut terpasang di APK.

## Related
- Spec: `docs/specs/F-02-play-note/spec.md`
- Plan: `docs/specs/F-02-play-note/plan.md`
- Source: https://github.com/Digitopia/CDM-GAMELAN-SAMPLE-LIBRARY
