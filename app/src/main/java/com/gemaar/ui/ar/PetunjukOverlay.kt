package com.gemaar.ui.ar

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gemaar.R
import com.gemaar.ui.components.PlaqueTitle
import com.gemaar.ui.theme.GemaARBody
import com.gemaar.ui.theme.GemaARColors

/**
 * S3 Petunjuk — overlay di atas S4.
 *
 * Referensi visual: `docs/design/UI/exports/S3-petunjuk.png`.
 * Kartu marker memakai artwork asli `marker_placeholder.png` supaya pengguna
 * melihat persis gambar yang harus dicetak.
 */
@Composable
fun PetunjukOverlay(
    onClose: () -> Unit,
    onDownloadMarker: () -> Unit,
    onStartAr: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.62f))
            .pointerInput(Unit) { detectTapGestures { } },
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.94f)
                .fillMaxHeight(0.82f)
                .widthIn(max = 760.dp),
        ) {
            val panelShape = RoundedCornerShape(20.dp)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .shadow(16.dp, panelShape)
                    .background(GemaARColors.Surface, panelShape)
                    .border(2.dp, GemaARColors.Border, panelShape)
                    .padding(horizontal = 24.dp, vertical = 20.dp)
                    .padding(top = 28.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.Top,
                ) {
                    StepColumn(
                        caption = "Unduh marker",
                        download = true,
                        onDownloadMarker = onDownloadMarker,
                    ) {
                        MarkerCard()
                    }
                    StepArrow()
                    StepColumn(caption = "Arahkan kamera ke marker") {
                        MarkerCard(showDetectionFrame = true)
                    }
                    StepArrow()
                    StepColumn(caption = "Objek 3D akan tampil di atas marker") {
                        MarkerCard(showSaron = true)
                    }
                }

                Text(
                    text = "Satu marker yang sama untuk semua alat. Petunjuk tampil " +
                        "otomatis saat pertama kali — buka lagi lewat tombol ? di layar AR.",
                    color = GemaARColors.Muted,
                    fontFamily = GemaARBody,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp,
                    lineHeight = 16.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 24.dp),
                )

                Spacer(Modifier.height(12.dp))

                PrimaryButton(
                    label = "Mulai AR",
                    onClick = onStartAr,
                    modifier = Modifier.width(160.dp),
                )

            }

            PlaqueTitle(
                text = "Petunjuk",
                fontSize = 30.sp,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .offset(y = (-22).dp),
            )

            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(12.dp)
                    .size(44.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(GemaARColors.Wood)
                    .border(2.dp, GemaARColors.Border, RoundedCornerShape(8.dp))
                    .clickable(onClick = onClose),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "Tutup petunjuk",
                    tint = GemaARColors.Fg,
                    modifier = Modifier.size(24.dp),
                )
            }
        }
    }
}

@Composable
private fun StepColumn(
    caption: String,
    download: Boolean = false,
    onDownloadMarker: () -> Unit = {},
    card: @Composable () -> Unit,
) {
    Column(
        modifier = Modifier.width(184.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        card()
        Text(
            text = caption,
            color = GemaARColors.Fg,
            fontFamily = GemaARBody,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            lineHeight = 18.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(top = 8.dp)
                .height(38.dp),
        )
        if (download) {
            DownloadButton(onClick = onDownloadMarker)
        }
    }
}

@Composable
private fun StepArrow() {
    Icon(
        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
        contentDescription = null,
        tint = GemaARColors.Primary,
        modifier = Modifier
            .padding(horizontal = 6.dp)
            .size(28.dp),
    )
}

@Composable
private fun MarkerCard(
    showDetectionFrame: Boolean = false,
    showSaron: Boolean = false,
) {
    val shape = RoundedCornerShape(8.dp)
    Box(
        modifier = Modifier
            .width(184.dp)
            .height(142.dp)
            .clip(shape)
            .background(GemaARColors.Background)
            .border(2.dp, GemaARColors.Border, shape),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painterResource(R.drawable.marker_placeholder),
            contentDescription = "Marker AR GemaAR",
            contentScale = ContentScale.Fit,
            alpha = if (showSaron) 0.18f else 1f,
            modifier = Modifier
                .fillMaxSize()
                .padding(6.dp),
        )
        if (showSaron) {
            Image(
                painter = painterResource(R.drawable.alat_saron),
                contentDescription = "Ilustrasi Saron",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
            )
        }
        if (showDetectionFrame) {
            DetectionCorners(
                modifier = Modifier
                    .matchParentSize()
                    .padding(4.dp),
            )
        }
    }
}

@Composable
private fun DetectionCorners(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val length = 18.dp.toPx()
        val stroke = 3.dp.toPx()
        val width = size.width
        val height = size.height
        val color = GemaARColors.Primary

        fun corner(x: Float, y: Float, dx: Float, dy: Float) {
            drawLine(color, Offset(x, y), Offset(x + dx * length, y), stroke, StrokeCap.Square)
            drawLine(color, Offset(x, y), Offset(x, y + dy * length), stroke, StrokeCap.Square)
        }

        corner(0f, 0f, 1f, 1f)
        corner(width, 0f, -1f, 1f)
        corner(0f, height, 1f, -1f)
        corner(width, height, -1f, -1f)
    }
}

@Composable
private fun DownloadButton(onClick: () -> Unit) {
    val shape = RoundedCornerShape(8.dp)
    androidx.compose.material3.Button(
        onClick = onClick,
        shape = shape,
        colors = androidx.compose.material3.ButtonDefaults.buttonColors(
            containerColor = GemaARColors.Success,
            contentColor = GemaARColors.PrimaryFg,
        ),
        modifier = Modifier
            .padding(top = 6.dp)
            .width(120.dp)
            .height(42.dp),
    ) {
        Text(
            text = "Unduh",
            fontFamily = GemaARBody,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
        )
    }
}

@Composable
private fun PrimaryButton(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val shape = RoundedCornerShape(8.dp)
    androidx.compose.material3.Button(
        onClick = onClick,
        shape = shape,
        colors = androidx.compose.material3.ButtonDefaults.buttonColors(
            containerColor = GemaARColors.Primary,
            contentColor = GemaARColors.PrimaryFg,
        ),
        modifier = modifier.height(46.dp),
    ) {
        Text(
            text = label,
            fontFamily = GemaARBody,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
        )
    }
}
