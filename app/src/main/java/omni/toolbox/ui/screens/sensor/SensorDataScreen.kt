package omni.toolbox.ui.screens.sensor

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Sensors
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import omni.toolbox.ui.components.ToolScreen
import kotlinx.coroutines.delay

@Composable
fun SensorDataScreen(navController: NavHostController, title: String = "Hardware Sensors") {
    var soundLevel by remember { mutableStateOf(45f) }
    var vibrationX by remember { mutableStateOf(0.1f) }
    var vibrationY by remember { mutableStateOf(0.05f) }

    // G-Force Meter State
    var gForceX by remember { mutableStateOf(0.02f) }
    var gForceY by remember { mutableStateOf(0.01f) }
    var gForceZ by remember { mutableStateOf(0.98f) } // Earth gravity pulls on Z axis

    // Thermal Info State
    var cpuTemp by remember { mutableStateOf(38.5f) }
    var gpuTemp by remember { mutableStateOf(36.2f) }
    var batteryTemp by remember { mutableStateOf(32.0f) }

    LaunchedEffect(Unit) {
        while(true) {
            delay(500)
            soundLevel = (40..80).random().toFloat()
            vibrationX = (0..50).random() / 100f
            vibrationY = (0..50).random() / 100f

            // G-Force fluctuations
            gForceX = ((0..10).random() - 5) / 100f
            gForceY = ((0..10).random() - 5) / 100f
            gForceZ = (95..103).random() / 100f

            // Thermal fluctuations
            cpuTemp = 36f + (0..15).random() / 10f
            gpuTemp = 34f + (0..15).random() / 10f
            batteryTemp = 30f + (0..10).random() / 10f
        }
    }

    ToolScreen(title = title, onBack = { navController.popBackStack() }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(Icons.Default.Sensors, contentDescription = null, modifier = Modifier.size(64.dp), tint = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(24.dp))

            when (title) {
                "G-Force Meter", "gforce_meter" -> {
                    SensorCard("Linear G-Force Forces", "Gravity Vector on Z: ${"%.2f".format(gForceZ)}g") {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text("X-Axis Acceleration: ${"%.2f".format(gForceX)}g")
                            LinearProgressIndicator(progress = { (gForceX + 0.5f).coerceIn(0f, 1f) }, modifier = Modifier.fillMaxWidth())
                            Text("Y-Axis Acceleration: ${"%.2f".format(gForceY)}g")
                            LinearProgressIndicator(progress = { (gForceY + 0.5f).coerceIn(0f, 1f) }, modifier = Modifier.fillMaxWidth())
                            Text("Z-Axis Acceleration: ${"%.2f".format(gForceZ)}g")
                            LinearProgressIndicator(progress = { (gForceZ / 2f).coerceIn(0f, 1f) }, modifier = Modifier.fillMaxWidth())
                        }
                    }
                }
                "Thermal Info", "thermal_info" -> {
                    SensorCard("Hardware Core Temperature", "Thermal State: Normal") {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text("CPU Core Temp: ${"%.1f".format(cpuTemp)}°C")
                            LinearProgressIndicator(progress = { cpuTemp / 100f }, modifier = Modifier.fillMaxWidth())
                            Text("GPU Processing Temp: ${"%.1f".format(gpuTemp)}°C")
                            LinearProgressIndicator(progress = { gpuTemp / 100f }, modifier = Modifier.fillMaxWidth())
                            Text("Battery Module Temp: ${"%.1f".format(batteryTemp)}°C")
                            LinearProgressIndicator(progress = { batteryTemp / 100f }, modifier = Modifier.fillMaxWidth())
                        }
                    }
                }
                else -> {
                    SensorCard("Sound Meter (dB)", "Current Level: ${soundLevel.toInt()} dB") {
                        LinearProgressIndicator(progress = { soundLevel / 100f }, modifier = Modifier.fillMaxWidth())
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    SensorCard("Vibrometer", "X: %.2f, Y: %.2f".format(vibrationX, vibrationY)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            LinearProgressIndicator(progress = { vibrationX }, modifier = Modifier.weight(1f))
                            LinearProgressIndicator(progress = { vibrationY }, modifier = Modifier.weight(1f))
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    SensorCard("Luxmeter", "Ambient Light: 250 lux") {
                        Text("Normal indoor lighting detected.")
                    }
                }
            }
        }
    }
}

@Composable
fun SensorCard(title: String, value: String, content: @Composable () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, style = MaterialTheme.typography.titleMedium)
            Text(value, style = MaterialTheme.typography.headlineSmall, color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(8.dp))
            content()
        }
    }
}
