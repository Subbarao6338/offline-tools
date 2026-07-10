package omni.toolbox.ui.screens.food

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import omni.toolbox.ui.components.ToolScreen

data class Ingredient(
    val id: String,
    val amount: Double,
    val unit: String,
    val name: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodDrinkScreen(navController: NavHostController) {
    var selectedTab by remember { mutableStateOf(0) }

    ToolScreen(
        title = "Kitchen & Recipe Companion",
        onBack = { navController.popBackStack() }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            TabRow(selectedTabIndex = selectedTab) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    text = { Text("Recipe Scaler") }
                )
                Tab(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    text = { Text("Kitchen Unit Converter") }
                )
            }

            if (selectedTab == 0) {
                RecipeScalerTab()
            } else {
                KitchenConverterTab()
            }
        }
    }
}

@Composable
fun RecipeScalerTab() {
    var multiplier by remember { mutableStateOf(2.0) }
    var ingredientName by remember { mutableStateOf("") }
    var ingredientAmount by remember { mutableStateOf("") }
    var ingredientUnit by remember { mutableStateOf("g") }

    val ingredients = remember {
        mutableStateListOf(
            Ingredient("1", 250.0, "g", "All-Purpose Flour"),
            Ingredient("2", 2.0, "pcs", "Large Eggs"),
            Ingredient("3", 120.0, "ml", "Whole Milk"),
            Ingredient("4", 50.0, "g", "Unsalted Butter"),
            Ingredient("5", 2.0, "tsp", "Baking Powder")
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Recipe Scale Multiplier",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Slider(
                        value = multiplier.toFloat(),
                        onValueChange = { multiplier = Math.round(it * 2) / 2.0 }, // Snap to nearest 0.5
                        valueRange = 0.5f..5f,
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = "x${multiplier}",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Ingredients List", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(ingredients, key = { it.id }) { ing ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(ing.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            Text(
                                "Original: ${ing.amount} ${ing.unit}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
                            )
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "${String.format("%.1f", ing.amount * multiplier)} ${ing.unit}",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            IconButton(onClick = { ingredients.remove(ing) }) {
                                Icon(Icons.Default.Delete, contentDescription = "Delete", tint = MaterialTheme.colorScheme.error)
                            }
                        }
                    }
                }
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text("Add New Ingredient", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleSmall)
                Spacer(modifier = Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = ingredientName,
                        onValueChange = { ingredientName = it },
                        placeholder = { Text("Flour, Milk...") },
                        label = { Text("Ingredient") },
                        modifier = Modifier.weight(2f)
                    )
                    OutlinedTextField(
                        value = ingredientAmount,
                        onValueChange = { ingredientAmount = it },
                        placeholder = { Text("100") },
                        label = { Text("Amt") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = ingredientUnit,
                        onValueChange = { ingredientUnit = it },
                        placeholder = { Text("g") },
                        label = { Text("Unit") },
                        modifier = Modifier.weight(1f)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        val amt = ingredientAmount.toDoubleOrNull()
                        if (ingredientName.isNotBlank() && amt != null) {
                            ingredients.add(
                                Ingredient(
                                    System.currentTimeMillis().toString(),
                                    amt,
                                    ingredientUnit,
                                    ingredientName
                                )
                            )
                            ingredientName = ""
                            ingredientAmount = ""
                            ingredientUnit = "g"
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.Add, contentDescription = null)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Add Ingredient")
                }
            }
        }
    }
}

@Composable
fun KitchenConverterTab() {
    var inputValue by remember { mutableStateOf("") }
    var selectedFromUnit by remember { mutableStateOf("Cups") }
    var selectedToUnit by remember { mutableStateOf("Milliliters") }

    val units = listOf("Cups", "Milliliters", "Tablespoons", "Teaspoons", "Fluid Ounces")

    // Conversion factor to Milliliters
    val factorToMl = mapOf(
        "Cups" to 236.588,
        "Milliliters" to 1.0,
        "Tablespoons" to 14.7868,
        "Teaspoons" to 4.92892,
        "Fluid Ounces" to 29.5735
    )

    val convertedValue = remember(inputValue, selectedFromUnit, selectedToUnit) {
        derivedStateOf {
            val originalVal = inputValue.toDoubleOrNull() ?: 0.0
            val ml = originalVal * (factorToMl[selectedFromUnit] ?: 1.0)
            ml / (factorToMl[selectedToUnit] ?: 1.0)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = inputValue,
            onValueChange = { inputValue = it },
            label = { Text("Enter Quantity") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text("From Unit", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                UnitDropdown(selectedFromUnit, units) { selectedFromUnit = it }
            }
            Column(modifier = Modifier.weight(1f)) {
                Text("To Unit", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                UnitDropdown(selectedToUnit, units) { selectedToUnit = it }
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Result",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onTertiaryContainer
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "${String.format("%.3f", convertedValue.value)} $selectedToUnit",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onTertiaryContainer
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnitDropdown(selectedUnit: String, units: List<String>, onUnitSelect: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = selectedUnit,
            onValueChange = {},
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.menuAnchor()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            units.forEach { unit ->
                DropdownMenuItem(
                    text = { Text(unit) },
                    onClick = {
                        onUnitSelect(unit)
                        expanded = false
                    }
                )
            }
        }
    }
}
