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


## 2026-11-22 11:58 — F-01 — selesai
- Done: S3 Petunjuk, S4 AR, marker A4 21 cm, Saron menempel, hilang/muncul 4×, izin kamera, gate ARCore, share marker, konfirmasi keluar
- Contract: ya — deteksi wajib `FULL_TRACKING`; gate `ArCoreApk.awaitAvailability`; update `plan.md`
- Docs: `docs/specs/F-01-ar-placement/converge.md`, `tasks.md`, `plan.md`, `feature_list.json`, `ROADMAP.md`, `docs/design/UI/exports/verification/`
- Verify: 7 unit test, lint 0 issue, `init.sh --check`, `verify-docs.sh`
- Next: F-02 ketuk alat → nada
- Blocked: none

## Handoff — 11:58, F-01 → F-02
- Selesai: F-01 `passes: true`, semua task `[x]`
- Kontrak: `ArController` membungkus SceneView/ARCore; UI hanya lihat `ArUiState`
- Belum: audio, tap bilah, note bar
- Boleh mulai: baca `docs/specs/F-02-play-note/spec.md`
- Jebakan: `LAST_KNOWN_POSE` bukan marker terlihat; marker 21 cm; model 0,5 m
- Baca: `docs/specs/F-01-ar-placement/converge.md`
- Fokus sesi berikutnya: F-02


## 2026-11-22 10:35 — F-01 — T001 S1 Beranda
- Done: S1 mengikuti `S1-beranda.png`; font Alfa Slab One/Nunito; background export Pen; Mulai → AR placeholder; Alat Musik/Pengaturan nonaktif
- Contract: no
- Docs: docs/specs/F-01-ar-placement/tasks.md, docs/design/UI/exports/verification/
- Next: T002 S3 Petunjuk
- Blocked: none

## 2026-11-22 10:25 — design — export Pen ke repo
- Done: S1–S7 + state board + aset bersih diexport ke `docs/design/UI/exports/`
- Contract: no
- Docs: docs/design/UI/exports/README.md, SCREENS.md, F-01 plan/tasks
- Next: implement T001 mengikuti S1-beranda.png
- Blocked: marker artwork final (placeholder)

## 2026-11-22 10:05 — F-01 — grill selesai, spec/plan/tasks
- Done: Saron default, marker A4 21 cm, model hide saat marker hilang, note bar/scale ditunda, Unduh Marker share PNG
- Contract: `ArUiState`, `ArController`, `PetunjukStore`
- Docs: docs/specs/F-01-ar-placement/spec.md, plan.md, tasks.md
- Next: review spec → implement T001
- Blocked: marker artwork final (placeholder dipakai)

## 2026-11-22 09:35 — docs — rename F-01
- Done: F-01 jadi "Tempatkan satu alat musik di marker"; referensi SCREENS/ROADMAP disesuaikan
- Contract: no (spec F-01 belum dibuat)
- Docs: feature_list.json, docs/design/SCREENS.md, ROADMAP.md
- Next: F-01 grill + spec
- Blocked: ARCore package di tablet belum dicek

## 2026-11-22 09:25 — design — Alat Musik tanpa navigasi ke AR
- Done: S6 Alat Musik hanya daftar + status; tidak ada tombol ke AR; F-03 hanya S2
- Contract: no (belum ada kode AR)
- Docs: docs/design/SCREENS.md
- Next: F-01 grill + spec
- Blocked: ARCore package di tablet belum dicek

## 2026-11-22 09:20 — design — Koleksi Alat Musik + status
- Done: S6 jadi "Koleksi Alat Musik", daftar 6 alat + badge sudah/belum dimainkan
- Contract: no (belum ada kode AR)
- Docs: docs/design/SCREENS.md
- Next: F-01 grill + spec
- Blocked: ARCore package di tablet belum dicek

## 2026-11-22 09:10 — design — revisi alur: overlay + Alat Musik
- Done: S2/S3 jadi overlay S4; S6 jadi Alat Musik (daftar + status sudah/belum dimainkan)
- Contract: no (belum ada kode AR)
- Docs: docs/design/SCREENS.md, docs/design/COMPONENTS.md
- Next: F-01 grill + spec
- Blocked: ARCore package di tablet belum dicek

## 2026-11-21 16:30 — F-00 — setup + scaffold selesai
- Done: Gradle 9.9.2/AGP 9.5.2, 3D viewer, build/test/init hijau, install di Galaxy Tab S9
- Contract: `ModelScale.factorFor`; asset `models/*.glb`
- Docs: spec/plan/tasks/converge F-00, ADR 0002, README, AGENTS, ROADMAP
- Next: F-01 grill + spec (marker + penempatan)
- Blocked: ARCore package di tablet belum dicek

## 2026-11-21 16:02 — docs — fase setup environment + scaffold
- Done: ROADMAP: Fase 0 setup environment, Fase 1 scaffold project; fase lain digeser
- Contract: no (belum ada kode)
- Docs: ROADMAP.md, README.md
- Next: isi checklist Fase 0
- Blocked: none

## 2026-11-21 15:55 — assets — keputusan skala MVP
- Done: model tetap skala sekarang; tampilan pakai `scaleToUnits = 0.5f` (marker A4)
- Contract: no (belum ada kode)
- Docs: docs/design/assets/README.md
- Next: scaffold app + salin model ke `app/src/main/assets/models/`
- Blocked: none

## 2026-11-21 15:48 — assets — masukkan model 3D + preview
- Done: 6 GLB final + 7 preview masuk `docs/design/assets/`; tambah `CREDITS.md`
- Contract: no (belum ada kode)
- Docs: README, AGENTS, ARCHITECTURE, docs/design/assets/README.md, CREDITS.md
- Next: scaffold app, lalu salin model ke `app/src/main/assets/models/`
- Blocked: none

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
