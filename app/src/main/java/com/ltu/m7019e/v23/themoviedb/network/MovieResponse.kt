package com.ltu.m7019e.v23.themoviedb.network

import com.ltu.m7019e.v23.themoviedb.model.Movie
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

@JsonClass(generateAdapter = true)
class MovieResponse {
    @Json(name = "page")
    var page: Int = 0;

    @Json(name = "results")
    var results: List<Movie> = emptyList()

    @Json(name = "total_pages")
    var total_pages: Int = 0

    @Json(name = "total_results")
    var total_results: Int = 0
}