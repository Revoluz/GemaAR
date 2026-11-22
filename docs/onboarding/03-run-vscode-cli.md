# Menjalankan lewat VS Code + Terminal

VS Code **bisa** dipakai untuk mengedit kode Kotlin, tetapi bukan IDE Android
resmi. Build, install, dan debug tetap lewat **Gradle** dan **ADB** di terminal.

**Rekomendasi:** pakai VS Code untuk menulis kode, lalu Android Studio untuk
Run/Debug kalau ada masalah.

## Yang perlu dipasang

| Kebutuhan | Fungsi |
|---|---|
| VS Code | editor |
| JDK 21+ | compile Kotlin |
| Android SDK + Platform Tools | build + `adb` |
| Git | clone repo |
| Ekstensi Kotlin (opsional) | highlight/syntax |

Ekstensi populer:
- **Kotlin** (mathiasfrohlich)
- **Gradle for Java** (Microsoft) — opsional

Tidak ada ekstensi yang bisa menggantikan Android Studio untuk Run/Debug penuh.

## 1. Buka project

`File > Open Folder` → pilih root `GemaAR`.

Buka terminal VS Code: `Terminal > New Terminal`.

## 2. Set JAVA_HOME

**Windows (PowerShell)**
```powershell
$env:JAVA_HOME = "C:\Program Files\Eclipse Adoptium\jdk-21..."
$env:Path = "$env:JAVA_HOME\bin;$env:Path"
```

**Windows (cmd)**
```bat
set JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-21...
set PATH=%JAVA_HOME%\bin;%PATH%
```

**Linux/macOS**
```bash
export JAVA_HOME=/usr/lib/jvm/temurin-21-jdk-amd64
export PATH="$JAVA_HOME/bin:$PATH"
```

Cek:
```bash
java -version
```

## 3. Build

**Windows**
```bat
gradlew.bat assembleDebug
```

**Linux/macOS**
```bash
./gradlew assembleDebug
```

APK ada di:
```
app/build/outputs/apk/debug/app-debug.apk
```

## 4. Install ke HP

Pastikan HP terhubung dan USB debugging aktif.

**Windows**
```bat
gradlew.bat installDebug
adb shell am start -n com.gemaar/.MainActivity
```

**Linux/macOS**
```bash
./gradlew installDebug
adb shell am start -n com.gemaar/.MainActivity
```

Kalau `adb` tidak dikenal:

**Windows**
```bat
"%LOCALAPPDATA%\Android\Sdk\platform-tools\adb.exe" devices
```

**Linux/macOS**
```bash
~/Android/Sdk/platform-tools/adb devices
```

## 5. Test dan lint

```bash
./gradlew testDebugUnitTest
./gradlew lintDebug
```

Windows: `gradlew.bat ...`.

## 6. Debugging

VS Code tidak punya debugger Android/Compose selengkap Android Studio.
Yang bisa dilakukan:

- `adb logcat` untuk melihat log.
- `adb shell am start` untuk membuka activity.
- Breakpoint Kotlin: terbatas/tidak resmi.

Untuk debug step-by-step, buka project yang sama di Android Studio.

## 7. Kekurangan VS Code

| Hal | VS Code | Android Studio |
|---|---|---|
| Edit Kotlin | ✅ | ✅ |
| Compose preview | ❌ | ✅ |
| Run/Debug Android | lewat terminal | ✅ |
| Layout inspector | ❌ | ✅ |
| Logcat terintegrasi | terbatas | ✅ |
| Refactor Android | terbatas | ✅ |

## Kesimpulan

- **Bisa** kerja di VS Code + terminal.
- Untuk pengalaman standar Android, tetap gunakan **Android Studio**.
- Jangan lupa build setelah setiap perubahan:
  `gradlew.bat assembleDebug` atau `./gradlew assembleDebug`.
