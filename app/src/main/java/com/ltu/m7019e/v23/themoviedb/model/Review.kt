package com.ltu.m7019e.v23.themoviedb.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class Review(
    @Json(name = "id")
    var id: String = "",

    @Json(name = "author")
    var author: String,

    @Json(name = "content")
    var content: String,

    @Json(name = "url")
    var url: String,

    @Json(name = "created_at")
    var createdAt: String,

    @Json(name = "updated_at")
    var updatedAt: String,

    @Json(name = "author_details")
    var authorDetails: AuthorDetails? = null
) : Parcelable

@Parcelize
data class AuthorDetails(
    @Json(name = "name")
    var name: String? = null,

    @Json(name = "username")
    var username: String? = null,

    @Json(name = "avatar_path")
    var avatarPath: String? = null,

    @Json(name = "rating")
    var rating: Float? = null
) : Parcelable