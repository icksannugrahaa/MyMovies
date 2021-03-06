package com.sh.s1.made.mymovies.search

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.sh.s1.made.mymovies.R
import com.sh.s1.made.mymovies.core.data.Resource
import com.sh.s1.made.mymovies.core.ui.SearchAdapter
import com.sh.s1.made.mymovies.databinding.FragmentSearchBinding
import com.sh.s1.made.mymovies.detail.DetailMovieActivity
import com.sh.s1.made.mymovies.utils.NetworkUtils.isInternetAvailable
import org.koin.android.viewmodel.ext.android.viewModel
import java.lang.StringBuilder

class SearchFragment : Fragment() {
    private val searchViewModel: SearchViewModel by viewModel()
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    companion object {
        const val ARG_QUERY = "query"

        fun newInstance(query: String): SearchFragment {
            val fragment = SearchFragment()

            val bundle = Bundle().apply {
                putString(ARG_QUERY, query)
            }

            fragment.arguments = bundle

            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            searchMovies()
        }
    }

    private fun searchMovies() {
        val query = arguments?.getString(ARG_QUERY)

        val searchAdapter = SearchAdapter()
        searchAdapter.onItemClick = { selectedData ->
            Intent(activity, DetailMovieActivity::class.java).apply {
                putExtra(DetailMovieActivity.EXTRA_ID, selectedData.id)
                putExtra(DetailMovieActivity.EXTRA_CATEGORY, selectedData.category)
                putExtra(DetailMovieActivity.EXTRA_FAVORITE, selectedData.isFavorite)
                putExtra(DetailMovieActivity.EXTRA_TITLE, selectedData.title)
                startActivity(this)
            }
        }

        if(query != null) {
            binding.tvSearchResult.text = StringBuilder().append("Search \"$query\"").toString()
            searchViewModel.searchMovie(query, isInternetAvailable(requireContext())).observe(viewLifecycleOwner) { movie ->
                if (movie != null) {
                    when (movie) {
                        is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                        is Resource.Success -> {
                            binding.progressBar.visibility = View.GONE
                            searchAdapter.setData(movie.data)
                        }
                        is Resource.Error -> {
                            binding.progressBar.visibility = View.GONE
                            binding.viewError.root.visibility = View.VISIBLE
                            binding.viewError.tvError.text =
                                movie.message ?: getString(R.string.something_wrong)
                        }
                    }
                }

                with(binding.rvMovieSearch) {
                    layoutManager = LinearLayoutManager(context)
                    setHasFixedSize(true)
                    adapter = searchAdapter
                }
            }
        }
    }
}