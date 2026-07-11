package omni.toolbox.ui.screens.lifestyle

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import omni.toolbox.ui.components.ToolScreen
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ZodiacFinderScreen(navController: NavHostController) {
    var birthDate by remember { mutableStateOf(LocalDate.now()) }
    val showDatePicker = remember { mutableStateOf(false) }
    var activeTab by remember { mutableIntStateOf(0) } // 0: Western, 1: Indian/Telugu, 2: Chinese

    if (showDatePicker.value) {
        val datePickerState = rememberDatePickerState()
        DatePickerDialog(
            onDismissRequest = { showDatePicker.value = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let {
                        birthDate = LocalDate.ofEpochDay(it / (24 * 60 * 60 * 1000))
                    }
                    showDatePicker.value = false
                }) { Text("OK") }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    ToolScreen(title = "Astrology & Zodiac Finder", onBack = { navController.popBackStack() }) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {

            // Birthday Header selector card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.2f))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Select Your Birth Date", style = MaterialTheme.typography.labelLarge)
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = { showDatePicker.value = true }) {
                        Text(birthDate.format(DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy")))
                    }
                }
            }

            // Tab selectors
            TabRow(selectedTabIndex = activeTab) {
                Tab(selected = activeTab == 0, onClick = { activeTab = 0 }, text = { Text("Western", fontSize = 13.sp) })
                Tab(selected = activeTab == 1, onClick = { activeTab = 1 }, text = { Text("Telugu/Vedic", fontSize = 13.sp) })
                Tab(selected = activeTab == 2, onClick = { activeTab = 2 }, text = { Text("Chinese", fontSize = 13.sp) })
            }

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    when (activeTab) {
                        0 -> WesternAstrologyUI(birthDate)
                        1 -> TeluguAstrologyUI(birthDate)
                        else -> ChineseAstrologyUI(birthDate)
                    }
                }
            }
        }
    }
}

// ----------------------------------------------------
// 1. WESTERN ASTROLOGY VIEW
// ----------------------------------------------------
@Composable
fun WesternAstrologyUI(date: LocalDate) {
    val zodiacSign = getZodiacSign(date.dayOfMonth, date.monthValue)
    val info = getWesternZodiacInfo(zodiacSign)

    val gradient = when (info.element) {
        "Fire" -> Brush.horizontalGradient(listOf(Color(0xFFFF5722), Color(0xFFFF9800)))
        "Water" -> Brush.horizontalGradient(listOf(Color(0xFF2196F3), Color(0xFF00BCD4)))
        "Earth" -> Brush.horizontalGradient(listOf(Color(0xFF4CAF50), Color(0xFF8BC34A)))
        else -> Brush.horizontalGradient(listOf(Color(0xFF9C27B0), Color(0xFFE91E63))) // Air
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(gradient)
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(info.symbol, fontSize = 48.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(zodiacSign, style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Bold, color = Color.White)
                    Text("Element: ${info.element} | Planet: ${info.rulingPlanet}", style = MaterialTheme.typography.bodyMedium, color = Color.White.copy(alpha = 0.9f))
                }
            }

            Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                AstrologyRow("Lucky Color", info.luckyColor)
                AstrologyRow("Compatibility", info.compatibility)
                HorizontalDivider()

                Text("Strengths", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleSmall)
                Text(info.strengths, style = MaterialTheme.typography.bodyMedium)

                Text("Weaknesses", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleSmall)
                Text(info.weaknesses, style = MaterialTheme.typography.bodyMedium)

                Text("Vibe & Personality", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.primary)
                Text(info.personality, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

// ----------------------------------------------------
// 2. TELUGU & VEDIC ASTROLOGY VIEW
// ----------------------------------------------------
@Composable
fun TeluguAstrologyUI(date: LocalDate) {
    val westernSign = getZodiacSign(date.dayOfMonth, date.monthValue)
    val info = getVedicZodiacInfo(westernSign)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Brush.horizontalGradient(listOf(Color(0xFFE65100), Color(0xFFFFB300))))
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(info.rashiName, style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Bold, color = Color.White)
                    Text("Telugu Rashi: ${info.teluguRashi}", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Medium, color = Color.White)
                }
            }

            Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                AstrologyRow("Lord (అధిపతి)", info.lord)
                AstrologyRow("Nature (తత్వము)", info.nature)
                AstrologyRow("Suggested Nakshatras", info.nakshatras.joinToString(", "))
                HorizontalDivider()

                Text("Telugu Spiritual Guidance (ఆధ్యాత్మిక సూచనలు)", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleSmall, color = Color(0xFFE65100))
                Text(info.spiritualGuidance, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

// ----------------------------------------------------
// 3. CHINESE ASTROLOGY VIEW
// ----------------------------------------------------
@Composable
fun ChineseAstrologyUI(date: LocalDate) {
    val year = date.year
    val animal = getChineseZodiacAnimal(year)
    val element = getChineseZodiacElement(year)
    val info = getChineseZodiacDetails(animal)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Brush.horizontalGradient(listOf(Color(0xFFB71C1C), Color(0xFFE53935))))
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(info.emoji, fontSize = 48.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(animal, style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Bold, color = Color.White)
                    Text("Year of the $animal ($element)", style = MaterialTheme.typography.bodyMedium, color = Color.White.copy(alpha = 0.9f))
                }
            }

            Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                AstrologyRow("Lucky Numbers", info.luckyNumbers)
                AstrologyRow("Lucky Flower", info.luckyFlower)
                HorizontalDivider()

                Text("Personality Traits", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleSmall)
                Text(info.traits, style = MaterialTheme.typography.bodyMedium)

                Text("Spiritual & Practical Compatibility", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleSmall, color = Color(0xFFB71C1C))
                Text(info.compatibility, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Composable
fun AstrologyRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(value, style = MaterialTheme.typography.bodyMedium, textAlign = TextAlign.End)
    }
}

