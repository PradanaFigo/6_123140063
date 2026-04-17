package com.example.newsreader.data.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class NewsResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
)

@Serializable
data class Article(
    val title: String,
    val description: String? = null,
    val urlToImage: String? = null,
    val author: String? = "Unknown Author",
    val publishedAt: String,
    val content: String? = null
)