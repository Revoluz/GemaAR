package com.gemaar.ui.ar

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.gemaar.ar.ArController
import com.gemaar.ar.ArUiState
import com.gemaar.ar.NoteIndex
import com.gemaar.audio.NotePlayerState
import com.gemaar.audio.SoundPoolNotePlayer
import com.gemaar.data.Catalog
import com.gemaar.data.Instrument
import com.gemaar.data.InstrumentStore
import com.gemaar.data.ModelScale
import com.gemaar.data.MarkerSharer
import com.gemaar.data.Note
import com.gemaar.data.PetunjukStore
import com.gemaar.data.SaronNoteMap
import com.gemaar.ui.components.PlaqueTitle
import com.gemaar.ui.theme.GemaARBody
import com.gemaar.ui.theme.GemaARColors
import com.google.ar.core.ArCoreApk
import io.github.sceneview.ar.arcore.awaitAvailability
import kotlinx.coroutines.delay

/**
 * S4 AR — F-01.
 *
 * Referensi visual: `docs/design/UI/exports/S4-ar.png`.
 * F-01 hanya mengaktifkan Home dan “?”; Katalog/Info/Pengaturan tampil nonaktif.
 * Note bar dan pemilih tangga nada belum ditampilkan (F-02/F-04).
 */
@Composable
fun ArScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    val modelScale = ModelScale.DEFAULT_TARGET_METERS
    val petunjukStore = remember(context) { PetunjukStore(context) }
    val notePlayer = remember(context) { SoundPoolNotePlayer(context) }
    val notePlayerState by notePlayer.state.collectAsState()

    var activeNoteId by remember { mutableStateOf<Int?>(null) }
    var noteLabel by remember { mutableStateOf<String?>(null) }
    var noteFeedbackToken by remember { mutableIntStateOf(0) }

    var petunjukVisible by remember { mutableStateOf(!petunjukStore.hasSeenPetunjuk) }
    var arStarted by remember { mutableStateOf(petunjukStore.hasSeenPetunjuk) }
    var uiState by remember { mutableStateOf<ArUiState>(ArUiState.ModelLoading(0f)) }
    var cameraBlocked by remember { mutableStateOf(false) }
    var arCoreBlocked by remember { mutableStateOf(false) }
    var retryToken by remember { mutableIntStateOf(0) }
    var sessionKey by remember { mutableIntStateOf(0) }
    var showExitDialog by remember { mutableStateOf(false) }
    var arCoreAvailability by remember {
        mutableStateOf<ArCoreApk.Availability?>(null)
    }
    var arCoreCheckToken by remember { mutableIntStateOf(0) }

    // Simpan alat musik yang sedang dipilih (default: Saron)
    val instrumentStore = remember(context) { InstrumentStore(context) }
    var catalogVisible by remember { mutableStateOf(false) }
    var selectedInstrument by remember {
        mutableStateOf(Catalog.byId(instrumentStore.selectedInstrumentId))
    }

    fun playNote(note: Note) {
        if (!selectedInstrument.audioAvailable) return
        if (notePlayerState != NotePlayerState.Ready) return
        notePlayer.play(note)
        activeNoteId = note.id
        noteLabel = note.label
        noteFeedbackToken += 1
    }

    LaunchedEffect(noteFeedbackToken) {
        if (noteFeedbackToken > 0) {
            delay(220)
            activeNoteId = null
            delay(680)
            noteLabel = null
        }
    }

    LaunchedEffect(uiState) {
        if (uiState !is ArUiState.Placed) {
            activeNoteId = null
            noteLabel = null
        }
    }

    DisposableEffect(notePlayer) {
        onDispose { notePlayer.release() }
    }

    LaunchedEffect(arStarted, arCoreCheckToken) {
        if (!arStarted) return@LaunchedEffect
        arCoreAvailability = null
        arCoreAvailability = runCatching {
            ArCoreApk.getInstance().awaitAvailability(context)
        }.getOrElse { ArCoreApk.Availability.UNKNOWN_ERROR }
    }

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(
        lifecycleOwner,
        cameraBlocked,
        arCoreBlocked,
        arStarted,
        arCoreAvailability,
    ) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                val cameraGranted = ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.CAMERA,
                ) == PackageManager.PERMISSION_GRANTED
                if ((cameraBlocked && cameraGranted) || arCoreBlocked) {
                    cameraBlocked = false
                    arCoreBlocked = false
                    sessionKey += 1
                }
                if (arStarted &&
                    arCoreAvailability != null &&
                    arCoreAvailability != ArCoreApk.Availability.SUPPORTED_INSTALLED
                ) {
                    arCoreCheckToken += 1
                }
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }

    DisposableEffect(lifecycleOwner, notePlayer) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_PAUSE) notePlayer.stopAll()
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }

    fun dismissPetunjuk() {
        petunjukStore.markPetunjukSeen()
        petunjukVisible = false
        arStarted = true
    }

    LaunchedEffect(arStarted) {
        if (arStarted && !instrumentStore.catalogAutoOpened) {
            instrumentStore.catalogAutoOpened = true
            catalogVisible = true
        }
    }

    BackHandler {
        when {
            catalogVisible -> catalogVisible = false
            petunjukVisible -> dismissPetunjuk()
            arStarted -> showExitDialog = true
            else -> onBack()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(GemaARColors.Surface),
    ) {
        val arCoreReady = arCoreAvailability == ArCoreApk.Availability.SUPPORTED_INSTALLED

        when {
            !arStarted -> Unit
            arCoreAvailability == null -> ArCoreChecking()
            arCoreReady -> key(sessionKey) {
                ArController(
                    instrument = selectedInstrument,
                    modifier = Modifier.fillMaxSize(),
                    modelScale = modelScale,
                    retryToken = retryToken,
                    onUiState = { uiState = it },
                    onModelTap = { x ->
                        if (selectedInstrument.audioAvailable) {
                            playNote(
                                SaronNoteMap.noteAt(
                                    NoteIndex.forMarkerLocalX(x, modelScale),
                                ),
                            )
                        }
                    },
                    onCameraPermissionChanged = { cameraBlocked = it },
                    onArCoreAvailabilityChanged = { arCoreBlocked = it },
                    cameraPermissionOverlay = { state ->
                        CameraPermissionOverlay(state = state, onBack = onBack)
                    },
                    arCoreAvailabilityOverlay = { state ->
                        ArCoreUnavailableOverlay(state = state, onBack = onBack)
                    },
                )
            }
            else -> ArCoreGateOverlay(
                availability = arCoreAvailability
                    ?: ArCoreApk.Availability.UNKNOWN_ERROR,
                onRetry = { arCoreCheckToken += 1 },
                onOpenStore = { openArCoreStore(context) },
                onBack = onBack,
            )
        }

        if (arCoreReady && !cameraBlocked && !arCoreBlocked) {
            ArHud(
                uiState = uiState,
                chromeVisible = !petunjukVisible && !catalogVisible,
                notePlayerState = notePlayerState,
                selectedInstrument = selectedInstrument,
                activeNoteId = activeNoteId,
                noteLabel = noteLabel,
                onNoteClick = { playNote(it) },
                onHome = {
                    if (arStarted) showExitDialog = true else onBack()
                },
                onHelp = { petunjukVisible = true },
                onRetryModel = { retryToken += 1 },
                onCatalog = {
                    notePlayer.stopAll()
                    catalogVisible = true
                },
            )
        }

        if (petunjukVisible) {
            PetunjukOverlay(
                onClose = { dismissPetunjuk() },
                onDownloadMarker = { MarkerSharer.share(context) },
                onStartAr = { dismissPetunjuk() },
            )
        }
        if (catalogVisible) {
            CatalogOverlay(
                instruments = Catalog.instruments,
                selectedId = selectedInstrument.id,
                onSelect = { instrument ->
                    notePlayer.stopAll()
                    selectedInstrument = instrument
                    instrumentStore.selectedInstrumentId = instrument.id
                    catalogVisible = false
                },
                onClose = { catalogVisible = false },
            )
        }

        if (showExitDialog) {
            ExitArDialog(
                onDismiss = { showExitDialog = false },
                onConfirm = {
                    showExitDialog = false
                    onBack()
                },
            )
        }
    }
}

