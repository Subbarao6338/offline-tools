package omni.toolbox.ui.screens.lifestyle

import android.content.Context
import android.content.Intent
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import omni.toolbox.ui.components.ToolScreen
import kotlin.random.Random

data class Quote(
    val text: String,
    val author: String,
    val category: String
)

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun DailyQuotesScreen(navController: NavHostController) {
    val context = LocalContext.current
    val haptic = LocalHapticFeedback.current
    val sharedPrefs = remember { context.getSharedPreferences("daily_quotes_prefs", Context.MODE_PRIVATE) }

    val allQuotes = remember {
        listOf(
            // Motivation
            Quote("The only way to do great work is to love what you do.", "Steve Jobs", "Motivation"),
            Quote("It always seems impossible until it's done.", "Nelson Mandela", "Motivation"),
            Quote("Believe you can and you're halfway there.", "Theodore Roosevelt", "Motivation"),
            Quote("Act as if what you do makes a difference. It does.", "William James", "Motivation"),
            Quote("Success is not final, failure is not fatal: it is the courage to continue that counts.", "Winston Churchill", "Motivation"),
            Quote("Keep your face always toward the sunshine—and shadows will fall behind you.", "Walt Whitman", "Motivation"),

            // Wisdom
            Quote("The only true wisdom is in knowing you know nothing.", "Socrates", "Wisdom"),
            Quote("In the end, it's not the years in your life that count. It's the life in your years.", "Abraham Lincoln", "Wisdom"),
            Quote("The journey of a thousand miles begins with one step.", "Lao Tzu", "Wisdom"),
            Quote("Knowing yourself is the beginning of all wisdom.", "Aristotle", "Wisdom"),
            Quote("The only constant in life is change.", "Heraclitus", "Wisdom"),
            Quote("What we achieve inwardly will change outer reality.", "Plutarch", "Wisdom"),

            // Nature
            Quote("Look deep into nature, and then you will understand everything better.", "Albert Einstein", "Nature"),
            Quote("The best thing one can do when it's raining is to let it rain.", "Henry Wadsworth Longfellow", "Nature"),
            Quote("Nature does not hurry, yet everything is accomplished.", "Lao Tzu", "Nature"),
            Quote("In every walk with nature one receives far more than he seeks.", "John Muir", "Nature"),
            Quote("The Earth has music for those who listen.", "George Santayana", "Nature"),
            Quote("Adopt the pace of nature: her secret is patience.", "Ralph Waldo Emerson", "Nature"),

            // Success
            Quote("Success usually comes to those who are too busy to be looking for it.", "Henry David Thoreau", "Success"),
            Quote("Don't be afraid to give up the good to go for the great.", "John D. Rockefeller", "Success"),
            Quote("I find that the harder I work, the more luck I seem to have.", "Thomas Jefferson", "Success"),
            Quote("Opportunities don't happen. You create them.", "Chris Grosser", "Success"),
            Quote("Try not to become a man of success, but rather try to become a man of value.", "Albert Einstein", "Success"),
            Quote("The secret of success is to do the common thing uncommonly well.", "John D. Rockefeller Jr.", "Success"),

            // Mindfulness
            Quote("Quiet the mind and the soul will speak.", "Buddha", "Mindfulness"),
            Quote("Be here now.", "Ram Dass", "Mindfulness"),
            Quote("The present moment is filled with joy and happiness. If you are attentive, you will see it.", "Thich Nhat Hanh", "Mindfulness"),
            Quote("Feelings come and go like clouds in a windy sky. Conscious breathing is my anchor.", "Thich Nhat Hanh", "Mindfulness"),
            Quote("Mindfulness isn't difficult, we just need to remember to do it.", "Sharon Salzberg", "Mindfulness"),
            Quote("Do not dwell in the past, do not dream of the future, concentrate the mind on the present moment.", "Buddha", "Mindfulness")
        )
    }

    // Load persisted favorites from SharedPreferences
    var favorites by remember {
        mutableStateOf(sharedPrefs.getStringSet("favorite_quotes", emptySet()) ?: emptySet())
    }

    var searchQuery by remember { mutableStateOf("") }
    val categories = remember { listOf("All", "Motivation", "Wisdom", "Nature", "Success", "Mindfulness", "Favorites") }
    var selectedCategory by remember { mutableStateOf("All") }

    // Floating Spotlight Quote Card State
    var showSpotlightDialog by remember { mutableStateOf(false) }
    var spotlightQuote by remember { mutableStateOf<Quote?>(null) }

    fun toggleFavorite(quote: Quote) {
        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
        val newFavorites = favorites.toMutableSet()
        if (newFavorites.contains(quote.text)) {
            newFavorites.remove(quote.text)
        } else {
            newFavorites.add(quote.text)
        }
        favorites = newFavorites
        sharedPrefs.edit().putStringSet("favorite_quotes", newFavorites).apply()
    }

    fun shareQuote(quote: Quote) {
        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "\"${quote.text}\" — ${quote.author}")
            type = "text/plain"
        }
        context.startActivity(Intent.createChooser(shareIntent, "Share Quote via"))
    }

    // Filter quotes based on search query and category
    val filteredQuotes = remember(searchQuery, selectedCategory, favorites) {
        allQuotes.filter { quote ->
            val matchesCategory = when (selectedCategory) {
                "All" -> true
                "Favorites" -> favorites.contains(quote.text)
                else -> quote.category.equals(selectedCategory, ignoreCase = true)
            }
            val matchesSearch = quote.text.contains(searchQuery, ignoreCase = true) ||
                    quote.author.contains(searchQuery, ignoreCase = true)
            matchesCategory && matchesSearch
        }
    }

    // Trigger random spotlight
    fun triggerRandomSpotlight() {
        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
        val pool = if (selectedCategory == "Favorites") {
            allQuotes.filter { favorites.contains(it.text) }
        } else if (selectedCategory == "All") {
            allQuotes
        } else {
            allQuotes.filter { it.category.equals(selectedCategory, ignoreCase = true) }
        }
        if (pool.isNotEmpty()) {
            spotlightQuote = pool[Random.nextInt(pool.size)]
            showSpotlightDialog = true
        } else {
            spotlightQuote = null
        }
    }

    ToolScreen(
        title = "Daily Quotes & Wisdom",
        onBack = { navController.popBackStack() }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                // 1. Interactive Search Bar
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Search quotes or authors...") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { searchQuery = "" }) {
                                Icon(Icons.Default.Close, contentDescription = "Clear search")
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
                    )
                )

                // 2. Scrollable Category Chips Row
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(categories) { category ->
                        val isSelected = selectedCategory == category
                        val count = when (category) {
                            "All" -> allQuotes.size
                            "Favorites" -> favorites.size
                            else -> allQuotes.count { it.category.equals(category, ignoreCase = true) }
                        }

                        FilterChip(
                            selected = isSelected,
                            onClick = {
                                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                selectedCategory = category
                            },
                            label = {
                                Text(
                                    text = "$category ($count)",
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            shape = CircleShape,
                            leadingIcon = {
                                if (category == "Favorites") {
                                    Icon(
                                        imageVector = if (favorites.isNotEmpty()) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                        contentDescription = null,
                                        modifier = Modifier.size(16.dp),
                                        tint = if (favorites.isNotEmpty()) Color.Red else MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                        )
                    }
                }

                // 3. Quotes Lazy List with Custom Transition Animations
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    if (filteredQuotes.isEmpty()) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(32.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Default.FormatQuote,
                                contentDescription = null,
                                modifier = Modifier.size(64.dp),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f)
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = if (selectedCategory == "Favorites") "No favorite quotes yet.\nTap the heart icon on any quote to save it!"
                                       else "No quotes found matching your criteria.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                textAlign = TextAlign.Center
                            )
                        }
                    } else {
                        LazyColumn(
                            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 80.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(filteredQuotes, key = { it.text }) { quote ->
                                val isFav = favorites.contains(quote.text)
                                QuoteListItem(
                                    quote = quote,
                                    isFavorite = isFav,
                                    onFavoriteToggle = { toggleFavorite(quote) },
                                    onShare = { shareQuote(quote) },
                                    modifier = Modifier.animateItemPlacement()
                                )
                            }
                        }
                    }
                }
            }

            // 4. Floating Spotlight/Shuffle Action Button
            FloatingActionButton(
                onClick = { triggerRandomSpotlight() },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Shuffle, contentDescription = "Shuffle Spotlight")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Shuffle spotlight", fontWeight = FontWeight.Bold)
                }
            }
        }

        // 5. Spotlight Modal Sheet Dialog (Animated Overlay)
        if (showSpotlightDialog && spotlightQuote != null) {
            val currentSpotlight = spotlightQuote!!
            val isSpotlightFav = favorites.contains(currentSpotlight.text)

            AlertDialog(
                onDismissRequest = { showSpotlightDialog = false },
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "${currentSpotlight.category} Spotlight",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        IconButton(onClick = { showSpotlightDialog = false }) {
                            Icon(Icons.Default.Close, contentDescription = "Dismiss")
                        }
                    }
                },
                text = {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    Brush.linearGradient(
                                        colors = listOf(
                                            MaterialTheme.colorScheme.primaryContainer,
                                            MaterialTheme.colorScheme.secondaryContainer
                                        )
                                    )
                                )
                                .padding(24.dp)
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Icon(
                                    imageVector = Icons.Default.FormatQuote,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(48.dp)
                                        .align(Alignment.Start),
                                    tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = currentSpotlight.text,
                                    style = MaterialTheme.typography.headlineSmall.copy(
                                        fontSize = 18.sp,
                                        lineHeight = 26.sp
                                    ),
                                    fontStyle = FontStyle.Italic,
                                    fontWeight = FontWeight.Medium,
                                    textAlign = TextAlign.Center,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    text = "— ${currentSpotlight.author}",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }
                },
                confirmButton = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = { toggleFavorite(currentSpotlight) }) {
                            Icon(
                                imageVector = if (isSpotlightFav) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = "Favorite",
                                tint = if (isSpotlightFav) Color.Red else MaterialTheme.colorScheme.primary
                            )
                        }
                        IconButton(onClick = { shareQuote(currentSpotlight) }) {
                            Icon(Icons.Default.Share, contentDescription = "Share", tint = MaterialTheme.colorScheme.primary)
                        }
                        Button(
                            onClick = {
                                val pool = if (selectedCategory == "Favorites") {
                                    allQuotes.filter { favorites.contains(it.text) }
                                } else if (selectedCategory == "All") {
                                    allQuotes
                                } else {
                                    allQuotes.filter { it.category.equals(selectedCategory, ignoreCase = true) }
                                }
                                if (pool.isNotEmpty()) {
                                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                    spotlightQuote = pool[Random.nextInt(pool.size)]
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                        ) {
                            Icon(Icons.Default.Shuffle, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Next")
                        }
                    }
                },
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
fun QuoteListItem(
    quote: Quote,
    isFavorite: Boolean,
    onFavoriteToggle: () -> Unit,
    onShare: () -> Unit,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                Icon(
                    imageVector = Icons.Default.FormatQuote,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                    modifier = Modifier.size(36.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = quote.text,
                        style = MaterialTheme.typography.bodyLarge.copy(lineHeight = 22.sp),
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "— ${quote.author}",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Category badge
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = quote.category,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Action buttons
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    IconButton(onClick = onFavoriteToggle) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Toggle Favorite",
                            tint = if (isFavorite) Color.Red else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    IconButton(onClick = onShare) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Share",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }
}
