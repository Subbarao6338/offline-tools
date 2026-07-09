package omni.toolbox.ui.screens.science

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import omni.toolbox.ui.components.ToolScreen
import kotlin.math.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BallisticsScreen(navController: NavHostController) {
    var muzzleVelocity by remember { mutableStateOf("2700") }
    var bulletWeight by remember { mutableStateOf("150") }
    var ballisticCoefficient by remember { mutableStateOf("0.4") }
    var sightHeight by remember { mutableStateOf("1.5") }
    var zeroRange by remember { mutableStateOf("100") }

    ToolScreen(title = "Ballistics Calculator", onBack = { navController.popBackStack() }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Projectile Inputs", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)

            OutlinedTextField(
                value = muzzleVelocity,
                onValueChange = { muzzleVelocity = it },
                label = { Text("Muzzle Velocity (fps)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            OutlinedTextField(
                value = bulletWeight,
                onValueChange = { bulletWeight = it },
                label = { Text("Bullet Weight (grains)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            OutlinedTextField(
                value = ballisticCoefficient,
                onValueChange = { ballisticCoefficient = it },
                label = { Text("Ballistic Coefficient (G1)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            OutlinedTextField(
                value = sightHeight,
                onValueChange = { sightHeight = it },
                label = { Text("Sight Height (inches)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            OutlinedTextField(
                value = zeroRange,
                onValueChange = { zeroRange = it },
                label = { Text("Zero Range (yards)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Button(
                onClick = { /* Calculation logic would go here */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Calculate Trajectory")
            }

            // Results Placeholder
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.3f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Trajectory Summary", fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Calculated using G1 drag model.")
                    Text("Range: 0 - 500 yards")
                    Text("Drop at 200y: --")
                    Text("Velocity at 200y: --")
                }
            }
        }
    }
}