@Composable
private fun BoxScope.ArHud(
    uiState: ArUiState,
    chromeVisible: Boolean,
    notePlayerState: NotePlayerState,
    activeNoteId: Int?,
    selectedInstrument: Instrument,
    noteLabel: String?,
    onNoteClick: (Note) -> Unit,
    onHome: () -> Unit,
    onHelp: () -> Unit,
    onCatalog: () -> Unit,
    onRetryModel: () -> Unit,
) {
    val title = when (uiState) {
        is ArUiState.ModelLoading -> "Memuat model"
        else -> selectedInstrument.name
    }

    if (chromeVisible) {
        PlaqueTitle(
            text = title,
            fontSize = 30.sp,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 16.dp),
        )
    }

    Column(
        modifier = Modifier
            .align(Alignment.CenterStart)
            .padding(start = 20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        HudButton(
            icon = Icons.Filled.Home,
            contentDescription = "Kembali ke Beranda",
            enabled = true,
            onClick = onHome,
        )
        HudButton(
            icon = Icons.AutoMirrored.Filled.List,
            contentDescription = "Katalog",
            enabled = true,
            onClick =  onCatalog,
        )
        HudButton(
            icon = null,
            contentDescription = "Buka petunjuk",
            label = "?",
            enabled = true,
            onClick = onHelp,
        )
        HudButton(
            icon = Icons.Filled.Info,
            contentDescription = "Info (belum aktif)",
            enabled = false,
            onClick = {},
        )
    }

    HudButton(
        icon = Icons.Filled.Settings,
        contentDescription = "Pengaturan (belum aktif)",
        enabled = false,
        onClick = {},
        modifier = Modifier
            .align(Alignment.CenterEnd)
            .padding(end = 20.dp),
    )

    if (chromeVisible) {
        when (uiState) {
            is ArUiState.ModelLoading -> LoadingCard(
                progress = uiState.progress,
                modifier = Modifier.align(Alignment.Center),
            )
            is ArUiState.ModelError -> ModelErrorCard(
                message = uiState.message,
                onRetry = onRetryModel,
                modifier = Modifier.align(Alignment.Center),
            )
            ArUiState.Scanning, ArUiState.MarkerLost -> HintPill(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 24.dp),
            )
                ArUiState.Placed ->  {
                    if (selectedInstrument.audioAvailable) {
                        NoteBarSection(
                            playerState = notePlayerState,
                            activeNoteId = activeNoteId,
                            noteLabel = noteLabel,
                            onNoteClick = onNoteClick,
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(bottom = 20.dp),
                        )
                    } else {
                        Text(
                            text = "Audio belum tersedia untuk alat ini",
                            color = GemaARColors.Muted,
                            fontFamily = GemaARBody,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp,
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(bottom = 24.dp)
                                .clip(RoundedCornerShape(999.dp))
                                .background(GemaARColors.Surface.copy(alpha = 0.94f))
                                .border(1.dp, GemaARColors.Border, RoundedCornerShape(999.dp))
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                        )
                    }
                }
        }

        StatusPill(
            uiState = uiState,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 20.dp, bottom = 20.dp),
        )
    }
}

