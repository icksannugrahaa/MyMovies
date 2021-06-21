package com.sh.s1.made.mymovies.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ResponseMovies(
		@field:SerializedName("results")
		val results: List<ResultMovieDetail?>? = null,
)