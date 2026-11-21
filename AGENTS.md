# AGENTS.md

## Project
GemaAR — aplikasi Android AR untuk mengenal dan memainkan alat musik tradisional
Indonesia. Pengguna mengarahkan kamera ke satu marker (kartu cetak), model 3D
alat musik yang dipilih muncul di atasnya, lalu mengetuk bagian alat untuk
membunyikan nada. "Done" berarti satu alat musik muncul stabil di atas marker,
dimainkan minimal satu oktaf, dan tidak crash di HP yang mendukung ARCore.

Kotlin + Jetpack Compose + ARCore + SceneView 4.70 (Filament). Build: Gradle KTS.
Toolchain: compileSdk 37, minSdk 26, AGP 9.4+, Kotlin 2.3+, JVM target 21.

## Read this when
| You are doing | Read | Do not read |
|---|---|---|
| Starting a session | `progress.md` (last 40 lines), `feature_list.json`, this file | other features |
| Specifying | `docs/constitution.md`, `docs/specs/_templates/spec.md`, `docs/GLOSSARY.md` | plan, tasks, code |
| Planning | `docs/specs/F-XX/spec.md` | code |
| Tasking | `plan.md` | code |
| Mendesain UI | `docs/design/DESIGN.md`, `SCREENS.md`, `COMPONENTS.md` | code |
| Implementing | **one** task from `tasks.md` | other features |
| Converging | `spec.md` + `tasks.md` | code edits |

**Never read the whole `docs/` folder.**

## Precedence on conflict
```
constitution > AGENTS.md > feature_list.json > spec.md > plan.md > tasks.md
```
`progress.md` is history, not a rule.

## Commands
```bash
# Sync/install:      ./gradlew help
# Build debug:       ./gradlew assembleDebug
# Test all:          ./gradlew testDebugUnitTest
# Test one:          ./gradlew testDebugUnitTest --tests "com.gemaar.*"
# Lint:              ./gradlew lintDebug
# Typecheck:         ./gradlew compileDebugKotlin
# Install ke HP:     ./gradlew installDebug
# Smoke test:        bash init.sh
# Verify invariants: bash scripts/verify-docs.sh
```

## Where things live
| Area | Path |
|---|---|
| Source | `app/src/main/java/com/gemaar/` |
| UI (Compose) | `app/src/main/java/com/gemaar/ui/` |
| AR (SceneView/ARCore) | `app/src/main/java/com/gemaar/ar/` |
| Audio | `app/src/main/java/com/gemaar/audio/` |
| Domain + data | `app/src/main/java/com/gemaar/data/` |
| Model 3D (GLB) | `app/src/main/assets/models/` |
| Sampel suara | `app/src/main/res/raw/` |
| Katalog | `app/src/main/assets/catalog.json` |
| Unit test | `app/src/test/` |
| Instrumented test | `app/src/androidTest/` |
| Generated | `app/build/` — never hand-edit |
| Specs | `docs/specs/` |
| UI design | `docs/design/` |
| Decisions | `docs/decisions/` |
| Feature status | `feature_list.json` |
| Session memory | `progress.md` |

## Boundaries
- `app/build/`, `.gradle/` — generated, never hand-edit.
- Model 3D: GLB, satu model < 10 MB, tekstur maksimal 2048 px.
- Sampel audio: OGG mono 44.1 kHz, satu file < 300 KB.
- `ui/` tidak memanggil ARCore/SceneView langsung; lewat interface di `ar/`.
- `ar/` dan `audio/` tidak saling bergantung; koordinasi lewat `data/`.
- `local.properties` — mesin-lokal, jangan commit.
- Tidak ada backend, login, atau secret di project ini.

## Change protocol
| If you change | You must also |
|---|---|
| Interface `ar/` atau `audio/` | Update contract di `docs/specs/F-XX/plan.md` |
| A dependency | `docs/decisions/NNNN-<slug>.md` |
| Skema `assets/catalog.json` | `docs/ARCHITECTURE.md` + versi skema |
| Asset model/audio baru | `docs/GLOSSARY.md` bila istilah baru |
| Feature acceptance | `docs/specs/F-XX/spec.md` |

## Definition of done
- [ ] Tests for the changed area pass
- [ ] Lint + typecheck clean
- [ ] `bash scripts/verify-docs.sh` passes
- [ ] No changes outside scope
- [ ] `feature_list.json` status updated (only `passes`)
- [ ] `progress.md` appended

## Session start
1. `pwd`
2. Read `progress.md` (last 40 lines)
3. Read `feature_list.json`, pick highest-priority `passes: false`
4. Read that feature's `docs/specs/<feature>/`
5. `bash init.sh`

## Session end
1. Run the feature's verification commands
2. Commit
3. Append to `progress.md` using the handoff format
4. Update `passes` only if converge passed

## Rules
- One task at a time from `tasks.md`.
- Ambiguity → `[NEEDS CLARIFICATION: ...]`, then stop.
- No `passes: true` without converge evidence.
- Spec is read-only after approval; changes go through an explicit step.
