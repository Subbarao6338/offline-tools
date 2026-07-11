package omni.toolbox.ui.screens.health

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import omni.toolbox.ui.components.ToolScreen
import omni.toolbox.ui.components.AdjustmentSlider
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import kotlinx.coroutines.delay

@Composable
fun SleepCycleCalculator() {
    var wakeTimeStr by remember { mutableStateOf("07:00") }
    val formatter = DateTimeFormatter.ofPattern("HH:mm")

    Column(modifier = Modifier.fillMaxWidth()) {
        Text("Sleep Cycle (90-min cycles)", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = wakeTimeStr,
            onValueChange = { wakeTimeStr = it },
            label = { Text("Wake up time (HH:mm)") },
            modifier = Modifier.fillMaxWidth()
        )

        val wakeTime = try { LocalTime.parse(wakeTimeStr, formatter) } catch (e: Exception) { null }

        if (wakeTime != null) {
            Spacer(modifier = Modifier.height(16.dp))
            Text("To wake up refreshed, go to bed at:")
            // 9h (6 cycles), 7.5h (5 cycles), 6h (4 cycles)
            val suggestedTimes = listOf(wakeTime.minusMinutes(540), wakeTime.minusMinutes(450), wakeTime.minusMinutes(360))
            suggestedTimes.forEach { time ->
                ListItem(
                    headlineContent = { Text(time.format(DateTimeFormatter.ofPattern("hh:mm a"))) },
                    supportingContent = {
                        val hours = java.time.Duration.between(time, wakeTime).let { if (it.isNegative) it.plusDays(1) else it }.toHours()
                        Text("$hours hours of sleep")
                    }
                )
            }
        }
    }
}

