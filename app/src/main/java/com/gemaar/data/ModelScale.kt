package com.gemaar.data

/**
 * Marker/AR display helpers.
 *
 * Models are stored in real-world meters. For the MVP we normalize each model
 * so its largest dimension fits on a small marker.
 */
object ModelScale {
    const val DEFAULT_TARGET_METERS = 0.5f

    fun factorFor(
        modelMaxMeters: Float,
        targetMeters: Float = DEFAULT_TARGET_METERS,
    ): Float {
        if (modelMaxMeters <= 0f || targetMeters <= 0f) return 1f
        return targetMeters / modelMaxMeters
    }
}
