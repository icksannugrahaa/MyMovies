package com.sh.s1.made.mymovies.core.data.source.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sh.s1.made.mymovies.BuildConfig
import com.sh.s1.made.mymovies.core.data.source.remote.network.ApiResponse
import com.sh.s1.made.mymovies.core.data.source.remote.network.ApiService
import com.sh.s1.made.mymovies.core.data.source.remote.response.ResponseMovies
import com.sh.s1.made.mymovies.core.data.source.remote.response.ResultMovieDetail
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource private constructor(private val apiService: ApiService) {
    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(service: ApiService): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource(service)
            }
    }

    fun getPopularMovies(): LiveData<ApiResponse<List<ResultMovieDetail>>> {
        val resultData = MutableLiveData<ApiResponse<List<ResultMovieDetail>>>()

        //get data from remote api
        val client = apiService.getFavoriteMovies(api_key = BuildConfig.API_KEY, language = "en-US", page = "1")

        client.enqueue(object : Callback<ResponseMovies> {
            override fun onResponse(
                call: Call<ResponseMovies>,
                response: Response<ResponseMovies>
            ) {
                val dataArray = response.body()?.results
                Log.d("DATA_POPULER_REMOT",dataArray.toString())
                resultData.value = if (dataArray != null) ApiResponse.Success(dataArray) as ApiResponse<List<ResultMovieDetail>>? else ApiResponse.Empty
            }
            override fun onFailure(call: Call<ResponseMovies>, t: Throwable) {
                resultData.value = ApiResponse.Error(t.message.toString())
                Log.e("RemoteDataSource", t.message.toString())
            }
        })

        return resultData
    }

    fun getTopRatedMovies(): LiveData<ApiResponse<List<ResultMovieDetail>>> {
        val resultData = MutableLiveData<ApiResponse<List<ResultMovieDetail>>>()

        //get data from remote api
        val client = apiService.getTopRatedMovies(api_key = BuildConfig.API_KEY, language = "en-US", page = "1")

        client.enqueue(object : Callback<ResponseMovies> {
            override fun onResponse(
                call: Call<ResponseMovies>,
                response: Response<ResponseMovies>
            ) {
                val dataArray = response.body()?.results
                resultData.value = if (dataArray != null) ApiResponse.Success(dataArray) as ApiResponse<List<ResultMovieDetail>>? else ApiResponse.Empty
            }
            override fun onFailure(call: Call<ResponseMovies>, t: Throwable) {
                resultData.value = ApiResponse.Error(t.message.toString())
                Log.e("RemoteDataSource", t.message.toString())
            }
        })

        return resultData
    }

    fun getSearchMovies(query: String) : LiveData<ApiResponse<List<ResultMovieDetail>>> {
        val resultData = MutableLiveData<ApiResponse<List<ResultMovieDetail>>>()

        //get data from remote api
        val client = apiService.getSearchMovies(api_key = BuildConfig.API_KEY, language = "en-US", page = "1", query = query)

        client.enqueue(object : Callback<ResponseMovies> {
            override fun onResponse(
                call: Call<ResponseMovies>,
                response: Response<ResponseMovies>
            ) {
                val dataArray = response.body()?.results
                Log.d("DATA_SEARCH_REMOT",dataArray.toString())
                resultData.value = if (dataArray != null) ApiResponse.Success(dataArray) as ApiResponse<List<ResultMovieDetail>>? else ApiResponse.Empty
            }
            override fun onFailure(call: Call<ResponseMovies>, t: Throwable) {
                resultData.value = ApiResponse.Error(t.message.toString())
                Log.e("RemoteDataSource", t.message.toString())
            }
        })

        return resultData
    }

    fun getDetailMovie(id: String) : LiveData<ApiResponse<ResultMovieDetail>> {
        val resultData = MutableLiveData<ApiResponse<ResultMovieDetail>>()

        //get data from remote api
        val client = apiService.getDetailMovie(api_key = BuildConfig.API_KEY, language = "en-US", id = id)

        client.enqueue(object : Callback<ResultMovieDetail> {
            override fun onResponse(
                call: Call<ResultMovieDetail>,
                response: Response<ResultMovieDetail>
            ) {
                val dataArray = response.body()
                Log.d("DATA_SEARCH_REMOT",dataArray.toString())
                resultData.value = if (dataArray != null) ApiResponse.Success(dataArray) else ApiResponse.Empty
            }

            override fun onFailure(call: Call<ResultMovieDetail>, t: Throwable) {
                resultData.value = ApiResponse.Error(t.message.toString())
                Log.e("RemoteDataSource", t.message.toString())
            }
        })

        return resultData
    }
}