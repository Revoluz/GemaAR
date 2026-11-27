package com.gemaar.ui.components

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.SliderState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.gemaar.R
import com.gemaar.ui.theme.GemaARBody
import com.gemaar.ui.theme.GemaARColors
import com.gemaar.ui.theme.GemaARFontSize
import com.gemaar.ui.theme.GemaARLayout
import com.gemaar.ui.theme.GemaARMotion
import com.gemaar.ui.theme.GemaARRadius
import com.gemaar.ui.theme.GemaARShadow
import com.gemaar.ui.theme.GemaARSpace
import com.gemaar.ui.theme.GemaARStroke
import kotlin.math.roundToInt

/** Motion panel S7: masuk/keluar 180ms (`motion.normal` di design-tokens). */
val SettingsEnterTransition: EnterTransition =
    fadeIn(tween(GemaARMotion.normalMs, easing = FastOutSlowInEasing)) +
        scaleIn(
            initialScale = 0.96f,
            animationSpec = tween(GemaARMotion.normalMs, easing = FastOutSlowInEasing),
        )

val SettingsExitTransition: ExitTransition =
    fadeOut(tween(GemaARMotion.normalMs)) +
        scaleOut(targetScale = 0.98f, animationSpec = tween(GemaARMotion.normalMs))

/** Scrim overlay: nilai sama dengan overlay S2 (`CatalogOverlay`). */
private const val SCRIM_ALPHA = 0.62f

/**
 * Panel Pengaturan (S7) — hanya volume musik.
 *
 * Acuan visual: `docs/design/UI/exports/S7-pengaturan.png`.
 * Komponen ini tidak menyimpan nilai permanen (COMPONENTS #5): pemanggil yang
 * memegang nilai global dan menyimpannya. Bungkus dengan `AnimatedVisibility`
 * memakai [SettingsEnterTransition] / [SettingsExitTransition] agar motion 180ms
 * — pemasangan itu bagian T004.
 *
 * @param percent nilai volume saat ini, 0..100.
 * @param onPercentChange dipanggil live saat slider digeser (persen dibulatkan).
 * @param onPercentCommit dipanggil saat slider dilepas — di sinilah nilai disimpan.
 * @param onClose tutup panel (tombol X).
 * @param message pesan singkat, mis. saat gagal menyimpan (FR-012).
 */
@Composable
fun SettingsPanel(
    percent: Int,
    onPercentChange: (Int) -> Unit,
    onPercentCommit: () -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier,
    message: String? = null,
) {
    val sliderState = remember { SliderState(percent.toFloat(), 0, 0f..100f) }
    // Sinkron sekali saat panel dibuka. Jangan memantulkan `percent` setiap
    // perubahan: saat jari masih menyeret, itu melawan gerak slider (nilai
    // melompat, mis. geser ke kiri malah naik).
    LaunchedEffect(Unit) { sliderState.value = percent.toFloat() }

    val panelShape = RoundedCornerShape(GemaARRadius.lg)

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = SCRIM_ALPHA))
            // Tap di luar panel tidak menutup; desain S7 tidak menampilkan
            // petunjuk "tap untuk menutup".
            .pointerInput(Unit) { detectTapGestures { } },
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(GemaARLayout.panelCompactWidthFraction)
                .widthIn(max = GemaARLayout.panelMaxWidth),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(GemaARShadow.panel, panelShape)
                    .background(GemaARColors.Surface, panelShape)
                    .border(GemaARStroke.panel, GemaARColors.Border, panelShape)
                    .padding(horizontal = GemaARSpace.x6, vertical = GemaARSpace.x6)
                    .padding(top = GemaARSpace.x8),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                SliderRow(
                    percent = percent,
                    sliderState = sliderState,
                    onSliderChange = { value -> onPercentChange(value.roundToInt()) },
                    onSliderChangeFinished = onPercentCommit,
                )
                Spacer(Modifier.height(GemaARSpace.x4))
                AutoSavePill()
                if (message != null) {
                    Spacer(Modifier.height(GemaARSpace.x3))
                    Text(
                        text = message,
                        color = GemaARColors.Muted,
                        fontFamily = GemaARBody,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = GemaARFontSize.caption,
                        textAlign = TextAlign.Center,
                    )
                }
            }

            PlaqueTitle(
                text = stringResource(R.string.settings_title),
                fontSize = GemaARFontSize.display,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .offset(y = -GemaARSpace.x6),
            )

            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(GemaARSpace.x3)
                    .size(GemaARLayout.touchTarget)
                    .clip(RoundedCornerShape(GemaARRadius.sm))
                    .background(GemaARColors.Wood)
                    .border(
                        GemaARStroke.panel,
                        GemaARColors.Border,
                        RoundedCornerShape(GemaARRadius.sm),
                    )
                    .clickable(onClick = onClose),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = stringResource(R.string.settings_close),
                    tint = GemaARColors.Fg,
                    modifier = Modifier.size(GemaARSpace.x6),
                )
            }
        }
    }
}

