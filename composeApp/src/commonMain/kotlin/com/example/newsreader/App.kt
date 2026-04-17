package com.example.newsreader

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import com.example.newsreader.data.api.HttpClientFactory
import com.example.newsreader.data.model.Article
import com.example.newsreader.data.repository.NewsRepository
import com.example.newsreader.presentation.ui.screens.NewsDetailScreen
import com.example.newsreader.presentation.ui.screens.NewsListScreen
import com.example.newsreader.presentation.viewmodel.NewsViewModel

@Composable
fun App() {
    MaterialTheme {
        // Inisialisasi Ktor Client & Repository
        val client = remember { HttpClientFactory.create() }
        val repository = remember { NewsRepository(client) }

        // Inisialisasi ViewModel
        val viewModel = remember { NewsViewModel(repository) }

        // Navigasi
        var selectedArticle by remember { mutableStateOf<Article?>(null) }

        // Logika Pindah Halaman
        if (selectedArticle == null) {
            NewsListScreen(
                viewModel = viewModel,
                onArticleClick = { clickedArticle ->
                    selectedArticle = clickedArticle
                }
            )
        } else {
            NewsDetailScreen(
                viewModel = viewModel,
                article = selectedArticle!!,
                onBackClick = {
                    selectedArticle = null
                }
            )
        }
    }
}