package com.gemaar.ar

import com.google.ar.core.AugmentedImage
import com.google.ar.core.TrackingState

/**
 * Marker dianggap benar-benar terlihat hanya saat ARCore melakukan
 * `FULL_TRACKING`.
 *
 * ARCore tetap melaporkan `TRACKING` dengan method `LAST_KNOWN_POSE` ketika
 * marker sudah keluar dari kamera. Kalau kondisi itu dianggap terdeteksi,
 * model tidak pernah disembunyikan dan hint tidak muncul.
 */
internal fun isMarkerVisible(
    trackingState: TrackingState?,
    trackingMethod: AugmentedImage.TrackingMethod?,
): Boolean =
    trackingState == TrackingState.TRACKING &&
        trackingMethod == AugmentedImage.TrackingMethod.FULL_TRACKING
