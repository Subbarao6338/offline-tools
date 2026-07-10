package omni.toolbox.ui.screens.utility

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.UploadFile
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import omni.toolbox.ui.components.ToolScreen
import java.io.InputStream
import java.security.MessageDigest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FileChecksumScreen(navController: NavHostController) {
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current
    val scope = rememberCoroutineScope()

    var selectedFileUri by remember { mutableStateOf<Uri?>(null) }
    var selectedFileName by remember { mutableStateOf("") }
    var selectedFileSize by remember { mutableStateOf("") }

    var md5Hash by remember { mutableStateOf("") }
    var sha1Hash by remember { mutableStateOf("") }
    var sha256Hash by remember { mutableStateOf("") }
    var isCalculating by remember { mutableStateOf(false) }

    var comparisonHash by remember { mutableStateOf("") }

    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            selectedFileUri = uri
            selectedFileName = getFileName(context, uri)
            selectedFileSize = getFileSize(context, uri)

            md5Hash = ""
            sha1Hash = ""
            sha256Hash = ""
            isCalculating = true

            scope.launch {
                val hashes = calculateFileHashes(context, uri)
                md5Hash = hashes["MD5"] ?: "Error"
                sha1Hash = hashes["SHA-1"] ?: "Error"
                sha256Hash = hashes["SHA-256"] ?: "Error"
                isCalculating = false
            }
        }
    }

    ToolScreen(
        title = "Checksum Verifier",
        onBack = { navController.popBackStack() }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Select a file to compute and verify its cryptographic integrity checksums (MD5, SHA-1, SHA-256).",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Button(
                onClick = { filePickerLauncher.launch("*/*") },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Icon(Icons.Default.UploadFile, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Select Local File", fontWeight = FontWeight.Bold)
            }

            if (selectedFileName.isNotEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                ) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text("Selected File Details", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                        Text("Name: $selectedFileName", style = MaterialTheme.typography.bodyMedium)
                        Text("Size: $selectedFileSize", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }

            if (isCalculating) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    CircularProgressIndicator()
                    Text("Calculating Cryptographic Hashes...", style = MaterialTheme.typography.bodyMedium)
                }
            } else if (md5Hash.isNotEmpty()) {
                Text("Computed Checksums", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)

                listOf(
                    "MD5" to md5Hash,
                    "SHA-1" to sha1Hash,
                    "SHA-256" to sha256Hash
                ).forEach { (algo, hash) ->
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text(algo, fontWeight = FontWeight.Bold, fontSize = 12.sp, color = MaterialTheme.colorScheme.secondary)
                                IconButton(
                                    onClick = { clipboardManager.setText(AnnotatedString(hash)) },
                                    modifier = Modifier.size(24.dp)
                                ) {
                                    Icon(Icons.Default.ContentCopy, contentDescription = "Copy Hash", modifier = Modifier.size(16.dp))
                                }
                            }
                            Text(
                                text = hash,
                                fontFamily = FontFamily.Monospace,
                                fontSize = 11.sp,
                                modifier = Modifier.padding(top = 4.dp),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }

                Text("Checksum Verification", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                OutlinedTextField(
                    value = comparisonHash,
                    onValueChange = { comparisonHash = it.trim() },
                    label = { Text("Compare with Target Checksum") },
                    placeholder = { Text("Paste expected MD5, SHA-1, or SHA-256 hash here...") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                if (comparisonHash.isNotEmpty()) {
                    val cleanedComparison = comparisonHash.lowercase()
                    val isMatch = cleanedComparison == md5Hash.lowercase() ||
                            cleanedComparison == sha1Hash.lowercase() ||
                            cleanedComparison == sha256Hash.lowercase()

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isMatch) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.errorContainer
                        )
                    ) {
                        Text(
                            text = if (isMatch) "✓ CHECKSUM MATCH: Integrity Verified!" else "✗ CHECKSUM MISMATCH: Checksum does not match!",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(16.dp),
                            color = if (isMatch) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onErrorContainer,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

private fun getFileName(context: Context, uri: Uri): String {
    var name = ""
    context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
        val nameIndex = cursor.getColumnIndex(android.provider.OpenableColumns.DISPLAY_NAME)
        if (nameIndex != -1 && cursor.moveToFirst()) {
            name = cursor.getString(nameIndex)
        }
    }
    if (name.isEmpty()) {
        name = uri.lastPathSegment ?: "Unknown File"
    }
    return name
}

private fun getFileSize(context: Context, uri: Uri): String {
    var sizeBytes: Long = 0
    context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
        val sizeIndex = cursor.getColumnIndex(android.provider.OpenableColumns.SIZE)
        if (sizeIndex != -1 && cursor.moveToFirst()) {
            sizeBytes = cursor.getLong(sizeIndex)
        }
    }
    if (sizeBytes <= 0) return "Unknown size"
    val k = 1024.0
    val m = k * k
    val g = m * k
    return when {
        sizeBytes >= g -> String.format("%.2f GB", sizeBytes / g)
        sizeBytes >= m -> String.format("%.2f MB", sizeBytes / m)
        sizeBytes >= k -> String.format("%.2f KB", sizeBytes / k)
        else -> "$sizeBytes Bytes"
    }
}

private suspend fun calculateFileHashes(context: Context, uri: Uri): Map<String, String> = withContext(Dispatchers.IO) {
    val hashes = mutableMapOf<String, String>()
    val md5Digest = MessageDigest.getInstance("MD5")
    val sha1Digest = MessageDigest.getInstance("SHA-1")
    val sha256Digest = MessageDigest.getInstance("SHA-256")

    try {
        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
        if (inputStream != null) {
            val buffer = ByteArray(8192)
            inputStream.use { fis ->
                var bytesRead: Int
                while (fis.read(buffer).also { bytesRead = it } != -1) {
                    md5Digest.update(buffer, 0, bytesRead)
                    sha1Digest.update(buffer, 0, bytesRead)
                    sha256Digest.update(buffer, 0, bytesRead)
                }
            }
            hashes["MD5"] = md5Digest.digest().joinToString("") { "%02x".format(it) }
            hashes["SHA-1"] = sha1Digest.digest().joinToString("") { "%02x".format(it) }
            hashes["SHA-256"] = sha256Digest.digest().joinToString("") { "%02x".format(it) }
        } else {
            hashes["MD5"] = "Error reading stream"
            hashes["SHA-1"] = "Error reading stream"
            hashes["SHA-256"] = "Error reading stream"
        }
    } catch (e: Exception) {
        hashes["MD5"] = "Error: ${e.message}"
        hashes["SHA-1"] = "Error: ${e.message}"
        hashes["SHA-256"] = "Error: ${e.message}"
    }
    hashes
}
