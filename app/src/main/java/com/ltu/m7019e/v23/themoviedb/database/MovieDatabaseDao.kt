package com.ltu.m7019e.v23.themoviedb.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ltu.m7019e.v23.themoviedb.model.Movie
import com.ltu.m7019e.v23.themoviedb.model.MovieAttributes

@Dao
interface MovieDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: Movie)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieAttribute(movieAttributes: MovieAttributes)

    @Query("UPDATE movie_attributes SET top_rated = 0")
    suspend fun resetTopRatedAttribute()

    @Query("UPDATE movie_attributes SET popular = 0")
    suspend fun resetPopularAttribute()

    @Query("SELECT * FROM movies WHERE id = :id")
    suspend fun getMovie(id: Long): Movie

    @Query("SELECT * FROM movies")
    suspend fun getAllMovies(): List<Movie>

    @Query("SELECT * FROM movie_attributes")
    suspend fun getAllMovieAttributes(): List<MovieAttributes>

    @Query("SELECT EXISTS(SELECT * FROM movies " +
            "INNER JOIN movie_attributes " +
            "ON movies.id = movie_attributes.id " +
            "WHERE movies.id = :id AND movie_attributes.favorite = 1)")
    suspend fun isFavorite(id: Long): Boolean

    @Query("SELECT * FROM movies " +
            "INNER JOIN movie_attributes " +
            "ON movies.id = movie_attributes.id " +
            "WHERE movie_attributes.top_rated = 1")
    suspend fun getTopRatedMovies(): List<Movie>

    @Query("SELECT * FROM movies " +
            "INNER JOIN movie_attributes " +
            "ON movies.id = movie_attributes.id " +
            "WHERE movie_attributes.popular = 1")
    suspend fun getPopularMovies(): List<Movie>

    @Query("SELECT * FROM movies " +
            "INNER JOIN movie_attributes " +
            "ON movies.id = movie_attributes.id " +
            "WHERE movie_attributes.favorite = 1")
    suspend fun getSavedMovies(): List<Movie>
}