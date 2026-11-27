package com.gemaar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.gemaar.data.AudioSettingsStore
import com.gemaar.data.Catalog
import com.gemaar.data.InstrumentStore
import com.gemaar.data.MusicVolume
import com.gemaar.ui.CollectionScreen
import com.gemaar.ui.HomeScreen
import com.gemaar.ui.ar.ArScreen
import com.gemaar.ui.components.SettingsEnterTransition
import com.gemaar.ui.components.SettingsExitTransition
import com.gemaar.ui.components.SettingsPanel
import com.gemaar.ui.theme.GemaARTheme

private enum class Screen {
    Home,
    Ar,
    Collection,
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        hideSystemBars()

        setContent {
            GemaARTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    GemaARApp()
                }
            }
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) hideSystemBars()
    }

    private fun hideSystemBars() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, window.decorView).apply {
            hide(WindowInsetsCompat.Type.systemBars())
            systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }
}

@Composable
private fun GemaARApp() {
    val context = LocalContext.current
    val instrumentStore = remember(context) { InstrumentStore(context) }
    val audioSettingsStore = remember(context) { AudioSettingsStore(context) }
    var screen by remember { mutableStateOf(Screen.Home) }

    // F-07: satu sumber nilai volume di level aplikasi (plan: Navigasi & state).
    var settingsVisible by remember { mutableStateOf(false) }
    var volumePercent by remember {
        mutableIntStateOf(audioSettingsStore.loadVolume().percent)
    }
    var lastSavedPercent by remember { mutableIntStateOf(volumePercent) }
    var saveFailed by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        when (screen) {
            Screen.Home -> HomeScreen(
                onStart = { screen = Screen.Ar },
                onOpenCollection = { screen = Screen.Collection },
                onOpenSettings = {
                    saveFailed = false
                    settingsVisible = true
                },
            )
            Screen.Ar -> ArScreen(
                onBack = { screen = Screen.Home },
                onOpenSettings = {
                    saveFailed = false
                    settingsVisible = true
                },
                volumePercent = volumePercent,
            )
            Screen.Collection -> CollectionScreen(
                instruments = Catalog.instruments,
                playedIds = instrumentStore.playedInstrumentIds,
                onBack = { screen = Screen.Home },
            )
        }

        // Overlay panel di atas layar asal; tutup kembali ke layar yang sama.
        AnimatedVisibility(
            visible = settingsVisible,
            enter = SettingsEnterTransition,
            exit = SettingsExitTransition,
        ) {
            // Back menutup panel lebih dulu, bukan keluar dari AR.
            BackHandler { settingsVisible = false }
            SettingsPanel(
                percent = volumePercent,
                onPercentChange = { volumePercent = it },
                onPercentCommit = {
                    val volume = MusicVolume.of(volumePercent)
                    if (audioSettingsStore.saveVolume(volume)) {
                        lastSavedPercent = volume.percent
                        saveFailed = false
                    } else {
                        volumePercent = lastSavedPercent
                        saveFailed = true
                    }
                },
                onClose = { settingsVisible = false },
                message = if (saveFailed) stringResource(R.string.settings_save_failed) else null,
            )
        }
    }
}
