package com.gemaar.data

import org.junit.Assert.assertEquals
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
        assertEquals(1, rows.rows.size)
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
    fun `gender has fourteen notes in one continuous row`() {
        val rows = audio("gender_barung") as InstrumentAudio.Rows
        assertEquals(1, rows.rows.size)
        assertEquals(14, rows.rows.first().size)
        assertEquals(14, rows.allNotes.size)
        assertEquals(
            listOf("1", "2", "3", "4", "5", "6", "7", "1h", "2h", "3h", "4h", "5h", "6h", "7h"),
            rows.rows.first().map { it.keyLabel },
        )
    }

    @Test
    fun `gender bar four uses note three`() {
        val notes = audio("gender_barung").allNotes
        assertEquals(notes[2].sampleRes, notes[3].sampleRes)
        assertEquals(notes[9].sampleRes, notes[10].sampleRes)
    }

    @Test
    fun `gong has nine notes with ageng and suwukan at the ends`() {
        val pads = (audio("gong_kempul") as InstrumentAudio.Pads).pads
        assertEquals(9, pads.size)
        assertEquals("gong_ageng", pads.first().id)
        assertEquals("suwukan", pads.last().id)
        assertEquals(
            listOf(
                "gong_ageng", "kempul_1", "kempul_2", "kempul_3", "kempul_4",
                "kempul_5", "kempul_6", "kempul_7", "suwukan",
            ),
            pads.map { it.id },
        )
    }

    @Test
    fun `gong kempul four uses kempul three`() {
        val pads = (audio("gong_kempul") as InstrumentAudio.Pads).pads
        assertEquals(pads[3].sampleRes, pads[4].sampleRes)
    }

    @Test
    fun `kendang has seven pads`() {
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
