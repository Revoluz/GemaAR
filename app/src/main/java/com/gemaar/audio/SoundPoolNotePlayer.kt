package com.gemaar.audio

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import android.util.Log
import com.gemaar.data.Note
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Pemutar nada pendek berbasis SoundPool.
 *
 * Tujuh sampel dimuat sekali saat layar AR dibuat. Nada boleh tumpang tindih;
 * SoundPool tidak menghentikan nada sebelumnya saat nada baru dimainkan.
 */
class SoundPoolNotePlayer(
    context: Context,
    private val notes: List<Note> = emptyList(),
) : NotePlayer {
    private val soundPool = SoundPool.Builder()
        .setMaxStreams(MAX_STREAMS)
        .setAudioAttributes(
            AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build(),
        )
        .build()

    private val soundIdsByNoteId = mutableMapOf<String, Int>()
    private var loadedCount = 0
    private var failed = false
    private var released = false

    /** Volume 0f..1f yang dipakai nada berikutnya. Default penuh. */
    private var volume = 1f

    private val _state = MutableStateFlow<NotePlayerState>(NotePlayerState.Loading)
    override val state: StateFlow<NotePlayerState> = _state.asStateFlow()

    init {
        if (notes.isEmpty()) {
            _state.value = NotePlayerState.Failed
        } else {
            soundPool.setOnLoadCompleteListener { _, sampleId, status ->
                if (status != 0) {
                    Log.e("GemaArAudio", "load failed sampleId=$sampleId status=$status")
                    failed = true
                    _state.value = NotePlayerState.Failed
                    return@setOnLoadCompleteListener
                }
                loadedCount += 1
                if (!failed && loadedCount == notes.size) {
                    Log.d("GemaArAudio", "ready: ${notes.size} samples loaded")
                    _state.value = NotePlayerState.Ready
                }
            }

            notes.forEach { note ->
                val soundId = soundPool.load(context.applicationContext, note.sampleRes, 1)
                if (soundId == 0) {
                    Log.e("GemaArAudio", "load returned 0 for note ${note.id}")
                    failed = true
                    _state.value = NotePlayerState.Failed
                } else {
                    Log.d("GemaArAudio", "loading note=${note.id} sampleId=$soundId")
                    soundIdsByNoteId[note.id] = soundId
                }
            }
        }
    }

    override fun setVolume(volume: Float) {
        this.volume = volume.coerceIn(0f, 1f)
    }

    override fun play(note: Note) {
        if (released || _state.value != NotePlayerState.Ready) return
        val soundId = soundIdsByNoteId[note.id] ?: return
        val streamId = soundPool.play(
            soundId,
            volume,
            volume,
            PRIORITY,
            NO_LOOP,
            NORMAL_RATE,
        )
        Log.d("GemaArAudio", "play note=${note.id} stream=$streamId volume=$volume")
    }

    override fun stopAll() {
        if (released) return
        soundIdsByNoteId.values.forEach { soundPool.stop(it) }
    }

    override fun release() {
        if (released) return
        released = true
        soundPool.release()
    }

    private companion object {
        const val MAX_STREAMS = 8
        const val PRIORITY = 1
        const val NO_LOOP = 0
        const val NORMAL_RATE = 1f
    }
}
