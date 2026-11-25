package com.gemaar.ar

import org.junit.Assert.assertEquals
import org.junit.Test

class InstrumentTapMapTest {
    @Test
    fun `slenthem columns use the one row`() {
        assertEquals(0, InstrumentTapMap.noteIndexFor("slenthem", -0.15f, 0f, 0.3f))
        assertEquals(3, InstrumentTapMap.noteIndexFor("slenthem", 0f, 0f, 0.3f))
        assertEquals(6, InstrumentTapMap.noteIndexFor("slenthem", 0.15f, 0f, 0.3f))
    }

    @Test
    fun `bonang maps two rows of six`() {
        assertEquals(0, InstrumentTapMap.noteIndexFor("bonang", -0.25f, 0.05f, 0.5f))
        assertEquals(5, InstrumentTapMap.noteIndexFor("bonang", 0.25f, 0.05f, 0.5f))
        assertEquals(6, InstrumentTapMap.noteIndexFor("bonang", -0.25f, -0.05f, 0.5f))
        assertEquals(11, InstrumentTapMap.noteIndexFor("bonang", 0.25f, -0.05f, 0.5f))
    }

    @Test
    fun `gender maps two rows of seven`() {
        assertEquals(0, InstrumentTapMap.noteIndexFor("gender_barung", -0.25f, 0.05f, 0.5f))
        assertEquals(6, InstrumentTapMap.noteIndexFor("gender_barung", 0.25f, 0.05f, 0.5f))
        assertEquals(7, InstrumentTapMap.noteIndexFor("gender_barung", -0.25f, -0.05f, 0.5f))
        assertEquals(13, InstrumentTapMap.noteIndexFor("gender_barung", 0.25f, -0.05f, 0.5f))
    }

    @Test
    fun `gong and kendang map seven pads`() {
        assertEquals(0, InstrumentTapMap.noteIndexFor("gong_kempul", -0.25f, 0f, 0.5f))
        assertEquals(6, InstrumentTapMap.noteIndexFor("gong_kempul", 0.25f, 0f, 0.5f))
        assertEquals(0, InstrumentTapMap.noteIndexFor("kendang", -0.15f, 0f, 0.3f))
        assertEquals(6, InstrumentTapMap.noteIndexFor("kendang", 0.15f, 0f, 0.3f))
    }
}
