package com.sh.s1.made.mymovies.core.di

import android.content.Context
import com.sh.s1.made.mymovies.core.data.MovieRepository
import com.sh.s1.made.mymovies.core.data.source.local.LocalDataSource
import com.sh.s1.made.mymovies.core.data.source.local.room.MovieDatabase
import com.sh.s1.made.mymovies.core.data.source.remote.RemoteDataSource
import com.sh.s1.made.mymovies.core.data.source.remote.network.ApiConfig
import com.sh.s1.made.mymovies.core.utils.AppExecutors
import com.sh.s1.made.mymovies.domain.repository.IMovieRepository
import com.sh.s1.made.mymovies.domain.usecase.MovieInteractor
import com.sh.s1.made.mymovies.domain.usecase.MovieUseCase

object Injection {
    fun provideRepository(context: Context): IMovieRepository {
        val database = MovieDatabase.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance(ApiConfig.provideApiService())
        val localDataSource = LocalDataSource.getInstance(database.movieDao())
        val appExecutors = AppExecutors()

        return MovieRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }

    fun provideMovieUseCase(context: Context): MovieUseCase {
        val repository = provideRepository(context)
        return MovieInteractor(repository)
    }

}