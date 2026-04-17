package com.example.newsreader.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import kotlinx.coroutines.launch
import com.example.newsreader.data.model.Article
import com.example.newsreader.domain.states.UiState
import com.example.newsreader.presentation.ui.components.*
import com.example.newsreader.presentation.ui.theme.*
import com.example.newsreader.presentation.viewmodel.NewsViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun NewsListScreen(viewModel: NewsViewModel, onArticleClick: (Article) -> Unit) {
    val uiState by viewModel.uiState.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val favorites by viewModel.favorites.collectAsState()
    val pullRefreshState = rememberPullRefreshState(refreshing = isRefreshing, onRefresh = { viewModel.refresh() })

    var selectedTab by remember { mutableIntStateOf(0) }

    var showMenu by remember { mutableStateOf(false) }
    var isSearchActive by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    val newsCategories = listOf("Latest", "Google", "AI", "Mobile", "Security", "Apple")
    var activeCategory by remember { mutableStateOf(newsCategories[0]) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (isSearchActive) {
                        TextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            placeholder = { Text("Cari berita...", color = TextGray) },
                            singleLine = true,
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent, unfocusedContainerColor = Color.Transparent,
                                focusedTextColor = TextWhite, unfocusedTextColor = TextWhite,
                                focusedIndicatorColor = AccentBlue, unfocusedIndicatorColor = Color.Transparent
                            )
                        )
                    }
                },
                navigationIcon = {
                    Box {
                        IconButton(onClick = { showMenu = true }) { Icon(Icons.Default.Menu, "Menu", tint = TextWhite) }
                        MaterialTheme(
                            colorScheme = MaterialTheme.colorScheme.copy(surface = CardDark),
                            shapes = MaterialTheme.shapes.copy(extraSmall = RoundedCornerShape(16.dp)) // Mengatur kelengkungan pop-up
                        ) {
                            DropdownMenu(
                                expanded = showMenu,
                                onDismissRequest = { showMenu = false },
                                modifier = Modifier.background(CardDark).width(200.dp)
                            ) {
                                DropdownMenuItem(
                                    leadingIcon = { Icon(Icons.Outlined.Star, contentDescription = null, tint = AccentBlue) },
                                    text = { Text("Menu Favorit", color = TextWhite) },
                                    onClick = { selectedTab = 1; showMenu = false }
                                )
                                HorizontalDivider(color = Color.DarkGray, modifier = Modifier.padding(horizontal = 12.dp))
                                DropdownMenuItem(
                                    leadingIcon = { Icon(Icons.Outlined.Person, contentDescription = null, tint = AccentBlue) },
                                    text = { Text("Profil Saya", color = TextWhite) },
                                    onClick = { selectedTab = 2; showMenu = false }
                                )
                            }
                        }
                    }
                },
                actions = {
                    IconButton(onClick = {
                        isSearchActive = !isSearchActive
                        if (!isSearchActive) searchQuery = ""
                    }) {
                        Icon(if (isSearchActive) Icons.Default.Close else Icons.Default.Search, "Search", tint = TextWhite)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BgDark)
            )
        },
        bottomBar = { CustomBottomNavigation(selectedTab = selectedTab, onTabSelected = { selectedTab = it }) },
        containerColor = BgDark
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding).pullRefresh(pullRefreshState)) {

            if (selectedTab == 0) {
                when (val state = uiState) {
                    is UiState.Loading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center), color = AccentBlue)
                    is UiState.Success -> {
                        val filteredArticles = state.data.filter { article ->
                            val matchesSearch = article.title.contains(searchQuery, ignoreCase = true)
                            val matchesCategory = if (activeCategory == "Latest") {
                                true
                            } else {
                                article.title.contains(activeCategory, ignoreCase = true) ||
                                        (article.description?.contains(activeCategory, ignoreCase = true) == true)
                            }
                            matchesSearch && matchesCategory
                        }

                        LazyColumn(modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(bottom = 24.dp)) {

                            // Menu Kategori
                            item {
                                LazyRow(
                                    horizontalArrangement = Arrangement.spacedBy(24.dp),
                                    contentPadding = PaddingValues(horizontal = 24.dp),
                                    modifier = Modifier.padding(vertical = 16.dp)
                                ) {
                                    items(newsCategories) { category ->
                                        val isActive = category == activeCategory
                                        Column(
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            modifier = Modifier.clickable { activeCategory = category }
                                        ) {
                                            Text(
                                                text = category,
                                                color = if (isActive) TextWhite else TextGray,
                                                fontWeight = if (isActive) FontWeight.Bold else FontWeight.Normal
                                            )
                                            Spacer(Modifier.height(4.dp))
                                            if (isActive) {
                                                Box(Modifier.size(4.dp).clip(CircleShape).background(AccentBlue))
                                            } else {
                                                Spacer(Modifier.height(4.dp))
                                            }
                                        }
                                    }
                                }
                            }

                            if (filteredArticles.isEmpty()) {
                                item {
                                    Column(
                                        modifier = Modifier.fillMaxWidth().padding(top = 48.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text("Berita tidak ditemukan.", color = TextGray)
                                        Text("Coba kategori lain.", color = TextGray, fontSize = 12.sp)
                                    }
                                }
                            } else {
                                if (!isSearchActive && filteredArticles.size >= 3) {
                                    item {
                                        LazyRow(contentPadding = PaddingValues(horizontal = 24.dp), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                                            items(filteredArticles.take(3)) { article -> FeaturedNewsCard(article, onClick = { onArticleClick(article) }) }
                                        }
                                    }
                                    items(filteredArticles.drop(3)) { article -> StandardNewsCard(article, onClick = { onArticleClick(article) }) }
                                } else {
                                    items(filteredArticles) { article -> StandardNewsCard(article, onClick = { onArticleClick(article) }) }
                                }
                            }
                        }
                    }
                    is UiState.Error -> { /* Handle Error */ }
                }
            } else if (selectedTab == 1) {
                Column(modifier = Modifier.fillMaxSize()) {
                    Text("Berita Tersimpan", color = TextWhite, fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(24.dp))
                    if (favorites.isEmpty()) {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text("Belum ada berita yang difavoritkan.", color = TextGray)
                        }
                    } else {
                        LazyColumn(contentPadding = PaddingValues(bottom = 24.dp)) {
                            items(favorites) { article ->
                                StandardNewsCard(article, onClick = { onArticleClick(article) })
                            }
                        }
                    }
                }
            } else {
                // HALAMAN PROFIL
                Column(
                    modifier = Modifier.fillMaxSize().padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AsyncImage(
                        model = "",
                        contentDescription = "Profile Picture",
                        modifier = Modifier
                            .size(110.dp)
                            .clip(CircleShape)
                            .border(3.dp, AccentBlue, CircleShape)
                    )
                    Spacer(Modifier.height(16.dp))

                    Text("Pradana Figo Ariasya", color = TextWhite, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    Text("Teknik Informatika", color = AccentBlue, fontSize = 16.sp, fontWeight = FontWeight.Medium)

                    Spacer(Modifier.height(32.dp))

                    Card(
                        colors = CardDefaults.cardColors(containerColor = CardDark),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(20.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Outlined.Person, contentDescription = null, tint = AccentBlue, modifier = Modifier.size(20.dp))
                                Spacer(Modifier.width(8.dp))
                                Text("Tentang Saya", color = TextWhite, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            }
                            Spacer(Modifier.height(12.dp))
                            Text(
                                "Pengembang perangkat lunak yang berfokus pada Mobile Development menggunakan Kotlin Multiplatform (KMP) dan Jetpack Compose. Memiliki minat mendalam pada pengembangan Full-Stack dan integrasi Machine Learning.",
                                color = TextGray, fontSize = 14.sp, lineHeight = 22.sp
                            )
                        }
                    }

                    Spacer(Modifier.height(16.dp))

                    Card(
                        colors = CardDefaults.cardColors(containerColor = CardDark),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(20.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Outlined.Star, contentDescription = null, tint = AccentBlue, modifier = Modifier.size(20.dp))
                                Spacer(Modifier.width(8.dp))
                                Text("Proyek Unggulan", color = TextWhite, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            }
                            Spacer(Modifier.height(12.dp))
                            Text("📱 HealthyU (Health Platform)", color = TextGray, fontSize = 14.sp, modifier = Modifier.padding(bottom = 6.dp))
                            Text("💻 PropertiKu (Web Apps)", color = TextGray, fontSize = 14.sp, modifier = Modifier.padding(bottom = 6.dp))
                        }
                    }
                }
            }
            PullRefreshIndicator(refreshing = isRefreshing, state = pullRefreshState, modifier = Modifier.align(Alignment.TopCenter))
        }
    }
}