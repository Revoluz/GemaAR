package com.gemaar.data

import com.gemaar.R

/**
 * Peta bunyi F-04 per alat. Saron tetap memakai [SaronNoteMap].
 */
object InstrumentAudioMap {
    private fun note(
        id: String,
        key: String,
        feedback: String,
        resource: Int,
    ) = Note(id, key, feedback, resource)

    private val slenthem = InstrumentAudio.Rows(
        listOf(
            listOf(
                note("slenthem_1", "1", "Nada 1", R.raw.slenthem_1),
                note("slenthem_2", "2", "Nada 2", R.raw.slenthem_2),
                note("slenthem_3", "3", "Nada 3", R.raw.slenthem_3),
                note("slenthem_4", "4", "Nada 4", R.raw.slenthem_4),
                note("slenthem_5", "5", "Nada 5", R.raw.slenthem_5),
                note("slenthem_6", "6", "Nada 6", R.raw.slenthem_6),
                note("slenthem_7", "7", "Nada 7", R.raw.slenthem_7),
            ),
        ),
    )

    private val bonangLow = listOf(
        note("bonang_1", "1", "Nada 1", R.raw.bonang_1),
        note("bonang_2", "2", "Nada 2", R.raw.bonang_2),
        note("bonang_3", "3", "Nada 3", R.raw.bonang_3),
        note("bonang_4", "4", "Nada 4", R.raw.bonang_4),
        note("bonang_5", "5", "Nada 5", R.raw.bonang_5),
        note("bonang_6", "6", "Nada 6", R.raw.bonang_6),
    )

    private val bonangHigh = listOf(
        note("bonang_1h", "1h", "Nada 1 atas", R.raw.bonang_1h),
        note("bonang_2h", "2h", "Nada 2 atas", R.raw.bonang_2h),
        note("bonang_3h", "3h", "Nada 3 atas", R.raw.bonang_3h),
        note("bonang_4h", "4h", "Nada 4 atas", R.raw.bonang_4h),
        note("bonang_5h", "5h", "Nada 5 atas", R.raw.bonang_5h),
        note("bonang_6h", "6h", "Nada 6 atas", R.raw.bonang_6h),
    )

    private val genderLow = listOf(
        note("gender_1", "1", "Nada 1", R.raw.gender_1),
        note("gender_2", "2", "Nada 2", R.raw.gender_2),
        note("gender_3", "3", "Nada 3", R.raw.gender_3),
        // CDM tidak punya GBPL4; pakai sample nada 3 sebagai tetangga terdekat.
        note("gender_4", "4", "Nada 4", R.raw.gender_3),
        note("gender_5", "5", "Nada 5", R.raw.gender_5),
        note("gender_6", "6", "Nada 6", R.raw.gender_6),
        note("gender_7", "7", "Nada 7", R.raw.gender_7),
    )

    private val genderHigh = listOf(
        note("gender_1h", "1h", "Nada 1 atas", R.raw.gender_1h),
        note("gender_2h", "2h", "Nada 2 atas", R.raw.gender_2h),
        note("gender_3h", "3h", "Nada 3 atas", R.raw.gender_3h),
        note("gender_4h", "4h", "Nada 4 atas", R.raw.gender_3h),
        note("gender_5h", "5h", "Nada 5 atas", R.raw.gender_5h),
        note("gender_6h", "6h", "Nada 6 atas", R.raw.gender_6h),
        note("gender_7h", "7h", "Nada 7 atas", R.raw.gender_7h),
    )

    private val gong = InstrumentAudio.Pads(
        listOf(
            note("gong_ageng", "Gong", "Gong Ageng", R.raw.gong_ageng),
            note("kempul_1", "K1", "Kempul 1", R.raw.kempul_1),
            note("kempul_2", "K2", "Kempul 2", R.raw.kempul_2),
            note("kempul_3", "K3", "Kempul 3", R.raw.kempul_3),
            note("kempul_5", "K5", "Kempul 5", R.raw.kempul_5),
            note("kempul_6", "K6", "Kempul 6", R.raw.kempul_6),
            note("kempul_7", "K7", "Kempul 7", R.raw.kempul_7),
        ),
    )

    private val kendang = InstrumentAudio.Pads(
        listOf(
            note("kendang_dha", "DHA", "DHA", R.raw.kendang_dha),
            note("kendang_dlang", "DLANG", "DLANG", R.raw.kendang_dlang),
            note("kendang_lung", "LUNG", "LUNG", R.raw.kendang_lung),
            note("kendang_tak", "TAK", "TAK", R.raw.kendang_tak),
            note("kendang_thung", "THUNG", "THUNG", R.raw.kendang_thung),
            note("kendang_tong", "TONG", "TONG", R.raw.kendang_tong),
            note("kendang_dhung", "DHUNG", "DHUNG", R.raw.kendang_dhung),
        ),
    )

    private val byInstrument: Map<String, InstrumentAudio> = mapOf(
        "saron" to InstrumentAudio.Rows(listOf(SaronNoteMap.notes)),
        "slenthem" to slenthem,
        "bonang" to InstrumentAudio.Rows(listOf(bonangLow, bonangHigh)),
        "gender_barung" to InstrumentAudio.Rows(listOf(genderLow, genderHigh)),
        "gong_kempul" to gong,
        "kendang" to kendang,
    )

    fun forInstrument(instrumentId: String): InstrumentAudio? =
        byInstrument[instrumentId]
}
