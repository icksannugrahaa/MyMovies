package com.sh.s1.made.mymovies.core.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sh.s1.made.mymovies.core.data.MovieRepository
import com.sh.s1.made.mymovies.core.di.Injection
import com.sh.s1.made.mymovies.detail.DetailMovieViewModel
import com.sh.s1.made.mymovies.domain.usecase.MovieUseCase
import com.sh.s1.made.mymovies.favorite.FavoriteViewModel
import com.sh.s1.made.mymovies.home.HomeViewModel
import com.sh.s1.made.mymovies.search.SearchViewModel

class ViewModelFactory private constructor(private val movieUseCase: MovieUseCase) :
    ViewModelProvider.NewInstanceFactory() {

//    companion object {
//        @Volatile
//        private var instance: ViewModelFactory? = null
//
//        fun getInstance(context: Context): ViewModelFactory =
//            instance
//                ?: synchronized(this) {
//                    instance
//                        ?: ViewModelFactory(
//                            Injection.provideMovieUseCase(context)
//                        )
//                }
//    }
//
//    @Suppress("UNCHECKED_CAST")
//    override fun <T : ViewModel> create(modelClass: Class<T>): T =
//        when {
//            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
//                HomeViewModel(movieUseCase) as T
//            }
//            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> {
//                FavoriteViewModel(movieUseCase) as T
//            }
//            modelClass.isAssignableFrom(DetailMovieViewModel::class.java) -> {
//                DetailMovieViewModel(movieUseCase) as T
//            }
//            modelClass.isAssignableFrom(SearchViewModel::class.java) -> {
//                SearchViewModel(movieUseCase) as T
//            }
//            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
//        }
}