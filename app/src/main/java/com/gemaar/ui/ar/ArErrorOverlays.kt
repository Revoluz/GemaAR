package com.gemaar.ui.ar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gemaar.ui.theme.GemaARBody
import com.gemaar.ui.theme.GemaARColors
import com.google.ar.core.ArCoreApk
import io.github.sceneview.ar.ARCoreAvailability
import io.github.sceneview.ar.ARCoreAvailabilityState
import io.github.sceneview.ar.ARCameraPermissionState

/**
 * State S4 “Izin kamera ditolak” mengikuti
 * `docs/design/UI/exports/states-S1-S7.png`.
 */
@Composable
fun BoxScope.CameraPermissionOverlay(
    state: ARCameraPermissionState,
    onBack: () -> Unit,
) {
    ErrorScaffold(
        title = "Izin kamera ditolak",
        message = "Buka Pengaturan untuk mengizinkan kamera, lalu kembali ke aplikasi.",
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            if (!state.permanentlyDenied) {
                SecondaryButton(
                    label = "Coba Lagi",
                    onClick = { state.request() },
                )
            }
            PrimaryButton(
                label = "Buka Pengaturan",
                onClick = { state.openSettings() },
            )
        }
        Spacer(Modifier.height(4.dp))
        TextButton(onClick = onBack) {
            Text(
                text = "Kembali ke Beranda",
                color = GemaARColors.Muted,
                fontFamily = GemaARBody,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
            )
        }
    }
}

@Composable
fun ArCoreChecking() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(GemaARColors.Surface),
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator(color = GemaARColors.Primary)
            Spacer(Modifier.height(16.dp))
            Text(
                text = "Memeriksa AR…",
                color = GemaARColors.Fg,
                fontFamily = GemaARBody,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
            )
        }
    }
}

/**
 * Gate sebelum sesi AR dibuat. Membedakan perangkat tidak didukung,
 * ARCore belum terpasang, dan ARCore perlu diperbarui.
 */
@Composable
fun ArCoreGateOverlay(
    availability: ArCoreApk.Availability,
    onRetry: () -> Unit,
    onOpenStore: () -> Unit,
    onBack: () -> Unit,
) {
    val title: String
    val message: String
    when (availability) {
        ArCoreApk.Availability.UNSUPPORTED_DEVICE_NOT_CAPABLE -> {
            title = "ARCore tidak didukung"
            message = "Perangkat ini tidak mendukung ARCore, jadi alat musik belum " +
                "bisa ditampilkan di marker."
        }
        ArCoreApk.Availability.SUPPORTED_NOT_INSTALLED -> {
            title = "ARCore belum terpasang"
            message = "Pasang Google Play Services for AR (ARCore) untuk memakai " +
                "kamera AR dan menampilkan alat musik di marker."
        }
        ArCoreApk.Availability.SUPPORTED_APK_TOO_OLD -> {
            title = "ARCore perlu diperbarui"
            message = "Perbarui Google Play Services for AR (ARCore) untuk " +
                "memakai kamera AR."
        }
        else -> {
            title = "ARCore tidak tersedia"
            message = "ARCore belum terpasang atau ketersediaannya belum bisa " +
                "diperiksa. Pasang Google Play Services for AR, lalu coba lagi."
        }
    }

    ErrorScaffold(title = title, message = message) {
        when {
            availability == ArCoreApk.Availability.UNSUPPORTED_DEVICE_NOT_CAPABLE -> {
                PrimaryButton(label = "Kembali", onClick = onBack)
            }
            availability.isSupported -> {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    SecondaryButton(label = "Coba Lagi", onClick = onRetry)
                    PrimaryButton(
                        label = if (availability == ArCoreApk.Availability.SUPPORTED_APK_TOO_OLD) {
                            "Perbarui ARCore"
                        } else {
                            "Pasang ARCore"
                        },
                        onClick = onOpenStore,
                    )
                }
                Spacer(Modifier.height(4.dp))
                TextButton(onClick = onBack) {
                    Text(
                        text = "Kembali ke Beranda",
                        color = GemaARColors.Muted,
                        fontFamily = GemaARBody,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                    )
                }
            }
            else -> {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    SecondaryButton(label = "Coba Lagi", onClick = onRetry)
                    PrimaryButton(label = "Buka Play Store", onClick = onOpenStore)
                }
                Spacer(Modifier.height(4.dp))
                TextButton(onClick = onBack) {
                    Text(
                        text = "Kembali ke Beranda",
                        color = GemaARColors.Muted,
                        fontFamily = GemaARBody,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                    )
                }
            }
        }
    }
}

/** State S4 “ARCore tidak tersedia”. */
@Composable
fun BoxScope.ArCoreUnavailableOverlay(
    state: ARCoreAvailabilityState,
    onBack: () -> Unit,
) {
    val message = when (state.availability) {
        ARCoreAvailability.Unsupported ->
            "Perangkat ini tidak mendukung ARCore, jadi alat musik belum bisa " +
                "ditampilkan di marker."
        ARCoreAvailability.NotInstalled ->
            "Google Play Services for AR belum terpasang di perangkat ini."
        ARCoreAvailability.NeedsUpdate ->
            "Google Play Services for AR perlu diperbarui sebelum AR bisa dipakai."
        ARCoreAvailability.CheckFailed ->
            "Ketersediaan ARCore tidak bisa diperiksa. Periksa koneksi lalu coba lagi."
        ARCoreAvailability.SessionFailed ->
            "Sesi AR gagal dimulai. Tutup aplikasi lain yang memakai kamera lalu coba lagi."
    }

    ErrorScaffold(
        title = "ARCore tidak tersedia",
        message = message,
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            SecondaryButton(label = "Kembali", onClick = onBack)
            PrimaryButton(label = "Coba Lagi", onClick = { state.retry() })
        }
    }
}

@Composable
private fun ErrorScaffold(
    title: String,
    message: String,
    actions: @Composable () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.78f)),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier
                .widthIn(max = 460.dp)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                imageVector = Icons.Filled.Warning,
                contentDescription = null,
                tint = GemaARColors.Danger,
                modifier = Modifier.height(48.dp),
            )
            Spacer(Modifier.height(12.dp))
            Text(
                text = title,
                color = GemaARColors.Fg,
                fontFamily = GemaARBody,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                textAlign = TextAlign.Center,
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = message,
                color = GemaARColors.Muted,
                fontFamily = GemaARBody,
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp,
                lineHeight = 21.sp,
                textAlign = TextAlign.Center,
            )
            Spacer(Modifier.height(20.dp))
            actions()
        }
    }
}

@Composable
private fun PrimaryButton(label: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = GemaARColors.Primary,
            contentColor = GemaARColors.PrimaryFg,
        ),
    ) {
        Text(
            text = label,
            fontFamily = GemaARBody,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
        )
    }
}

@Composable
private fun SecondaryButton(label: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        border = androidx.compose.foundation.BorderStroke(2.dp, GemaARColors.Border),
        colors = ButtonDefaults.buttonColors(
            containerColor = GemaARColors.Wood,
            contentColor = GemaARColors.Fg,
        ),
    ) {
        Text(
            text = label,
            fontFamily = GemaARBody,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
        )
    }
}
