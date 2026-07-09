package omni.toolbox.ui.screens.outdoor

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import omni.toolbox.ui.components.ToolScreen
import kotlinx.coroutines.delay
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CliffHeightScreen(navController: NavHostController) {
    var isRunning by remember { mutableStateOf(false) }
    var startTime by remember { mutableLongStateOf(0L) }
    var elapsedTime by remember { mutableLongStateOf(0L) }

    LaunchedEffect(isRunning) {
        if (isRunning) {
            startTime = System.currentTimeMillis()
            while (isRunning) {
                elapsedTime = System.currentTimeMillis() - startTime
                delay(10)
            }
        }
    }

    ToolScreen(title = "Cliff Height", onBack = { navController.popBackStack() }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                "Measure by Falling Time",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.secondary
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = String.format(Locale.getDefault(), "%.2f s", elapsedTime / 1000f),
                style = MaterialTheme.typography.displayLarge.copy(fontSize = 64.sp),
                fontWeight = FontWeight.Bold
            )

            val height = 0.5 * 9.80665 * (elapsedTime / 1000.0) * (elapsedTime / 1000.0)
            Text(
                text = String.format(Locale.getDefault(), "Estimated Height: %.1f m", height),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(48.dp))

            Button(
                onClick = { isRunning = !isRunning },
                modifier = Modifier.size(200.dp),
                shape = MaterialTheme.shapes.extraLarge,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isRunning) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
                )
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.Timer, null, modifier = Modifier.size(48.dp))
                    Text(if (isRunning) "STOP" else "START", style = MaterialTheme.typography.headlineSmall)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = { elapsedTime = 0L; isRunning = false }) {
                Text("RESET")
            }

            Spacer(modifier = Modifier.height(32.dp))

            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
            ) {
                Text(
                    "How it works: Press START when you drop an object, and STOP when it hits the bottom. Uses d = 1/2 * g * t²",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
