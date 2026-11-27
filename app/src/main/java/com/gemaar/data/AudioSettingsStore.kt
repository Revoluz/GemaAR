package com.gemaar.data

import android.content.Context

class AudioSettingsStore(context: Context) {
    private val preferences =
        context.getSharedPreferences("gemaar_preferences", Context.MODE_PRIVATE)

    fun loadVolume(): MusicVolume {
        val stored = preferences.all[KEY_MUSIC_VOLUME] as? Int
        return MusicVolume.of(stored ?: MusicVolume.DEFAULT_PERCENT)
    }

    // commit() (bukan edit { } yang memakai apply()) karena FR-012 butuh status
    // berhasil/gagal untuk ditampilkan ke pengguna.
    @Suppress("UseKtx")
    fun saveVolume(volume: MusicVolume): Boolean =
        preferences.edit().putInt(KEY_MUSIC_VOLUME, volume.percent).commit()

    private companion object {
        const val KEY_MUSIC_VOLUME = "music_volume"
    }
}
