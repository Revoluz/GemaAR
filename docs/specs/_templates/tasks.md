# Tasks: <FEATURE NAME>

> Fase mengikuti Spec Kit. Format task mengikuti `to-tickets`:
> **vertical slice + blocked by**.
> Aturan: satu task ≤ 30 menit, ada path, ada perintah verifikasi.

**Input:** `spec.md`, `plan.md`
**Prerequisites:** spec disetujui, plan lulus constitution check

## Aturan slice

Setelah Foundational, setiap task harus **vertical slice**:

- menembus semua layer yang relevan (data → logic → interface → test)
- **bisa didemokan sendiri**
- muat dalam satu context window

Bukan potongan horizontal seperti "semua model" atau "semua fungsi".

**Pengecualian:** refactor mekanis yang menyentuh banyak file.
Pecah sebagai expand → migrate → contract.

## Format

`[ID] [P?] [USx] <judul>` + `blocked by` + `files` + `verify`

- `[P]` = boleh paralel (file berbeda)
- `[USx]` = user story terkait
- `blocked by` = task yang harus selesai dulu
- `verify` = perintah yang harus lulus

---

## Phase 1: Setup

- [ ] **T001** <setup task>
      blocked by: none
      files: `<path>`
      verify: `<command>`

---

## Phase 2: Foundational (blocking)

**⚠️ Jangan mulai slice sebelum fase ini selesai.**

- [ ] **T002** [P] <task>
      blocked by: T001
      files: `<path>`
      verify: `<command>`

**Checkpoint:** fondasi siap.

---

## Phase 3: US1 — <judul> (P1) 🎯 MVP

**Goal:** <apa yang user dapat>
**Independent test:** <cara menguji sendirian>

- [ ] **T003** [US1] <slice: data + logic + test>
      blocked by: T002
      files: `<path>`
      verify: `<command>`

- [ ] **T004** [US1] <slice: interface + e2e>
      blocked by: T003
      files: `<path>`
      verify: `<command>`

**Checkpoint:** US1 bisa didemokan end-to-end.

---

## Phase 4: US2 — <judul> (P2)

- [ ] **T005** [US2] <slice>
      blocked by: T002
      files: `<path>`
      verify: `<command>`

---

## Phase 5: Polish

- [ ] **T006** Update `progress.md` + `GLOSSARY.md`
      blocked by: semua slice
      verify: `bash scripts/verify-docs.sh`

- [ ] **T007** Jalankan converge
      blocked by: T006
      verify: semua criteria PASS

---

## Frontier

Kerjakan task yang **semua blocker-nya selesai**.
Untuk rantai linear, itu dari atas ke bawah.

Urutan ditentukan `blocked by`, bukan nomor task.

---

## Rules

- **Satu task per iterasi.**
- Commit setelah setiap task.
- Task > 30 menit → pecah sebelum dikerjakan.
- Verifikasi gagal → perbaiki, jangan lanjut.
- Jangan tandai `[x]` tanpa output verifikasi.
- Task baru: tambahkan di akhir dengan `blocked by` yang jelas.
