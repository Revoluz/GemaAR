# Spec: F-02 — Ketuk alat musik untuk membunyikan nada

**Feature ID:** F-02
**Status:** draft
**Owner:** product + engineering

---

## Problem

Setelah F-01, model Saron bisa muncul dan menempel di marker, tetapi alat itu
masih diam. Pengguna belum bisa mendengar nada, mencoba memukul bilah, atau
merasakan pengalaman “memainkan” alat musik. Pengalaman berhenti di visual.

## Why now

Bunyi adalah inti dari “mengenal dan memainkan alat musik”. Tanpa nada, AR hanya
menjadi penampil model. F-02 juga menjadi fondasi data nada untuk F-03 (katalog),
F-04 (tangga nada/oktaf), F-06 (progres dimainkan), dan F-07 (volume musik).

## User stories

### US1 — Mengetuk bilah Saron (Priority: P1)

Pengguna mengetuk bagian alat yang terlihat di marker dan mendengar nada yang
sesuai dengan posisi bilah.

- **Why this priority:** ini pengalaman utama F-02; tanpa ini fitur belum ada.
- **Independent test:** tempatkan Saron di marker, ketuk bilah paling kiri,
  lalu bilah paling kanan; dengarkan dua nada berbeda.

**Acceptance scenarios:**

1. **Given** Saron sudah tampil di marker, **Then** tabuh tidak menutupi bilah
   dan ketujuh bilah terlihat.
2. **Given** Saron sudah tampil di marker, **When** pengguna mengetuk bilah
   paling kiri, **Then** nada 1 (paling rendah) berbunyi dan label **“Nada 1”**
   muncul.
3. **Given** Saron sudah tampil, **When** pengguna mengetuk ketujuh bilah dari
   kiri ke kanan, **Then** ketujuh nada berbunyi berurutan dari rendah ke tinggi.
4. **Given** pengguna mengetuk rangka, **When** ketukan masih berada di area
   model, **Then** nada dari bilah terdekat berbunyi.
5. **Given** pengguna mengetuk beberapa bilah dengan cepat, **When** jeda antar
   ketukan kurang dari dua detik, **Then** nada-nada boleh tumpang tindih dan
   aplikasi tidak berhenti.
6. **Given** marker hilang dari kamera, **When** pengguna mengetuk layar,
   **Then** tidak ada nada baru yang berbunyi.
7. **Given** model belum selesai dimuat, **When** pengguna mengetuk layar,
   **Then** tidak ada nada yang berbunyi dan aplikasi tidak berhenti.

### US2 — Memainkan nada lewat baris nada (Priority: P1)

Pengguna bisa memainkan nada tertentu lewat tujuh tombol nada yang jelas, tanpa
harus menebak posisi bilah.

- **Why this priority:** memberi input yang pasti, menampilkan pemetaan nada,
  dan menjadi tempat umpan balik “nada yang sedang berbunyi”.
- **Independent test:** dengan Saron tampil, ketuk tombol **3**; nada 3 berbunyi
  dan tombol 3 menyala.

**Acceptance scenarios:**

1. **Given** Saron sudah tampil di marker, **When** model siap dimainkan,
   **Then** baris nada **1–7** muncul di bagian bawah layar.
2. **Given** baris nada terlihat, **When** pengguna memilih tombol **3**,
   **Then** nada 3 berbunyi, tombol 3 menyala, dan label **“Nada 3”** muncul.
3. **Given** baris nada terlihat, **When** pengguna mengetuk tombol 1 sampai 7
   secara berurutan, **Then** setiap tombol membunyikan nada yang berbeda dan
   tombol yang ditekan menyala.
4. **Given** marker hilang, **When** layar kembali ke keadaan mencari marker,
   **Then** baris nada disembunyikan dan tidak ada nada baru yang bisa dimainkan.
5. **Given** berkas suara belum siap, **Then** tombol nada tampil nonaktif dan
   tidak membunyikan apa pun.

### US3 — Menangani suara yang gagal dimuat (Priority: P2)

Pengguna tetap mendapat penjelasan yang jelas saat audio tidak bisa dipakai.

- **Why this priority:** mencegah layar terlihat normal tetapi tidak berbunyi
  tanpa sebab.
- **Independent test:** jalankan aplikasi tanpa berkas suara yang valid; layar AR
  tetap terbuka dan menampilkan pesan.

**Acceptance scenarios:**

1. **Given** berkas suara gagal dimuat, **When** Saron tampil di marker,
   **Then** muncul pesan **“Audio tidak bisa dimuat”** dan baris nada nonaktif.
2. **Given** audio gagal, **When** pengguna mengetuk model atau baris nada,
   **Then** aplikasi tidak berhenti dan tidak ada suara yang diputar.

---

## Edge cases

- Marker hilang saat nada masih berdering → nada yang sudah berbunyi boleh
  melanjutkan decay, tetapi tidak ada nada baru.
- Pengguna keluar dari layar AR atau aplikasi masuk background → semua suara
  berhenti; saat kembali, nada bisa dimainkan lagi.
