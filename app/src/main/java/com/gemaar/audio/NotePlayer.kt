package com.gemaar.audio

import com.gemaar.data.Note
import kotlinx.coroutines.flow.StateFlow

sealed interface NotePlayerState {
    data object Loading : NotePlayerState
    data object Ready : NotePlayerState
    data object Failed : NotePlayerState
}

/**
 * Kontrak pemutar nada. UI tidak menyentuh SoundPool secara langsung.
 */
interface NotePlayer {
    val state: StateFlow<NotePlayerState>

    /**
     * Volume untuk nada berikutnya, 0f..1f. Nilai di luar rentang dijepit.
     * Nada yang sedang berbunyi tidak diubah.
     */
    fun setVolume(volume: Float)

    fun play(note: Note)

    fun stopAll()

    fun release()
}
