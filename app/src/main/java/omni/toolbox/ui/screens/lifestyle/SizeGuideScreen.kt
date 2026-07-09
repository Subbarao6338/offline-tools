package omni.toolbox.ui.screens.lifestyle

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Straighten
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import omni.toolbox.model.SizeChart
import omni.toolbox.model.SizeGuideData
import omni.toolbox.ui.components.ToolScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SizeGuideScreen(navController: NavHostController, initialMainTab: Int = 0, initialSubTab: Int = 0) {
    var selectedMainTab by remember { mutableIntStateOf(initialMainTab) }
    val mainTabs = listOf("Women", "Men", "Kids", "Footwear", "Accessories", "Indian", "World", "Tribal", "Modern", "Global", "Innerwear")

    val currentCategories = when (selectedMainTab) {
        0 -> SizeGuideData.womenCategories
        1 -> SizeGuideData.menCategories
        2 -> SizeGuideData.kidsCategories
        3 -> SizeGuideData.footwearCategories
        4 -> SizeGuideData.accessoriesCategories
        5 -> SizeGuideData.indianCategories
        6 -> SizeGuideData.worldCategories
        7 -> SizeGuideData.tribalCategories
        8 -> SizeGuideData.modernCategories
        9 -> SizeGuideData.globalConversion
        else -> SizeGuideData.innerwearCategories
    }

    var selectedSubCategoryIndex by remember(selectedMainTab) {
        mutableIntStateOf(if (selectedMainTab == initialMainTab) initialSubTab else 0)
    }

    ToolScreen(title = "Fashion & Size Hub", onBack = { navController.popBackStack() }) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            ScrollableTabRow(
                selectedTabIndex = selectedMainTab,
                edgePadding = 16.dp,
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.primary
            ) {
                mainTabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedMainTab == index,
                        onClick = { selectedMainTab = index },
                        text = { Text(title) }
                    )
                }
            }

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    "Explore ${mainTabs[selectedMainTab]}",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    currentCategories.forEachIndexed { index, chart ->
                        FilterChip(
                            selected = selectedSubCategoryIndex == index,
                            onClick = { selectedSubCategoryIndex = index },
                            label = { Text(chart.title) }
                        )
                    }
                }
            }

            LazyColumn(
                modifier = Modifier.weight(1f).fillMaxWidth(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                val currentChart = currentCategories.getOrNull(selectedSubCategoryIndex)
                if (currentChart != null) {
                    item {
                        SizeChartTable(currentChart)
                    }
                }

                if (selectedMainTab == 10 && selectedSubCategoryIndex == 0) {
                   item {
                       BraSizeCalculatorUI()
                   }
                }

                item {
                    MeasurementGuide()
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Card(
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                    ) {
                        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Info, null, tint = MaterialTheme.colorScheme.primary)
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                "Fashion is a global heritage. Use this guide to explore sizes and styles from around the world.",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SizeChartTable(chart: SizeChart) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(chart.title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(12.dp))

            Box(modifier = Modifier.horizontalScroll(rememberScrollState())) {
                Column {
                    // Table Header
                    Row(
                        modifier = Modifier.background(MaterialTheme.colorScheme.secondaryContainer).padding(8.dp)
                    ) {
                        chart.columns.forEach { col ->
                            Text(
                                text = col,
                                modifier = Modifier.widthIn(min = 100.dp, max = 250.dp).padding(end = 16.dp),
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        }
                    }

                    // Table Rows
                    chart.rows.forEachIndexed { index, row ->
                        val bgColor = if (index % 2 == 0) Color.Transparent else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                        Row(
                            modifier = Modifier.background(bgColor).padding(8.dp)
                        ) {
                            row.values.forEach { value ->
                                Text(
                                    text = value,
                                    modifier = Modifier.widthIn(min = 100.dp, max = 250.dp).padding(end = 16.dp),
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BraSizeCalculatorUI() {
    var underbust by remember { mutableStateOf("") }
    var bust by remember { mutableStateOf("") }
    var unit by remember { mutableIntStateOf(0) } // 0: inches, 1: cm

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Bra Size Calculator", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))

            SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
                SegmentedButton(selected = unit == 0, onClick = { unit = 0 }, shape = SegmentedButtonDefaults.itemShape(index = 0, count = 2)) { Text("Inches") }
                SegmentedButton(selected = unit == 1, onClick = { unit = 1 }, shape = SegmentedButtonDefaults.itemShape(index = 1, count = 2)) { Text("CM") }
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = underbust,
                onValueChange = { underbust = it },
                label = { Text("Underbust (Band)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = bust,
                onValueChange = { bust = it },
                label = { Text("Bust") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            val uValue = underbust.toDoubleOrNull() ?: 0.0
            val bValue = bust.toDoubleOrNull() ?: 0.0

            if (uValue > 0 && bValue > 0) {
                val uInches = if (unit == 1) uValue / 2.54 else uValue
                val bInches = if (unit == 1) bValue / 2.54 else bValue

                // Traditional band calculation
                val band = if (uInches.toInt() % 2 == 0) uInches.toInt() + 4 else uInches.toInt() + 5
                val diff = bInches - band

                val cup = when {
                    diff < 1 -> "AA"
                    diff < 2 -> "A"
                    diff < 3 -> "B"
                    diff < 4 -> "C"
                    diff < 5 -> "D"
                    diff < 6 -> "DD/E"
                    diff < 7 -> "DDD/F"
                    diff < 8 -> "G"
                    else -> "H+"
                }

                Spacer(modifier = Modifier.height(16.dp))
                Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)) {
                    Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Estimated US/UK Size")
                        Text("$band$cup", style = MaterialTheme.typography.displaySmall, fontWeight = FontWeight.Bold)
                        Text("EU Band: ${(uInches * 2.54 / 5).toInt() * 5}", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
    }
}

@Composable
fun MeasurementGuide() {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Straighten, null, tint = MaterialTheme.colorScheme.primary)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Measurement & Fit Guide", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(12.dp))

            MeasurementItem("Chest/Bust", "Measure around the fullest part of your chest, keeping the tape horizontal.")
            MeasurementItem("Waist", "Measure around the narrowest part (typically where your body bends side to side).")
            MeasurementItem("Hips", "Measure around the fullest part of your hips.")
            MeasurementItem("Foot Length", "Place your foot on a paper, mark the heel and longest toe. Measure the distance.")
            MeasurementItem("Head Circumference", "Measure around your head where a hat would rest (usually 1/2 inch above ears).")
            MeasurementItem("Saree Length", "Standard saree is 5.5 meters, while some regional styles can be up to 9 meters.")
        }
    }
}

@Composable
fun MeasurementItem(title: String, description: String) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(title, style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary)
        Text(description, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}
