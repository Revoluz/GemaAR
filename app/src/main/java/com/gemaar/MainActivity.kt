package com.gemaar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.gemaar.data.Catalog
import com.gemaar.data.InstrumentStore
import com.gemaar.ui.CollectionScreen
import com.gemaar.ui.HomeScreen
import com.gemaar.ui.ar.ArScreen
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
    var screen by remember { mutableStateOf(Screen.Home) }

    when (screen) {
        Screen.Home -> HomeScreen(
            onStart = { screen = Screen.Ar },
            onOpenCollection = { screen = Screen.Collection },
        )
        Screen.Ar -> ArScreen(onBack = { screen = Screen.Home })
        Screen.Collection -> CollectionScreen(
            instruments = Catalog.instruments,
            playedIds = instrumentStore.playedInstrumentIds,
            onBack = { screen = Screen.Home },
        )
    }
}
