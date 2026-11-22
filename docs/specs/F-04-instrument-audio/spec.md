# Spec: F-04 — Audio semua alat selain Saron

**Feature ID:** F-04
**Status:** draft
**Owner:** product + engineering

> Menggantikan fitur lama “Pilihan tangga nada dan oktaf” yang dihapus dari MVP.

---

## Problem

Setelah F-03, pengguna bisa memilih enam alat dan melihat modelnya, tetapi hanya
Saron yang berbunyi. Lima alat lain tampil diam dengan pesan “Audio belum
tersedia”. Katalog terasa belum selesai, dan pengalaman “memainkan alat musik”
baru berlaku untuk satu alat.

## Why now

F-04 melengkapi inti produk: semua alat di katalog bisa dilihat **dan**
dimainkan. Setelah F-03, data katalog dan pemilihan alat sudah ada; F-04 tinggal
menambahkan nada, pemetaan bagian alat, dan kontrol main per alat.

## User stories

### US1 — Memainkan Bonang dan Gender Barung (Priority: P1)

Pengguna memilih Bonang atau Gender Barung, lalu mengetuk pencon/bilah dan
mendengar nada yang sesuai.

- **Why this priority:** dua alat ini punya banyak bagian; mewakili pengalaman
  metallofon gamelan.
- **Independent test:** pilih Bonang, ketuk beberapa pencon; ulangi untuk Gender.

**Acceptance scenarios:**

1. **Given** Bonang aktif dan model tampil, **Then** semua pencon yang terlihat
   bisa diketuk dan menghasilkan nada berbeda.
2. **Given** Gender Barung aktif, **Then** semua bilah yang terlihat bisa
   diketuk dan menghasilkan nada berbeda.
3. **Given** Bonang/Gender aktif, **Then** baris nada disusun mengikuti dua baris
   alat, bukan baris Saron 1–7.
4. **Given** pengguna mengetuk cepat beberapa bagian, **Then** nada boleh
   tumpang tindih dan aplikasi tidak berhenti.

### US2 — Memainkan Slenthem (Priority: P1)

Pengguna memilih Slenthem dan memainkan bilahnya pada oktaf rendah.

- **Why this priority:** Slenthem adalah alat balungan penting dan sumber
  sample-nya sudah tersedia.
- **Independent test:** pilih Slenthem, ketuk tujuh bilah; dengarkan nada rendah.

**Acceptance scenarios:**

1. **Given** Slenthem aktif, **Then** tujuh bilahnya bisa diketuk dan
   menghasilkan tujuh nada berbeda.
2. **Given** Slenthem aktif, **Then** baris nada menampilkan tujuh bilah.
3. **Given** Slenthem dan Saron dibandingkan, **Then** Slenthem terdengar pada
   oktaf yang lebih rendah.

### US3 — Memainkan Gong & Kempul (Priority: P1)

Pengguna memilih Gong & Kempul dan mengetuk gong atau kempul yang terlihat.

- **Why this priority:** gong adalah identitas gamelan; tanpa audio, model ini
  terasa mati.
- **Independent test:** pilih Gong & Kempul, ketuk gong ageng dan beberapa
  kempul.

**Acceptance scenarios:**

1. **Given** Gong & Kempul aktif, **Then** setiap gong/kempul yang terlihat
   bisa diketuk dan menghasilkan bunyi.
2. **Given** gong ageng diketuk, **Then** bunyinya berbeda dari kempul.
3. **Given** pengguna mengetuk gong saat nada lain masih berbunyi, **Then**
   keduanya boleh tumpang tindih.

### US4 — Memainkan Kendang (Priority: P2)

Pengguna memilih Kendang dan memainkan pukulan yang tersedia lewat pad.

- **Why this priority:** kendang bukan alat bernada; butuh kontrol pukulan,
  bukan baris nada.
- **Independent test:** pilih Kendang, tekan pad DHA/TAK/THUNG; dengarkan bunyi
  berbeda.

**Acceptance scenarios:**

1. **Given** Kendang aktif, **Then** muncul tujuh pad pukulan: **DHA, DLANG,
   LUNG, TAK, THUNG, TONG, DHUNG**.
2. **Given** sebuah pad ditekan, **Then** sample pukulan itu berbunyi dan pad
   menyala.
3. **Given** pengguna menekan beberapa pad cepat, **Then** pukulan boleh
   tumpang tindih.
4. **Given** Kendang aktif, **Then** tidak ada baris nada Saron yang dipakai.

### US5 — Berpindah alat dan state (Priority: P2)

Audio mengikuti alat yang dipilih; kontrol dan suara tidak tercampur.

- **Why this priority:** F-03 memungkinkan ganti alat; audio harus ikut.
- **Independent test:** mainkan Saron, pilih Bonang, lalu kembali ke Saron.

**Acceptance scenarios:**

1. **Given** Saron sedang berbunyi, **When** pengguna memilih Bonang,
   **Then** suara Saron berhenti dan kontrol Bonang muncul.
2. **Given** Bonang aktif, **When** pengguna memilih Slenthem,
   **Then** tidak ada suara Bonang yang tersisa.
3. **Given** alat non-Saron aktif, **When** pengguna mengetuk model,
   **Then** tidak ada sample Saron yang diputar.
4. **Given** marker hilang, **Then** kontrol nada/pad disembunyikan dan tidak ada
   nada baru.
5. **Given** pengguna keluar dari AR atau aplikasi masuk background,
   **Then** semua suara berhenti.
