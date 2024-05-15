package com.example.newsapp

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.newsapp.adapter.ViewPagerAdapter
import com.example.newsapp.databinding.ActivityMainBinding
import com.example.newsapp.view.FavoriteFragment
import com.example.newsapp.view.NewsFragment
import com.example.newsapp.viewmodel.NewsViewModel
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: NewsViewModel by viewModels()
    private lateinit var newsFragment: NewsFragment
    private lateinit var favoriteFragment: FavoriteFragment
    private lateinit var favoriteManager: FavoriteManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        favoriteManager = FavoriteManager(this)
        newsFragment = NewsFragment()
        favoriteFragment = FavoriteFragment()

        setupViewPager()

        // Set click listener for the search button
        binding.searchButton.setOnClickListener {
            val query = binding.searchEditText.text.toString()
            if (query.isNotEmpty()) {
                newsFragment.performSearch(query) // Perform search
            }
        }
    }

    private fun setupViewPager() {
        val fragments = listOf(
            newsFragment, // News fragment
            favoriteFragment // Favorites fragment
        )

        val titles = listOf(
            "News",
            "Favorites"
        )

        val adapter = ViewPagerAdapter(this, fragments)
        binding.viewPager.adapter = adapter

        // Attach the TabLayout with the ViewPager
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = titles[position]
        }.attach()
    }
}
