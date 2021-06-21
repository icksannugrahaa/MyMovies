package com.sh.s1.made.mymovies.core.data.source.local

import android.util.Log
import androidx.lifecycle.LiveData
import com.sh.s1.made.mymovies.core.data.source.local.entity.MovieEntity
import com.sh.s1.made.mymovies.core.data.source.local.room.MovieDao
import com.sh.s1.made.mymovies.domain.model.Movie

class LocalDataSource private constructor(private val movieDao: MovieDao) {

    companion object {
        private var instance: LocalDataSource? = null

        fun getInstance(movieDao: MovieDao): LocalDataSource =
            instance ?: synchronized(this) {
                instance ?: LocalDataSource(movieDao)
            }
    }

    fun getPopularMovies(): LiveData<List<MovieEntity>> = movieDao.getPopularMovies()

    fun getTopRatedMovies(): LiveData<List<MovieEntity>> = movieDao.getTopRatedMovies()

    fun getSearchMovies(query: String): LiveData<List<MovieEntity>> {
        val newQuery = "%$query%"
        return movieDao.getSearchMovies(newQuery)
    }

    fun getDetailMovie(id: String): LiveData<MovieEntity> = movieDao.getDetailMovie(id)

    fun getFavoriteMovies(): LiveData<List<MovieEntity>> = movieDao.getFavoriteMovies()

    fun insertMovie(movies: List<MovieEntity>) = movieDao.insertMovie(movies)

    fun updateMovie(movie: MovieEntity) = movieDao.updateMovie(movie)

    fun setFavoriteMovie(movie: MovieEntity, newState: Boolean) {
        movie.isFavorite = newState
        movieDao.updateMovie(movie)
    }
}