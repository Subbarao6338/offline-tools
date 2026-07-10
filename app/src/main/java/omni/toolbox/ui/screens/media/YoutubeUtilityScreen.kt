package omni.toolbox.ui.screens.media

import android.content.Intent
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import omni.toolbox.ui.components.ToolScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YoutubeUtilityScreen(navController: NavHostController) {
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current
    var currentUrl by remember { mutableStateOf("https://m.youtube.com/") }
    var webView: WebView? by remember { mutableStateOf(null) }
    var isLoading by remember { mutableStateOf(false) }

    ToolScreen(
        title = "YouTube Companion",
        onBack = { navController.popBackStack() },
        actions = {
            IconButton(onClick = {
                clipboardManager.setText(AnnotatedString(currentUrl))
            }) {
                Icon(Icons.Default.ContentCopy, contentDescription = "Copy Current URL")
            }
            IconButton(onClick = {
                webView?.reload()
            }) {
                Icon(Icons.Default.Refresh, contentDescription = "Refresh Page")
            }
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            // Top Bar with Background Play trigger
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Background Playback",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Text(
                            text = "Play current video audio in background",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                        )
                    }
                    Button(
                        onClick = {
                            val intent = Intent(context, omni.toolbox.service.YoutubeForegroundService::class.java).apply {
                                putExtra("videoUrl", currentUrl)
                            }
                            androidx.core.content.ContextCompat.startForegroundService(context, intent)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Icon(Icons.Default.PlayArrow, contentDescription = null)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Play", fontWeight = FontWeight.Bold)
                    }
                }
            }

            if (isLoading) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }

            // Custom WebView for browsing YouTube and YouTube Music
            AndroidView(
                factory = { ctx ->
                    WebView(ctx).apply {
                        @Suppress("SetJavaScriptEnabled")
                        settings.javaScriptEnabled = true
                        settings.domStorageEnabled = true
                        settings.mediaPlaybackRequiresUserGesture = false
                        webViewClient = object : WebViewClient() {
                            override fun onPageStarted(view: WebView?, url: String?, favicon: android.graphics.Bitmap?) {
                                isLoading = true
                                if (url != null) {
                                    currentUrl = url
                                }
                            }

                            override fun onPageFinished(view: WebView?, url: String?) {
                                isLoading = false
                                if (url != null) {
                                    currentUrl = url
                                }
                            }
                        }
                        loadUrl(currentUrl)
                        webView = this
                    }
                },
                update = {
                    webView = it
                },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            )
        }
    }
}
