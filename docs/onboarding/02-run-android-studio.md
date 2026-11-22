# Menjalankan di Android Studio

Android Studio adalah cara **paling mudah dan standar** untuk project Android.
Gunakan versi terbaru yang mendukung **AGP 9.5.2** (lihat
`gradle/libs.versions.toml`). Kalau Android Studio menolak versi AGP, update
Android Studio.

## 1. Install yang dibutuhkan

| Kebutuhan | Catatan |
|---|---|
| Android Studio | versi terbaru yang mendukung AGP 9.x |
| JDK 21+ | Android Studio biasanya membawa JBR; untuk terminal install Temurin 21 |
| Android SDK Platform 37 / 37.2 | lewat SDK Manager |
| Android SDK Build-Tools | lewat SDK Manager |
| Platform Tools (`adb`) | lewat SDK Manager |
| Git | untuk clone repo |
| HP Android / emulator | fitur AR butuh HP fisik dengan ARCore |

## 2. Buka project

1. Buka Android Studio.
2. **Open** → pilih folder root `GemaAR` (bukan folder `app`).
3. Tunggu **Gradle Sync** selesai.
4. Jika diminta SDK, biarkan Android Studio mengunduh yang kurang.

## 3. Set Gradle JDK

`File > Settings > Build, Execution, Deployment > Build Tools > Gradle`

- **Gradle JDK**: pilih **21** (atau JBR 21 / JDK 25).
- Jangan pilih JDK 8/11.

Untuk terminal, set `JAVA_HOME` ke JDK yang sama.

## 4. Cek `local.properties`

File ini dibuat otomatis oleh Android Studio. Isinya kira-kira:

**Windows**
```properties
sdk.dir=C\:\\Users\\NamaKamu\\AppData\\Local\\Android\\Sdk
```

**Linux/macOS**
```properties
sdk.dir=/home/nama/Android/Sdk
```

Jangan commit file ini.

## 5. Pilih device

1. Sambungkan HP dengan kabel USB.
2. Aktifkan **USB debugging** (lihat `04-hubungkan-hp.md`).
3. Di toolbar Android Studio, pilih device di dropdown.
4. Kalau HP tidak muncul, klik **Device Manager** atau jalankan `adb devices`.

## 6. Run

Klik tombol **Run ▶** (Shift+F10).

Android Studio akan:
1. build debug,
2. install APK,
3. membuka `MainActivity`.

Atau lewat terminal:

```bash
# Linux/macOS
./gradlew installDebug
adb shell am start -n com.gemaar/.MainActivity

# Windows
gradlew.bat installDebug
adb shell am start -n com.gemaar/.MainActivity
```

## 7. Build/test manual

```bash
./gradlew assembleDebug
./gradlew testDebugUnitTest
./gradlew lintDebug
```

Windows: ganti `./gradlew` dengan `gradlew.bat`.

## 8. Melihat log

Di Android Studio: tab **Logcat**.

Filter yang berguna:

```
GemaArMarker
GemaArAudio
AndroidRuntime
```

## Masalah umum

| Gejala | Perbaikan |
|---|---|
| Gradle sync gagal | Cek JDK 21, koneksi internet, dan versi Android Studio |
| SDK 37 tidak ditemukan | SDK Manager → install Platform 37/37.2 |
| Device tidak muncul | USB debugging, kabel data, driver, `adb devices` |
| `local.properties` hilang | Buat manual atau sync ulang |
| Build lambat | Biarkan Gradle cache; jangan hapus `.gradle` tanpa perlu |
| AR tidak jalan di emulator | Emulator tidak punya ARCore; pakai HP fisik |

Detail error lain: `06-troubleshooting.md`.
