# Struktur Project Kotlin — GemaAR

Project ini adalah aplikasi Android dengan **Kotlin + Jetpack Compose + ARCore +
SceneView**. Build memakai Gradle Kotlin DSL (`.kts`).

## Gambaran besar

```
GemaAR/
├── app/                         # modul aplikasi Android
│   ├── src/main/
│   │   ├── java/com/gemaar/     # kode Kotlin
│   │   ├── assets/              # model 3D, marker, lisensi audio
│   │   └── res/                 # audio, gambar, font, ikon, XML
│   ├── src/test/                # unit test (JVM)
│   └── src/androidTest/         # instrumented test (HP/emulator)
├── docs/                        # spec, desain, keputusan, onboarding
├── scripts/                     # script verifikasi & olah audio
├── gradle/                      # version catalog
├── build.gradle.kts             # build level project
├── settings.gradle.kts          # daftar modul
├── gradlew / gradlew.bat        # Gradle Wrapper
├── feature_list.json            # status fitur
├── progress.md                  # memori sesi / handoff
├── ROADMAP.md
└── AGENTS.md                    # aturan kerja agent
```

## Folder kode Kotlin

Lokasi: `app/src/main/java/com/gemaar/`

```
com/gemaar/
├── MainActivity.kt          # activity utama; routing Beranda ↔ AR
├── ar/                      # semua akses ARCore/SceneView
│   ├── ArController.kt      # sesi AR, marker, model, tap
│   ├── ArUiState.kt         # state layar AR
│   ├── MarkerRepository.kt  # marker A4 21 cm
│   ├── MarkerVisibility.kt  # FULL_TRACKING vs LAST_KNOWN_POSE
│   └── NoteIndex.kt         # posisi tap → indeks bilah
├── audio/                   # pemutar audio
│   ├── NotePlayer.kt        # interface
│   └── SoundPoolNotePlayer.kt
├── data/                    # data/domain, tanpa UI/AR
│   ├── Note.kt
│   ├── SaronNoteMap.kt
│   ├── PetunjukStore.kt
│   └── MarkerSharer.kt
├── ui/                      # Compose UI
│   ├── HomeScreen.kt        # S1 Beranda
│   ├── ar/                  # S3/S4
│   │   ├── ArScreen.kt
│   │   ├── PetunjukOverlay.kt
│   │   ├── NoteBar.kt
│   │   └── ArErrorOverlays.kt
│   ├── components/          # komponen dipakai >1 layar
│   │   └── PlaqueTitle.kt
│   └── theme/               # warna, font, typography
└── ...
```

### Aturan boundary

| Folder | Boleh bergantung pada | Tidak boleh |
|---|---|---|
| `ui/` | `data/`, `ar/`, `audio/` | ARCore/SceneView/SoundPool langsung |
| `ar/` | `data/` | `ui/`, `audio/` |
| `audio/` | `data/` | `ui/`, `ar/` |
| `data/` | Android SDK minimal | `ui/`, `ar/`, `audio/` |

Contoh: `ui/ar/ArScreen.kt` memanggil `ArController` dan `NotePlayer`, bukan
`ARSceneView` atau `SoundPool` langsung.

## Folder resource Android

Lokasi: `app/src/main/res/`

| Folder | Isi |
|---|---|
| `raw/` | sampel audio `.ogg` (`saron_pelog_1..7.ogg`) |
| `drawable-nodpi/` | gambar tanpa scaling: background, ikon alat, marker |
| `font/` | Alfa Slab One, Nunito |
| `mipmap-xxxhdpi/` | ikon launcher |
| `values/` | `strings.xml`, `themes.xml` |
| `xml/` | `file_paths.xml` untuk FileProvider |

## Folder assets

Lokasi: `app/src/main/assets/`

| Folder | Isi |
|---|---|
| `models/` | 6 model `.glb` |
| `markers/` | marker A4 21 cm |
| `audio/` | lisensi dan NOTICE audio (bukan file suara) |

File suara runtime ada di `res/raw/`, bukan `assets/`.

## Unit test

Lokasi: `app/src/test/java/com/gemaar/`

Contoh:
- `data/ModelScaleTest.kt`
- `data/SaronNoteMapTest.kt`
- `ar/MarkerVisibilityTest.kt`
- `ar/NoteIndexTest.kt`

Jalankan:

```bash
./gradlew testDebugUnitTest          # Linux/macOS
gradlew.bat testDebugUnitTest        # Windows
```

## File build

| File | Fungsi |
|---|---|
| `settings.gradle.kts` | nama project + modul |
| `build.gradle.kts` | plugin level project |
| `app/build.gradle.kts` | SDK, dependency, Compose |
| `gradle/libs.versions.toml` | versi dependency |
| `gradle/wrapper/` | versi Gradle |
| `local.properties` | lokasi Android SDK (lokal, jangan commit) |

## Jangan diedit manual

- `app/build/` — hasil build.
- `.gradle/` — cache Gradle.
- `local.properties` — dibuat Android Studio / lokal.
- File generated lain di `build/`.

## Untuk F-03

File baru yang akan ditambahkan:

```
data/Instrument.kt
data/Catalog.kt
data/InstrumentStore.kt
ui/ar/CatalogOverlay.kt
res/drawable-nodpi/ikon_*.png
```

Lokasi detail dan urutan kerja ada di
`docs/specs/F-03-catalog/team-guide.md`.
