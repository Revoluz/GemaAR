package com.gemaar.data

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class InstrumentAudioMapTest {
    private fun audio(id: String): InstrumentAudio =
        requireNotNull(InstrumentAudioMap.forInstrument(id))

    @Test
    fun `saron has one row of seven notes`() {
        val rows = audio("saron") as InstrumentAudio.Rows
        assertEquals(1, rows.rows.size)
        assertEquals(7, rows.rows.first().size)
    }

    @Test
    fun `slenthem has seven notes`() {
        val rows = audio("slenthem") as InstrumentAudio.Rows
        assertEquals(7, rows.allNotes.size)
    }

    @Test
    fun `bonang has six low and six high notes`() {
        val rows = audio("bonang") as InstrumentAudio.Rows
        assertEquals(2, rows.rows.size)
        assertEquals(6, rows.rows[0].size)
        assertEquals(6, rows.rows[1].size)
        assertEquals(12, rows.allNotes.size)
    }

    @Test
    fun `gender has fourteen notes and bar four uses note three`() {
        val rows = audio("gender_barung") as InstrumentAudio.Rows
        assertEquals(2, rows.rows.size)
        assertEquals(7, rows.rows[0].size)
        assertEquals(7, rows.rows[1].size)
        assertEquals(14, rows.allNotes.size)
        assertEquals(rows.rows[0][2].sampleRes, rows.rows[0][3].sampleRes)
        assertEquals(rows.rows[1][2].sampleRes, rows.rows[1][3].sampleRes)
        assertEquals("4", rows.rows[0][3].keyLabel)
        assertEquals("4h", rows.rows[1][3].keyLabel)
    }

    @Test
    fun `gong and kendang have seven pads`() {
        assertEquals(7, (audio("gong_kempul") as InstrumentAudio.Pads).pads.size)
        assertEquals(7, (audio("kendang") as InstrumentAudio.Pads).pads.size)
    }

    @Test
    fun `note ids are unique per instrument`() {
        listOf("saron", "slenthem", "bonang", "gender_barung", "gong_kempul", "kendang")
            .forEach { id ->
                val notes = audio(id).allNotes
                assertEquals(notes.size, notes.map { it.id }.toSet().size)
            }
    }

    @Test
    fun `unknown instrument has no audio`() {
        assertEquals(null, InstrumentAudioMap.forInstrument("unknown"))
    }
}
