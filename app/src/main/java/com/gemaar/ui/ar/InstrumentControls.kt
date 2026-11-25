package com.gemaar.ui.ar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gemaar.data.InstrumentAudio
import com.gemaar.data.Note
import com.gemaar.ui.theme.GemaARBody
import com.gemaar.ui.theme.GemaARColors

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
        is InstrumentAudio.Pads -> Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            audio.pads.chunked(4).forEach { row ->
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    row.forEach { pad ->
                        Pad(
                            note = pad,
                            active = pad.id == activeNoteId,
                            enabled = enabled,
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
            .width(78.dp)
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
