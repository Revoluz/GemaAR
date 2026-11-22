package com.gemaar.ar

import android.util.Log
import android.view.MotionEvent
import android.view.ViewConfiguration
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.gemaar.data.Instrument
import com.google.ar.core.AugmentedImage
import com.google.ar.core.AugmentedImageDatabase
import com.google.ar.core.Config
import io.github.sceneview.ModelLoadState
import io.github.sceneview.ar.ARCoreAvailabilityState
import io.github.sceneview.ar.ARCameraPermissionState
import io.github.sceneview.ar.ARSceneView
import io.github.sceneview.math.Position
import io.github.sceneview.rememberEngine
import io.github.sceneview.rememberModelLoadState
import io.github.sceneview.rememberModelLoader

private const val SARON_TARGET_METERS = 0.5f

/**
 * Pembungkus sesi AR. Semua akses SceneView/ARCore ada di paket `ar/`;
 * `ui/` hanya menerima [ArUiState] dan slot overlay.
 *
 * @param retryToken ubah nilainya untuk memuat ulang model setelah gagal.
 */
@Composable
fun ArController(
    instrument: Instrument,
    modifier: Modifier = Modifier,
    retryToken: Int = 0,
    onUiState: (ArUiState) -> Unit,
    onModelTap: (markerLocalX: Float) -> Unit = {},
    onCameraPermissionChanged: (Boolean) -> Unit = {},
    onArCoreAvailabilityChanged: (Boolean) -> Unit = {},
    cameraPermissionOverlay: (@Composable BoxScope.(ARCameraPermissionState) -> Unit)? = null,
    arCoreAvailabilityOverlay: (@Composable BoxScope.(ARCoreAvailabilityState) -> Unit)? = null,
) {
    val context = LocalContext.current
    val touchSlop = remember(context) {
        ViewConfiguration.get(context).scaledTouchSlop.toFloat()
    }
    val engine = rememberEngine()
    val modelLoader = rememberModelLoader(engine)
    val markerBitmap = remember(context) { MarkerRepository.loadBitmap(context) }

    var trackedImage by remember { mutableStateOf<AugmentedImage?>(null) }
    var hasEverTracked by remember { mutableStateOf(false) }

    val modelState = key(instrument.id, retryToken) {
        rememberModelLoadState(modelLoader, instrument.modelAsset)
    }

    val hasMarker = trackedImage != null
    LaunchedEffect(hasMarker) {
        if (hasMarker) hasEverTracked = true
    }

    val state: ArUiState = when {
        markerBitmap == null ->
            ArUiState.ModelError("Marker AR tidak bisa dimuat.")
        modelState is ModelLoadState.Failed ->
            ArUiState.ModelError(
                modelState.error.message ?: "Model ${instrument.name} gagal dimuat.",
            )
        modelState is ModelLoadState.Loading ->
            ArUiState.ModelLoading(modelState.progress.fraction)
        hasMarker -> ArUiState.Placed
        hasEverTracked -> ArUiState.MarkerLost
        else -> ArUiState.Scanning
    }

    val notifyState by rememberUpdatedState(onUiState)
    val notifyTap by rememberUpdatedState(onModelTap)
    val currentImage by rememberUpdatedState(trackedImage)
    LaunchedEffect(state) { notifyState(state) }

    var downX by remember { mutableFloatStateOf(0f) }
    var downY by remember { mutableFloatStateOf(0f) }
    var isTap by remember { mutableStateOf(false) }

    ARSceneView(
        modifier = modifier,
        engine = engine,
        modelLoader = modelLoader,
        planeFindingMode = Config.PlaneFindingMode.DISABLED,
        sessionConfiguration = { session, config ->
            config.augmentedImageDatabase = AugmentedImageDatabase(session).also { database ->
                markerBitmap?.let { bitmap ->
                    database.addImage(
                        MarkerRepository.IMAGE_NAME,
                        bitmap,
                        MarkerRepository.PHYSICAL_WIDTH_METERS,
                    )
                }
            }
            config.focusMode = Config.FocusMode.AUTO
        },
        onTouchEvent = { event, hitResult ->
            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    downX = event.x
                    downY = event.y
                    isTap = true
                    true
                }
                MotionEvent.ACTION_MOVE -> {
                    if (isTap) {
                        val dx = event.x - downX
                        val dy = event.y - downY
                        if (dx * dx + dy * dy > touchSlop * touchSlop) {
                            isTap = false
                        }
                    }
                    true
                }
                MotionEvent.ACTION_UP -> {
                    if (isTap && hitResult != null) {
                        currentImage?.centerPose?.let { markerPose ->
                            val world = hitResult.getWorldPosition()
                            val local = markerPose.inverse().transformPoint(
                                floatArrayOf(world.x, world.y, world.z),
                            )
                            notifyTap(local[0])
                        }
                    }
                    isTap = false
                    true
                }
                MotionEvent.ACTION_CANCEL -> {
                    isTap = false
                    true
                }
                else -> false
            }
        },
        onSessionUpdated = { session, _ ->
            val image = session
                .getAllTrackables(AugmentedImage::class.java)
                .firstOrNull { it.name == MarkerRepository.IMAGE_NAME }
            val visible = image != null &&
                isMarkerVisible(image.trackingState, image.trackingMethod)

            // Hanya tulis state saat status berubah, bukan tiap frame.
            if (visible && trackedImage == null) {
                Log.d("GemaArMarker", "visible=true")
                trackedImage = image
            } else if (!visible && trackedImage != null) {
                Log.d(
                    "GemaArMarker",
                    "visible=false state=${image?.trackingState} method=${image?.trackingMethod}",
                )
                trackedImage = null
            }
        },
        onCameraPermissionStateChanged = { permissionState ->
            onCameraPermissionChanged(permissionState != null)
        },
        onARCoreAvailability = { availability ->
            onArCoreAvailabilityChanged(availability != null)
        },
        cameraPermissionOverlay = cameraPermissionOverlay,
        arCoreAvailabilityOverlay = arCoreAvailabilityOverlay,
    ) {
        trackedImage?.let { image ->
            key(image.name) {
                AugmentedImageNode(augmentedImage = image) {
                    (modelState as? ModelLoadState.Ready)?.let { ready ->
                        ModelNode(
                            modelInstance = ready.instance,
                            scaleToUnits = SARON_TARGET_METERS,
                            centerOrigin = Position(0f, -1f, 0f),
                        )
                    }
                }
            }
        }
    }
}

