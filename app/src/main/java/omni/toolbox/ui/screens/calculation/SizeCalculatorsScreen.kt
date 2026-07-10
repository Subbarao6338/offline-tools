package omni.toolbox.ui.screens.calculation

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
import omni.toolbox.ui.components.ToolScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SizeCalculatorsScreen(navController: NavHostController, initialTab: Int = 0) {
    var selectedTab by remember { mutableIntStateOf(initialTab) }
    val tabTitles = listOf("Bra", "Underwear", "Dress", "Ring", "Arm", "Body Frame", "Kids")

    ToolScreen(title = "Fashion & Size Calculators", onBack = { navController.popBackStack() }) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            ScrollableTabRow(
                selectedTabIndex = selectedTab,
                edgePadding = 16.dp,
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.primary
            ) {
                tabTitles.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { Text(title) }
                    )
                }
            }

            LazyColumn(
                modifier = Modifier.weight(1f).fillMaxWidth(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    when (selectedTab) {
                        0 -> BraCalculatorUI()
                        1 -> UnderwearCalculatorUI()
                        2 -> DressCalculatorUI()
                        3 -> RingCalculatorUI()
                        4 -> ArmCalculatorUI()
                        5 -> BodyMeasurementsUI()
                        else -> KidsSizeCalculatorUI()
                    }
                }
            }
        }
    }
}

// ----------------------------------------------------
// 1. BRA SIZE CALCULATOR
// ----------------------------------------------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BraCalculatorUI() {
    var underbust by remember { mutableStateOf("") }
    var bust by remember { mutableStateOf("") }
    var unit by remember { mutableIntStateOf(0) } // 0: Inches, 1: CM

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.15f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Bra Size Calculator", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(12.dp))

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
                label = { Text("Bust Size") },
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
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Estimated US/UK Size", style = MaterialTheme.typography.labelMedium)
                        Text("$band$cup", style = MaterialTheme.typography.displaySmall, fontWeight = FontWeight.Bold)
                        Text("EU Band Size: ${(uInches * 2.54 / 5).toInt() * 5}", style = MaterialTheme.typography.bodySmall)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                Text("Sister Sizes (Alternate Fitting):", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                Text("Tight band alternative: ${band - 2}${nextCup(cup)}  |  Loose band alternative: ${band + 2}${prevCup(cup)}", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }

    Spacer(modifier = Modifier.height(16.dp))
    BraSizeGuideUI()
}

fun nextCup(cup: String): String = when (cup) {
    "AA" -> "A"
    "A" -> "B"
    "B" -> "C"
    "C" -> "D"
    "D" -> "DD/E"
    "DD/E" -> "DDD/F"
    else -> "G"
}

fun prevCup(cup: String): String = when (cup) {
    "G" -> "DDD/F"
    "DDD/F" -> "DD/E"
    "DD/E" -> "D"
    "D" -> "C"
    "C" -> "B"
    "B" -> "A"
    else -> "AA"
}

