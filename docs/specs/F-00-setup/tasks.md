# Tasks: F-00 — Setup environment & scaffold project

**Input:** `spec.md`, `plan.md`

## Phase 1: Setup

- [x] **T001** Cek JDK 21+, SDK 37.2, build-tools 37.0.0, adb
      blocked by: none
      files: `local.properties`
      verify: `java -version`, `ls $ANDROID_HOME/platforms`

- [x] **T002** Generate Gradle wrapper 9.9.2
      blocked by: T001
      files: `gradlew`, `gradle/wrapper/*`
      verify: `./gradlew --version`

## Phase 2: Scaffold

- [x] **T003** Buat module `app` + manifest + theme
      blocked by: T002
      files: `settings.gradle.kts`, `build.gradle.kts`, `app/build.gradle.kts`, `app/src/main/AndroidManifest.xml`
      verify: `./gradlew tasks`

- [x] **T004** Tambah dependency Compose + SceneView + DataStore
      blocked by: T003
      files: `gradle/libs.versions.toml`, `app/build.gradle.kts`
      verify: `./gradlew :app:dependencies --configuration debugRuntimeClasspath | grep sceneview`

- [x] **T005** Salin 6 model GLB + marker placeholder
      blocked by: T003
      files: `app/src/main/assets/models/`, `app/src/main/assets/markers/`
      verify: `ls app/src/main/assets/models | wc -l`

- [x] **T006** Buat 3D model viewer + unit test skala
      blocked by: T004, T005
      files: `MainActivity.kt`, `ui/theme/Theme.kt`, `data/ModelScale.kt`, `ModelScaleTest.kt`
      verify: `./gradlew testDebugUnitTest`

## Phase 3: Converge

- [x] **T007** Build APK + jalankan smoke test
      blocked by: T006
      verify: `./gradlew assembleDebug && bash init.sh --check`

- [x] **T008** Update `feature_list.json`, `progress.md`, `ROADMAP.md`
      blocked by: T007
      verify: `bash scripts/verify-docs.sh`

## Catatan

- T002–T007 dikerjakan berurutan.
- Install ke HP dan cek tampilan 3D menunggu device fisik.
