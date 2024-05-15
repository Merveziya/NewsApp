package com.example.newsapp.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.FavoriteManager
import com.example.newsapp.adapter.NewsAdapter
import com.example.newsapp.databinding.FragmentFavoriteBinding
import com.example.newsapp.models.Article

class FavoriteFragment : Fragment() {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private lateinit var favoriteManager: FavoriteManager
    private lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favoriteManager = FavoriteManager(requireContext())
        newsAdapter = NewsAdapter(emptyList(), { article ->
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra("article", article)
            startActivity(intent)
        }, null, { article ->
            favoriteManager.removeFavorite(article)
            loadFavorites()
        })

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = newsAdapter
        }

        loadFavorites()
    }

    private fun loadFavorites() {
        val favorites = favoriteManager.getFavorites()
        newsAdapter.updateData(favorites)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
