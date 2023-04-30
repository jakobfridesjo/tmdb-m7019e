package com.ltu.m7019e.v23.themoviedb.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "movie_attributes")
data class MovieAttributes (

    @PrimaryKey
    var id: Long = 0L,

    @ColumnInfo(name = "favorite")
    var favorite: String? = null,

    @ColumnInfo(name = "popular")
    var popular: String? = null,

    @ColumnInfo(name = "top_rated")
    var topRated: String? = null

): Parcelable