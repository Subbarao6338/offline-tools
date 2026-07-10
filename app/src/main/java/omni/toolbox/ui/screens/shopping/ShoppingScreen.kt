package omni.toolbox.ui.screens.shopping

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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import omni.toolbox.ui.components.ToolScreen

data class ShoppingItem(
    val id: String,
    val name: String,
    val quantity: Int,
    val price: Double,
    val isBought: Boolean = false
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingScreen(navController: NavHostController) {
    val items = remember {
        mutableStateListOf(
            ShoppingItem("1", "Organic Milk", 2, 3.49),
            ShoppingItem("2", "Whole Wheat Bread", 1, 2.99, isBought = true),
            ShoppingItem("3", "Fresh Bananas (Bunch)", 1, 1.89),
            ShoppingItem("4", "Free Range Eggs (Dozen)", 1, 4.29)
        )
    }

    var itemName by remember { mutableStateOf("") }
    var itemQty by remember { mutableStateOf("1") }
    var itemPrice by remember { mutableStateOf("") }
    var isAddDialogOpen by remember { mutableStateOf(false) }

    val totalCost = remember(items) {
        derivedStateOf {
            items.sumOf { it.price * it.quantity }
        }
    }

    val remainingCost = remember(items) {
        derivedStateOf {
            items.filter { !it.isBought }.sumOf { it.price * it.quantity }
        }
    }

    ToolScreen(
        title = "Shopping List Pro",
        onBack = { navController.popBackStack() },
        actions = {
            IconButton(onClick = {
                items.removeAll { it.isBought }
            }) {
                Icon(Icons.Default.ClearAll, contentDescription = "Clear bought items")
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                "Grand Total",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                            Text(
                                "$${String.format("%.2f", totalCost.value)}",
                                style = MaterialTheme.typography.headlineLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                "Remaining",
                                style = MaterialTheme.typography.titleSmall,
                                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                            )
                            Text(
                                "$${String.format("%.2f", remainingCost.value)}",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                            )
                        }
                    }
                }

                if (items.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                Icons.Default.ShoppingCart,
                                contentDescription = null,
                                modifier = Modifier.size(64.dp),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Your list is empty", color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(items, key = { it.id }) { item ->
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(
                                    containerColor = if (item.isBought) MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                                                    else MaterialTheme.colorScheme.surfaceVariant
                                )
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Checkbox(
                                        checked = item.isBought,
                                        onCheckedChange = { checked ->
                                            val index = items.indexOfFirst { it.id == item.id }
                                            if (index != -1) {
                                                items[index] = item.copy(isBought = checked)
                                            }
                                        }
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = item.name,
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.Bold,
                                            textDecoration = if (item.isBought) TextDecoration.LineThrough else null,
                                            color = if (item.isBought) MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                                                    else MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                        Text(
                                            text = "Qty: ${item.quantity}  •  $${String.format("%.2f", item.price)} each",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
                                        )
                                    }
                                    Text(
                                        text = "$${String.format("%.2f", item.price * item.quantity)}",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = if (item.isBought) MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                                                else MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    IconButton(onClick = { items.remove(item) }) {
                                        Icon(Icons.Default.Delete, contentDescription = "Delete", tint = MaterialTheme.colorScheme.error)
                                    }
                                }
                            }
                        }
                    }
                }
            }

            FloatingActionButton(
                onClick = { isAddDialogOpen = true },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Item")
            }
        }

        if (isAddDialogOpen) {
            AlertDialog(
                onDismissRequest = { isAddDialogOpen = false },
                title = { Text("Add Shopping Item") },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = itemName,
                            onValueChange = { itemName = it },
                            label = { Text("Item Name") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            OutlinedTextField(
                                value = itemQty,
                                onValueChange = { itemQty = it },
                                label = { Text("Qty") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                modifier = Modifier.weight(1f)
                            )
                            OutlinedTextField(
                                value = itemPrice,
                                onValueChange = { itemPrice = it },
                                label = { Text("Price ($)") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            if (itemName.isNotBlank()) {
                                val qty = itemQty.toIntOrNull() ?: 1
                                val price = itemPrice.toDoubleOrNull() ?: 0.0
                                items.add(ShoppingItem(System.currentTimeMillis().toString(), itemName, qty, price))
                                itemName = ""
                                itemQty = "1"
                                itemPrice = ""
                                isAddDialogOpen = false
                            }
                        }
                    ) {
                        Text("Add")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { isAddDialogOpen = false }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}
