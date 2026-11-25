package com.gemaar.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.gemaar.R

val GemaARDisplay = FontFamily(
    Font(R.font.alfaslabone_regular),
)

val GemaARBody = FontFamily(
    Font(R.font.nunito),
)

/** Font script untuk nama alat di Kartu Budaya (DESIGN: Caveat 20/26). */
val GemaARScript = FontFamily(
    Font(
        resId = R.font.caveat,
        weight = FontWeight.Bold,
        variationSettings = FontVariation.Settings(FontVariation.weight(700)),
    ),
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
