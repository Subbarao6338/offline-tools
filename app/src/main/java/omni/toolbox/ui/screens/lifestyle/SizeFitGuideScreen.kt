package omni.toolbox.ui.screens.lifestyle

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.horizontalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import omni.toolbox.ui.components.ToolScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SizeFitGuideScreen(navController: NavHostController, initialTab: Int = 0) {
    var selectedTab by remember { mutableIntStateOf(initialTab) }
    val tabs = listOf("Clothing", "Footwear", "Jewelry", "Calculators")

    ToolScreen(
        title = "Size & Fit Guide",
        onBack = { navController.popBackStack() }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            ScrollableTabRow(
                selectedTabIndex = selectedTab,
                edgePadding = 16.dp,
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.primary
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { Text(title) }
                    )
                }
            }

            when (selectedTab) {
                0 -> ClothingSizeModule()
                1 -> FootwearSizeModule()
                2 -> JewelrySizeModule()
                3 -> CalculatorsModule()
            }
        }
    }
}

@Composable
fun ClothingSizeModule() {
    val scrollState = rememberScrollState()
    Column(modifier = Modifier.fillMaxSize().verticalScroll(scrollState).padding(16.dp)) {
        Text("Women's International Sizes", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        SizeTable(
            headers = listOf("Alpha", "UK", "US", "EU"),
            rows = listOf(
                listOf("XS", "4", "0", "32"),
                listOf("XS", "6", "2", "34"),
                listOf("S", "8", "4", "36"),
                listOf("S", "10", "6", "38"),
                listOf("M", "12", "8", "40"),
                listOf("M", "14", "10", "42"),
                listOf("L", "16", "12", "44"),
                listOf("L", "18", "14", "46"),
                listOf("XL", "20", "16", "48"),
                listOf("XL", "22", "18", "50"),
                listOf("XXL", "24", "20", "52"),
                listOf("XXL", "26", "22", "54")
            )
        )

        Spacer(modifier = Modifier.height(24.dp))
        Text("Men's Tops (Chest)", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        SizeTable(
            headers = listOf("Alpha", "Chest (in)", "Chest (cm)"),
            rows = listOf(
                listOf("XS", "34-36", "86-91"),
                listOf("S", "36-38", "91-96"),
                listOf("M", "38-40", "96-101"),
                listOf("L", "40-42", "101-106"),
                listOf("XL", "42-44", "106-111"),
                listOf("XXL", "44-46", "111-116"),
                listOf("XXXL", "46-48", "116-121")
            )
        )

        Spacer(modifier = Modifier.height(24.dp))
        Text("Men's Trousers (Waist)", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        SizeTable(
            headers = listOf("Alpha", "Waist (in)", "Waist (cm)"),
            rows = listOf(
                listOf("XS", "26-28", "66-71"),
                listOf("S", "29-30", "73.5-76"),
                listOf("M", "31-32", "78.5-81"),
                listOf("L", "34-36", "86-91"),
                listOf("XL", "38", "96"),
                listOf("XXL", "40", "101")
            )
        )

        Spacer(modifier = Modifier.height(24.dp))
        Text("Kids (Age to Height)", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        SizeTable(
            headers = listOf("Age", "UK/US", "EU (cm)"),
            rows = listOf(
                listOf("4-5y", "5", "104-110"),
                listOf("5-6y", "6", "110-116"),
                listOf("6-7y", "6X-7", "116-122"),
                listOf("7-8y", "7-8", "122-128"),
                listOf("8-9y", "9-10", "128-134"),
                listOf("9-10y", "10", "134-140"),
                listOf("10-11y", "11", "140-146"),
                listOf("11-12y", "14", "146-152")
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FootwearSizeModule() {
    var selectedGender by remember { mutableIntStateOf(0) } // 0: Men, 1: Women, 2: Kids
    val scrollState = rememberScrollState()

    Column(modifier = Modifier.fillMaxSize().verticalScroll(scrollState).padding(16.dp)) {
        SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
            SegmentedButton(selected = selectedGender == 0, onClick = { selectedGender = 0 }, shape = SegmentedButtonDefaults.itemShape(index = 0, count = 3)) { Text("Men") }
            SegmentedButton(selected = selectedGender == 1, onClick = { selectedGender = 1 }, shape = SegmentedButtonDefaults.itemShape(index = 1, count = 3)) { Text("Women") }
            SegmentedButton(selected = selectedGender == 2, onClick = { selectedGender = 2 }, shape = SegmentedButtonDefaults.itemShape(index = 2, count = 3)) { Text("Kids") }
        }

        Spacer(modifier = Modifier.height(16.dp))

        val data = when (selectedGender) {
            0 -> listOf(
                listOf("25.4", "7", "6", "40"),
                listOf("25.8", "7.5", "6.5", "40.5"),
                listOf("26.2", "8", "7", "41"),
                listOf("26.7", "8.5", "7.5", "42"),
                listOf("27.1", "9", "8", "42.5"),
                listOf("27.5", "9.5", "8.5", "43"),
                listOf("27.9", "10", "9", "44"),
                listOf("28.3", "10.5", "9.5", "44.5"),
                listOf("28.8", "11", "10", "45")
            )
            1 -> listOf(
                listOf("22.5", "5", "3", "35"),
                listOf("23.0", "6", "4", "36"),
                listOf("23.5", "6.5", "4.5", "37"),
                listOf("23.8", "7", "5", "37.5"),
                listOf("24.2", "7.5", "5.5", "38"),
                listOf("24.6", "8", "6", "39"),
                listOf("25.1", "8.5", "6.5", "39.5"),
                listOf("25.4", "9", "7", "40"),
                listOf("25.9", "9.5", "7.5", "41")
            )
            else -> listOf(
                listOf("17.0", "10C", "9.5", "27"),
                listOf("17.8", "11C", "10.5", "28"),
                listOf("18.7", "12C", "11.5", "30"),
                listOf("19.5", "13C", "12.5", "31"),
                listOf("20.4", "1Y", "13.5", "32"),
                listOf("21.2", "2Y", "1.5", "33"),
                listOf("22.0", "3Y", "2.5", "35")
            )
        }

        SizeTable(
            headers = listOf("Length (cm)", "US", "UK", "EU"),
            rows = data
        )
    }
}

@Composable
fun JewelrySizeModule() {
    val scrollState = rememberScrollState()
    Column(modifier = Modifier.fillMaxSize().verticalScroll(scrollState).padding(16.dp)) {
        Text("International Ring Sizes", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        SizeTable(
            headers = listOf("Diam (mm)", "Circ (mm)", "US", "UK", "EU"),
            rows = listOf(
                listOf("14.1", "44.2", "3", "F", "44"),
                listOf("14.9", "46.8", "4", "H", "47"),
                listOf("15.7", "49.3", "5", "J 1/2", "49"),
                listOf("16.5", "51.9", "6", "M", "52"),
                listOf("17.3", "54.4", "7", "O", "54"),
                listOf("18.1", "57.0", "8", "Q", "57"),
                listOf("19.0", "59.5", "9", "S", "59"),
                listOf("19.8", "62.1", "10", "T 1/2", "62"),
                listOf("20.6", "64.6", "11", "V 1/2", "65"),
                listOf("21.4", "67.2", "12", "Y", "67")
            )
        )

        Spacer(modifier = Modifier.height(24.dp))
        Text("Bangle Sizing (Hand Circumference)", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        SizeTable(
            headers = listOf("Size", "Circ (in)", "Circ (cm)"),
            rows = listOf(
                listOf("Small", "7.5\"", "19.0"),
                listOf("Medium", "8.25\"", "21.0"),
                listOf("Large", "9.0\"", "22.9")
            )
        )
    }
}

@Composable
fun CalculatorsModule() {
    val scrollState = rememberScrollState()
    Column(modifier = Modifier.fillMaxSize().verticalScroll(scrollState).padding(16.dp)) {
        BraSizeCalculator()
        Spacer(modifier = Modifier.height(32.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(16.dp))
        BodyMeasurementGuide()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BraSizeCalculator() {
    var underbust by remember { mutableStateOf("") }
    var bust by remember { mutableStateOf("") }
    var unit by remember { mutableIntStateOf(0) } // 0: inches, 1: cm

    Text("Bra Size Calculator", style = MaterialTheme.typography.titleMedium)
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
        modifier = Modifier.fillMaxWidth()
    )
    Spacer(modifier = Modifier.height(8.dp))
    OutlinedTextField(
        value = bust,
        onValueChange = { bust = it },
        label = { Text("Bust") },
        modifier = Modifier.fillMaxWidth()
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

@Composable
fun BodyMeasurementGuide() {
    Text("How to Measure", style = MaterialTheme.typography.titleMedium)
    Spacer(modifier = Modifier.height(16.dp))

    MeasurementItem(Icons.Default.Straighten, "Chest/Bust", "Measure around the fullest part of your chest, keeping the tape horizontal.")
    MeasurementItem(Icons.Default.Straighten, "Waist", "Measure around your natural waistline (narrowest part), usually just above the belly button.")
    MeasurementItem(Icons.Default.Straighten, "Hips", "Measure around the fullest part of your hips, about 8 inches below your waist.")
    MeasurementItem(Icons.Default.Straighten, "Foot Length", "Place your foot on a paper, mark the heel and longest toe. Measure the distance.")
}

@Composable
fun MeasurementItem(icon: androidx.compose.ui.graphics.vector.ImageVector, title: String, description: String) {
    Row(modifier = Modifier.padding(vertical = 8.dp)) {
        Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(title, fontWeight = FontWeight.Bold)
            Text(description, style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
fun SizeTable(headers: List<String>, rows: List<List<String>>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.horizontalScroll(rememberScrollState())) {
            Row(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                headers.forEach { header ->
                    Text(
                        text = header,
                        modifier = Modifier.width(80.dp).padding(4.dp),
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }
            }
            HorizontalDivider()
            rows.forEach { row ->
                Row(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                    row.forEach { cell ->
                        Text(
                            text = cell,
                            modifier = Modifier.width(80.dp).padding(4.dp),
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}
