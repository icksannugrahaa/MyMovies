package com.sh.s1.made.mymovies.favorite

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

object FavoriteModule {
    val favoriteModule = module {
        viewModel { FavoriteViewModel(get()) }
    }

}