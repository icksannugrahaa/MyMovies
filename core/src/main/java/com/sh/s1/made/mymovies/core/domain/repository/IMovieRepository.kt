package com.sh.s1.made.mymovies.core.domain.repository

import com.sh.s1.made.mymovies.core.data.Resource
import com.sh.s1.made.mymovies.core.domain.model.Movie
import io.reactivex.Flowable

interface IMovieRepository {
    fun getPopularMovies(): Flowable<Resource<List<Movie>>>
    fun getTopRatedMovies(isInternetAvailable: Boolean): Flowable<Resource<List<Movie>>>
    fun getFavoriteMovies(): Flowable<List<Movie>>
    fun getSearchMovies(query: String, isInternetAvailable: Boolean): Flowable<Resource<List<Movie>>>
    fun getDetailMovie(id: String, state: Boolean, category: String): Flowable<Resource<Movie>>
    fun setFavoriteMovie(movie: Movie, state: Boolean)
}
