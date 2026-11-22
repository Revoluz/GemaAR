package com.gemaar.ar

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory

/**
 * Sumber marker AR.
 *
 * F-01 memakai satu marker untuk semua alat. File marker yang sama dipakai
 * untuk database AR dan untuk tombol Unduh di Petunjuk.
 */
object MarkerRepository {
    const val IMAGE_NAME = "gemaar-marker"
    const val ASSET_PATH = "markers/marker_placeholder.png"

    /** Marker dicetak/ditampilkan dengan lebar 21 cm (A4). */
    const val PHYSICAL_WIDTH_METERS = 0.21f

    fun loadBitmap(context: Context): Bitmap? = runCatching {
        context.assets.open(ASSET_PATH).use { input ->
            val decoded = BitmapFactory.decodeStream(input) ?: return null
            if (decoded.config == Bitmap.Config.ARGB_8888) {
                decoded
            } else {
                decoded.copy(Bitmap.Config.ARGB_8888, false) ?: decoded
            }
        }
    }.getOrNull()
}
