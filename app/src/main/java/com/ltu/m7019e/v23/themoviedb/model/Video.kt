package com.ltu.m7019e.v23.themoviedb.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class Video(
    @Json(name = "id")
    val id: String,

    @Json(name = "key")
    val key: String,

    @Json(name = "name")
    val name: String,

    @Json(name = "site")
    val site: String,

    @Json(name = "size")
    val size: Int,

    @Json(name = "type")
    val type: String
) : Parcelable