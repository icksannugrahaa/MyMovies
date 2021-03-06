package com.sh.s1.made.mymovies.detail

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.sh.s1.made.mymovies.core.domain.model.Movie
import com.sh.s1.made.mymovies.core.domain.usecase.MovieUseCase

class DetailMovieViewModel (private val movieUseCase: MovieUseCase) : ViewModel() {
    fun getDetailMovie(id: String, state: Boolean, category: String) = LiveDataReactiveStreams.fromPublisher(movieUseCase.getDetailMovie(id, state, category))
    fun setFavoriteMovie(movie: Movie, newStatus:Boolean) = movieUseCase.setFavoriteMovie(movie, newStatus)
}
