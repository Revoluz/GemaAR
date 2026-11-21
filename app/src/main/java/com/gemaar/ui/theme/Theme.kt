package com.gemaar.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val GemaARColors = darkColorScheme(
    primary = Color(0xFFF8C32A),
    onPrimary = Color(0xFF3B2C1C),
    background = Color(0xFF3D382C),
    onBackground = Color(0xFFFFF6E3),
    surface = Color(0xFF4E3D35),
    onSurface = Color(0xFFFFF6E3),
)

@Composable
fun GemaARTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = GemaARColors,
        content = content,
    )
}
