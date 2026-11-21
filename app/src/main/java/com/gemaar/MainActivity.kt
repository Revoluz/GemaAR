package com.gemaar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gemaar.ui.theme.GemaARTheme
import io.github.sceneview.SceneView
import io.github.sceneview.SurfaceType
import io.github.sceneview.rememberEngine
import io.github.sceneview.rememberEnvironment
import io.github.sceneview.rememberEnvironmentLoader
import io.github.sceneview.rememberModelInstance
import io.github.sceneview.rememberModelLoader

private data class InstrumentModel(
    val label: String,
    val asset: String,
)

private val models = listOf(
    InstrumentModel("Gong & Kempul", "models/gong_kempul.glb"),
    InstrumentModel("Bonang", "models/bonang.glb"),
    InstrumentModel("Saron", "models/saron.glb"),
    InstrumentModel("Gender Barung", "models/gender_barung.glb"),
    InstrumentModel("Slenthem", "models/slenthem.glb"),
    InstrumentModel("Kendang", "models/kendang.glb"),
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GemaARTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    ModelPreviewScreen()
                }
            }
        }
    }
}

@Composable
private fun ModelPreviewScreen() {
    var selected by remember { mutableStateOf(models.first()) }
    val engine = rememberEngine()
    val modelLoader = rememberModelLoader(engine)
    val environmentLoader = rememberEnvironmentLoader(engine)
    val environment = rememberEnvironment(environmentLoader) {
        environmentLoader.createEnvironment()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        SceneView(
            modifier = Modifier.fillMaxSize(),
            surfaceType = SurfaceType.TextureSurface,
            engine = engine,
            modelLoader = modelLoader,
            environment = environment,
        ) {
            rememberModelInstance(modelLoader, selected.asset)?.let { instance ->
                ModelNode(
                    modelInstance = instance,
                    scaleToUnits = 0.5f,
                )
            }
        }

        Column(
            modifier = Modifier.padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = "GemaAR — scaffold",
                style = MaterialTheme.typography.titleLarge,
            )
            Text(
                text = selected.label,
                style = MaterialTheme.typography.bodyMedium,
            )
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(models) { model ->
                    Button(onClick = { selected = model }) {
                        Text(model.label)
                    }
                }
            }
        }
    }
}
