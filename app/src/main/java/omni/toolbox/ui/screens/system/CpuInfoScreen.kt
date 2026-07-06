package omni.toolbox.ui.screens.system

import android.os.Build
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import omni.toolbox.ui.components.ToolScreen
import java.io.File
import java.io.FileFilter
import java.util.regex.Pattern

@Composable
fun CpuInfoScreen(navController: NavHostController) {
    val procCpuInfo = getProcCpuInfo()
    val cpuInfo = listOf(
        "Processor" to (procCpuInfo["model name"] ?: Build.HARDWARE),
        "BogoMIPS" to (procCpuInfo["bogomips"] ?: "N/A"),
        "Features" to (procCpuInfo["flags"] ?: "N/A"),
        "CPU MHz" to (procCpuInfo["cpu MHz"] ?: "N/A"),
        "Supported ABIs" to Build.SUPPORTED_ABIS.joinToString(", "),
        "Cores" to getNumberOfCores().toString(),
        "Model" to Build.MODEL,
        "Manufacturer" to Build.MANUFACTURER,
        "Board" to Build.BOARD,
        "Brand" to Build.BRAND
    )

    ToolScreen(title = "CPU Info", onBack = { navController.popBackStack() }) { padding ->
        LazyColumn(modifier = Modifier.padding(padding).fillMaxSize()) {
            items(cpuInfo) { (label, value) ->
                ListItem(
                    headlineContent = { Text(value) },
                    overlineContent = { Text(label) }
                )
                HorizontalDivider()
            }
        }
    }
}

private fun getProcCpuInfo(): Map<String, String> {
    val info = mutableMapOf<String, String>()
    try {
        File("/proc/cpuinfo").forEachLine { line ->
            val parts = line.split(":")
            if (parts.size == 2) {
                info[parts[0].trim()] = parts[1].trim()
            }
        }
    } catch (e: Exception) {
        // Handle exception
    }
    return info
}

private fun getNumberOfCores(): Int {
    return try {
        val dir = File("/sys/devices/system/cpu/")
        val files = dir.listFiles(FileFilter { Pattern.matches("cpu[0-9]+", it.name) })
        files?.size ?: 1
    } catch (e: Exception) {
        1
    }
}
