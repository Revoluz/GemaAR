package com.gemaar.data

/**
 * Teks budaya untuk satu alat.
 *
 * Naskah mengikuti `docs/specs/F-05-culture-card/spec.md`. Empat topik wajib
 * dipecah ke [origin], [formAndMaterial], [playing], dan [role] agar isi tiap
 * kartu bisa diverifikasi.
 */
data class CultureCard(
    val instrumentId: String,
    val origin: String,
    val formAndMaterial: String,
    val playing: String,
    val role: String,
    val sourceCredit: String,
)
