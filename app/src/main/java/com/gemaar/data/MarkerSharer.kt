package com.gemaar.data

import android.content.ClipData
import android.content.Context
import android.content.Intent
import androidx.core.content.FileProvider
import java.io.File

object MarkerSharer {
    private const val MARKER_ASSET = "markers/marker_placeholder.png"
    private const val AUTHORITY_SUFFIX = ".fileprovider"

    fun share(context: Context) {
        val directory = File(context.cacheDir, "markers").apply { mkdirs() }
        val file = File(directory, "gemaar-marker.png")
        context.assets.open(MARKER_ASSET).use { input ->
            file.outputStream().use { output -> input.copyTo(output) }
        }

        val uri = FileProvider.getUriForFile(
            context,
            context.packageName + AUTHORITY_SUFFIX,
            file,
        )

        val send = Intent(Intent.ACTION_SEND).apply {
            type = "image/png"
            putExtra(Intent.EXTRA_STREAM, uri)
            clipData = ClipData.newRawUri("GemaAR marker", uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        context.startActivity(Intent.createChooser(send, "Bagikan marker GemaAR"))
    }
}
