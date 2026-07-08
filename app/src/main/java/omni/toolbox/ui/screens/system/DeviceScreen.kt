package omni.toolbox.ui.screens.system

import android.os.Build
import android.os.SystemClock
import android.util.DisplayMetrics
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import omni.toolbox.ui.components.ToolScreen
import java.util.concurrent.TimeUnit

@Composable
fun DeviceScreen(navController: NavHostController) {
    val context = LocalContext.current
    val displayMetrics = context.resources.displayMetrics
    val windowManager = context.getSystemService(android.content.Context.WINDOW_SERVICE) as android.view.WindowManager
    val refreshRate = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        context.display?.refreshRate ?: 0f
    } else {
        @Suppress("DEPRECATION")
        windowManager.defaultDisplay.refreshRate
    }

    val uptimeMillis = SystemClock.elapsedRealtime()
    val uptime = remember(uptimeMillis) {
        val days = TimeUnit.MILLISECONDS.toDays(uptimeMillis)
        val hours = TimeUnit.MILLISECONDS.toHours(uptimeMillis) % 24
        val minutes = TimeUnit.MILLISECONDS.toMinutes(uptimeMillis) % 60
        val seconds = TimeUnit.MILLISECONDS.toSeconds(uptimeMillis) % 60
        buildString {
            if (days > 0) append("${days}d ")
            if (hours > 0) append("${hours}h ")
            if (minutes > 0) append("${minutes}m ")
            append("${seconds}s")
        }
    }

    val deviceInfo = listOf(
        "Model" to Build.MODEL,
        "Manufacturer" to Build.MANUFACTURER,
        "Device" to Build.DEVICE,
        "Board" to Build.BOARD,
        "Hardware" to Build.HARDWARE,
        "CPU Architecture" to (System.getProperty("os.arch") ?: "Unknown"),
        "Kernel Version" to (System.getProperty("os.version") ?: "Unknown"),
        "Brand" to Build.BRAND,
        "Android Version" to Build.VERSION.RELEASE,
        "SDK Level" to Build.VERSION.SDK_INT.toString(),
        "Build ID" to Build.ID,
        "Resolution" to "${displayMetrics.widthPixels} x ${displayMetrics.heightPixels}",
        "Refresh Rate" to "${refreshRate.toInt()} Hz",
        "Screen Density" to "${displayMetrics.densityDpi} DPI",
        "System Uptime" to uptime
    )

    ToolScreen(title = "Device Info", onBack = { navController.popBackStack() }, toolRoute = "device") { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(deviceInfo) { (label, value) ->
                OutlinedCard(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(label, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.primary)
                        Text(value, style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }
        }
    }
}
