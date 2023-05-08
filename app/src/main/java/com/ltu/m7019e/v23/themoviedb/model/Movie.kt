package com.ltu.m7019e.v23.themoviedb.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "movies")
data class Movie(
    @PrimaryKey
    @Json(name = "id")
    var id: Long = 0L,

    @ColumnInfo(name = "title")
    @Json(name = "title")
    var title: String,

    @ColumnInfo(name = "poster_path")
    @Json(name = "poster_path")
    var posterPath: String,

    @ColumnInfo(name = "backdrop_path")
    @Json(name = "backdrop_path")
    var backdropPath: String,

    @ColumnInfo(name = "release_date")
    @Json(name = "release_date")
    var releaseDate: String,

    @ColumnInfo(name = "overview")
    @Json(name = "overview")
    var overview: String,

    @ColumnInfo(name = "genres")
    @Json(name = "genres")
    var genres: String? = "",

    @ColumnInfo(name = "runtime")
    @Json(name = "runtime")
    var runtime: Int = 0,

    @ColumnInfo(name = "imdb_id")
    @Json(name = "imdb_id")
    var imdbId: String = "",

    @ColumnInfo(name = "homepage")
    @Json(name = "homepage")
    var homepage: String = ""
) : Parcelable

@Parcelize
data class Genre(
        @Json(name = "id")
        val id: Long = 0L,
        @Json(name = "name")
        val name: String
) : Parcelable
