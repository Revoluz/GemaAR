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
    fun `all instruments have audio after F-04`() {
        assertTrue(Catalog.instruments.all { it.audioAvailable })
    }

    @Test
    fun `unknown id falls back to saron`() {
        assertEquals("saron", Catalog.byId("unknown").id)
        assertEquals("saron", Catalog.byId(null).id)
    }

    @Test
    fun `bonang is audio available`() {
        assertTrue(Catalog.byId("bonang").audioAvailable)
    }

    @Test
    fun `kendang and slenthem use a smaller display scale`() {
        assertEquals(0.3f, Catalog.byId("kendang").modelScale, 0.0001f)
        assertEquals(0.3f, Catalog.byId("slenthem").modelScale, 0.0001f)
        assertEquals(
            ModelScale.DEFAULT_TARGET_METERS,
            Catalog.byId("saron").modelScale,
            0.0001f,
        )
    }
}
