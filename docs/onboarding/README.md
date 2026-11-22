# Onboarding Tim — GemaAR

Dokumentasi ini untuk anggota tim yang baru memulai project GemaAR, termasuk
yang memakai **Windows**.

## Urutan baca

1. [`01-struktur-project.md`](01-struktur-project.md) — folder dan file Kotlin.
2. [`02-run-android-studio.md`](02-run-android-studio.md) — cara standar menjalankan app.
3. [`03-run-vscode-cli.md`](03-run-vscode-cli.md) — edit di VS Code + build lewat terminal.
4. [`04-hubungkan-hp.md`](04-hubungkan-hp.md) — USB debugging, adb, install.
5. [`05-setup-windows.md`](05-setup-windows.md) — setup lengkap di Windows.
6. [`06-troubleshooting.md`](06-troubleshooting.md) — error umum.
7. [`07-git-workflow.md`](07-git-workflow.md) — commit, push, handoff, konflik.

## Jawaban singkat

| Pertanyaan | Jawaban |
|---|---|
| Harus Android Studio? | **Disarankan ya.** Android Studio adalah IDE resmi Android dan paling mudah untuk build/run/debug. |
| Bisa VS Code saja? | Bisa untuk **edit kode**, tetapi build/run tetap lewat Gradle + ADB di terminal. Debugging terbatas. |
| Bisa di Windows? | Bisa. Gunakan `gradlew.bat`, Android Studio, dan Git Bash untuk script `init.sh`. |
| Bisa di emulator? | UI bisa, tetapi fitur AR (F-01/F-02/F-03 di layar AR) butuh HP fisik dengan ARCore. |
| Perlu install Gradle? | Tidak. Project memakai Gradle Wrapper (`gradlew` / `gradlew.bat`). |

## Quick start

### Windows (cmd/PowerShell)

```bat
git clone https://github.com/Revoluz/GemaAR.git
cd GemaAR
gradlew.bat assembleDebug
```

Buka project di Android Studio, pilih device, lalu **Run**.

### Linux/macOS

```bash
git clone https://github.com/Revoluz/GemaAR.git
cd GemaAR
./gradlew assembleDebug
```

## Command penting

| Tujuan | Windows | Linux/macOS |
|---|---|---|
| Build debug | `gradlew.bat assembleDebug` | `./gradlew assembleDebug` |
| Unit test | `gradlew.bat testDebugUnitTest` | `./gradlew testDebugUnitTest` |
| Lint | `gradlew.bat lintDebug` | `./gradlew lintDebug` |
| Install ke HP | `gradlew.bat installDebug` | `./gradlew installDebug` |
| Cek dokumen | `bash init.sh --check` (Git Bash) | `bash init.sh --check` |

## Alur kerja tim

Karena dua orang mengerjakan file yang sama:

1. **Kerjakan berurutan**, bukan bersamaan.
2. Sebelum mulai: `git pull origin main`.
3. Selesai satu langkah: build, lalu commit.
4. Jangan commit:
   - `local.properties`
   - `app/build/`
   - `.gradle/`
5. Setelah selesai bagianmu, tulis handoff di `progress.md`.

## File penting di root

| File | Isi |
|---|---|
| `AGENTS.md` | aturan project dan command |
| `progress.md` | memori antar sesi / handoff |
| `feature_list.json` | status fitur |
| `ROADMAP.md` | urutan pekerjaan |
| `docs/specs/` | spec, plan, tasks per fitur |
| `docs/onboarding/` | dokumen ini |
