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

## 2026-11-25 11:40 — F-04 — implementasi inti
- Done: `Note` String id, `InstrumentAudio` Rows/Pads, `InstrumentAudioMap`, `InstrumentControls`, `InstrumentTapMap`, player per alat, 45 OGG baru
- Alat: Slenthem 7, Bonang 12 (2×6), Gender 14 (bar 4 sample 3/3h), Gong 7 pad, Kendang 7 pad
- Verify: 35 unit test, lint 0, build OK; log sample loaded 7/12/14/7/7; screenshot F04-*
- Next: T008 uji tap 3D + marker semua alat, lalu converge
- Blocked: tap 3D 2 baris perlu validasi device

## 2026-11-25 11:20 — F-04 — grill selesai
- Keputusan: urutan Slenthem→Bonang→Gender→Gong→Kendang; Bonang 6+6; Gender 14 bilah (nada 4 pakai sample 3/3h); Kendang 7 pad; tap 3D + kontrol
- Catatan: CDM GENDER tidak punya GBPL4; sudah diverifikasi dari file dan PDF; Gender Barung Pelog memakai subset pathet
- Docs: `docs/specs/F-04-instrument-audio/spec.md`, `plan.md`, `tasks.md`
- Next: implement T001
- Blocked: none

## 2026-11-25 11:05 — F-03 — skala Kendang/Slenthem 0,3
- Done: Kendang + Slenthem modelScale 0,3 m; alat lain 0,5 m
- Verify: build/test/lint OK
- Next: cek visual di device
- Blocked: none

## 2026-11-25 11:00 — F-03 — skala Kendang/Slenthem 0,2
- Done: Kendang + Slenthem modelScale 0,2 m; alat lain 0,5 m
- Verify: build/test/lint OK
- Next: cek visual di device
- Blocked: none

## 2026-11-25 10:45 — F-03 — skala Kendang/Slenthem 0,25
- Done: Kendang + Slenthem modelScale 0,25 m; alat lain 0,5 m
- Verify: build/test/lint OK
- Next: cek visual di device
- Blocked: none

## 2026-11-25 10:35 — F-03 — skala Slenthem
- Done: `slenthem` modelScale 0,3 m; Kendang 0,3 m; alat lain 0,5 m
- Verify: build/test/lint OK; 24 unit test
- Next: cek visual Slenthem di device
- Blocked: none

## 2026-11-25 10:25 — F-03 — skala Kendang
- Done: `Instrument.modelScale`; default 0,5 m; Kendang 0,3 m; `ArScreen` memakai skala per alat
- Docs: F-03 plan/converge, ARCHITECTURE, assets README, BUG-001
- Verify: 24 unit test, lint 0, build OK
- Next: uji visual Kendang di device
- Blocked: none

## 2026-11-25 10:10 — F-03 — selesai
- Done: katalog 6 alat, pilih model tanpa rescan, last instrument, first-run, non-Saron message, compact catalog HP
- Keputusan: skala model seragam **0,5 m** tablet+HP; kamera AR 1080p; grace marker 500 ms
- Bug: BUG-001 A25 flicker dicatat `docs/bugs/BUG-001-a25-ar-flicker.md` (fix nanti; Tab S9 stabil)
- Verify: build OK, 23 unit test, lint 0, init --check, verify-docs; F-03 `passes: true`
- Next: F-04 audio semua alat selain Saron
- Blocked: BUG-001 (device-specific, deferred)

## Handoff — F-03 → F-04
- Selesai: F-03 `passes: true`; Catalog 6 alat, InstrumentStore, CatalogOverlay
- Kontrak: `Catalog.instruments`, `Instrument.audioAvailable`, `InstrumentStore`
- Belum: audio Bonang/Gender/Slenthem/Gong/Kendang (F-04)
- Baca: `docs/specs/F-04-instrument-audio/spec.md`, `plan.md`, `tasks.md`
- Jebakan: Gender `GBPL4` tidak ada; Kendang subset pukulan; skala model 0,5 m
- Fokus sesi berikutnya: F-04

