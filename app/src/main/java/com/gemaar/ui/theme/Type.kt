package com.gemaar.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.gemaar.R

val GemaARDisplay = FontFamily(
    Font(R.font.alfaslabone_regular),
)

val GemaARBody = FontFamily(
    Font(R.font.nunito),
)

val GemaARTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = GemaARDisplay,
        fontSize = 48.sp,
        lineHeight = 56.sp,
        fontWeight = FontWeight.Normal,
    ),
    titleLarge = TextStyle(
        fontFamily = GemaARBody,
        fontSize = 20.sp,
        lineHeight = 28.sp,
        fontWeight = FontWeight.Bold,
    ),
    bodyMedium = TextStyle(
        fontFamily = GemaARBody,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        fontWeight = FontWeight.Normal,
    ),
)
