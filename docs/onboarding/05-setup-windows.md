# Setup Project di Windows

Panduan ini untuk teman yang memakai **Windows 10/11**.

## 1. Install aplikasi dasar

| Aplikasi | Link/instruksi |
|---|---|
| Android Studio | https://developer.android.com/studio |
| Git for Windows | https://git-scm.com/download/win (termasuk Git Bash) |
| JDK 21 (Temurin) | https://adoptium.net/ (pilih JDK 21) |
| VS Code (opsional) | https://code.visualstudio.com/ |

Android Studio biasanya sudah membawa JDK. JDK 21 terpisah berguna untuk
terminal dan Gradle.

## 2. Install Android SDK

Buka Android Studio → **More Actions > SDK Manager**:

- **SDK Platforms**: install **Android 37 / 37.2** (sesuai `compileSdk`).
- **SDK Tools**: install
  - Android SDK Build-Tools,
  - Android SDK Platform-Tools,
  - Android SDK Command-line Tools (opsional),
  - Google USB Driver (untuk Pixel; Samsung punya driver sendiri).

Lokasi SDK default:

```
C:\Users\NamaKamu\AppData\Local\Android\Sdk
```

## 3. Set environment variable

Buka **Start → Edit the system environment variables → Environment Variables**.

### JAVA_HOME

Buat **User variable**:

```
JAVA_HOME = C:\Program Files\Eclipse Adoptium\jdk-21.x.x-hotspot
```

Tambahkan ke **Path**:

```
%JAVA_HOME%\bin
```

### Android SDK

Buat:

```
ANDROID_HOME = C:\Users\NamaKamu\AppData\Local\Android\Sdk
```

Tambahkan ke **Path**:

```
%ANDROID_HOME%\platform-tools
%ANDROID_HOME%\emulator
```

Tutup dan buka ulang terminal, lalu cek:

```bat
java -version
adb version
```

## 4. Clone project

Buka **Git Bash** atau **cmd**:

```bash
git clone https://github.com/Revoluz/GemaAR.git
cd GemaAR
```

## 5. Buat `local.properties`

Buat file `local.properties` di root project:

```properties
sdk.dir=C\:\\Users\\NamaKamu\\AppData\\Local\\Android\\Sdk
```

Perhatikan:
- gunakan **double backslash** `\\`;
- ganti `NamaKamu`;
- jangan commit file ini.

Android Studio biasanya membuat file ini otomatis saat pertama sync.

## 6. Build

Di **cmd/PowerShell**:

```bat
gradlew.bat assembleDebug
```

Hasil:

```
app\build\outputs\apk\debug\app-debug.apk
```

Kalau berhasil, lanjut test:

```bat
gradlew.bat testDebugUnitTest
gradlew.bat lintDebug
```

## 7. Jalankan di HP

1. Aktifkan USB debugging (lihat `04-hubungkan-hp.md`).
2. Cek:
   ```bat
   adb devices
   ```
3. Install:
   ```bat
   gradlew.bat installDebug
   adb shell am start -n com.gemaar/.MainActivity
   ```

Atau buka project di Android Studio dan klik **Run**.

## 8. Script bash (`init.sh`)

`init.sh` dan `scripts/verify-docs.sh` adalah **bash script**. Di Windows
jalankan lewat **Git Bash**:

```bash
bash init.sh --check
bash scripts/verify-docs.sh
```

Jangan jalankan `./init.sh` di cmd/PowerShell biasa.

## 9. Git Bash vs cmd

| Perintah | cmd/PowerShell | Git Bash |
|---|---|---|
| Gradle | `gradlew.bat ...` | `./gradlew ...` |
| Bash script | tidak bisa | `bash init.sh --check` |
| `adb` | `adb ...` | `adb ...` |

Gunakan Git Bash untuk perintah yang memakai `./gradlew` atau `bash`.

## 10. Line ending

Jika Git mengubah line ending dan script bash error, set:

```bash
git config --global core.autocrlf input
```

Lalu checkout ulang file yang bermasalah.

## 11. Masalah umum Windows

| Gejala | Perbaikan |
|---|---|
| `JAVA_HOME` tidak ditemukan | Set environment variable, buka terminal baru |
| `SDK location not found` | Buat `local.properties` |
| `adb` tidak dikenal | Tambah `platform-tools` ke `PATH` |
| `gradlew.bat` tidak dikenal | Jalankan dari root project |
| `init.sh` gagal | Jalankan di Git Bash |
| Device tidak muncul | Driver USB, mode MTP, izin USB debugging |
| Build error AGP | Update Android Studio |
| Path mengandung spasi | Gunakan tanda kutip atau pindahkan project ke path sederhana |

## 12. Struktur folder setelah clone

```
GemaAR\
├── app\
├── docs\
├── gradle\
├── gradlew
├── gradlew.bat
├── local.properties   ← dibuat lokal, jangan commit
└── ...
```

Jangan menghapus `gradlew.bat`; itu dipakai Windows.
