package omni.toolbox.ui.screens.outdoor

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TriangulationScreen(navController: NavHostController) {
    var lat1 by remember { mutableStateOf("") }
    var lon1 by remember { mutableStateOf("") }
    var bearing1 by remember { mutableStateOf("") }
    var lat2 by remember { mutableStateOf("") }
    var lon2 by remember { mutableStateOf("") }
    var bearing2 by remember { mutableStateOf("") }

    ToolScreen(title = "Triangulate Location", onBack = { navController.popBackStack() }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Point 1 (Reference)", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = lat1, onValueChange = { lat1 = it }, label = { Text("Lat") }, modifier = Modifier.weight(1f), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
                OutlinedTextField(value = lon1, onValueChange = { lon1 = it }, label = { Text("Lon") }, modifier = Modifier.weight(1f), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
            }
            OutlinedTextField(value = bearing1, onValueChange = { bearing1 = it }, label = { Text("Bearing to Target (°)") }, modifier = Modifier.fillMaxWidth(), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))

            Divider()

            Text("Point 2 (Reference)", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = lat2, onValueChange = { lat2 = it }, label = { Text("Lat") }, modifier = Modifier.weight(1f), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
                OutlinedTextField(value = lon2, onValueChange = { lon2 = it }, label = { Text("Lon") }, modifier = Modifier.weight(1f), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
            }
            OutlinedTextField(value = bearing2, onValueChange = { bearing2 = it }, label = { Text("Bearing to Target (°)") }, modifier = Modifier.fillMaxWidth(), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))

            Button(onClick = { /* Triangulation Logic */ }, modifier = Modifier.fillMaxWidth()) {
                Text("Calculate Target Location")
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.2f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Resulting Coordinate", fontWeight = FontWeight.Bold)
                    Text("Enter two points and their respective bearings to a distant object to find its location.")
                }
            }
        }
    }
}
