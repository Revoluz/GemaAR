package com.gemaar.data

import androidx.annotation.DrawableRes

data class Instrument(
    val id: String,
    val name: String,
    val origin: String,
    val modelAsset: String,
    @DrawableRes val iconRes: Int,
    val audioAvailable: Boolean,
)
