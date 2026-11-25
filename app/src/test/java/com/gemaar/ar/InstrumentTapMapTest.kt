package com.gemaar.ar

import org.junit.Assert.assertEquals
import org.junit.Test

class InstrumentTapMapTest {
    private val slenthemScale = 0.3f / 0.8447165f
    private val genderScale = 0.5f / 1.444458f
    private val gongScale = 0.5f / 2.5320467f

    @Test
    fun `slenthem taps hit the measured bar centers`() {
        val centers = floatArrayOf(
            -0.322f, -0.217f, -0.112f, -0.006f, 0.099f, 0.204f, 0.310f,
        )
        centers.forEachIndexed { index, center ->
            assertEquals(
                index,
                InstrumentTapMap.noteIndexFor(
                    "slenthem",
                    center * slenthemScale,
                    0f,
                    0.3f,
                ),
            )
        }
    }

    @Test
    fun `slenthem chooses the nearest bar at the boundary`() {
        // Batas antara bilah 4 dan 5 ada di x = 0,0465 * scale.
        val boundary = 0.0465f * slenthemScale
        assertEquals(
            3,
            InstrumentTapMap.noteIndexFor("slenthem", boundary - 0.004f, 0f, 0.3f),
        )
        assertEquals(
            4,
            InstrumentTapMap.noteIndexFor("slenthem", boundary + 0.004f, 0f, 0.3f),
        )
    }

    @Test
    fun `bonang maps two rows of six`() {
        assertEquals(0, InstrumentTapMap.noteIndexFor("bonang", -0.25f, 0.05f, 0.5f))
        assertEquals(5, InstrumentTapMap.noteIndexFor("bonang", 0.25f, 0.05f, 0.5f))
        assertEquals(6, InstrumentTapMap.noteIndexFor("bonang", -0.25f, -0.05f, 0.5f))
        assertEquals(11, InstrumentTapMap.noteIndexFor("bonang", 0.25f, -0.05f, 0.5f))
    }

    @Test
    fun `gender taps hit all fourteen bar centers in one row`() {
        val centers = floatArrayOf(
            -0.535f, -0.441f, -0.347f, -0.253f, -0.159f, -0.065f, 0.029f,
            0.123f, 0.217f, 0.311f, 0.406f, 0.500f, 0.594f, 0.688f,
        )
        centers.forEachIndexed { index, center ->
            assertEquals(
                index,
                InstrumentTapMap.noteIndexFor(
                    "gender_barung",
                    center * genderScale,
                    0f,
                    0.5f,
                ),
            )
        }
    }

    @Test
    fun `gong taps hit all nine measured gongs`() {
        val centers = floatArrayOf(
            -1.100f, -0.720f, -0.443f, -0.180f, 0.018f,
            0.180f, 0.454f, 0.656f, 1.042f,
        )
        centers.forEachIndexed { index, center ->
            assertEquals(
                index,
                InstrumentTapMap.noteIndexFor(
                    "gong_kempul",
                    center * gongScale,
                    0f,
                    0.5f,
                ),
            )
        }
    }

    @Test
    fun `kendang maps seven pads`() {
        assertEquals(0, InstrumentTapMap.noteIndexFor("kendang", -0.15f, 0f, 0.3f))
        assertEquals(6, InstrumentTapMap.noteIndexFor("kendang", 0.15f, 0f, 0.3f))
    }
}
