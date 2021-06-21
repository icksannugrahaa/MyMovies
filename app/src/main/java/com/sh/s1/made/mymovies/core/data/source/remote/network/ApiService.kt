package com.sh.s1.made.mymovies.core.data.source.remote.network

import com.sh.s1.made.mymovies.core.data.source.remote.response.ResponseMovies
import com.sh.s1.made.mymovies.core.data.source.remote.response.ResultMovieDetail
import com.sh.s1.made.mymovies.core.utils.Consts.MOVIE_DETAIL_EP
import com.sh.s1.made.mymovies.core.utils.Consts.MOVIE_POPULAR_EP
import com.sh.s1.made.mymovies.core.utils.Consts.MOVIE_SEARCH_EP
import com.sh.s1.made.mymovies.core.utils.Consts.MOVIE_TOP_RATED_EP
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET(MOVIE_POPULAR_EP)
    fun getFavoriteMovies(
        @Query("api_key") api_key: String,
        @Query("language") language: String,
        @Query("page") page: String
    ): Call<ResponseMovies>

    @GET(MOVIE_TOP_RATED_EP)
    fun getTopRatedMovies(
        @Query("api_key") api_key: String,
        @Query("language") language: String,
        @Query("page") page: String
    ): Call<ResponseMovies>

    @GET(MOVIE_SEARCH_EP)
    fun getSearchMovies(
        @Query("api_key") api_key: String,
        @Query("language") language: String,
        @Query("query") query: String,
        @Query("page") page: String
    ): Call<ResponseMovies>

    @GET(MOVIE_DETAIL_EP)
    fun getDetailMovie(
        @Path("movie_id") id: String,
        @Query("api_key") api_key: String,
        @Query("language") language: String
    ): Call<ResultMovieDetail>
}
