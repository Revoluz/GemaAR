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
    fun `bonang maps the front row to low notes and back row to high notes`() {
        // allNotes: 0–5 = 1h–6h (atas), 6–11 = 1–6 (bawah).
        // +Z = baris depan model = nada bawah.
        assertEquals(6, InstrumentTapMap.noteIndexFor("bonang", -0.25f, 0.05f, 0.5f))
        assertEquals(11, InstrumentTapMap.noteIndexFor("bonang", 0.25f, 0.05f, 0.5f))
        // -Z = baris belakang model = nada atas.
        assertEquals(0, InstrumentTapMap.noteIndexFor("bonang", -0.25f, -0.05f, 0.5f))
        assertEquals(5, InstrumentTapMap.noteIndexFor("bonang", 0.25f, -0.05f, 0.5f))
    }

    @Test
    fun `gender taps hit all fourteen bar centers in one row`() {
        val centers = floatArrayOf(
            -0.629f, -0.535f, -0.441f, -0.347f, -0.253f, -0.159f, -0.065f,
            0.029f, 0.123f, 0.217f, 0.311f, 0.406f, 0.500f, 0.594f,
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
    fun `gender 1h maps to the eighth bar and 7h stays on the last bar`() {
        val eighthBar = 0.029f * genderScale
        val lastBar = 0.594f * genderScale
        val rightOrnament = 0.688f * genderScale

        assertEquals(
            7,
            InstrumentTapMap.noteIndexFor("gender_barung", eighthBar, 0f, 0.5f),
        )
        assertEquals(
            13,
            InstrumentTapMap.noteIndexFor("gender_barung", lastBar, 0f, 0.5f),
        )
        // Ornamen ujung kanan bukan bilah; tap terdekat tetap bilah 14.
        assertEquals(
            13,
            InstrumentTapMap.noteIndexFor("gender_barung", rightOrnament, 0f, 0.5f),
        )
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
