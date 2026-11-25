package com.gemaar.data

import android.content.Context
import androidx.core.content.edit

class InstrumentStore(context: Context) {
    private val preferences =
        context.getSharedPreferences("gemaar_preferences", Context.MODE_PRIVATE)

    var selectedInstrumentId: String?
        get() = preferences.getString(KEY_SELECTED_INSTRUMENT, null)
        set(value) = preferences.edit { putString(KEY_SELECTED_INSTRUMENT, value) }

    var catalogAutoOpened: Boolean
        get() = preferences.getBoolean(KEY_CATALOG_AUTO_OPENED, false)
        set(value) = preferences.edit { putBoolean(KEY_CATALOG_AUTO_OPENED, value) }

    /** Id alat yang pernah dimainkan; salinan agar StringSet tidak dimutasi. */
    val playedInstrumentIds: Set<String>
        get() = preferences
            .getStringSet(KEY_PLAYED_INSTRUMENTS, emptySet())
            ?.toSet()
            .orEmpty()

    fun markPlayed(instrumentId: String) {
        val progress = CollectionProgress(playedInstrumentIds).markPlayed(instrumentId)
        preferences.edit { putStringSet(KEY_PLAYED_INSTRUMENTS, progress.playedIds) }
    }

    private companion object {
        const val KEY_SELECTED_INSTRUMENT = "selected_instrument"
        const val KEY_CATALOG_AUTO_OPENED = "catalog_auto_opened"
        const val KEY_PLAYED_INSTRUMENTS = "played_instruments"
    }
}