package com.sh.s1.made.mymovies.domain.usecase

import com.sh.s1.made.mymovies.domain.model.Movie
import com.sh.s1.made.mymovies.domain.repository.IMovieRepository

class MovieInteractor(private val movieRepository: IMovieRepository): MovieUseCase {
    override fun getPopularMovies() = movieRepository.getPopularMovies()
    override fun getTopRatedMovies(isInternetAvailable: Boolean) = movieRepository.getTopRatedMovies(isInternetAvailable)
    override fun getFavoriteMovies() = movieRepository.getFavoriteMovies()
    override fun getSearchMovies(query: String, isInternetAvailable: Boolean) = movieRepository.getSearchMovies(query, isInternetAvailable)
    override fun getDetailMovie(id: String, state: Boolean, category: String) = movieRepository.getDetailMovie(id, state, category)
    override fun setFavoriteMovie(movie: Movie, state: Boolean) = movieRepository.setFavoriteMovie(movie, state)
}