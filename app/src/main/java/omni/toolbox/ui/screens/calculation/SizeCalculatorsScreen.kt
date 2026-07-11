package omni.toolbox.ui.screens.calculation

import android.content.Context
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Straighten
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import omni.toolbox.ui.components.ToolScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SizeCalculatorsScreen(
    navController: NavHostController,
    initialTab: Int = 0,
    showTabs: Boolean = true
) {
    var selectedTab by remember { mutableIntStateOf(initialTab) }
    val tabTitles = listOf("Bra", "Underwear", "Dress", "Ring", "Arm", "Body Frame", "Kids", "Bangle")

    val screenTitle = if (showTabs) {
        "Fashion & Size Calculators"
    } else {
        when (selectedTab) {
            0 -> "Bra Size Calculator"
            1 -> "Underwear Size Calculator"
            2 -> "Dress Size Calculator"
            3 -> "Ring Size Calculator"
            4 -> "Arm & Sleeve Calculator"
            5 -> "Body Frame Calculator"
            6 -> "Kids Size Calculator"
            else -> "Bangle Size Calculator"
        }
    }

    ToolScreen(title = screenTitle, onBack = { navController.popBackStack() }) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            if (showTabs) {
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
                        6 -> KidsSizeCalculatorUI()
                        else -> BangleCalculatorUI()
                    }
                }
            }
        }
    }
}

