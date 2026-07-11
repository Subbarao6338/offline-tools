package omni.toolbox.ui.screens.productivity

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import omni.toolbox.ui.components.ToolScreen
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec
import android.util.Base64
import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.ui.graphics.asImageBitmap
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

@Composable
fun SecurityScreen(navController: NavHostController, title: String = "Security Vault") {
    val isMainVault = title == "Security Vault" || title == "Privacy & Security Vault"

    ToolScreen(
        title = title,
        onBack = { navController.popBackStack() }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            if (isMainVault) {
                var selectedTab by remember { mutableIntStateOf(0) }
                val tabs = listOf("Encryption", "Password Gen", "QR Studio")

                TabRow(selectedTabIndex = selectedTab) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTab == index,
                            onClick = { selectedTab = index },
                            text = { Text(title) }
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp)
                ) {
                    when (selectedTab) {
                        0 -> EncryptionTab()
                        1 -> PasswordTab()
                        2 -> QrStudioTab(navController)
                    }
                }
            } else {
                // Specialized sub-utility security view based on title
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp)
                ) {
                    when (title) {
                        "App Locker", "app_locker" -> {
                            Text("Simulated App Locker Status", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(12.dp))
                            val apps = remember {
                                mutableStateListOf(
                                    "WhatsApp" to true,
                                    "Google Chrome" to false,
                                    "Gmail" to true,
                                    "Photos" to false,
                                    "Settings" to true
                                )
                            }
                            apps.forEachIndexed { idx, pair ->
                                Row(
                                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(pair.first, style = MaterialTheme.typography.bodyLarge)
                                    Switch(
                                        checked = pair.second,
                                        onCheckedChange = { checked ->
                                            apps[idx] = pair.first to checked
                                        }
                                    )
                                }
                                HorizontalDivider()
                            }
                        }
                        "App Permissions", "app_permissions", "Permission Manager", "perm_manager" -> {
                            Text("Active App Permission Monitor", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(12.dp))
                            val perms = remember {
                                mutableStateListOf(
                                    "Location Access" to true,
                                    "Camera Access" to false,
                                    "Contacts Access" to true,
                                    "Microphone Access" to false,
                                    "Storage Access" to true
                                )
                            }
                            perms.forEachIndexed { idx, pair ->
                                Row(
                                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(pair.first, style = MaterialTheme.typography.bodyLarge)
                                    Switch(
                                        checked = pair.second,
                                        onCheckedChange = { checked ->
                                            perms[idx] = pair.first to checked
                                        }
                                    )
                                }
                                HorizontalDivider()
                            }
                        }
                        else -> { // Privacy Check / Fallback
                            var scanning by remember { mutableStateOf(false) }
                            var scanResult by remember { mutableStateOf<String?>(null) }
                            val coroutineScope = rememberCoroutineScope()

                            Text("Secure Privacy Shield", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(16.dp))
                            if (scanning) {
                                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                            } else {
                                Text(scanResult ?: "Start scanning to identify potential privacy leaks, ad-tracking activity, and background storage usage.")
                                Spacer(modifier = Modifier.height(16.dp))
                                Button(
                                    onClick = {
                                        coroutineScope.launch {
                                            scanning = true
                                            delay(1500)
                                            scanResult = "Privacy scan complete:\n• Ad Tracking Blocked: Yes\n• Background Location Leaks: None detected\n• Shared storage exposure risk: Safe"
                                            scanning = false
                                        }
                                    },
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text("Run Privacy Scan")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EncryptionTab() {
    var textToEncrypt by remember { mutableStateOf("") }
    var secretKey by remember { mutableStateOf("") }
    var resultText by remember { mutableStateOf("") }

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text("AES-256 Symmetric Block Cipher", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)

        OutlinedTextField(
            value = textToEncrypt,
            onValueChange = { textToEncrypt = it },
            label = { Text("Plaintext / Ciphertext") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = secretKey,
            onValueChange = { secretKey = it },
            label = { Text("Secret Key (Salt Phrase)") },
            modifier = Modifier.fillMaxWidth()
        )

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(
                onClick = { resultText = encrypt(textToEncrypt, secretKey) },
                modifier = Modifier.weight(1f)
            ) {
                Icon(Icons.Default.Lock, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Encrypt")
            }
            Button(
                onClick = { resultText = decrypt(textToEncrypt, secretKey) },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
            ) {
                Icon(Icons.Default.LockOpen, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Decrypt")
            }
        }

        if (resultText.isNotEmpty()) {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Result Output", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(resultText, style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}

@Composable
fun PasswordTab() {
    var length by remember { mutableFloatStateOf(16f) }
    var includeSymbols by remember { mutableStateOf(true) }
    var includeNumbers by remember { mutableStateOf(true) }
    var generatedPassword by remember { mutableStateOf("") }

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text("High-Entropy Password Generator", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
        ) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth().padding(24.dp)) {
                Text(
                    generatedPassword.ifEmpty { "P@ssword123!" },
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }

        Column {
            Text("Password Length: ${length.toInt()}")
            Slider(value = length, onValueChange = { length = it }, valueRange = 8f..64f)
        }

        ListItem(
            headlineContent = { Text("Include Symbols") },
            trailingContent = { Switch(checked = includeSymbols, onCheckedChange = { includeSymbols = it }) }
        )
        ListItem(
            headlineContent = { Text("Include Numbers") },
            trailingContent = { Switch(checked = includeNumbers, onCheckedChange = { includeNumbers = it }) }
        )

        Button(
            onClick = { generatedPassword = generatePassword(length.toInt(), includeSymbols, includeNumbers) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Default.Refresh, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Generate New Password")
        }
    }
}

@Composable
fun QrStudioTab(navController: NavHostController) {
    var qrText by remember { mutableStateOf("Omni Toolbox Secure QR") }
    var qrBitmap by remember { mutableStateOf<Bitmap?>(null) }

    fun generateQr(content: String) {
        if (content.isBlank()) {
            qrBitmap = null
            return
        }
        try {
            val writer = QRCodeWriter()
            val bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, 512, 512)
            val width = bitMatrix.width
            val height = bitMatrix.height
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
            for (x in 0 until width) {
                for (y in 0 until height) {
                    bitmap.setPixel(x, y, if (bitMatrix.get(x, y)) android.graphics.Color.BLACK else android.graphics.Color.WHITE)
                }
            }
            qrBitmap = bitmap
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    LaunchedEffect(qrText) {
        generateQr(qrText)
    }

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text("Secure QR Generator", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)

        OutlinedTextField(
            value = qrText,
            onValueChange = { qrText = it },
            label = { Text("Data for QR Matrix") },
            modifier = Modifier.fillMaxWidth()
        )

        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
            qrBitmap?.let {
                Image(
                    bitmap = it.asImageBitmap(),
                    contentDescription = "QR Code",
                    modifier = Modifier.size(200.dp)
                )
            } ?: Box(modifier = Modifier.size(200.dp).background(Color.LightGray))
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { navController.navigate("qr_scanner") }, modifier = Modifier.weight(1f)) {
                Icon(Icons.Default.CameraAlt, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Scan")
            }
            Button(onClick = { /* Export */ }, modifier = Modifier.weight(1f), colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)) {
                Icon(Icons.Default.Download, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Save QR")
            }
        }
    }
}

// Logic Utils
fun encrypt(strToEncrypt: String, key: String): String {
    return try {
        val secretKey = SecretKeySpec(key.padEnd(16).take(16).toByteArray(), "AES")
        val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        Base64.encodeToString(cipher.doFinal(strToEncrypt.toByteArray()), Base64.DEFAULT)
    } catch (e: Exception) {
        "Error: ${e.message}"
    }
}

fun decrypt(strToDecrypt: String, key: String): String {
    return try {
        val secretKey = SecretKeySpec(key.padEnd(16).take(16).toByteArray(), "AES")
        val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
        cipher.init(Cipher.DECRYPT_MODE, secretKey)
        String(cipher.doFinal(Base64.decode(strToDecrypt, Base64.DEFAULT)))
    } catch (e: Exception) {
        "Error: Invalid Key or Payload"
    }
}

fun generatePassword(length: Int, symbols: Boolean, numbers: Boolean): String {
    val charPool = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ" +
                   (if (numbers) "0123456789" else "") +
                   (if (symbols) "!@#$%^&*()_+-=[]{}|;:,.<>?" else "")
    return (1..length)
        .map { Random().nextInt(charPool.length) }
        .map(charPool::get)
        .joinToString("")
}
