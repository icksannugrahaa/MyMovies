package com.sh.s1.made.mymovies.core.data.source.local.room

import androidx.room.*
import com.sh.s1.made.mymovies.core.data.source.local.entity.MovieEntity
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies where category = 'popular'")
    fun getPopularMovies(): Flowable<List<MovieEntity>>

    @Query("SELECT * FROM movies order by voteScore desc limit 20")
    fun getTopRatedMovies(): Flowable<List<MovieEntity>>

    @Query("SELECT * FROM movies where movieId = :id")
    fun getDetailMovie(id: String): Flowable<MovieEntity>

    @Query("SELECT * FROM movies where title LIKE :query")
    fun getSearchMovies(query: String): Flowable<List<MovieEntity>>

    @Query("SELECT * FROM movies where isFavorite = 1")
    fun getFavoriteMovies(): Flowable<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMovie(movies: List<MovieEntity>) : Completable

    @Update
    fun updateMovie(movie: MovieEntity) : Completable

    @Update
    fun updateFavoriteMovie(movie: MovieEntity)
}