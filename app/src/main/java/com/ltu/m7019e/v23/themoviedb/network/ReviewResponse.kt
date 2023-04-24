package com.ltu.m7019e.v23.themoviedb.network

import com.ltu.m7019e.v23.themoviedb.model.Review
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ReviewResponse(
    @Json(name = "id")
    val id: Long = 0L,

    @Json(name = "page")
    val page: Int = 0,

    @Json(name = "results")
    val results: List<Review> = emptyList(),

    @Json(name = "total_pages")
    val totalPages: Int = 0,

    @Json(name = "total_results")
    val totalResults: Int = 0
)