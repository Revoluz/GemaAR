# progress.md

> Sumber: Anthropic *Effective Harnesses for Long-Running Agents*.
> File ini adalah **memori antar sesi**. Chat boleh hilang; file ini tidak.
>
> Aturan:
> - **Append-only.** Jangan hapus entri lama.
> - Setiap sesi menulis satu entri + satu handoff card.
> - Maksimal 8 baris per entri. Kalau lebih, itu bukan progress — itu cerita.

---

## Format

```markdown
## YYYY-MM-DD HH:MM — <area> — <fitur/task>
- Done: <apa yang selesai> (<commit>)
- Contract: <berubah? apa?> | no
- Docs: <file yang di-update>
- Next: <langkah berikutnya>
- Blocked: <kalau ada>
```

## Handoff card (dipakai saat pindah area atau menutup sesi)

> Jangan mengulang isi artefak lain. Sebut path-nya saja.
> Tujuannya: agent berikutnya bisa lanjut tanpa membaca seluruh riwayat chat.

```markdown
## Handoff — HH:MM, BE → FE
- Selesai: register + login endpoint (commit a1b2c3)
- Kontrak: berubah, sudah regenerate
- Belum: logout (T4)
- Boleh mulai: halaman login, pakai client terbaru
- Jebakan: cookie httpOnly, jangan localStorage
- Baca: docs/specs/F-02-login/spec.md, tasks.md
- Fokus sesi berikutnya: Phase 3 (UI)
```

---

## Log

## 2026-11-21 14:32 — docs — tambah F-07 Pengaturan volume musik
- Done: F-07 masuk feature_list; S7 aktif di SCREENS; SettingsPanel + SliderRow masuk COMPONENTS
- Contract: no (belum ada kode)
- Docs: feature_list.json, docs/design/SCREENS.md, docs/design/COMPONENTS.md, docs/design/DESIGN.md, ROADMAP.md
- Next: tulis spec F-01 (marker + swap model)
- Blocked: none

## 2026-11-21 14:26 — docs — selaraskan file lain dengan DESIGN.md
- Done: hapus font mono dari token, rename CulturePanel → InfoPanel, referensi desain di AGENTS/ARCHITECTURE/README/ROADMAP
- Contract: no (belum ada kode)
- Docs: AGENTS.md, README.md, ROADMAP.md, docs/ARCHITECTURE.md, docs/design/*
- Next: tulis spec F-01
- Blocked: F-07 belum ada di feature_list.json

## 2026-11-21 14:20 — docs — satu marker untuk semua alat
- Done: tegaskan marker tunggal; alat dipilih/diganti dari katalog, model swap tanpa scan ulang
- Contract: no (belum ada kode)
- Docs: AGENTS, README, ROADMAP, PRODUCT, ARCHITECTURE, GLOSSARY, ADR 0001, docs/design/SCREENS
- Next: tulis spec F-01 (satu marker + swap model)
- Blocked: none

## 2026-11-21 14:12 — docs — keputusan desain: alat, marker, pengaturan, font
- Done: alat ikut GLOSSARY; AR marker (bukan plane); pengaturan hanya musik; 3 font dikunci
- Contract: no (belum ada kode)
- Docs: AGENTS, README, ROADMAP, PRODUCT, ARCHITECTURE, GLOSSARY, ADR 0001, docs/design/*
- Next: tambah F-07 (manusia) + tulis spec F-01 untuk marker
- Blocked: F-07 belum ada di feature_list.json

## 2026-11-21 14:05 — design — dokumentasi desain dari referensi UI
- Done: isi DESIGN, design-tokens, SCREENS, COMPONENTS; petakan S1–S6 ke F-01…F-06; panel Info sisi kanan
- Contract: no (belum ada kode)
- Docs: docs/design/*
- Next: konfirmasi font/palet + keputusan marker vs plane di spec F-01
- Blocked: none

## 2026-11-21 13:52 — docs — sinkron struktur kit terbaru
- Done: salin `docs/design/` (DESIGN, SCREENS, COMPONENTS, design-tokens.json) + update referensi
- Contract: no (belum ada kode)
- Docs: AGENTS.md, README.md, ROADMAP.md, docs/design/*
- Next: isi docs/design saat masuk Fase 2
- Blocked: none

## 2026-11-21 12:20 — docs — Gong & Kempul satu instrument
- Done: gabung Gong dan Kempul jadi satu entri karena satu object/rak model 3D
- Contract: no (belum ada kode)
- Docs: docs/GLOSSARY.md
- Next: jawab grill F-01 (nada, sumber model, interaksi, HP uji)
- Blocked: none

## 2026-11-21 12:15 — docs — alat pertama F-01
- Done: tetapkan Saron sebagai instrument pertama F-01/F-02
- Contract: no (belum ada kode)
- Docs: docs/PRODUCT.md (Q1 closed)
- Next: grill F-01 (nada, interaksi, sumber model) → spec
- Blocked: none

## 2026-11-21 12:05 — docs — revisi daftar alat ke gamelan
- Done: ganti daftar alat MVP: Gong, Kempul, Bonang, Saron, Gender Barung (14 bilah), Slenthem (7 bilah), Kendang
- Contract: no (belum ada kode)
- Docs: docs/GLOSSARY.md, docs/PRODUCT.md, ROADMAP.md
- Next: tentukan alat pertama untuk F-01, lalu grill
- Blocked: Q1 masih open

## 2026-11-21 11:30 — setup — bootstrap repo GemaAR
- Done: copy Agent Project Kit, isi AGENTS/README/PRODUCT/ARCHITECTURE/GLOSSARY, feature_list 6 fitur, ADR stack
- Contract: no (belum ada kode)
- Docs: AGENTS.md, README.md, ROADMAP.md, docs/*, docs/decisions/0001-native-ar-android.md
- Next: grill F-01, lalu scaffold Gradle + ARSceneView
- Blocked: none

---

<!-- Tambahkan entri baru di ATAS bagian Log, tepat setelah heading "## Log". -->
