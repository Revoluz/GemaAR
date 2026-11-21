package com.gemaar.ui.ar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gemaar.ui.theme.GemaARBody
import com.gemaar.ui.theme.GemaARColors

/**
 * Sementara untuk T001. T004 akan mengganti ini dengan S4 AR shell
 * mengikuti docs/design/UI/exports/S4-ar.png.
 */
@Composable
fun ArPlaceholderScreen(onBack: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(GemaARColors.Surface),
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text(
                text = "Saron",
                color = GemaARColors.Primary,
                fontFamily = GemaARBody,
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
            )
            Text(
                text = "Arahkan kamera ke marker",
                color = GemaARColors.Fg,
                fontFamily = GemaARBody,
                fontSize = 18.sp,
            )
        }

        IconButton(
            onClick = onBack,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(24.dp),
        ) {
            Icon(
                imageVector = Icons.Filled.Home,
                contentDescription = "Kembali ke Beranda",
                tint = GemaARColors.Fg,
            )
        }
    }
}
