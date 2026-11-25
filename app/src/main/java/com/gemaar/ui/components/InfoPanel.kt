package com.gemaar.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gemaar.data.CultureCard
import com.gemaar.ui.theme.GemaARBody
import com.gemaar.ui.theme.GemaARColors
import com.gemaar.ui.theme.GemaARScript

/**
 * Panel Kartu Budaya (S5) di sisi kanan layar AR.
 *
 * Acuan visual: `docs/design/UI/exports/S5-kartu-budaya.png`.
 * Panel tidak menghentikan AR; pemanggil yang mengatur state buka/tutup.
 */
@Composable
fun InfoPanel(
    instrumentName: String,
    card: CultureCard?,
    onClose: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val shape = RoundedCornerShape(20.dp)
    val bodyStyle = TextStyle(
        fontFamily = GemaARBody,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        lineHeight = 28.sp,
        textAlign = TextAlign.End,
    )

    Box(
        modifier = modifier
            .fillMaxWidth(0.34f)
            .widthIn(max = 420.dp)
            .fillMaxHeight(0.8f)
            .padding(top = 26.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .shadow(16.dp, shape)
                .clip(shape)
                .background(GemaARColors.Surface, shape)
                .border(2.dp, GemaARColors.Border, shape)
                .padding(24.dp),
        ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top,
        ) {
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.End,
            ) {
                Text(
                    text = instrumentName,
                    color = GemaARColors.Primary,
                    fontFamily = GemaARScript,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    lineHeight = 26.sp,
                    textAlign = TextAlign.End,
                )
                if (card != null) {
                    Text(
                        text = card.origin,
                        color = GemaARColors.Muted,
                        fontFamily = GemaARBody,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        lineHeight = 16.sp,
                        textAlign = TextAlign.End,
                    )
                }
            }
            Spacer(Modifier.width(12.dp))
            CloseButton(onClose = onClose)
        }

        Spacer(Modifier.height(12.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(GemaARColors.Border.copy(alpha = 0.6f)),
        )
        Spacer(Modifier.height(16.dp))

        if (card == null) {
            Text(
                text = "Info belum tersedia untuk alat ini.",
                color = GemaARColors.Fg,
                fontFamily = GemaARBody,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                lineHeight = 24.sp,
            )
            Spacer(Modifier.weight(1f))
        } else {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Text(card.formAndMaterial, style = bodyStyle)
                Text(card.playing, style = bodyStyle)
                Text(card.role, style = bodyStyle)
            }
        }

        Spacer(Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = card?.sourceCredit.orEmpty(),
                color = GemaARColors.Muted,
                fontFamily = GemaARBody,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                lineHeight = 16.sp,
                modifier = Modifier.weight(1f),
            )
            Spacer(Modifier.width(8.dp))
            CultureBadge()
            }
        }

        PlaqueTitle(
            text = "Info",
            fontSize = 24.sp,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = (-26).dp),
        )
    }
}

@Composable
private fun CloseButton(onClose: () -> Unit) {
    val shape = RoundedCornerShape(12.dp)
    Box(
        modifier = Modifier
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
            contentDescription = "Tutup info",
            tint = GemaARColors.Fg,
        )
    }
}

@Composable
private fun CultureBadge() {
    val shape = RoundedCornerShape(999.dp)
    Row(
        modifier = Modifier
            .clip(shape)
            .background(GemaARColors.Surface2, shape)
            .border(1.dp, GemaARColors.Border, shape)
            .padding(horizontal = 10.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(6.dp)
                .background(GemaARColors.Primary, CircleShape),
        )
        Spacer(Modifier.width(6.dp))
        Text(
            text = "Kartu Budaya",
            color = GemaARColors.Muted,
            fontFamily = GemaARBody,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            lineHeight = 16.sp,
        )
    }
}
