package com.gemaar.data

/**
 * Naskah Kartu Budaya untuk enam alat.
 *
 * Fakta mengikuti rujukan di `docs/specs/F-05-culture-card/spec.md`:
 * UNESCO (Gamelan, 2021) dan artikel Wikipedia untuk tiap alat. Teks sengaja
 * pendek dan memakai istilah yang dijelaskan di kartu.
 */
object CultureCardMap {
    private val cards: List<CultureCard> = listOf(
        CultureCard(
            instrumentId = "saron",
            origin = "Jawa Tengah — gamelan Jawa",
            formAndMaterial = "Saron terdiri atas tujuh bilah perunggu yang " +
                "ditata di atas rangka kayu beresonansi (rancak). Bilah dipukul " +
                "dengan tabuh kayu.",
            playing = "Pemain duduk di lantai menghadap saron. Tangan kanan " +
                "memukul bilah, tangan lainnya menahan bilah sebelumnya agar " +
                "suara tidak saling bertumpuk. Nada dimainkan mengikuti melodi.",
            role = "Saron memainkan balungan, yaitu kerangka melodi lagu " +
                "gamelan. Ada saron panerus (peking), saron barung, dan demung; " +
                "masing-masing satu oktaf berbeda.",
            sourceCredit = "Sumber: UNESCO 2021; Wikipedia — Saron",
        ),
        CultureCard(
            instrumentId = "bonang",
            origin = "Jawa Tengah — gamelan Jawa",
            formAndMaterial = "Bonang adalah kumpulan gong kecil berpencon " +
                "(pencon) yang diletakkan mendatar di atas tali dalam rangka " +
                "kayu. Pencon bernada rendah berkepala datar, yang tinggi " +
                "berkepala melengkung. Alat ini dipukul dengan tabuh berlapis kain.",
            playing = "Pemain memukul pencon dengan tabuh. Nada rendah dan " +
                "tinggi dimainkan bergantian untuk membentuk pola. Bonang barung " +
                "memberi isyarat kepada pemain lain, sedangkan bonang panerus " +
                "memainkan pola lebih cepat.",
            role = "Bonang termasuk instrumen penghias (elaborasi). Polanya " +
                "dapat berupa imbal (saling mengisi) dan sekaran (hiasan melodi). " +
                "Ada juga bonang panembung yang bernada paling rendah.",
            sourceCredit = "Sumber: UNESCO 2021; Wikipedia — Bonang",
        ),
        CultureCard(
            instrumentId = "slenthem",
            origin = "Jawa Tengah — gamelan Jawa",
            formAndMaterial = "Slenthem adalah metalofon dari keluarga gendèr. " +
                "Bilah perunggunya digantung dengan tali kulit di atas tabung " +
                "resonansi bambu di dalam rangka kayu. Untuk laras pelog ada " +
                "tujuh bilah; untuk slendro enam bilah.",
            playing = "Bilah dipukul dengan tabuh pendek berlapis kain atau " +
                "karet. Satu tangan menahan bilah sebelumnya agar suara tidak " +
                "tumpang tindih. Slenthem dimainkan dengan lembut, tanpa " +
                "hentakan keras.",
            role = "Slenthem bersuara rendah dan lembut. Alat ini memainkan " +
                "balungan dan sering dipakai di ansambel halus seperti Gamelan " +
                "Gadhon, tetapi juga hadir di Gamelan Gedhé.",
            sourceCredit = "Sumber: UNESCO 2021; Wikipedia — Slenthem",
        ),
        CultureCard(
            instrumentId = "gender_barung",
            origin = "Jawa Tengah — gamelan Jawa",
            formAndMaterial = "Gender barung adalah metalofon dengan bilah " +
                "logam yang digantung di atas resonator bambu atau logam. " +
                "Setiap bilah punya resonator sendiri. Alat ini dipukul dengan " +
                "tabuh berdisk kayu berlapis.",
            playing = "Pemain memakai dua tabuh, satu di tiap tangan. Tangan " +
                "kanan dan kiri memainkan pola yang saling melengkapi; nada yang " +
                "sudah dibunyikan ditahan agar tidak mengganggu nada berikutnya.",
            role = "Gender barung memainkan pola melodi yang lebih lambat dan " +
                "rumit daripada balungan. Pola semi-improvisasinya disebut " +
                "cengkok, dengan interval kempyung (kuint) dan gembyang (oktaf). " +
                "Gender juga sering memberi isyarat peralihan.",
            sourceCredit = "Sumber: UNESCO 2021; Wikipedia — Gendèr",
        ),
        CultureCard(
            instrumentId = "gong_kempul",
            origin = "Jawa Tengah — gamelan Jawa",
            formAndMaterial = "Gong ageng adalah gong perunggu terbesar dengan " +
                "pencon di tengah. Gong suwukan lebih kecil, sedangkan kempul " +
                "adalah gong gantung kecil berpencon. Semuanya dipukul dengan " +
                "tabuh berlapis.",
            playing = "Gong dipukul pada pencon tengah dengan tabuh. Gong ageng " +
                "menandai bagian lagu yang panjang (gongan), gong suwukan " +
                "menandai frasa yang lebih pendek, dan kempul mengisi bagian di " +
                "antaranya.",
            role = "Gong ageng dianggap instrumen terpenting dalam gamelan; " +
                "bunyinya menandai bagian besar lagu. Kempul membagi nongan, " +
                "yaitu bagian di antara pukulan kenong. Satu rak biasanya berisi " +
                "beberapa kempul.",
            sourceCredit = "Sumber: UNESCO 2021; Wikipedia — Gong ageng, Kempul",
        ),
        CultureCard(
            instrumentId = "kendang",
            origin = "Jawa — gamelan Jawa",
            formAndMaterial = "Kendang adalah drum dua sisi. Di Jawa, sisi yang " +
                "lebih besar biasanya berada di kanan dan berbunyi lebih rendah. " +
                "Kulit kerbau dipakai untuk sisi rendah, kulit kambing untuk sisi " +
                "tinggi; tubuhnya dari kayu nangka, kelapa, atau cempedak.",
            playing = "Kendang diletakkan mendatar di atas stand dan dimainkan " +
                "dengan tangan sambil duduk. Pukulan pada sisi dan tepi " +
                "menghasilkan bunyi berbeda. Pemain mengatur tempo dan memberi " +
                "isyarat kepada ansambel.",
            role = "Kendang menjaga tempo (laya), mengubah kerapatan irama, dan " +
                "menandai peralihan (paralihan) serta akhir bagian (suwuk). " +
                "Dalam tari dan wayang, kendang mengikuti gerak penari dan " +
                "meneruskannya ke pemain lain.",
            sourceCredit = "Sumber: UNESCO 2021; Wikipedia — Kendang",
        ),
    )

    private val byInstrument: Map<String, CultureCard> =
        cards.associateBy { it.instrumentId }

    fun forInstrument(instrumentId: String): CultureCard? = byInstrument[instrumentId]
}
