package com.gemaar.ar

import kotlin.math.abs

/**
 * Pemetaan tap model ke indeks di `InstrumentAudio.allNotes`.
 *
 * Pusat bilah/gong diukur dari mesh GLB (satuan model, sebelum `scaleToUnits`).
 * SceneView men-scale model dengan `units / dimensiTerbesar`, jadi pusat di
 * ruang marker = `pusatModel * (modelScale / dimensiModel)`.
 *
 * Memakai pusat terdekat — bukan membagi lebar model rata — karena jarak antar
 * bilah tidak selalu sama dan pembagian rata membuat tap bilah 5 berbunyi 4.
 *
 * Bonang tetap dua baris: sumbu X = kolom, sumbu Z = baris.
 */
object InstrumentTapMap {
    // Slenthem: 7 bilah, lebar model 0,8447165 m.
    private val SLENTHEM_CENTERS = floatArrayOf(
        -0.322f, -0.217f, -0.112f, -0.006f, 0.099f, 0.204f, 0.310f,
    )
    private const val SLENTHEM_MODEL_WIDTH = 0.8447165f

    // Gender Barung: 14 bilah satu deret, lebar model 1,444458 m.
    private val GENDER_CENTERS = floatArrayOf(
        -0.535f, -0.441f, -0.347f, -0.253f, -0.159f, -0.065f, 0.029f,
        0.123f, 0.217f, 0.311f, 0.406f, 0.500f, 0.594f, 0.688f,
    )
    private const val GENDER_MODEL_WIDTH = 1.444458f

    // Gong & Kempul: 9 gong, lebar model 2,5320467 m.
    // Kiri ke kanan: Gong Ageng, Kempul 1–7, Suwukan.
    private val GONG_CENTERS = floatArrayOf(
        -1.100f, -0.720f, -0.443f, -0.180f, 0.018f,
        0.180f, 0.454f, 0.656f, 1.042f,
    )
    private const val GONG_MODEL_WIDTH = 2.5320467f

    private fun column(x: Float, halfWidth: Float, count: Int): Int {
        if (count <= 1) return 0
        val fraction = ((x + halfWidth) / (halfWidth * 2f)).coerceIn(0f, 1f)
        return (fraction * count).toInt().coerceIn(0, count - 1)
    }

    private fun row(z: Float): Int = if (z >= 0f) 0 else 1

    private fun nearestCenter(x: Float, centers: FloatArray, scale: Float): Int {
        var bestIndex = 0
        var bestDistance = Float.MAX_VALUE
        for (index in centers.indices) {
            val distance = abs(x - centers[index] * scale)
            if (distance < bestDistance) {
                bestDistance = distance
                bestIndex = index
            }
        }
        return bestIndex
    }

    fun noteIndexFor(
        instrumentId: String,
        x: Float,
        z: Float,
        modelScale: Float,
    ): Int = when (instrumentId) {
        "saron" -> NoteIndex.forMarkerLocalX(x, modelScale)
        "slenthem" -> nearestCenter(
            x,
            SLENTHEM_CENTERS,
            modelScale / SLENTHEM_MODEL_WIDTH,
        )
        "bonang" -> {
            val columns = 6
            val row = row(z)
            row * columns + column(x, 0.25f * (modelScale / 0.5f), columns)
        }
        "gender_barung" -> nearestCenter(
            x,
            GENDER_CENTERS,
            modelScale / GENDER_MODEL_WIDTH,
        )
        "gong_kempul" -> nearestCenter(
            x,
            GONG_CENTERS,
            modelScale / GONG_MODEL_WIDTH,
        )
        "kendang" -> column(x, 0.15f * (modelScale / 0.3f), 7)
        else -> 0
    }
}
