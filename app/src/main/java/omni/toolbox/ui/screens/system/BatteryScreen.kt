package omni.toolbox.ui.screens.system

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BatteryFull
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import omni.toolbox.ui.components.ToolScreen

@Composable
fun BatteryScreen(navController: NavHostController) {
    val context = LocalContext.current
    var batteryInfo by remember { mutableStateOf<Intent?>(null) }

    DisposableEffect(context) {
        val receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                batteryInfo = intent
            }
        }
        val filter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        val sticky = context.registerReceiver(receiver, filter)
        batteryInfo = sticky

        onDispose {
            context.unregisterReceiver(receiver)
        }
    }

    val level = batteryInfo?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: -1
    val scale = batteryInfo?.getIntExtra(BatteryManager.EXTRA_SCALE, -1) ?: -1
    val batteryPct = if (scale > 0) level * 100 / scale.toFloat() else 0f

    val status = batteryInfo?.getIntExtra(BatteryManager.EXTRA_STATUS, -1) ?: -1
    val isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                     status == BatteryManager.BATTERY_STATUS_FULL

    val chargePlug = batteryInfo?.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1) ?: -1
    val usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB
    val acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC

    val health = batteryInfo?.getIntExtra(BatteryManager.EXTRA_HEALTH, BatteryManager.BATTERY_HEALTH_UNKNOWN) ?: BatteryManager.BATTERY_HEALTH_UNKNOWN
    val temperature = (batteryInfo?.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0) ?: 0) / 10.0
    val voltage = batteryInfo?.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0) ?: 0
    val technology = batteryInfo?.getStringExtra(BatteryManager.EXTRA_TECHNOLOGY) ?: "Unknown"

    ToolScreen(title = "Battery Info", onBack = { navController.popBackStack() }, toolRoute = "battery") { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            Icon(
                Icons.Default.BatteryFull,
                contentDescription = null,
                modifier = Modifier.size(100.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text("${batteryPct.toInt()}%", style = MaterialTheme.typography.displayLarge)
            Text(if (isCharging) "Charging" else "Discharging", style = MaterialTheme.typography.headlineSmall)

            Spacer(modifier = Modifier.height(32.dp))

            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                BatteryInfoCard(
                    listOf(
                        "Status" to when(status) {
                            BatteryManager.BATTERY_STATUS_CHARGING -> "Charging"
                            BatteryManager.BATTERY_STATUS_DISCHARGING -> "Discharging"
                            BatteryManager.BATTERY_STATUS_FULL -> "Full"
                            BatteryManager.BATTERY_STATUS_NOT_CHARGING -> "Not Charging"
                            else -> "Unknown"
                        },
                        "Power Source" to if (usbCharge) "USB" else if (acCharge) "AC" else "Battery"
                    )
                )

                BatteryInfoCard(
                    listOf(
                        "Health" to when(health) {
                            BatteryManager.BATTERY_HEALTH_GOOD -> "Good"
                            BatteryManager.BATTERY_HEALTH_OVERHEAT -> "Overheat"
                            BatteryManager.BATTERY_HEALTH_DEAD -> "Dead"
                            BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE -> "Over Voltage"
                            BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE -> "Unspecified Failure"
                            BatteryManager.BATTERY_HEALTH_COLD -> "Cold"
                            else -> "Unknown"
                        },
                        "Technology" to technology
                    )
                )

                BatteryInfoCard(
                    listOf(
                        "Temperature" to "$temperature °C",
                        "Voltage" to "$voltage mV"
                    )
                )
            }
        }
    }
}

@Composable
fun BatteryInfoCard(infoItems: List<Pair<String, String>>) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            infoItems.forEachIndexed { index, pair ->
                BatteryInfoRow(pair.first, pair.second)
                if (index < infoItems.size - 1) {
                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                }
            }
        }
    }
}

@Composable
fun BatteryInfoRow(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, style = MaterialTheme.typography.bodyLarge)
        Text(value, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
    }
}
