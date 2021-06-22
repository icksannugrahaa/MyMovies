package com.sh.s1.made.mymovies.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.sh.s1.made.mymovies.core.ui.SearchAdapter
import com.sh.s1.made.mymovies.detail.DetailMovieActivity
import com.sh.s1.made.mymovies.favorite.FavoriteModule.favoriteModule
import com.sh.s1.made.mymovies.favorite.databinding.ActivityFavoriteBinding
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private val favoriteViewModel: FavoriteViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadKoinModules(favoriteModule)

        val movieAdapter = SearchAdapter()
        movieAdapter.onItemClick = { selectedData ->
            Intent(this, DetailMovieActivity::class.java).apply {
                putExtra(DetailMovieActivity.EXTRA_ID, selectedData.id)
                putExtra(DetailMovieActivity.EXTRA_CATEGORY, selectedData.category)
                putExtra(DetailMovieActivity.EXTRA_FAVORITE, selectedData.isFavorite)
                putExtra(DetailMovieActivity.EXTRA_TITLE, selectedData.title)
                startActivity(this)
            }
        }

        favoriteViewModel.favoriteMovie.observe(this) { movies ->
            movieAdapter.setData(movies)
            binding.viewEmpty.root.visibility =
                if (movies.isNotEmpty()) View.GONE else View.VISIBLE
        }

        with(binding.rvFavorite) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = movieAdapter
        }
    }
}