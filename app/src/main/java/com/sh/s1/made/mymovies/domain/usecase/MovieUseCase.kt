package com.sh.s1.made.mymovies.domain.usecase

import androidx.lifecycle.LiveData
import com.sh.s1.made.mymovies.core.data.Resource
import com.sh.s1.made.mymovies.domain.model.Movie

interface MovieUseCase {
    fun getPopularMovies(): LiveData<Resource<List<Movie>>>
    fun getTopRatedMovies(isInternetAvailable: Boolean): LiveData<Resource<List<Movie>>>
    fun getFavoriteMovies(): LiveData<List<Movie>>
    fun getSearchMovies(query: String, isInternetAvailable: Boolean): LiveData<Resource<List<Movie>>>
    fun getDetailMovie(id: String, state: Boolean, category: String): LiveData<Resource<Movie>>
    fun setFavoriteMovie(movie: Movie, state: Boolean)
}