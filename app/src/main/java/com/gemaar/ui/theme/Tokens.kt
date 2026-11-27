package com.gemaar.ui.theme

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Token ukuran, diturunkan langsung dari `docs/design/design-tokens.json`
 * (`space`, `radius`, `layout`, `motion`, `font.size`, `shadow`).
 * Warna sudah dijembatani [GemaARColors].
 *
 * Aturan COMPONENTS #6: komponen tidak memakai ukuran mentah.
 */

object GemaARSpace {
    val x1 = 4.dp
    val x2 = 8.dp
    val x3 = 12.dp
    val x4 = 16.dp
    val x6 = 24.dp
    val x8 = 32.dp
    val x12 = 48.dp
}

object GemaARRadius {
    val sm = 8.dp
    val md = 12.dp
    val lg = 20.dp
    val full = 9999.dp
}

object GemaARLayout {
    val touchTarget = 48.dp
    val panelMaxWidth = 720.dp
    const val panelCompactWidthFraction = 0.92f
    val pagePaddingX = 24.dp
}

object GemaARMotion {
    const val fastMs = 100
    const val normalMs = 180
}

object GemaARFontSize {
    val caption = 12.sp
    val body = 16.sp
    val info = 18.sp
    val numeric = 20.sp
    val display = 32.sp
}

object GemaARShadow {
    /** `shadow.panel` (0 8px 24px): elevasi panel, sama dengan panel S2. */
    val panel = 16.dp
}

object GemaARStroke {
    val panel = 2.dp
    val hairline = 1.dp
}
