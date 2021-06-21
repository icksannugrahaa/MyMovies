package com.sh.s1.made.mymovies.home

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sh.s1.made.mymovies.R
import com.sh.s1.made.mymovies.core.data.Resource
import com.sh.s1.made.mymovies.core.ui.MovieAdapter
import com.sh.s1.made.mymovies.core.ui.ViewModelFactory
import com.sh.s1.made.mymovies.databinding.FragmentHomeBinding
import com.sh.s1.made.mymovies.detail.DetailMovieActivity
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {
    private val homeViewModel: HomeViewModel by viewModel()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            showPopularMovie()
        }
    }

    override fun onResume() {
        super.onResume()
        showPopularMovie()
        showTopratedMovie()
    }

    private fun showPopularMovie() {
        val moviePopularAdapter = MovieAdapter()
        moviePopularAdapter.onItemClick = { selectedData ->
            Intent(activity, DetailMovieActivity::class.java).apply {
                putExtra(DetailMovieActivity.EXTRA_ID, selectedData.id)
                putExtra(DetailMovieActivity.EXTRA_CATEGORY, selectedData.category)
                putExtra(DetailMovieActivity.EXTRA_FAVORITE, selectedData.isFavorite)
                putExtra(DetailMovieActivity.EXTRA_TITLE, selectedData.title)
                startActivity(this)
            }
        }
        homeViewModel.moviePopular.observe(viewLifecycleOwner, { movie ->
            if (movie != null) {
                when (movie) {
                    is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                    is Resource.Success -> {
                        Log.d("DATA_MOVIE_POPULAR", movie.data.toString())
                        binding.progressBar.visibility = View.GONE
                        moviePopularAdapter.setData(movie.data)
                        showTopratedMovie()
                    }
                    is Resource.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.viewError.root.visibility = View.VISIBLE
                        binding.viewError.tvError.text = movie.message ?: getString(R.string.something_wrong)
                    }
                }
            }
        })

        with(binding.rvMoviePopular) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            adapter = moviePopularAdapter
        }
    }

    private fun showTopratedMovie() {
        val movieTopRatedAdapter = MovieAdapter()
        movieTopRatedAdapter.onItemClick = { selectedData ->
            Intent(activity, DetailMovieActivity::class.java).apply {
                putExtra(DetailMovieActivity.EXTRA_ID, selectedData.id)
                putExtra(DetailMovieActivity.EXTRA_CATEGORY, selectedData.category)
                putExtra(DetailMovieActivity.EXTRA_FAVORITE, selectedData.isFavorite)
                putExtra(DetailMovieActivity.EXTRA_TITLE, selectedData.title)
                startActivity(this)
            }
        }
        val internet = (requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo?.isConnected
        homeViewModel.movieTopRated(internet?:false).observe(viewLifecycleOwner, { movie ->
            if (movie != null) {
                when (movie) {
                    is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                    is Resource.Success -> {
                        Log.d("DATA_MOVIE_TOP", movie.data.toString())
                        binding.progressBar.visibility = View.GONE
                        movieTopRatedAdapter.setData(movie.data)
                    }
                    is Resource.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.viewError.root.visibility = View.VISIBLE
                        binding.viewError.tvError.text = movie.message ?: getString(R.string.something_wrong)
                    }
                }
            }
        })

        with(binding.rvMovieTopRated) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            adapter = movieTopRatedAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}