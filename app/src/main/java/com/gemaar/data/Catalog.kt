package com.gemaar.data
import com.gemaar.R

object Catalog {
    val instruments = listOf(
        Instrument("saron", "Saron", "Jawa", "models/saron.glb", R.drawable.ikon_saron, true),
        Instrument("bonang", "Bonang", "Jawa", "models/bonang.glb", R.drawable.ikon_bonang, false),
        Instrument("slenthem", "Slenthem", "Jawa", "models/slenthem.glb", R.drawable.ikon_slenthem, false),
    )
    val saron: Instrument = instruments.first { it.id == "saron" }

    fun byId(id: String?): Instrument =
        instruments.firstOrNull { it.id == id } ?: saron

}