# COMPONENTS — inventaris (GemaAR)

> Menjawab: **komponen apa yang sudah ada, dipakai di mana, dan jangan dibuat ulang.**
>
> Ini yang mencegah agent membuat tombol ke-7 yang bentuknya hampir sama.
> Basis: Compose Material 3 + ornamen dari referensi `docs/design/UI/`.

---

## Inventaris

| Komponen | Jenis | Dipakai di | Varian | Fitur | Status |
|---|---|---|---|---|---|
| `AppBackground` | composite | S1–S7 | siang (ilustrasi), AR (gelap) | semua | draft |
| `OrnatePanel` | composite | S1, S2, S3, S5, S6 | default, compact | F-01, F-03, F-05, F-06 | draft |
| `PlaqueTitle` | composite | S1–S7 | statis, dinamis (nama alat) | semua | draft |
| `IconButton` | primitive | S1–S4 | home, katalog, petunjuk, info, close | F-01…F-05 | draft |
| `ActionButton` | primitive | S1, S2, S3 | gold (utama), green (unduh), wood (sekunder) | F-01, F-03 | draft |
| `InstrumentCard` | composite | S2, S6 | default, selected, played (S6) | F-03, F-06 | ada |
| `CatalogOverlay` | composite | S2 | grid 6 kartu, selected | F-03 | ada |
| `InstrumentControls` | composite | S4 | note bar, 2 baris, pad | F-04 | draft |
| `ProgressBadge` | primitive | S6 | sudah / belum dimainkan | F-06 | draft |
| `NoteBar` | composite | S4 | 1 baris, 2 baris, pad | F-02, F-04 | ada |
| `NoteKey` | primitive | S4 | idle, pressed, active | F-02 | ada |
| `InfoPanel` | composite | S5 | side-panel, scroll | F-05 | draft |
| `HintOverlay` | composite | S3, S4 | reticle, marker, permission | F-01 | draft |
| `EmptyState` | composite | S2, S6 | — | F-03, F-06 | draft |
| `ErrorState` | composite | semua | inline, full-page | semua | draft |
| `LoadingState` | composite | S1, S2, S4, S6 | skeleton, spinner | F-01, F-02, F-03, F-06 | draft |
| `SettingsPanel` | composite | S7 | music-only | F-07 | draft |
| `SliderRow` | composite | S7 | default, disabled | F-07 | draft |

Jenis:
- **primitive** — tidak bergantung komponen lain
- **composite** — menyusun primitive

Status:
- `belum` → `draft` → `ada` → `dipakai > 2 layar`

## Di luar scope (jangan dibuat)

Komponen dari referensi yang tidak dimiliki fitur mana pun:

| Komponen referensi | Alasan |
|---|---|
| `FlagButton` / pemilih bahasa | dihapus dari scope (tanpa multibahasa) |

## Aturan

1. **Cari dulu sebelum membuat.** Kalau ada komponen mirip, pakai atau perluas.
2. **Satu komponen, satu tempat** — `app/src/main/java/com/gemaar/ui/components/`.
   Tidak ada salinan di folder fitur/layar.
3. **Varian, bukan komponen baru.** Tombol hijau = varian `green`, bukan
   `GreenButton`.
4. **Tidak ada komponen sekali pakai.** Kalau hanya dipakai satu layar dan sangat
   spesifik, biarkan inline di layar itu.
5. **Komponen tidak memuat data.** Ia menerima state/props, tidak fetch.
6. **Tidak ada hex atau ukuran mentah.** Semua lewat token di `design-tokens.json`.
7. **Teks dari `strings.xml`**, bukan hardcode (MVP bahasa Indonesia).
8. **Ikon dari aset referensi** di `docs/design/UI/`; jangan mencampur ikon
   outline generik dengan ikon ilustratif.

## Kapan menambah

Tambah baris saat:

- komponen yang sama muncul di layar kedua, atau
- ada kebutuhan varian yang jelas, atau
- spec menyebut state yang butuh komponen baru (`EmptyState`, `ErrorState`).

**Jangan** menambah baris untuk komponen yang belum dipakai.

## Yang tidak masuk sini

- Token warna/spacing → `DESIGN.md`
- Layar → `SCREENS.md`
- Perilaku → `spec.md`
