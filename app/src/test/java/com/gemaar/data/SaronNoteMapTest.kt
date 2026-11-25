package com.gemaar.data

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNull
import org.junit.Test

class SaronNoteMapTest {
    @Test
    fun `has seven notes in low to high order`() {
        assertEquals(7, SaronNoteMap.count)
        assertEquals(
            (1..7).map { "saron_$it" },
            SaronNoteMap.notes.map { it.id },
        )
    }

    @Test
    fun `labels follow note ids`() {
        assertEquals(
            (1..7).map { "Nada $it" },
            SaronNoteMap.notes.map { it.feedbackLabel },
        )
        assertEquals(
            (1..7).map { it.toString() },
            SaronNoteMap.notes.map { it.keyLabel },
        )
    }

    @Test
    fun `every note has a unique sample`() {
        val samples = SaronNoteMap.notes.map { it.sampleRes }
        assertEquals(samples.size, samples.toSet().size)
    }

    @Test
    fun `noteAt clamps the key index`() {
        assertEquals("saron_1", SaronNoteMap.noteAt(-4).id)
        assertEquals("saron_1", SaronNoteMap.noteAt(0).id)
        assertEquals("saron_4", SaronNoteMap.noteAt(3).id)
        assertEquals("saron_7", SaronNoteMap.noteAt(6).id)
        assertEquals("saron_7", SaronNoteMap.noteAt(99).id)
    }

    @Test
    fun `noteById returns the matching note`() {
        assertEquals("saron_2", SaronNoteMap.noteById("saron_2")?.id)
        assertNull(SaronNoteMap.noteById("unknown"))
    }

    @Test
    fun `sample resources are not all identical`() {
        assertNotEquals(
            SaronNoteMap.noteAt(0).sampleRes,
            SaronNoteMap.noteAt(6).sampleRes,
        )
    }
}
