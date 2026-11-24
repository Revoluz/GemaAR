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

    private companion object {
        const val KEY_SELECTED_INSTRUMENT = "selected_instrument"
        const val KEY_CATALOG_AUTO_OPENED = "catalog_auto_opened"
    }
}