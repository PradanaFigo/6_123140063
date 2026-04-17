package com.example.newsreader.data.repository

import com.example.newsreader.data.model.Article
import com.example.newsreader.data.model.NewsResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class NewsRepository(private val client: HttpClient) {
    private val apiKey = "b73cac97bf0b4e1dbaef1a365a46acea"
    private val baseUrl = "https://newsapi.org/v2"

    suspend fun getTopHeadlines(): Result<List<Article>> {
        return try {
            val response: NewsResponse = client.get("$baseUrl/top-headlines") {
                url {
                    parameters.append("country", "us")
                    parameters.append("category", "technology")
                    parameters.append("apiKey", apiKey)
                }
            }.body()

            val validArticles = response.articles.filter {
                it.title != "[Removed]" && it.urlToImage != null
            }
            Result.success(validArticles)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}