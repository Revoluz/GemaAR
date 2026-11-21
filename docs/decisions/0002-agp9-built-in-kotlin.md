# 0002 — AGP 9.5.2 built-in Kotlin + compileSdk 37.2

**Status:** accepted
**Date:** 2026-11-21
**Deciders:** engineering

---

## Context

SceneView 4.70 mewajibkan `compileSdk 37`, AGP 9.4+, Kotlin 2.3+ (AAR-nya
dibangun dengan Kotlin 2.4.21), dan JVM target 21. AGP 9 mengaktifkan **built-in
Kotlin** secara default, sehingga plugin `org.jetbrains.kotlin.android` tidak
kompatibel. Selain itu, dependency Compose 1.13.0 (BOM 2026.11.01) meminta
compileSdk minimal 37.1.

## Decision

Pakai **Gradle 9.9.2 + AGP 9.5.2 (built-in Kotlin) + Kotlin Compose plugin
2.4.21**, dengan `compileSdk = 37` dan `compileSdkMinor = 2`, `minSdk 26`,
JVM target 21.

## Alternatives considered

| Option | Pros | Cons | Why rejected |
|---|---|---|---|
| AGP 8.x + SceneView 4.18.0 | toolchain lama | bukan versi yang dipilih di ADR 0001; API lebih lama | tidak sesuai desain |
| Opt out built-in Kotlin (`android.builtInKotlin=false`) | bisa pakai plugin lama | opsi sementara, dihapus di AGP 10 | utang teknis |
| compileSdk 37.0 | tidak perlu install 37.2 | Compose 1.13.0 menolak, build gagal | dependency butuh 37.1+ |

## Consequences

### Positive
- Sesuai requirement resmi SceneView 4.70.
- Tidak ada dua sistem Kotlin (plugin vs built-in).
- Build stabil di Gradle 9.9.2.

### Negative
- Wajib SDK platform 37.2.
- `ModelNode`/`AnchorNode` adalah member scope `SceneView { }`, bukan import.
- JDK minimal 17; di mesin ini dipakai JDK 25 dengan target 21.

## Verification

```bash
./gradlew assembleDebug
./gradlew testDebugUnitTest
bash init.sh --check
```

Ketiganya lulus (lihat `docs/specs/F-00-setup/converge.md`).

## Related
- `docs/specs/F-00-setup/spec.md`
- `docs/specs/F-00-setup/plan.md`
- ADR 0001
