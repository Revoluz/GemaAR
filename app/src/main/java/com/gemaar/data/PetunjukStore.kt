package com.gemaar.data

import android.content.Context
import androidx.core.content.edit

class PetunjukStore(context: Context) {
    private val preferences =
        context.getSharedPreferences("gemaar_preferences", Context.MODE_PRIVATE)

    val hasSeenPetunjuk: Boolean
        get() = preferences.getBoolean(KEY_PETUNJUK_SEEN, false)

    fun markPetunjukSeen() {
        preferences.edit { putBoolean(KEY_PETUNJUK_SEEN, true) }
    }

    private companion object {
        const val KEY_PETUNJUK_SEEN = "petunjuk_seen"
    }
}
