package com.gemaar.data

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNull
import org.junit.Test

class SaronNoteMapTest {
    @Test
    fun `has seven notes in low to high order`() {
        assertEquals(7, SaronNoteMap.count)
        assertEquals((1..7).toList(), SaronNoteMap.notes.map { it.id })
    }

    @Test
    fun `labels follow note ids`() {
        assertEquals(
            (1..7).map { "Nada $it" },
            SaronNoteMap.notes.map { it.label },
        )
    }

    @Test
    fun `every note has a unique sample`() {
        val samples = SaronNoteMap.notes.map { it.sampleRes }
        assertEquals(samples.size, samples.toSet().size)
    }

    @Test
    fun `noteAt clamps the key index`() {
        assertEquals(1, SaronNoteMap.noteAt(-4).id)
        assertEquals(1, SaronNoteMap.noteAt(0).id)
        assertEquals(4, SaronNoteMap.noteAt(3).id)
        assertEquals(7, SaronNoteMap.noteAt(6).id)
        assertEquals(7, SaronNoteMap.noteAt(99).id)
    }

    @Test
    fun `noteById returns the matching note`() {
        assertEquals(2, SaronNoteMap.noteById(2)?.id)
        assertNull(SaronNoteMap.noteById(0))
    }

    @Test
    fun `sample resources are not all identical`() {
        assertNotEquals(
            SaronNoteMap.noteAt(0).sampleRes,
            SaronNoteMap.noteAt(6).sampleRes,
        )
    }
}
