package com.gemaar.data

import com.gemaar.R

/**
 * Peta nada Saron Pelog.
 *
 * Urutan mengikuti bilah model dari kiri ke kanan: nada 1 paling rendah,
 * nada 7 paling tinggi.
 */
object SaronNoteMap {
    val notes: List<Note> = listOf(
        Note("saron_1", "1", "Nada 1", R.raw.saron_pelog_1),
        Note("saron_2", "2", "Nada 2", R.raw.saron_pelog_2),
        Note("saron_3", "3", "Nada 3", R.raw.saron_pelog_3),
        Note("saron_4", "4", "Nada 4", R.raw.saron_pelog_4),
        Note("saron_5", "5", "Nada 5", R.raw.saron_pelog_5),
        Note("saron_6", "6", "Nada 6", R.raw.saron_pelog_6),
        Note("saron_7", "7", "Nada 7", R.raw.saron_pelog_7),
    )

    val count: Int = notes.size

    fun noteAt(index: Int): Note = notes[index.coerceIn(0, notes.lastIndex)]

    fun noteById(id: String): Note? = notes.firstOrNull { it.id == id }
}