- Ketukan di luar model (background kamera) → tidak ada nada.
- Ketukan sangat cepat pada nada yang sama → nada boleh tumpang tindih.
- Model belum tampil / marker belum terdeteksi → baris nada tidak tampil.
- Volume perangkat nol atau mode senyap → umpan balik visual tetap muncul.
- Ketukan dengan dua jari sekaligus → hanya satu nada per ketukan; akor tidak
  didukung di F-02.
- Orientasi landscape; layar tidak berubah saat perangkat diputar.

## Functional requirements

- **FR-001**: Sistem MUST memakai **Saron** sebagai satu-satunya alat yang bisa
  dimainkan di F-02.
- **FR-002**: Saron MUST memiliki **tujuh nada Pelog** dalam satu oktaf.
- **FR-003**: Urutan bilah MUST dari kiri ke kanan = nada 1 sampai 7, rendah ke
  tinggi.
- **FR-004**: Ketukan di area model MUST membunyikan nada dari bilah terdekat.
- **FR-005**: Sistem MUST menampilkan baris nada **1–7** saat Saron siap
  dimainkan, dan menyembunyikannya saat marker/model tidak tersedia.
- **FR-006**: Setiap tombol baris nada MUST membunyikan nada yang sesuai.
- **FR-007**: Nada yang sedang berbunyi MUST ditandai dengan tombol menyala dan
  label nada.
- **FR-008**: Beberapa nada MUST boleh berbunyi bersamaan saat diketuk beruntun.
- **FR-009**: Sistem MUST berhenti membunyikan nada saat pengguna keluar dari
  layar AR atau aplikasi masuk background.
- **FR-010**: Jika audio gagal dimuat, sistem MUST menampilkan pesan yang jelas
  dan menonaktifkan input nada, tanpa berhenti.
- **FR-011**: F-02 MUST NOT menambah alat lain, pemilih tangga nada/oktaf,
  pengaturan volume, panel Info, atau progres koleksi.
- **FR-012**: Umpan balik F-02 MUST berupa highlight tombol dan label nada; tidak
  ada getar atau pewarnaan bilah 3D.
- **FR-013**: Model Saron yang dipakai F-02 MUST tidak menampilkan tabuh yang
  menutupi area bilah.

## Key entities

- **Instrument**: Saron; memiliki nama, model, dan kumpulan nada.
- **Note**: satu nada bernama (1–7) dengan label dan berkas suara.
- **NoteMap**: pemetaan posisi bilah ke Note; F-02 memakai urutan kiri ke kanan.
- **Scale**: susunan nada Pelog yang dipakai Saron di F-02.
- **Playback**: satu kejadian memainkan Note; beberapa Playback boleh tumpang
  tindih.
- **Sample**: berkas suara pendek untuk satu Note.

## Success criteria

- **SC-001**: Ketujuh bilah menghasilkan tujuh nada berbeda saat diuji manual.
- **SC-002**: Nada berbunyi ≤ 150 ms setelah ketukan pada perangkat target.
- **SC-003**: Lima ketukan cepat dalam dua detik berbunyi tanpa crash.
- **SC-004**: Ketujuh tombol baris nada membunyikan nada yang benar (7/7).
- **SC-005**: Tidak ada suara tersisa setelah keluar dari layar AR.
- **SC-006**: Marker hilang/muncul tiga kali berturut-turut tidak memicu nada
  hantu atau crash.
- **SC-007**: Audio gagal dimuat tidak membuat aplikasi berhenti.
- **SC-008**: Ketujuh bilah terlihat tanpa terhalang tabuh.

## Non-goals

- Alat selain Saron (F-03).
- Slendro, pemilihan pathet, perpindahan oktaf (F-04).
- Pengaturan volume musik (F-07).
- Panel Info budaya (F-05).
- Progres alat yang sudah dimainkan (F-06).
- Getar, highlight per bilah 3D, atau animasi tabuh.
- Akor, multi-touch, perekaman, notasi, atau pelajaran musik.
- Sintesis suara atau editor audio.

## Assumptions

- Saron dari F-01 sudah tampil dan mengikuti marker.
- Model F-02 memakai Saron tanpa tabuh di area bilah (tabuh dihapus dari aset).
- Tujuh sampel Saron Pelog tersedia dan berlisensi.
- Perangkat target memakai speaker/headphone dan volume tidak nol.
- Orientasi landscape.
- F-02 memakai Pelog tetap; perubahan tangga nada/oktaf ditunda ke F-04.
- Baris nada adalah bagian dari layar AR, bukan layar baru.

## Open questions

- Tidak ada.

---

## Checklist

- [x] Tidak ada nama teknologi/library
- [x] Setiap acceptance scenario bisa diamati dari luar
- [x] Setiap FR punya cara verifikasi
- [x] Tidak ada `[NEEDS CLARIFICATION]` tersisa
- [x] Non-goals eksplisit
- [x] Tidak ada fitur spekulatif