/**
 * Baris slider pengaturan (COMPONENTS: `SliderRow`, varian default/disabled).
 * Ditempatkan di file yang sama dengan [SettingsPanel] karena hanya dipakai S7
 * (COMPONENTS #4), namun tetap publik agar sesuai inventaris (COMPONENTS #2/#3).
 *
 * @param percent nilai 0..100 untuk label persentase.
 * @param enabled varian `disabled` mematikan slider.
 */
@Composable
fun SliderRow(
    percent: Int,
    sliderState: SliderState,
    onSliderChange: (Float) -> Unit,
    onSliderChangeFinished: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        MusicNoteGlyph()
        Spacer(Modifier.width(GemaARSpace.x3))
        Text(
            text = stringResource(R.string.settings_music_label),
            color = GemaARColors.Fg,
            fontFamily = GemaARBody,
            fontWeight = FontWeight.Bold,
            fontSize = GemaARFontSize.info,
        )
        Spacer(Modifier.width(GemaARSpace.x4))
        Slider(
            state = sliderState,
            onValueChange = { value ->
                // SliderState tidak memperbarui dirinya sendiri: tanpa baris ini
                // persentase berubah tapi thumb tidak bergeser.
                sliderState.value = value
                onSliderChange(value)
            },
            onValueChangeFinished = onSliderChangeFinished,
            enabled = enabled,
            colors = SliderDefaults.colors(
                thumbColor = GemaARColors.Primary,
                activeTrackColor = GemaARColors.Primary,
                inactiveTrackColor = GemaARColors.Wood,
                activeTickColor = GemaARColors.PrimaryFg,
                inactiveTickColor = GemaARColors.Wood,
            ),
            modifier = Modifier.weight(1f),
        )
        Spacer(Modifier.width(GemaARSpace.x4))
        Text(
            text = stringResource(R.string.settings_percent, percent),
            // Angka tabular (DESIGN) supaya lebar tidak bergoyang saat digeser.
            style = TextStyle(
                fontFamily = GemaARBody,
                fontWeight = FontWeight.Bold,
                fontSize = GemaARFontSize.numeric,
                fontFeatureSettings = "tnum",
            ),
            color = GemaARColors.Fg,
            textAlign = TextAlign.End,
            modifier = Modifier.widthIn(min = GemaARSpace.x12),
        )
    }
}

@Composable
private fun AutoSavePill() {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(GemaARRadius.full))
            .background(GemaARColors.Surface2)
            .border(
                GemaARStroke.hairline,
                GemaARColors.Border,
                RoundedCornerShape(GemaARRadius.full),
            )
            .padding(horizontal = GemaARSpace.x3, vertical = GemaARSpace.x1),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(GemaARSpace.x1),
    ) {
        Icon(
            imageVector = Icons.Filled.Check,
            contentDescription = null,
            tint = GemaARColors.Success,
            modifier = Modifier.size(GemaARSpace.x3),
        )
        Text(
            text = stringResource(R.string.settings_autosave),
            color = GemaARColors.Muted,
            fontFamily = GemaARBody,
            fontWeight = FontWeight.SemiBold,
            fontSize = GemaARFontSize.caption,
        )
    }
}

/**
 * Glyph not sederhana (gambar, bukan aset & bukan ikon inti) sesuai `plan.md`
 * bagian Risiko: set ikon inti Compose tidak memuat ikon musik.
 */
@Composable
private fun MusicNoteGlyph(
    modifier: Modifier = Modifier,
    color: Color = GemaARColors.Fg,
) {
    Canvas(modifier.size(GemaARSpace.x6)) {
        val headRadius = size.minDimension * 0.20f
        val stemWidth = size.minDimension * 0.10f
        val centerX = size.width * 0.34f
        val headY = size.height * 0.76f
        val stemTop = size.height * 0.12f

        drawCircle(color = color, radius = headRadius, center = Offset(centerX, headY))
        drawRect(
            color = color,
            topLeft = Offset(centerX + headRadius - stemWidth / 2f, stemTop),
            size = Size(stemWidth, headY - stemTop),
        )
        val flag = Path().apply {
            moveTo(centerX + headRadius + stemWidth / 2f, stemTop)
            quadraticTo(
                size.width * 0.86f,
                size.height * 0.20f,
                size.width * 0.78f,
                size.height * 0.42f,
            )
            quadraticTo(
                size.width * 0.74f,
                size.height * 0.22f,
                centerX + headRadius + stemWidth / 2f,
                size.height * 0.28f,
            )
            close()
        }
        drawPath(flag, color)
    }
}
