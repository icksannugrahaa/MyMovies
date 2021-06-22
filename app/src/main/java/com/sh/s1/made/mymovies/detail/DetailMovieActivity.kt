package com.sh.s1.made.mymovies.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.core.content.ContextCompat
import com.sh.s1.made.mymovies.R
import com.sh.s1.made.mymovies.core.data.Resource
import com.sh.s1.made.mymovies.core.utils.GlideUtils.loadImage
import com.sh.s1.made.mymovies.core.utils.MyUtils.toHourStringFormat
import com.sh.s1.made.mymovies.databinding.ActivityDetailMovieBinding
import com.sh.s1.made.mymovies.core.domain.model.Movie
import org.koin.android.viewmodel.ext.android.viewModel

class DetailMovieActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID = "extra_id"
        const val EXTRA_CATEGORY = "extra_category"
        const val EXTRA_FAVORITE = "extra_favorite"
        const val EXTRA_TITLE = "extra_title"
    }

    private val detailMovieViewModel: DetailMovieViewModel by viewModel()
    private lateinit var binding: ActivityDetailMovieBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        val id = intent.extras?.getInt(EXTRA_ID)
        val favorite = intent.extras?.getBoolean(EXTRA_FAVORITE)
        val category = intent.extras?.getString(EXTRA_CATEGORY)
        val title = intent.extras?.getString(EXTRA_TITLE)
        supportActionBar?.title = title.toString()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (id != null) {
            detailMovieViewModel.getDetailMovie(id.toString(), favorite!!, category!!).observe(this) { movie ->
                if (movie != null) {
                    when (movie) {
                        is Resource.Loading -> binding.content.progressBar.visibility = View.VISIBLE
                        is Resource.Success -> {
                            binding.content.progressBar.visibility = View.GONE
                            setDetailMovie(movie.data)
                        }
                        is Resource.Error -> {
                            binding.content.progressBar.visibility = View.GONE
                            binding.content.viewError.root.visibility = View.VISIBLE
                            binding.content.viewError.tvError.text =
                                movie.message ?: getString(R.string.something_wrong)
                        }
                    }
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun setDetailMovie(movie: Movie?) {
        movie?.let {
            with(binding.content) {
                tvDescrtiption.text = it.overview
                tvGenre.text = it.genres
                tvRelease.text = it.releaseDate
                tvTagline.text = it.tagLine
                tvDuration.text = toHourStringFormat(it.length)
                movieProgressScore.progress = ((it.voteScore?.toInt() ?: 0) * 10)
                movieTvScore.text = it.voteScore.toString()
                binding.ivDetailImage.loadImage(it.posterPath)
            }
            var statusFavorite = movie.isFavorite
            setStatusFavorite(statusFavorite)
            binding.fab.setOnClickListener {
                statusFavorite = !statusFavorite
                setStatusFavorite(statusFavorite)
                detailMovieViewModel.setFavoriteMovie(movie, statusFavorite)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val item = menu.findItem(R.id.menu_search)
        item.isVisible = false
        return true
    }

    private fun setStatusFavorite(statusFavorite: Boolean) {
        if (statusFavorite) {
            binding.fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite_white))
        } else {
            binding.fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_not_favorite_white))
        }
    }
}