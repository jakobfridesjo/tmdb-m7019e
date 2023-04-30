package com.ltu.m7019e.v23.themoviedb.network

import com.ltu.m7019e.v23.themoviedb.model.Video
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class VideoResponse {
    @Json(name = "id")
    var id: Long = 0L

    @Json(name = "results")
    var results: List<Video> = emptyList()
}