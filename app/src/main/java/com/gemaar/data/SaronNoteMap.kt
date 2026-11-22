package com.gemaar.data

import com.gemaar.R

/**
 * Peta nada Saron Pelog untuk F-02.
 *
 * Urutan mengikuti bilah model dari kiri ke kanan: nada 1 paling rendah,
 * nada 7 paling tinggi. F-03 akan memindahkan data ini ke katalog alat;
 * untuk sekarang satu alat supaya scope tetap kecil.
 */
object SaronNoteMap {
    val notes: List<Note> = listOf(
        Note(id = 1, label = "Nada 1", sampleRes = R.raw.saron_pelog_1),
        Note(id = 2, label = "Nada 2", sampleRes = R.raw.saron_pelog_2),
        Note(id = 3, label = "Nada 3", sampleRes = R.raw.saron_pelog_3),
        Note(id = 4, label = "Nada 4", sampleRes = R.raw.saron_pelog_4),
        Note(id = 5, label = "Nada 5", sampleRes = R.raw.saron_pelog_5),
        Note(id = 6, label = "Nada 6", sampleRes = R.raw.saron_pelog_6),
        Note(id = 7, label = "Nada 7", sampleRes = R.raw.saron_pelog_7),
    )

    val count: Int = notes.size

    /** Nada pada posisi bilah 0..6; nilai di luar batas di-clamp. */
    fun noteAt(index: Int): Note = notes[index.coerceIn(0, notes.lastIndex)]

    fun noteById(id: Int): Note? = notes.firstOrNull { it.id == id }
}
