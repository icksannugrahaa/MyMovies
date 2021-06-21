package com.sh.s1.made.mymovies.favorite

import androidx.lifecycle.ViewModel
import com.sh.s1.made.mymovies.core.data.MovieRepository
import com.sh.s1.made.mymovies.domain.usecase.MovieUseCase

class FavoriteViewModel(movieUseCase: MovieUseCase) : ViewModel() {
    val favoriteMovie = movieUseCase.getFavoriteMovies()
}