package omni.toolbox.ui.screens.lifestyle

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Straighten
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.compose.ui.graphics.Path
import omni.toolbox.model.SizeChart
import omni.toolbox.model.SizeGuideData
import omni.toolbox.ui.components.ToolScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SizeGuideScreen(
    navController: NavHostController,
    initialMainTab: Int = 0,
    initialSubTab: Int = 0,
    showTabs: Boolean = true
) {
    var selectedMainTab by remember { mutableIntStateOf(initialMainTab) }
    val mainTabs = listOf("Women", "Men", "Kids", "Footwear", "Accessories", "Indian", "World", "Tribal", "Modern", "Global", "Innerwear", "Materials")

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
        10 -> SizeGuideData.innerwearCategories
        else -> SizeGuideData.materialCategories
    }

    var selectedSubCategoryIndex by remember(selectedMainTab) {
        mutableIntStateOf(if (selectedMainTab == initialMainTab) initialSubTab else 0)
    }

    val screenTitle = if (showTabs) "Fashion & Size Hub" else "${mainTabs[selectedMainTab]} Size Guide"

    ToolScreen(title = screenTitle, onBack = { navController.popBackStack() }) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            if (showTabs) {
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
                item {
                    LifestyleVisualIllustration(selectedMainTab, mainTabs[selectedMainTab])
                }

                val currentChart = currentCategories.getOrNull(selectedSubCategoryIndex)
                if (currentChart != null) {
                    item {
                        SizeChartTable(currentChart)
                    }
                }

                item {
                    InteractiveInlineCalculator(selectedMainTab, selectedSubCategoryIndex)
                }

                item {
                    MeasurementGuide()
                }

                item {
                    UncensoredMasterDirectoryUI()
                }

                item {
                    FashionCombinationGuide()
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InteractiveInlineCalculator(mainTab: Int, @Suppress("UNUSED_PARAMETER") subTab: Int) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.2f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            when (mainTab) {
                0, 1 -> { // Women, Men Clothing
                    var chestOrBust by remember { mutableStateOf("36") }
                    var waist by remember { mutableStateOf("30") }
                    var hips by remember { mutableStateOf("38") }
                    Text(
                        text = "${if (mainTab == 0) "Women" else "Men"} Clothing Size Calculator",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = chestOrBust,
                        onValueChange = { chestOrBust = it },
                        label = { Text(if (mainTab == 0) "Bust (inches)" else "Chest (inches)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = waist,
                        onValueChange = { waist = it },
                        label = { Text("Waist (inches)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = hips,
                        onValueChange = { hips = it },
                        label = { Text("Hips (inches)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    val chestVal = chestOrBust.toDoubleOrNull() ?: 36.0
                    val waistVal = waist.toDoubleOrNull() ?: 30.0
                    val hipsVal = hips.toDoubleOrNull() ?: 38.0
                    val estimatedSize = when {
                        chestVal < 34 || waistVal < 26 || hipsVal < 36 -> "S (US 4 / EU 36)"
                        chestVal < 37 || waistVal < 29 || hipsVal < 39 -> "M (US 6-8 / EU 38-40)"
                        chestVal < 40 || waistVal < 32 || hipsVal < 42 -> "L (US 10-12 / EU 42-44)"
                        else -> "XL (US 14-16 / EU 46)"
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)) {
                        Text(
                            text = "Recommended Size: $estimatedSize",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(16.dp),
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
                2 -> { // Kids
                    var age by remember { mutableStateOf("6") }
                    var height by remember { mutableStateOf("45") }
                    Text(
                        text = "Kids Clothing Size Calculator",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = age,
                        onValueChange = { age = it },
                        label = { Text("Age (years)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = height,
                        onValueChange = { height = it },
                        label = { Text("Height (inches)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    @Suppress("UNUSED_VARIABLE")
                    val ageVal = age.toDoubleOrNull() ?: 6.0
                    val heightVal = height.toDoubleOrNull() ?: 45.0
                    val estimatedSize = when {
                        heightVal < 35 -> "Toddler (2T/3T)"
                        heightVal < 42 -> "Kids XS (4-5)"
                        heightVal < 48 -> "Kids S (6-7)"
                        heightVal < 54 -> "Kids M (8-10)"
                        else -> "Kids L (12-14)"
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)) {
                        Text(
                            text = "Recommended Size: $estimatedSize",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(16.dp),
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
                3 -> { // Footwear
                    var footLen by remember { mutableStateOf("25") }
                    var unitByCm by remember { mutableStateOf(true) }
                    Text(
                        text = "Shoe Size Calculator",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = footLen,
                        onValueChange = { footLen = it },
                        label = { Text(if (unitByCm) "Foot Length (cm)" else "Foot Length (inches)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(checked = unitByCm, onCheckedChange = { unitByCm = it })
                        Text("Measure in Centimeters (cm)")
                    }
                    val lengthCm = if (unitByCm) (footLen.toDoubleOrNull() ?: 25.0) else (footLen.toDoubleOrNull() ?: 9.8) * 2.54
                    // Simple shoe sizing mapping formula
                    val usSize = ((lengthCm - 12.0) * 0.84).coerceIn(4.0, 15.0)
                    val euSize = (lengthCm * 1.5 + 2.0).coerceIn(34.0, 48.0)
                    Spacer(modifier = Modifier.height(12.dp))
                    Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)) {
                        Text(
                            text = "Estimated Size: US ${"%.1f".format(usSize)} / EU ${euSize.toInt()}",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(16.dp),
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
                4 -> { // Accessories
                    var headCircumference by remember { mutableStateOf("56") }
                    Text(
                        text = "Hat Size Calculator",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = headCircumference,
                        onValueChange = { headCircumference = it },
                        label = { Text("Head Circumference (cm)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    val circ = headCircumference.toDoubleOrNull() ?: 56.0
                    val sizeCode = when {
                        circ < 54.0 -> "XS"
                        circ < 56.0 -> "S"
                        circ < 58.0 -> "M"
                        circ < 60.0 -> "L"
                        else -> "XL"
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)) {
                        Text(
                            text = "Recommended Hat Size: $sizeCode ($circ cm)",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(16.dp),
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
                else -> { // Default/Fallback
                    var lengthYards by remember { mutableStateOf("5.5") }
                    Text(
                        text = "Materials & Yardage Estimator",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = lengthYards,
                        onValueChange = { lengthYards = it },
                        label = { Text("Estimated Fabric Length (yards)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    val length = lengthYards.toDoubleOrNull() ?: 5.5
                    Spacer(modifier = Modifier.height(12.dp))
                    Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)) {
                        Text(
                            text = "Estimated Meters: ${"%.2f".format(length * 0.9144)} m",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(16.dp),
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun LifestyleVisualIllustration(selectedMainTab: Int, title: String) {
    Card(
        modifier = Modifier.fillMaxWidth().height(160.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)),
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Canvas(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                val width = size.width
                val height = size.height
                val centerX = width / 2f
                val centerY = height / 2f

                when (selectedMainTab) {
                    0 -> { // Women
                        val path = Path().apply {
                            moveTo(centerX - 20f, centerY - 50f)
                            lineTo(centerX + 20f, centerY - 50f)
                            lineTo(centerX + 25f, centerY - 20f)
                            lineTo(centerX + 60f, centerY + 50f)
                            lineTo(centerX - 60f, centerY + 50f)
                            lineTo(centerX - 25f, centerY - 20f)
                            close()
                        }
                        drawPath(path, color = Color(0xFFE91E63).copy(alpha = 0.2f))
                        drawPath(path, color = Color(0xFFE91E63), style = Stroke(width = 3f))
                        drawArc(color = Color(0xFFE91E63), startAngle = 0f, sweepAngle = 180f, useCenter = false, size = Size(40f, 20f), topLeft = Offset(centerX - 20f, centerY - 60f), style = Stroke(width = 3f))
                    }
                    1 -> { // Men
                        val path = Path().apply {
                            moveTo(centerX - 40f, centerY - 50f)
                            lineTo(centerX + 40f, centerY - 50f)
                            lineTo(centerX + 40f, centerY + 50f)
                            lineTo(centerX - 40f, centerY + 50f)
                            close()
                        }
                        drawPath(path, color = Color(0xFF2196F3).copy(alpha = 0.2f))
                        drawPath(path, color = Color(0xFF2196F3), style = Stroke(width = 3f))
                        val collar = Path().apply {
                            moveTo(centerX - 25f, centerY - 50f)
                            lineTo(centerX, centerY - 20f)
                            lineTo(centerX + 25f, centerY - 50f)
                        }
                        drawPath(collar, color = Color(0xFF2196F3), style = Stroke(width = 3f))
                        drawLine(color = Color(0xFF2196F3), start = Offset(centerX, centerY - 20f), end = Offset(centerX, centerY + 50f), strokeWidth = 2f)
                        drawCircle(color = Color(0xFF2196F3), radius = 3f, center = Offset(centerX, centerY))
                        drawCircle(color = Color(0xFF2196F3), radius = 3f, center = Offset(centerX, centerY + 20f))
                    }
                    2 -> { // Kids
                        val path = Path().apply {
                            moveTo(centerX - 30f, centerY - 30f)
                            lineTo(centerX + 30f, centerY - 30f)
                            lineTo(centerX + 30f, centerY + 20f)
                            lineTo(centerX + 15f, centerY + 20f)
                            lineTo(centerX + 15f, centerY + 45f)
                            lineTo(centerX - 15f, centerY + 45f)
                            lineTo(centerX - 15f, centerY + 20f)
                            lineTo(centerX - 30f, centerY + 20f)
                            close()
                        }
                        drawPath(path, color = Color(0xFF8BC34A).copy(alpha = 0.2f))
                        drawPath(path, color = Color(0xFF8BC34A), style = Stroke(width = 3f))
                        drawLine(color = Color(0xFF8BC34A), start = Offset(centerX - 20f, centerY - 30f), end = Offset(centerX - 20f, centerY - 50f), strokeWidth = 5f)
                        drawLine(color = Color(0xFF8BC34A), start = Offset(centerX + 20f, centerY - 30f), end = Offset(centerX + 20f, centerY - 50f), strokeWidth = 5f)
                    }
                    3 -> { // Footwear
                        val path = Path().apply {
                            moveTo(centerX - 50f, centerY - 20f)
                            lineTo(centerX - 20f, centerY - 20f)
                            lineTo(centerX - 10f, centerY + 10f)
                            lineTo(centerX + 50f, centerY + 10f)
                            lineTo(centerX + 55f, centerY + 30f)
                            lineTo(centerX - 55f, centerY + 30f)
                            close()
                        }
                        drawPath(path, color = Color(0xFFFF5722).copy(alpha = 0.2f))
                        drawPath(path, color = Color(0xFFFF5722), style = Stroke(width = 3f))
                        drawLine(color = Color(0xFFFF5722), start = Offset(centerX - 55f, centerY + 30f), end = Offset(centerX + 55f, centerY + 30f), strokeWidth = 4f)
                    }
                    4 -> { // Accessories
                        drawCircle(color = Color(0xFFFFD700), radius = 25f, center = Offset(centerX, centerY + 15f), style = Stroke(width = 4f))
                        val path = Path().apply {
                            moveTo(centerX, centerY - 25f)
                            lineTo(centerX + 15f, centerY - 10f)
                            lineTo(centerX, centerY)
                            lineTo(centerX - 15f, centerY - 10f)
                            close()
                        }
                        drawPath(path, color = Color(0xFF00BCD4).copy(alpha = 0.4f))
                        drawPath(path, color = Color(0xFF00BCD4), style = Stroke(width = 3f))
                    }
                    5 -> { // Indian
                        val path = Path().apply {
                            moveTo(centerX - 50f, centerY - 30f)
                            quadraticBezierTo(centerX - 20f, centerY + 40f, centerX + 50f, centerY + 20f)
                        }
                        drawPath(path, color = Color(0xFF9C27B0), style = Stroke(width = 4f))
                        val path2 = Path().apply {
                            moveTo(centerX - 40f, centerY - 40f)
                            quadraticBezierTo(centerX - 10f, centerY + 30f, centerX + 60f, centerY + 10f)
                        }
                        drawPath(path2, color = Color(0xFFE91E63), style = Stroke(width = 2f))
                    }
                    6 -> { // World
                        val path = Path().apply {
                            moveTo(centerX - 35f, centerY - 45f)
                            lineTo(centerX + 35f, centerY - 45f)
                            lineTo(centerX + 40f, centerY + 40f)
                            lineTo(centerX - 40f, centerY + 40f)
                            close()
                        }
                        drawPath(path, color = Color(0xFF009688).copy(alpha = 0.2f))
                        drawPath(path, color = Color(0xFF009688), style = Stroke(width = 3f))
                        drawLine(color = Color(0xFF009688), start = Offset(centerX - 35f, centerY - 45f), end = Offset(centerX + 20f, centerY + 15f), strokeWidth = 3f)
                        drawLine(color = Color(0xFF009688), start = Offset(centerX + 35f, centerY - 45f), end = Offset(centerX - 20f, centerY + 15f), strokeWidth = 3f)
                    }
                    7 -> { // Tribal
                        val path = Path().apply {
                            moveTo(centerX - 80f, centerY - 10f)
                            lineTo(centerX - 60f, centerY + 10f)
                            lineTo(centerX - 40f, centerY - 10f)
                            lineTo(centerX - 20f, centerY + 10f)
                            lineTo(centerX, centerY - 10f)
                            lineTo(centerX + 20f, centerY + 10f)
                            lineTo(centerX + 40f, centerY - 10f)
                            lineTo(centerX + 60f, centerY + 10f)
                            lineTo(centerX + 80f, centerY - 10f)
                        }
                        drawPath(path, color = Color(0xFF795548), style = Stroke(width = 4f))
                    }
                    8 -> { // Modern
                        drawOval(color = Color(0xFF607D8B).copy(alpha = 0.2f), size = Size(40f, 20f), topLeft = Offset(centerX - 45f, centerY - 10f))
                        drawOval(color = Color(0xFF607D8B).copy(alpha = 0.2f), size = Size(40f, 20f), topLeft = Offset(centerX + 5f, centerY - 10f))
                        drawOval(color = Color(0xFF607D8B), size = Size(40f, 20f), topLeft = Offset(centerX - 45f, centerY - 10f), style = Stroke(width = 3f))
                        drawOval(color = Color(0xFF607D8B), size = Size(40f, 20f), topLeft = Offset(centerX + 5f, centerY - 10f), style = Stroke(width = 3f))
                        drawLine(color = Color(0xFF607D8B), start = Offset(centerX - 5f, centerY), end = Offset(centerX + 5f, centerY), strokeWidth = 3f)
                    }
                    9 -> { // Global
                        drawCircle(color = Color(0xFF03A9F4), radius = 35f, center = Offset(centerX, centerY), style = Stroke(width = 3f))
                        drawOval(color = Color(0xFF03A9F4), size = Size(70f, 25f), topLeft = Offset(centerX - 35f, centerY - 12.5f), style = Stroke(width = 2f))
                        drawLine(color = Color(0xFF03A9F4), start = Offset(centerX, centerY - 35f), end = Offset(centerX, centerY + 35f), strokeWidth = 2f)
                        drawLine(color = Color(0xFF03A9F4), start = Offset(centerX - 35f, centerY), end = Offset(centerX + 35f, centerY), strokeWidth = 2f)
                    }
                    10 -> { // Innerwear
                        drawArc(color = Color(0xFFE91E63), startAngle = 0f, sweepAngle = 180f, useCenter = false, size = Size(35f, 25f), topLeft = Offset(centerX - 40f, centerY - 15f), style = Stroke(width = 3f))
                        drawArc(color = Color(0xFFE91E63), startAngle = 0f, sweepAngle = 180f, useCenter = false, size = Size(35f, 25f), topLeft = Offset(centerX + 5f, centerY - 15f), style = Stroke(width = 3f))
                        drawLine(color = Color(0xFFE91E63), start = Offset(centerX - 5f, centerY), end = Offset(centerX + 5f, centerY), strokeWidth = 3f)
                    }
                    else -> { // Materials
                        for (i in -4..4) {
                            drawLine(color = Color(0xFF9E9E9E), start = Offset(centerX + i * 15f, centerY - 40f), end = Offset(centerX + i * 15f, centerY + 40f), strokeWidth = 2f)
                            drawLine(color = Color(0xFF9E9E9E), start = Offset(centerX - 60f, centerY + i * 10f), end = Offset(centerX + 60f, centerY + i * 10f), strokeWidth = 2f)
                        }
                    }
                }
            }
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 8.dp)
            )
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

// ----------------------------------------------------
// MEASUREMENT GUIDE WITH DETAILED VISUALS
// ----------------------------------------------------
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
            Spacer(modifier = Modifier.height(16.dp))

            MeasurementItemWithVisual("Chest/Bust", "Measure around the fullest part of your chest, keeping the tape horizontal.") {
                Canvas(modifier = Modifier.size(50.dp)) {
                    drawCircle(color = Color(0xFFE91E63).copy(alpha = 0.2f), radius = 20f)
                    drawCircle(color = Color(0xFFE91E63), radius = 20f, style = Stroke(width = 3f))
                    drawLine(color = Color(0xFFE91E63), start = Offset(0f, 25f), end = Offset(100f, 25f), strokeWidth = 4f)
                }
            }

            MeasurementItemWithVisual("Waist", "Measure around the narrowest part (typically where your body bends side to side).") {
                Canvas(modifier = Modifier.size(50.dp)) {
                    drawRoundRect(
                        color = Color(0xFF4CAF50).copy(alpha = 0.2f),
                        size = Size(35f, 45f),
                        cornerRadius = CornerRadius(8f, 8f),
                        topLeft = Offset(10f, 5f)
                    )
                    drawArc(
                        color = Color(0xFF4CAF50),
                        startAngle = 135f,
                        sweepAngle = 90f,
                        useCenter = false,
                        size = Size(40f, 40f),
                        topLeft = Offset(8f, 8f),
                        style = Stroke(width = 3f)
                    )
                }
            }

            MeasurementItemWithVisual("Hips", "Measure around the fullest part of your hips.") {
                Canvas(modifier = Modifier.size(50.dp)) {
                    drawOval(color = Color(0xFF00BCD4).copy(alpha = 0.2f), size = Size(45f, 30f), topLeft = Offset(5f, 12f))
                    drawOval(color = Color(0xFF00BCD4), size = Size(45f, 30f), topLeft = Offset(5f, 12f), style = Stroke(width = 3f))
                }
            }

            MeasurementItemWithVisual("Foot Length", "Place your foot on a paper, mark the heel and longest toe. Measure the distance.") {
                Canvas(modifier = Modifier.size(50.dp)) {
                    drawRoundRect(
                        color = Color(0xFF2196F3).copy(alpha = 0.15f),
                        size = Size(20f, 40f),
                        cornerRadius = CornerRadius(10f, 10f),
                        topLeft = Offset(15f, 5f)
                    )
                    drawCircle(color = Color(0xFF2196F3), radius = 4f, center = Offset(18f, 10f))
                    drawCircle(color = Color(0xFF2196F3), radius = 3f, center = Offset(25f, 12f))
                    drawCircle(color = Color(0xFF2196F3), radius = 3f, center = Offset(30f, 16f))
                }
            }

            MeasurementItemWithVisual("Head Circumference", "Measure around your head where a hat would rest (usually 1/2 inch above ears).") {
                Canvas(modifier = Modifier.size(50.dp)) {
                    drawCircle(color = Color(0xFFFF9800).copy(alpha = 0.2f), radius = 18f, center = Offset(25f, 25f))
                    drawCircle(color = Color(0xFFFF9800), radius = 18f, center = Offset(25f, 25f), style = Stroke(width = 3f))
                    drawArc(
                        color = Color(0xFFFF9800),
                        startAngle = 0f,
                        sweepAngle = 360f,
                        useCenter = false,
                        size = Size(42f, 20f),
                        topLeft = Offset(4f, 15f),
                        style = Stroke(width = 2f)
                    )
                }
            }

            MeasurementItemWithVisual("Saree Length", "Standard saree is 5.5 meters, while some regional styles can be up to 9 meters.") {
                Canvas(modifier = Modifier.size(50.dp)) {
                    drawArc(
                        color = Color(0xFF9C27B0),
                        startAngle = 0f,
                        sweepAngle = 180f,
                        useCenter = false,
                        size = Size(40f, 35f),
                        topLeft = Offset(5f, 5f),
                        style = Stroke(width = 3f)
                    )
                    drawArc(
                        color = Color(0xFF9C27B0).copy(alpha = 0.5f),
                        startAngle = 20f,
                        sweepAngle = 140f,
                        useCenter = false,
                        size = Size(35f, 25f),
                        topLeft = Offset(8f, 15f),
                        style = Stroke(width = 2f)
                    )
                }
            }
        }
    }
}

@Composable
fun MeasurementItemWithVisual(title: String, description: String, visual: @Composable () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(55.dp)
                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f), RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            visual()
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(title, style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
            Text(description, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

// ----------------------------------------------------
// NEW UNCENSORED MASTER STYLING & PHYSIQUE DIRECTORY
// ----------------------------------------------------
@Composable
fun UncensoredMasterDirectoryUI() {
    var activeCategory by remember { mutableIntStateOf(0) } // 0: Silhouette, 1: Intimate & Inners, 2: Eras & Styles, 3: Weather/Loc

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.25f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                "Uncensored Body, Style & Context Guides",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(12.dp))

            // Sub-tabs for our uncensored guide categories
            ScrollableTabRow(
                selectedTabIndex = activeCategory,
                edgePadding = 0.dp,
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.secondary
            ) {
                listOf("Silhouette & Parts", "Inners & Intimate", "Fashion Eras", "Climate & Loc").forEachIndexed { index, title ->
                    Tab(
                        selected = activeCategory == index,
                        onClick = { activeCategory = index },
                        text = { Text(title, fontSize = 11.sp, fontWeight = FontWeight.Bold) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            when (activeCategory) {
                0 -> SilhouetteGuidesUI()
                1 -> IntimateInnersGuidesUI()
                2 -> FashionErasGuidesUI()
                else -> ClimateLocationGuidesUI()
            }
        }
    }
}

@Composable
fun SilhouetteGuidesUI() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("Detailed Physique & Proportion Guides", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.secondary, fontWeight = FontWeight.Bold)

        MeasurementItemWithVisual(
            "Body Shape Outlines",
            "• Hourglass: Balanced bust/hips with defined waist.\n• Pear: Hips wider than bust; highlight shoulders.\n• Apple: Carry weight around midsection; wear empire/V-necks.\n• Rectangle: Straight hips/bust; create waist definition using belts."
        ) {
            Canvas(modifier = Modifier.size(50.dp)) {
                // Hourglass curve contour visual
                drawArc(color = Color(0xFFE91E63), startAngle = -45f, sweepAngle = 90f, useCenter = false, size = Size(30f, 40f), topLeft = Offset(5f, 5f), style = Stroke(width = 3f))
                drawArc(color = Color(0xFFE91E63), startAngle = 135f, sweepAngle = 90f, useCenter = false, size = Size(30f, 40f), topLeft = Offset(15f, 5f), style = Stroke(width = 3f))
            }
        }

        MeasurementItemWithVisual(
            "Body Parts Shapes",
            "• Bust Shape: Round, teardrop, East-West (affects cup support).\n• Buttocks Shape: Round, A-shape, Heart-shape (dictates optimal underwear leg cuts).\n• Shoulder Width: Broad vs Narrow (determines collar and sleeve seam placements)."
        ) {
            Canvas(modifier = Modifier.size(50.dp)) {
                // Round parts silhouette
                drawCircle(color = Color(0xFFFF5722), radius = 10f, center = Offset(16f, 25f))
                drawCircle(color = Color(0xFFFF5722), radius = 10f, center = Offset(34f, 25f))
                drawCircle(color = Color(0xFFFF5722).copy(alpha = 0.2f), radius = 10f, center = Offset(16f, 25f))
                drawCircle(color = Color(0xFFFF5722).copy(alpha = 0.2f), radius = 10f, center = Offset(34f, 25f))
            }
        }
    }
}

@Composable
fun IntimateInnersGuidesUI() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("Bra & Underwear Style Visual Guides", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.secondary, fontWeight = FontWeight.Bold)

        MeasurementItemWithVisual(
            "Bra & Cup Styles",
            "• Balconette: Low-cut cup, lifts from below.\n• Plunge: Deep V-cut center, ideal for low necklines.\n• Bralette: Lightweight, wire-free comfort.\n• Push-up: Thick inner padding for enhanced cleavage."
        ) {
            Canvas(modifier = Modifier.size(50.dp)) {
                // Bra cups schematic
                drawArc(color = Color(0xFFE91E63), startAngle = 0f, sweepAngle = 180f, useCenter = false, size = Size(20f, 15f), topLeft = Offset(4f, 18f), style = Stroke(width = 3f))
                drawArc(color = Color(0xFFE91E63), startAngle = 0f, sweepAngle = 180f, useCenter = false, size = Size(20f, 15f), topLeft = Offset(26f, 18f), style = Stroke(width = 3f))
                drawLine(color = Color(0xFFE91E63), start = Offset(24f, 25f), end = Offset(26f, 25f), strokeWidth = 3f)
            }
        }

        MeasurementItemWithVisual(
            "Underwear & Inners",
            "• Briefs/Hipsters: Full back coverage, sits on hips.\n• Boxers: Loose, breathable square leg fit.\n• Thongs/G-strings: Zero rear coverage, eliminates lines.\n• Bikinis: Low waist, moderate side and rear coverage."
        ) {
            Canvas(modifier = Modifier.size(50.dp)) {
                // Briefs outline
                drawLine(color = Color(0xFF3F51B5), start = Offset(5f, 12f), end = Offset(45f, 12f), strokeWidth = 4f)
                drawLine(color = Color(0xFF3F51B5), start = Offset(5f, 12f), end = Offset(12f, 28f), strokeWidth = 3f)
                drawLine(color = Color(0xFF3F51B5), start = Offset(45f, 12f), end = Offset(38f, 28f), strokeWidth = 3f)
                drawLine(color = Color(0xFF3F51B5), start = Offset(12f, 28f), end = Offset(38f, 28f), strokeWidth = 4f)
            }
        }
    }
}

@Composable
fun FashionErasGuidesUI() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("Fashion Aesthetics & Cultural Eras", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.secondary, fontWeight = FontWeight.Bold)

        MeasurementItemWithVisual(
            "Vintage & Modern Styles",
            "• Vintage: 1950s full circle swing skirts, 1970s flared jeans, 1990s distressed grunge.\n• Modern: Tech-wear (buckles, waterproof fabrics), Minimalist (capsule wardrobe), Cottagecore (puff sleeves, natural linen)."
        ) {
            Canvas(modifier = Modifier.size(50.dp)) {
                // Dress silhouette
                drawArc(color = Color(0xFF4CAF50), startAngle = 45f, sweepAngle = 90f, useCenter = true, size = Size(35f, 35f), topLeft = Offset(7f, 10f))
            }
        }

        MeasurementItemWithVisual(
            "Traditional & Professional",
            "• Traditional: Curated regional outfits (Saree, Kimono, Hanbok) with fabric measurements.\n• Professional: Corporate Power Suits (structured padded shoulders) & smart Business Casual rules."
        ) {
            Canvas(modifier = Modifier.size(50.dp)) {
                // Necktie / Suit lapel representation
                drawLine(color = Color(0xFFFF9800), start = Offset(25f, 5f), end = Offset(25f, 45f), strokeWidth = 4f)
                drawLine(color = Color(0xFFFF9800), start = Offset(15f, 5f), end = Offset(25f, 15f), strokeWidth = 3f)
                drawLine(color = Color(0xFFFF9800), start = Offset(35f, 5f), end = Offset(25f, 15f), strokeWidth = 3f)
            }
        }

        MeasurementItemWithVisual(
            "Cosplay & Fantasy Guides",
            "• Character Anime-Styles: Exaggerated proportions, layered synthetic wigs.\n• Armor & Props: Sizing rules for EVA foam plates, gauntlets, and wing harnesses.\n• Fantasy: Draped capes, structural corsets, and medieval fantasy boots."
        ) {
            Canvas(modifier = Modifier.size(50.dp)) {
                // Wing/Sword emblem
                drawLine(color = Color(0xFF9C27B0), start = Offset(10f, 10f), end = Offset(40f, 40f), strokeWidth = 3f)
                drawCircle(color = Color(0xFF9C27B0), radius = 6f, center = Offset(25f, 25f))
            }
        }
    }
}

@Composable
fun ClimateLocationGuidesUI() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("Environmental, Location & Weather Styling", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.secondary, fontWeight = FontWeight.Bold)

        MeasurementItemWithVisual(
            "Location-Specific Sizing",
            "• Urban Streetwear: High stretch, loose fit for movement, high durable denims.\n• Beach Cruise: Extremely lightweight, high-draping semi-sheer fabrics (viscose, sheer silk).\n• Mountain Hiking: High thermal retention, strict athletic fits to prevent chafing."
        ) {
            Canvas(modifier = Modifier.size(50.dp)) {
                // City Skyline buildings schematic
                drawRect(color = Color(0xFF607D8B), size = Size(12f, 35f), topLeft = Offset(5f, 10f))
                drawRect(color = Color(0xFF607D8B), size = Size(15f, 25f), topLeft = Offset(18f, 20f))
                drawRect(color = Color(0xFF607D8B), size = Size(10f, 40f), topLeft = Offset(34f, 5f))
            }
        }

        MeasurementItemWithVisual(
            "Weather & Climate Layering",
            "• Humid Heat: Single-layer cottons below 120 GSM, loose cuts for max ventilation.\n• Dry Arid: Loose weave linen, full cover long sleeve to shield UV radiation.\n• Sub-Zero Cold: Triple layering (synthetic base layer + wool mid-layer + heavy down coat outer layer)."
        ) {
            Canvas(modifier = Modifier.size(50.dp)) {
                // Sun & Cloud weather diagram
                drawCircle(color = Color(0xFFFFB300), radius = 12f, center = Offset(18f, 18f))
                drawCircle(color = Color(0xFFB0BEC5), radius = 10f, center = Offset(30f, 28f))
                drawRect(color = Color(0xFFB0BEC5), size = Size(20f, 10f), topLeft = Offset(20f, 23f))
            }
        }
    }
}

// ----------------------------------------------------
// FASHION COMBINATION & LAYERING GUIDES
// ----------------------------------------------------
@Composable
fun FashionCombinationGuide() {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Info, null, tint = MaterialTheme.colorScheme.secondary)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Aesthetic & Layering Combination Guide", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(16.dp))

            MeasurementItemWithVisual(
                "Color Contrast Combinations",
                "• Complementary: Combine opposite colors (e.g. Navy Blue + Mustard Yellow) for high contrast.\n• Monochromatic: Blend different tints of one color (e.g. Sky Blue + Royal Blue) for an elegant look."
            ) {
                Canvas(modifier = Modifier.size(50.dp)) {
                    // Complementary color segments
                    drawRect(color = Color(0xFF0D47A1), size = Size(18f, 35f), topLeft = Offset(6f, 8f))
                    drawRect(color = Color(0xFFFFB300), size = Size(18f, 35f), topLeft = Offset(26f, 8f))
                }
            }

            MeasurementItemWithVisual(
                "Garment Layering (Combination Theory)",
                "• Base Layer: Breathable cotton/linen t-shirt.\n• Middle Layer: Insulating denim jacket, knit sweater, or cardigan.\n• Outer Layer: Protective windbreaker, trench coat, or structured blazer."
            ) {
                Canvas(modifier = Modifier.size(50.dp)) {
                    // Stacked layered colored boxes
                    drawRoundRect(color = Color(0xFF9C27B0).copy(alpha = 0.3f), size = Size(40f, 10f), topLeft = Offset(5f, 30f), cornerRadius = CornerRadius(4f, 4f))
                    drawRoundRect(color = Color(0xFF2196F3).copy(alpha = 0.5f), size = Size(34f, 10f), topLeft = Offset(8f, 20f), cornerRadius = CornerRadius(4f, 4f))
                    drawRoundRect(color = Color(0xFF4CAF50), size = Size(28f, 10f), topLeft = Offset(11f, 10f), cornerRadius = CornerRadius(4f, 4f))
                }
            }

            MeasurementItemWithVisual(
                "Silhouette & Proportion Balance",
                "Balance clothing weights by combining tight and loose silhouettes: pair a loose, relaxed top with structured/fitted pants, or vice versa, to create optimal body lines."
            ) {
                Canvas(modifier = Modifier.size(50.dp)) {
                    // Balance indicator lines
                    drawLine(color = Color(0xFF9E9E9E), start = Offset(5f, 25f), end = Offset(45f, 25f), strokeWidth = 3f)
                    drawCircle(color = Color(0xFFFF5722), radius = 8f, center = Offset(15f, 25f))
                    drawCircle(color = Color(0xFF03A9F4), radius = 12f, center = Offset(35f, 25f))
                }
            }
        }
    }
}
