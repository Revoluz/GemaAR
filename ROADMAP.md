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

---

## Fase 0 — Setup environment

- [x] JDK 21+ aktif (JDK 25 dipakai; target JVM 21)
- [x] Gradle 9.9.2 + AGP 9.5.2 (Android Studio opsional)
- [x] Android SDK 37.2 + Build Tools 37.0.0 + Platform Tools
- [x] `sdk.dir` di `local.properties` (jangan commit)
- [x] `adb` berfungsi (emulator API 35)
- [x] Device fisik terhubung (Galaxy Tab S9 / SM-X710)
- [ ] Google Play Services for AR (`com.google.ar.core`) terpasang:
      `adb shell pm list packages | grep -i arcore`
- [ ] Device ada di daftar ARCore-supported: https://developers.google.com/ar/devices
- [x] Koneksi internet untuk unduh dependency Gradle
- [x] Blender + Node (`npx`) untuk pipeline aset 3D

---

## Fase 1 — Scaffold project

- [x] `settings.gradle.kts`, `build.gradle.kts`, `app/build.gradle.kts`
- [x] `gradle/libs.versions.toml` + wrapper (`gradlew`)
- [x] Dependency: Compose, Material3, Navigation, SceneView `arsceneview:4.70.0`, DataStore, JUnit
- [x] Manifest: izin kamera, ARCore optional, landscape, theme
- [x] Salin `docs/design/assets/models/*.glb` → `app/src/main/assets/models/`
- [x] Salin marker placeholder → `app/src/main/assets/markers/marker_placeholder.png`
- [x] `./gradlew assembleDebug` hijau
- [x] `./gradlew testDebugUnitTest` hijau (3 test)
- [x] `./gradlew installDebug` di emulator API 35
- [x] `./gradlew installDebug` di device fisik (Galaxy Tab S9)
- [x] Viewer 3D menampilkan model di device
- [x] `bash init.sh --check` hijau

---

## Fase 2 — Produk (sekali)

- [x] `docs/PRODUCT.md` — masalah, user, non-goals, metrik
- [x] `docs/constitution.md` — 9 artikel (tetap, stack-agnostic)
- [x] `docs/ARCHITECTURE.md` — modul, boundary, alur data
- [x] `docs/GLOSSARY.md` — istilah AR + alat musik MVP
- [ ] Jawab Q1–Q3 di `docs/PRODUCT.md`

---

## Fase 3 — Desain & daftar fitur (sekali)

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

## Fase 4 — Per fitur (ulangi)

Mulai dari **F-01 — penempatan model di marker**.

- [ ] **4.1 Grill** — wawancara sampai bisa menulis spec tanpa menebak
- [ ] **4.2 Research** — hanya kalau butuh fakta eksternal (ARCore device support, SceneView API)
- [ ] **4.3 Spec** — apa & kenapa, tanpa teknologi
- [ ] **4.4 Plan** — bagaimana, tanpa kode
- [ ] **4.5 Tasks** — vertical slice, `blocked by`, ≤30 menit
- [ ] **4.6 Implement** — **satu task per pesan**, verify, commit
- [ ] **4.7 Converge** — spec + standards, dengan bukti
- [ ] **4.8 Tutup** — `passes: true`, `evidence`, `progress.md`

---

## Fase 5 — Loop harian

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
