# Converge: <FEATURE NAME>

> Dua sumbu, mengikuti `code-review` (Matt Pocock): **Spec** dan **Standards**.
> Dinilai terpisah supaya satu sumbu tidak menutupi yang lain.
>
> Aturan: **nilai dulu, perbaiki kemudian.** Jangan gabungkan dua langkah itu.

**Feature ID:** F-XX
**Spec:** `docs/specs/F-XX-<slug>/spec.md`
**Iteration:** 1

---

## Axis 1 — Spec

Apakah kode mengerjakan **apa yang diminta**?

| # | Criteria | Status | Evidence |
|---|---|---|---|
| US1-1 | <criteria> | PASS | `<command>` → `<output>` |
| US1-2 | <criteria> | PASS | screenshot `<path>` |
| US2-1 | <criteria> | FAIL | `<command>` → `<output>` |

Cari juga:

- requirement yang **hilang** atau baru sebagian
- perilaku yang **tidak diminta** (scope creep)
- requirement yang terlihat ada tapi implementasinya salah

## Axis 2 — Standards

Apakah kode mengikuti **aturan repo**?

Sumber aturan: `AGENTS.md`, `docs/constitution.md`.

| # | Aturan | Status | Evidence |
|---|---|---|---|
| S1 | Boundary modul tidak dilanggar | PASS | `<lint/typecheck command>` |
| S2 | Generated code tidak diedit manual | PASS | `<regenerate command>` + `git diff` kosong |
| S3 | Test ada untuk perubahan | FAIL | endpoint logout tanpa test |
| S4 | Tidak ada `any` | PASS | lint |

Jangan laporkan hal yang sudah di-enforce tooling.
Jangan campur temuan Standards ke tabel Spec.

---

## Verdict

| Axis | Hasil |
|---|---|
| Spec | pass / fail |
| Standards | pass / fail |

**Verdict keseluruhan:** `converged` hanya kalau **kedua axis pass** dan tidak ada BLOCKED.

---

## Gaps

| # | Axis | Gap | Root cause | Task |
|---|---|---|---|---|
| 1 | Spec | <...> | kode / spec / kontrak | T? |
| 2 | Standards | <...> | kode / aturan | T? |

Aturan root cause:

- Kode salah → perbaiki kode, tambah test yang menangkapnya.
- Spec ambigu → **jangan** perbaiki kode dulu; perbaiki spec.
- Kontrak tidak sinkron → regenerate, jangan tambal di konsumen.

---

## Evidence rules

- Setiap PASS butuh **output perintah** atau **screenshot**. Bukan opini.
- "Sudah dicek" bukan bukti.
- Kalau bukti tidak bisa dikumpulkan → status BLOCKED, bukan PASS.

---

## Next action

- [ ] Perbaiki gap #1
- [ ] Jalankan converge iterasi 2
- [ ] Kalau kedua axis pass: `feature_list.json` → `passes: true`, isi `evidence`
