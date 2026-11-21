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
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.gemaar.ui.HomeScreen
import com.gemaar.ui.ar.ArPlaceholderScreen
import com.gemaar.ui.theme.GemaARTheme

private enum class Screen {
    Home,
    Ar,
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, window.decorView).apply {
            hide(WindowInsetsCompat.Type.systemBars())
            systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }

        setContent {
            GemaARTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    GemaARApp()
                }
            }
        }
    }
}

@Composable
private fun GemaARApp() {
    var screen by remember { mutableStateOf(Screen.Home) }

    when (screen) {
        Screen.Home -> HomeScreen(onStart = { screen = Screen.Ar })
        Screen.Ar -> ArPlaceholderScreen(onBack = { screen = Screen.Home })
    }
}
