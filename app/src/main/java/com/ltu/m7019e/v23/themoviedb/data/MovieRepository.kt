package com.ltu.m7019e.v23.themoviedb.data

import com.ltu.m7019e.v23.themoviedb.database.MovieDatabaseDao
import com.ltu.m7019e.v23.themoviedb.model.Movie
import com.ltu.m7019e.v23.themoviedb.model.Review
import com.ltu.m7019e.v23.themoviedb.model.Video
import com.ltu.m7019e.v23.themoviedb.network.TMDBApiService

interface MovieRepository {
    suspend fun getTopRatedMovies(): List<Movie>
    suspend fun getPopularMovies(): List<Movie>
    suspend fun getMovieReviews(movieId: Long): List<Review>
    suspend fun getMovieVideos(movieId: Long): List<Video>
    suspend fun getMovieDetails(movieId: Long): Movie
    suspend fun getSavedMovies(): List<Movie>
    suspend fun saveMovie(movie: Movie)
    suspend fun deleteMovie(movie: Movie)
}

class DefaultMovieRepository(private val movieDatabaseDao: MovieDatabaseDao, private val movieApiService: TMDBApiService) : MovieRepository {
    override suspend fun getTopRatedMovies(): List<Movie> {
        return movieApiService.getTopRatedMovies().results
    }

    override suspend fun getPopularMovies(): List<Movie> {
        return movieApiService.getPopularMovies().results
    }

    override suspend fun getMovieDetails(movieId: Long): Movie {
        val movieResponse = movieApiService.getMovieDetails(movieId)
        return Movie(
            movieResponse.id,
            movieResponse.title,
            movieResponse.posterPath,
            movieResponse.backdropPath,
            movieResponse.releaseDate,
            movieResponse.overview,
            movieResponse.genres.joinToString(", ") { it.name },
            movieResponse.imdbId,
            movieResponse.homepage
        )
    }

    override suspend fun getMovieReviews(movieId: Long): List<Review> {
        return movieApiService.getMovieReviews(movieId).results
    }

    override suspend fun getMovieVideos(movieId: Long): List<Video> {
        return movieApiService.getMovieVideos(movieId).results
    }

    override suspend fun getSavedMovies(): List<Movie> {
        return movieDatabaseDao.getAllMovies()
    }

    override suspend fun saveMovie(movie: Movie) {
        movieDatabaseDao.insert(movie)
    }

    override suspend fun deleteMovie(movie: Movie) {
        movieDatabaseDao.delete(movie)
    }
}