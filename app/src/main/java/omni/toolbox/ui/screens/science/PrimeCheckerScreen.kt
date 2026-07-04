package omni.toolbox.ui.screens.science

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import omni.toolbox.ui.components.ToolScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

@Composable
fun PrimeCheckerScreen(navController: NavHostController) {
    var input by remember { mutableStateOf("") }
    var result by remember { mutableStateOf<String?>(null) }
    var isCalculating by remember { mutableStateOf(false) }

    suspend fun isPrime(n: Long): Boolean = withContext(Dispatchers.Default) {
        if (n <= 1) return@withContext false
        if (n <= 3) return@withContext true
        if (n % 2 == 0L || n % 3 == 0L) return@withContext false
        var i = 5L
        while (i * i <= n) {
            if (n % i == 0L || n % (i + 2) == 0L) return@withContext false
            i += 6
        }
        return@withContext true
    }

    LaunchedEffect(input) {
        if (input.isBlank()) {
            result = null
            isCalculating = false
            return@LaunchedEffect
        }

        delay(500) // Debounce

        val n = input.toLongOrNull()
        if (n == null) {
            result = "Invalid input (Too large? Max: ${Long.MAX_VALUE})"
            isCalculating = false
        } else {
            isCalculating = true
            result = "Calculating..."
            val prime = isPrime(n)
            result = if (prime) "$n is a prime number"
                     else "$n is not a prime number"
            isCalculating = false
        }
    }

    ToolScreen(title = "Prime Checker", onBack = { navController.popBackStack() }) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            OutlinedTextField(
                value = input,
                onValueChange = { input = it.filter { char -> char.isDigit() } },
                label = { Text("Enter a number") },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("e.g. 104729") }
            )

            result?.let {
                Spacer(modifier = Modifier.height(24.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isCalculating) MaterialTheme.colorScheme.surfaceVariant
                                         else MaterialTheme.colorScheme.secondaryContainer
                    )
                ) {
                    Text(
                        it,
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            }

            if (isCalculating) {
                Spacer(modifier = Modifier.height(16.dp))
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "Fast prime number verification for large integers using optimized trial division.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.outline
            )
        }
    }
}
