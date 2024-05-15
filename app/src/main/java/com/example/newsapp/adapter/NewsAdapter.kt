package com.example.newsapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.databinding.ItemLayoutBinding
import com.example.newsapp.models.Article

class NewsAdapter(
    private var articles: List<Article>,
    private val onClick: (Article) -> Unit,
    private val onFavorite: ((Article) -> Unit)? = null,
    private val onDelete: ((Article) -> Unit)? = null
) : RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {

    class ArticleViewHolder(val binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    // This function creates a new ViewHolder when needed
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleViewHolder(binding)
    }

    // This function binds data to the ViewHolder
    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = articles[position]
        holder.binding.title.text = article.title
        holder.binding.description.text = article.description
        holder.binding.date.text = article.publishedAt
        holder.binding.source.text = article.author

        // Load article image using Glide
        val imageUrl = article.urlToImage
        if (imageUrl != null) {
            Glide.with(holder.binding.image.context)
                .load(imageUrl)
                .placeholder(R.drawable.downloading) // Placeholder image
                .into(holder.binding.image)
        } else {
            holder.binding.image.setImageResource(R.drawable.downloading) // Placeholder image
        }

        // Set click listener for the root view
        holder.binding.root.setOnClickListener { onClick(article) }

        // Show or hide favorite button
        if (onFavorite != null) {
            holder.binding.favoriteButton.visibility = View.VISIBLE
            holder.binding.favoriteButton.setOnClickListener { onFavorite?.invoke(article) }
        } else {
            holder.binding.favoriteButton.visibility = View.GONE
        }

        // Show or hide delete button
        if (onDelete != null) {
            holder.binding.deleteButton.visibility = View.VISIBLE
            holder.binding.deleteButton.setOnClickListener { onDelete?.invoke(article) }
        } else {
            holder.binding.deleteButton.visibility = View.GONE
        }
    }

    // This function returns the size of the dataset
    override fun getItemCount(): Int = articles.size

    // This function updates the dataset
    fun updateData(newArticles: List<Article>) {
        articles = newArticles
        notifyDataSetChanged()
    }

    // This function clears the dataset
    fun clearData() {
        articles = emptyList()
        notifyDataSetChanged()
    }
}