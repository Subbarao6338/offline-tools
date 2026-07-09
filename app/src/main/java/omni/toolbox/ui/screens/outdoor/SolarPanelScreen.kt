package omni.toolbox.ui.screens.outdoor

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import omni.toolbox.ui.components.ToolScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SolarPanelScreen(navController: NavHostController) {
    ToolScreen(title = "Solar Panel Aligner", onBack = { navController.popBackStack() }) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(Icons.Default.WbSunny, null, modifier = Modifier.size(80.dp), tint = MaterialTheme.colorScheme.primary)

            Text("Maximize Solar Efficiency", style = MaterialTheme.typography.titleMedium)

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Optimal Alignment:", fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                    Text("Tilt: --°")
                    Text("Azimuth: --°")
                }
            }

            Text(
                "Place your phone flat on the solar panel to measure current orientation and compare with optimal settings based on your location and time.",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(onClick = { /* Align */ }, modifier = Modifier.fillMaxWidth()) {
                Text("START MEASURING")
            }
        }
    }
}