6. **Given** sample/model gagal dimuat, **Then** muncul pesan yang jelas;
   aplikasi tidak berhenti.

---

## Edge cases

- Bagian alat yang tidak punya sample → tidak boleh membuat crash; beri tanda
  atau lewati bagian itu, dan catat sebagai keterbatasan.
- Marker hilang saat nada masih berdentang → decay boleh lanjut, tetapi tidak
  ada nada baru.
- Ganti alat saat model lama masih dimuat → pilihan terbaru menang.
- Model alat gagal dimuat → pesan + coba lagi; audio alat lama berhenti.
- Sample gagal dimuat → kontrol nonaktif + pesan.
- Ketukan di luar bagian alat → tidak ada suara.
- Dua jari sekaligus → satu nada per ketukan; akor tidak didukung.
- Volume perangkat nol → feedback visual tetap muncul.
- Mode senyap/headphone → perilaku mengikuti perangkat.

## Functional requirements

- **FR-001**: F-04 MUST mengaktifkan audio untuk Bonang, Gender Barung,
  Slenthem, Gong & Kempul, dan Kendang.
- **FR-002**: Bonang MUST memetakan semua pencon yang terlihat ke nada berbeda.
- **FR-003**: Gender Barung MUST memetakan semua bilah yang terlihat ke nada
  berbeda.
- **FR-004**: Slenthem MUST memetakan tujuh bilah ke tujuh nada berbeda pada
  oktaf rendah.
- **FR-005**: Gong & Kempul MUST membunyikan setiap gong/kempul yang terlihat.
- **FR-006**: Kendang MUST menyediakan tujuh pad pukulan: DHA, DLANG, LUNG, TAK,
  THUNG, TONG, DHUNG.
- **FR-007**: Kontrol nada MUST menyesuaikan bentuk alat: baris 7, dua baris,
  atau pad pukulan.
- **FR-008**: Mengetuk bagian alat MUST membunyikan nada/pukulan yang sesuai.
- **FR-009**: Berpindah alat MUST menghentikan suara alat sebelumnya.
- **FR-010**: Beberapa nada/pukulan MUST boleh tumpang tindih.
- **FR-011**: Marker hilang MUST menyembunyikan kontrol dan menghentikan nada
  baru.
- **FR-012**: Keluar AR/background MUST menghentikan semua suara.
- **FR-013**: Semua sample MUST punya lisensi/atribusi yang tercatat.
- **FR-014**: F-04 MUST NOT menambah pemilih Pelog/Slendro, pindah oktaf,
  panel Info, progres koleksi, atau pengaturan volume.
- **FR-015**: F-04 MUST NOT memakai sample Saron untuk alat lain.
- **FR-016**: Sample/model gagal dimuat MUST ditangani tanpa crash.

## Key entities

- **Instrument**: alat dengan model, kontrol, dan kumpulan sample.
- **Note**: satu nada bernama pada alat bernada.
- **Stroke**: satu pukulan bernama pada Kendang.
- **NoteMap**: pemetaan bagian model ke Note/Stroke.
- **Sample**: berkas suara pendek untuk satu Note/Stroke.
- **Scale**: satu tangga nada default per alat; tidak ada pemilih di F-04.

## Success criteria

- **SC-001**: Kelima alat menghasilkan suara saat dimainkan.
- **SC-002**: Setiap bagian yang dipetakan menghasilkan bunyi berbeda.
- **SC-003**: Kontrol tiap alat sesuai bentuknya (7, dua baris, atau pad).
- **SC-004**: Berpindah alat tidak memutar sample alat sebelumnya.
- **SC-005**: Lima ketukan cepat per alat tidak membuat crash.
- **SC-006**: Tidak ada suara setelah keluar AR/background.
- **SC-007**: Marker hilang/muncul tiga kali tidak memicu nada hantu.
- **SC-008**: Bunyi muncul ≤150 ms setelah ketukan pada perangkat target.

## Non-goals

- Pemilih Pelog/Slendro dan pindah oktaf (dihapus dari MVP).
- Simulasi dua kepala Kendang secara penuh; F-04 memakai subset pukulan CDM.
- Merekam sample baru di dalam aplikasi.
- Notasi, pelajaran musik, akor, multi-touch.
- Animasi tabuh atau highlight per bilah 3D.
- Alat di luar enam katalog (Gambang, Kenong, Kethuk, Kempyang, dll.).
- Panel Info, koleksi, dan volume (F-05–F-07).

## Assumptions

- Sampel CDM tersedia untuk Bonang (`BBPL*`), Gender Barung (`GBPL*`),
  Slenthem (`GSPL*`), Gong/Kempul (`GA`, `GKPL*`, `GS`), dan Kendang
  (`DHA`, `DLANG`, `LUNG`, `TAK`, `THUNG`, `TONG`, `DHUNG`).
- Satu tangga nada default per alat; tidak ada pemilih.
- Kendang memakai subset pukulan yang tersedia; bukan emulasi dua kepala penuh.
- Model dan pusat bagian alat bisa diukur dari mesh, seperti Saron.
- Lisensi sample CDM (Artistic 2.0) dipatuhi.

## Open questions

- Tidak ada.

---

## Checklist

- [x] Tidak ada nama teknologi/library di acceptance
- [x] Setiap acceptance scenario bisa diamati dari luar
- [x] Setiap FR punya cara verifikasi
- [x] Tidak ada `[NEEDS CLARIFICATION]` tersisa
- [x] Non-goals eksplisit
- [x] Tidak ada fitur spekulatif
