package omni.toolbox.ui.screens.conversion

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import omni.toolbox.ui.components.ToolScreen
import org.json.JSONObject
import java.util.concurrent.TimeUnit

@Composable
fun CurrencyConverterScreen(navController: NavHostController) {
    val context = LocalContext.current
    val prefs = remember { context.getSharedPreferences("currency_rates", Context.MODE_PRIVATE) }

    var amount by remember { mutableStateOf("") }
    var fromCurrency by remember { mutableStateOf("USD") }
    var toCurrency by remember { mutableStateOf("EUR") }
    var isRefreshing by remember { mutableStateOf(false) }

    // Initial rates fallback
    val initialRates = mapOf(
        "USD" to 1.0, "EUR" to 0.92, "GBP" to 0.79, "JPY" to 151.0,
        "AUD" to 1.52, "CAD" to 1.35, "CHF" to 0.90, "CNY" to 7.23,
        "INR" to 83.3, "BRL" to 5.05, "AED" to 3.67, "AFN" to 71.2,
        "ALL" to 94.5, "AMD" to 395.0, "ANG" to 1.79, "AOA" to 833.0
    )

    var rates by remember {
        mutableStateOf(
            prefs.getString("latest", null)?.let { json ->
                try {
                    val obj = JSONObject(json)
                    val map = mutableMapOf<String, Double>()
                    obj.keys().forEach { map[it] = obj.getDouble(it) }
                    map
                } catch(e: Exception) { initialRates }
            } ?: initialRates
        )
    }

    val currencies = remember(rates) { rates.keys.toList().sorted() }

    val client = remember {
        OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .build()
    }

    suspend fun refreshRates() {
        isRefreshing = true
        withContext(Dispatchers.IO) {
            try {
                // Using a public free API (frankfurter.app is a good alternative without API key)
                val request = Request.Builder()
                    .url("https://api.exchangerate-api.com/v4/latest/USD")
                    .build()

                client.newCall(request).execute().use { response ->
                    if (response.isSuccessful) {
                        val body = response.body?.string()
                        if (body != null) {
                            val json = JSONObject(body)
                            val ratesObj = json.getJSONObject("rates")
                            val newRates = mutableMapOf<String, Double>()
                            ratesObj.keys().forEach { newRates[it] = ratesObj.getDouble(it) }

                            withContext(Dispatchers.Main) {
                                rates = newRates
                                prefs.edit().putString("latest", JSONObject(newRates as Map<*, *>).toString()).apply()
                                prefs.edit().putLong("last_update", System.currentTimeMillis()).apply()
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                withContext(Dispatchers.Main) { isRefreshing = false }
            }
        }
    }

    val result = try {
        val value = amount.toDoubleOrNull() ?: 0.0
        val base = value / (rates[fromCurrency] ?: 1.0)
        base * (rates[toCurrency] ?: 1.0)
    } catch (e: Exception) {
        0.0
    }

    LaunchedEffect(Unit) {
        val lastUpdate = prefs.getLong("last_update", 0)
        // Refresh if older than 24 hours
        if (System.currentTimeMillis() - lastUpdate > 24 * 60 * 60 * 1000) {
            refreshRates()
        }
    }

    ToolScreen(
        title = "Currency Converter",
        onBack = { navController.popBackStack() },
        actions = {
            if (isRefreshing) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp), strokeWidth = 2.dp)
            } else {
                val refreshScope = rememberCoroutineScope()
                IconButton(onClick = {
                    refreshScope.launch {
                        refreshRates()
                    }
                }) {
                    Icon(Icons.Default.Refresh, contentDescription = "Refresh")
                }
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            OutlinedTextField(
                value = amount,
                onValueChange = { amount = it.filter { c -> c.isDigit() || c == '.' } },
                label = { Text("Amount") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                CurrencyDropdown("From", fromCurrency, currencies, { fromCurrency = it }, Modifier.weight(1f))
                IconButton(onClick = {
                    val temp = fromCurrency
                    fromCurrency = toCurrency
                    toCurrency = temp
                }) {
                    Icon(Icons.Default.SwapHoriz, contentDescription = "Swap")
                }
                CurrencyDropdown("To", toCurrency, currencies, { toCurrency = it }, Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(32.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Column(modifier = Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Result", style = MaterialTheme.typography.labelLarge)
                    Text(
                        java.lang.String.format(java.util.Locale.US, "%.2f", result),
                        style = MaterialTheme.typography.displayMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(toCurrency, style = MaterialTheme.typography.titleMedium)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            val lastUpdateStr = remember(rates) {
                val last = prefs.getLong("last_update", 0)
                if (last == 0L) "never" else java.text.DateFormat.getDateTimeInstance().format(java.util.Date(last))
            }
            Text("Rates updated: $lastUpdateStr", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.outline)
            Text("Rates are cached for offline use.", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.outline)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyDropdown(label: String, selected: String, options: List<String>, onSelect: (String) -> Unit, modifier: Modifier) {
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = selected,
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.menuAnchor()
        )
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { option ->
                DropdownMenuItem(text = { Text(option) }, onClick = { onSelect(option); expanded = false })
            }
        }
    }
}
