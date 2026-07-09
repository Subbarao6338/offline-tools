package omni.toolbox.ui.screens.audio

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NightsStay
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import omni.toolbox.ui.components.ToolScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WhiteNoiseScreen(navController: NavHostController) {
    var selectedNoise by remember { mutableStateOf("White") }
    var isPlaying by remember { mutableStateOf(false) }

    ToolScreen(title = "White Noise", onBack = { navController.popBackStack() }) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(Icons.Default.NightsStay, null, modifier = Modifier.size(80.dp), tint = MaterialTheme.colorScheme.primary)

            Text("Sleep & Focus Sounds", style = MaterialTheme.typography.titleMedium)

            listOf("White", "Pink", "Brown", "Rain", "Waves").forEach { noise ->
                FilterChip(
                    selected = selectedNoise == noise,
                    onClick = { selectedNoise = noise },
                    label = { Text(noise) },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { isPlaying = !isPlaying },
                modifier = Modifier.fillMaxWidth().height(64.dp)
            ) {
                Text(if (isPlaying) "PAUSE" else "PLAY $selectedNoise NOISE")
            }
        }
    }
}
