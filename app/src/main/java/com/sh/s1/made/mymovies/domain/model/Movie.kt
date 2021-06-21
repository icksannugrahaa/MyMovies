package com.sh.s1.made.mymovies.domain.model

data class Movie (
    var id: Int = 0,
    var title: String? = "title",
    var genres: String? = "genres",
    var overview: String? = "overview",
    var tagLine: String? = "tagLine",
    var releaseDate: String? = "release_date",
    var posterPath: String? = "path",
    var length: Int? = 0,
    var voteScore: Double? = 0.0,
    var status: String? = "status",
    var category: String? = "category",
    var isFavorite: Boolean = false,
)