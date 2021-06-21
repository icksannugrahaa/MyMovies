package com.sh.s1.made.mymovies.di

import com.sh.s1.made.mymovies.detail.DetailMovieViewModel
import com.sh.s1.made.mymovies.core.domain.usecase.MovieInteractor
import com.sh.s1.made.mymovies.core.domain.usecase.MovieUseCase
import com.sh.s1.made.mymovies.favorite.FavoriteViewModel
import com.sh.s1.made.mymovies.home.HomeViewModel
import com.sh.s1.made.mymovies.search.SearchViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

object AppModule {
    val useCaseModule = module {
        factory<MovieUseCase> { MovieInteractor(get()) }
    }

    val viewModelModule = module {
        viewModel { HomeViewModel(get()) }
        viewModel { FavoriteViewModel(get()) }
        viewModel { DetailMovieViewModel(get()) }
        viewModel { SearchViewModel(get()) }
    }

}