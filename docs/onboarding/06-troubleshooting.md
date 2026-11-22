# Troubleshooting

## Build

### `JAVA_HOME is not set` / `Unsupported class file major version`

Penyebab: JDK salah atau belum diset.

```bash
java -version
```

Gunakan **JDK 21+**. Set `JAVA_HOME` ke JDK tersebut, buka terminal baru.

Android Studio:
`Settings > Build, Execution, Deployment > Build Tools > Gradle > Gradle JDK` →
pilih 21.

### `SDK location not found`

Buat `local.properties` di root:

**Windows**
```properties
sdk.dir=C\:\\Users\\NamaKamu\\AppData\\Local\\Android\\Sdk
```

**Linux/macOS**
```properties
sdk.dir=/home/nama/Android/Sdk
```

### `compileSdk 37` / SDK Platform tidak ditemukan

Buka SDK Manager → install **Android SDK Platform 37 / 37.2** dan
**Build-Tools**. Jika versi minor belum ada, update Android Studio/SDK Manager.

### Gradle sync gagal / AGP 9.5.2 tidak didukung

Project memakai AGP 9.5.2. Gunakan Android Studio versi terbaru yang
mendukungnya. Cek `gradle/libs.versions.toml`.

### Build aneh setelah pull

Coba:

```bash
./gradlew --stop
./gradlew clean assembleDebug
```

Windows: `gradlew.bat --stop` lalu `gradlew.bat clean assembleDebug`.

Jangan hapus `.gradle` kecuali perlu.

## ADB dan HP

### `adb: command not found`

Tambahkan platform-tools ke PATH:

**Windows**
```
%LOCALAPPDATA%\Android\Sdk\platform-tools
```

**Linux/macOS**
```bash
export PATH="$HOME/Android/Sdk/platform-tools:$PATH"
```

### `adb devices` kosong

- kabel data, bukan charge-only;
- mode USB **File transfer/MTP**;
- USB debugging aktif;
- driver OEM (Windows);
- coba port/kabel lain;
- `adb kill-server` lalu `adb start-server`.

### `unauthorized`

Di HP: **Allow USB debugging** → **Always allow**.

Kalau tidak muncul:
Developer options → **Revoke USB debugging authorizations** → colok ulang.

### `device offline`

Cabut-colok, restart adb:

```bash
adb kill-server
adb start-server
adb devices
```

### `INSTALL_FAILED_UPDATE_INCOMPATIBLE`

APK lama beda signature. Uninstall dulu:

```bash
adb uninstall com.gemaar
./gradlew installDebug
```

### App menampilkan `SuspendedAppActivity`

Sebagian tablet Samsung men-suspend app sideload. Coba:

```bash
adb shell pm unsuspend com.gemaar
adb shell pm enable com.gemaar
```

atau uninstall lalu install ulang.

## AR

### `ARCore tidak tersedia`

- HP harus mendukung ARCore;
- install/update **Google Play Services for AR**;
- emulator biasanya tidak mendukung AR.

### Izin kamera ditolak

Di app: **Buka Pengaturan** → izinkan kamera → kembali.

### Kamera hitam

- ruangan terlalu gelap;
- kamera tertutup;
- app lain sedang memakai kamera;
- coba keluar/masuk AR.

### Marker tidak terdeteksi

- cetak/display marker **21 cm**;
- permukaan datar, tidak silau;
- seluruh marker terlihat;
- jarak 30–80 cm;
- cahaya cukup.

### Model tidak muncul

- marker belum `FULL_TRACKING`;
- model masih dimuat;
- cek Logcat `GemaArMarker`.

## Audio

### Tidak ada suara

- volume media perangkat;
- mode senyap;
- cek Logcat `GemaArAudio`;
- pastikan 7 sampel loaded (`ready: 7 samples loaded`).

### Suara alat lama masih berbunyi setelah ganti alat

Pastikan `notePlayer.stopAll()` dipanggil saat memilih alat.

## Script bash

### `init.sh: command not found` / `syntax error`

Windows: jalankan lewat **Git Bash**:

```bash
bash init.sh --check
bash scripts/verify-docs.sh
```

Jangan lewat cmd/PowerShell biasa.

### `Permission denied` saat `./gradlew`

Linux/macOS:

```bash
chmod +x gradlew
```

Windows: pakai `gradlew.bat`.

## Git

### `git pull` konflik

Karena dua orang mengerjakan file yang sama:

1. commit pekerjaan sendiri;
2. `git pull --rebase origin main`;
3. selesaikan konflik;
4. build ulang;
5. push.

Lebih aman: kerja **berurutan**, bukan bersamaan.

### File yang tidak boleh di-commit

- `local.properties`
- `app/build/`
- `.gradle/`

Cek dengan:

```bash
git status
```

## Log yang berguna

```bash
adb logcat | grep -E "GemaArMarker|GemaArAudio|AndroidRuntime"
```

Windows PowerShell:

```powershell
adb logcat | Select-String "GemaArMarker|GemaArAudio|AndroidRuntime"
```
