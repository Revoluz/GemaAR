package com.gemaar.data

import androidx.annotation.RawRes

/**
 * Satu bunyi yang bisa dimainkan: nada alat bernada atau pukulan Kendang.
 *
 * @param id kunci unik lintas baris/alat, mis. "bonang_1", "gender_4h".
 * @param keyLabel teks pendek pada tombol/pad, mis. "1", "1h", "DHA".
 * @param feedbackLabel teks yang muncul saat bunyi dimainkan.
 * @param sampleRes berkas suara pendek untuk bunyi ini.
 */
data class Note(
    val id: String,
    val keyLabel: String,
    val feedbackLabel: String,
    @RawRes val sampleRes: Int,
)