@Composable
fun BraSizeGuideUI() {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Bra Sizing Reference Guide", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(8.dp))
            Text("• Underbust measurement determines the Band size.\n• Bust measurement minus Band size determines Cup size (each 1-inch difference is one cup size: 1\"=A, 2\"=B, 3\"=C, etc.).", style = MaterialTheme.typography.bodySmall)

            Spacer(modifier = Modifier.height(12.dp))
            Text("Standard Band Sizing Chart (Inches to EU/UK)", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column {
                    Text("Underbust (in)", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodySmall)
                    Text("26\" - 27\"", style = MaterialTheme.typography.bodySmall)
                    Text("28\" - 29\"", style = MaterialTheme.typography.bodySmall)
                    Text("30\" - 31\"", style = MaterialTheme.typography.bodySmall)
                    Text("32\" - 33\"", style = MaterialTheme.typography.bodySmall)
                }
                Column {
                    Text("US/UK Band", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodySmall)
                    Text("30", style = MaterialTheme.typography.bodySmall)
                    Text("32", style = MaterialTheme.typography.bodySmall)
                    Text("34", style = MaterialTheme.typography.bodySmall)
                    Text("36", style = MaterialTheme.typography.bodySmall)
                }
                Column {
                    Text("EU Band", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodySmall)
                    Text("65", style = MaterialTheme.typography.bodySmall)
                    Text("70", style = MaterialTheme.typography.bodySmall)
                    Text("75", style = MaterialTheme.typography.bodySmall)
                    Text("80", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}

// ----------------------------------------------------
// 2. UNDERWEAR SIZE CALCULATOR
// ----------------------------------------------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnderwearCalculatorUI() {
    var waist by remember { mutableStateOf("") }
    var hips by remember { mutableStateOf("") }
    var genderIndex by remember { mutableIntStateOf(0) } // 0: Men, 1: Women
    var unit by remember { mutableIntStateOf(0) } // 0: Inches, 1: CM

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.15f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Underwear Size Calculator", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(12.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = { genderIndex = 0 },
                    colors = ButtonDefaults.buttonColors(containerColor = if (genderIndex == 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Men", color = if (genderIndex == 0) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant)
                }
                Button(
                    onClick = { genderIndex = 1 },
                    colors = ButtonDefaults.buttonColors(containerColor = if (genderIndex == 1) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Women", color = if (genderIndex == 1) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
                SegmentedButton(selected = unit == 0, onClick = { unit = 0 }, shape = SegmentedButtonDefaults.itemShape(index = 0, count = 2)) { Text("Inches") }
                SegmentedButton(selected = unit == 1, onClick = { unit = 1 }, shape = SegmentedButtonDefaults.itemShape(index = 1, count = 2)) { Text("CM") }
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = waist,
                onValueChange = { waist = it },
                label = { Text("Waist Measurement") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            if (genderIndex == 1) {
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = hips,
                    onValueChange = { hips = it },
                    label = { Text("Hips Measurement") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            }

            val wValue = waist.toDoubleOrNull() ?: 0.0
            val hValue = hips.toDoubleOrNull() ?: 0.0

            if (wValue > 0) {
                val wInches = if (unit == 1) wValue / 2.54 else wValue
                val hInches = if (unit == 1) hValue / 2.54 else hValue

                val calculatedSize = if (genderIndex == 0) {
                    // Men Underwear Sizing
                    when {
                        wInches < 28 -> "XS"
                        wInches < 31 -> "S"
                        wInches < 35 -> "M"
                        wInches < 39 -> "L"
                        wInches < 43 -> "XL"
                        else -> "XXL"
                    }
                } else {
                    // Women Underwear Sizing based primarily on Hips (or waist if hips blank)
                    val baseMetric = if (hInches > 0) hInches else wInches + 10
                    when {
                        baseMetric < 34 -> "XXS (US 0)"
                        baseMetric < 36 -> "XS (US 2)"
                        baseMetric < 38 -> "S (US 4-6)"
                        baseMetric < 40 -> "M (US 8-10)"
                        baseMetric < 43 -> "L (US 12-14)"
                        baseMetric < 46 -> "XL (US 16)"
                        else -> "XXL (US 18+)"
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Estimated Underwear Size", style = MaterialTheme.typography.labelMedium)
                        Text(calculatedSize, style = MaterialTheme.typography.displaySmall, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }

    Spacer(modifier = Modifier.height(16.dp))
    UnderwearSizeGuideUI()
}

@Composable
fun UnderwearSizeGuideUI() {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Underwear Size Reference Charts", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(12.dp))

            Text("Men's Underwear Chart (Waist)", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyMedium)
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("XS: 26\"-28\"", style = MaterialTheme.typography.bodySmall)
                Text("S: 28\"-30\"", style = MaterialTheme.typography.bodySmall)
                Text("M: 32\"-34\"", style = MaterialTheme.typography.bodySmall)
                Text("L: 36\"-38\"", style = MaterialTheme.typography.bodySmall)
                Text("XL: 40\"-42\"", style = MaterialTheme.typography.bodySmall)
            }

            Spacer(modifier = Modifier.height(12.dp))
            Text("Women's Underwear Chart (Hips)", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyMedium)
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("XS: 34\"-35\"", style = MaterialTheme.typography.bodySmall)
                Text("S: 36\"-37\"", style = MaterialTheme.typography.bodySmall)
                Text("M: 38\"-39\"", style = MaterialTheme.typography.bodySmall)
                Text("L: 40\"-42\"", style = MaterialTheme.typography.bodySmall)
                Text("XL: 43\"-45\"", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

// ----------------------------------------------------
// 3. DRESS SIZE CALCULATOR
// ----------------------------------------------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DressCalculatorUI() {
    var bust by remember { mutableStateOf("") }
    var waist by remember { mutableStateOf("") }
    var hips by remember { mutableStateOf("") }
    var unit by remember { mutableIntStateOf(0) } // 0: Inches, 1: CM

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.15f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Dress Size Calculator", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(12.dp))

            SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
                SegmentedButton(selected = unit == 0, onClick = { unit = 0 }, shape = SegmentedButtonDefaults.itemShape(index = 0, count = 2)) { Text("Inches") }
                SegmentedButton(selected = unit == 1, onClick = { unit = 1 }, shape = SegmentedButtonDefaults.itemShape(index = 1, count = 2)) { Text("CM") }
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(value = bust, onValueChange = { bust = it }, label = { Text("Bust") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(value = waist, onValueChange = { waist = it }, label = { Text("Waist") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(value = hips, onValueChange = { hips = it }, label = { Text("Hips") }, modifier = Modifier.fillMaxWidth(), singleLine = true)

            val bValue = bust.toDoubleOrNull() ?: 0.0
            val wValue = waist.toDoubleOrNull() ?: 0.0
            val hValue = hips.toDoubleOrNull() ?: 0.0

            if (bValue > 0 && wValue > 0 && hValue > 0) {
                val bIn = if (unit == 1) bValue / 2.54 else bValue
                val wIn = if (unit == 1) wValue / 2.54 else wValue
                val hIn = if (unit == 1) hValue / 2.54 else hValue

                // Comprehensive dress sizing algorithm
                val sizeVal = when {
                    bIn <= 32 && wIn <= 24 && hIn <= 34 -> "XXS (US 0 / UK 4)"
                    bIn <= 33 && wIn <= 25 && hIn <= 35 -> "XS (US 2 / UK 6)"
                    bIn <= 35 && wIn <= 27 && hIn <= 37 -> "S (US 4-6 / UK 8-10)"
                    bIn <= 37 && wIn <= 29 && hIn <= 39 -> "M (US 8-10 / UK 12-14)"
                    bIn <= 40 && wIn <= 32 && hIn <= 42 -> "L (US 12-14 / UK 16-18)"
                    bIn <= 43 && wIn <= 35 && hIn <= 45 -> "XL (US 16 / UK 20)"
                    else -> "XXL (US 18+ / UK 22+)"
                }

                Spacer(modifier = Modifier.height(16.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Estimated Dress Size", style = MaterialTheme.typography.labelMedium)
                        Text(sizeVal, style = MaterialTheme.typography.displaySmall, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }

    Spacer(modifier = Modifier.height(16.dp))
    DressSizeGuideUI()
}

@Composable
fun DressSizeGuideUI() {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Dress Sizing Standards", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(12.dp))

            Text("Standard US/UK size mapping", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))

            Box(modifier = Modifier.horizontalScroll(rememberScrollState())) {
                Column {
                    Row(modifier = Modifier.background(MaterialTheme.colorScheme.secondaryContainer).padding(8.dp)) {
                        listOf("Size", "US", "UK", "Bust", "Waist", "Hips").forEach { title ->
                            Text(title, modifier = Modifier.width(80.dp), fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodySmall)
                        }
                    }
                    listOf(
                        listOf("XS", "2", "6", "32.5\"", "25\"", "35\""),
                        listOf("S", "4", "8", "33.5\"", "26\"", "36\""),
                        listOf("S", "6", "10", "34.5\"", "27\"", "37\""),
                        listOf("M", "8", "12", "35.5\"", "28\"", "38\""),
                        listOf("M", "10", "14", "37\"-38\"", "30\"", "40\""),
                        listOf("L", "12", "16", "39\"-40\"", "32\"", "42\""),
                        listOf("XL", "16", "20", "43\"", "35\"", "45\"")
                    ).forEach { row ->
                        Row(modifier = Modifier.padding(8.dp)) {
                            row.forEach { cell ->
                                Text(cell, modifier = Modifier.width(80.dp), style = MaterialTheme.typography.bodySmall)
                            }
                        }
                    }
                }
            }
        }
    }
}

// ----------------------------------------------------
// 4. RING SIZE CALCULATOR
// ----------------------------------------------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RingCalculatorUI() {
    var circumference by remember { mutableStateOf("") }
    var diameter by remember { mutableStateOf("") }
    var calculationType by remember { mutableIntStateOf(0) } // 0: Circumference, 1: Diameter

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.15f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Ring Size Calculator", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(12.dp))

            SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
                SegmentedButton(selected = calculationType == 0, onClick = { calculationType = 0 }, shape = SegmentedButtonDefaults.itemShape(index = 0, count = 2)) { Text("Circumference (mm)") }
                SegmentedButton(selected = calculationType == 1, onClick = { calculationType = 1 }, shape = SegmentedButtonDefaults.itemShape(index = 1, count = 2)) { Text("Diameter (mm)") }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (calculationType == 0) {
                OutlinedTextField(
                    value = circumference,
                    onValueChange = { circumference = it },
                    label = { Text("Circumference of Finger (mm)") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            } else {
                OutlinedTextField(
                    value = diameter,
                    onValueChange = { diameter = it },
                    label = { Text("Inner Diameter of Existing Ring (mm)") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            }

            val circVal = if (calculationType == 0) circumference.toDoubleOrNull() ?: 0.0 else (diameter.toDoubleOrNull() ?: 0.0) * Math.PI

            if (circVal > 0) {
                // Calculate US ring size based on standard formula: Size = (Circumference - 36.5) / 2.58
                val rawSize = (circVal - 36.5) / 2.58
                // round to nearest 0.5 size
                val roundedSize = (Math.round(rawSize * 2.0) / 2.0).coerceIn(1.0, 15.0)

                Spacer(modifier = Modifier.height(16.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Estimated US Ring Size", style = MaterialTheme.typography.labelMedium)
                        Text("Size $roundedSize", style = MaterialTheme.typography.displaySmall, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("EU Circumference: ${circVal.toInt()} mm  |  UK: ${ukRingLetter(roundedSize)}", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
    }

    Spacer(modifier = Modifier.height(16.dp))
    RingSizeGuideUI()
}

fun ukRingLetter(usSize: Double): String = when (usSize) {
    1.0 -> "A"
    1.5 -> "B"
    2.0 -> "C"
    2.5 -> "D"
    3.0 -> "F"
    3.5 -> "G"
    4.0 -> "H 1/2"
    4.5 -> "I 1/2"
    5.0 -> "J 1/2"
    5.5 -> "L"
    6.0 -> "M"
    6.5 -> "N"
    7.0 -> "O"
    7.5 -> "P"
    8.0 -> "Q"
    8.5 -> "R"
    9.0 -> "S"
    9.5 -> "T 1/2"
    10.0 -> "U 1/2"
    10.5 -> "V 1/2"
    11.0 -> "W 1/2"
    11.5 -> "Y"
    12.0 -> "Z"
    else -> "Z+"
}

@Composable
fun RingSizeGuideUI() {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Ring Sizing Guide", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(8.dp))
            Text("To measure circumference at home: Wrap a thin strip of paper or string snuggly around the base of your finger, mark the overlap, and measure its length in millimeters.", style = MaterialTheme.typography.bodySmall)

            Spacer(modifier = Modifier.height(12.dp))
            Text("Common US Ring Size Benchmarks", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column {
                    Text("Diameter (mm)", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodySmall)
                    Text("14.1 mm", style = MaterialTheme.typography.bodySmall)
                    Text("14.9 mm", style = MaterialTheme.typography.bodySmall)
                    Text("15.7 mm", style = MaterialTheme.typography.bodySmall)
                    Text("16.5 mm", style = MaterialTheme.typography.bodySmall)
                    Text("17.3 mm", style = MaterialTheme.typography.bodySmall)
                }
                Column {
                    Text("Circumference (mm)", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodySmall)
                    Text("44.2 mm", style = MaterialTheme.typography.bodySmall)
                    Text("46.8 mm", style = MaterialTheme.typography.bodySmall)
                    Text("49.3 mm", style = MaterialTheme.typography.bodySmall)
                    Text("51.8 mm", style = MaterialTheme.typography.bodySmall)
                    Text("54.4 mm", style = MaterialTheme.typography.bodySmall)
                }
                Column {
                    Text("US Ring Size", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodySmall)
                    Text("Size 3", style = MaterialTheme.typography.bodySmall)
                    Text("Size 4", style = MaterialTheme.typography.bodySmall)
                    Text("Size 5", style = MaterialTheme.typography.bodySmall)
                    Text("Size 6", style = MaterialTheme.typography.bodySmall)
                    Text("Size 7", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}

// ----------------------------------------------------
// 5. ARM / SLEEVE SIZE CALCULATOR
// ----------------------------------------------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArmCalculatorUI() {
    var height by remember { mutableStateOf("") }
    var ageGroup by remember { mutableIntStateOf(0) } // 0: Men, 1: Women, 2: Boys, 3: Girls
    var unit by remember { mutableIntStateOf(0) } // 0: Inches, 1: CM

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.15f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Arm & Sleeve Size Calculator", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(12.dp))

            ScrollableTabRow(
                selectedTabIndex = ageGroup,
                edgePadding = 0.dp,
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.primary
            ) {
                listOf("Men", "Women", "Boys", "Girls").forEachIndexed { index, name ->
                    Tab(selected = ageGroup == index, onClick = { ageGroup = index }, text = { Text(name, fontSize = 12.sp) })
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
                SegmentedButton(selected = unit == 0, onClick = { unit = 0 }, shape = SegmentedButtonDefaults.itemShape(index = 0, count = 2)) { Text("Inches") }
                SegmentedButton(selected = unit == 1, onClick = { unit = 1 }, shape = SegmentedButtonDefaults.itemShape(index = 1, count = 2)) { Text("CM") }
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = height,
                onValueChange = { height = it },
                label = { Text("Your Total Height") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            val hVal = height.toDoubleOrNull() ?: 0.0

            if (hVal > 0) {
                val hIn = if (unit == 1) hVal / 2.54 else hVal

                // Sleeve length is strongly correlated with overall height (typically height * 0.48 / 2 to find standard single arm sleeve)
                val estimatedSleeve = hIn * 0.48

                val sizeCode = if (ageGroup == 0 || ageGroup == 1) {
                    // Adults Sizing
                    when {
                        estimatedSleeve < 30.5 -> "Short (XS / S)"
                        estimatedSleeve < 32.5 -> "Regular (M)"
                        estimatedSleeve < 34.5 -> "Regular (L)"
                        else -> "Long (XL / XXL)"
                    }
                } else {
                    // Kids Sizing (Boys & Girls)
                    when {
                        estimatedSleeve < 20.0 -> "Toddler (XS)"
                        estimatedSleeve < 23.0 -> "Small (6-8)"
                        estimatedSleeve < 26.0 -> "Medium (10-12)"
                        else -> "Large (14-16)"
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Estimated Sleeve Length", style = MaterialTheme.typography.labelMedium)
                        Text("%.1f ${if(unit == 0) "in" else "cm"}".format(if(unit == 0) estimatedSleeve else estimatedSleeve * 2.54), style = MaterialTheme.typography.displaySmall, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Recommended Fit: $sizeCode", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
    }

    Spacer(modifier = Modifier.height(16.dp))
    ArmSizeGuideUI()
}

@Composable
fun ArmSizeGuideUI() {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Sleeve Measuring Standard", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Standard sleeve is measured from the nape of the neck (center back of your neck), over the shoulder point, and down to the wrist bone.", style = MaterialTheme.typography.bodySmall)
        }
    }
}

// ----------------------------------------------------
// 6. BODY MEASUREMENTS & FRAME CALCULATOR
// ----------------------------------------------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BodyMeasurementsUI() {
    var height by remember { mutableStateOf("") }
    var wrist by remember { mutableStateOf("") }
    var genderIndex by remember { mutableIntStateOf(0) } // 0: Men, 1: Women
    var unit by remember { mutableIntStateOf(0) } // 0: Inches, 1: CM

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.15f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Body Frame & Proportions Assessment", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(12.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = { genderIndex = 0 },
                    colors = ButtonDefaults.buttonColors(containerColor = if (genderIndex == 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Men", color = if (genderIndex == 0) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant)
                }
                Button(
                    onClick = { genderIndex = 1 },
                    colors = ButtonDefaults.buttonColors(containerColor = if (genderIndex == 1) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Women", color = if (genderIndex == 1) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
                SegmentedButton(selected = unit == 0, onClick = { unit = 0 }, shape = SegmentedButtonDefaults.itemShape(index = 0, count = 2)) { Text("Inches") }
                SegmentedButton(selected = unit == 1, onClick = { unit = 1 }, shape = SegmentedButtonDefaults.itemShape(index = 1, count = 2)) { Text("CM") }
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(value = height, onValueChange = { height = it }, label = { Text("Height") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(value = wrist, onValueChange = { wrist = it }, label = { Text("Wrist Circumference") }, modifier = Modifier.fillMaxWidth(), singleLine = true)

            val hVal = height.toDoubleOrNull() ?: 0.0
            val wVal = wrist.toDoubleOrNull() ?: 0.0

            if (hVal > 0 && wVal > 0) {
                val hIn = if (unit == 1) hVal / 2.54 else hVal
                val wIn = if (unit == 1) wVal / 2.54 else wVal

                // Calculate ratio to determine frame size
                val ratio = hIn / wIn

                val frameSize = if (genderIndex == 0) {
                    when {
                        ratio > 10.4 -> "Small Frame"
                        ratio >= 9.6 -> "Medium Frame"
                        else -> "Large Frame"
                    }
                } else {
                    when {
                        ratio > 11.0 -> "Small Frame"
                        ratio >= 10.1 -> "Medium Frame"
                        else -> "Large Frame"
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Assessed Body Frame Size", style = MaterialTheme.typography.labelMedium)
                        Text(frameSize, style = MaterialTheme.typography.displaySmall, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Height-to-Wrist Ratio: %.2f".format(ratio), style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
    }

    Spacer(modifier = Modifier.height(16.dp))
    BodyFrameGuideUI()
}

@Composable
fun BodyFrameGuideUI() {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Understanding Body Frame Size", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Body frame size is a classification of your skeletal structure determined by height and wrist circumference. Knowing your frame size helps in establishing ideal weight distributions and tailoring custom garments.", style = MaterialTheme.typography.bodySmall)
        }
    }
}

// ----------------------------------------------------
// 7. KIDS SIZE CALCULATOR
// ----------------------------------------------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KidsSizeCalculatorUI() {
    var ageGroup by remember { mutableIntStateOf(0) } // 0: Boys, 1: Girls
    var age by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var unit by remember { mutableIntStateOf(0) } // 0: Inches, 1: CM

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.15f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Kids Growth & Clothing Size Calculator", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(12.dp))

            SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
                SegmentedButton(selected = ageGroup == 0, onClick = { ageGroup = 0 }, shape = SegmentedButtonDefaults.itemShape(index = 0, count = 2)) { Text("Boys") }
                SegmentedButton(selected = ageGroup == 1, onClick = { ageGroup = 1 }, shape = SegmentedButtonDefaults.itemShape(index = 1, count = 2)) { Text("Girls") }
            }

            Spacer(modifier = Modifier.height(12.dp))

            SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
                SegmentedButton(selected = unit == 0, onClick = { unit = 0 }, shape = SegmentedButtonDefaults.itemShape(index = 0, count = 2)) { Text("Inches") }
                SegmentedButton(selected = unit == 1, onClick = { unit = 1 }, shape = SegmentedButtonDefaults.itemShape(index = 1, count = 2)) { Text("CM") }
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(value = age, onValueChange = { age = it }, label = { Text("Age (Years)") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(value = height, onValueChange = { height = it }, label = { Text("Height") }, modifier = Modifier.fillMaxWidth(), singleLine = true)

            val aVal = age.toDoubleOrNull() ?: 0.0
            val hVal = height.toDoubleOrNull() ?: 0.0

            if (aVal > 0 || hVal > 0) {
                val finalHeight = if (hVal > 0) {
                    if (unit == 1) hVal / 2.54 else hVal
                } else {
                    // Estimate height based on average age development curve: base 20 inches at birth + ~3 inches per year
                    20.0 + (aVal * 3.2)
                }

                // Sizing based on Height in Inches
                val estimatedSize = when {
                    finalHeight < 24 -> "Newborn (0-3M)"
                    finalHeight < 28 -> "Infant (6-9M)"
                    finalHeight < 32 -> "Toddler (12-18M)"
                    finalHeight < 36 -> "2T"
                    finalHeight < 39 -> "3T"
                    finalHeight < 42 -> "4T"
                    finalHeight < 45 -> "Size 5 (S)"
                    finalHeight < 48 -> "Size 6 (S)"
                    finalHeight < 52 -> "Size 7-8 (M)"
                    finalHeight < 56 -> "Size 10 (M)"
                    finalHeight < 60 -> "Size 12-14 (L)"
                    else -> "Size 16 (XL)"
                }

                Spacer(modifier = Modifier.height(16.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Estimated Kids Size", style = MaterialTheme.typography.labelMedium)
                        Text(estimatedSize, style = MaterialTheme.typography.displaySmall, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Height: %.1f ${if(unit == 0) "in" else "cm"}".format(if(unit == 0) finalHeight else finalHeight * 2.54), style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
    }

    Spacer(modifier = Modifier.height(16.dp))
    KidsSizeGuideUI()
}

@Composable
fun KidsSizeGuideUI() {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Kids standard height guide", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column {
                    Text("Age", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodySmall)
                    Text("2 Years", style = MaterialTheme.typography.bodySmall)
                    Text("4 Years", style = MaterialTheme.typography.bodySmall)
                    Text("6 Years", style = MaterialTheme.typography.bodySmall)
                    Text("8 Years", style = MaterialTheme.typography.bodySmall)
                }
                Column {
                    Text("Avg. Height", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodySmall)
                    Text("33\" - 35\"", style = MaterialTheme.typography.bodySmall)
                    Text("39\" - 41\"", style = MaterialTheme.typography.bodySmall)
                    Text("45\" - 47\"", style = MaterialTheme.typography.bodySmall)
                    Text("50\" - 52\"", style = MaterialTheme.typography.bodySmall)
                }
                Column {
                    Text("Recommended Size", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodySmall)
                    Text("2T", style = MaterialTheme.typography.bodySmall)
                    Text("4T / XS", style = MaterialTheme.typography.bodySmall)
                    Text("6 / S", style = MaterialTheme.typography.bodySmall)
                    Text("8 / M", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}
