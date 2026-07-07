package omni.toolbox.ui.screens.utility

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Shortcut
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import omni.toolbox.ui.components.ToolScreen

@Composable
fun TilesAndWidgetsScreen(navController: NavHostController) {
    ToolScreen(
        title = "Tiles & Widgets",
        onBack = { navController.popBackStack() }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Text(
                "Quick Settings Tiles",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                "Add these tools to your Android Quick Settings for instant access. Swipe down twice from the top of your screen, tap the edit (pencil) icon, and drag the Omni Toolbox tiles into your active area.",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            val tiles = listOf(
                "Flashlight", "Stopwatch", "Calculator", "Compass",
                "Battery Level", "Settings", "Emergency SOS", "Speedometer",
                "Metronome", "QR Scanner", "Quick Note", "Unit Converter",
                "Hub", "Media Grabber", "Altimeter", "Light Meter"
            )

            tiles.chunked(2).forEach { rowTiles ->
                Row(modifier = Modifier.fillMaxWidth()) {
                    rowTiles.forEach { tile ->
                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .padding(4.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                            )
                        ) {
                            Text(
                                tile,
                                modifier = Modifier.padding(12.dp),
                                style = MaterialTheme.typography.labelLarge
                            )
                        }
                    }
                    if (rowTiles.size == 1) Spacer(Modifier.weight(1f))
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                "Home Screen Widgets",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                "Long-press an empty spot on your home screen, select 'Widgets', find 'Omni Toolbox', and choose from the available options:",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            val widgets = listOf(
                "Digital Clock", "Analogue Clock", "Battery Monitor",
                "Stopwatch", "Calculator", "Quick Note", "Task List",
                "Compass", "Altimeter", "Light Meter", "Recent Tools",
                "BMI Calculator", "Pomodoro Timer", "Unit Converter"
            )

            widgets.forEach { widget ->
                ListItem(
                    headlineContent = { Text(widget) },
                    leadingContent = { Icon(Icons.Default.Widgets, contentDescription = null, tint = MaterialTheme.colorScheme.secondary) }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                "App Shortcuts",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                "You can pin any tool directly to your home screen. Long-press the Omni Toolbox app icon on your home screen to see quick actions, or use the 'Pin to Home' icon on individual tool screens.",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            var showShortcutDialog by remember { mutableStateOf(false) }
            val context = LocalContext.current

            OutlinedButton(
                onClick = { showShortcutDialog = true },
                modifier = Modifier.fillMaxWidth(),
                enabled = true
            ) {
                Icon(Icons.AutoMirrored.Filled.Shortcut, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Pin Tool Shortcut (System Feature)")
            }

            if (showShortcutDialog) {
                AlertDialog(
                    onDismissRequest = { showShortcutDialog = false },
                    title = { Text("Select Tool to Pin") },
                    text = {
                        Box(modifier = Modifier.heightIn(max = 400.dp)) {
                            androidx.compose.foundation.lazy.LazyColumn {
                                items(omni.toolbox.model.ToolProvider.tools.filter { it.isVisibleOnHome }) { tool ->
                                    ListItem(
                                        headlineContent = { Text(tool.name) },
                                        modifier = Modifier.clickable {
                                            omni.toolbox.utils.ShortcutUtils.pinShortcut(context, tool)
                                            showShortcutDialog = false
                                        },
                                        leadingContent = { Icon(tool.icon, contentDescription = null, tint = tool.color) }
                                    )
                                }
                            }
                        }
                    },
                    confirmButton = {
                        TextButton(onClick = { showShortcutDialog = false }) {
                            Text("Cancel")
                        }
                    }
                )
            }
        }
    }
}
