package omni.toolbox.ui.screens.ai

import android.content.ContentValues
import android.content.Context
import android.graphics.*
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import omni.toolbox.ui.components.ToolScreen
import java.io.OutputStream

@Composable
fun FaceSwapScreen(navController: NavHostController) {
    val context = LocalContext.current
    var sourceImageUri by remember { mutableStateOf<Uri?>(null) }
    var targetImageUri by remember { mutableStateOf<Uri?>(null) }
    var resultBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var isProcessing by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    val sourceLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { sourceImageUri = it }
    val targetLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { targetImageUri = it }

    ToolScreen(title = "Face Swap AI", onBack = { navController.popBackStack() }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                ImagePickerCard("Source Face", sourceImageUri, { sourceLauncher.launch("image/*") }, Modifier.weight(1f))
                ImagePickerCard("Target Image", targetImageUri, { targetLauncher.launch("image/*") }, Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    scope.launch {
                        isProcessing = true
                        try {
                            val sourceBmp = loadBitmap(context, sourceImageUri!!)
                            val targetBmp = loadBitmap(context, targetImageUri!!)
                            if (sourceBmp != null && targetBmp != null) {
                                val result = performFaceSwap(sourceBmp, targetBmp)
                                if (result != null) {
                                    resultBitmap = result
                                } else {
                                    Toast.makeText(context, "Face not detected in one of the images.", Toast.LENGTH_SHORT).show()
                                }
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                            Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                        } finally {
                            isProcessing = false
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isProcessing && sourceImageUri != null && targetImageUri != null
            ) {
                if (isProcessing) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp), color = MaterialTheme.colorScheme.onPrimary)
                } else {
                    Text("Swap Faces")
                }
            }

            if (resultBitmap != null) {
                Spacer(modifier = Modifier.height(32.dp))
                Text("Result", style = MaterialTheme.typography.titleMedium)
                Card(modifier = Modifier.fillMaxWidth().height(300.dp).padding(vertical = 8.dp)) {
                    AsyncImage(
                        model = resultBitmap,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Fit
                    )
                }
                Button(
                    onClick = {
                        scope.launch(Dispatchers.IO) {
                            val saved = saveBitmapToGallery(context, resultBitmap!!)
                            withContext(Dispatchers.Main) {
                                if (saved) Toast.makeText(context, "Image saved to gallery", Toast.LENGTH_SHORT).show()
                                else Toast.makeText(context, "Failed to save image", Toast.LENGTH_SHORT).show()
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.Download, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Download Result")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "Note: This tool uses local ML Kit Face Detection to align faces. For best results, use clear, front-facing photos.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.outline
            )
        }
    }
}

private suspend fun loadBitmap(context: Context, uri: Uri): Bitmap? = withContext(Dispatchers.IO) {
    try {
        val options = BitmapFactory.Options().apply {
            inJustDecodeBounds = true
        }
        context.contentResolver.openInputStream(uri)?.use {
            BitmapFactory.decodeStream(it, null, options)
        }

        var inSampleSize = 1
        val reqWidth = 1024
        val reqHeight = 1024
        if (options.outHeight > reqHeight || options.outWidth > reqWidth) {
            val halfHeight: Int = options.outHeight / 2
            val halfWidth: Int = options.outWidth / 2
            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }

        val finalOptions = BitmapFactory.Options().apply {
            this.inSampleSize = inSampleSize
        }
        context.contentResolver.openInputStream(uri)?.use {
            BitmapFactory.decodeStream(it, null, finalOptions)
        }
    } catch (e: Exception) {
        null
    }
}

private suspend fun performFaceSwap(source: Bitmap, target: Bitmap): Bitmap? = withContext(Dispatchers.Default) {
    val options = FaceDetectorOptions.Builder()
        .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
        .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
        .build()
    val detector = FaceDetection.getClient(options)

    val sourceImage = InputImage.fromBitmap(source, 0)
    val targetImage = InputImage.fromBitmap(target, 0)

    val sourceFaces = detector.process(sourceImage).await()
    val targetFaces = detector.process(targetImage).await()

    if (sourceFaces.isEmpty() || targetFaces.isEmpty()) return@withContext null

    val resultBmp = target.copy(target.config ?: Bitmap.Config.ARGB_8888, true)
    val canvas = Canvas(resultBmp)

    val sourceFace = sourceFaces[0]
    val sourceRect = sourceFace.boundingBox
    val x = sourceRect.left.coerceIn(0, source.width - 1)
    val y = sourceRect.top.coerceIn(0, source.height - 1)
    val width = sourceRect.width().coerceIn(1, source.width - x)
    val height = sourceRect.height().coerceIn(1, source.height - y)

    // Extract source face
    val faceBmp = Bitmap.createBitmap(source, x, y, width, height)

    for (targetFace in targetFaces) {
        val targetRect = targetFace.boundingBox

        // Create a scaled version of the source face
        val scaledFace = Bitmap.createScaledBitmap(faceBmp, targetRect.width(), targetRect.height(), true)

        // Use a mask to blend the face better (oval shape)
        val layer = canvas.saveLayer(
            targetRect.left.toFloat(),
            targetRect.top.toFloat(),
            targetRect.right.toFloat(),
            targetRect.bottom.toFloat(),
            null
        )

        val maskPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        maskPaint.maskFilter = BlurMaskFilter(20f, BlurMaskFilter.Blur.NORMAL)
        canvas.drawOval(
            targetRect.left.toFloat() + 10f,
            targetRect.top.toFloat() + 10f,
            targetRect.right.toFloat() - 10f,
            targetRect.bottom.toFloat() - 10f,
            maskPaint
        )

        maskPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(scaledFace, targetRect.left.toFloat(), targetRect.top.toFloat(), maskPaint)

        canvas.restoreToCount(layer)
    }

    resultBmp
}

private fun saveBitmapToGallery(context: Context, bitmap: Bitmap): Boolean {
    val filename = "FaceSwap_${System.currentTimeMillis()}.jpg"
    var fos: OutputStream? = null
    var success = false
    try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
                put(MediaStore.MediaColumns.RELATIVE_PATH, "Pictures/FaceSwap")
            }
            val imageUri = context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            fos = imageUri?.let { context.contentResolver.openOutputStream(it) }
        } else {
            val imagesDir = android.os.Environment.getExternalStoragePublicDirectory(android.os.Environment.DIRECTORY_PICTURES)
            val image = java.io.File(imagesDir, filename)
            fos = java.io.FileOutputStream(image)
        }
        success = bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos!!)
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        fos?.close()
    }
    return success
}

@Composable
fun ImagePickerCard(label: String, uri: Uri?, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Card(onClick = onClick, modifier = modifier.height(150.dp)) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            if (uri != null) {
                AsyncImage(model = uri, contentDescription = null, modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
            } else {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.AddPhotoAlternate, contentDescription = null)
                    Text(label, style = MaterialTheme.typography.labelSmall)
                }
            }
        }
    }
}
