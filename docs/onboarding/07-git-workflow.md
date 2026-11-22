# Alur Git untuk Tim 2 Orang

Karena kalian berdua memakai repo yang sama, kerjakan **berurutan** dan sering
commit. Ini menghindari konflik di `Catalog.kt` dan `ArScreen.kt`.

## Sebelum mulai

```bash
git pull origin main
git status
```

Pastikan working tree bersih. Kalau ada perubahan, commit dulu.

## Saat mengerjakan

1. Kerjakan satu langkah kecil (mis. `A2 Instrument.kt`).
2. Build:
   ```bash
   ./gradlew assembleDebug
   ```
3. Cek file yang berubah:
   ```bash
   git status
   git diff
   ```
4. Commit:
   ```bash
   git add app/src/main/java/com/gemaar/data/Instrument.kt
   git commit -m "feat: add Instrument data class"
   ```
5. Push:
   ```bash
   git push origin main
   ```

## Handoff ke teman

Setelah bagianmu selesai:

1. Pastikan build hijau.
2. Push semua commit.
3. Tulis handoff di `progress.md`:
   - apa yang selesai,
   - file yang berubah,
   - apa yang belum,
   - cara menguji.
4. Temanmu mulai dengan:
   ```bash
   git pull origin main
   ```

## Jangan commit

- `local.properties`
- `app/build/`
- `.gradle/`
- file sementara di `/tmp`

Cek `.gitignore`; kalau file ini muncul di `git status`, jangan `git add -A`
tanpa memeriksa.

## Kalau terjadi konflik

Biasanya di `Catalog.kt` atau `ArScreen.kt`.

```bash
git pull --rebase origin main
```

Buka file yang konflik, selesaikan bagian `<<<<<<<`, `=======`, `>>>>>>>`,
lalu:

```bash
git add <file>
git rebase --continue
./gradlew assembleDebug
git push origin main
```

Kalau bingung, jangan force-push. Minta bantuan agent/reviewer.

## Branch (opsional)

Untuk pemula, kerja langsung di `main` boleh selama berurutan. Kalau mau lebih
aman:

```bash
git checkout -b f03-katalog-a
# kerja, commit
git push -u origin f03-katalog-a
```

Lalu buat Pull Request di GitHub. Jangan merge sendiri tanpa review.

## Pesan commit yang baik

```text
feat: add Instrument data class
feat: add catalog overlay
fix: stop Saron audio when switching instrument
docs: add Windows setup guide
test: add catalog fallback test
```

Satu commit = satu tujuan kecil.
