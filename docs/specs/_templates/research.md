# Research: <pertanyaan>

> Sumber: `research` (Matt Pocock).
> **Sumber primer saja**: docs resmi, source code, spesifikasi, API first-party.
> Bukan artikel orang lain tentang sumber itu.
>
> Pakai hanya kalau fitur bergantung pada fakta eksternal yang belum diketahui.
> Kalau jawabannya bisa dibaca dari repo, tidak perlu file ini.

**Feature:** F-XX
**Date:** YYYY-MM-DD
**Question owner:** <nama>

---

## Question

<Satu pertanyaan spesifik. Kalau lebih dari satu, pecah jadi beberapa file.>

## Answer (short)

<2–4 kalimat. Ini yang dibaca orang sibuk.>

## Findings

| # | Klaim | Sumber (primary) | Jenis |
|---|---|---|---|
| 1 | <klaim> | <URL docs resmi / path source code> | docs / code / spec |
| 2 | <klaim> | <URL> | docs |

Aturan:
- Setiap klaim **harus** punya link.
- Kalau tidak bisa dilacak ke sumber primer, tandai `unverified` dan jangan dipakai untuk keputusan.
- Jangan campur opini dengan fakta. Opini diberi label **Rekomendasi**.

## Implications for our code

- <apa artinya untuk modul yang terdampak>
- <batasan versi, config, atau pola yang harus dipakai>

## What this rules out

- <pendekatan yang ternyata tidak bisa>

## Open questions

- <yang masih belum terjawab>

---

## Checklist

- [ ] Semua klaim punya sumber primer
- [ ] Versi library/API disebut
- [ ] Ada bagian "implications" yang spesifik ke repo ini
- [ ] Tidak ada salinan paragraf docs — cukup ringkasan + link
