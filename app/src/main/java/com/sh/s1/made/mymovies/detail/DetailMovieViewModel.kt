package com.sh.s1.made.mymovies.detail

import androidx.lifecycle.ViewModel
import com.sh.s1.made.mymovies.core.data.MovieRepository
import com.sh.s1.made.mymovies.domain.model.Movie
import com.sh.s1.made.mymovies.domain.usecase.MovieUseCase

class DetailMovieViewModel (private val movieUseCase: MovieUseCase) : ViewModel() {
    fun getDetailMovie(id: String, state: Boolean, category: String) = movieUseCase.getDetailMovie(id, state, category)
    fun setFavoriteMovie(movie: Movie, newStatus:Boolean) = movieUseCase.setFavoriteMovie(movie, newStatus)
}
