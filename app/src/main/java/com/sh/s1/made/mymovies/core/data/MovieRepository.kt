package com.sh.s1.made.mymovies.core.data

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.sh.s1.made.mymovies.core.data.source.local.LocalDataSource
import com.sh.s1.made.mymovies.core.data.source.remote.RemoteDataSource
import com.sh.s1.made.mymovies.core.data.source.remote.network.ApiResponse
import com.sh.s1.made.mymovies.core.data.source.remote.response.ResultMovieDetail
import com.sh.s1.made.mymovies.core.utils.AppExecutors
import com.sh.s1.made.mymovies.core.utils.DataMapper
import com.sh.s1.made.mymovies.domain.model.Movie
import com.sh.s1.made.mymovies.domain.repository.IMovieRepository
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MovieRepository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IMovieRepository {

    companion object {
        @Volatile
        private var instance: MovieRepository? = null

        fun getInstance(
            remoteData: RemoteDataSource,
            localData: LocalDataSource,
            appExecutors: AppExecutors
        ): MovieRepository =
            instance ?: synchronized(this) {
                instance ?: MovieRepository(remoteData, localData, appExecutors)
            }
    }

    override fun getPopularMovies(): Flowable<Resource<List<Movie>>> =
        object : NetworkBoundResource<List<Movie>, List<ResultMovieDetail>>(appExecutors) {
            override fun loadFromDB(): Flowable<List<Movie>> {
                return localDataSource.getPopularMovies().map { DataMapper.mapEntitiesToDomain(it) }
            }

            override fun shouldFetch(data: List<Movie>?): Boolean =
                data == null || data.isEmpty()

            override fun createCall(): Flowable<ApiResponse<List<ResultMovieDetail>>> =
                remoteDataSource.getPopularMovies()

            override fun saveCallResult(data: List<ResultMovieDetail>) {
                val movieList = DataMapper.mapResponsesToEntities(data, "popular")
                localDataSource.insertMovie(movieList)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()
            }
        }.asFlowable()

    override fun getTopRatedMovies(isInternetAvailable: Boolean): Flowable<Resource<List<Movie>>> =
        object : NetworkBoundResource<List<Movie>, List<ResultMovieDetail>>(appExecutors) {
            override fun loadFromDB(): Flowable<List<Movie>> {
                return localDataSource.getTopRatedMovies()
                    .map { DataMapper.mapEntitiesToDomain(it) }
            }

            override fun shouldFetch(data: List<Movie>?): Boolean =
                data == null || data.isEmpty() || isInternetAvailable

            override fun createCall(): Flowable<ApiResponse<List<ResultMovieDetail>>> =
                remoteDataSource.getTopRatedMovies()

            override fun saveCallResult(data: List<ResultMovieDetail>) {
                val movieList = DataMapper.mapResponsesToEntities(data, "")
                localDataSource.insertMovie(movieList)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()
            }
        }.asFlowable()

    override fun getFavoriteMovies(): Flowable<List<Movie>> =
        localDataSource.getFavoriteMovies().map { DataMapper.mapEntitiesToDomain(it) }

    override fun getSearchMovies(
        query: String,
        isInternetAvailable: Boolean
    ): Flowable<Resource<List<Movie>>> =
        object : NetworkBoundResource<List<Movie>, List<ResultMovieDetail>>(appExecutors) {
            override fun loadFromDB(): Flowable<List<Movie>> {
                return localDataSource.getSearchMovies(query).map { DataMapper.mapEntitiesToDomain(it) }
            }

            override fun shouldFetch(data: List<Movie>?): Boolean =
                data == null || data.isEmpty() || isInternetAvailable

            override fun createCall(): Flowable<ApiResponse<List<ResultMovieDetail>>> =
                remoteDataSource.getSearchMovies(query)

            override fun saveCallResult(data: List<ResultMovieDetail>) {
                val movieList = DataMapper.mapResponsesToEntities(data, "")
                localDataSource.insertMovie(movieList)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()
            }
        }.asFlowable()

    override fun getDetailMovie(
        id: String,
        state: Boolean,
        category: String
    ): Flowable<Resource<Movie>> =
        object : NetworkBoundResource<Movie, ResultMovieDetail>(appExecutors) {
            override fun loadFromDB(): Flowable<Movie> {
                return localDataSource.getDetailMovie(id).map { DataMapper.mapEntityToDomain(it) }
            }

            override fun shouldFetch(data: Movie?): Boolean =
                data?.genres == "" || data?.length == null

            override fun createCall(): Flowable<ApiResponse<ResultMovieDetail>> =
                remoteDataSource.getDetailMovie(id)

            override fun saveCallResult(data: ResultMovieDetail) {
                val movie = DataMapper.mapResponseToEntity(data, category, state)
                localDataSource.updateMovie(movie)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()
            }
        }.asFlowable()


    override fun setFavoriteMovie(movie: Movie, state: Boolean) {
        val movieEntity = DataMapper.mapDomainToEntity(movie)
        appExecutors.diskIO().execute {
            localDataSource.setFavoriteMovie(movieEntity, state)
        }
    }
}