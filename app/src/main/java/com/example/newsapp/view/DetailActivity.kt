package com.example.newsapp.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.newsapp.FavoriteManager
import com.example.newsapp.databinding.ActivityDetailBinding
import com.example.newsapp.models.Article

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var favoriteManager: FavoriteManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        favoriteManager = FavoriteManager(this)

        val article = intent.getParcelableExtra<Article>("article")

        if (article != null) {
            binding.article = article // Bind article to the layout
        } else {
            Log.e("","");
        }

        // Open the article URL in a WebViewActivity
        binding.sourceButton.setOnClickListener {
            article?.url?.let { url ->
                val intent = Intent(this, WebViewActivity::class.java)
                intent.putExtra("url", url)
                startActivity(intent)
            }
        }

        // Add article to favorites
        binding.favoriteButton.setOnClickListener {
            article?.let {
                favoriteManager.addFavorite(it)
                Toast.makeText(this, "Added to favorites", Toast.LENGTH_SHORT).show()
            }
        }

        // Share the article URL
        binding.shareButton.setOnClickListener {
            article?.url?.let { url ->
                val shareIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, url)
                    type = "text/plain"
                }
                startActivity(Intent.createChooser(shareIntent, "Share link!"))
            }
        }
    }
}
