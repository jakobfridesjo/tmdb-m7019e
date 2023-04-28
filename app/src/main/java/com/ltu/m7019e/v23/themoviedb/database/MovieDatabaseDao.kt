package com.ltu.m7019e.v23.themoviedb.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.ltu.m7019e.v23.themoviedb.model.Movie

@Dao
interface MovieDatabaseDao {

    @Insert
    suspend fun insert(movie: Movie)

    @Delete
    suspend fun delete(movie: Movie)

    @Update
    suspend fun update(movie: Movie)

    @Query("SELECT * FROM movies WHERE id = :id")
    suspend fun getMovie(id: Long): Movie

    @Query("SELECT * FROM movies ORDER BY id ASC")
    suspend fun getAllMovies(): List<Movie>

    @Query("SELECT EXISTS(SELECT * from movies WHERE id = :id AND favorite = 1)")
    suspend fun isFavorite(id: Long): Boolean

    @Query("SELECT * FROM movies WHERE most_popular = 1")
    suspend fun getTopRatedMovies(): List<Movie>

    @Query("SELECT * FROM movies WHERE top_rated = 1")
    suspend fun getPopularMovies(): List<Movie>

    @Query("SELECT * FROM movies WHERE favorite = 1")
    suspend fun getSavedMovies(): List<Movie>
}