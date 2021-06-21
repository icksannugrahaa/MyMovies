package com.sh.s1.made.mymovies.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ResultMovieDetail(

        @field:SerializedName("original_title")
        val title: String? = null,

        @field:SerializedName("backdrop_path")
        val backdropPath: String? = null,

        @field:SerializedName("genres")
        val genres: List<GenresItem?>? = null,

        @field:SerializedName("id")
        val id: Int? = null,

        @field:SerializedName("overview")
        val overview: String? = null,

        @field:SerializedName("runtime")
        val runtime: Int? = null,

        @field:SerializedName("poster_path")
        val posterPath: String? = null,

        @field:SerializedName("production_companies")
        val productionCountries: List<ProductionCountriesItem?>? = null,

        @field:SerializedName("release_date")
        val releaseDate: String? = null,

        @field:SerializedName("vote_average")
        val voteAverage: Double? = null,

        @field:SerializedName("tagline")
        val tagline: String? = null,

        @field:SerializedName("homepage")
        val homepage: String? = null,

        @field:SerializedName("status")
        val status: String? = null
)