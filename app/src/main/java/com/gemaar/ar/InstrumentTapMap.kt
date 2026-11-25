package com.gemaar.ar

/**
 * Pemetaan tap model ke indeks di `InstrumentAudio.allNotes`.
 *
 * Untuk alat dua baris (Bonang/Gender), sumbu X menentukan kolom dan sumbu Z
 * menentukan baris. Untuk alat satu baris/pad, hanya X yang dipakai.
 */
object InstrumentTapMap {
    private fun column(x: Float, halfWidth: Float, count: Int): Int {
        if (count <= 1) return 0
        val fraction = ((x + halfWidth) / (halfWidth * 2f)).coerceIn(0f, 1f)
        return (fraction * count).toInt().coerceIn(0, count - 1)
    }

    private fun row(z: Float): Int = if (z >= 0f) 0 else 1

    fun noteIndexFor(
        instrumentId: String,
        x: Float,
        z: Float,
        modelScale: Float,
    ): Int = when (instrumentId) {
        "saron" -> NoteIndex.forMarkerLocalX(x, modelScale)
        "slenthem" -> column(x, 0.15f * (modelScale / 0.3f), 7)
        "bonang" -> {
            val columns = 6
            val row = row(z)
            row * columns + column(x, 0.25f * (modelScale / 0.5f), columns)
        }
        "gender_barung" -> {
            val columns = 7
            val row = row(z)
            row * columns + column(x, 0.25f * (modelScale / 0.5f), columns)
        }
        "gong_kempul" -> column(x, 0.25f * (modelScale / 0.5f), 7)
        "kendang" -> column(x, 0.15f * (modelScale / 0.3f), 7)
        else -> 0
    }
}
