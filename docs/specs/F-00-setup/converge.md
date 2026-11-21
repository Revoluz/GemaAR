# Converge: F-00 — Setup environment & scaffold project

**Spec:** `spec.md`
**Tasks:** `tasks.md`

---

## Evidence

| Kriteria | Bukti | Status |
|---|---|---|
| FR-001 build APK | `./gradlew assembleDebug` → **BUILD SUCCESSFUL**, APK 54 MB di `app/build/outputs/apk/debug/app-debug.apk` | PASS |
| FR-002 unit test | `./gradlew testDebugUnitTest` → **BUILD SUCCESSFUL**, 3 test (`ModelScaleTest`) | PASS |
| FR-003 6 model GLB | `assets/models/` berisi 6 file (gong, bonang, saron, gender, slenthem, kendang) | PASS |
| FR-004 viewer 3D | APK di-install di **Galaxy Tab S9 (SM-X710)**; app dibuka; model 3D tampil (dikonfirmasi user) | PASS |
| FR-005 `init.sh` | `bash init.sh --check` → **Environment OK (check mode)** | PASS |

Catatan:
- Latar viewport **hitam** karena `SceneView` tidak memakai skybox/background;
  model tetap tampil. Ini bukan kegagalan.
- Screenshot device tidak tersimpan karena koneksi USB terputus setelah launch;
  verifikasi visual dikonfirmasi user.
- ARCore/marker **tidak** diuji di F-00 (itu F-01). Tablet dipakai untuk viewer 3D.

## Spec vs implementation

| Spec | Implementasi | Cocok? |
|---|---|---|
| US1 build & test | Gradle + app module + unit test | ya |
| US2 lihat model 3D | `SceneView` + `rememberModelInstance` + `ModelNode` | ya |
| Non-goal AR placement | tidak ada `ARSceneView`/marker | ya |
| Non-goal katalog/audio | belum ada catalog/audio | ya |

## Standards

- Tidak ada DI framework/database.
- `SceneView` hanya dipakai di UI.
- Model tetap di `assets/models/`; sumber desain di `docs/design/assets/`.
- Toolchain sesuai ADR 0002 (AGP 9 built-in Kotlin, compileSdk 37.2).

## Verdict

**PASS** — scaffold dan viewer 3D berfungsi. Verifikasi ARCore/marker
dilanjutkan di F-01 dengan device fisik.
