package omni.toolbox.ui.screens.outdoor

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import omni.toolbox.ui.components.ToolScreen
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LightningScreen(navController: NavHostController) {
    var flashTime by remember { mutableLongStateOf(0L) }
    var thunderTime by remember { mutableLongStateOf(0L) }
    var distanceKm by remember { mutableDoubleStateOf(0.0) }

    ToolScreen(title = "Lightning Distance", onBack = { navController.popBackStack() }) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            Icon(Icons.Default.FlashOn, null, modifier = Modifier.size(100.dp), tint = MaterialTheme.colorScheme.tertiary)

            Text("Strike Distance Calculator", style = MaterialTheme.typography.titleLarge)

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = String.format(Locale.getDefault(), "%.1f km", distanceKm),
                    style = MaterialTheme.typography.displayLarge,
                    fontWeight = FontWeight.Bold
                )
                Text("Estimated Distance", style = MaterialTheme.typography.bodyMedium)
            }

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Button(
                    onClick = { flashTime = System.currentTimeMillis() },
                    modifier = Modifier.weight(1f).height(100.dp)
                ) {
                    Text("SAW FLASH", textAlign = androidx.compose.ui.text.style.TextAlign.Center)
                }
                Button(
                    onClick = {
                        thunderTime = System.currentTimeMillis()
                        if (flashTime > 0) {
                            val seconds = (thunderTime - flashTime) / 1000.0
                            distanceKm = seconds * 0.343 // Speed of sound approx 343 m/s
                        }
                    },
                    modifier = Modifier.weight(1f).height(100.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                ) {
                    Text("HEARD THUNDER", textAlign = androidx.compose.ui.text.style.TextAlign.Center)
                }
            }

            TextButton(onClick = { distanceKm = 0.0; flashTime = 0L }) {
                Text("RESET")
            }

            Card {
                Text(
                    "Safety Tip: If the time between flash and thunder is less than 30 seconds, seek shelter immediately. Lightning is within 10 km.",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
