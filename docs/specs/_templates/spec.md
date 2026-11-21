# Spec: <FEATURE NAME>

> Struktur mengikuti Spec Kit `spec-template.md`. Isi tetap milik kita.
> Aturan keras: **tidak boleh menyebut teknologi** (bahasa, framework, library, database).
> Spec harus bisa diverifikasi oleh orang non-teknis.

**Feature ID:** F-XX
**Status:** draft | review | approved
**Owner:** <name>

---

## Problem

<Apa yang tidak bisa dilakukan user saat ini. Fakta, bukan opini.>

## Why now

<Kenapa dikerjakan sekarang.>

## User stories (prioritized)

> Setiap story harus bisa diuji **sendiri**. P1 = paling penting.
> Kalau hanya P1 yang jadi, itu sudah MVP yang berguna.

### US1 — <judul> (Priority: P1)

<Deskripsi journey dalam bahasa biasa.>

- **Why this priority:** <nilai dan alasannya>
- **Independent test:** <cara menguji story ini sendirian>

**Acceptance scenarios:**

1. **Given** <state awal>, **When** <aksi>, **Then** <hasil>
2. **Given** <state>, **When** <aksi>, **Then** <hasil>

---

### US2 — <judul> (Priority: P2)

<Deskripsi.>

- **Why this priority:** <...>
- **Independent test:** <...>

**Acceptance scenarios:**

1. **Given** <...>, **When** <...>, **Then** <...>

---

## Edge cases

- Apa yang terjadi saat <kondisi batas>?
- Bagaimana sistem menangani <error scenario>?

## Functional requirements

- **FR-001**: Sistem MUST <kapabilitas spesifik>
- **FR-002**: Sistem MUST <...>
- **FR-003**: User MUST bisa <interaksi kunci>
- **FR-004**: <...> [NEEDS CLARIFICATION: <pertanyaan>]

## Key entities *(kalau melibatkan data)*

- **<Entity>**: <apa yang direpresentasikan, atribut kunci, tanpa implementasi>
- **<Entity>**: <relasi ke entity lain>

## Success criteria *(measurable, technology-agnostic)*

- **SC-001**: <metrik, mis. "user menyelesaikan pendaftaran < 2 menit">
- **SC-002**: <metrik, mis. "90% user berhasil pada percobaan pertama">
- **SC-003**: <metrik bisnis, mis. "tiket support soal X turun 50%">

## Non-goals

- <yang tidak dibangun>
- <yang ditunda>

## Assumptions

- <asumsi tentang user, mis. "punya koneksi stabil">
- <asumsi scope, mis. "mobile di luar scope v1">
- <dependensi, mis. "memakai auth yang sudah ada">

## Open questions

- [NEEDS CLARIFICATION: <pertanyaan>]

---

## Checklist

- [ ] Tidak ada nama teknologi/library
- [ ] Setiap acceptance scenario bisa diamati dari luar
- [ ] Setiap FR punya cara verifikasi
- [ ] Tidak ada `[NEEDS CLARIFICATION]` tersisa
- [ ] Non-goals eksplisit
- [ ] Tidak ada fitur spekulatif
