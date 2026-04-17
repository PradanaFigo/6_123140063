package com.example.newsreader.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsreader.data.model.Article
import com.example.newsreader.data.repository.NewsRepository
import com.example.newsreader.domain.states.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NewsViewModel(private val repository: NewsRepository) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<List<Article>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Article>>> = _uiState.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    private val _favorites = MutableStateFlow<List<Article>>(emptyList())
    val favorites: StateFlow<List<Article>> = _favorites.asStateFlow()

    init {
        loadNews()
    }

    fun loadNews() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            fetchData()
        }
    }

    fun refresh() {
        viewModelScope.launch {
            _isRefreshing.value = true
            fetchData()
            _isRefreshing.value = false
        }
    }

    private suspend fun fetchData() {
        repository.getTopHeadlines()
            .onSuccess { articles -> _uiState.value = UiState.Success(articles) }
            .onFailure { error -> _uiState.value = UiState.Error(error.message ?: "Koneksi internet bermasalah") }
    }

    fun toggleFavorite(article: Article) {
        val currentList = _favorites.value.toMutableList()
        if (currentList.any { it.title == article.title }) {
            currentList.removeAll { it.title == article.title }
        } else {
            currentList.add(article)
        }
        _favorites.value = currentList
    }

    fun isFavorite(article: Article): Boolean {
        return _favorites.value.any { it.title == article.title }
    }
}