package com.gemaar.data

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class CatalogTest {
    @Test
    fun `catalog has six instruments`() {
        assertEquals(6, Catalog.instruments.size)
    }

    @Test
    fun `instrument ids are unique`() {
        val ids = Catalog.instruments.map { it.id }
        assertEquals(ids.size, ids.toSet().size)
    }

    @Test
    fun `only saron has audio in F-03`() {
        assertTrue(Catalog.saron.audioAvailable)
        assertTrue(
            Catalog.instruments
                .filter { it.id != "saron" }
                .none { it.audioAvailable },
        )
    }

    @Test
    fun `unknown id falls back to saron`() {
        assertEquals("saron", Catalog.byId("unknown").id)
        assertEquals("saron", Catalog.byId(null).id)
    }

    @Test
    fun `bonang is not audio available`() {
        assertFalse(Catalog.byId("bonang").audioAvailable)
    }

    @Test
    fun `kendang uses a smaller display scale`() {
        assertEquals(0.3f, Catalog.byId("kendang").modelScale, 0.0001f)
        assertEquals(
            ModelScale.DEFAULT_TARGET_METERS,
            Catalog.byId("saron").modelScale,
            0.0001f,
        )
    }
}
