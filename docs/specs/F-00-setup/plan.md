# Plan: F-00 — Setup environment & scaffold project

**Input:** `spec.md`
**Status:** draft

## Toolchain

| Komponen | Versi |
|---|---|
| JDK | 21+ (dipakai Temurin 25; target 21) |
| Gradle | 9.9.2 (wrapper) |
| AGP | 9.5.2 (built-in Kotlin) |
| Kotlin | 2.4.21 (Compose compiler plugin) |
| compileSdk | 37 (minor 2) |
| targetSdk | 37 |
| minSdk | 26 |
| JVM target | 21 |
| SceneView | 4.70.0 |
| Compose BOM | 2026.11.01 |

## Struktur

```
app/
├── build.gradle.kts
├── src/main/AndroidManifest.xml
├── src/main/java/com/gemaar/
│   ├── MainActivity.kt
│   ├── data/ModelScale.kt
│   └── ui/theme/Theme.kt
├── src/main/assets/models/*.glb
├── src/main/assets/markers/marker_placeholder.png
└── src/test/java/com/gemaar/data/ModelScaleTest.kt
```

## Dependency

- Compose + Material3 (BOM)
- Activity Compose, Navigation Compose, Lifecycle
- DataStore Preferences
- SceneView `arsceneview` 4.70.0
- JUnit 4

## Contract

- `ModelScale.factorFor(maxMeters, targetMeters = 0.5f): Float`
- Asset model: `models/<nama>.glb` (6 file)
- Marker: `markers/marker_placeholder.png` (sementara)

## Verifikasi

```bash
export JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64
./gradlew assembleDebug
./gradlew testDebugUnitTest
bash init.sh --check
```

## Risiko

- compileSdk 37 butuh `platforms;android-37.2` dan `compileSdkMinor = 2`
  (dependency Compose 1.13.0 minta 37.1+).
- AGP 9 memakai built-in Kotlin: plugin `org.jetbrains.kotlin.android` harus
  dilepas; Compose compiler plugin tetap dipakai.
- `ModelNode` adalah member scope `SceneView { }`, bukan import.
- Device ARCore fisik belum terhubung → install/visual check memakai emulator API 35.

## Constitution check

- [x] Simplicity: tanpa DI framework/database.
- [x] Boundary: UI → data; SceneView hanya di UI.
- [x] Tidak ada dependency tanpa alasan (ADR 0001).
