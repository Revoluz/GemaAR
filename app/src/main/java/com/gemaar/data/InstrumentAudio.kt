package com.gemaar.data

/**
 * Kontrol bunyi untuk satu alat.
 *
 * - [Rows] untuk alat bernada dengan satu atau dua baris bilah/pencon.
 * - [Pads] untuk gong/kempul dan Kendang.
 */
sealed interface InstrumentAudio {
    val allNotes: List<Note>

    data class Rows(val rows: List<List<Note>>) : InstrumentAudio {
        init {
            require(rows.isNotEmpty() && rows.all { it.isNotEmpty() }) {
                "Rows must not be empty"
            }
        }

        override val allNotes: List<Note> = rows.flatten()
    }

    data class Pads(val pads: List<Note>) : InstrumentAudio {
        init {
            require(pads.isNotEmpty()) { "Pads must not be empty" }
        }

        override val allNotes: List<Note> = pads
    }
}
