# GemaAR

Aplikasi Android AR untuk mengenal dan memainkan alat musik tradisional
Indonesia. Arahkan kamera ke satu marker, model 3D alat musik yang dipilih muncul
di atasnya, lalu ketuk bagian alat untuk membunyikan nada.

## Stack (sengaja minimal)

| Layer | Pilihan | Alasan |
|---|---|---|
| Bahasa | Kotlin | Standar Android modern, tooling terbaik |
| UI | Jetpack Compose + Material 3 | Satu bahasa untuk layar & state |
| AR | ARCore + SceneView 4.70 (Filament) | Compose-native, marker/image tracking + GLB loader siap pakai, tanpa game engine |
| Audio | `SoundPool` bawaan Android | Zero dependency, latensi rendah untuk sampel pendek |
| Data | JSON di `assets/` | Offline, tanpa backend/database |
| Build | Gradle KTS + version catalog | Standar Android Studio |
| Test | JUnit untuk logika, manual di HP untuk AR | AR tidak realistis di unit test |

Toolchain SceneView 4.70: `compileSdk 37.2`, `minSdk 26`, AGP 9.5.2, Kotlin 2.4.21,
Gradle 9.9.2, JVM target 21 (JDK 21+; di mesin ini JDK 25).

Tidak ada DI framework, database, backend, login, atau Unity. Tambah hanya kalau
sebuah fitur benar-benar menuntutnya (`docs/constitution.md`, Article VII).

## Alur kerja

Repo ini memakai **Agent Project Kit — General**:

```
PRODUCT → feature_list → grill → spec → plan → tasks → implement → converge
```

| File | Isi |
|---|---|
| `AGENTS.md` | peta repo, commands, boundary |
| `feature_list.json` | daftar fitur + status |
| `progress.md` | memori antar sesi |
| `docs/PRODUCT.md` | intent, user, non-goals |
| `docs/ARCHITECTURE.md` | modul, alur data, boundary |
| `docs/design/` | token, peta layar, inventaris komponen |
| `docs/specs/F-XX/` | spec + plan + tasks + converge |
| `docs/decisions/` | ADR (mis. pilihan tech stack) |

## Quickstart

```bash
# prasyarat: JDK 21+, Gradle 9.9.2/AGP 9.5.2, Android SDK 37.2, device ARCore-supported
bash init.sh --check

./gradlew assembleDebug
./gradlew testDebugUnitTest
./gradlew installDebug
```

Project Gradle sudah ada (F-00); perintah di atas untuk build, test, dan install.

Saat ini repo sudah punya project Gradle (F-00). Langkah berikutnya adalah
**verifikasi device ARCore** lalu **F-01 (penempatan model)**.

## Struktur

```
GemaAR/
├── AGENTS.md
├── README.md
├── ROADMAP.md
├── CREDITS.md
├── feature_list.json
├── progress.md
├── init.sh
├── gradlew / gradlew.bat
├── settings.gradle.kts
├── build.gradle.kts
├── gradle.properties
├── local.properties        # tidak di-commit
├── app/
│   ├── build.gradle.kts
│   └── src/
│       ├── main/
│       │   ├── java/com/gemaar/
│       │   ├── assets/models/
│       │   ├── assets/markers/
│       │   └── res/
│       └── test/java/com/gemaar/
├── gradle/
│   ├── libs.versions.toml
│   └── wrapper/
├── scripts/verify-docs.sh
├── .gitignore
└── docs/
    ├── PRODUCT.md
    ├── constitution.md
    ├── ARCHITECTURE.md
    ├── GLOSSARY.md
    ├── design/
    │   ├── DESIGN.md
    │   ├── SCREENS.md
    │   ├── COMPONENTS.md
    │   ├── design-tokens.json
    │   ├── UI/                 # screenshot referensi
    │   └── assets/             # model GLB + preview
    │       ├── models/
    │       └── preview/
    ├── decisions/
    │   ├── NNNN-template.md
    │   └── 0001-native-ar-android.md
    └── specs/
        ├── _templates/
        └── F-00-setup/
```

Project Android mengikuti `docs/ARCHITECTURE.md`; model runtime ada di
`app/src/main/assets/models/`.
