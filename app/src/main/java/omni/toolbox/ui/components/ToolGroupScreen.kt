package omni.toolbox.ui.components

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
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import omni.toolbox.model.Tool
import omni.toolbox.model.ToolProvider
import omni.toolbox.model.UrlLinksManager
import omni.toolbox.ui.components.ToolScreen

import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ToolGroupScreen(
    navController: NavHostController,
    groupRoute: String,
    favorites: Set<String>,
    onToggleFavorite: (String) -> Unit
) {
    val haptic = LocalHapticFeedback.current
    val groupTool = remember(groupRoute) {
        ToolProvider.tools.find { it.route == groupRoute }
    }

    var showActionsDialogForTool by remember { mutableStateOf<Tool?>(null) }

    if (groupTool == null) {
        navController.popBackStack()
        return
    }

    val subTools = remember(groupTool) {
        groupTool.subToolRoutes?.mapNotNull { route ->
            ToolProvider.tools.find { it.route == route }
        }?.sortedBy { it.name } ?: emptyList()
    }

    ToolScreen(
        title = groupTool.name,
        onBack = { navController.popBackStack() },
        actions = {
            IconButton(onClick = { onToggleFavorite(groupTool.route) }) {
                Icon(
                    if (favorites.contains(groupTool.route)) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = null,
                    tint = if (favorites.contains(groupTool.route)) Color.Red else MaterialTheme.colorScheme.primary
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            groupTool.description?.let { desc ->
                Text(
                    text = desc,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            if (groupRoute == "calc_group") {
                val coreCalcs = listOf("calculator", "sci_calc")
                val practicalCalcs = listOf("discount", "tip", "unit_price", "unit_compare", "volume_calc")
                val geometryCalcs = listOf("area_calc")
                val businessCalcs = listOf("billing")
                val sizeCalcs = listOf("bra_calculator", "underwear_calculator", "dress_calculator", "ring_calculator", "arm_calculator", "body_calculator", "kids_calculator")
                val textCalcs = listOf("word_rank_calc")

                val sections = listOf(
                    "Core & Scientific" to coreCalcs,
                    "Practical & Shopping" to practicalCalcs,
                    "Geometry & Area" to geometryCalcs,
                    "Business & Billing" to businessCalcs,
                    "Fashion & Size" to sizeCalcs,
                    "Text & Word Utilities" to textCalcs
                )

                sections.forEach { (sectionName, routes) ->
                    val toolsInSection = routes.mapNotNull { route ->
                        subTools.find { it.route == route }
                    }
                    if (toolsInSection.isNotEmpty()) {
                        Text(
                            text = sectionName,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                        )
                        toolsInSection.forEach { tool ->
                            SubToolCard(
                                tool = tool,
                                isFavorite = favorites.contains(tool.route),
                                onToggleFavorite = {
                                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                    onToggleFavorite(tool.route)
                                },
                                onClick = {
                                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                    navController.navigate(tool.route)
                                }
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                    }
                }
            } else if (groupRoute == "docs_group") {
                val corePdf = listOf("pdf_merge", "pdf_split", "pdf_preview", "pdf_print", "pdf_metadata")
                val optPdf = listOf("pdf_compress", "pdf_protect", "pdf_unlock", "pdf_watermark", "pdf_flatten", "pdf_grayscale", "pdf_invert", "pdf_repair")
                val convPdf = listOf("images_to_pdf", "pdf_html_to_pdf", "pdf_word_to_pdf", "pdf_excel_to_pdf", "pdf_text_to_pdf", "pdf_to_mdx", "pdf_to_mhtml", "pdf_extract_images", "csv_to_json", "sql_format", "markitdown")
                val formPdf = listOf("pdf_fill_forms", "pdf_signature", "pdf_page_numbers", "pdf_rearrange", "pdf_remove_pages", "pdf_crop")
                val officeFile = listOf("doc_scanner", "pdf_scan_to_pdf", "pdf_qr_to_pdf", "pdf_barcode_to_pdf", "duplicate_finder", "file_shredder", "storage_cleaner", "zip_unzip", "pdf_zip", "docs_online")

                val sections = listOf(
                    "Core PDF Utilities" to corePdf,
                    "PDF Optimization & Security" to optPdf,
                    "Format & Content Converters" to convPdf,
                    "Form & Page Editors" to formPdf,
                    "Office Scanner & File Tools" to officeFile
                )

                sections.forEach { (sectionName, routes) ->
                    val toolsInSection = routes.mapNotNull { route ->
                        subTools.find { it.route == route }
                    }
                    if (toolsInSection.isNotEmpty()) {
                        Text(
                            text = sectionName,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                        )
                        toolsInSection.forEach { tool ->
                            SubToolCard(
                                tool = tool,
                                isFavorite = favorites.contains(tool.route),
                                onToggleFavorite = { onToggleFavorite(tool.route) },
                                onClick = { navController.navigate(tool.route) }
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                    }
                }
            } else {
                subTools.forEach { tool ->
                    SubToolCard(
                        tool = tool,
                        isFavorite = favorites.contains(tool.route),
                        onToggleFavorite = {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            onToggleFavorite(tool.route)
                        },
                        onClick = {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            navController.navigate(tool.route)
                        },
                        onLongClick = {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            if (tool.route.startsWith("dyn_link_")) {
                                showActionsDialogForTool = tool
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }

    if (showActionsDialogForTool != null) {
        val context = LocalContext.current
        val linkItem = remember(showActionsDialogForTool) {
            UrlLinksManager.getLinks(context).find {
                it.title == showActionsDialogForTool?.name
            }
        }

        if (linkItem != null) {
            AlertDialog(
                onDismissRequest = { showActionsDialogForTool = null },
                title = { Text(linkItem.title, fontWeight = FontWeight.Bold) },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("Available URLs:", style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.primary)

                        linkItem.urls.forEach { url ->
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                            ) {
                                Row(
                                    modifier = Modifier.padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = url,
                                        modifier = Modifier.weight(1f),
                                        style = MaterialTheme.typography.bodySmall,
                                        maxLines = 2,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    IconButton(
                                        onClick = {
                                            val clipboard = context.getSystemService(android.content.Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
                                            val clip = android.content.ClipData.newPlainText("URL", url)
                                            clipboard.setPrimaryClip(clip)
                                            android.widget.Toast.makeText(context, "URL copied!", android.widget.Toast.LENGTH_SHORT).show()
                                        },
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                        Icon(Icons.Default.ContentCopy, contentDescription = "Copy URL", modifier = Modifier.size(16.dp))
                                    }
                                    IconButton(
                                        onClick = {
                                            try {
                                                navController.navigate("web?url=${android.net.Uri.encode(url)}&showBar=true")
                                            } catch (e: Exception) {
                                                android.widget.Toast.makeText(context, "Cannot open URL", android.widget.Toast.LENGTH_SHORT).show()
                                            }
                                        },
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                        Icon(Icons.Default.OpenInBrowser, contentDescription = "Open in Browser", modifier = Modifier.size(16.dp))
                                    }
                                }
                            }
                        }
                    }
                },
                confirmButton = {
                    TextButton(onClick = { showActionsDialogForTool = null }) {
                        Text("Close")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            val clipboard = context.getSystemService(android.content.Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
                            val allUrls = linkItem.urls.joinToString("\n")
                            val clip = android.content.ClipData.newPlainText("All URLs", allUrls)
                            clipboard.setPrimaryClip(clip)
                            android.widget.Toast.makeText(context, "All URLs copied!", android.widget.Toast.LENGTH_SHORT).show()
                            showActionsDialogForTool = null
                        }
                    ) {
                        Text("Copy All")
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun SubToolCard(
    tool: Tool,
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit,
    onClick: () -> Unit,
    onLongClick: (() -> Unit)? = null
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick ?: {}
            )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(48.dp),
                shape = RoundedCornerShape(12.dp),
                color = tool.color.copy(alpha = 0.1f)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        tool.icon,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        tint = tool.color
                    )
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = tool.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                tool.description?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 2
                    )
                }
            }
            IconButton(onClick = onToggleFavorite) {
                Icon(
                    if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = null,
                    tint = if (isFavorite) Color.Red else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}