@Composable
private fun NoteBarSection(
    playerState: NotePlayerState,
    activeNoteId: Int?,
    noteLabel: String?,
    onNoteClick: (Note) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (playerState == NotePlayerState.Failed) {
            Text(
                text = "Audio tidak bisa dimuat",
                color = GemaARColors.Fg,
                fontFamily = GemaARBody,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                modifier = Modifier
                    .clip(RoundedCornerShape(999.dp))
                    .background(GemaARColors.Danger.copy(alpha = 0.92f))
                    .border(1.dp, GemaARColors.Border, RoundedCornerShape(999.dp))
                    .padding(horizontal = 12.dp, vertical = 6.dp),
            )
            Spacer(Modifier.height(8.dp))
        }
        if (noteLabel != null) {
            Text(
                text = noteLabel,
                color = GemaARColors.Primary,
                fontFamily = GemaARBody,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                modifier = Modifier
                    .clip(RoundedCornerShape(999.dp))
                    .background(GemaARColors.Surface.copy(alpha = 0.94f))
                    .border(1.dp, GemaARColors.Border, RoundedCornerShape(999.dp))
                    .padding(horizontal = 14.dp, vertical = 6.dp),
            )
            Spacer(Modifier.height(8.dp))
        }
        NoteBar(
            notes = SaronNoteMap.notes,
            activeNoteId = activeNoteId,
            enabled = playerState == NotePlayerState.Ready,
            onNoteClick = onNoteClick,
        )
    }
}

@Composable
private fun HudButton(
    icon: ImageVector?,
    contentDescription: String,
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
) {
    val shape = RoundedCornerShape(12.dp)
    val alpha = if (enabled) 1f else 0.45f
    Box(
        modifier = modifier
            .size(52.dp)
            .shadow(if (enabled) 4.dp else 0.dp, shape)
            .clip(shape)
            .background(
                if (enabled) GemaARColors.Wood else GemaARColors.Surface2,
                shape,
            )
            .border(2.dp, GemaARColors.Border.copy(alpha = alpha), shape)
            .clickable(enabled = enabled, onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                tint = GemaARColors.Fg.copy(alpha = alpha),
                modifier = Modifier.size(24.dp),
            )
        } else {
            Text(
                text = label.orEmpty(),
                color = GemaARColors.Fg.copy(alpha = alpha),
                fontFamily = GemaARBody,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
            )
        }
    }
}

