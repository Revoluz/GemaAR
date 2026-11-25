package com.gemaar.data

import com.gemaar.R

object Catalog {
    val instruments = listOf(
        Instrument(
            id = "saron",
            name = "Saron",
            origin = "Jawa",
            modelAsset = "models/saron.glb",
            iconRes = R.drawable.ikon_saron,
            audioAvailable = true,
        ),
        Instrument(
            id = "bonang",
            name = "Bonang",
            origin = "Jawa",
            modelAsset = "models/bonang.glb",
            iconRes = R.drawable.ikon_bonang,
            audioAvailable = true,
        ),
        Instrument(
            id = "slenthem",
            name = "Slenthem",
            origin = "Jawa",
            modelAsset = "models/slenthem.glb",
            iconRes = R.drawable.ikon_slenthem,
            audioAvailable = true,
            modelScale = 0.3f,
        ),
        Instrument(
            id = "gong_kempul",
            name = "Gong & Kempul",
            origin = "Jawa",
            modelAsset = "models/gong_kempul.glb",
            iconRes = R.drawable.ikon_gong_kempul,
            audioAvailable = true,
        ),
        Instrument(
            id = "gender_barung",
            name = "Gender Barung",
            origin = "Jawa",
            modelAsset = "models/gender_barung.glb",
            iconRes = R.drawable.ikon_gender_barung,
            audioAvailable = true,
        ),
        Instrument(
            id = "kendang",
            name = "Kendang",
            origin = "Jawa",
            modelAsset = "models/kendang.glb",
            iconRes = R.drawable.ikon_kendang,
            audioAvailable = true,
            modelScale = 0.3f,
        ),
    )

    val saron: Instrument = instruments.first { it.id == "saron" }

    fun byId(id: String?): Instrument =
        instruments.firstOrNull { it.id == id } ?: saron
}
