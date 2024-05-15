package com.example.newsapp.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.FavoriteManager
import com.example.newsapp.adapter.NewsAdapter
import com.example.newsapp.databinding.FragmentNewsBinding
import com.example.newsapp.viewmodel.NewsViewModel

class NewsFragment : Fragment() {
    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var favoriteManager: FavoriteManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
        favoriteManager = FavoriteManager(requireContext())

        newsAdapter = NewsAdapter(emptyList(), { article ->
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra("article", article)
            startActivity(intent)
        }, { article ->
            favoriteManager.addFavorite(article)
        })

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = newsAdapter

            // Add scroll listener to load more news when reaching the end
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (!recyclerView.canScrollVertically(1)) {
                        viewModel.getMoreNews("1e710a5bba3a41458bbcd05f474f21a7")
                    }
                }
            })
        }

        viewModel.news.observe(viewLifecycleOwner, { articles ->
            articles?.let {
                newsAdapter.updateData(it)
            }
        })
    }

    fun performSearch(query: String) {
        Log.d("NewsFragment", "Performing search with query: $query")
        viewModel.getNews(query, 1, "1e710a5bba3a41458bbcd05f474f21a7")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