// ----------------------------------------------------
// HELPERS & MODELS
// ----------------------------------------------------
data class WesternZodiacInfo(
    val symbol: String,
    val element: String,
    val rulingPlanet: String,
    val luckyColor: String,
    val compatibility: String,
    val strengths: String,
    val weaknesses: String,
    val personality: String
)

fun getWesternZodiacInfo(sign: String): WesternZodiacInfo {
    return when (sign) {
        "Aries" -> WesternZodiacInfo("♈", "Fire", "Mars", "Red", "Leo, Sagittarius", "Courageous, determined, confident, enthusiastic", "Impatient, moody, short-tempered, aggressive", "Aries is a passionate trailblazer. They lead with enthusiastic energy and take initiative on challenges.")
        "Taurus" -> WesternZodiacInfo("♉", "Earth", "Venus", "Green", "Virgo, Capricorn", "Reliable, patient, practical, devoted, stable", "Stubborn, possessive, uncompromising", "Taurus appreciates comfort, beauty, and practical stability. They are deeply devoted and incredibly resilient.")
        "Gemini" -> WesternZodiacInfo("♊", "Air", "Mercury", "Yellow", "Libra, Aquarius", "Gentle, affectionate, curious, adaptable, quick learner", "Nervous, inconsistent, indecisive", "Gemini is expressive and quick-witted. Their curious dual-nature drives them to explore multiple ideas at once.")
        "Cancer" -> WesternZodiacInfo("♋", "Water", "Moon", "White", "Scorpio, Pisces", "Tenacious, highly imaginative, loyal, sympathetic", "Moody, pessimistic, suspicious, insecure", "Cancer is deeply intuitive and sentimental. They care deeply about family, home, and protective spaces.")
        "Leo" -> WesternZodiacInfo("♌", "Fire", "Sun", "Gold", "Aries, Sagittarius", "Creative, passionate, generous, warm-hearted, cheerful", "Arrogant, stubborn, self-centered, lazy", "Leo is a charismatic, natural-born leader. Bold and dramatic, they light up rooms and hold deep loyalty to friends.")
        "Virgo" -> WesternZodiacInfo("♍", "Earth", "Mercury", "Grey/Pale Yellow", "Taurus, Capricorn", "Loyal, analytical, kind, hardworking, practical", "Shyness, worry, overly critical of self and others", "Virgo has an exquisite eye for detail. Methodical and dedicated, they strive for order and love helping others.")
        "Libra" -> WesternZodiacInfo("♎", "Air", "Venus", "Pink/Blue", "Gemini, Aquarius", "Cooperative, gracious, fair-minded, social", "Indecisive, avoids confrontations, holds grudges", "Libra seeks balance, harmony, and beautiful aesthetics. They flourish in peaceful environments and fair communities.")
        "Scorpio" -> WesternZodiacInfo("♏", "Water", "Pluto/Mars", "Scarlet/Rust", "Cancer, Pisces", "Brave, passionate, stubborn, resourceful, true friend", "Distrusting, jealous, secretive, violent", "Scorpio is intense, magnetic, and profoundly intuitive. They possess great emotional strength and focus.")
        "Sagittarius" -> WesternZodiacInfo("♐", "Fire", "Jupiter", "Blue", "Aries, Leo", "Generous, idealistic, great sense of humor", "Impatient, promises more than can deliver, tactless", "Sagittarius is an adventurous free spirit. Optimistic and philosophical, they seek truth and intellectual horizon.")
        "Capricorn" -> WesternZodiacInfo("♑", "Earth", "Saturn", "Brown/Black", "Taurus, Virgo", "Responsible, disciplined, self-control, good managers", "Know-it-all, unforgiving, condescending", "Capricorn represents responsibility, patience, and professional discipline. They climb mountains with deliberate focus.")
        "Aquarius" -> WesternZodiacInfo("♒", "Air", "Uranus/Saturn", "Light Blue/Silver", "Gemini, Libra", "Progressive, original, independent, humanitarian", "Runs from emotional expression, temperamental", "Aquarius is a futuristic visionary. Intellectual and humanitarian, they thrive on originality and social progress.")
        else -> WesternZodiacInfo("♓", "Water", "Neptune/Jupiter", "Mauve", "Cancer, Scorpio", "Compassionate, artistic, intuitive, gentle, wise", "Fearful, overly trusting, sad, desire to escape reality", "Pisces is creative, dreamy, and highly empathetic. They navigate life with deep emotional wisdom and kindness.")
    }
}

