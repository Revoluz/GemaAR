# Menghubungkan HP Android

Fitur AR membutuhkan **HP/tablet fisik** yang mendukung ARCore. Emulator bisa
untuk UI, tetapi AR tidak akan berjalan normal.

Device target project: **Samsung Galaxy Tab S9 (SM-X710)**.

## 1. Aktifkan Developer Options

1. Buka **Settings > About phone/tablet**.
2. Ketuk **Build number** 7 kali.
3. Masukkan PIN jika diminta.
4. Kembali ke Settings → **Developer options**.

## 2. Aktifkan USB debugging

Di **Developer options**:

- aktifkan **USB debugging**;
- (Samsung) aktifkan **Install via USB** jika ada;
- (Xiaomi/Redmi) aktifkan **Install via USB** dan **USB debugging (Security settings)**;
- (Oppo/Realme) aktifkan **Disable permission monitoring** jika diperlukan.

## 3. Sambungkan kabel

1. Pakai kabel **data**, bukan kabel charge-only.
2. Pilih mode USB **File transfer / MTP** (bukan charging only).
3. Di HP muncul dialog **Allow USB debugging?** → centang **Always allow** →
   **Allow/OK**.

## 4. Cek dari komputer

```bash
adb devices
```

Contoh hasil benar:

```
List of devices attached
RR2WA00019D     device
```

Kalau muncul `unauthorized`:
- buka dialog di HP dan izinkan;
- atau **Revoke USB debugging authorizations** lalu colok ulang.

Kalau kosong:
- cek kabel/mode USB;
- install driver (Windows);
- coba port USB lain;
- restart `adb`.

## 5. Install dan buka app

```bash
# Linux/macOS
./gradlew installDebug
adb shell am start -n com.gemaar/.MainActivity

# Windows
gradlew.bat installDebug
adb shell am start -n com.gemaar/.MainActivity
```

Atau klik **Run** di Android Studio.

Package name: `com.gemaar`
Activity: `com.gemaar.MainActivity`

## 6. Melihat log

```bash
adb logcat | grep -E "GemaArMarker|GemaArAudio|AndroidRuntime"
```

Windows PowerShell:

```powershell
adb logcat | Select-String "GemaArMarker|GemaArAudio|AndroidRuntime"
```

## 7. Wireless debugging (opsional)

Android 11+:

1. Developer options → **Wireless debugging** → aktifkan.
2. **Pair device with pairing code**.
3. Di komputer:
   ```bash
   adb pair <IP>:<PORT_PAIRING>
   adb connect <IP>:<PORT>
   ```
4. Cek `adb devices`.

## 8. Driver Windows

Windows kadang butuh driver:

| Merek | Driver |
|---|---|
| Google/Pixel | Google USB Driver (SDK Manager) |
| Samsung | Samsung Android USB Driver |
| Xiaomi | Xiaomi USB Driver / Mi Assistant |
| Oppo/Realme | OEM driver |

Cek **Device Manager** → apakah ada tanda seru di perangkat Android.

## 9. Kalau app tidak muncul / suspended

Di sebagian tablet Samsung, sideloaded app bisa berstatus `suspended`.

Cek:
```bash
adb shell dumpsys package com.gemaar | grep suspended
```

Perbaikan cepat:
```bash
adb uninstall com.gemaar
./gradlew installDebug
```

atau:
```bash
adb shell pm unsuspend com.gemaar
adb shell pm enable com.gemaar
```

## 10. Catatan AR

- App meminta izin kamera saat pertama membuka AR.
- Kalau izin ditolak: **Buka Pengaturan** → izinkan kamera.
- ARCore harus terpasang. Di perangkat tanpa ARCore, app menampilkan pesan
  “ARCore tidak tersedia”.
- Marker A4 harus dicetak/ditampilkan dengan lebar **21 cm**.
