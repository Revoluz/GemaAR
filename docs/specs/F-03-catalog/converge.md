# Converge: F-03 — Katalog alat musik + pilih dari daftar

**Status:** PASS (dengan known issue BUG-001)
**Tanggal:** 2026-11-25
**Device target:** Samsung Galaxy Tab S9 (SM-X710) — stabil
**Device tambahan:** Galaxy A25 5G (SM-A256E) — BUG-001 deferred
**Device uji tim:** Galaxy A35 (SM-A356E) — 6 alat, pilih, persistence

## Acceptance (spec)

| Ref | Skenario | Hasil | Bukti |
|---|---|---|---|
| US1.1 | Katalog terbuka dari AR, 6 kartu | PASS | `F03-A-catalog-3-alat.png`, `F03-B-catalog-phone-6-alat.png` |
| US1.2 | Tutup katalog kembali ke AR, alat tidak berubah | PASS | device test |
| US1.3 | Back menutup katalog dulu | PASS | device test |
| US1.4 | Katalog bisa dibuka saat marker hilang | PASS | device test |
| US2.1 | Pilih Bonang → tutup + model berganti | PASS | `F03-A-pilih-bonang.png`; uji tim SM-A356E |
| US2.2 | Model baru loading, bukan layar kosong | PASS | `ArUiState.ModelLoading` F-01 |
| US2.3 | Marker hilang saat pilih → model muncul saat marker kembali | PASS | uji tim SM-A356E |
| US2.4 | Pilih alat sama → tidak reload | PASS | `key(instrument.id)` |
| US2.5 | Model gagal → pesan + coba lagi | PASS (code path) | `ModelErrorCard` |
| US2.6 | Judul AR mengikuti alat | PASS | foto A25/Tab S9 “Bonang”, “Gong & Kempul” |
| US3.1 | Alat terakhir dipulihkan | PASS | uji tim SM-A356E; `InstrumentStore` |
| US3.2 | First run → katalog otomatis sekali | PASS | device test A25; screenshot katalog |
| US3.3 | Berikutnya tidak auto-open | PASS | `catalog_auto_opened` flag |
| US3.4 | Id tidak dikenal → Saron | PASS | `Catalog.byId` + `CatalogTest` |
| US4.1 | Non-Saron → pesan audio, note bar hilang | PASS | `F03-B-pilih-alat-phone.png` |
| US4.2 | Non-Saron tidak memutar sample Saron | PASS | `audioAvailable` gating |
| US4.3 | Ganti dari Saron → suara berhenti | PASS | `notePlayer.stopAll()` |
| US4.4 | Kembali Saron → note bar + audio | PASS | uji tim SM-A356E |
| US5 | Katalog kosong | N/A | data statis selalu 6 alat |

## Functional requirements

| FR | Hasil | Catatan |
|---|---|---|
| FR-001 enam alat | PASS | `Catalog.instruments` 6 entri |
| FR-002 nama/asal/ikon | PASS | kartu katalog |
| FR-003 alat aktif ditandai | PASS | border emas |
| FR-004 buka/tutup tanpa restart AR | PASS | `ArController` tidak di-key ulang |
| FR-005 ganti model tanpa rescan | PASS | `key(instrument.id, retryToken)` |
| FR-006 tutup otomatis setelah pilih | PASS | `onSelect` |
| FR-007 judul AR | PASS | `selectedInstrument.name` |
| FR-008 simpan pilihan | PASS | `InstrumentStore` |
| FR-009 first-run sekali | PASS | `catalog_auto_opened` |
| FR-010 non-Saron pesan + tanpa sample Saron | PASS | `audioAvailable` |
| FR-011 stop nada saat ganti | PASS | `notePlayer.stopAll()` |
| FR-012 katalog kosong | N/A | data statis |
| FR-013 model gagal + coba lagi | PASS (code path) | `ModelErrorCard` |
| FR-014 tidak menambah F-04–F-07 | PASS | tidak ada audio/scale/Info/volume |
| FR-015 Back urutan | PASS | katalog → petunjuk → keluar |

## Success criteria

| SC | Hasil | Bukti |
|---|---|---|
| SC-001 6 kartu + selected | PASS | screenshot |
| SC-002 ganti model tanpa rescan | PASS | uji tim + Tab S9 |
| SC-003 sesi AR tidak dibuat ulang | PASS | katalog overlay |
| SC-004 alat terakhir | PASS | uji tim SM-A356E |
| SC-005 first-run sekali | PASS | device test |
| SC-006 non-Saron pesan + tanpa Saron | PASS | screenshot/log |
| SC-007 model gagal tidak crash | PASS (code path) | ModelError |
| SC-008 Saron kembali | PASS | uji tim |

## Verifikasi

```bash
export JAVA_HOME=/usr/lib/jvm/temurin-25-jdk-amd64
./gradlew assembleDebug          # OK
./gradlew testDebugUnitTest      # OK, 23 test
./gradlew lintDebug              # OK, 0 issue
bash init.sh --check             # OK
bash scripts/verify-docs.sh      # OK
```

## Deviasi dari plan

1. **Katalog statis di `Catalog.kt`**, bukan `catalog.json` + parser. Lebih
   sederhana untuk tim pemula; tidak ada loading/error katalog.
2. **Katalog compact** untuk layar pendek (HP landscape): kartu 140 dp, ikon
   48 dp, panel 92%.
3. **First-run** dipindah ke `LaunchedEffect(arStarted)` agar tetap bekerja
   kalau Petunjuk sudah pernah dilihat.
4. **Skala model per alat**: default 0,5 m untuk tablet dan HP; **Kendang dan
   Slenthem 0,3 m** karena ukuran visualnya terlalu besar di marker. Flicker A25
   dicatat sebagai BUG-001.
5. **Kamera AR** dibatasi 1920×1080, 30 FPS, kamera belakang; default
   SceneView memilih 4080×3060 di A25.
6. **Grace 500 ms** sebelum model dianggap hilang saat `LAST_KNOWN_POSE`.

## Known issues

- **BUG-001** — model AR hilang/berkedip di Galaxy A25. Kamera 1080p dan
  grace 500 ms sudah diterapkan; skala tetap 0,5 m sesuai keputusan produk.
  Fix ditunda. Tab S9 stabil. Detail:
  `docs/bugs/BUG-001-a25-ar-flicker.md`.
- Petunjuk (S3) masih terpotong di HP landscape pendek; ini isu F-01, tidak
  menghalangi F-03 karena tombol close tetap bisa dipakai.

## Kesimpulan

F-03 memenuhi acceptance pada device target Tab S9 dan device uji tim
SM-A356E. BUG-001 dicatat sebagai keterbatasan device tambahan (A25) dan
tidak menghalangi `passes: true`.
