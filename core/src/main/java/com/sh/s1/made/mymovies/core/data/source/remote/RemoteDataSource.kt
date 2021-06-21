package com.sh.s1.made.mymovies.core.data.source.remote

import android.annotation.SuppressLint
import android.util.Log
import com.sh.s1.made.mymovies.core.BuildConfig
import com.sh.s1.made.mymovies.core.data.source.remote.network.ApiResponse
import com.sh.s1.made.mymovies.core.data.source.remote.network.ApiService
import com.sh.s1.made.mymovies.core.data.source.remote.response.ResultMovieDetail
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

@SuppressLint("CheckResult")
class RemoteDataSource(private val apiService: ApiService) {

    fun getPopularMovies(): Flowable<ApiResponse<List<ResultMovieDetail>>> {
        val resultData = PublishSubject.create<ApiResponse<List<ResultMovieDetail>>>()

        //get data from remote api
        val client = apiService.getFavoriteMovies(api_key = BuildConfig.API_KEY, language = "en-US", page = "1")

        client
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe ({ response ->
                val dataArray = response.results
                resultData.onNext((if (dataArray != null) ApiResponse.Success(dataArray) else ApiResponse.Empty) as ApiResponse<List<ResultMovieDetail>>)
            }, { error ->
                resultData.onNext(ApiResponse.Error(error.message.toString()))
                Log.e("RemoteDataSource", error.toString())
            })

        return resultData.toFlowable(BackpressureStrategy.BUFFER)
    }

    fun getTopRatedMovies(): Flowable<ApiResponse<List<ResultMovieDetail>>> {
        val resultData = PublishSubject.create<ApiResponse<List<ResultMovieDetail>>>()

        //get data from remote api
        val client = apiService.getTopRatedMovies(api_key = BuildConfig.API_KEY, language = "en-US", page = "1")

        client
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe ({ response ->
                val dataArray = response.results
                resultData.onNext((if (dataArray != null) ApiResponse.Success(dataArray) else ApiResponse.Empty) as ApiResponse<List<ResultMovieDetail>>)
            }, { error ->
                resultData.onNext(ApiResponse.Error(error.message.toString()))
                Log.e("RemoteDataSource", error.toString())
            })

        return resultData.toFlowable(BackpressureStrategy.BUFFER)
    }

    fun getSearchMovies(query: String) : Flowable<ApiResponse<List<ResultMovieDetail>>> {
        val resultData = PublishSubject.create<ApiResponse<List<ResultMovieDetail>>>()

        //get data from remote api
        val client = apiService.getSearchMovies(api_key = BuildConfig.API_KEY, language = "en-US", page = "1", query = query)

        client
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe ({ response ->
                val dataArray = response.results
                resultData.onNext((if (dataArray != null) ApiResponse.Success(dataArray) else ApiResponse.Empty) as ApiResponse<List<ResultMovieDetail>>)
            }, { error ->
                resultData.onNext(ApiResponse.Error(error.message.toString()))
                Log.e("RemoteDataSource", error.toString())
            })

        return resultData.toFlowable(BackpressureStrategy.BUFFER)
    }

    fun getDetailMovie(id: String) : Flowable<ApiResponse<ResultMovieDetail>> {
        val resultData = PublishSubject.create<ApiResponse<ResultMovieDetail>>()

        //get data from remote api
        val client = apiService.getDetailMovie(api_key = BuildConfig.API_KEY, language = "en-US", id = id)

        client
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe ({ response ->
                resultData.onNext(if(response.id != null) ApiResponse.Success(response) else ApiResponse.Empty)
            }, { error ->
                resultData.onNext(ApiResponse.Error(error.message.toString()))
                Log.e("RemoteDataSource", error.toString())
            })

        return resultData.toFlowable(BackpressureStrategy.BUFFER)
    }
}