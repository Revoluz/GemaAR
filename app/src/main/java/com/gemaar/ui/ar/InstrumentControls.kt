package com.gemaar.ui.ar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gemaar.data.InstrumentAudio
import com.gemaar.data.Note
import com.gemaar.ui.theme.GemaARBody
import com.gemaar.ui.theme.GemaARColors

/**
 * Kontrol bunyi di layar AR.
 *
 * Satu baris untuk alat berbilah satu deret (Saron, Slenthem, Gender 14 bilah)
 * dan pad untuk Gong/Kempul (9) serta Kendang (7). Lebar tombol menyesuaikan
 * layar agar 14 bilah Gender tetap muat di HP tanpa dipecah jadi dua baris.
 */
@Composable
fun InstrumentControls(
    audio: InstrumentAudio,
    activeNoteId: String?,
    enabled: Boolean,
    onNoteClick: (Note) -> Unit,
    modifier: Modifier = Modifier,
) {
    when (audio) {
        is InstrumentAudio.Rows -> Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            audio.rows.forEach { row ->
                NoteBar(
                    notes = row,
                    activeNoteId = activeNoteId,
                    enabled = enabled,
                    onNoteClick = onNoteClick,
                )
            }
        }
        is InstrumentAudio.Pads -> PadsGrid(
            pads = audio.pads,
            activeNoteId = activeNoteId,
            enabled = enabled,
            onNoteClick = onNoteClick,
            modifier = modifier,
        )
    }
}

@Composable
private fun PadsGrid(
    pads: List<Note>,
    activeNoteId: String?,
    enabled: Boolean,
    onNoteClick: (Note) -> Unit,
    modifier: Modifier = Modifier,
) {
    val spacing = 6.dp
    val columns = if (pads.size > 8) 5 else 4

    BoxWithConstraints(modifier = modifier) {
        val padWidth = ((maxWidth - spacing * (columns - 1)) / columns)
            .coerceIn(56.dp, 78.dp)

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(spacing),
        ) {
            pads.chunked(columns).forEach { row ->
                Row(horizontalArrangement = Arrangement.spacedBy(spacing)) {
                    row.forEach { pad ->
                        Pad(
                            note = pad,
                            active = pad.id == activeNoteId,
                            enabled = enabled,
                            width = padWidth,
                            onClick = { onNoteClick(pad) },
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun Pad(
    note: Note,
    active: Boolean,
    enabled: Boolean,
    width: Dp,
    onClick: () -> Unit,
) {
    val shape = RoundedCornerShape(8.dp)
    val background = when {
        active -> GemaARColors.Primary
        enabled -> GemaARColors.Metal
        else -> GemaARColors.Wood
    }
    val foreground = if (enabled || active) {
        GemaARColors.PrimaryFg
    } else {
        GemaARColors.Muted
    }
    Box(
        modifier = Modifier
            .width(width)
            .height(44.dp)
            .shadow(if (enabled) 4.dp else 0.dp, shape)
            .clip(shape)
            .background(background, shape)
            .border(
                if (active) 3.dp else 2.dp,
                if (active) GemaARColors.Primary else GemaARColors.Border,
                shape,
            )
            .clickable(enabled = enabled, onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = note.keyLabel,
            color = foreground,
            fontFamily = GemaARBody,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
        )
    }
}
