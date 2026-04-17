package com.example.newsreader.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import kotlinx.coroutines.launch
import com.example.newsreader.data.model.Article
import com.example.newsreader.presentation.ui.components.CustomBottomNavigation
import com.example.newsreader.presentation.ui.theme.*
import com.example.newsreader.presentation.viewmodel.NewsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsDetailScreen(viewModel: NewsViewModel, article: Article, onBackClick: () -> Unit) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val isFavorite = viewModel.isFavorite(article)

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("News", color = TextWhite, fontSize = 16.sp) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = TextWhite) }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BgDark),
            )
        },
        bottomBar = {
            CustomBottomNavigation(
                selectedTab = 0,
                onTabSelected = {
                    if (it == 0) onBackClick()
                    else coroutineScope.launch { snackbarHostState.showSnackbar("Silakan kembali ke Home terlebih dahulu") }
                }
            )
        },
        containerColor = BgDark
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).verticalScroll(rememberScrollState())) {
            Box(modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp).height(240.dp)) {
                AsyncImage(
                    model = article.urlToImage, contentDescription = null, contentScale = ContentScale.Crop, modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(16.dp))
                )

                FloatingActionButton(
                    onClick = {
                        viewModel.toggleFavorite(article)
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar(if (!isFavorite) "Ditambahkan ke Favorit!" else "Dihapus dari Favorit!")
                        }
                    },
                    containerColor = TextWhite,
                    shape = CircleShape,
                    modifier = Modifier.align(Alignment.BottomEnd).offset(x = (-16).dp, y = 24.dp).size(48.dp)
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = "Like",
                        tint = HeartPink
                    )
                }
            }

            Spacer(Modifier.height(36.dp))
            Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                Text(text = article.title, color = TextWhite, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Spacer(Modifier.height(12.dp))
                Box(modifier = Modifier.width(32.dp).height(2.dp).background(AccentBlue))
                Spacer(Modifier.height(16.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    AsyncImage(
                        model = "https://i.pravatar.cc/150?u=${article.author}",
                        contentDescription = null, contentScale = ContentScale.Crop, modifier = Modifier.size(32.dp).clip(CircleShape)
                    )
                    Spacer(Modifier.width(12.dp))
                    Column {
                        Text("by ${article.author}", color = TextWhite, fontSize = 12.sp)
                        Text(article.publishedAt.take(10), color = TextGray, fontSize = 10.sp)
                    }
                }

                Spacer(Modifier.height(24.dp))
                Text(text = article.description ?: "", color = TextGray, fontSize = 14.sp, lineHeight = 22.sp)
                Spacer(Modifier.height(16.dp))
                Text(text = article.content ?: "Isi berita tidak tersedia.", color = TextGray, fontSize = 14.sp, lineHeight = 22.sp)
            }
        }
    }
}