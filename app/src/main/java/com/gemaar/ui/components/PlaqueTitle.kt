package com.gemaar.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gemaar.ui.theme.GemaARColors
import com.gemaar.ui.theme.GemaARDisplay

@Composable
fun PlaqueTitle(
    text: String,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 40.sp,
) {
    val shape = RoundedCornerShape(12.dp)
    Row(
        modifier = modifier
            .shadow(6.dp, shape)
            .background(GemaARColors.Surface, shape)
            .border(2.dp, GemaARColors.Border, shape)
            .padding(horizontal = 24.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(6.dp)
                .background(GemaARColors.Primary, CircleShape),
        )
        Spacer(Modifier.width(10.dp))
        Text(
            text = text,
            color = GemaARColors.Primary,
            fontFamily = GemaARDisplay,
            fontSize = fontSize,
            lineHeight = fontSize * 1.2f,
        )
        Spacer(Modifier.width(10.dp))
        Box(
            modifier = Modifier
                .size(6.dp)
                .background(GemaARColors.Primary, CircleShape),
        )
    }
}
