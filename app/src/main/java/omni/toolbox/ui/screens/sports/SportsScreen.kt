package omni.toolbox.ui.screens.sports

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
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
import kotlinx.coroutines.delay
import omni.toolbox.ui.components.ToolScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SportsScreen(navController: NavHostController) {
    var selectedTab by remember { mutableStateOf(0) }

    ToolScreen(
        title = "Sports & Fitness Training",
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
                    text = { Text("HIIT Interval Timer") }
                )
                Tab(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    text = { Text("Stopwatch") }
                )
            }

            if (selectedTab == 0) {
                HIITTimerTab()
            } else {
                StopwatchTab()
            }
        }
    }
}

@Composable
fun HIITTimerTab() {
    var workTime by remember { mutableStateOf(30) } // Seconds
    var restTime by remember { mutableStateOf(10) }  // Seconds
    var totalRounds by remember { mutableStateOf(8) }

    var currentRound by remember { mutableStateOf(1) }
    var isWorkPhase by remember { mutableStateOf(true) }
    var timeLeft by remember { mutableStateOf(workTime) }
    var isRunning by remember { mutableStateOf(false) }

    LaunchedEffect(isRunning, timeLeft, isWorkPhase) {
        if (isRunning) {
            if (timeLeft > 0) {
                delay(1000)
                timeLeft -= 1
            } else {
                if (isWorkPhase) {
                    isWorkPhase = false
                    timeLeft = restTime
                } else {
                    if (currentRound < totalRounds) {
                        currentRound += 1
                        isWorkPhase = true
                        timeLeft = workTime
                    } else {
                        isRunning = false
                        currentRound = 1
                        isWorkPhase = true
                        timeLeft = workTime
                    }
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        if (!isRunning) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Work Duration (Secs)", fontWeight = FontWeight.Bold)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Slider(
                                value = workTime.toFloat(),
                                onValueChange = { workTime = it.toInt(); timeLeft = it.toInt() },
                                valueRange = 5f..120f,
                                modifier = Modifier.weight(1f)
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Text("$workTime s", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Rest Duration (Secs)", fontWeight = FontWeight.Bold)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Slider(
                                value = restTime.toFloat(),
                                onValueChange = { restTime = it.toInt() },
                                valueRange = 5f..60f,
                                modifier = Modifier.weight(1f)
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Text("$restTime s", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Total Rounds", fontWeight = FontWeight.Bold)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Slider(
                                value = totalRounds.toFloat(),
                                onValueChange = { totalRounds = it.toInt() },
                                valueRange = 1f..25f,
                                modifier = Modifier.weight(1f)
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Text("$totalRounds", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        } else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = if (isWorkPhase) "WORK!" else "REST",
                    style = MaterialTheme.typography.headlineLarge,
                    color = if (isWorkPhase) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.tertiary,
                    fontWeight = FontWeight.ExtraBold
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = String.format("%02d:%02d", timeLeft / 60, timeLeft % 60),
                    style = MaterialTheme.typography.displayLarge.copy(fontSize = 72.sp),
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Round $currentRound / $totalRounds",
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            if (isRunning) {
                Button(
                    onClick = { isRunning = false },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Icon(Icons.Default.Pause, contentDescription = "Pause")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Pause")
                }
            } else {
                Button(
                    onClick = { isRunning = true },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Icon(Icons.Default.PlayArrow, contentDescription = "Start")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Start")
                }
            }

            Button(
                onClick = {
                    isRunning = false
                    currentRound = 1
                    isWorkPhase = true
                    timeLeft = workTime
                },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
            ) {
                Icon(Icons.Default.Refresh, contentDescription = "Reset")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Reset")
            }
        }
    }
}

@Composable
fun StopwatchTab() {
    var timeMillis by remember { mutableStateOf(0L) }
    var isRunning by remember { mutableStateOf(false) }
    val laps = remember { mutableStateListOf<Long>() }

    LaunchedEffect(isRunning) {
        if (isRunning) {
            val startTime = System.currentTimeMillis() - timeMillis
            while (isRunning) {
                timeMillis = System.currentTimeMillis() - startTime
                delay(10)
            }
        }
    }

    val formatTime: (Long) -> String = { ms ->
        val mins = (ms / 60000) % 60
        val secs = (ms / 1000) % 60
        val hundredths = (ms / 10) % 100
        String.format("%02d:%02d.%02d", mins, secs, hundredths)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = formatTime(timeMillis),
                    style = MaterialTheme.typography.displayLarge.copy(fontSize = 64.sp),
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            if (isRunning) {
                Button(
                    onClick = { isRunning = false },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Icon(Icons.Default.Pause, contentDescription = "Pause")
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Pause")
                }
                Button(
                    onClick = { laps.add(0, timeMillis) },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary)
                ) {
                    Icon(Icons.Default.Flag, contentDescription = "Lap")
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Lap")
                }
            } else {
                Button(
                    onClick = { isRunning = true },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Icon(Icons.Default.PlayArrow, contentDescription = "Start")
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Start")
                }
                Button(
                    onClick = {
                        timeMillis = 0L
                        laps.clear()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                ) {
                    Icon(Icons.Default.Refresh, contentDescription = "Reset")
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Reset")
                }
            }
        }

        if (laps.isNotEmpty()) {
            Text(
                "Laps",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                itemsIndexed(laps) { index, lapTime ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Lap ${laps.size - index}", fontWeight = FontWeight.SemiBold)
                        Text(formatTime(lapTime))
                    }
                    HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
                }
            }
        }
    }
}
