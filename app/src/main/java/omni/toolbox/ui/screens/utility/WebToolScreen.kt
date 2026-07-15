package omni.toolbox.ui.screens.utility

import android.content.Context
import android.net.ConnectivityManager
import android.content.Intent
import android.net.NetworkCapabilities
import android.net.Uri
import android.webkit.CookieManager
import android.webkit.GeolocationPermissions
import android.webkit.PermissionRequest
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.webkit.WebViewCompat
import androidx.webkit.WebViewFeature
import androidx.webkit.ProfileStore
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import omni.toolbox.ui.components.ToolScreen
import java.net.URL

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WebToolScreen(
    navController: NavHostController,
    initialUrl: String? = null,
    showUrlBar: Boolean = true,
    title: String = "Web Search"
) {
    val context = LocalContext.current
    val defaultUrl = initialUrl ?: "https://www.google.com"
    var urlInput by remember { mutableStateOf(defaultUrl) }
    var urlToLoad by remember { mutableStateOf(defaultUrl) }
    var isOffline by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var isDesktopMode by remember { mutableStateOf(false) }
    var showAnalysis by remember { mutableStateOf(false) }
    val prefs = remember { context.getSharedPreferences("web_profiles", Context.MODE_PRIVATE) }
    var currentProfile by remember { mutableStateOf(prefs.getString("web_profile_$title", "Default") ?: "Default") }
    var showProfileSwitcher by remember { mutableStateOf(false) }
    var webView: WebView? by remember { mutableStateOf(null) }
    var canGoBack by remember { mutableStateOf(false) }
    var showMenuSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    fun clearProfileCookies() {
        if (WebViewFeature.isFeatureSupported(WebViewFeature.MULTI_PROFILE)) {
            val profile = ProfileStore.getInstance().getOrCreateProfile(currentProfile)
            profile.cookieManager.removeAllCookies(null)
            profile.cookieManager.flush()
        } else {
            CookieManager.getInstance().removeAllCookies(null)
            CookieManager.getInstance().flush()
        }
        android.widget.Toast.makeText(context, "Cookies cleared for $currentProfile", android.widget.Toast.LENGTH_SHORT).show()
    }

    fun clearProfileCache() {
        webView?.clearCache(true)
        android.widget.Toast.makeText(context, "Cache cleared", android.widget.Toast.LENGTH_SHORT).show()
    }

    fun clearProfileData() {
        if (WebViewFeature.isFeatureSupported(WebViewFeature.MULTI_PROFILE)) {
            val profile = ProfileStore.getInstance().getOrCreateProfile(currentProfile)
            profile.webStorage.deleteAllData()
        } else {
            android.webkit.WebStorage.getInstance().deleteAllData()
        }
        webView?.clearFormData()
        webView?.clearHistory()
        webView?.clearSslPreferences()
        android.widget.Toast.makeText(context, "Data cleared for $currentProfile", android.widget.Toast.LENGTH_SHORT).show()
    }

    val desktopUserAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36"
    val mobileUserAgent = remember {
        WebView(context).settings.userAgentString
            .replace("; wv", "")
            .replace("Version/4.0 ", "")
    }

    fun checkConnectivity(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = cm.activeNetwork ?: return false
        val capabilities = cm.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    LaunchedEffect(urlToLoad, webView) {
        isOffline = !checkConnectivity()
        if (!isOffline && webView != null) {
            val currentUrl = webView?.url?.removeSuffix("/")
            val targetUrl = urlToLoad.removeSuffix("/")
            if (currentUrl != targetUrl) {
                webView?.loadUrl(urlToLoad)
            }
        }
    }

    BackHandler(enabled = canGoBack) {
        webView?.goBack()
    }

    val actions: @Composable RowScope.() -> Unit = {
        IconButton(onClick = { showProfileSwitcher = true }) {
            Icon(Icons.Default.AccountCircle, contentDescription = "Switch Profile", tint = if (currentProfile == "Default") LocalContentColor.current else MaterialTheme.colorScheme.primary)
        }
        IconButton(onClick = { showAnalysis = !showAnalysis }) {
            Icon(Icons.Default.Info, contentDescription = "Web Analysis")
        }
        IconButton(onClick = {
            isDesktopMode = !isDesktopMode
            webView?.settings?.userAgentString = if (isDesktopMode) desktopUserAgent else mobileUserAgent
            webView?.reload()
        }) {
            Icon(
                if (isDesktopMode) Icons.Default.Smartphone else Icons.Default.DesktopWindows,
                contentDescription = if (isDesktopMode) "Mobile Site" else "Desktop Site"
            )
        }
        IconButton(onClick = {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(webView?.url ?: urlToLoad))
            context.startActivity(intent)
        }) {
            Icon(Icons.Default.OpenInBrowser, contentDescription = "Open in Browser")
        }
        IconButton(onClick = { webView?.reload() }) {
            Icon(Icons.Default.Refresh, contentDescription = "Refresh")
        }
        IconButton(onClick = {
            val currentUrl = webView?.url ?: urlToLoad
            navController.navigate("media_grabber?url=$currentUrl")
        }) {
            Icon(Icons.Default.Download, contentDescription = "Grab Media")
        }
    }

    ToolScreen(
        title = title,
        onBack = {
            if (canGoBack) {
                webView?.goBack()
            } else {
                navController.popBackStack()
            }
        },
        actions = actions,
        showTopBar = showUrlBar,
        floatingActionButton = {
            if (!showUrlBar) {
                FloatingActionButton(onClick = { showMenuSheet = true }) {
                    Icon(Icons.Default.Menu, contentDescription = "Menu")
                }
            }
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            if (showUrlBar) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = urlInput,
                        onValueChange = { urlInput = it },
                        modifier = Modifier.weight(1f),
                        label = { Text("URL") },
                        leadingIcon = { Icon(Icons.Default.Language, contentDescription = null) }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = {
                        urlToLoad = if (urlInput.startsWith("http")) urlInput else "https://$urlInput"
                    }) {
                        Text("Go")
                    }
                }
            }

            if (isLoading) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }

            if (showProfileSwitcher) {
                AlertDialog(
                    onDismissRequest = { showProfileSwitcher = false },
                    title = { Text("Switch Profile") },
                    text = {
                        Column {
                            listOf("Default", "Profile 2", "Profile 3", "Work", "Private").forEach { profile ->
                                Row(
                                    modifier = Modifier.fillMaxWidth().clickable {
                                        currentProfile = profile
                                        prefs.edit().putString("web_profile_$title", profile).apply()
                                        if (!WebViewFeature.isFeatureSupported(WebViewFeature.MULTI_PROFILE)) {
                                            CookieManager.getInstance().removeAllCookies(null)
                                        }
                                        webView?.reload()
                                        showProfileSwitcher = false
                                    }.padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    RadioButton(selected = currentProfile == profile, onClick = null)
                                    Spacer(Modifier.width(8.dp))
                                    Text(profile)
                                }
                            }
                        }
                    },
                    confirmButton = { TextButton(onClick = { showProfileSwitcher = false }) { Text("Close") } }
                )
            }

            if (showMenuSheet) {
                ModalBottomSheet(
                    onDismissRequest = { showMenuSheet = false },
                    sheetState = sheetState
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 32.dp)
                    ) {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(16.dp)
                        )
                        HorizontalDivider()

                        ListItem(
                            headlineContent = { Text("Go Back") },
                            leadingContent = { Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null) },
                            modifier = Modifier.clickable {
                                showMenuSheet = false
                                if (canGoBack) {
                                    webView?.goBack()
                                } else {
                                    navController.popBackStack()
                                }
                            }
                        )

                        ListItem(
                            headlineContent = { Text("Back to Tool Hub") },
                            leadingContent = { Icon(Icons.Default.Home, contentDescription = null) },
                            modifier = Modifier.clickable {
                                showMenuSheet = false
                                navController.popBackStack()
                            }
                        )

                        ListItem(
                            headlineContent = { Text("Switch Profile") },
                            supportingContent = { Text("Current: $currentProfile") },
                            leadingContent = { Icon(Icons.Default.AccountCircle, contentDescription = null) },
                            modifier = Modifier.clickable {
                                showMenuSheet = false
                                showProfileSwitcher = true
                            }
                        )

                        ListItem(
                            headlineContent = { Text("Web Analysis") },
                            leadingContent = { Icon(Icons.Default.Info, contentDescription = null) },
                            modifier = Modifier.clickable {
                                showMenuSheet = false
                                showAnalysis = !showAnalysis
                            }
                        )

                        ListItem(
                            headlineContent = { Text(if (isDesktopMode) "Mobile Site" else "Desktop Site") },
                            leadingContent = { Icon(if (isDesktopMode) Icons.Default.Smartphone else Icons.Default.DesktopWindows, contentDescription = null) },
                            modifier = Modifier.clickable {
                                showMenuSheet = false
                                isDesktopMode = !isDesktopMode
                                webView?.settings?.userAgentString = if (isDesktopMode) desktopUserAgent else mobileUserAgent
                                webView?.reload()
                            }
                        )

                        ListItem(
                            headlineContent = { Text("Open in Browser") },
                            leadingContent = { Icon(Icons.Default.OpenInBrowser, contentDescription = null) },
                            modifier = Modifier.clickable {
                                showMenuSheet = false
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(webView?.url ?: urlToLoad))
                                context.startActivity(intent)
                            }
                        )

                        ListItem(
                            headlineContent = { Text("Refresh") },
                            leadingContent = { Icon(Icons.Default.Refresh, contentDescription = null) },
                            modifier = Modifier.clickable {
                                showMenuSheet = false
                                webView?.reload()
                            }
                        )

                        ListItem(
                            headlineContent = { Text("Grab Media") },
                            leadingContent = { Icon(Icons.Default.Download, contentDescription = null) },
                            modifier = Modifier.clickable {
                                showMenuSheet = false
                                val currentUrl = webView?.url ?: urlToLoad
                                navController.navigate("media_grabber?url=$currentUrl")
                            }
                        )

                        HorizontalDivider()

                        ListItem(
                            headlineContent = { Text("Clear Cookies") },
                            supportingContent = { Text("Delete login sessions & cookies") },
                            leadingContent = { Icon(Icons.Default.Cookie, contentDescription = null) },
                            modifier = Modifier.clickable {
                                showMenuSheet = false
                                clearProfileCookies()
                            }
                        )

                        ListItem(
                            headlineContent = { Text("Clear Cache") },
                            supportingContent = { Text("Delete cached images & temporary files") },
                            leadingContent = { Icon(Icons.Default.DeleteOutline, contentDescription = null) },
                            modifier = Modifier.clickable {
                                showMenuSheet = false
                                clearProfileCache()
                            }
                        )

                        ListItem(
                            headlineContent = { Text("Clear Data") },
                            supportingContent = { Text("Delete local storage, databases & forms") },
                            leadingContent = { Icon(Icons.Default.CleaningServices, contentDescription = null) },
                            modifier = Modifier.clickable {
                                showMenuSheet = false
                                clearProfileData()
                            }
                        )
                    }
                }
            }

            Box(modifier = Modifier.fillMaxWidth().weight(1f)) {
                key(currentProfile) {
                    AndroidView(
                        factory = {
                            WebView(it).apply {
                                layoutParams = ViewGroup.LayoutParams(
                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.MATCH_PARENT
                                )
                                if (WebViewFeature.isFeatureSupported(WebViewFeature.MULTI_PROFILE)) {
                                    WebViewCompat.setProfile(this, currentProfile)
                                }
                                webViewClient = object : WebViewClient() {
                                    override fun onPageStarted(view: WebView?, url: String?, favicon: android.graphics.Bitmap?) {
                                        isLoading = true
                                        super.onPageStarted(view, url, favicon)
                                    }

                                    override fun onLoadResource(view: WebView?, url: String?) {
                                        super.onLoadResource(view, url)
                                        if (url != null) {
                                            val mediaExtensions = listOf(".mp4", ".mkv", ".mp3", ".jpg", ".jpeg", ".png", ".webp")
                                            if (mediaExtensions.any { url.lowercase().contains(it) }) {
                                                android.util.Log.d("WebToolMedia", "Detected potential media: $url")
                                                // In a real app, we'd add this to a shared media list in the ViewModel
                                            }
                                        }
                                    }

                                    override fun onPageFinished(view: WebView?, url: String?) {
                                        isLoading = false
                                        canGoBack = view?.canGoBack() ?: false
                                        super.onPageFinished(view, url)
                                        val improveJS = """
                                            (function() {
                                                var style = document.getElementById('omni-custom-style');
                                                if (!style) {
                                                    style = document.createElement('style');
                                                    style.id = 'omni-custom-style';
                                                    style.innerHTML = '.ad-unit, .adsbygoogle, #google_ads_frame, .perchance-ad, iframe[src*="googleads"] { display: none !important; }';
                                                    document.head.appendChild(style);
                                                }
                                            })();
                                        """.trimIndent()
                                        view?.evaluateJavascript(improveJS, null)
                                    }

                                    override fun doUpdateVisitedHistory(view: WebView?, url: String?, isReload: Boolean) {
                                        canGoBack = view?.canGoBack() ?: false
                                        super.doUpdateVisitedHistory(view, url, isReload)
                                    }

                                    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                                        val url = request?.url?.toString() ?: return false
                                        if (url.startsWith("http://") || url.startsWith("https://")) {
                                            return false
                                        }
                                        try {
                                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                                            view?.context?.startActivity(intent)
                                            return true
                                        } catch (e: Exception) {
                                            return false
                                        }
                                    }

                                    override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                                        isLoading = false
                                        super.onReceivedError(view, request, error)
                                    }
                                }
                                webChromeClient = object : WebChromeClient() {
                                    override fun onPermissionRequest(request: PermissionRequest?) {
                                        request?.grant(request.resources)
                                    }

                                    override fun onGeolocationPermissionsShowPrompt(
                                        origin: String?,
                                        callback: GeolocationPermissions.Callback?
                                    ) {
                                        callback?.invoke(origin, true, false)
                                    }
                                }
                                settings.apply {
                                    @Suppress("SetJavaScriptEnabled")
                                    javaScriptEnabled = true
                                    databaseEnabled = true
                                    domStorageEnabled = true
                                    useWideViewPort = true
                                    loadWithOverviewMode = true
                                    mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                                    setSupportZoom(true)
                                    builtInZoomControls = true
                                    displayZoomControls = false
                                    javaScriptCanOpenWindowsAutomatically = true
                                    allowFileAccess = true
                                    allowContentAccess = true
                                    userAgentString = if (isDesktopMode) desktopUserAgent else mobileUserAgent
                                    mediaPlaybackRequiresUserGesture = false
                                }
                                CookieManager.getInstance().let { cm ->
                                    cm.setAcceptCookie(true)
                                    cm.setAcceptThirdPartyCookies(this, true)
                                }
                                webView = this
                            }
                        },
                        update = { },
                        modifier = Modifier.fillMaxSize()
                    )
                }

                if (isOffline) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.background),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                Icons.Default.WifiOff,
                                contentDescription = null,
                                modifier = Modifier.size(64.dp),
                                tint = MaterialTheme.colorScheme.error
                            )
                            Text("No Internet Connection", style = MaterialTheme.typography.titleLarge)
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(onClick = {
                                isOffline = !checkConnectivity()
                                if (!isOffline) {
                                    val currentUrl = webView?.url
                                    if (currentUrl.isNullOrEmpty() || currentUrl == "about:blank") {
                                        webView?.loadUrl(urlToLoad)
                                    } else {
                                        webView?.reload()
                                    }
                                }
                            }) {
                                Text("Retry")
                            }
                        }
                    }
                } else if (showAnalysis) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.background)
                    ) {
                        WebAnalysisView(webView?.url ?: urlToLoad)
                    }
                }
            }
        }
    }
}

