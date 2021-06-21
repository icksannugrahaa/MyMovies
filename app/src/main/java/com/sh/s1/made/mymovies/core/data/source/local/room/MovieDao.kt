package com.sh.s1.made.mymovies.core.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sh.s1.made.mymovies.core.data.source.local.entity.MovieEntity

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies where category = 'popular'")
    fun getPopularMovies(): LiveData<List<MovieEntity>>

    @Query("SELECT * FROM movies order by voteScore desc limit 20")
    fun getTopRatedMovies(): LiveData<List<MovieEntity>>

    @Query("SELECT * FROM movies where movieId = :id")
    fun getDetailMovie(id: String): LiveData<MovieEntity>

    @Query("SELECT * FROM movies where title LIKE :query")
    fun getSearchMovies(query: String): LiveData<List<MovieEntity>>

    @Query("SELECT * FROM movies where isFavorite = 1")
    fun getFavoriteMovies(): LiveData<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMovie(movies: List<MovieEntity>)

    @Update
    fun updateMovie(movie: MovieEntity)
}