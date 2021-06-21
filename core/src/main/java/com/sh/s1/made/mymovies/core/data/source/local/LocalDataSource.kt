package com.sh.s1.made.mymovies.core.data.source.local

import com.sh.s1.made.mymovies.core.data.source.local.entity.MovieEntity
import com.sh.s1.made.mymovies.core.data.source.local.room.MovieDao
import io.reactivex.Flowable

class LocalDataSource(private val movieDao: MovieDao) {

    fun getPopularMovies(): Flowable<List<MovieEntity>> = movieDao.getPopularMovies()

    fun getTopRatedMovies(): Flowable<List<MovieEntity>> = movieDao.getTopRatedMovies()

    fun getSearchMovies(query: String): Flowable<List<MovieEntity>> {
        val newQuery = "%$query%"
        return movieDao.getSearchMovies(newQuery)
    }

    fun getDetailMovie(id: String): Flowable<MovieEntity> = movieDao.getDetailMovie(id)

    fun getFavoriteMovies(): Flowable<List<MovieEntity>> = movieDao.getFavoriteMovies()

    fun insertMovie(movies: List<MovieEntity>) = movieDao.insertMovie(movies)

    fun updateMovie(movie: MovieEntity) = movieDao.updateMovie(movie)

    fun setFavoriteMovie(movie: MovieEntity, newState: Boolean) {
        movie.isFavorite = newState
        movieDao.updateFavoriteMovie(movie)
    }
}