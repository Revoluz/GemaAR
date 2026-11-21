# Spec: F-00 — Setup environment & scaffold project

**Feature ID:** F-00
**Status:** draft
**Owner:** engineering

---

## Problem

Repo saat ini hanya berisi dokumen dan aset. Belum ada project Android yang bisa
di-build, belum ada test, dan environment AR (JDK/SDK/ARCore) belum diverifikasi.

## Why now

Fitur F-01 dan seterusnya tidak bisa diverifikasi tanpa project yang bisa
di-build dan dijalankan.

## User stories

### US1 — Build dan test project (P1)

Sebagai developer, saya bisa menjalankan build dan unit test dari terminal.

- **Independent test:** jalankan `./gradlew assembleDebug` dan
  `./gradlew testDebugUnitTest`.
- **Acceptance:**
  1. **Given** repo bersih, **When** build dijalankan, **Then** APK debug terbentuk.
  2. **Given** repo bersih, **When** unit test dijalankan, **Then** semua test lulus.

### US2 — Melihat model 3D (P2)

Sebagai developer, saya bisa membuka app dan melihat salah satu model alat musik
untuk memastikan pipeline aset bekerja.

- **Independent test:** install APK di HP/emulator, buka app, pilih alat.
- **Acceptance:**
  1. **Given** app terbuka, **When** memilih alat, **Then** model tampil.
  2. **Given** model dipilih, **When** app diputar, **Then** tidak crash.

## Edge cases

- HP tanpa ARCore → app tetap bisa menampilkan 3D viewer.
- Tidak ada koneksi → build gagal dengan pesan dependency, bukan crash app.
- Device tidak terhubung → install tidak bisa diverifikasi.

## Functional requirements

- **FR-001**: Project harus bisa di-build dan menghasilkan APK debug.
- **FR-002**: Unit test harus bisa dijalankan dan lulus.
- **FR-003**: Enam model GLB harus tersedia di asset aplikasi.
- **FR-004**: App harus menampilkan salah satu model 3D tanpa crash.
- **FR-005**: `bash init.sh --check` harus lulus.

## Key entities

- **Model**: satu alat musik dengan file GLB di `assets/models/`.

## Success criteria

- **SC-001**: Build + unit test lulus dalam satu perintah masing-masing.
- **SC-002**: APK debug ada di `app/build/outputs/apk/debug/`.
- **SC-003**: 6/6 model GLB ada di `app/src/main/assets/models/`.

## Non-goals

- Penempatan AR di marker (F-01).
- Katalog dan audio (F-02/F-03).
- UI final dan pengaturan (F-07).

## Assumptions

- JDK 21+ tersedia (di mesin ini JDK 25; target JVM 21).
- Android SDK 37.2 dan build-tools 37.0.0 tersedia.
- Koneksi internet untuk mengunduh dependency Gradle.

## Open questions

- Verifikasi ARCore/marker ditunda ke F-01. Viewer 3D sudah diverifikasi di
  Galaxy Tab S9; latar hitam karena belum ada skybox, bukan kegagalan.

---

## Checklist

- [x] Tidak ada nama teknologi/library di acceptance criteria
- [x] Setiap acceptance scenario bisa diamati dari luar
- [x] Setiap FR punya cara verifikasi
- [x] Tidak ada `[NEEDS CLARIFICATION]` tersisa
- [x] Non-goals eksplisit
