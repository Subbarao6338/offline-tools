package omni.toolbox.ui.screens.media

import android.content.Context
import android.graphics.Bitmap
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.ui.viewinterop.AndroidView
import android.graphics.pdf.PdfDocument
import android.graphics.pdf.PdfRenderer
import android.net.Uri
import android.os.ParcelFileDescriptor
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.background
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import omni.toolbox.ui.components.ToolScreen
import kotlinx.coroutines.*
import com.tom_roush.pdfbox.io.MemoryUsageSetting
import com.tom_roush.pdfbox.multipdf.PDFMergerUtility
import com.tom_roush.pdfbox.multipdf.Splitter
import com.tom_roush.pdfbox.pdmodel.PDDocument
import com.tom_roush.pdfbox.pdmodel.PDDocumentInformation
import com.tom_roush.pdfbox.text.PDFTextStripper
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

@Composable
fun PdfToolScreen(navController: NavHostController, title: String) {
    val context = LocalContext.current
    var selectedFiles by remember { mutableStateOf<List<Uri>>(emptyList()) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetMultipleContents()) {
        selectedFiles = it
    }
    val imageLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetMultipleContents()) {
        selectedFiles = it
    }
    var isProcessing by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    ToolScreen(title = title, onBack = { navController.popBackStack() }, toolRoute = "pdf_tools_group") { padding ->
        val isPreviewMode = title == "Preview PDF" && selectedFiles.isNotEmpty()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(padding)
                .then(
                    if (isPreviewMode) Modifier else Modifier.verticalScroll(rememberScrollState())
                )
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (!isPreviewMode) {
                Icon(Icons.Default.PictureAsPdf, contentDescription = null, modifier = Modifier.size(64.dp), tint = MaterialTheme.colorScheme.primary)
                Spacer(modifier = Modifier.height(16.dp))
                Text(title, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text(getPdfToolDescription(title), style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.outline)

                Spacer(modifier = Modifier.height(24.dp))
            }

            if (selectedFiles.isEmpty() && title != "Text to PDF") {
                Button(onClick = {
                    if (title == "Images to PDF") {
                        imageLauncher.launch("image/*")
                    } else {
                        launcher.launch("application/pdf")
                    }
                }) {
                    Icon(Icons.Default.Add, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(if (title == "Images to PDF") "Select Images" else "Select PDF Files")
                }
            } else if (title == "Preview PDF" && selectedFiles.isNotEmpty()) {
                Column(modifier = Modifier.weight(1f).fillMaxWidth()) {
                    OutlinedButton(
                        onClick = { selectedFiles = emptyList() },
                        modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                    ) {
                        Icon(Icons.Default.Refresh, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Select Another PDF File")
                    }
                    PdfWebViewPreview(
                        uri = selectedFiles[0],
                        modifier = Modifier.weight(1f).fillMaxWidth()
                    )
                }
            } else if (selectedFiles.isNotEmpty() || title == "Text to PDF") {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("${selectedFiles.size} file(s) selected", fontWeight = FontWeight.Bold)
                        selectedFiles.forEach { uri ->
                            Text(uri.lastPathSegment ?: "Unknown file", style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                var pageRanges by remember { mutableStateOf("") }
                var password by remember { mutableStateOf("") }
                var rotationAngle by remember { mutableIntStateOf(90) }
                var pdfTitle by remember { mutableStateOf("") }
                var pdfAuthor by remember { mutableStateOf("") }
                var pdfSubject by remember { mutableStateOf("") }
                var pdfKeywords by remember { mutableStateOf("") }
                var textContent by remember { mutableStateOf("") }

                PdfToolOptions(
                    title,
                    pageRanges, { pageRanges = it },
                    password, { password = it },
                    rotationAngle, { rotationAngle = it },
                    pdfTitle, { pdfTitle = it },
                    pdfAuthor, { pdfAuthor = it },
                    pdfSubject, { pdfSubject = it },
                    pdfKeywords, { pdfKeywords = it },
                    textContent, { textContent = it }
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        isProcessing = true
                        scope.launch(Dispatchers.IO) {
                            try {
                                val outputDir = File(context.cacheDir, "pdf_output")
                                if (!outputDir.exists()) outputDir.mkdirs()

                                when (title) {
                                    "Merge PDF" -> {
                                        if (selectedFiles.size < 2) {
                                            withContext(Dispatchers.Main) {
                                                Toast.makeText(context, "Please select at least 2 files to merge", Toast.LENGTH_SHORT).show()
                                            }
                                        } else {
                                            val merger = PDFMergerUtility()
                                            val outPath = File(outputDir, "merged_${System.currentTimeMillis()}.pdf")
                                            merger.destinationFileName = outPath.absolutePath
                                            selectedFiles.forEach { uri ->
                                                context.contentResolver.openInputStream(uri)?.use { inputStream ->
                                                    merger.addSource(inputStream)
                                                }
                                            }
                                            merger.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly())
                                            withContext(Dispatchers.Main) {
                                                Toast.makeText(context, "Merged PDF saved to ${outPath.name}", Toast.LENGTH_LONG).show()
                                            }
                                        }
                                    }
                                    "Crop PDF" -> {
                                        if (selectedFiles.isNotEmpty()) {
                                            val tempFile = File(context.cacheDir, "temp_crop.pdf")
                                            context.contentResolver.openInputStream(selectedFiles[0])?.use { input ->
                                                tempFile.outputStream().use { output -> input.copyTo(output) }
                                            }
                                            val outPath = File(outputDir, "cropped_${System.currentTimeMillis()}.pdf")
                                            omni.toolbox.utils.PdfUtils.cropPdf(tempFile, outPath)
                                            withContext(Dispatchers.Main) {
                                                Toast.makeText(context, "Cropped PDF: ${outPath.name}", Toast.LENGTH_LONG).show()
                                            }
                                        }
                                    }
                                    "Zip PDF" -> {
                                        if (selectedFiles.isNotEmpty()) {
                                            val tempFile = File(context.cacheDir, "temp_zip.pdf")
                                            context.contentResolver.openInputStream(selectedFiles[0])?.use { input ->
                                                tempFile.outputStream().use { output -> input.copyTo(output) }
                                            }
                                            val outPath = File(outputDir, "zipped_${System.currentTimeMillis()}.zip")
                                            omni.toolbox.utils.PdfUtils.zipPdf(tempFile, outPath)
                                            withContext(Dispatchers.Main) {
                                                Toast.makeText(context, "PDF Zipped: ${outPath.name}", Toast.LENGTH_LONG).show()
                                            }
                                        }
                                    }
                                    "Print PDF" -> {
                                        if (selectedFiles.isNotEmpty()) {
                                            withContext(Dispatchers.Main) {
                                                val printManager = context.getSystemService(Context.PRINT_SERVICE) as android.print.PrintManager
                                                val jobName = "${context.getString(omni.toolbox.R.string.app_name)} Document"
                                                printManager.print(jobName, object : android.print.PrintDocumentAdapter() {
                                                    override fun onLayout(oldAttributes: android.print.PrintAttributes?, newAttributes: android.print.PrintAttributes?, cancellationSignal: android.os.CancellationSignal?, callback: LayoutResultCallback?, extras: android.os.Bundle?) {
                                                        if (cancellationSignal?.isCanceled == true) {
                                                            callback?.onLayoutCancelled()
                                                            return
                                                        }
                                                        val info = android.print.PrintDocumentInfo.Builder(jobName)
                                                            .setContentType(android.print.PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
                                                            .build()
                                                        callback?.onLayoutFinished(info, true)
                                                    }

                                                    override fun onWrite(pages: Array<out android.print.PageRange>?, destination: android.os.ParcelFileDescriptor?, cancellationSignal: android.os.CancellationSignal?, callback: WriteResultCallback?) {
                                                        try {
                                                            context.contentResolver.openInputStream(selectedFiles[0])?.use { input ->
                                                                FileOutputStream(destination?.fileDescriptor).use { output ->
                                                                    input.copyTo(output)
                                                                }
                                                            }
                                                            callback?.onWriteFinished(arrayOf(android.print.PageRange.ALL_PAGES))
                                                        } catch (e: Exception) {
                                                            callback?.onWriteFailed(e.message)
                                                        }
                                                    }
                                                }, null)
                                            }
                                        }
                                    }
                                    "Text to PDF" -> {
                                        if (textContent.isNotEmpty()) {
                                            val outPath = File(outputDir, "text_${System.currentTimeMillis()}.pdf")
                                            omni.toolbox.utils.PdfUtils.textToPdf(textContent, outPath)
                                            withContext(Dispatchers.Main) {
                                                Toast.makeText(context, "PDF created: ${outPath.name}", Toast.LENGTH_LONG).show()
                                            }
                                        } else {
                                            withContext(Dispatchers.Main) {
                                                Toast.makeText(context, "Please enter some text", Toast.LENGTH_SHORT).show()
                                            }
                                        }
                                    }
                                    "Images to PDF" -> {
                                        if (selectedFiles.isNotEmpty()) {
                                            val outPath = File(outputDir, "converted_${System.currentTimeMillis()}.pdf")
                                            omni.toolbox.utils.PdfUtils.imagesToPdf(context, selectedFiles, outPath)
                                            withContext(Dispatchers.Main) {
                                                Toast.makeText(context, "PDF created: ${outPath.name}", Toast.LENGTH_LONG).show()
                                            }
                                        }
                                    }
                                    "Invert PDF" -> {
                                        if (selectedFiles.isNotEmpty()) {
                                            val tempFile = File(context.cacheDir, "temp_invert.pdf")
                                            context.contentResolver.openInputStream(selectedFiles[0])?.use { input ->
                                                tempFile.outputStream().use { output -> input.copyTo(output) }
                                            }
                                            val outPath = File(outputDir, "inverted_${System.currentTimeMillis()}.pdf")
                                            omni.toolbox.utils.PdfUtils.invert(tempFile, outPath)
                                            withContext(Dispatchers.Main) {
                                                Toast.makeText(context, "Inverted PDF: ${outPath.name}", Toast.LENGTH_LONG).show()
                                            }
                                        }
                                    }
                                    "Compress PDF" -> {
                                        if (selectedFiles.isNotEmpty()) {
                                            val tempFile = File(context.cacheDir, "temp_compress.pdf")
                                            context.contentResolver.openInputStream(selectedFiles[0])?.use { input ->
                                                tempFile.outputStream().use { output -> input.copyTo(output) }
                                            }
                                            val outPath = File(outputDir, "compressed_${System.currentTimeMillis()}.pdf")
                                            omni.toolbox.utils.PdfUtils.compress(tempFile, outPath)
                                            withContext(Dispatchers.Main) {
                                                Toast.makeText(context, "Compressed PDF: ${outPath.name}", Toast.LENGTH_LONG).show()
                                            }
                                        }
                                    }
                                    "Flatten PDF" -> {
                                        if (selectedFiles.isNotEmpty()) {
                                            val tempFile = File(context.cacheDir, "temp_flatten.pdf")
                                            context.contentResolver.openInputStream(selectedFiles[0])?.use { input ->
                                                tempFile.outputStream().use { output -> input.copyTo(output) }
                                            }
                                            val outPath = File(outputDir, "flattened_${System.currentTimeMillis()}.pdf")
                                            omni.toolbox.utils.PdfUtils.flatten(tempFile, outPath)
                                            withContext(Dispatchers.Main) {
                                                Toast.makeText(context, "Flattened PDF: ${outPath.name}", Toast.LENGTH_LONG).show()
                                            }
                                        }
                                    }
                                    "Grayscale PDF" -> {
                                        if (selectedFiles.isNotEmpty()) {
                                            val tempFile = File(context.cacheDir, "temp_grayscale.pdf")
                                            context.contentResolver.openInputStream(selectedFiles[0])?.use { input ->
                                                tempFile.outputStream().use { output -> input.copyTo(output) }
                                            }
                                            val outPath = File(outputDir, "grayscale_${System.currentTimeMillis()}.pdf")
                                            omni.toolbox.utils.PdfUtils.grayscale(tempFile, outPath)
                                            withContext(Dispatchers.Main) {
                                                Toast.makeText(context, "Grayscale PDF: ${outPath.name}", Toast.LENGTH_LONG).show()
                                            }
                                        }
                                    }
                                    "Rotate PDF" -> {
                                        if (selectedFiles.isNotEmpty()) {
                                            val tempFile = File(context.cacheDir, "temp_rotate.pdf")
                                            context.contentResolver.openInputStream(selectedFiles[0])?.use { input ->
                                                tempFile.outputStream().use { output -> input.copyTo(output) }
                                            }
                                            val outPath = File(outputDir, "rotated_${System.currentTimeMillis()}.pdf")
                                            omni.toolbox.utils.PdfUtils.rotatePages(tempFile, rotationAngle, outPath)
                                            withContext(Dispatchers.Main) {
                                                Toast.makeText(context, "Rotated PDF: ${outPath.name}", Toast.LENGTH_LONG).show()
                                            }
                                        }
                                    }
                                    "Protect PDF" -> {
                                        if (selectedFiles.isNotEmpty()) {
                                            val tempFile = File(context.cacheDir, "temp_protect.pdf")
                                            context.contentResolver.openInputStream(selectedFiles[0])?.use { input ->
                                                tempFile.outputStream().use { output -> input.copyTo(output) }
                                            }
                                            val outPath = File(outputDir, "protected_${System.currentTimeMillis()}.pdf")
                                            omni.toolbox.utils.PdfUtils.protect(tempFile, password, outPath)
                                            withContext(Dispatchers.Main) {
                                                Toast.makeText(context, "Protected PDF: ${outPath.name}", Toast.LENGTH_LONG).show()
                                            }
                                        }
                                    }
                                    "Unlock PDF" -> {
                                        if (selectedFiles.isNotEmpty()) {
                                            val tempFile = File(context.cacheDir, "temp_unlock.pdf")
                                            context.contentResolver.openInputStream(selectedFiles[0])?.use { input ->
                                                tempFile.outputStream().use { output -> input.copyTo(output) }
                                            }
                                            val outPath = File(outputDir, "unlocked_${System.currentTimeMillis()}.pdf")
                                            omni.toolbox.utils.PdfUtils.unlock(tempFile, password, outPath)
                                            withContext(Dispatchers.Main) {
                                                Toast.makeText(context, "Unlocked PDF: ${outPath.name}", Toast.LENGTH_LONG).show()
                                            }
                                        }
                                    }
                                    "Repair PDF" -> {
                                        if (selectedFiles.isNotEmpty()) {
                                            val tempFile = File(context.cacheDir, "temp_repair.pdf")
                                            context.contentResolver.openInputStream(selectedFiles[0])?.use { input ->
                                                tempFile.outputStream().use { output -> input.copyTo(output) }
                                            }
                                            val outPath = File(outputDir, "repaired_${System.currentTimeMillis()}.pdf")
                                            omni.toolbox.utils.PdfUtils.repair(tempFile, outPath)
                                            withContext(Dispatchers.Main) {
                                                Toast.makeText(context, "Repaired PDF: ${outPath.name}", Toast.LENGTH_LONG).show()
                                            }
                                        }
                                    }
                                    "Extract Images PDF" -> {
                                        if (selectedFiles.isNotEmpty()) {
                                            val tempFile = File(context.cacheDir, "temp_extract.pdf")
                                            context.contentResolver.openInputStream(selectedFiles[0])?.use { input ->
                                                tempFile.outputStream().use { output -> input.copyTo(output) }
                                            }
                                            val exported = omni.toolbox.utils.PdfUtils.exportToImages(tempFile, outputDir)
                                            withContext(Dispatchers.Main) {
                                                Toast.makeText(context, "Extracted ${exported.size} images to ${outputDir.name}", Toast.LENGTH_LONG).show()
                                            }
                                        }
                                    }
                                    "Split PDF" -> {
                                        if (selectedFiles.isNotEmpty()) {
                                            context.contentResolver.openInputStream(selectedFiles[0])?.use { inputStream ->
                                                val document = PDDocument.load(inputStream)
                                                val splitter = Splitter()
                                                val pages: List<PDDocument> = splitter.split(document)
                                                pages.forEachIndexed { index, doc ->
                                                    val outPath = File(outputDir, "split_${index}_${System.currentTimeMillis()}.pdf")
                                                    doc.save(outPath)
                                                    doc.close()
                                                }
                                                document.close()
                                                withContext(Dispatchers.Main) {
                                                    Toast.makeText(context, "Split into ${pages.size} files", Toast.LENGTH_LONG).show()
                                                }
                                            }
                                        }
                                    }
                                    "PDF Metadata" -> {
                                        if (selectedFiles.isNotEmpty()) {
                                            context.contentResolver.openInputStream(selectedFiles[0])?.use { inputStream ->
                                                val document = PDDocument.load(inputStream)
                                                val info = document.documentInformation ?: PDDocumentInformation()
                                                info.setTitle(pdfTitle)
                                                info.setAuthor(pdfAuthor)
                                                info.setSubject(pdfSubject)
                                                info.setKeywords(pdfKeywords)
                                                document.documentInformation = info
                                                val outPath = File(outputDir, "updated_${System.currentTimeMillis()}.pdf")
                                                document.save(outPath)
                                                document.close()
                                                withContext(Dispatchers.Main) {
                                                    Toast.makeText(context, "Metadata updated: ${outPath.name}", Toast.LENGTH_LONG).show()
                                                }
                                            }
                                        }
                                    }
                                    "Remove PDF pages" -> {
                                        if (selectedFiles.isNotEmpty()) {
                                            val tempFile = File(context.cacheDir, "temp_remove.pdf")
                                            context.contentResolver.openInputStream(selectedFiles[0])?.use { input ->
                                                tempFile.outputStream().use { output -> input.copyTo(output) }
                                            }
                                            val outPath = File(outputDir, "removed_${System.currentTimeMillis()}.pdf")
                                            omni.toolbox.utils.PdfUtils.removePages(tempFile, pageRanges, outPath)
                                            withContext(Dispatchers.Main) {
                                                Toast.makeText(context, "Pages removed: ${outPath.name}", Toast.LENGTH_LONG).show()
                                            }
                                        }
                                    }
                                    "Page Numbers" -> {
                                        if (selectedFiles.isNotEmpty()) {
                                            val tempFile = File(context.cacheDir, "temp_numbers.pdf")
                                            context.contentResolver.openInputStream(selectedFiles[0])?.use { input ->
                                                tempFile.outputStream().use { output -> input.copyTo(output) }
                                            }
                                            val outPath = File(outputDir, "numbered_${System.currentTimeMillis()}.pdf")
                                            omni.toolbox.utils.PdfUtils.addPageNumbers(tempFile, outPath)
                                            withContext(Dispatchers.Main) {
                                                Toast.makeText(context, "Page numbers added: ${outPath.name}", Toast.LENGTH_LONG).show()
                                            }
                                        }
                                    }
                                    "Watermarking PDF" -> {
                                        if (selectedFiles.isNotEmpty()) {
                                            val tempFile = File(context.cacheDir, "temp_watermark.pdf")
                                            context.contentResolver.openInputStream(selectedFiles[0])?.use { input ->
                                                tempFile.outputStream().use { output -> input.copyTo(output) }
                                            }
                                            val outPath = File(outputDir, "watermarked_${System.currentTimeMillis()}.pdf")
                                            omni.toolbox.utils.PdfUtils.addWatermark(tempFile, textContent, outPath)
                                            withContext(Dispatchers.Main) {
                                                Toast.makeText(context, "Watermark added: ${outPath.name}", Toast.LENGTH_LONG).show()
                                            }
                                        }
                                    }
                                    "PDF to MDX" -> {
                                        if (selectedFiles.isNotEmpty()) {
                                            context.contentResolver.openInputStream(selectedFiles[0])?.use { inputStream ->
                                                val document = PDDocument.load(inputStream)
                                                val stripper = PDFTextStripper()
                                                val rawText = stripper.getText(document)
                                                document.close()

                                                val mdxContent = buildString {
                                                    append("---")
                                                    append("\ntitle: ${selectedFiles[0].lastPathSegment ?: "Converted PDF"}")
                                                    append("\ndate: ${java.util.Date()}")
                                                    append("\n---\n\n")

                                                    rawText.lines().forEach { line ->
                                                        val trimmed = line.trim()
                                                        if (trimmed.isEmpty()) return@forEach
                                                        if (trimmed.length < 50 && trimmed.any { it.isUpperCase() } && !trimmed.endsWith(".")) {
                                                            append("## $trimmed\n")
                                                        } else if (trimmed.startsWith("•") || trimmed.startsWith("-")) {
                                                            append("* ${trimmed.substring(1).trim()}\n")
                                                        } else {
                                                            append(trimmed)
                                                            append("\n\n")
                                                        }
                                                    }
                                                }
                                                val outPath = File(outputDir, "docling_${System.currentTimeMillis()}.mdx")
                                                outPath.writeText(mdxContent)
                                                withContext(Dispatchers.Main) {
                                                    Toast.makeText(context, "Locally converted to MDX: ${outPath.name}", Toast.LENGTH_LONG).show()
                                                }
                                            }
                                        }
                                    }
                                    else -> {
                                        delay(1500)
                                        withContext(Dispatchers.Main) {
                                            Toast.makeText(context, "Feature '$title' not fully supported in this version.", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                }
                            } catch (e: Exception) {
                                withContext(Dispatchers.Main) {
                                    Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                                }
                            } finally {
                                isProcessing = false
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isProcessing,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    if (isProcessing) {
                        CircularProgressIndicator(modifier = Modifier.size(24.dp), color = MaterialTheme.colorScheme.onPrimary)
                    } else {
                        val actionLabel = when(title) {
                            "Flatten PDF" -> "Flatten & Secure"
                            "Grayscale PDF" -> "Convert to Grayscale"
                            "PDF Metadata" -> "Update Metadata"
                            "Merge PDF" -> "Merge Selected Files"
                            "Split PDF" -> "Split Into Pages"
                            "Text to PDF" -> "Convert to PDF"
                            "PDF to MDX" -> "Convert to MDX"
                            "PDF to MHTML" -> "Convert to MHTML"
                            else -> "Process PDF"
                        }
                        Text(actionLabel)
                    }
                }

                OutlinedButton(
                    onClick = { selectedFiles = emptyList() },
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
                ) {
                    Text("Clear Selection")
                }
            }
        }
    }
}

@Composable
fun PdfToolOptions(
    title: String,
    pageRanges: String, onPageRangesChange: (String) -> Unit,
    password: String, onPasswordChange: (String) -> Unit,
    rotation: Int, onRotationChange: (Int) -> Unit,
    pdfTitle: String, onPdfTitleChange: (String) -> Unit,
    pdfAuthor: String, onPdfAuthorChange: (String) -> Unit,
    pdfSubject: String, onPdfSubjectChange: (String) -> Unit,
    pdfKeywords: String, onPdfKeywordsChange: (String) -> Unit,
    textContent: String, onTextContentChange: (String) -> Unit
) {
    when (title) {
        "Split PDF", "Remove PDF pages" -> {
            OutlinedTextField(value = pageRanges, onValueChange = onPageRangesChange, label = { Text("Page Ranges (e.g., 1-5, 8, 11-13)") }, modifier = Modifier.fillMaxWidth())
        }
        "Merge PDF" -> {
            Text("Files will be merged in the order they were selected.")
        }
        "Protect PDF" -> {
            OutlinedTextField(value = password, onValueChange = onPasswordChange, label = { Text("Set Password") }, modifier = Modifier.fillMaxWidth())
        }
        "Rotate PDF" -> {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Rotation Angle:")
                Spacer(modifier = Modifier.width(16.dp))
                listOf(90, 180, 270).forEach { angle ->
                    FilterChip(
                        selected = rotation == angle,
                        onClick = { onRotationChange(angle) },
                        label = { Text("$angle°") },
                        modifier = Modifier.padding(end = 8.dp)
                    )
                }
            }
        }
        "PDF Metadata" -> {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = pdfTitle, onValueChange = onPdfTitleChange, label = { Text("Title") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = pdfAuthor, onValueChange = onPdfAuthorChange, label = { Text("Author") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = pdfSubject, onValueChange = onPdfSubjectChange, label = { Text("Subject") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = pdfKeywords, onValueChange = onPdfKeywordsChange, label = { Text("Keywords") }, modifier = Modifier.fillMaxWidth())
            }
        }
        "Flatten PDF" -> {
            Text("This will make all form fields and annotations permanent and non-editable.")
        }
        "Grayscale PDF" -> {
            Text("This will convert all colored elements in the PDF to shades of gray to save ink or reduce complexity.")
        }
        "HTML to PDF" -> {
            OutlinedTextField(value = "", onValueChange = {}, label = { Text("Enter Web URL") }, modifier = Modifier.fillMaxWidth())
        }
        "Text to PDF" -> {
            OutlinedTextField(value = textContent, onValueChange = onTextContentChange, label = { Text("Enter or paste text content") }, modifier = Modifier.fillMaxWidth(), minLines = 5)
        }
        "Watermarking PDF" -> {
            OutlinedTextField(value = textContent, onValueChange = onTextContentChange, label = { Text("Watermark Text") }, modifier = Modifier.fillMaxWidth())
        }
        "Invert PDF" -> {
            Text("Invert colors for night mode reading or high contrast viewing.")
        }
        "PDF to MDX" -> {
            Text("Convert PDF documents to MDX (Markdown with JSX) format.")
        }
        "PDF to MHTML" -> {
            Text("Convert PDF documents to MHTML web archive format.")
        }
    }
}

fun getPdfToolDescription(title: String): String {
    return when (title) {
        "Merge PDF" -> "Combine multiple PDF documents into one."
        "Split PDF" -> "Divide a PDF into separate files by page ranges."
        "Rotate PDF" -> "Rotate PDF pages in 90-degree increments."
        "Protect PDF" -> "Add password protection to your PDF."
        "Unlock PDF" -> "Remove password protection from a PDF."
        "PDF Metadata" -> "Edit PDF Title, Author, Subject, and Keywords."
        "Compress PDF" -> "Reduce file size by optimizing PDF streams."
        "Grayscale PDF" -> "Convert all PDF pages to black and white."
        "Flatten PDF" -> "Make forms and annotations permanent."
        "PDF to MDX" -> "Convert PDF documents to MDX (Markdown with JSX) format."
        "PDF to MHTML" -> "Convert PDF documents to MHTML web archive format."
        else -> "Professional PDF manipulation tool."
    }
}

@Composable
fun PdfWebViewPreview(uri: Uri, modifier: Modifier = Modifier) {
    val context = LocalContext.current

    AndroidView(
        factory = { ctx ->
            WebView(ctx).apply {
                webViewClient = object : android.webkit.WebViewClient() {
                    override fun shouldInterceptRequest(
                        view: WebView?,
                        request: android.webkit.WebResourceRequest?
                    ): android.webkit.WebResourceResponse? {
                        val url = request?.url?.toString() ?: return null
                        if (url == "https://localpdf/document.pdf") {
                            try {
                                val inputStream = context.contentResolver.openInputStream(uri)
                                return android.webkit.WebResourceResponse("application/pdf", "UTF-8", inputStream)
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                        return super.shouldInterceptRequest(view, request)
                    }
                }
                @Suppress("SetJavaScriptEnabled")
                settings.javaScriptEnabled = true
                settings.allowFileAccess = true
                settings.allowContentAccess = true
                @Suppress("deprecation")
                settings.allowUniversalAccessFromFileURLs = true
                @Suppress("deprecation")
                settings.allowFileAccessFromFileURLs = true
                settings.builtInZoomControls = true
                settings.displayZoomControls = false
                settings.setSupportZoom(true)
                loadUrl("file:///android_asset/pdf_viewer.html")
            }
        },
        update = {
            // No-op to prevent infinite recomposition reloads
        },
        modifier = modifier.fillMaxSize()
    )
}