## 2026-11-25 09:35 — F-03 — review B + flicker
- Done: compact catalog untuk HP, first-run via `LaunchedEffect(arStarted)`, grace marker 500 ms, `CatalogTest`
- Verify: 23 unit test, lint 0, build OK; katalog 6 alat di HP landscape; Tab S9 stabil
- Temuan: A25 (SM-A256E) flicker pada model; Tab S9 tidak. Perlu diagnosis jarak/device
- Next: R3 device test Tab S9 + keputusan flicker A25
- Blocked: A25 flicker (device-specific)

## 2026-11-24 14:53 — F-03 — B selesai (3 alat + persistence)
- Done: ikon `ikon_gender_barung`/`ikon_gong_kempul`/`ikon_kendang`; `Catalog` 6 alat; `InstrumentStore`; `ArScreen` restore alat terakhir + simpan pilihan + katalog first-run sekali; warning UseKtx dibersihkan
- Uji device SM-A356E: 6 kartu, tiap alat dipilih tanpa scan ulang marker, non-Saron pesan audio + note bar hilang, kembali ke Saron audio hidup, marker hilang/muncul, Back, alat terakhir pulih, first-run sekali setelah reset data (user-confirmed)
- Contract: no
- Docs: docs/specs/F-03-catalog/tasks.md (B1–B6)
- Next: R1–R4 review, build/lint, uji device, converge F-03
- Blocked: none

## 2026-11-24 10:32 — setup — environment Windows
- Done: install Temurin JDK 21.0.13.1 (winget); set `JAVA_HOME`, `ANDROID_HOME`, `ANDROID_SDK_ROOT` + Path user; `assembleDebug` OK, `testDebugUnitTest` OK, `lintDebug` OK (1 info versi SceneView), `init.sh --check` OK
- Contract: no
- Docs: docs/onboarding/05-setup-windows.md (dibaca, tidak diubah)
- Next: hubungkan HP ARCore via USB (04-hubungkan-hp.md) lalu `installDebug`
- Blocked: belum ada HP terhubung (`adb devices` kosong)

## 2026-11-23 01:35 — F-03 — A selesai + review
- Done: A1–A7 (Saron/Bonang/Slenthem, CatalogOverlay, ArController instrument, wiring)
- Review agent: fix `catalogVisible` default, stop audio saat pilih, import `android.R`, `Catalog.instruments`, error model generik; hapus daemon JVM generated
- Verify: `assembleDebug`, `testDebugUnitTest`, `lintDebug` hijau; katalog 3 kartu + pilih Bonang (judul berubah) di Tab S9
- Next: B1–B6 (3 alat, ikon, InstrumentStore, first-run)
- Blocked: none

## Handoff — A → B, F-03
- Selesai: fondasi `Instrument`, `Catalog` 3 alat, `CatalogOverlay`, `ArController(instrument)`, wiring katalog
- Kontrak: `Catalog.instruments`, `Catalog.byId`, `Catalog.saron`; `Instrument.audioAvailable`
- Belum: 3 alat (Gender/Gong/Kendang), ikon, `InstrumentStore`, first-run, last-instrument
- File: `data/Instrument.kt`, `data/Catalog.kt`, `ui/ar/CatalogOverlay.kt`, `ui/ar/ArScreen.kt`, `ar/ArController.kt`
- Catatan: `catalogVisible` masih state sementara; B mengganti dengan store + first-run
- Baca: `docs/specs/F-03-catalog/team-guide.md`, `tasks.md`

## 2026-11-22 18:40 — docs — onboarding tim
- Done: folder `docs/onboarding/`; struktur Kotlin, Android Studio, VS Code/CLI, koneksi HP, setup Windows, troubleshooting, Git workflow
- Docs: `docs/onboarding/README.md` + 7 panduan; root `README.md` dan `AGENTS.md` diperbarui
- Next: A mulai F-03 A1 (ikon) sesuai `docs/specs/F-03-catalog/team-guide.md`
- Blocked: none

## 2026-11-22 18:20 — F-03 — disederhanakan + pembagian tim
- Done: F-03 tanpa JSON/parser; `Catalog.kt` statis + ikon PNG; panduan A/B di `team-guide.md`
- Pembagian: A = overlay + Saron/Bonang/Slenthem; B = Gender/Gong/Kendang + InstrumentStore + first-run
- Docs: `docs/specs/F-03-catalog/spec.md`, `plan.md`, `tasks.md`, `team-guide.md`, `SCREENS.md`, `COMPONENTS.md`, `ARCHITECTURE.md`
- Next: A mulai A1 (ikon) lalu A2–A7; handoff ke B
- Blocked: none