@Composable
private fun StatusPill(uiState: ArUiState, modifier: Modifier = Modifier) {
    val (text, color) = when (uiState) {
        is ArUiState.ModelLoading -> "Menyiapkan model" to GemaARColors.Primary
        is ArUiState.ModelError -> "Model gagal dimuat" to GemaARColors.Danger
        ArUiState.Placed -> "Marker terdeteksi" to GemaARColors.Success
        ArUiState.MarkerLost -> "Marker hilang" to GemaARColors.Danger
        ArUiState.Scanning -> "Mencari marker" to GemaARColors.Primary
    }

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(999.dp))
            .background(GemaARColors.Surface.copy(alpha = 0.92f))
            .border(1.dp, GemaARColors.Border, RoundedCornerShape(999.dp))
            .padding(horizontal = 12.dp, vertical = 7.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .background(color, CircleShape),
        )
        Text(
            text = text,
            color = GemaARColors.Fg,
            fontFamily = GemaARBody,
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.sp,
        )
    }
}

@Composable
private fun HintPill(modifier: Modifier = Modifier) {
    val transition = rememberInfiniteTransition(label = "hint")
    val alpha by transition.animateFloat(
        initialValue = 0.6f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "hint-alpha",
    )

    Column(
        modifier = modifier
            .alpha(alpha)
            .clip(RoundedCornerShape(999.dp))
            .background(GemaARColors.Surface.copy(alpha = 0.94f))
            .border(2.dp, GemaARColors.Border, RoundedCornerShape(999.dp))
            .padding(horizontal = 20.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Arahkan kamera ke marker",
            color = GemaARColors.Fg,
            fontFamily = GemaARBody,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
        )
        Text(
            text = "Gerakkan perlahan sampai seluruh marker terlihat.",
            color = GemaARColors.Muted,
            fontFamily = GemaARBody,
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.sp,
        )
    }
}

@Composable
private fun LoadingCard(progress: Float, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .widthIn(max = 340.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(GemaARColors.Surface.copy(alpha = 0.94f))
            .border(2.dp, GemaARColors.Border, RoundedCornerShape(16.dp))
            .padding(horizontal = 24.dp, vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Memuat model",
            color = GemaARColors.Fg,
            fontFamily = GemaARBody,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
        )
        Spacer(Modifier.height(12.dp))
        LinearProgressIndicator(
            progress = { progress.coerceIn(0f, 1f) },
            color = GemaARColors.Primary,
            trackColor = GemaARColors.Wood,
            modifier = Modifier
                .width(260.dp)
                .height(8.dp)
                .clip(RoundedCornerShape(999.dp)),
        )
        Spacer(Modifier.height(10.dp))
        Text(
            text = "Menyiapkan Alat Musik…",
            color = GemaARColors.Muted,
            fontFamily = GemaARBody,
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.sp,
        )
    }
}

@Composable
private fun ModelErrorCard(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .widthIn(max = 380.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(GemaARColors.Surface.copy(alpha = 0.96f))
            .border(2.dp, GemaARColors.Border, RoundedCornerShape(16.dp))
            .padding(horizontal = 24.dp, vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Model gagal dimuat",
            color = GemaARColors.Fg,
            fontFamily = GemaARBody,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
        )
        Spacer(Modifier.height(6.dp))
        Text(
            text = message,
            color = GemaARColors.Muted,
            fontFamily = GemaARBody,
            fontWeight = FontWeight.SemiBold,
            fontSize = 13.sp,
            lineHeight = 18.sp,
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.height(14.dp))
        Button(
            onClick = onRetry,
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = GemaARColors.Primary,
                contentColor = GemaARColors.PrimaryFg,
            ),
        ) {
            Text(
                text = "Coba lagi",
                fontFamily = GemaARBody,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
            )
        }
    }
}

private fun openArCoreStore(context: Context) {
    val market = Intent(
        Intent.ACTION_VIEW,
        "market://details?id=com.google.ar.core".toUri(),
    )
    runCatching { context.startActivity(market) }.onFailure {
        if (it is ActivityNotFoundException) {
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    "https://play.google.com/store/apps/details?id=com.google.ar.core"
                        .toUri(),
                ),
            )
        }
    }
}

@Composable
private fun ExitArDialog(onDismiss: () -> Unit, onConfirm: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = GemaARColors.Surface2,
        titleContentColor = GemaARColors.Fg,
        textContentColor = GemaARColors.Muted,
        title = {
            Text(
                text = "Keluar dari AR?",
                fontFamily = GemaARBody,
                fontWeight = FontWeight.Bold,
            )
        },
        text = {
            Text(
                text = "Sesi AR akan berhenti dan kamu kembali ke Beranda.",
                fontFamily = GemaARBody,
            )
        },
        confirmButton = {
            Button(
                onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(
                    containerColor = GemaARColors.Primary,
                    contentColor = GemaARColors.PrimaryFg,
                ),
            ) {
                Text("Keluar", fontFamily = GemaARBody, fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    text = "Batal",
                    color = GemaARColors.Fg,
                    fontFamily = GemaARBody,
                    fontWeight = FontWeight.Bold,
                )
            }
        },
    )
}
