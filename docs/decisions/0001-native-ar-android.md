# 0001 — Native Android ARCore + SceneView, bukan Unity/Flutter

**Status:** accepted
**Date:** 2026-11-21
**Deciders:** product + engineering

---

## Context

GemaAR butuh AR untuk menampilkan model 3D alat musik di atas marker, lalu
menerima tap pada model untuk membunyikan sampel audio. Scope MVP kecil: satu
layar katalog, satu layar AR, tanpa backend, tanpa multiplayer, satu developer.
Prioritas: iteration cepat dan APK kecil.

## Decision

Pakai **Kotlin + Jetpack Compose + ARCore + SceneView 4.70 (Filament)** dalam
satu project Gradle, audio lewat `SoundPool`, data lewat JSON di `assets/`.

## Alternatives considered

| Option | Pros | Cons | Why rejected |
|---|---|---|---|
| Unity 6 + AR Foundation | 3D/animation/gameplay paling kuat, ekosistem AR besar | Project & build berat, C#, iterasi lambat, APK besar | MVP tidak butuh physics/game loop; overkill |
| Flutter + plugin AR | Satu codebase lintas platform | Plugin ARCore tidak matang/sering abandoned, akses scene terbatas | Risiko maintenance tinggi |
| React Native + ViroReact | JS bila tim web | Viro kurang aktif, ARCore terbatas | Sama: risiko & fitur |
| WebXR / PWA | Tanpa install | Dukungan Android terbatas, tidak bisa Play Store-native | Tidak memenuhi target platform |
| Native + custom OpenGL | Kontrol penuh | Menulis renderer & loader GLB sendiri | Jauh lebih mahal |

## Consequences

### Positive
- Satu bahasa (Kotlin) untuk UI, AR, dan audio; API SceneView 4.x berbentuk
  Composable (`ARSceneView { AnchorNode { ModelNode } }`).
- SceneView menyediakan `ARSceneView`, marker/image tracking, anchor, dan loader GLB.
- APK lebih kecil dan build lebih cepat dibanding Unity.
- Tidak ada dependency audio tambahan (`SoundPool` bawaan).

### Negative
- Animasi kompleks/physics harus dibuat manual.
- SceneView adalah library komunitas; API 2.x → 4.x berubah cukup banyak
  (pakai dokumen migrasi, bukan contoh 2.x).
- Butuh toolchain baru: AGP 9.4+, Kotlin 2.3+, compileSdk 37, JDK 21.
- Butuh HP fisik ARCore-supported untuk uji yang meyakinkan.

### Follow-ups
- [ ] Scaffold Gradle + SceneView 4.70, pin versi di `gradle/libs.versions.toml`
- [ ] Siapkan **satu** marker image untuk semua alat + `AugmentedImageDatabase` di `assets/markers/`
- [ ] Uji `ARSceneView` di minimal 2 HP berbeda
- [ ] Siapkan pipeline Blender → GLB (dan cek batas ukuran di `AGENTS.md`)

## Verification

MVP F-01 + F-02 berjalan di HP ARCore-supported: model muncul stabil di atas
marker dan nada berbunyi saat diketuk. Bila SceneView terbukti tidak stabil pada
device target, tinjau ulang ADR ini (kandidat: AR Foundation).

## Related
- Spec: `docs/specs/F-01-ar-placement/spec.md`
- PR: —
