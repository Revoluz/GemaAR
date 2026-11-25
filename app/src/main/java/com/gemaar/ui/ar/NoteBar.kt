package com.gemaar.ui.ar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
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
import com.gemaar.data.Note
import com.gemaar.ui.theme.GemaARBody
import com.gemaar.ui.theme.GemaARColors

/**
 * Baris nada di layar AR.
 *
 * Lebar tombol menyesuaikan lebar layar. Gender Barung memakai satu baris
 * 14 bilah; di HP tombolnya mengecil, bukan dipecah menjadi dua baris nada.
 *
 * Referensi visual: `docs/design/UI/exports/S4-ar.png` (baris angka di bawah).
 * Komponen ini hanya menampilkan state; pemutaran nada dilakukan pemanggil.
 */
@Composable
fun NoteBar(
    notes: List<Note>,
    activeNoteId: String?,
    enabled: Boolean,
    onNoteClick: (Note) -> Unit,
    modifier: Modifier = Modifier,
) {
    val spacing = 6.dp
    BoxWithConstraints(modifier = modifier) {
        val keyWidth = ((maxWidth - spacing * (notes.size - 1)) / notes.size)
            .coerceIn(20.dp, 44.dp)

        Row(
            horizontalArrangement = Arrangement.spacedBy(spacing),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            notes.forEach { note ->
                NoteKey(
                    note = note,
                    active = note.id == activeNoteId,
                    enabled = enabled,
                    width = keyWidth,
                    onClick = { onNoteClick(note) },
                )
            }
        }
    }
}

@Composable
private fun NoteKey(
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
    val foreground = when {
        active || enabled -> GemaARColors.PrimaryFg
        else -> GemaARColors.Muted
    }
    val borderAlpha = if (enabled) 1f else 0.45f

    Box(
        modifier = Modifier
            .width(width)
            .height(56.dp)
            .shadow(if (enabled) 4.dp else 0.dp, shape)
            .clip(shape)
            .background(background, shape)
            .border(2.dp, GemaARColors.Border.copy(alpha = borderAlpha), shape)
            .clickable(enabled = enabled, onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = note.keyLabel,
            color = foreground,
            fontFamily = GemaARBody,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
        )
    }
}
