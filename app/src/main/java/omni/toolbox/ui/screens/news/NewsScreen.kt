package omni.toolbox.ui.screens.news

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import omni.toolbox.ui.components.ToolScreen

data class Article(
    val id: String,
    val title: String,
    val source: String,
    val category: String,
    val content: String,
    val date: String,
    var isBookmarked: Boolean = false
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsScreen(navController: NavHostController) {
    var selectedCategory by remember { mutableStateOf("All") }
    var searchQuery by remember { mutableStateOf("") }
    var selectedArticle by remember { mutableStateOf<Article?>(null) }

    val articles = remember {
        mutableStateListOf(
            Article(
                "1",
                "New Quantum Processor Achieves 99.9% Gate Fidelity",
                "Tech Insights",
                "Tech",
                "Researchers have unveiled a novel superconducting quantum processor that achieves state-of-the-art gate control with 99.9% fidelity. This milestone brings fault-tolerant quantum computing closer to reality. The architecture uses advanced noise-filtering circuits to stabilize fragile qubits against environmental thermal disruptions.",
                "2 Hours Ago"
            ),
            Article(
                "2",
                "James Webb Telescope Detects Organic Molecules in Deep Space",
                "Science Daily",
                "Science",
                "Astronomers utilizing the James Webb Space Telescope have identified complex carbon-bearing molecules in the interstellar medium of a proto-planetary disk located 1,500 light-years away. This finding suggests the molecular building blocks of life are far more abundant in early planetary systems than previously modeled.",
                "4 Hours Ago"
            ),
            Article(
                "3",
                "Global Stock Markets Face Sudden Shift on Inflation Policy Updates",
                "Financial Pulse",
                "Business",
                "Leading central banks have hinted at a coordinated recalibration of interest rates in response to stronger-than-expected labor statistics and stabilizing supply lines. Stock indices fluctuated dramatically as traders adjusted high-growth portfolios ahead of the formal policy release next Wednesday.",
                "5 Hours Ago"
            ),
            Article(
                "4",
                "New Wearable Health Sensor Tracks Real-time Hydration and Electrolytes",
                "BioTech Weekly",
                "Health",
                "An innovative biosensor that adheres directly to the skin like a temporary patch has been cleared for clinical testing. The patch continuously monitors sweat composition to alert users of dangerous drop-offs in sodium, potassium, and fluid levels, preventing acute dehydration and heat stroke.",
                "Yesterday"
            ),
            Article(
                "5",
                "Major Breakthrough in Solid-State Battery Density Promised for EVs",
                "AutoFuturism",
                "Tech",
                "A battery research consortium has demonstrated a solid-state cell prototype exceeding 500 Wh/kg energy density. If scalable, this technology will effectively double the range of modern electric vehicles while minimizing charging times to under 10 minutes and dramatically reducing thermal runaway risks.",
                "Yesterday"
            )
        )
    }

    val categories = listOf("All", "Tech", "Science", "Business", "Health", "Bookmarks")

    val filteredArticles = remember(selectedCategory, searchQuery, articles) {
        articles.filter { article ->
            val matchesCategory = when (selectedCategory) {
                "All" -> true
                "Bookmarks" -> article.isBookmarked
                else -> article.category == selectedCategory
            }
            val matchesSearch = article.title.contains(searchQuery, ignoreCase = true) ||
                    article.content.contains(searchQuery, ignoreCase = true)
            matchesCategory && matchesSearch
        }
    }

    ToolScreen(
        title = "News Hub",
        onBack = {
            if (selectedArticle != null) {
                selectedArticle = null
            } else {
                navController.popBackStack()
            }
        }
    ) { padding ->
        if (selectedArticle != null) {
            val article = selectedArticle!!
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SuggestionChip(
                        onClick = {},
                        label = { Text(article.category) },
                        colors = SuggestionChipDefaults.suggestionChipColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    )
                    IconButton(onClick = {
                        val index = articles.indexOfFirst { it.id == article.id }
                        if (index != -1) {
                            val updated = article.copy(isBookmarked = !article.isBookmarked)
                            articles[index] = updated
                            selectedArticle = updated
                        }
                    }) {
                        Icon(
                            if (article.isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                            contentDescription = "Bookmark",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = article.title,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "By ${article.source}",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Text(
                        text = article.date,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))
                Text(
                    text = article.content,
                    style = MaterialTheme.typography.bodyLarge,
                    lineHeight = 24.sp
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Search articles...") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )

                ScrollableTabRow(
                    selectedTabIndex = categories.indexOf(selectedCategory).coerceAtLeast(0),
                    edgePadding = 16.dp,
                    divider = {},
                    indicator = {}
                ) {
                    categories.forEach { cat ->
                        FilterChip(
                            selected = selectedCategory == cat,
                            onClick = { selectedCategory = cat },
                            label = { Text(cat) },
                            modifier = Modifier.padding(horizontal = 4.dp),
                            leadingIcon = {
                                if (cat == "Bookmarks") {
                                    Icon(Icons.Default.Bookmark, contentDescription = null, modifier = Modifier.size(16.dp))
                                }
                            }
                        )
                    }
                }

                if (filteredArticles.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.Newspaper, contentDescription = null, modifier = Modifier.size(48.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f))
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("No articles found", color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(filteredArticles, key = { it.id }) { article ->
                            Card(
                                onClick = { selectedArticle = article },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = article.category,
                                            style = MaterialTheme.typography.labelMedium,
                                            color = MaterialTheme.colorScheme.primary,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = article.date,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = article.title,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        maxLines = 2,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = article.content,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        maxLines = 2,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
