# Plan: <FEATURE NAME>

> Struktur mengikuti Spec Kit `plan-template.md`.
> Plan menjawab **bagaimana**. Tidak menulis kode.
> **Fase kerja tidak di sini** — fase ada di `tasks.md`.

**Feature ID:** F-XX
**Spec:** `docs/specs/F-XX-<slug>/spec.md`
**Status:** draft | review | approved

---

## Summary

<1–3 kalimat: requirement utama + pendekatan teknis.>

## Technical context

| Item | Value |
|---|---|
| Language / version | <... atau NEEDS CLARIFICATION> |
| Primary dependencies | <...> |
| Storage | <... / N/A> |
| Testing | <framework + command> |
| Target platform | <...> |
| Project type | <library / CLI / service / app / script> |
| Performance goals | <... / N/A> |
| Constraints | <... / N/A> |

## Constitution check

- [ ] Tidak ada dependency baru — atau alasannya di `docs/decisions/`
- [ ] Tidak ada abstraksi baru — atau alasannya di `docs/decisions/`
- [ ] Memakai fitur platform/bahasa langsung
- [ ] Interface/kontrak didefinisikan sebelum implementasi
- [ ] Test ditulis sebelum atau bersama implementasi

Kalau ada yang gagal, isi **Complexity tracking**.

## Structure

### Documentation (fitur ini)

```text
docs/specs/F-XX-<slug>/
├── spec.md
├── plan.md
├── tasks.md
└── converge.md
```

### Source (repo)

```text
src/<module>/          # kode
tests/<mirror>/        # test
<generated>/           # hasil generate
```

Ganti dengan path nyata. Hapus placeholder.

## Decisions

| Decision | Choice | Why | Rejected alternative |
|---|---|---|---|
| <topik> | <pilihan> | <alasan> | <alternatif> |

## Interface / contract impact

| Interface / schema | Change | Consumer |
|---|---|---|
| `<nama>` | added / changed | `<modul>` |

Kalau ada perubahan: perbarui kontrak **sebelum** implementasi konsumen.

## Testing strategy

| Level | What | Command |
|---|---|---|
| Unit | <logic> | `<command>` |
| Integration | <batas modul> | `<command>` |
| E2E | <alur> | `<command>` |

## Risks

| Risk | Impact | Mitigation | Detection |
|---|---|---|---|
| <...> | <...> | <...> | <...> |

## Complexity tracking

> Isi **hanya** kalau ada gate yang dilanggar.

| Violation | Why needed | Simpler alternative rejected because |
|---|---|---|
| <...> | <...> | <...> |

## Out of scope

- <...>
