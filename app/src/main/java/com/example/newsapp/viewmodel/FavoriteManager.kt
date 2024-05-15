package com.example.newsapp

import android.content.Context
import com.example.newsapp.models.Article
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class FavoriteManager(context: Context) {
    private val prefs = context.getSharedPreferences("favorites", Context.MODE_PRIVATE)
    private val gson = Gson()

    // Add an article to favorites
    fun addFavorite(article: Article) {
        val favorites = getFavorites().toMutableList()
        favorites.add(article)
        saveFavorites(favorites)
    }

    // Remove an article from favorites
    fun removeFavorite(article: Article) {
        val favorites = getFavorites().toMutableList()
        favorites.remove(article)
        saveFavorites(favorites)
    }

    // Get the list of favorite articles
    fun getFavorites(): List<Article> {
        val json = prefs.getString("favorite_articles", "[]")
        val type = object : TypeToken<List<Article>>() {}.type
        return gson.fromJson(json, type)
    }

    // Save the list of favorite articles to SharedPreferences
    private fun saveFavorites(favorites: List<Article>) {
        val json = gson.toJson(favorites)
        prefs.edit().putString("favorite_articles", json).apply()
    }
}
