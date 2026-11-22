package com.gemaar.ar

import org.junit.Assert.assertEquals
import org.junit.Test

class NoteIndexTest {
    @Test
    fun `first bar maps to note 1`() {
        // Previously this landed in note 2 because the full model width was used.
        assertEquals(0, NoteIndex.forMarkerLocalX(-0.1321f))
        assertEquals(0, NoteIndex.forMarkerLocalX(-0.15f))
    }

    @Test
    fun `last bar maps to note 7`() {
        // Previously this landed in note 6.
        assertEquals(6, NoteIndex.forMarkerLocalX(0.1373f))
        assertEquals(6, NoteIndex.forMarkerLocalX(0.15f))
    }

    @Test
    fun `middle bar maps to note 4`() {
        assertEquals(3, NoteIndex.forMarkerLocalX(0f))
        assertEquals(3, NoteIndex.forMarkerLocalX(0.0089f))
    }

    @Test
    fun `each bar center maps to its own note`() {
        assertEquals(0, NoteIndex.forMarkerLocalX(-0.1321f))
        assertEquals(1, NoteIndex.forMarkerLocalX(-0.0836f))
        assertEquals(2, NoteIndex.forMarkerLocalX(-0.0363f))
        assertEquals(3, NoteIndex.forMarkerLocalX(0.0089f))
        assertEquals(4, NoteIndex.forMarkerLocalX(0.0540f))
        assertEquals(5, NoteIndex.forMarkerLocalX(0.0959f))
        assertEquals(6, NoteIndex.forMarkerLocalX(0.1373f))
    }

    @Test
    fun `out of bounds clamps to the edge notes`() {
        assertEquals(0, NoteIndex.forMarkerLocalX(-10f))
        assertEquals(6, NoteIndex.forMarkerLocalX(10f))
    }
}
