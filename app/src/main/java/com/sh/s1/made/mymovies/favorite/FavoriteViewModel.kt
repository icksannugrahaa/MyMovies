package com.sh.s1.made.mymovies.favorite

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.sh.s1.made.mymovies.core.domain.usecase.MovieUseCase

class FavoriteViewModel(movieUseCase: MovieUseCase) : ViewModel() {
    val favoriteMovie = LiveDataReactiveStreams.fromPublisher(movieUseCase.getFavoriteMovies())
}