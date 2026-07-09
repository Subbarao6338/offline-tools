package omni.toolbox.ui.screens.audio

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import omni.toolbox.ui.components.ToolScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WhistleScreen(navController: NavHostController) {
    var isPlaying by remember { mutableStateOf(false) }

    ToolScreen(title = "Emergency Whistle", onBack = { navController.popBackStack() }) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(Icons.Default.Campaign, null, modifier = Modifier.size(120.dp), tint = if (isPlaying) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary)

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { isPlaying = !isPlaying },
                modifier = Modifier.fillMaxWidth().height(80.dp),
                colors = ButtonDefaults.buttonColors(containerColor = if (isPlaying) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary)
            ) {
                Text(if (isPlaying) "STOP WHISTLE" else "BLOW WHISTLE", style = MaterialTheme.typography.headlineSmall)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text("Whistle Signals:", fontWeight = FontWeight.Bold)
            Text("1 Blast: Where are you?")
            Text("2 Blasts: Come here.")
            Text("3 Blasts: SOS / Help Needed.")
        }
    }
}
