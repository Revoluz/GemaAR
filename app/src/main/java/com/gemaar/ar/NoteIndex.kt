package com.gemaar.ar

import kotlin.math.abs

/**
 * Pemetaan posisi tap pada model ke indeks bilah.
 *
 * Model Saron dinormalkan ke 0,5 m. Area bilah asli (diukur dari mesh
 * `Object_33` sebelum scaling) memiliki pusat:
 *
 * ```
 * -0.24670, -0.15615, -0.06783, 0.01654, 0.10078, 0.17912, 0.25643
 * ```
 *
 * Lebar penuh model sebelum scaling = 0,9335134 m, jadi faktor scaling
 * `scaleToUnits = 0.5f` = 0,53561095. Tap dipetakan ke pusat bilah terdekat,
 * bukan dibagi rata selebar model — pembagian selebar model membuat bilah 1
 * dan 7 bergeser satu nada.
 */
object NoteIndex {
    const val KEY_COUNT = 7

    private const val MODEL_SCALE = 0.53561095f

    private val BAR_CENTERS = floatArrayOf(
        -0.24670f,
        -0.15615f,
        -0.06783f,
        0.01654f,
        0.10078f,
        0.17912f,
        0.25643f,
    ).map { it * MODEL_SCALE }.toFloatArray()

    /** Indeks bilah 0..6 untuk koordinat X lokal marker (meter). */
    fun forMarkerLocalX(x: Float): Int {
        var bestIndex = 0
        var bestDistance = Float.MAX_VALUE
        for (index in BAR_CENTERS.indices) {
            val distance = abs(x - BAR_CENTERS[index])
            if (distance < bestDistance) {
                bestDistance = distance
                bestIndex = index
            }
        }
        return bestIndex
    }
}
