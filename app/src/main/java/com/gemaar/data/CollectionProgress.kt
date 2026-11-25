package com.gemaar.data

/**
 * Kumpulan alat yang sudah pernah dimainkan.
 *
 * Nilai ini murni dan tidak menyentuh penyimpanan; `InstrumentStore` yang
 * menyimpan hasilnya. Operasi [markPlayed] idempoten agar mengetuk beberapa
 * nada tidak mengubah status berkali-kali.
 */
data class CollectionProgress(
    val playedIds: Set<String> = emptySet(),
) {
    fun markPlayed(instrumentId: String): CollectionProgress =
        if (instrumentId in playedIds) {
            this
        } else {
            copy(playedIds = playedIds + instrumentId)
        }

    fun isPlayed(instrumentId: String): Boolean = instrumentId in playedIds
}
