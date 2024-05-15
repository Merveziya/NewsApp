package com.example.newsapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.models.Article
import com.example.newsapp.repository.NewsRepository
import kotlinx.coroutines.launch
import android.util.Log

class NewsViewModel : ViewModel() {
    private val _news = MutableLiveData<List<Article>>()
    val news: LiveData<List<Article>> get() = _news

    private val newsRepository = NewsRepository()
    private var currentPage = 1
    private var currentQuery = ""

    // Function to get news articles based on a query
    fun getNews(query: String, page: Int, apiKey: String) {
        currentQuery = query
        currentPage = page
        viewModelScope.launch {
            try {
                val response = newsRepository.getNews(query, page, apiKey)
                if (response.isSuccessful) {
                    response.body()?.articles?.let {
                        _news.postValue(it)
                    }
                } else {
                    Log.e("NewsViewModel", "Response was not successful!!")
                }
            } catch (e: Exception) {
                Log.e("NewsViewModel", "Exception occurred!")
            }
        }
    }

    // Function to get more news articles for pagination
    fun getMoreNews(apiKey: String) {
        viewModelScope.launch {
            try {
                val response = newsRepository.getNews(currentQuery, currentPage + 1, apiKey)
                if (response.isSuccessful) {
                    response.body()?.articles?.let {
                        val updatedArticles = _news.value?.toMutableList() ?: mutableListOf()
                        updatedArticles.addAll(it)
                        _news.postValue(updatedArticles)
                        currentPage++
                    }
                } else {
                    Log.e("NewsViewModel", "Response was not successful!!")
                }
            } catch (e: Exception) {
                Log.e("NewsViewModel", "Exception occurred!")
            }
        }
    }
}
