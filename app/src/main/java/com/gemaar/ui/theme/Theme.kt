package com.gemaar.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

object GemaARColors {
    val Background = Color(0xFFF8F4E8)
    val Surface = Color(0xFF3D382C)
    val Surface2 = Color(0xFF4E3D35)
    val Wood = Color(0xFF6C543C)
    val Fg = Color(0xFFFFF6E3)
    val FgDark = Color(0xFF3B2C1C)
    val Muted = Color(0xFFC9B99F)
    val Border = Color(0xFF2E2418)
    val Primary = Color(0xFFF8C32A)
    val PrimaryFg = Color(0xFF3B2C1C)
    val Success = Color(0xFF8BAC26)
}

private val GemaARColorScheme = darkColorScheme(
    primary = GemaARColors.Primary,
    onPrimary = GemaARColors.PrimaryFg,
    background = GemaARColors.Surface,
    onBackground = GemaARColors.Fg,
    surface = GemaARColors.Surface2,
    onSurface = GemaARColors.Fg,
    surfaceVariant = GemaARColors.Wood,
    onSurfaceVariant = GemaARColors.Muted,
    outline = GemaARColors.Border,
)

@Composable
fun GemaARTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = GemaARColorScheme,
        typography = GemaARTypography,
        content = content,
    )
}
