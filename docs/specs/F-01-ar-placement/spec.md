# Spec: F-01 — Tempatkan satu alat musik di marker

**Feature ID:** F-01
**Status:** review
**Owner:** product + engineering

---

## Problem

Saat ini aplikasi hanya bisa menampilkan model 3D di dalam layar (viewer biasa).
Pengguna belum bisa melihat alat musik seolah-olah berada di dunia nyata, dan
belum ada panduan marker yang jelas.

## Why now

F-01 adalah inti pengalaman AR: tanpa penempatan model di marker, fitur audio
(F-02), katalog (F-03), dan tangga nada (F-04) tidak punya panggung.

## User stories

### US1 — Petunjuk sebelum masuk AR (Priority: P1)

Pengguna baru melihat cara memakai marker sebelum kamera AR aktif.

- **Why this priority:** mencegah kebingungan “kamera aktif tapi tidak ada objek”.
- **Independent test:** buka aplikasi pertama kali → Mulai → Petunjuk muncul.

**Acceptance scenarios:**

1. **Given** aplikasi baru dibuka, **When** pengguna memilih **Mulai**,
   **Then** layar AR terbuka dengan overlay **Petunjuk**.
2. **Given** Petunjuk terbuka, **When** pengguna memilih **Mulai AR**,
   **Then** Petunjuk tertutup dan kamera AR aktif.
3. **Given** Petunjuk sudah pernah ditutup, **When** pengguna memilih Mulai lagi,
   **Then** Petunjuk tidak muncul otomatis; tombol **“?”** membukanya kembali.
4. **Given** Petunjuk terbuka, **When** pengguna menutupnya,
   **Then** pengguna kembali ke layar AR tanpa memulai ulang aplikasi.

### US2 — Menempatkan Saron di marker (Priority: P1)

Pengguna mengarahkan kamera ke satu marker A4 dan melihat model Saron menempel
di atas marker.

- **Why this priority:** ini tujuan utama F-01.
- **Independent test:** cetak marker A4 → Mulai → Mulai AR → arahkan kamera.

**Acceptance scenarios:**

1. **Given** kamera aktif dan marker belum terlihat, **Then** muncul hint
   “Arahkan kamera ke marker”.
2. **Given** kamera diarahkan ke marker A4, **When** marker dikenali,
   **Then** model Saron muncul menempel pada marker dalam ≤ 2 detik.
3. **Given** model Saron tampil, **When** marker digeser di meja,
   **Then** model ikut bergeser mengikuti marker.
4. **Given** model Saron tampil, **When** marker keluar dari kamera,
   **Then** model disembunyikan dan hint muncul kembali.
5. **Given** model disembunyikan, **When** marker kembali terlihat,
   **Then** model muncul lagi di posisi marker saat itu.

### US3 — Unduh / cetak marker (Priority: P2)

Pengguna bisa mendapatkan file marker untuk dicetak.

- **Independent test:** buka Petunjuk → tekan **Unduh Marker**.
- **Acceptance:**
  1. **Given** Petunjuk terbuka, **When** pengguna memilih **Unduh Marker**,
     **Then** muncul share sheet dengan file PNG marker.
  2. **Given** share sheet muncul, **When** pengguna menyimpan/mengirim file,
     **Then** file marker bisa dibuka dan dicetak.

### US4 — Error state yang jelas (Priority: P2)

- **Acceptance:**
  1. **Given** izin kamera ditolak, **Then** muncul pesan + tombol
     **Buka Pengaturan**; aplikasi tidak crash.
  2. **Given** perangkat tidak mendukung AR, **Then** muncul pesan yang jelas;
     aplikasi tidak crash.
  3. **Given** model gagal dimuat, **Then** muncul pesan + tombol **Coba lagi**.

## Edge cases

- Marker terlihat sebagian → jangan menampilkan model sampai tracking stabil.
- Marker terdeteksi tetapi model masih dimuat → tampilkan loading, bukan layar kosong.
- Aplikasi masuk background saat AR aktif → sesi berhenti; saat kembali, marker
  harus bisa terdeteksi lagi.
- Cahaya redup / marker silau → tampilkan hint, jangan crash.
- Hanya satu marker; tidak ada marker per alat.

## Functional requirements

- **FR-001**: Sistem MUST memakai satu marker A4 dengan lebar fisik 21 cm.
- **FR-002**: Sistem MUST menampilkan model **Saron** sebagai alat default F-01.
- **FR-003**: Model MUST menempel pada marker dan mengikuti pergerakan marker.
- **FR-004**: Model MUST disembunyikan saat marker hilang, dan muncul lagi saat
  marker terdeteksi kembali.
- **FR-005**: Petunjuk MUST tampil otomatis sekali, dan bisa dibuka lagi lewat “?”.
- **FR-006**: Pengguna MUST bisa membagikan/menyimpan file PNG marker.
- **FR-007**: Sistem MUST menangani izin kamera, AR tidak tersedia, dan model gagal dimuat.
- **FR-008**: F-01 MUST NOT mengaktifkan katalog, note bar, pemilih tangga nada,
  panel Info, koleksi, atau pengaturan. Tombol yang belum aktif boleh tampil
  nonaktif mengikuti desain; note bar/scale tidak ditampilkan.
- **FR-009**: Tombol kembali dari AR MUST kembali ke Beranda dengan konfirmasi
  bila sesi AR aktif.

## Key entities

- **Marker**: satu gambar A4 yang dikenali kamera.
- **Instrument**: Saron, dengan model 3D dan identitas.
- **Placement**: posisi model yang terikat pada marker.
- **AR Session**: masa kamera aktif dan tracking berjalan.

## Success criteria

- **SC-001**: Model muncul ≤ 2 detik setelah marker terdeteksi (device target).
- **SC-002**: Model tetap menempel saat marker digeser; tidak terlihat melayang.
- **SC-003**: Skenario marker hilang → muncul lagi berhasil 3× berturut-turut.
- **SC-004**: Tidak ada crash pada izin ditolak / AR tidak tersedia / model gagal.
- **SC-005**: File marker bisa dibagikan dan dibuka dari aplikasi lain.

## Non-goals

- Katalog dan ganti alat (F-03).
- Audio/note bar (F-02).
- Pemilih Pelog/Slendro dan oktaf (F-04).
- Panel Info budaya (F-05).
- Koleksi/status dimainkan (F-06).
- Pengaturan volume (F-07).
- Marker per alat, marker-based multiplayer, atau cloud anchor.

## Assumptions

- Marker dicetak di kertas A4, lebar area marker 21 cm.
- Model `saron.glb` sudah tersedia di `assets/models/`.
- Device target: Galaxy Tab S9 (ARCore-supported).
- Orientasi landscape.
- Marker F-01 memakai `marker_placeholder.png`; artwork final bisa diganti
  tanpa mengubah perilaku.

## Open questions

- Artwork marker final belum ditetapkan. F-01 memakai placeholder yang sudah
  ada; penggantian artwork tidak mengubah acceptance criteria.

---

## Checklist

- [x] Tidak ada nama teknologi/library di acceptance criteria
- [x] Setiap acceptance scenario bisa diamati dari luar
- [x] Setiap FR punya cara verifikasi
- [x] Tidak ada `[NEEDS CLARIFICATION]` tersisa
- [x] Non-goals eksplisit
- [x] Tidak ada fitur spekulatif
