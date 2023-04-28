package com.ltu.m7019e.v23.themoviedb.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "movies")
data class Movie(
    @PrimaryKey
    @Json(name = "id")
    var id: Long = 0L,

    @ColumnInfo
    @Json(name = "title")
    var title: String,

    @ColumnInfo
    @Json(name = "poster_path")
    var posterPath: String,

    @ColumnInfo
    @Json(name = "backdrop_path")
    var backdropPath: String,

    @ColumnInfo
    @Json(name = "release_date")
    var releaseDate: String,

    @ColumnInfo
    @Json(name = "overview")
    var overview: String,

    @ColumnInfo
    @Json(name = "genres")
    var genres: String? = null,

    @ColumnInfo
    @Json(name = "imdb_id")
    var imdbId: String? = null,

    @ColumnInfo
    @Json(name = "homepage")
    var homepage: String? = null,

    @ColumnInfo(name = "favorite")
    var favorite: Boolean = false,

    @ColumnInfo(name = "most_popular")
    var mostPopular: Boolean = false,

    @ColumnInfo(name = "top_rated")
    var topRated: Boolean = false
) : Parcelable

@Parcelize
data class Genre(
        @Json(name = "id")
        val id: Long = 0L,
        @Json(name = "name")
        val name: String
) : Parcelable

class Converters {

        @TypeConverter
        fun fromGenresList(genres: List<Genre>?): String? {
                return genres?.joinToString(separator = ",") { it.name }
        }

        @TypeConverter
        fun toGenresList(genresString: String?): List<Genre>? {
                return genresString?.split(",")?.map { Genre(0, it) }
        }
}
