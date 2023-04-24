package com.ltu.m7019e.v23.themoviedb.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class MovieDetailsResponse {
    @Json(name = "id")
    var id: Int = 0

    @Json(name = "title")
    var title: String = ""

    @Json(name = "overview")
    var overview: String = ""

    @Json(name = "release_date")
    var releaseDate: String = ""

    @Json(name = "poster_path")
    var posterPath: String = ""

    @Json(name = "backdrop_path")
    var backdropPath: String = ""

    @Json(name = "genres")
    var genres: List<Genre> = emptyList()

    @Json(name = "runtime")
    var runtime: Int = 0

    @Json(name = "imdb_id")
    var imdbId: String = ""

    @Json(name = "homepage")
    var homepage: String = ""
}

@JsonClass(generateAdapter = true)
class Genre {
    @Json(name = "id")
    var id: Int = 0

    @Json(name = "name")
    var name: String = ""
}
