# 3D Assets — GemaAR

Model alat musik untuk desain dan implementasi AR.

> **Bukan path runtime.** Project Android sudah ada (F-00); salinan runtime ada di
> `app/src/main/assets/models/`. Jika model diperbarui, salin ulang ke sana.

---

## Model

| File | Ukuran | Tris | Material | Dimensi (W×H×D, m) | Catatan |
|---|---|---|---|---|---|
| `gong_kempul.glb` | 3,77 MB | 95.418 | 3 | 2,53 × 1,46 × 0,55 | paling berat; kandidat decimate |
| `bonang.glb` | 1,25 MB | 26.404 | 7 | 1,79 × 0,55 × 0,57 | 12 pencon, 2 baris |
| `saron.glb` | 0,46 MB | 5.058 | 4 | 0,93 × 0,32 × 0,30 | satu unit; **tanpa tabuh** untuk F-02 |
| `gender_barung.glb` | 0,94 MB | 13.664 | 4 | 1,44 × 0,57 × 0,47 | 14 bilah |
| `slenthem.glb` | 1,61 MB | 22.800 | 65 | 0,84 × 0,57 × 0,47 | 65 material → draw call tinggi |
| `kendang.glb` | 0,58 MB | 7.580 | 5 | 0,36 × 0,28 × 0,21 | satu drum |

Semua GLB:
- satuan **meter**
- root bernama `*_Root` di **(0,0,0)**
- bagian bawah model di **Y = 0**
- bbox terpusat di X/Z

## Preview

| File | Isi |
|---|---|
| `preview/00_contact_sheet.png` | ringkasan 6 model (tile Saron masih render lama) |
| `preview/01_gong_kempul.png` | rak gong |
| `preview/02_bonang.png` | bonang |
| `preview/03_saron.png` | saron tanpa tabuh (F-02) |
| `preview/04_gender_barung.png` | gender barung |
| `preview/05_slenthem.png` | slenthem |
| `preview/06_kendang.png` | kendang |

## Skala untuk AR (keputusan MVP)

Model disimpan pada skala sekarang (meter). Ukuran tampilan dinormalkan di
SceneView:

- Marker A4 (21 cm): `scaleToUnits = 0.5f`
- Kendang dan Slenthem: `scaleToUnits = 0.3f` (visual lebih pas di marker)
- Marker A3 (29,7 cm): `scaleToUnits = 0.7f`
- `physicalWidth` di marker database harus sama dengan lebar cetakan
  (A4 = `0.21f`, A3 = `0.297f`).

Catatan: `scaleToUnits` menormalkan **dimensi terbesar** tiap model, jadi ukuran
relatif antar alat tidak dipertahankan (Gong dan Bonang akan sama lebar). Ini
disengaja untuk MVP agar semua alat terlihat di marker kecil.

Skala nyata (tanpa `scaleToUnits`) butuh marker A2/A1 dan bukan target MVP.

## Optimasi sebelum rilis

- **Gong** 95k tris → pertimbangkan Decimate ke ±50k.
- **Slenthem** 65 material/63 tekstur → gabung material atau atlas tekstur.
- Sisanya sudah relatif ringan (< 27k tris).

## Lisensi

> "GAMELAN JAWA" (https://skfb.ly/pzsLX) by eventdesignws is licensed under
> Creative Commons Attribution (http://creativecommons.org/licenses/by/4.0/).
>
> Changes were made: model split into individual instruments, duplicate
> instruments removed, stray geometry removed, and hierarchy cleaned up.

CC BY 4.0 mewajibkan atribusi yang terlihat di aplikasi, bukan hanya di repo.
Lihat `CREDITS.md` di root project.

## Sumber kerja

File Blender dan hasil export mentah:

```
/home/fanxx/Downloads/gamelan_jawa/gamelan_final.blend
/home/fanxx/Downloads/gamelan_jawa/export/
```

Untuk export ulang dari Blender, set root collection ke `(0,0,0)` lebih dulu
agar GLB tetap terpusat di marker.