data class VedicZodiacInfo(
    val rashiName: String,
    val teluguRashi: String,
    val lord: String,
    val nature: String,
    val nakshatras: List<String>,
    val spiritualGuidance: String
)

fun getVedicZodiacInfo(westernSign: String): VedicZodiacInfo {
    return when (westernSign) {
        "Aries" -> VedicZodiacInfo("Mesha Rashi", "మేష రాశి", "Mars (కుజుడు)", "Chara / Fire (చర / అగ్ని తత్వము)", listOf("Aswini", "Bharani", "Krittika 1st Pada"), "మేష రాశి వారు సాహసోపేతమైన పనులు చేస్తారు. శ్రీ సుబ్రహ్మణ్యేశ్వర స్వామి ఆరాధన వీరికి పరమ శ్రేయస్కరం.")
        "Taurus" -> VedicZodiacInfo("Vrishabha Rashi", "వృషభ రాశి", "Venus (శుక్రుడు)", "Sthira / Earth (స్థిర / భూ తత్వము)", listOf("Krittika 2,3,4", "Rohini", "Mrigasira 1,2"), "వృషభ రాశి వారికి స్థిరమైన ఆలోచనలు ఉంటాయి. లలితా సహస్రనామ పారాయణ శుభప్రదం.")
        "Gemini" -> VedicZodiacInfo("Mithuna Rashi", "మిథున రాశి", "Mercury (బుధుడు)", "Dwisvabhava / Air (ద్విస్వభావ / వాయు తత్వము)", listOf("Mrigasira 3,4", "Arudra", "Punarvasu 1,2,3"), "మిథున రాశి వారు అద్భుతమైన తెలివితేటలు కలిగి ఉంటారు. విష్ణు సహస్రనామ స్తోత్రం పఠించడం మంచిది.")
        "Cancer" -> VedicZodiacInfo("Karkataka Rashi", "కర్కాటక రాశి", "Moon (చంద్రుడు)", "Chara / Water (చర / జల తత్వము)", listOf("Punarvasu 4", "Pushyami", "Aslesha"), "వీరి మనస్సు సున్నితంగా ఉంటుంది. శ్రీ పార్వతీ దేవి లేదా దుర్గాదేవి ఆరాధన మానసిక ప్రశాంతతను ఇస్తుంది.")
        "Leo" -> VedicZodiacInfo("Simha Rashi", "సింహ రాశి", "Sun (సూర్యుడు)", "Sthira / Fire (స్థిర / అగ్ని తత్వము)", listOf("Makha", "Pubba", "Uttara 1st Pada"), "సింహ రాశి వారికి నాయకత్వ లక్షణాలు ఎక్కువ. నిత్యం ఆదిత్య హృదయం పఠించడం వల్ల తేజస్సు పెరుగుతుంది.")
        "Virgo" -> VedicZodiacInfo("Kanya Rashi", "కన్యా రాశి", "Mercury (బుధుడు)", "Dwisvabhava / Earth (ద్విస్వభావ / భూ తత్వము)", listOf("Uttara 2,3,4", "Hasta", "Chitta 1,2"), "కన్యా రాశి వారు విశ్లేషణాత్మక బుద్ధి కలిగి ఉంటారు. గాయత్రీ దేవి జపం లేదా లక్ష్మీ పూజ శుభకరం.")
        "Libra" -> VedicZodiacInfo("Tula Rashi", "తులా రాశి", "Venus (శుక్రుడు)", "Chara / Air (చర / వాయు తత్వము)", listOf("Chitta 3,4", "Swati", "Visakha 1,2,3"), "తులా రాశి వారు సమానత్వానికి ప్రాధాన్యత ఇస్తారు. శ్రీ వెంకటేశ్వర స్వామి పూజ విశేష ఫలితాలనిస్తుంది.")
        "Scorpio" -> VedicZodiacInfo("Vrishchika Rashi", "వృశ్చిక రాశి", "Mars (కుజుడు)", "Sthira / Water (స్థిర / జల తత్వము)", listOf("Visakha 4", "Anuradha", "Jyeshta"), "వీరిలో బలమైన ఆత్మవిశ్వాసం ఉంటుంది. శ్రీ హనుమాన్ చాలీసా పారాయణ సర్వ గ్రహ దోషాలను నివారిస్తుంది.")
        "Sagittarius" -> VedicZodiacInfo("Dhanu Rashi", "ధను రాశి", "Jupiter (గురువు)", "Dwisvabhava / Fire (ద్విస్వభావ / అగ్ని తత్వము)", listOf("Moola", "Poorvashada", "Uttarashada 1st Pada"), "ధను రాశి వారు ధర్మ పరాయణులు. దత్తాత్రేయ స్వామి లేదా దక్షిణామూర్తి ఆరాధన ఉత్తమం.")
        "Capricorn" -> VedicZodiacInfo("Makara Rashi", "మకర రాశి", "Saturn (శని)", "Chara / Earth (చర / భూ తత్వము)", listOf("Uttarashada 2,3,4", "Sravanam", "Dhanishta 1,2"), "మకర రాశి వారు కష్టపడే తత్త్వం గలవారు. శని దోష నివారణకు ఆంజనేయ స్వామి సింధూర పూజ చేయించుకోవాలి.")
        "Aquarius" -> VedicZodiacInfo("Kumbha Rashi", "కుంభ రాశి", "Saturn (శని)", "Sthira / Air (స్థిర / వాయు తత్వము)", listOf("Dhanishta 3,4", "Satabhisham", "Poorvabhadra 1,2,3"), "కుంభ రాశి వారు నూతన ఆవిష్కరణలు ఇష్టపడతారు. శివ సహస్రనామ స్తోత్ర పఠనం మంచిది.")
        else -> VedicZodiacInfo("Meena Rashi", "మీన రాశి", "Jupiter (గురువు)", "Dwisvabhava / Water (ద్విస్వభావ / జల తత్వము)", listOf("Poorvabhadra 4", "Uttarabhadra", "Revati"), "మీన రాశి వారు ఆధ్యాత్మిక చింతన గలవారు. శ్రీ లక్ష్మీనారాయణ స్వామి ఆరాధన ఐశ్వర్య ప్రదం.")
    }
}