@Composable
fun WebAnalysisView(url: String) {
    val parsedUrl = remember(url) {
        try {
            URL(url)
        } catch (e: Exception) {
            null
        }
    }

    Column(modifier = Modifier.fillMaxWidth().padding(16.dp).verticalScroll(rememberScrollState())) {
        Text("URL Analysis", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))

        AnalysisItem("Protocol", parsedUrl?.protocol ?: "N/A")
        AnalysisItem("Host", parsedUrl?.host ?: "N/A")
        AnalysisItem("Path", parsedUrl?.path ?: "N/A")
        AnalysisItem("Query", parsedUrl?.query ?: "N/A")

        Spacer(modifier = Modifier.height(24.dp))

        Text("Metadata Generation", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))
        AnalysisItem("Suggested Title", parsedUrl?.host?.substringBefore(".")?.replaceFirstChar { it.uppercase() } ?: "Omni Toolbox Web View")
        AnalysisItem("Description", "Exploration utility for $url")
        AnalysisItem("Keywords", "${parsedUrl?.host?.replace(".", ", ")}, nature, tools")

        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = {}, modifier = Modifier.fillMaxWidth()) {
            Icon(Icons.Default.Code, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Copy Markdown Summary")
        }
    }
}

@Composable
fun AnalysisItem(label: String, value: String) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Text(label, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.primary)
        Text(value, style = MaterialTheme.typography.bodyLarge)
        HorizontalDivider(modifier = Modifier.padding(top = 4.dp))
    }
}
