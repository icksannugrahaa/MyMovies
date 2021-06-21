package com.sh.s1.made.mymovies.core.utils

import com.sh.s1.made.mymovies.core.data.source.local.entity.MovieEntity
import com.sh.s1.made.mymovies.core.data.source.remote.response.ResultMovieDetail
import com.sh.s1.made.mymovies.core.domain.model.Movie

object DataMapper {
    fun mapResponsesToEntities(
        input: List<ResultMovieDetail>,
        category: String
    ): List<MovieEntity> {
        val tourismList = ArrayList<MovieEntity>()
        var genreJoin = ""
        input.map {
            it.genres?.forEach { gen ->
                genreJoin += "${gen?.name}, "
            }

            val tourism = MovieEntity(
                id = it.id ?: 0,
                title = it.title,
                genres = genreJoin,
                overview = it.overview,
                tagLine = it.tagline ?: "null",
                releaseDate = it.releaseDate,
                posterPath = it.posterPath,
                length = it.runtime,
                voteScore = it.voteAverage,
                status = it.status,
                category = category,
                isFavorite = false
            )

            tourismList.add(tourism)
        }
        return tourismList
    }

    fun mapResponseToEntity(
        input: ResultMovieDetail,
        category: String,
        state: Boolean
    ): MovieEntity {
        var genreJoin = ""
        input.genres?.forEach { gen ->
            genreJoin += "${gen?.name}, "
        }
        return MovieEntity(
            id = input.id ?: 0,
            title = input.title,
            genres = genreJoin,
            overview = input.overview,
            tagLine = input.tagline ?: "null",
            releaseDate = input.releaseDate,
            posterPath = input.posterPath,
            length = input.runtime,
            voteScore = input.voteAverage,
            status = input.status,
            category = category,
            isFavorite = state
        )
    }

    fun mapEntitiesToDomain(input: List<MovieEntity>): List<Movie> =
        input.map {
            Movie(
                id = it.id,
                title = it.title,
                genres = it.genres,
                overview = it.overview,
                tagLine = it.tagLine,
                releaseDate = it.releaseDate,
                posterPath = it.posterPath,
                length = it.length,
                voteScore = it.voteScore,
                status = it.status,
                category = it.category,
                isFavorite = it.isFavorite
            )
        }

    fun mapEntityToDomain(input: MovieEntity): Movie =
        Movie(
            id = input.id,
            title = input.title,
            genres = input.genres,
            overview = input.overview,
            tagLine = input.tagLine,
            releaseDate = input.releaseDate,
            posterPath = input.posterPath,
            length = input.length,
            voteScore = input.voteScore,
            status = input.status,
            category = input.category,
            isFavorite = input.isFavorite
        )

    fun mapDomainToEntity(input: Movie) = MovieEntity(
        id = input.id,
        title = input.title,
        genres = input.genres,
        overview = input.overview,
        tagLine = input.tagLine,
        releaseDate = input.releaseDate,
        posterPath = input.posterPath,
        length = input.length,
        voteScore = input.voteScore,
        status = input.status,
        category = input.category,
        isFavorite = input.isFavorite
    )

}