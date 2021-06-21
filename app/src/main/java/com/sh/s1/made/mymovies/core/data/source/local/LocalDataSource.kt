package com.sh.s1.made.mymovies.core.data.source.local

import android.util.Log
import androidx.lifecycle.LiveData
import com.sh.s1.made.mymovies.core.data.source.local.entity.MovieEntity
import com.sh.s1.made.mymovies.core.data.source.local.room.MovieDao
import com.sh.s1.made.mymovies.domain.model.Movie
import io.reactivex.Flowable

class LocalDataSource(private val movieDao: MovieDao) {

    companion object {
//        private var instance: LocalDataSource? = null
//
//        fun getInstance(movieDao: MovieDao): LocalDataSource =
//            instance ?: synchronized(this) {
//                instance ?: LocalDataSource(movieDao)
//            }
    }

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