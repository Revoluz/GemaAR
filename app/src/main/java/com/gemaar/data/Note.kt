package com.gemaar.data

import androidx.annotation.RawRes

/**
 * Satu nada yang bisa dibunyikan.
 *
 * @param id nomor nada 1..7, rendah ke tinggi.
 * @param label teks yang ditampilkan ke pengguna, mis. "Nada 3".
 * @param sampleRes berkas suara pendek untuk nada ini.
 */
data class Note(
    val id: Int,
    val label: String,
    @RawRes val sampleRes: Int,
)
