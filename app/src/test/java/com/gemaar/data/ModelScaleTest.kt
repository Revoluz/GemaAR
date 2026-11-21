package com.gemaar.data

import org.junit.Assert.assertEquals
import org.junit.Test

class ModelScaleTest {
    @Test
    fun `gong fits the default marker target`() {
        assertEquals(0.5f / 2.53f, ModelScale.factorFor(2.53f), 0.0001f)
    }

    @Test
    fun `saron fits the default marker target`() {
        assertEquals(0.5f / 0.93f, ModelScale.factorFor(0.93f), 0.0001f)
    }

    @Test
    fun `invalid size returns identity`() {
        assertEquals(1f, ModelScale.factorFor(0f), 0f)
    }
}