## 2026-11-22 18:00 — F-03/F-04 — spec
- Done: F-03 katalog (model swap, last instrument, first-run, audio Saron saja); F-04 audio semua alat selain Saron (menggantikan scale/oktaf)
- Contract: `catalog.json` v1, `Instrument`, `InstrumentStore`, `InstrumentAudio`, `InstrumentControls`
- Docs: `docs/specs/F-03-catalog/`, `docs/specs/F-04-instrument-audio/`, `SCREENS.md`, `COMPONENTS.md`, `ARCHITECTURE.md`, `PRODUCT.md`, `ROADMAP.md`, `feature_list.json`
- Catatan: Slenthem ada di CDM sebagai `GENDER/GSPL*`; Kendang memakai 7 pukulan subset
- Next: implement F-03 T001 (`catalog.json` + repository)
- Blocked: none

## 2026-11-22 17:20 — F-02 — selesai
- Done: T001a–T009; 7 sampel audio, NoteMap, NotePlayer, tap mapping pusat bilah, note bar, feedback, lifecycle
- Contract: `NotePlayer` + `NoteIndex`; mapping bug bilah 1→2 / 7→6 diperbaiki
- Docs: `docs/specs/F-02-play-note/converge.md`, `tasks.md`, `feature_list.json`, `ROADMAP.md`, `CREDITS.md`, `assets/audio/`, `verification/T006–T008*`
- Verify: 18 unit test, lint 0, `init.sh --check`, `verify-docs.sh`; device Tab S9
- Next: F-03 katalog + pilih alat
- Blocked: none

## Handoff — 17:20, F-02 → F-03
- Selesai: F-02 `passes: true`; audio Saron 7 nada, tap + note bar jalan
- Kontrak: `NotePlayer`, `NoteIndex`, `SaronNoteMap`; pindahkan note map ke `catalog.json` di F-03
- Belum: katalog 6 alat, pilih/ganti model
- Boleh mulai: grill + spec F-03 (folder belum ada)
- Jebakan: lisensi audio Artistic 2.0; tabuh sudah dihapus; mapping pakai pusat bilah, bukan lebar model
- Baca: `docs/specs/F-02-play-note/converge.md`, `app/src/main/assets/audio/NOTICE.md`
- Fokus sesi berikutnya: F-03

## 2026-11-22 16:45 — F-02 — T001a selesai
- Done: tabuh (`Object_109` + `Object_112`) dihapus; `saron.glb` 463 KB, 5.058 tris; preview diperbarui
- Contract: model F-02 tanpa tabuh; bbox X/Y tetap, tinggi 0,3205 m
- Docs: `docs/specs/F-02-play-note/tasks.md`, `docs/design/assets/README.md`, `verification/T001a-saron-tanpa-tabuh.png`
- Verify: Blender render 7 bilah bersih; gltf-transform inspect OK; app load tanpa ModelError
- Next: T001 sampel audio
- Blocked: none

## 2026-11-22 16:40 — F-02 — tabuh dihapus
- Done: setuju hapus tabuh; tambah FR-013/SC-008 dan task T001a
- Contract: model Saron F-02 tanpa `Object_109` + `Object_112`
- Docs: `docs/specs/F-02-play-note/spec.md`, `plan.md`, `tasks.md`
- Next: T001a ekspor ulang model tanpa tabuh
- Blocked: none

## 2026-11-22 16:25 — F-02 — spec/plan/tasks
- Done: grill F-02; Saron-only, Pelog 7 (SBPL1–7), tap model→nada terdekat, note bar 1–7, overlap, highlight+label
- Contract: `NotePlayer`, `NoteMap`, `onModelTap(normalizedX)`
- Docs: `docs/specs/F-02-play-note/{spec,plan,tasks}.md`, `docs/decisions/0003-gamelan-audio-samples.md`, `docs/PRODUCT.md`
- Next: T001 sampel audio + lisensi
- Blocked: none


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
