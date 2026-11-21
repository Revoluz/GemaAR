package com.gemaar.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gemaar.R
import com.gemaar.ui.theme.GemaARBody
import com.gemaar.ui.theme.GemaARColors
import com.gemaar.ui.theme.GemaARDisplay

/**
 * S1 Beranda.
 * Visual reference: docs/design/UI/exports/S1-beranda.png.
 * Background: docs/design/UI/exports/assets/ilustrasi-latar.png.
 */
@Composable
fun HomeScreen(onStart: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.ilustrasi_latar),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
        )

        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            TitlePlaque("GemaAR")

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                HomeAction(
                    label = "Mulai",
                    icon = Icons.Filled.PlayArrow,
                    primary = true,
                    enabled = true,
                    onClick = onStart,
                )
                HomeAction(
                    label = "Alat Musik",
                    icon = Icons.Filled.List,
                    primary = false,
                    enabled = false,
                    onClick = {},
                )
                HomeAction(
                    label = "Pengaturan",
                    icon = Icons.Filled.Settings,
                    primary = false,
                    enabled = false,
                    onClick = {},
                )
            }
        }
    }
}

@Composable
private fun TitlePlaque(text: String) {
    val shape = RoundedCornerShape(12.dp)
    Row(
        modifier = Modifier
            .shadow(6.dp, shape)
            .background(GemaARColors.Surface, shape)
            .border(2.dp, GemaARColors.Border, shape)
            .padding(horizontal = 28.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(6.dp)
                .background(GemaARColors.Primary, CircleShape),
        )
        Spacer(Modifier.width(12.dp))
        Text(
            text = text,
            color = GemaARColors.Primary,
            fontFamily = GemaARDisplay,
            fontSize = 40.sp,
            lineHeight = 48.sp,
        )
        Spacer(Modifier.width(12.dp))
        Box(
            modifier = Modifier
                .size(6.dp)
                .background(GemaARColors.Primary, CircleShape),
        )
    }
}

@Composable
private fun HomeAction(
    label: String,
    icon: ImageVector,
    primary: Boolean,
    enabled: Boolean,
    onClick: () -> Unit,
) {
    val shape = RoundedCornerShape(12.dp)
    val background = if (primary) GemaARColors.Primary else GemaARColors.Wood
    val foreground = if (primary) GemaARColors.PrimaryFg else GemaARColors.Fg
    val alpha = if (enabled) 1f else 0.72f

    Row(
        modifier = Modifier
            .shadow(4.dp, shape)
            .clip(shape)
            .background(background.copy(alpha = alpha), shape)
            .border(2.dp, GemaARColors.Border.copy(alpha = alpha), shape)
            .clickable(enabled = enabled, onClick = onClick)
            .padding(horizontal = 20.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = foreground.copy(alpha = alpha),
            modifier = Modifier.size(20.dp),
        )
        Text(
            text = label,
            color = foreground.copy(alpha = alpha),
            fontFamily = GemaARBody,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
        )
    }
}
