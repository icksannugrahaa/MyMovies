package com.sh.s1.made.mymovies.home

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.sh.s1.made.mymovies.core.domain.usecase.MovieUseCase

class HomeViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {
    val moviePopular = LiveDataReactiveStreams.fromPublisher(movieUseCase.getPopularMovies())
    fun movieTopRated(isInternetAvailable: Boolean) = LiveDataReactiveStreams.fromPublisher(movieUseCase.getTopRatedMovies(isInternetAvailable))
}