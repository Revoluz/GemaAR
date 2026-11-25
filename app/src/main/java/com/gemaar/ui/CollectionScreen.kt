package com.gemaar.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gemaar.R
import com.gemaar.data.Instrument
import com.gemaar.ui.components.PlaqueTitle
import com.gemaar.ui.theme.GemaARBody
import com.gemaar.ui.theme.GemaARColors
import com.gemaar.ui.theme.GemaARScript

/**
 * S6 Alat Musik — daftar koleksi dan status sudah/belum dimainkan.
 *
 * Acuan visual: `docs/design/UI/exports/S6-alat-musik.png`.
 * Layar ini read-only: tidak memilih alat dan tidak membuka AR.
 */
@Composable
fun CollectionScreen(
    instruments: List<Instrument>,
    playedIds: Set<String>,
    onBack: () -> Unit,
) {
    BackHandler(onBack = onBack)

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.ilustrasi_latar),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
        )

        BoxWithConstraints(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            val compact = maxHeight < 600.dp
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.94f)
                    .widthIn(max = 720.dp)
                    .fillMaxHeight(if (compact) 0.92f else 0.84f),
            ) {
                val panelShape = RoundedCornerShape(20.dp)
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .shadow(16.dp, panelShape)
                        .background(GemaARColors.Surface, panelShape)
                        .border(2.dp, GemaARColors.Border, panelShape)
                        .padding(
                            horizontal = if (compact) 16.dp else 24.dp,
                            vertical = if (compact) 14.dp else 20.dp,
                        )
                        .padding(top = if (compact) 58.dp else 62.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = "Enam alat gamelan — badge hijau berarti sudah " +
                            "kamu mainkan di AR",
                        color = GemaARColors.Muted,
                        fontFamily = GemaARBody,
                        fontWeight = FontWeight.Bold,
                        fontSize = if (compact) 11.sp else 13.sp,
                        lineHeight = if (compact) 15.sp else 18.sp,
                        textAlign = TextAlign.Center,
                    )
                    Spacer(Modifier.height(if (compact) 10.dp else 16.dp))

                    if (instruments.isEmpty()) {
                        Text(
                            text = "Belum ada alat",
                            color = GemaARColors.Fg,
                            fontFamily = GemaARBody,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                        )
                    } else {
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .verticalScroll(rememberScrollState()),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(
                                if (compact) 10.dp else 16.dp,
                            ),
                        ) {
                            instruments.chunked(3).forEach { rowItems ->
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(
                                        if (compact) 10.dp else 16.dp,
                                    ),
                                    verticalAlignment = Alignment.Top,
                                ) {
                                    rowItems.forEach { instrument ->
                                        CollectionCard(
                                            instrument = instrument,
                                            played = instrument.id in playedIds,
                                            compact = compact,
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                PlaqueTitle(
                    text = "Alat Musik",
                    fontSize = if (compact) 24.sp else 30.sp,
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .offset(y = if (compact) (-18).dp else (-22).dp),
                )

                CloseButton(
                    onClose = onBack,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(if (compact) 6.dp else 8.dp),
                )
            }
        }

    }
}

@Composable
private fun CollectionCard(
    instrument: Instrument,
    played: Boolean,
    compact: Boolean,
) {
    val shape = RoundedCornerShape(12.dp)
    Column(
        modifier = Modifier
            .width(if (compact) 150.dp else 196.dp)
            .clip(shape)
            .background(GemaARColors.Surface2, shape)
            .border(2.dp, GemaARColors.Border, shape)
            .padding(if (compact) 8.dp else 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(if (compact) 62.dp else 92.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(GemaARColors.Background),
            contentAlignment = Alignment.Center,
        ) {
            Image(
                painter = painterResource(instrument.iconRes),
                contentDescription = instrument.name,
                modifier = Modifier.size(if (compact) 46.dp else 68.dp),
            )
        }
        Spacer(Modifier.height(if (compact) 4.dp else 8.dp))
        Text(
            text = instrument.name,
            color = GemaARColors.Fg,
            fontFamily = GemaARScript,
            fontWeight = FontWeight.Bold,
            fontSize = if (compact) 16.sp else 20.sp,
            lineHeight = if (compact) 20.sp else 26.sp,
            textAlign = TextAlign.Center,
            maxLines = 1,
        )
        Text(
            text = instrument.origin,
            color = GemaARColors.Muted,
            fontFamily = GemaARBody,
            fontWeight = FontWeight.Bold,
            fontSize = if (compact) 9.sp else 12.sp,
        )
        Spacer(Modifier.height(if (compact) 6.dp else 8.dp))
        ProgressBadge(played = played, compact = compact)
    }
}

@Composable
private fun ProgressBadge(played: Boolean, compact: Boolean) {
    val shape = RoundedCornerShape(999.dp)
    Row(
        modifier = Modifier
            .clip(shape)
            .background(if (played) GemaARColors.Success else GemaARColors.Background)
            .border(1.dp, GemaARColors.Border, shape)
            .padding(
                horizontal = if (compact) 6.dp else 9.dp,
                vertical = if (compact) 3.dp else 4.dp,
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(if (compact) 3.dp else 5.dp),
    ) {
        Text(
            text = if (played) "Sudah dimainkan" else "Belum dimainkan",
            color = GemaARColors.FgDark,
            fontFamily = GemaARBody,
            fontWeight = FontWeight.Bold,
            fontSize = if (compact) 9.sp else 11.sp,
        )
        Icon(
            imageVector = if (played) Icons.Filled.Check else Icons.Filled.Refresh,
            contentDescription = null,
            tint = GemaARColors.FgDark,
            modifier = Modifier.size(if (compact) 11.dp else 13.dp),
        )
    }
}

@Composable
private fun CloseButton(onClose: () -> Unit, modifier: Modifier = Modifier) {
    val shape = RoundedCornerShape(12.dp)
    Box(
        modifier = modifier
            .size(48.dp)
            .shadow(4.dp, shape)
            .clip(shape)
            .background(GemaARColors.Wood, shape)
            .border(2.dp, GemaARColors.Border, shape)
            .clickable(onClick = onClose),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = Icons.Filled.Close,
            contentDescription = "Tutup dan kembali ke Beranda",
            tint = GemaARColors.Fg,
        )
    }
}
