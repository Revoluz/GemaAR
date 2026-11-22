package com.gemaar.ar

import com.google.ar.core.AugmentedImage
import com.google.ar.core.TrackingState
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class MarkerVisibilityTest {
    @Test
    fun `full tracking is visible`() {
        assertTrue(
            isMarkerVisible(
                TrackingState.TRACKING,
                AugmentedImage.TrackingMethod.FULL_TRACKING,
            ),
        )
    }

    @Test
    fun `last known pose is not visible`() {
        assertFalse(
            isMarkerVisible(
                TrackingState.TRACKING,
                AugmentedImage.TrackingMethod.LAST_KNOWN_POSE,
            ),
        )
    }

    @Test
    fun `paused marker is not visible`() {
        assertFalse(
            isMarkerVisible(
                TrackingState.PAUSED,
                AugmentedImage.TrackingMethod.NOT_TRACKING,
            ),
        )
    }

    @Test
    fun `missing image is not visible`() {
        assertFalse(isMarkerVisible(null, null))
    }
}
