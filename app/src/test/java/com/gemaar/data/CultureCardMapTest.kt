package com.gemaar.data

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class CultureCardMapTest {
    @Test
    fun `every catalog instrument has a culture card`() {
        Catalog.instruments.forEach { instrument ->
            assertNotNull(
                "missing culture card for ${instrument.id}",
                CultureCardMap.forInstrument(instrument.id),
            )
        }
    }

    @Test
    fun `card has all required topics filled`() {
        Catalog.instruments.forEach { instrument ->
            val card = requireNotNull(CultureCardMap.forInstrument(instrument.id))
            assertEquals(instrument.id, card.instrumentId)
            assertTrue("origin empty for ${instrument.id}", card.origin.isNotBlank())
            assertTrue(
                "form empty for ${instrument.id}",
                card.formAndMaterial.isNotBlank(),
            )
            assertTrue("playing empty for ${instrument.id}", card.playing.isNotBlank())
            assertTrue("role empty for ${instrument.id}", card.role.isNotBlank())
            assertTrue(
                "source credit empty for ${instrument.id}",
                card.sourceCredit.isNotBlank(),
            )
        }
    }

    @Test
    fun `cards cite the UNESCO gamelan source`() {
        Catalog.instruments.forEach { instrument ->
            val card = requireNotNull(CultureCardMap.forInstrument(instrument.id))
            assertTrue(card.sourceCredit.contains("UNESCO"))
            assertTrue(card.sourceCredit.contains("Sumber"))
        }
    }

    @Test
    fun `unknown instrument has no card`() {
        assertNull(CultureCardMap.forInstrument("unknown"))
    }
}