data class ChineseZodiacDetails(
    val emoji: String,
    val traits: String,
    val luckyNumbers: String,
    val luckyFlower: String,
    val compatibility: String
)

fun getChineseZodiacAnimal(year: Int): String {
    val animals = listOf("Monkey", "Rooster", "Dog", "Pig", "Rat", "Ox", "Tiger", "Rabbit", "Dragon", "Snake", "Horse", "Goat")
    return animals[year % 12]
}

fun getChineseZodiacElement(year: Int): String {
    return when (year % 10) {
        0, 1 -> "Metal"
        2, 3 -> "Water"
        4, 5 -> "Wood"
        6, 7 -> "Fire"
        else -> "Earth"
    }
}

fun getChineseZodiacDetails(animal: String): ChineseZodiacDetails {
    return when (animal) {
        "Rat" -> ChineseZodiacDetails("🐭", "Quick-witted, resourceful, versatile, kind", "2, 3", "Lily, African Violet", "Ox, Dragon, Monkey")
        "Ox" -> ChineseZodiacDetails("🐮", "Diligent, dependable, strong, determined", "1, 9", "Tulip, Peach Blossom", "Rat, Snake, Rooster")
        "Tiger" -> ChineseZodiacDetails("🐯", "Brave, confident, competitive, magnetic", "1, 3, 4", "Cineraria, Anthurium", "Horse, Dog, Pig")
        "Rabbit" -> ChineseZodiacDetails("🐰", "Gentle, quiet, elegant, alert, patient", "3, 4, 9", "Jasmine, Lily", "Sheep, Monkey, Dog, Pig")
        "Dragon" -> ChineseZodiacDetails("🐲", "Confident, intelligent, enthusiastic, natural leader", "1, 6, 7", "Larkspur, Bleeding Heart", "Rat, Tiger, Snake")
        "Snake" -> ChineseZodiacDetails("🐍", "Enigmatic, intelligent, wise, intuitive", "2, 8, 9", "Orchid, Cactus", "Dragon, Rooster")
        "Horse" -> ChineseZodiacDetails("🐴", "Animated, active, energetic, optimistic", "2, 3, 7", "Calla Lily, Jasmine", "Tiger, Goat, Dog")
        "Goat" -> ChineseZodiacDetails("🐐", "Gentle, mild-mannered, shy, sympathetic", "2, 7", "Carnation, Primrose", "Rabbit, Horse, Pig")
        "Monkey" -> ChineseZodiacDetails("🐵", "Sharp, smart, curious, mischievous", "4, 9", "Chrysanthemum, Allium", "Rat, Dragon, Snake")
        "Rooster" -> ChineseZodiacDetails("🐔", "Observant, hardworking, courageous, talented", "5, 7, 8", "Gladiola, Cockscomb", "Ox, Snake, Dragon")
        "Dog" -> ChineseZodiacDetails("🐶", "Lovely, honest, prudent, loyal, helpful", "3, 4, 9", "Rose, Cymbidium", "Tiger, Rabbit, Horse")
        else -> ChineseZodiacDetails("🐷", "Compassionate, generous, diligent, realistic", "2, 5, 8", "Hydrangea, Daisy", "Tiger, Rabbit, Goat")
    }
}

fun getZodiacSign(day: Int, month: Int): String {
    return when (month) {
        1 -> if (day < 20) "Capricorn" else "Aquarius"
        2 -> if (day < 19) "Aquarius" else "Pisces"
        3 -> if (day < 21) "Pisces" else "Aries"
        4 -> if (day < 20) "Aries" else "Taurus"
        5 -> if (day < 21) "Taurus" else "Gemini"
        6 -> if (day < 21) "Gemini" else "Cancer"
        7 -> if (day < 23) "Cancer" else "Leo"
        8 -> if (day < 23) "Leo" else "Virgo"
        9 -> if (day < 23) "Virgo" else "Libra"
        10 -> if (day < 23) "Libra" else "Scorpio"
        11 -> if (day < 22) "Scorpio" else "Sagittarius"
        12 -> if (day < 22) "Sagittarius" else "Capricorn"
        else -> "Unknown"
    }
}
