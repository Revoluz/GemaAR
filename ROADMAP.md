# ROADMAP — GemaAR

Satu halaman. Cetak, tempel, centang.

---

## Sebelum mulai

- [x] Struktur Agent Project Kit di-copy ke repo
- [x] `AGENTS.md` → Commands & Where things live (Android/Gradle)
- [x] `feature_list.json` → 7 fitur
- [x] `docs/ARCHITECTURE.md` → modul & boundary
- [x] `scripts/verify-docs.sh` → `SRC_GLOB=app/src`
- [x] `docs/decisions/0001-native-ar-android.md`
- [x] `docs/design/` disalin dari kit (masih template)
- [ ] `bash init.sh --check` lulus (butuh project Gradle)

---

## Fase 0 — Scaffold

- [ ] Project Gradle + `app/` + `gradlew` ada
- [ ] `./gradlew assembleDebug` hijau (HP/emulator belum perlu)
- [ ] `./gradlew testDebugUnitTest` hijau (minimal 1 test)
- [ ] `init.sh --check` hijau
- [ ] Satu HP ARCore-supported terpasang untuk uji manual

---

## Fase 1 — Produk (sekali)

- [x] `docs/PRODUCT.md` — masalah, user, non-goals, metrik
- [x] `docs/constitution.md` — 9 artikel (tetap, stack-agnostic)
- [x] `docs/ARCHITECTURE.md` — modul, boundary, alur data
- [x] `docs/GLOSSARY.md` — istilah AR + alat musik MVP
- [ ] Jawab Q1–Q3 di `docs/PRODUCT.md`

---

## Fase 2 — Desain & daftar fitur (sekali)

**Hanya kalau project punya UI.**

- [x] `docs/design/DESIGN.md` — token + alasan tiap keputusan (draft)
- [x] `docs/design/design-tokens.json` — token dalam bentuk mesin
- [x] `docs/design/SCREENS.md` — peta layar → data → fitur
- [x] `docs/design/COMPONENTS.md` — inventaris komponen
- [x] `feature_list.json` berisi 7 fitur
- [x] tiap fitur bisa didemokan sendiri
- [x] urut by `priority`

Jalankan `/design-system generate` kalau skill terpasang.
Lalu audit: `/design-system audit` sebelum rilis besar.

---

## Fase 3 — Per fitur (ulangi)

Mulai dari **F-01 — Scaffold + penempatan model**.

- [ ] **3.1 Grill** — wawancara sampai bisa menulis spec tanpa menebak
- [ ] **3.2 Research** — hanya kalau butuh fakta eksternal (ARCore device support, SceneView API)
- [ ] **3.3 Spec** — apa & kenapa, tanpa teknologi
- [ ] **3.4 Plan** — bagaimana, tanpa kode
- [ ] **3.5 Tasks** — vertical slice, `blocked by`, ≤30 menit
- [ ] **3.6 Implement** — **satu task per pesan**, verify, commit
- [ ] **3.7 Converge** — spec + standards, dengan bukti
- [ ] **3.8 Tutup** — `passes: true`, `evidence`, `progress.md`

---

## Fase 4 — Loop harian

**Pagi**
- [ ] baca `progress.md` (40 baris terakhir)
- [ ] `bash init.sh`
- [ ] "lanjut task berikutnya"

**Setiap task**
- [ ] kerjakan satu
- [ ] jalankan `verify`
- [ ] commit
- [ ] tandai `[x]`

**Sore**
- [ ] handoff di `progress.md`
- [ ] `bash scripts/verify-docs.sh`

---

## Target MVP (setelah F-01 + F-02)

Satu alat musik (mis. saron) bisa:
- muncul di atas satu marker lewat kamera,
- diketuk dan berbunyi minimal satu oktaf,
- tanpa crash di HP ARCore-supported.

---

## File yang penting

| File | Isi |
|---|---|
| `AGENTS.md` | peta repo, commands, boundary |
| `feature_list.json` | daftar fitur + status |
| `progress.md` | memori antar sesi |
| `docs/specs/F-XX/` | spec + plan + tasks + converge |

---

## Tiga aturan

1. **Satu task per pesan.**
2. **Tidak ada `passes: true` tanpa bukti.**
3. **Fitur kecil (<2 jam) tidak perlu 4 dokumen.**

---

## Selesai kalau

- [ ] semua fitur `passes: true` dengan evidence
- [ ] `verify-docs.sh` hijau
- [ ] `progress.md` cukup untuk melanjutkan besok
