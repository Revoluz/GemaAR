# GLOSSARY

> Sumber: `domain-modeling` (Matt Pocock).
>
> **Hanya istilah.** Tidak ada implementasi, tidak ada spec, tidak ada task.
> Kalau sebuah istilah muncul dua kali dengan arti berbeda, itu tanda harus
> masuk ke sini.
>
> Efeknya: agent memakai istilah yang sama di kode, nama file, dan diskusi.
> Lebih sedikit kata, lebih sedikit token, lebih kecil kemungkinan salah paham.

---

## Istilah teknis

| Istilah | Arti | Jangan tertukar dengan |
|---|---|---|
| **Instrument** | Satu alat musik yang bisa ditempatkan & dimainkan; punya model, nada, dan info budaya | **Katalog** — daftar semua instrument |
| **Catalog** | Daftar instrument beserta metadata, dibaca dari `assets/catalog.json` | **Instrument** — satu item di dalamnya |
| **Note** | Satu nada bernama yang bisa dibunyikan (mis. `do`, `mi`, `ding`) | **Sample** — file audionya |
| **Sample** | File audio pendek untuk satu note | **Note** — konsep musiknya |
| **NoteMap** | Pemetaan bagian model (node) → note | **Scale** — susunan nada |
| **Scale** | Susunan note dalam satu tangga nada (mis. pentatonis) | **Note** — satu nada |
| **Marker** | Gambar cetak (kartu) yang dikenali kamera dan menjadi tempat model | **Anchor** — titik tempel di marker |
| **Anchor** | Titik pada marker tempat model dikunci | **Marker** — gambar yang dikenali |
| **Hit-test** | Sinar dari titik layar ke dunia untuk menemukan node | — |
| **Marker database** | Kumpulan gambar marker yang dikenali selama sesi AR; MVP berisi **satu marker** | **Marker** — satu gambar |
| **Node** | Objek 3D di dalam scene | **Instrument** — data domain |
| **GLB** | Format file model 3D biner (glTF binary) | — |
| **AR Session** | Masa kamera AR aktif dan tracking berjalan | **Session** (agent) — tidak dipakai di sini |

## Instrument MVP (gamelan)

| Instrument | Asal | Cara main (ringkas) |
|---|---|---|
| **Gong & Kempul** | Jawa/Bali | Satu rak gong berisi gong besar dan kempul, dipukul dengan tabuh |
| **Bonang** | Jawa/Bali | Deretan pencon (gong kecil) di atas rancak, dipukul |
| **Saron** | Jawa/Bali | Bilah logam di atas rancak kayu, dipukul dengan tabuh |
| **Gender Barung** | Jawa/Bali | 14 bilah logam beresonansi tabung, dipukul |
| **Slenthem** | Jawa/Bali | 7 bilah logam lebar beresonansi tabung, dipukul |
| **Kendang** | Jawa/Bali/Sunda | Kendang dua sisi, ditabuh dengan tangan |

> Daftar ini domain, bukan spesifikasi. Model/sampel yang benar-benar dibuat
> ditentukan di `spec.md` per fitur.

## Aturan

1. **Satu istilah, satu arti.** Kalau artinya berubah, ganti namanya.
2. **Tambahkan saat istilah itu membingungkan**, bukan di depan.
3. **Tanpa detail implementasi.** Bukan tempat menulis tabel database atau endpoint.
4. **Pakai istilah ini di kode.** Nama variabel, file, dan komponen mengikuti glossary.
5. **Kalau kode bertentangan dengan glossary**, perbaiki salah satunya — jangan biarkan.

## Kapan memperbarui

- Agent dan Anda memakai kata berbeda untuk hal yang sama
- Ada istilah yang butuh satu paragraf untuk dijelaskan
- Ada dua konsep yang sering tertukar

## Yang tidak masuk sini

- Endpoint / interface → kontrak di kode (OpenAPI, JSON Schema, protobuf, atau tipe)
- Skema database → file migration / model
- Keputusan arsitektur → `docs/decisions/`
- Requirement → `spec.md`
