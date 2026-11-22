# PRODUCT

> Hanya **intent, alasan, non-goals**. Tidak ada endpoint, schema, atau struktur kode.
> Acceptance criteria ada di `docs/specs/<feature>/spec.md` — satu sumber.

---

## What this is
GemaAR membawa alat musik tradisional Indonesia — khususnya gamelan — ke atas
satu marker yang diletakkan di meja. Marker sama dipakai untuk semua alat; user
memilih alat (mis. saron) dari katalog/menu, mengarahkan kamera ke marker, lalu
model 3D alat itu muncul dan bisa diketuk untuk mendengar nadanya. Tujuannya
membuat pengenalan alat musik terasa konkret — bukan sekadar gambar dan teks.

## Who it is for
| User | What they need | What they do today |
|---|---|---|
| Pelajar/mahasiswa | Melihat bentuk & cara main alat musik | Video YouTube, gambar 2D |
| Guru seni/musik | Media peraga tanpa membawa alat asli | Bawa alat fisik atau slide |
| Wisatawan/pengguna umum | Pengalaman singkat yang berkesan | Foto, cari artikel |

## Why now
ARCore tersedia luas di HP mid-range, model GLB bisa dibuat dari Blender, dan
sampel audio alat musik mudah direkam. Marker cetak sederhana sudah cukup untuk
memberi pengalaman "alat musik muncul di atas kartu" tanpa perangkat khusus.

## Success (outcome, bukan fitur)
| Signal | Target | How measured |
|---|---|---|
| Placement berhasil | ≥ 80% percobaan pertama | Log lokal + uji manual |
| Sesi bermakna | ≥ 60 detik, ≥ 5 nada dimainkan | Log lokal |
| Alat dicoba | ≥ 3 alat per pengguna | Log lokal |
| Retensi D7 (opsional, setelah rilis) | ≥ 15% | Play Console |

## Non-goals
- Multiplayer / kolaborasi real-time.
- Penempatan tanpa marker (plane/permukaan bebas) di MVP.
- iOS atau WebXR.
- Sintesis suara fisik yang presisi (cukup sampel audio).
- Rekaman/DAW, notasi musik, atau pelajaran musik terstruktur.
- Login, akun, cloud sync, atau backend.

## Constraints
| Type | Constraint |
|---|---|
| Tech | Kotlin, Jetpack Compose, ARCore, SceneView, minSdk 26 |
| Team | 1 developer + agent, paruh waktu |
| Budget | Rp0 (tanpa server, aset dibuat sendiri/open license) |
| Deadline | none |

## Open questions
| # | Question | Owner | Status |
|---|---|---|---|
| Q1 | Alat pertama untuk F-01: **Saron** (diputuskan 2026-11-21) | product | closed |
| Q2 | Perlu mode tanpa AR (3D viewer) sebagai fallback HP non-ARCore? | product | open |
| Q3 | Sumber model 3D & sampel audio: buat sendiri atau lisensi CC? | product | closed — model dibuat sendiri; sampel Saron dari CDM Gamelan Sample Library (Artistic 2.0), lihat ADR 0003 |

---

## Yang tidak boleh ada di sini
- Daftar endpoint / interface → kontrak di kode atau `spec.md`
- Skema data → migration / model
- Acceptance criteria → `spec.md`
- Struktur folder → `ARCHITECTURE.md`

Duplikasi di sini akan basi dan menyesatkan.
