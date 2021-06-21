package com.sh.s1.made.mymovies.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieEntity (
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "movieId")
    var id: Int = 0,
    @ColumnInfo(name = "title")
    var title: String? = "title",
    @ColumnInfo(name = "genres")
    var genres: String? = "genres",
    @ColumnInfo(name = "overview")
    var overview: String? = "overview",
    @ColumnInfo(name = "tagline")
    var tagLine: String? = "tagline",
    @ColumnInfo(name = "release_date")
    var releaseDate: String? = "release_date",
    @ColumnInfo(name = "path")
    var posterPath: String? = "path",
    @ColumnInfo(name = "length")
    var length: Int? = 0,
    @ColumnInfo(name = "voteScore")
    var voteScore: Double? = 0.0,
    @ColumnInfo(name = "status")
    var status: String? = "status",
    @ColumnInfo(name = "category")
    var category: String? = "category",
    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean = false,
)