@Composable
fun MedicationReminderSystem() {
    var medicineName by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("08:00") }
    val reminders = remember { mutableStateListOf<Pair<String, String>>() }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text("Medicine Reminder", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = medicineName,
            onValueChange = { medicineName = it },
            label = { Text("Medicine Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = time,
            onValueChange = { time = it },
            label = { Text("Time (HH:mm)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (medicineName.isNotBlank()) {
                    reminders.add(medicineName to time)
                    medicineName = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Default.Add, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Add Reminder")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text("Scheduled Reminders", style = MaterialTheme.typography.titleSmall)
        Spacer(modifier = Modifier.height(8.dp))

        if (reminders.isEmpty()) {
            Text("No reminders set", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.outline)
        } else {
            reminders.forEach { reminder ->
                Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                    Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Alarm, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(reminder.first, style = MaterialTheme.typography.bodyLarge)
                            Text(reminder.second, style = MaterialTheme.typography.bodySmall)
                        }
                        IconButton(onClick = { reminders.remove(reminder) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete", tint = MaterialTheme.colorScheme.error)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BmrCalculator() {
    var age by remember { mutableStateOf("30") }
    var weight by remember { mutableStateOf("70") }
    var height by remember { mutableStateOf("175") }
    var isMale by remember { mutableStateOf(true) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text("BMR Calculator (Mifflin-St Jeor)", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(value = age, onValueChange = { age = it }, label = { Text("Age") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = weight, onValueChange = { weight = it }, label = { Text("Weight (kg)") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = height, onValueChange = { height = it }, label = { Text("Height (cm)") }, modifier = Modifier.fillMaxWidth())
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(selected = isMale, onClick = { isMale = true })
            Text("Male")
            Spacer(modifier = Modifier.width(16.dp))
            RadioButton(selected = !isMale, onClick = { isMale = false })
            Text("Female")
        }

        val a = age.toDoubleOrNull() ?: 0.0
        val w = weight.toDoubleOrNull() ?: 0.0
        val h = height.toDoubleOrNull() ?: 0.0

        if (a > 0 && w > 0 && h > 0) {
            val bmr = (10 * w) + (6.25 * h) - (5 * a) + (if (isMale) 5 else -161)
            Card(modifier = Modifier.fillMaxWidth().padding(top = 16.dp)) {
                Text("BMR: ${bmr.toInt()} kcal/day", modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.headlineSmall)
            }
        }
    }
}

@Composable
fun HealthScreen(navController: NavHostController, title: String) {
    ToolScreen(
        title = title,
        onBack = { navController.popBackStack() }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                Icons.Default.HealthAndSafety,
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(24.dp))

            when (title) {
                "Posture Checker", "posture_check" -> {
                    var headAligned by remember { mutableStateOf(true) }
                    var shouldersAligned by remember { mutableStateOf(true) }
                    var spineAligned by remember { mutableStateOf(true) }
                    val ergonomicsScore = remember(headAligned, shouldersAligned, spineAligned) {
                        var score = 100
                        if (!headAligned) score -= 30
                        if (!shouldersAligned) score -= 30
                        if (!spineAligned) score -= 40
                        score
                    }
                    Text("Posture Assessment & Ergonomics", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(checked = headAligned, onCheckedChange = { headAligned = it })
                        Text("Head Aligned (Ear over shoulder)")
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(checked = shouldersAligned, onCheckedChange = { shouldersAligned = it })
                        Text("Shoulders Aligned (No hunching)")
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(checked = spineAligned, onCheckedChange = { spineAligned = it })
                        Text("Spine Aligned (Neutral curvature)")
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Calculated Ergonomics Score:")
                            Text("$ergonomicsScore / 100", style = MaterialTheme.typography.displayMedium, fontWeight = FontWeight.Bold, color = if(ergonomicsScore > 70) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error)
                        }
                    }
                }
                "Eye Exercise", "eye_exercise" -> {
                    var timeLeft by remember { mutableIntStateOf(20) }
                    var exercisePhase by remember { mutableStateOf("Stare at 20 feet distance (20-20-20 Rule)") }
                    var active by remember { mutableStateOf(false) }

                    LaunchedEffect(active) {
                        if (active) {
                            while (timeLeft > 0) {
                                delay(1000)
                                timeLeft--
                            }
                            if (exercisePhase.startsWith("Stare")) {
                                exercisePhase = "Blink rapidly to moisten eyes"
                                timeLeft = 10
                            } else {
                                exercisePhase = "Finished! Eyes relaxed."
                                active = false
                            }
                        }
                    }

                    Text("Guided Eye Relaxation Exercise", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(exercisePhase, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Medium)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Timer: ${timeLeft}s", style = MaterialTheme.typography.displaySmall, color = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { active = true; timeLeft = 20; exercisePhase = "Stare at 20 feet distance (20-20-20 Rule)" }, enabled = !active) {
                        Text("Start Exercise")
                    }
                }
                "Water Reminder", "water_reminder" -> {
                    var weightStr by remember { mutableStateOf("150") }
                    var activityMinutes by remember { mutableStateOf("30") }
                    var cupsLogged by remember { mutableIntStateOf(0) }

                    val weight = weightStr.toDoubleOrNull() ?: 150.0
                    val act = activityMinutes.toDoubleOrNull() ?: 30.0
                    val targetOunces = remember(weight, act) {
                        // Formula: (Weight in lbs * 0.5) + (Activity minutes / 30 * 12)
                        (weight * 0.5) + (act / 30.0 * 12.0)
                    }

                    Text("Hydration Target & Tracker", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(value = weightStr, onValueChange = { weightStr = it }, label = { Text("Weight (lbs)") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = activityMinutes, onValueChange = { activityMinutes = it }, label = { Text("Activity (minutes/day)") }, modifier = Modifier.fillMaxWidth())
                    Spacer(modifier = Modifier.height(16.dp))
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Target Hydration:")
                            Text("${"%.1f".format(targetOunces)} oz (~${(targetOunces/8.0).toInt()} cups)", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                        Text("Logged Cups: $cupsLogged / ${(targetOunces/8.0).toInt()}")
                        Row {
                            Button(onClick = { if (cupsLogged > 0) cupsLogged-- }) { Text("-") }
                            Spacer(modifier = Modifier.width(8.dp))
                            Button(onClick = { cupsLogged++ }) { Text("+") }
                        }
                    }
                }
                "Blood Sugar", "blood_sugar" -> {
                    var fastingMode by remember { mutableStateOf(true) }
                    var sugarLevelStr by remember { mutableStateOf("95") }

                    val level = sugarLevelStr.toDoubleOrNull() ?: 0.0
                    val diagnosis = remember(fastingMode, level) {
                        if (level <= 0) "Enter Level"
                        else if (fastingMode) {
                            when {
                                level < 70 -> "Hypoglycemia"
                                level < 100 -> "Normal Fasting"
                                level < 126 -> "Prediabetes (Impaired Fasting)"
                                else -> "Diabetes"
                            }
                        } else {
                            when {
                                level < 140 -> "Normal Post-Meal"
                                level < 200 -> "Prediabetes"
                                else -> "Diabetes"
                            }
                        }
                    }

                    Text("Blood Sugar Analysis Form", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(selected = fastingMode, onClick = { fastingMode = true })
                        Text("Fasting (No food 8 hrs)")
                        Spacer(modifier = Modifier.width(16.dp))
                        RadioButton(selected = !fastingMode, onClick = { fastingMode = false })
                        Text("Post-Meal (2 hrs after)")
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(value = sugarLevelStr, onValueChange = { sugarLevelStr = it }, label = { Text("Glucose Level (mg/dL)") }, modifier = Modifier.fillMaxWidth())
                    Spacer(modifier = Modifier.height(16.dp))
                    Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)) {
                        Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Estimated Assessment:")
                            Text(diagnosis, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                        }
                    }
                }
                "Blood Pressure", "blood_pressure" -> {
                    var systolicStr by remember { mutableStateOf("120") }
                    var diastolicStr by remember { mutableStateOf("80") }

                    val sys = systolicStr.toIntOrNull() ?: 0
                    val dia = diastolicStr.toIntOrNull() ?: 0
                    val bpCategory = remember(sys, dia) {
                        if (sys <= 0 || dia <= 0) "Enter Readings"
                        else when {
                            sys < 120 && dia < 80 -> "Normal BP"
                            sys in 120..129 && dia < 80 -> "Elevated BP"
                            sys in 130..139 || dia in 80..89 -> "Hypertension (Stage 1)"
                            sys >= 140 || dia >= 90 -> "Hypertension (Stage 2)"
                            else -> "Hypertensive Crisis (Consult Doctor)"
                        }
                    }

                    Text("Blood Pressure Category Form", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(value = systolicStr, onValueChange = { systolicStr = it }, label = { Text("Systolic (top number, mmHg)") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = diastolicStr, onValueChange = { diastolicStr = it }, label = { Text("Diastolic (bottom number, mmHg)") }, modifier = Modifier.fillMaxWidth())
                    Spacer(modifier = Modifier.height(16.dp))
                    Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)) {
                        Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Assessment Category:")
                            Text(bpCategory, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                        }
                    }
                }
                "Period Tracker", "period_tracker" -> {
                    var lastStartStr by remember { mutableStateOf("2024-10-01") }
                    var cycleLengthStr by remember { mutableStateOf("28") }

                    val cycle = cycleLengthStr.toIntOrNull() ?: 28
                    val formatter = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd")
                    val parsedDate = try { java.time.LocalDate.parse(lastStartStr, formatter) } catch (e: Exception) { null }

                    Text("Menstrual Cycle & Fertility Predictor", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(value = lastStartStr, onValueChange = { lastStartStr = it }, label = { Text("Last Period Start (YYYY-MM-DD)") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = cycleLengthStr, onValueChange = { cycleLengthStr = it }, label = { Text("Cycle Length (Days)") }, modifier = Modifier.fillMaxWidth())
                    Spacer(modifier = Modifier.height(16.dp))

                    if (parsedDate != null) {
                        val nextStart = parsedDate.plusDays(cycle.toLong())
                        val ovulation = nextStart.minusDays(14)
                        val fertileStart = ovulation.minusDays(5)
                        val fertileEnd = ovulation.plusDays(1)

                        Card(modifier = Modifier.fillMaxWidth()) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text("Next Predicted Period Start:", fontWeight = FontWeight.Bold)
                                Text(nextStart.format(java.time.format.DateTimeFormatter.ofPattern("EEEE, MMM dd, yyyy")), style = MaterialTheme.typography.bodyLarge)
                                Spacer(modifier = Modifier.height(8.dp))
                                Text("Estimated Ovulation Day:", fontWeight = FontWeight.Bold)
                                Text(ovulation.format(java.time.format.DateTimeFormatter.ofPattern("EEEE, MMM dd, yyyy")), style = MaterialTheme.typography.bodyLarge)
                                Spacer(modifier = Modifier.height(8.dp))
                                Text("Fertile Window:", fontWeight = FontWeight.Bold)
                                Text("${fertileStart.format(java.time.format.DateTimeFormatter.ofPattern("MMM dd"))} to ${fertileEnd.format(java.time.format.DateTimeFormatter.ofPattern("MMM dd"))}", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.primary)
                            }
                        }
                    }
                }
                "Sleep Tracker", "Sleep Cycle", "Sleep Cycle calculator", "sleep_tracker" -> SleepCycleCalculator()
                "Yoga Guide", "yoga_guide" -> {
                    Text("Current Pose: Mountain Pose (Tadasana)", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(24.dp))
                    Text("Focus on your breath and stand tall with feet grounded.", style = MaterialTheme.typography.bodyMedium)
                }
                "Medication Tracker", "Medication reminder", "medication_tracker" -> {
                    MedicationReminderSystem()
                }
                "BMR Calculator", "bmr" -> BmrCalculator()
                "BMI Calc", "bmi" -> {
                    var weight by remember { mutableStateOf("70") }
                    var height by remember { mutableStateOf("175") }
                    OutlinedTextField(value = weight, onValueChange = { weight = it }, label = { Text("Weight (kg)") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = height, onValueChange = { height = it }, label = { Text("Height (cm)") }, modifier = Modifier.fillMaxWidth())
                    val w = weight.toDoubleOrNull() ?: 0.0
                    val h = height.toDoubleOrNull() ?: 0.0
                    if (w > 0 && h > 0) {
                        val bmi = w / (h/100 * h/100)
                        Card(modifier = Modifier.fillMaxWidth().padding(top = 16.dp)) {
                            Text("BMI: ${"%.1f".format(bmi)}", modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.headlineSmall)
                        }
                    }
                }
                else -> {
                    Text("Health monitoring for $title")
                    AdjustmentSlider("Reminder Frequency", initialValue = 0.5f)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { /* Specialized action */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Start Session")
            }
        }
    }
}
