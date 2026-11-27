package com.gemaar.data

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class MusicVolumeTest {

    @Test
    fun defaultAdalah100() {
        assertEquals(100, MusicVolume.default().percent)
    }

    @Test
    fun diAtas100Dipaksa100() {
        assertEquals(100, MusicVolume.of(150).percent)
    }

    @Test
    fun diBawah0Dipaksa0() {
        assertEquals(0, MusicVolume.of(-5).percent)
    }

    @Test
    fun fractionMengikutiPersen() {
        assertEquals(0.4f, MusicVolume.of(40).fraction, 0.001f)
    }

    @Test
    fun nolBerartiSenyap() {
        assertTrue(MusicVolume.of(0).isMuted)
    }

    @Test
    fun satuTidakSenyap() {
        assertFalse(MusicVolume.of(1).isMuted)
    }
}
