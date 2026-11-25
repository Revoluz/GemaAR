package com.gemaar.data

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class CollectionProgressTest {
    @Test
    fun `empty progress has no played instruments`() {
        val progress = CollectionProgress()
        assertTrue(progress.playedIds.isEmpty())
        assertFalse(progress.isPlayed("saron"))
    }

    @Test
    fun `marking an instrument adds only that instrument`() {
        val progress = CollectionProgress().markPlayed("saron")
        assertTrue(progress.isPlayed("saron"))
        assertFalse(progress.isPlayed("bonang"))
        assertEquals(setOf("saron"), progress.playedIds)
    }

    @Test
    fun `marking the same instrument twice is idempotent`() {
        val once = CollectionProgress().markPlayed("bonang")
        val twice = once.markPlayed("bonang")
        assertEquals(once, twice)
        assertEquals(setOf("bonang"), twice.playedIds)
    }

    @Test
    fun `all six instruments can be marked`() {
        val progress = Catalog.instruments.fold(CollectionProgress()) { acc, instrument ->
            acc.markPlayed(instrument.id)
        }
        assertEquals(Catalog.instruments.size, progress.playedIds.size)
        Catalog.instruments.forEach { assertTrue(progress.isPlayed(it.id)) }
    }

    @Test
    fun `unknown stored ids do not affect catalog instruments`() {
        val progress = CollectionProgress(setOf("unknown")).markPlayed("saron")
        assertTrue(progress.isPlayed("saron"))
        assertFalse(progress.isPlayed("bonang"))
        assertEquals(2, progress.playedIds.size)
    }
}