// ----------------------------------------------------
// 10. BANGLE SIZE CALCULATOR (NEW WITH DYNAMIC VISUALS)
// ----------------------------------------------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BangleCalculatorUI() {
    val context = LocalContext.current
    val prefs = remember { context.getSharedPreferences("size_calculators", Context.MODE_PRIVATE) }

    var diameter by remember { mutableStateOf(prefs.getString("bangle_diameter", "57.2") ?: "51.8") }
    var calculationType by remember { mutableIntStateOf(prefs.getInt("bangle_calc_type", 0)) } // 0: Diameter, 1: Circumference, 2: Hand Width

    fun save() {
        prefs.edit().putString("bangle_diameter", diameter).putInt("bangle_calc_type", calculationType).apply()
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.15f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("Bangle Size Calculator", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                IconButton(onClick = {
                    diameter = "57.2"
                    calculationType = 0
                    save()
                }) {
                    Icon(Icons.Default.Refresh, contentDescription = "Reset")
                }
            }
            Spacer(modifier = Modifier.height(12.dp))

            SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
                SegmentedButton(selected = calculationType == 0, onClick = { calculationType = 0; save() }, shape = SegmentedButtonDefaults.itemShape(index = 0, count = 3)) { Text("Diameter", fontSize = 10.sp) }
                SegmentedButton(selected = calculationType == 1, onClick = { calculationType = 1; save() }, shape = SegmentedButtonDefaults.itemShape(index = 1, count = 3)) { Text("Circum.", fontSize = 10.sp) }
                SegmentedButton(selected = calculationType == 2, onClick = { calculationType = 2; save() }, shape = SegmentedButtonDefaults.itemShape(index = 2, count = 3)) { Text("Hand Width", fontSize = 10.sp) }
            }

            Spacer(modifier = Modifier.height(16.dp))

            val currentVal = diameter.toFloatOrNull() ?: 57.2f
            when (calculationType) {
                0 -> {
                    Text("Inner Diameter: $diameter mm", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
                    val diamSliderVal = currentVal.coerceIn(50.0f, 70.0f)
                    Slider(value = diamSliderVal, onValueChange = { diameter = "%.1f".format(it); save() }, valueRange = 50.0f..70.0f, modifier = Modifier.fillMaxWidth())
                }
                1 -> {
                    val initialCirc = (currentVal * Math.PI).toFloat()
                    Text("Inner Circumference: ${"%.1f".format(initialCirc)} mm", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
                    val circSliderVal = initialCirc.coerceIn(150.0f, 220.0f)
                    Slider(value = circSliderVal, onValueChange = { diameter = "%.1f".format(it / Math.PI); save() }, valueRange = 150.0f..220.0f, modifier = Modifier.fillMaxWidth())
                }
                else -> {
                    val initialHand = (currentVal + 10.0f)
                    Text("Hand Width: ${"%.1f".format(initialHand)} mm", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
                    val handSliderVal = initialHand.coerceIn(60.0f, 90.0f)
                    Slider(value = handSliderVal, onValueChange = { diameter = "%.1f".format(it - 10.0f); save() }, valueRange = 60.0f..90.0f, modifier = Modifier.fillMaxWidth())
                }
            }

            val innerDiameterCalculated = diameter.toDoubleOrNull() ?: 57.2

            if (innerDiameterCalculated > 0) {
                val circVal = innerDiameterCalculated * Math.PI

                // Standard Indian bangle sizes: diameter maps to e.g. 2-2, 2-4, 2-6, 2-8, 2-10
                val indianSize = when {
                    innerDiameterCalculated < 52.4 -> "2-2 (XS)"
                    innerDiameterCalculated < 55.6 -> "2-4 (S)"
                    innerDiameterCalculated < 58.7 -> "2-6 (M)"
                    innerDiameterCalculated < 61.9 -> "2-8 (L)"
                    innerDiameterCalculated < 65.1 -> "2-10 (XL)"
                    else -> "2-12 (XXL)"
                }

                val usSize = when {
                    innerDiameterCalculated < 52.4 -> "7.0"
                    innerDiameterCalculated < 55.6 -> "7.5"
                    innerDiameterCalculated < 58.7 -> "8.0"
                    innerDiameterCalculated < 61.9 -> "8.5"
                    innerDiameterCalculated < 65.1 -> "9.0"
                    else -> "9.5+"
                }

                Spacer(modifier = Modifier.height(16.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Estimated Bangle Sizes", style = MaterialTheme.typography.labelMedium)
                        Text("Indian: $indianSize  |  US/Intl: $usSize", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Inner Diameter: ${"%.1f".format(innerDiameterCalculated)} mm  |  Circumference: ${"%.1f".format(circVal)} mm", style = MaterialTheme.typography.bodySmall)
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))
                Text("Interactive Bangle Circle Matcher:", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                Text("Place your physical bangle or wrist here and adjust above to match:", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)

                Box(
                    modifier = Modifier.fillMaxWidth().height(160.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(modifier = Modifier.size(140.dp)) {
                        // Drawing dynamic bangle circle with custom scaling factor of 4.2f
                        val scaleFactor = 4.2f
                        val radiusPx = (innerDiameterCalculated.toFloat() / 2f) * scaleFactor
                        drawCircle(
                            color = Color(0xFFE91E63),
                            radius = radiusPx,
                            style = Stroke(width = 6f)
                        )
                        drawCircle(
                            color = Color(0xFFE91E63).copy(alpha = 0.12f),
                            radius = radiusPx
                        )
                    }
                    Text("${"%.1f".format(innerDiameterCalculated)} mm", color = Color(0xFFE91E63), fontWeight = FontWeight.ExtraBold, fontSize = 16.sp)
                }
            }
        }
    }
}

// ----------------------------------------------------
// 1. BRA SIZE CALCULATOR (IMPROVED)
// ----------------------------------------------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BraCalculatorUI() {
    val context = LocalContext.current
    val prefs = remember { context.getSharedPreferences("size_calculators", Context.MODE_PRIVATE) }

    var underbust by remember { mutableStateOf(prefs.getString("bra_underbust", "34") ?: "34") }
    var bust by remember { mutableStateOf(prefs.getString("bra_bust", "36") ?: "36") }
    var unit by remember { mutableIntStateOf(prefs.getInt("bra_unit", 0)) } // 0: Inches, 1: CM

    fun save() {
        prefs.edit().putString("bra_underbust", underbust).putString("bra_bust", bust).putInt("bra_unit", unit).apply()
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.15f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("Bra Size Calculator", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                IconButton(onClick = {
                    underbust = "34"
                    bust = "36"
                    unit = 0
                    save()
                }) {
                    Icon(Icons.Default.Refresh, contentDescription = "Reset")
                }
            }
            Spacer(modifier = Modifier.height(12.dp))

            SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
                SegmentedButton(selected = unit == 0, onClick = { unit = 0; save() }, shape = SegmentedButtonDefaults.itemShape(index = 0, count = 2)) { Text("Inches") }
                SegmentedButton(selected = unit == 1, onClick = { unit = 1; save() }, shape = SegmentedButtonDefaults.itemShape(index = 1, count = 2)) { Text("CM") }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Underbust (Band): $underbust ${if(unit == 0) "in" else "cm"}", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
            val underMin = if(unit == 0) 24f else 60f
            val underMax = if(unit == 0) 48f else 120f
            val underSliderVal = (underbust.toFloatOrNull() ?: 34f).coerceIn(underMin, underMax)
            Slider(
                value = underSliderVal,
                onValueChange = {
                    underbust = "%.1f".format(it)
                    save()
                },
                valueRange = underMin..underMax,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text("Bust Size: $bust ${if(unit == 0) "in" else "cm"}", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
            val bustMin = if(unit == 0) 26f else 65f
            val bustMax = if(unit == 0) 60f else 150f
            val bustSliderVal = (bust.toFloatOrNull() ?: 36f).coerceIn(bustMin, bustMax)
            Slider(
                value = bustSliderVal,
                onValueChange = {
                    bust = "%.1f".format(it)
                    save()
                },
                valueRange = bustMin..bustMax,
                modifier = Modifier.fillMaxWidth()
            )

            val uValue = underbust.toDoubleOrNull() ?: 0.0
            val bValue = bust.toDoubleOrNull() ?: 0.0

            if (uValue > 0 && bValue > 0) {
                val uInches = if (unit == 1) uValue / 2.54 else uValue
                val bInches = if (unit == 1) bValue / 2.54 else bValue

                // Traditional band calculation (+4/+5 method)
                val bandTrad = if (uInches.toInt() % 2 == 0) uInches.toInt() + 4 else uInches.toInt() + 5
                val diffTrad = bInches - bandTrad

                // Modern band calculation (direct underbust)
                val bandMod = if (uInches.roundToInt() % 2 == 0) uInches.roundToInt() else uInches.roundToInt() + 1
                val diffMod = bInches - uInches

                fun getCup(diff: Double): String = when {
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

                val cupTrad = getCup(diffTrad)
                val cupMod = getCup(diffMod)

                Spacer(modifier = Modifier.height(16.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Traditional Fitting (US/UK)", style = MaterialTheme.typography.labelMedium)
                        Text("$bandTrad$cupTrad", style = MaterialTheme.typography.displaySmall, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Modern Comfort-Fit: $bandMod$cupMod", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium)
                        Text("EU Band Size: ${(uInches * 2.54 / 5).toInt() * 5}", style = MaterialTheme.typography.bodySmall)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                Text("Sister Sizes (Alternate Fitting):", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                Text("Tight band option: ${bandTrad - 2}${nextCup(cupTrad)}  |  Loose band option: ${bandTrad + 2}${prevCup(cupTrad)}", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }

    Spacer(modifier = Modifier.height(16.dp))
    BraSizeGuideUI()
}

fun Double.roundToInt(): Int = Math.round(this).toInt()

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
// 2. UNDERWEAR SIZE CALCULATOR (IMPROVED)
// ----------------------------------------------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnderwearCalculatorUI() {
    val context = LocalContext.current
    val prefs = remember { context.getSharedPreferences("size_calculators", Context.MODE_PRIVATE) }

    var waist by remember { mutableStateOf(prefs.getString("underwear_waist", "32") ?: "32") }
    var hips by remember { mutableStateOf(prefs.getString("underwear_hips", "38") ?: "38") }
    var genderIndex by remember { mutableIntStateOf(prefs.getInt("underwear_gender", 0)) } // 0: Men, 1: Women
    var unit by remember { mutableIntStateOf(prefs.getInt("underwear_unit", 0)) } // 0: Inches, 1: CM

    fun save() {
        prefs.edit().putString("underwear_waist", waist).putString("underwear_hips", hips).putInt("underwear_gender", genderIndex).putInt("underwear_unit", unit).apply()
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.15f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("Underwear Size Calculator", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                IconButton(onClick = {
                    waist = "32"
                    hips = "38"
                    genderIndex = 0
                    unit = 0
                    save()
                }) {
                    Icon(Icons.Default.Refresh, contentDescription = "Reset")
                }
            }
            Spacer(modifier = Modifier.height(12.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = { genderIndex = 0; save() },
                    colors = ButtonDefaults.buttonColors(containerColor = if (genderIndex == 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Men", color = if (genderIndex == 0) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant)
                }
                Button(
                    onClick = { genderIndex = 1; save() },
                    colors = ButtonDefaults.buttonColors(containerColor = if (genderIndex == 1) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Women", color = if (genderIndex == 1) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
                SegmentedButton(selected = unit == 0, onClick = { unit = 0; save() }, shape = SegmentedButtonDefaults.itemShape(index = 0, count = 2)) { Text("Inches") }
                SegmentedButton(selected = unit == 1, onClick = { unit = 1; save() }, shape = SegmentedButtonDefaults.itemShape(index = 1, count = 2)) { Text("CM") }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Waist: $waist ${if(unit == 0) "in" else "cm"}", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
            val waistMin = if(unit == 0) 22f else 55f
            val waistMax = if(unit == 0) 54f else 140f
            val waistSliderVal = (waist.toFloatOrNull() ?: 32f).coerceIn(waistMin, waistMax)
            Slider(
                value = waistSliderVal,
                onValueChange = {
                    waist = "%.1f".format(it)
                    save()
                },
                valueRange = waistMin..waistMax,
                modifier = Modifier.fillMaxWidth()
            )

            if (genderIndex == 1) {
                Spacer(modifier = Modifier.height(8.dp))
                Text("Hips: $hips ${if(unit == 0) "in" else "cm"}", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
                val hipsMin = if(unit == 0) 24f else 60f
                val hipsMax = if(unit == 0) 64f else 160f
                val hipsSliderVal = (hips.toFloatOrNull() ?: 38f).coerceIn(hipsMin, hipsMax)
                Slider(
                    value = hipsSliderVal,
                    onValueChange = {
                        hips = "%.1f".format(it)
                        save()
                    },
                    valueRange = hipsMin..hipsMax,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            val wValue = waist.toDoubleOrNull() ?: 0.0
            val hValue = hips.toDoubleOrNull() ?: 0.0

            if (wValue > 0) {
                val wInches = if (unit == 1) wValue / 2.54 else wValue
                val hInches = if (unit == 1) hValue / 2.54 else hValue

                val (calculatedSize, fitDesc) = if (genderIndex == 0) {
                    // Men Underwear Sizing
                    val size = when {
                        wInches < 28 -> "XS"
                        wInches < 31 -> "S"
                        wInches < 35 -> "M"
                        wInches < 39 -> "L"
                        wInches < 43 -> "XL"
                        else -> "XXL"
                    }
                    val fit = "For compressive sports fit, choose ${if(size == "XS") "XS" else "one size down"}. For standard lounge fit, select $size."
                    Pair(size, fit)
                } else {
                    // Women Underwear Sizing
                    val baseMetric = if (hInches > 0) hInches else wInches + 10
                    val size = when {
                        baseMetric < 34 -> "XXS (US 0)"
                        baseMetric < 36 -> "XS (US 2)"
                        baseMetric < 38 -> "S (US 4-6)"
                        baseMetric < 40 -> "M (US 8-10)"
                        baseMetric < 43 -> "L (US 12-14)"
                        baseMetric < 46 -> "XL (US 16)"
                        else -> "XXL (US 18+)"
                    }
                    val fit = "Primarily determined by Hip circumference. Suggest comfort-fit: $size."
                    Pair(size, fit)
                }

                Spacer(modifier = Modifier.height(16.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Estimated Underwear Size", style = MaterialTheme.typography.labelMedium)
                        Text(calculatedSize, style = MaterialTheme.typography.displaySmall, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(fitDesc, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f))
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
// 3. DRESS SIZE CALCULATOR (IMPROVED)
// ----------------------------------------------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DressCalculatorUI() {
    val context = LocalContext.current
    val prefs = remember { context.getSharedPreferences("size_calculators", Context.MODE_PRIVATE) }

    var bust by remember { mutableStateOf(prefs.getString("dress_bust", "34") ?: "34") }
    var waist by remember { mutableStateOf(prefs.getString("dress_waist", "27") ?: "27") }
    var hips by remember { mutableStateOf(prefs.getString("dress_hips", "37") ?: "37") }
    var unit by remember { mutableIntStateOf(prefs.getInt("dress_unit", 0)) } // 0: Inches, 1: CM

    fun save() {
        prefs.edit().putString("dress_bust", bust).putString("dress_waist", waist).putString("dress_hips", hips).putInt("dress_unit", unit).apply()
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.15f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("Dress Size Calculator", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                IconButton(onClick = {
                    bust = "34"
                    waist = "27"
                    hips = "37"
                    unit = 0
                    save()
                }) {
                    Icon(Icons.Default.Refresh, contentDescription = "Reset")
                }
            }
            Spacer(modifier = Modifier.height(12.dp))

            SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
                SegmentedButton(selected = unit == 0, onClick = { unit = 0; save() }, shape = SegmentedButtonDefaults.itemShape(index = 0, count = 2)) { Text("Inches") }
                SegmentedButton(selected = unit == 1, onClick = { unit = 1; save() }, shape = SegmentedButtonDefaults.itemShape(index = 1, count = 2)) { Text("CM") }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Bust: $bust ${if(unit == 0) "in" else "cm"}", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
            val bustMin = if(unit == 0) 26f else 65f
            val bustMax = if(unit == 0) 56f else 140f
            val bustSliderVal = (bust.toFloatOrNull() ?: 34f).coerceIn(bustMin, bustMax)
            Slider(value = bustSliderVal, onValueChange = { bust = "%.1f".format(it); save() }, valueRange = bustMin..bustMax, modifier = Modifier.fillMaxWidth())

            Spacer(modifier = Modifier.height(8.dp))

            Text("Waist: $waist ${if(unit == 0) "in" else "cm"}", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
            val waistMin = if(unit == 0) 20f else 50f
            val waistMax = if(unit == 0) 50f else 130f
            val waistSliderVal = (waist.toFloatOrNull() ?: 27f).coerceIn(waistMin, waistMax)
            Slider(value = waistSliderVal, onValueChange = { waist = "%.1f".format(it); save() }, valueRange = waistMin..waistMax, modifier = Modifier.fillMaxWidth())

            Spacer(modifier = Modifier.height(8.dp))

            Text("Hips: $hips ${if(unit == 0) "in" else "cm"}", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
            val hipsMin = if(unit == 0) 28f else 70f
            val hipsMax = if(unit == 0) 60f else 150f
            val hipsSliderVal = (hips.toFloatOrNull() ?: 37f).coerceIn(hipsMin, hipsMax)
            Slider(value = hipsSliderVal, onValueChange = { hips = "%.1f".format(it); save() }, valueRange = hipsMin..hipsMax, modifier = Modifier.fillMaxWidth())

            val bValue = bust.toDoubleOrNull() ?: 0.0
            val wValue = waist.toDoubleOrNull() ?: 0.0
            val hValue = hips.toDoubleOrNull() ?: 0.0

            if (bValue > 0 && wValue > 0 && hValue > 0) {
                val bIn = if (unit == 1) bValue / 2.54 else bValue
                val wIn = if (unit == 1) wValue / 2.54 else wValue
                val hIn = if (unit == 1) hValue / 2.54 else hValue

                // Comprehensive dress sizing algorithm with multi-regional support
                val (sizeVal, regionalMap) = when {
                    bIn <= 31 && wIn <= 23 && hIn <= 33 -> Pair("XXS (US 0 / UK 4)", "EU: 30 | IT: 34 | FR: 32 | JP: 3")
                    bIn <= 33 && wIn <= 25 && hIn <= 35 -> Pair("XS (US 2 / UK 6)", "EU: 32 | IT: 36 | FR: 34 | JP: 5")
                    bIn <= 35 && wIn <= 27 && hIn <= 37 -> Pair("S (US 4-6 / UK 8-10)", "EU: 34-36 | IT: 38-40 | FR: 36-38 | JP: 7-9")
                    bIn <= 37 && wIn <= 29 && hIn <= 39 -> Pair("M (US 8-10 / UK 12-14)", "EU: 38-40 | IT: 42-44 | FR: 40-42 | JP: 11-13")
                    bIn <= 40 && wIn <= 32 && hIn <= 42 -> Pair("L (US 12-14 / UK 16-18)", "EU: 42-44 | IT: 46-48 | FR: 44-46 | JP: 15-17")
                    bIn <= 43 && wIn <= 35 && hIn <= 45 -> Pair("XL (US 16 / UK 20)", "EU: 46 | IT: 50 | FR: 48 | JP: 19")
                    else -> Pair("XXL (US 18+ / UK 22+)", "EU: 48+ | IT: 52+ | FR: 50+")
                }

                Spacer(modifier = Modifier.height(16.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Estimated Dress Size", style = MaterialTheme.typography.labelMedium)
                        Text(sizeVal, style = MaterialTheme.typography.displaySmall, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(regionalMap, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Medium)
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
// 4. RING SIZE CALCULATOR (IMPROVED - INTERACTIVE)
// ----------------------------------------------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RingCalculatorUI() {
    val context = LocalContext.current
    val prefs = remember { context.getSharedPreferences("size_calculators", Context.MODE_PRIVATE) }

    var circumference by remember { mutableStateOf(prefs.getString("ring_circumference", "51.8") ?: "51.8") }
    var diameter by remember { mutableStateOf(prefs.getString("ring_diameter", "16.5") ?: "16.5") }
    var calculationType by remember { mutableIntStateOf(prefs.getInt("ring_calc_type", 0)) } // 0: Circumference, 1: Diameter

    fun save() {
        prefs.edit().putString("ring_circumference", circumference).putString("ring_diameter", diameter).putInt("ring_calc_type", calculationType).apply()
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.15f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("Ring Size Calculator", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                IconButton(onClick = {
                    circumference = "51.8"
                    diameter = "16.5"
                    calculationType = 0
                    save()
                }) {
                    Icon(Icons.Default.Refresh, contentDescription = "Reset")
                }
            }
            Spacer(modifier = Modifier.height(12.dp))

            SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
                SegmentedButton(selected = calculationType == 0, onClick = { calculationType = 0; save() }, shape = SegmentedButtonDefaults.itemShape(index = 0, count = 2)) { Text("Circumference (mm)") }
                SegmentedButton(selected = calculationType == 1, onClick = { calculationType = 1; save() }, shape = SegmentedButtonDefaults.itemShape(index = 1, count = 2)) { Text("Diameter (mm)") }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (calculationType == 0) {
                Text("Circumference: $circumference mm", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
                val circSliderVal = (circumference.toFloatOrNull() ?: 51.8f).coerceIn(36.5f, 75f)
                Slider(value = circSliderVal, onValueChange = { circumference = "%.1f".format(it); save() }, valueRange = 36.5f..75f, modifier = Modifier.fillMaxWidth())
            } else {
                Text("Inner Diameter: $diameter mm", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
                val diamSliderVal = (diameter.toFloatOrNull() ?: 16.5f).coerceIn(11.6f, 24f)
                Slider(value = diamSliderVal, onValueChange = { diameter = "%.1f".format(it); save() }, valueRange = 11.6f..24f, modifier = Modifier.fillMaxWidth())
            }

            val circVal = if (calculationType == 0) {
                circumference.toDoubleOrNull() ?: 0.0
            } else {
                (diameter.toDoubleOrNull() ?: 0.0) * Math.PI
            }

            if (circVal > 0) {
                // Formula: Size = (Circumference - 36.5) / 2.58
                val rawSize = (circVal - 36.5) / 2.58
                val roundedSize = (Math.round(rawSize * 2.0) / 2.0).coerceIn(1.0, 15.0)
                val innerDiameterCalculated = circVal / Math.PI

                Spacer(modifier = Modifier.height(16.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Estimated US Ring Size", style = MaterialTheme.typography.labelMedium)
                        Text("Size $roundedSize", style = MaterialTheme.typography.displaySmall, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("EU Circumference: ${circVal.roundToInt()} mm  |  UK: ${ukRingLetter(roundedSize)}", style = MaterialTheme.typography.bodySmall)
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))
                Text("Interactive Ring Matcher Circle:", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                Text("Place your physical ring on the screen and adjust above to match the circle size:", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)

                Box(
                    modifier = Modifier.fillMaxWidth().height(160.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(modifier = Modifier.size(140.dp)) {
                        // Visual scaling factor: 4f gives a nice representation
                        val scaleFactor = 4.2f
                        val radiusPx = (innerDiameterCalculated.toFloat() / 2f) * scaleFactor
                        drawCircle(
                            color = Color(0xFFE91E63),
                            radius = radiusPx,
                            style = Stroke(width = 6f)
                        )
                        drawCircle(
                            color = Color(0xFFE91E63).copy(alpha = 0.12f),
                            radius = radiusPx
                        )
                    }
                    Text("${"%.1f".format(innerDiameterCalculated)} mm", color = Color(0xFFE91E63), fontWeight = FontWeight.ExtraBold, fontSize = 16.sp)
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
// 5. ARM / SLEEVE SIZE CALCULATOR (IMPROVED)
// ----------------------------------------------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArmCalculatorUI() {
    val context = LocalContext.current
    val prefs = remember { context.getSharedPreferences("size_calculators", Context.MODE_PRIVATE) }

    var height by remember { mutableStateOf(prefs.getString("arm_height", "68") ?: "68") }
    var ageGroup by remember { mutableIntStateOf(prefs.getInt("arm_age_group", 0)) } // 0: Men, 1: Women, 2: Boys, 3: Girls
    var unit by remember { mutableIntStateOf(prefs.getInt("arm_unit", 0)) } // 0: Inches, 1: CM

    fun save() {
        prefs.edit().putString("arm_height", height).putInt("arm_age_group", ageGroup).putInt("arm_unit", unit).apply()
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.15f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("Arm & Sleeve Size Calculator", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                IconButton(onClick = {
                    height = "68"
                    ageGroup = 0
                    unit = 0
                    save()
                }) {
                    Icon(Icons.Default.Refresh, contentDescription = "Reset")
                }
            }
            Spacer(modifier = Modifier.height(12.dp))

            ScrollableTabRow(
                selectedTabIndex = ageGroup,
                edgePadding = 0.dp,
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.primary
            ) {
                listOf("Men", "Women", "Boys", "Girls").forEachIndexed { index, name ->
                    Tab(selected = ageGroup == index, onClick = { ageGroup = index; save() }, text = { Text(name, fontSize = 12.sp) })
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
                SegmentedButton(selected = unit == 0, onClick = { unit = 0; save() }, shape = SegmentedButtonDefaults.itemShape(index = 0, count = 2)) { Text("Inches") }
                SegmentedButton(selected = unit == 1, onClick = { unit = 1; save() }, shape = SegmentedButtonDefaults.itemShape(index = 1, count = 2)) { Text("CM") }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Total Height: $height ${if(unit == 0) "in" else "cm"}", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
            val heightMin = if(unit == 0) 30f else 80f
            val heightMax = if(unit == 0) 90f else 230f
            val heightSliderVal = (height.toFloatOrNull() ?: 68f).coerceIn(heightMin, heightMax)
            Slider(value = heightSliderVal, onValueChange = { height = "%.1f".format(it); save() }, valueRange = heightMin..heightMax, modifier = Modifier.fillMaxWidth())

            val hVal = height.toDoubleOrNull() ?: 0.0

            if (hVal > 0) {
                val hIn = if (unit == 1) hVal / 2.54 else hVal
                val estimatedSleeve = hIn * 0.48

                val sizeCode = if (ageGroup == 0 || ageGroup == 1) {
                    when {
                        estimatedSleeve < 30.5 -> "Short (XS / S)"
                        estimatedSleeve < 32.5 -> "Regular (M)"
                        estimatedSleeve < 34.5 -> "Regular (L)"
                        else -> "Long (XL / XXL)"
                    }
                } else {
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
// 6. BODY MEASUREMENTS & FRAME CALCULATOR (IMPROVED)
// ----------------------------------------------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BodyMeasurementsUI() {
    val context = LocalContext.current
    val prefs = remember { context.getSharedPreferences("size_calculators", Context.MODE_PRIVATE) }

    var height by remember { mutableStateOf(prefs.getString("body_height", "70") ?: "70") }
    var wrist by remember { mutableStateOf(prefs.getString("body_wrist", "7") ?: "7") }
    var genderIndex by remember { mutableIntStateOf(prefs.getInt("body_gender", 0)) } // 0: Men, 1: Women
    var unit by remember { mutableIntStateOf(prefs.getInt("body_unit", 0)) } // 0: Inches, 1: CM

    fun save() {
        prefs.edit().putString("body_height", height).putString("body_wrist", wrist).putInt("body_gender", genderIndex).putInt("body_unit", unit).apply()
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.15f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("Body Frame & Proportions", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                IconButton(onClick = {
                    height = "70"
                    wrist = "7"
                    genderIndex = 0
                    unit = 0
                    save()
                }) {
                    Icon(Icons.Default.Refresh, contentDescription = "Reset")
                }
            }
            Spacer(modifier = Modifier.height(12.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = { genderIndex = 0; save() },
                    colors = ButtonDefaults.buttonColors(containerColor = if (genderIndex == 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Men", color = if (genderIndex == 0) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant)
                }
                Button(
                    onClick = { genderIndex = 1; save() },
                    colors = ButtonDefaults.buttonColors(containerColor = if (genderIndex == 1) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Women", color = if (genderIndex == 1) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
                SegmentedButton(selected = unit == 0, onClick = { unit = 0; save() }, shape = SegmentedButtonDefaults.itemShape(index = 0, count = 2)) { Text("Inches") }
                SegmentedButton(selected = unit == 1, onClick = { unit = 1; save() }, shape = SegmentedButtonDefaults.itemShape(index = 1, count = 2)) { Text("CM") }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Height: $height ${if(unit == 0) "in" else "cm"}", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
            val heightMin = if(unit == 0) 40f else 100f
            val heightMax = if(unit == 0) 90f else 230f
            val heightSliderVal = (height.toFloatOrNull() ?: 70f).coerceIn(heightMin, heightMax)
            Slider(value = heightSliderVal, onValueChange = { height = "%.1f".format(it); save() }, valueRange = heightMin..heightMax, modifier = Modifier.fillMaxWidth())

            Spacer(modifier = Modifier.height(8.dp))

            Text("Wrist Circumference: $wrist ${if(unit == 0) "in" else "cm"}", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
            val wristMin = if(unit == 0) 4f else 10f
            val wristMax = if(unit == 0) 11f else 28f
            val wristSliderVal = (wrist.toFloatOrNull() ?: 7f).coerceIn(wristMin, wristMax)
            Slider(value = wristSliderVal, onValueChange = { wrist = "%.1f".format(it); save() }, valueRange = wristMin..wristMax, modifier = Modifier.fillMaxWidth())

            val hVal = height.toDoubleOrNull() ?: 0.0
            val wVal = wrist.toDoubleOrNull() ?: 0.0

            if (hVal > 0 && wVal > 0) {
                val hIn = if (unit == 1) hVal / 2.54 else hVal
                val wIn = if (unit == 1) wVal / 2.54 else wVal

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

                val frameDetails = when (frameSize) {
                    "Small Frame" -> "Sleek, minimalist profiles & lightweight materials suit you best."
                    "Medium Frame" -> "Classic and standard fits hang perfectly on your proportional build."
                    else -> "Robust, unstructured silhouettes & heavy fabrics like denim complement your frame."
                }

                Spacer(modifier = Modifier.height(16.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Assessed Body Frame Size", style = MaterialTheme.typography.labelMedium)
                        Text(frameSize, style = MaterialTheme.typography.displaySmall, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(frameDetails, style = MaterialTheme.typography.bodySmall, textAlign = TextAlign.Center, color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.85f))
                        Text("Height-to-Wrist Ratio: %.2f".format(ratio), style = MaterialTheme.typography.bodySmall, modifier = Modifier.padding(top = 8.dp))
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
// 7. KIDS SIZE CALCULATOR (IMPROVED - HEIGHT & WEIGHT)
// ----------------------------------------------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KidsSizeCalculatorUI() {
    val context = LocalContext.current
    val prefs = remember { context.getSharedPreferences("size_calculators", Context.MODE_PRIVATE) }

    var ageGroup by remember { mutableIntStateOf(prefs.getInt("kids_gender", 0)) } // 0: Boys, 1: Girls
    var age by remember { mutableStateOf(prefs.getString("kids_age", "4") ?: "4") }
    var height by remember { mutableStateOf(prefs.getString("kids_height", "40") ?: "40") }
    var weight by remember { mutableStateOf(prefs.getString("kids_weight", "35") ?: "35") }
    var unit by remember { mutableIntStateOf(prefs.getInt("kids_unit", 0)) } // 0: Inches/Lbs, 1: CM/Kgs

    fun save() {
        prefs.edit().putString("kids_age", age).putString("kids_height", height).putString("kids_weight", weight).putInt("kids_gender", ageGroup).putInt("kids_unit", unit).apply()
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.15f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("Kids Growth & Clothing Size", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                IconButton(onClick = {
                    age = "4"
                    height = "40"
                    weight = "35"
                    ageGroup = 0
                    unit = 0
                    save()
                }) {
                    Icon(Icons.Default.Refresh, contentDescription = "Reset")
                }
            }
            Spacer(modifier = Modifier.height(12.dp))

            SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
                SegmentedButton(selected = ageGroup == 0, onClick = { ageGroup = 0; save() }, shape = SegmentedButtonDefaults.itemShape(index = 0, count = 2)) { Text("Boys") }
                SegmentedButton(selected = ageGroup == 1, onClick = { ageGroup = 1; save() }, shape = SegmentedButtonDefaults.itemShape(index = 1, count = 2)) { Text("Girls") }
            }

            Spacer(modifier = Modifier.height(12.dp))

            SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
                SegmentedButton(selected = unit == 0, onClick = { unit = 0; save() }, shape = SegmentedButtonDefaults.itemShape(index = 0, count = 2)) { Text("In/Lbs") }
                SegmentedButton(selected = unit == 1, onClick = { unit = 1; save() }, shape = SegmentedButtonDefaults.itemShape(index = 1, count = 2)) { Text("Cm/Kgs") }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Age: $age Years", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
            val ageSliderVal = (age.toFloatOrNull() ?: 4f).coerceIn(0f, 16f)
            Slider(value = ageSliderVal, onValueChange = { age = "%.1f".format(it); save() }, valueRange = 0f..16f, modifier = Modifier.fillMaxWidth())

            Spacer(modifier = Modifier.height(8.dp))

            Text("Height: $height ${if(unit == 0) "in" else "cm"}", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
            val heightMin = if(unit == 0) 18f else 45f
            val heightMax = if(unit == 0) 70f else 180f
            val heightSliderVal = (height.toFloatOrNull() ?: 40f).coerceIn(heightMin, heightMax)
            Slider(value = heightSliderVal, onValueChange = { height = "%.1f".format(it); save() }, valueRange = heightMin..heightMax, modifier = Modifier.fillMaxWidth())

            Spacer(modifier = Modifier.height(8.dp))

            Text("Weight: $weight ${if(unit == 0) "lbs" else "kgs"}", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
            val weightMin = if(unit == 0) 5f else 2f
            val weightMax = if(unit == 0) 150f else 70f
            val weightSliderVal = (weight.toFloatOrNull() ?: 35f).coerceIn(weightMin, weightMax)
            Slider(value = weightSliderVal, onValueChange = { weight = "%.1f".format(it); save() }, valueRange = weightMin..weightMax, modifier = Modifier.fillMaxWidth())

            val aVal = age.toDoubleOrNull() ?: 0.0
            val hVal = height.toDoubleOrNull() ?: 0.0
            val wVal = weight.toDoubleOrNull() ?: 0.0

            if (aVal > 0 || hVal > 0 || wVal > 0) {
                val finalHeight = if (hVal > 0) {
                    if (unit == 1) hVal / 2.54 else hVal
                } else {
                    20.0 + (aVal * 3.2)
                }

                val finalWeight = if (wVal > 0) {
                    if (unit == 1) wVal * 2.20462 else wVal
                } else {
                    7.0 + (aVal * 5.5)
                }

                // Sizing based on Height and Weight unified assessment
                val estimatedSize = when {
                    finalHeight < 24 || finalWeight < 12 -> "Newborn (0-3M)"
                    finalHeight < 28 || finalWeight < 18 -> "Infant (6-9M)"
                    finalHeight < 32 || finalWeight < 25 -> "Toddler (12-18M)"
                    finalHeight < 36 || finalWeight < 30 -> "2T"
                    finalHeight < 39 || finalWeight < 34 -> "3T"
                    finalHeight < 42 || finalWeight < 38 -> "4T"
                    finalHeight < 45 || finalWeight < 44 -> "Size 5 (S)"
                    finalHeight < 48 || finalWeight < 50 -> "Size 6 (S)"
                    finalHeight < 52 || finalWeight < 62 -> "Size 7-8 (M)"
                    finalHeight < 56 || finalWeight < 75 -> "Size 10 (M)"
                    finalHeight < 60 || finalWeight < 95 -> "Size 12-14 (L)"
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
                        Text(
                            "Based on unified assessment of Height (%.1f in) and Weight (%.1f lbs)".format(finalHeight, finalWeight),
                            style = MaterialTheme.typography.bodySmall,
                            textAlign = TextAlign.Center
                        )
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
            Text("Kids standard growth guide", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
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
                    Text("Avg. Weight", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodySmall)
                    Text("24 - 29 lbs", style = MaterialTheme.typography.bodySmall)
                    Text("34 - 38 lbs", style = MaterialTheme.typography.bodySmall)
                    Text("44 - 49 lbs", style = MaterialTheme.typography.bodySmall)
                    Text("55 - 61 lbs", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}

// ----------------------------------------------------
// 8. SHOE SIZE CALCULATOR (NEW WITH VISUALS)
// ----------------------------------------------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoeSizeCalculatorUI() {
    val context = LocalContext.current
    val prefs = remember { context.getSharedPreferences("size_calculators", Context.MODE_PRIVATE) }

    var length by remember { mutableStateOf(prefs.getString("shoe_length", "10") ?: "10") }
    var gender by remember { mutableIntStateOf(prefs.getInt("shoe_gender", 0)) } // 0: Men, 1: Women, 2: Kids
    var unit by remember { mutableIntStateOf(prefs.getInt("shoe_unit", 0)) } // 0: Inches, 1: CM

    fun save() {
        prefs.edit().putString("shoe_length", length).putInt("shoe_gender", gender).putInt("shoe_unit", unit).apply()
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.15f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("Shoe Size Calculator", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                IconButton(onClick = {
                    length = "10"
                    gender = 0
                    unit = 0
                    save()
                }) {
                    Icon(Icons.Default.Refresh, contentDescription = "Reset")
                }
            }
            Spacer(modifier = Modifier.height(12.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf("Men", "Women", "Kids").forEachIndexed { index, name ->
                    Button(
                        onClick = { gender = index; save() },
                        colors = ButtonDefaults.buttonColors(containerColor = if (gender == index) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(name, color = if (gender == index) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 11.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
                SegmentedButton(selected = unit == 0, onClick = { unit = 0; save() }, shape = SegmentedButtonDefaults.itemShape(index = 0, count = 2)) { Text("Inches") }
                SegmentedButton(selected = unit == 1, onClick = { unit = 1; save() }, shape = SegmentedButtonDefaults.itemShape(index = 1, count = 2)) { Text("CM") }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Foot Length: $length ${if(unit == 0) "in" else "cm"}", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
            val minLen = if(unit == 0) 4f else 10f
            val maxLen = if(unit == 0) 14f else 35f
            val sliderVal = (length.toFloatOrNull() ?: 10f).coerceIn(minLen, maxLen)
            Slider(value = sliderVal, onValueChange = { length = "%.1f".format(it); save() }, valueRange = minLen..maxLen, modifier = Modifier.fillMaxWidth())

            val lenVal = length.toDoubleOrNull() ?: 0.0
            if (lenVal > 0) {
                val lenInches = if (unit == 1) lenVal / 2.54 else lenVal

                // Accurate shoe calculation formulas
                val (usSize, ukSize, euSize) = if (gender == 0) {
                    // Men
                    val us = (3.0 * lenInches) - 22.0
                    val roundedUs = (Math.round(us * 2.0) / 2.0).coerceIn(6.0, 16.0)
                    Triple("US %.1f".format(roundedUs), "UK %.1f".format(roundedUs - 0.5), "EU %d".format(Math.round(roundedUs + 33).toInt()))
                } else if (gender == 1) {
                    // Women
                    val us = (3.0 * lenInches) - 21.0
                    val roundedUs = (Math.round(us * 2.0) / 2.0).coerceIn(4.0, 12.0)
                    Triple("US %.1f".format(roundedUs), "UK %.1f".format(roundedUs - 2.0), "EU %d".format(Math.round(roundedUs + 31).toInt()))
                } else {
                    // Kids
                    val us = (3.0 * lenInches) - 11.6
                    val roundedUs = (Math.round(us * 2.0) / 2.0).coerceIn(1.0, 13.5)
                    Triple("US %.1f".format(roundedUs), "UK %.1f".format(roundedUs - 0.5), "EU %d".format(Math.round(roundedUs + 15).toInt()))
                }

                Spacer(modifier = Modifier.height(16.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Estimated Shoe Sizes", style = MaterialTheme.typography.labelMedium)
                        Text("$usSize | $ukSize | $euSize", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))
                Text("How to Measure Foot Length:", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)

                Box(
                    modifier = Modifier.fillMaxWidth().height(140.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(modifier = Modifier.size(120.dp)) {
                        // Draw Foot outline dynamically changing scale based on foot length input
                        val pathWidth = 60f
                        val pathHeight = (lenInches.toFloat() * 10f).coerceIn(60f, 130f)

                        // Foot Silhouette Visual representation
                        drawRoundRect(
                            color = Color(0xFF2196F3).copy(alpha = 0.2f),
                            size = size.copy(width = pathWidth, height = pathHeight),
                            cornerRadius = CornerRadius(24f, 24f)
                        )
                        drawRoundRect(
                            color = Color(0xFF2196F3),
                            size = size.copy(width = pathWidth, height = pathHeight),
                            cornerRadius = CornerRadius(24f, 24f),
                            style = Stroke(width = 4f)
                        )
                    }
                    Text("Foot Visual Model", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Medium, fontSize = 11.sp, modifier = Modifier.align(Alignment.BottomCenter))
                }
            }
        }
    }
}

// ----------------------------------------------------
// 9. HAT SIZE CALCULATOR (NEW WITH VISUALS)
// ----------------------------------------------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HatSizeCalculatorUI() {
    val context = LocalContext.current
    val prefs = remember { context.getSharedPreferences("size_calculators", Context.MODE_PRIVATE) }

    var circ by remember { mutableStateOf(prefs.getString("hat_circ", "22") ?: "22") }
    var unit by remember { mutableIntStateOf(prefs.getInt("hat_unit", 0)) } // 0: Inches, 1: CM

    fun save() {
        prefs.edit().putString("hat_circ", circ).putInt("hat_unit", unit).apply()
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.15f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("Hat / Headwear Size Calculator", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                IconButton(onClick = {
                    circ = "22"
                    unit = 0
                    save()
                }) {
                    Icon(Icons.Default.Refresh, contentDescription = "Reset")
                }
            }
            Spacer(modifier = Modifier.height(12.dp))

            SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
                SegmentedButton(selected = unit == 0, onClick = { unit = 0; save() }, shape = SegmentedButtonDefaults.itemShape(index = 0, count = 2)) { Text("Inches") }
                SegmentedButton(selected = unit == 1, onClick = { unit = 1; save() }, shape = SegmentedButtonDefaults.itemShape(index = 1, count = 2)) { Text("CM") }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Head Circumference: $circ ${if(unit == 0) "in" else "cm"}", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
            val minCirc = if(unit == 0) 18f else 45f
            val maxCirc = if(unit == 0) 26f else 66f
            val sliderVal = (circ.toFloatOrNull() ?: 22f).coerceIn(minCirc, maxCirc)
            Slider(value = sliderVal, onValueChange = { circ = "%.1f".format(it); save() }, valueRange = minCirc..maxCirc, modifier = Modifier.fillMaxWidth())

            val circVal = circ.toDoubleOrNull() ?: 0.0
            if (circVal > 0) {
                val circInches = if (unit == 1) circVal / 2.54 else circVal

                // Hat sizes
                val (usHatSize, intHatSize) = when {
                    circInches < 21.125 -> Pair("6 5/8", "XS")
                    circInches < 21.5 -> Pair("6 3/4", "S")
                    circInches < 21.875 -> Pair("6 7/8", "S")
                    circInches < 22.25 -> Pair("7", "M")
                    circInches < 22.625 -> Pair("7 1/8", "M")
                    circInches < 23.0 -> Pair("7 1/4", "L")
                    circInches < 23.375 -> Pair("7 3/8", "L")
                    circInches < 23.75 -> Pair("7 1/2", "XL")
                    circInches < 24.125 -> Pair("7 5/8", "XL")
                    else -> Pair("7 3/4+", "XXL")
                }

                Spacer(modifier = Modifier.height(16.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Estimated Hat Size", style = MaterialTheme.typography.labelMedium)
                        Text("US Hat Size: $usHatSize", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                        Text("International: $intHatSize", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium)
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))
                Box(
                    modifier = Modifier.fillMaxWidth().height(140.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(modifier = Modifier.size(100.dp)) {
                        // Drawing dynamic head contour
                        val scaleFactor = (circInches.toFloat() * 1.5f).coerceIn(30f, 60f)
                        drawCircle(
                            color = Color(0xFFFF9800).copy(alpha = 0.2f),
                            radius = scaleFactor
                        )
                        drawCircle(
                            color = Color(0xFFFF9800),
                            radius = scaleFactor,
                            style = Stroke(width = 4f)
                        )
                    }
                    Text("Hat Contour Model", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Medium, fontSize = 11.sp, modifier = Modifier.align(Alignment.BottomCenter))
                }
            }
        }
    }
}
