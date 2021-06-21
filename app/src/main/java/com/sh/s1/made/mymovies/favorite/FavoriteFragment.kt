package com.sh.s1.made.mymovies.favorite

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.sh.s1.made.mymovies.core.ui.SearchAdapter
import com.sh.s1.made.mymovies.databinding.FragmentFavoriteBinding
import com.sh.s1.made.mymovies.detail.DetailMovieActivity
import org.koin.android.viewmodel.ext.android.viewModel

class FavoriteFragment : Fragment() {
    private val favoriteViewModel: FavoriteViewModel by viewModel()

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {

            val movieAdapter = SearchAdapter()
            movieAdapter.onItemClick = { selectedData ->
                Intent(activity, DetailMovieActivity::class.java).apply {
                    putExtra(DetailMovieActivity.EXTRA_ID, selectedData.id)
                    putExtra(DetailMovieActivity.EXTRA_CATEGORY, selectedData.category)
                    putExtra(DetailMovieActivity.EXTRA_FAVORITE, selectedData.isFavorite)
                    putExtra(DetailMovieActivity.EXTRA_TITLE, selectedData.title)
                    startActivity(this)
                }
            }

            favoriteViewModel.favoriteMovie.observe(viewLifecycleOwner, { movies ->
                movieAdapter.setData(movies)
                binding.viewEmpty.root.visibility = if (movies.isNotEmpty()) View.GONE else View.VISIBLE
            })

            with(binding.rvTourism) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = movieAdapter
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}