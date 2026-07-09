package omni.toolbox.ui.screens.sensor

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Architecture
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import omni.toolbox.ui.components.ToolScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClinometerScreen(navController: NavHostController) {
    ToolScreen(title = "Clinometer", onBack = { navController.popBackStack() }) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(Icons.Default.Architecture, null, modifier = Modifier.size(120.dp), tint = MaterialTheme.colorScheme.primary)

            Spacer(modifier = Modifier.height(32.dp))

            Text("Slope Angle", style = MaterialTheme.typography.titleMedium)

            Text(
                text = "0.0°",
                style = MaterialTheme.typography.displayLarge.copy(fontSize = 72.sp),
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(48.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Button(onClick = { /* Lock */ }) {
                    Text("LOCK ANGLE")
                }
                OutlinedButton(onClick = { /* Camera */ }) {
                    Text("USE CAMERA")
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Card {
                Text(
                    "Used to measure angles of slope, elevation, or depression of an object with respect to gravity's direction.",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }
        }
    }
}
