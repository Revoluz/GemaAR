# Constitution

> Prinsip yang tidak boleh dilanggar. Stack-agnostic.
> Target: 40–60 baris. Kalau lebih, itu dokumentasi, bukan prinsip.

## Article I — Satu sumber kebenaran per fakta
Setiap fakta hidup di satu file. Kriteria diterima hanya di `spec.md`.
Kontrak/format data hanya di satu tempat (generated bila mungkin).
Kalau dua file menjawab hal yang sama, salah satunya harus dihapus.

## Article II — Boundary di-enforce, bukan dijelaskan
- `src/` tidak diubah dari `docs/` atau `scripts/`.
- Generated code tidak diedit manual.
- Akses lintas modul lewat interface, bukan path relatif.
Boundary yang penting harus dijaga linter, CI, atau type system.

## Article III — Test sebelum "selesai"
Tidak ada task selesai tanpa perintah verifikasi yang **dijalankan**
dan outputnya dicatat. "Sudah dicek" bukan bukti.

## Article IV — Satu task per iterasi
Satu iterasi = satu task dari `tasks.md`. Dilarang mengerjakan
seluruh fitur dalam satu langkah.

## Article V — Spec menjelaskan APA, plan menjelaskan BAGAIMANA
`spec.md` tidak menyebut bahasa, framework, atau struktur kode.
Begitu spec menyebut teknologi, ia tidak bisa diverifikasi non-engineer.

## Article VI — Ambiguitas ditandai, bukan ditebak
Setiap ketidakjelasan ditulis `[NEEDS CLARIFICATION: <pertanyaan>]`.
Agent berhenti dan bertanya. Menebak adalah pelanggaran.

## Article VII — Simplicity gate
Sebelum implementasi:
- [ ] Bisa selesai tanpa dependency baru?
- [ ] Bisa selesai tanpa abstraksi baru?
- [ ] Ada cara lebih kecil yang memenuhi spec?

Kalau ada dependency/abstraksi baru, alasannya masuk `docs/decisions/`.

## Article VIII — Anti-abstraction
- Pakai fitur platform/framework langsung sebelum membungkusnya.
- Satu model data, satu representasi.
- Kompleksitas harus dibayar requirement, bukan antisipasi.

## Article IX — Dokumen yang bisa di-generate harus di-generate
Kalau fakta bisa diturunkan dari kode, jangan ditulis manual.
Yang boleh manual: **intent, alasan, non-goals, keputusan**.

---

## Amendment
Perubahan constitution harus menyebut alasan nyata, ditinjau manusia,
dan dicatat di `docs/decisions/`.

## Verification
```bash
bash init.sh
bash scripts/verify-docs.sh
```
