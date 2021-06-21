package com.sh.s1.made.mymovies.home

import androidx.lifecycle.ViewModel
import com.sh.s1.made.mymovies.core.data.MovieRepository
import com.sh.s1.made.mymovies.domain.usecase.MovieUseCase

class HomeViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {
    val moviePopular = movieUseCase.getPopularMovies()
    fun movieTopRated(isInternetAvailable: Boolean) = movieUseCase.getTopRatedMovies(isInternetAvailable)
}