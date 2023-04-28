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
    suspend fun isFavorite(movie: Movie): Boolean

}

class DefaultMovieRepository(private val movieDatabaseDao: MovieDatabaseDao, private val movieApiService: TMDBApiService) : MovieRepository {
    override suspend fun getTopRatedMovies(): List<Movie> {
        val movies = movieApiService.getTopRatedMovies().results
        if (movies.isNotEmpty()) {
            movies.forEach { movie ->
                movie.topRated = true
                //movieDatabaseDao.update(movie)
            }
            return movies
        }
        return movieDatabaseDao.getTopRatedMovies()
    }

    override suspend fun getPopularMovies(): List<Movie> {
        val movies = movieApiService.getPopularMovies().results
        if (movies.isNotEmpty()) {
            movies.forEach { movie ->
                movie.mostPopular = true
                //movieDatabaseDao.update(movie)
            }
            return movies
        }
        return movieDatabaseDao.getPopularMovies()
    }

    override suspend fun getMovieDetails(movieId: Long): Movie {
        val movieResponse = movieApiService.getMovieDetails(movieId)
        val movie = Movie(
            movieResponse.id,
            movieResponse.title,
            movieResponse.posterPath,
            movieResponse.backdropPath,
            movieResponse.releaseDate,
            movieResponse.overview,
            movieResponse.genres.joinToString(", ") { it.name },
            movieResponse.imdbId,
            movieResponse.homepage,
            favorite = false,
            mostPopular = false,
            topRated = false,
        )
        movieDatabaseDao.update(movie)
        return movie
    }

    override suspend fun getMovieReviews(movieId: Long): List<Review> {
        return movieApiService.getMovieReviews(movieId).results
    }

    override suspend fun getMovieVideos(movieId: Long): List<Video> {
        return movieApiService.getMovieVideos(movieId).results
    }

    override suspend fun saveMovie(movie: Movie) {
        movie.favorite = true
        movieDatabaseDao.update(movie)
    }

    override suspend fun deleteMovie(movie: Movie) {
        movie.favorite = false
        movieDatabaseDao.update(movie)
    }

    override suspend fun getSavedMovies(): List<Movie> {
        return movieDatabaseDao.getSavedMovies()
    }

    override suspend fun isFavorite(movie: Movie): Boolean {
        return movieDatabaseDao.isFavorite(movie.id)
    }
}