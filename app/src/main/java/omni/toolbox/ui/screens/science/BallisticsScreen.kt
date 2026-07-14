package omni.toolbox.ui.screens.science

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import omni.toolbox.ui.components.ToolScreen
import kotlin.math.*

data class TrajectoryPoint(
    val yardage: Int,
    val velocity: Double,
    val energy: Double,
    val drop: Double, // relative to LOS (inches)
    val time: Double
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BallisticsScreen(navController: NavHostController) {
    var muzzleVelocity by remember { mutableStateOf("2700") }
    var bulletWeight by remember { mutableStateOf("150") }
    var ballisticCoefficient by remember { mutableStateOf("0.4") }
    var sightHeight by remember { mutableStateOf("1.5") }
    var zeroRange by remember { mutableStateOf("100") }

    var calculationResults by remember { mutableStateOf<List<TrajectoryPoint>?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    fun getG1Drag(v: Double): Double {
        return when {
            v > 3000 -> 0.000006 * v.pow(2.4)
            v > 2000 -> 0.000012 * v.pow(2.25)
            v > 1400 -> 0.000035 * v.pow(2.1)
            v > 1040 -> 0.0018 * v.pow(1.6)
            v > 800  -> 0.00015 * v.pow(1.9)
            else     -> 0.00008 * v.pow(2.0)
        }
    }

    fun simulateToRange(v0: Double, bc: Double, sightHeightInches: Double, theta: Double, targetFeet: Double): Double {
        val g = 32.174
        var x = 0.0
        var y = -sightHeightInches / 12.0
        var vx = v0 * cos(theta)
        var vy = v0 * sin(theta)
        var t = 0.0
        val dt = 0.0005
        while (x < targetFeet && t < 3.0) {
            val v = sqrt(vx * vx + vy * vy)
            if (v < 10.0) break
            val dragAcc = getG1Drag(v) / bc
            val ax = -dragAcc * (vx / v)
            val ay = -g - dragAcc * (vy / v)
            x += vx * dt + 0.5 * ax * dt * dt
            y += vy * dt + 0.5 * ay * dt * dt
            vx += ax * dt
            vy += ay * dt
            t += dt
        }
        return y
    }

    fun simulate(v0: Double, weight: Double, bc: Double, sightHeightInches: Double, theta: Double): List<TrajectoryPoint> {
        val g = 32.174
        var x = 0.0
        var y = -sightHeightInches / 12.0
        var vx = v0 * cos(theta)
        var vy = v0 * sin(theta)
        var t = 0.0
        val dt = 0.0005

        val targetYards = listOf(0, 100, 200, 300, 400, 500)
        val results = mutableMapOf<Int, TrajectoryPoint>()
        var currentTargetIdx = 0

        fun calcEnergy(v: Double) = (weight * v * v) / 450240.0

        while (currentTargetIdx < targetYards.size && t < 3.0) {
            val targetFeet = targetYards[currentTargetIdx] * 3.0
            if (x >= targetFeet) {
                val yInches = y * 12.0
                val vCur = sqrt(vx * vx + vy * vy)
                results[targetYards[currentTargetIdx]] = TrajectoryPoint(
                    yardage = targetYards[currentTargetIdx],
                    velocity = vCur,
                    energy = calcEnergy(vCur),
                    drop = yInches,
                    time = t
                )
                currentTargetIdx++
            }

            val v = sqrt(vx * vx + vy * vy)
            if (v < 10.0) break

            val dragAcc = getG1Drag(v) / bc
            val ax = -dragAcc * (vx / v)
            val ay = -g - dragAcc * (vy / v)

            x += vx * dt + 0.5 * ax * dt * dt
            y += vy * dt + 0.5 * ay * dt * dt
            vx += ax * dt
            vy += ay * dt
            t += dt
        }

        while (currentTargetIdx < targetYards.size) {
            val yds = targetYards[currentTargetIdx]
            results[yds] = results[yds] ?: TrajectoryPoint(yds, 0.0, 0.0, 0.0, 0.0)
            currentTargetIdx++
        }

        return targetYards.map { results[it]!! }
    }

    fun calculate() {
        val v0 = muzzleVelocity.toDoubleOrNull()
        val w = bulletWeight.toDoubleOrNull()
        val bc = ballisticCoefficient.toDoubleOrNull()
        val sh = sightHeight.toDoubleOrNull()
        val zr = zeroRange.toDoubleOrNull()

        if (v0 == null || w == null || bc == null || sh == null || zr == null || v0 <= 0.0 || w <= 0.0 || bc <= 0.0 || sh < 0.0 || zr <= 0.0) {
            errorMessage = "Please enter valid positive numbers for all fields."
            calculationResults = null
            return
        }

        errorMessage = null

        // Numerical search to find launch angle (theta) relative to barrel to zero at zeroRange (zr)
        var low = -0.01
        var high = 0.05
        var theta = 0.0
        val targetFeet = zr * 3.0

        for (iter in 0..30) {
            theta = (low + high) / 2.0
            val yAtZero = simulateToRange(v0, bc, sh, theta, targetFeet)
            if (yAtZero > 0.0) {
                high = theta
            } else {
                low = theta
            }
        }

        calculationResults = simulate(v0, w, bc, sh, theta)
    }

    // Run initial calculation with default parameters
    LaunchedEffect(Unit) {
        calculate()
    }

    ToolScreen(title = "Ballistics Calculator", onBack = { navController.popBackStack() }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Projectile Inputs", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)

            OutlinedTextField(
                value = muzzleVelocity,
                onValueChange = { muzzleVelocity = it },
                label = { Text("Muzzle Velocity (fps)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            OutlinedTextField(
                value = bulletWeight,
                onValueChange = { bulletWeight = it },
                label = { Text("Bullet Weight (grains)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            OutlinedTextField(
                value = ballisticCoefficient,
                onValueChange = { ballisticCoefficient = it },
                label = { Text("Ballistic Coefficient (G1)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            OutlinedTextField(
                value = sightHeight,
                onValueChange = { sightHeight = it },
                label = { Text("Sight Height (inches)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            OutlinedTextField(
                value = zeroRange,
                onValueChange = { zeroRange = it },
                label = { Text("Zero Range (yards)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Button(
                onClick = { calculate() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Calculate Trajectory")
            }

            if (errorMessage != null) {
                Text(errorMessage!!, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodyMedium)
            }

            if (calculationResults != null) {
                Text("Trajectory Results (G1 Model)", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Header row
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Range", modifier = Modifier.weight(1.5f), fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodySmall)
                            Text("Drop (in)", modifier = Modifier.weight(1.5f), fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodySmall)
                            Text("Velocity", modifier = Modifier.weight(1.5f), fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodySmall)
                            Text("Energy", modifier = Modifier.weight(1.5f), fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodySmall)
                            Text("Time (s)", modifier = Modifier.weight(1.2f), fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodySmall)
                        }

                        HorizontalDivider(color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f))

                        calculationResults!!.forEach { point ->
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("${point.yardage}y", modifier = Modifier.weight(1.5f), style = MaterialTheme.typography.bodyMedium)

                                val dropText = if (point.yardage == 0) "0.0" else "%.1f".format(point.drop)
                                Text(dropText, modifier = Modifier.weight(1.5f), style = MaterialTheme.typography.bodyMedium, color = if (point.drop < 0.0) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface)

                                Text("${point.velocity.toInt()} fps", modifier = Modifier.weight(1.5f), style = MaterialTheme.typography.bodyMedium)
                                Text("${point.energy.toInt()} ft-lb", modifier = Modifier.weight(1.5f), style = MaterialTheme.typography.bodyMedium)
                                Text("%.3f s".format(point.time), modifier = Modifier.weight(1.2f), style = MaterialTheme.typography.bodyMedium)
                            }
                        }
                    }
                }
            }
        }
    }
}
