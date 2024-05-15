package com.example.newsapp.service

import com.example.newsapp.models.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface Apiinterface {
    @GET("v2/everything")
    suspend fun getNews(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("apiKey") apiKey: String
    ): Response<NewsResponse>
}