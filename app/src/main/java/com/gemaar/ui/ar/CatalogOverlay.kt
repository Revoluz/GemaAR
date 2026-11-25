package com.gemaar.ui.ar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.gemaar.data.Instrument
import com.gemaar.ui.components.PlaqueTitle
import com.gemaar.ui.theme.GemaARColors

// Box (scrim gelap, isi layar)
// └── Box/Column panel
//     ├── PlaqueTitle("Katalog")
//     ├── IconButton close (X)
//     └── Column
//         ├── Row: 3 kartu
//         └── Row: 3 kartu

@Composable
fun CatalogOverlay (
    instruments: List<Instrument>,
    selectedId: String,
    onSelect: (Instrument) -> Unit,
    onClose: () -> Unit,
  ){
    // box background gelap opacity 0.62
    Box(
      modifier = Modifier
        .background(Color.Black.copy(alpha = 0.62f))
        .fillMaxSize()
        .pointerInput(Unit ) { detectTapGestures { } },
        contentAlignment = Alignment.Center,
    ){
        BoxWithConstraints(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            val compact = maxHeight < 600.dp
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.94f)
                    .fillMaxHeight(if (compact) 0.92f else 0.82f)
                    .widthIn(max = 760.dp),
            ) {
                val panelShape = RoundedCornerShape(20.dp)
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .shadow(16.dp, panelShape)
                        .background(GemaARColors.Surface, panelShape)
                        .border(2.dp, GemaARColors.Border, panelShape)
                        .padding(
                            horizontal = if (compact) 12.dp else 24.dp,
                            vertical = if (compact) 10.dp else 20.dp,
                        )
                        .padding(top = if (compact) 18.dp else 28.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    instruments.chunked(3).forEach { rowItems ->
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(
                                if (compact) 10.dp else 16.dp,
                            ),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            rowItems.forEach { instrument ->
                                InstrumentCard(
                                    instrument = instrument,
                                    isSelected = instrument.id == selectedId,
                                    compact = compact,
                                    onClick = { onSelect(instrument) },
                                )
                            }
                        }
                    }
                }
                PlaqueTitle(
                    text = "Katalog",
                    fontSize = if (compact) 24.sp else 30.sp,
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .offset(y = if (compact) (-18).dp else (-22).dp),
                )

                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(if (compact) 8.dp else 12.dp)
                        .size(if (compact) 40.dp else 44.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(GemaARColors.Wood)
                        .border(2.dp, GemaARColors.Border, RoundedCornerShape(8.dp))
                        .clickable(onClick = onClose),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Tutup katalog",
                        tint = GemaARColors.Fg,
                        modifier = Modifier.size(22.dp),
                    )
                }
            }
        }
    }
  }

@Composable
private fun InstrumentCard(
    instrument: Instrument,
    isSelected: Boolean,
    compact: Boolean,
    onClick: () -> Unit,
) {
    val cardShape = RoundedCornerShape(12.dp)
    val borderColor = if (isSelected) GemaARColors.Primary else GemaARColors.Border

    Column(
        modifier = Modifier
            .width(if (compact) 140.dp else 160.dp)
            .clip(cardShape)
            .background(GemaARColors.Wood)
            .border(if (isSelected) 3.dp else 2.dp, borderColor, cardShape)
            .clickable(onClick = onClick)
            .padding(if (compact) 8.dp else 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(instrument.iconRes),
            contentDescription = instrument.name,
            modifier = Modifier.size(if (compact) 48.dp else 72.dp),
        )
        Spacer(Modifier.height(if (compact) 4.dp else 8.dp))
        Text(
            text = instrument.name,
            color = GemaARColors.Fg,
            fontWeight = FontWeight.Bold,
            fontSize = if (compact) 12.sp else 14.sp,
        )
        Text(
            text = instrument.origin,
            color = GemaARColors.Muted,
            fontSize = if (compact) 10.sp else 12.sp,
        )
    }
}