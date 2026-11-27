package com.gemaar.data

class MusicVolume private constructor(val percent: Int) {

    val fraction: Float = percent / 100f
    val isMuted: Boolean = percent == 0

    companion object {
        const val DEFAULT_PERCENT = 100

        fun of(percent: Int): MusicVolume = MusicVolume(percent.coerceIn(0, 100))

        fun default(): MusicVolume = MusicVolume(DEFAULT_PERCENT)
    }
}
