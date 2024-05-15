package com.example.newsapp.repository

import com.example.newsapp.models.NewsResponse
import com.example.newsapp.service.RetrofitServiceInstance
import retrofit2.Response

class NewsRepository {
    // Function to get news articles from the API
    suspend fun getNews(query: String, page: Int, apiKey: String): Response<NewsResponse> {
        return RetrofitServiceInstance.api.getNews(query, page, apiKey)
    }
}
