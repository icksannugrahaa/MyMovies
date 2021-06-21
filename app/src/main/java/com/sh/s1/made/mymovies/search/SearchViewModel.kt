package com.sh.s1.made.mymovies.search

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.sh.s1.made.mymovies.domain.usecase.MovieUseCase

class SearchViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {
    fun searchMovie(query: String, isInternetAvailable: Boolean) =
        LiveDataReactiveStreams.fromPublisher(
            movieUseCase.getSearchMovies(
                query,
                isInternetAvailable
            )
        )
}