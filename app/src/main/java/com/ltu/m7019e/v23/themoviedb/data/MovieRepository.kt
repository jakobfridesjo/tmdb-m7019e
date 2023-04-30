package com.ltu.m7019e.v23.themoviedb.data

import android.util.Log
import com.ltu.m7019e.v23.themoviedb.database.MovieDatabaseDao
import com.ltu.m7019e.v23.themoviedb.model.Movie
import com.ltu.m7019e.v23.themoviedb.model.MovieAttributes
import com.ltu.m7019e.v23.themoviedb.model.Review
import com.ltu.m7019e.v23.themoviedb.model.Video
import com.ltu.m7019e.v23.themoviedb.network.TMDBApiService
import timber.log.Timber

interface MovieRepository {
    suspend fun getTopRatedMovies(): List<Movie>
    suspend fun getPopularMovies(): List<Movie>
    suspend fun getMovieReviews(movie: Movie): List<Review>
    suspend fun getMovieVideos(movie: Movie): List<Video>
    suspend fun getMovieDetails(movie: Movie): Movie
    suspend fun getSavedMovies(): List<Movie>
    suspend fun saveMovie(movie: Movie)
    suspend fun deleteMovie(movie: Movie)
    suspend fun isFavoriteMovie(movie: Movie): Boolean

}

class DefaultMovieRepository(private val movieDatabaseDao: MovieDatabaseDao, private val movieApiService: TMDBApiService) : MovieRepository {
    override suspend fun getTopRatedMovies(): List<Movie> {
        try {
            val movies = movieApiService.getTopRatedMovies().results
            movieDatabaseDao.resetTopRatedAttribute()
            movies.forEach { movie ->
                movieDatabaseDao.insertMovieAttribute(MovieAttributes(movie.id,null,null,"1"))
                movieDatabaseDao.insertMovie(movie)
            }
            return movies
        } catch (exception: Exception) {
            Timber.tag("MOVIE_REPOSITORY_TOP_RATED").d("NETWORK UNREACHABLE, USING LOCAL DATA")
        }
        return movieDatabaseDao.getTopRatedMovies()
    }

    override suspend fun getPopularMovies(): List<Movie> {
        try {
            val movies = movieApiService.getPopularMovies().results
            movieDatabaseDao.resetPopularAttribute()
            movies.forEach { movie ->
                movieDatabaseDao.insertMovieAttribute(MovieAttributes(movie.id,null,"1",null))
                movieDatabaseDao.insertMovie(movie)
            }
            return movies
        } catch (exception: Exception) {
            Timber.tag("MOVIE_REPOSITORY_POPULAR").d("NETWORK UNREACHABLE, USING LOCAL DATA")
        }
        println("LOADING LOCAL POPULAR MOVIES")
        return movieDatabaseDao.getPopularMovies()
    }

    override suspend fun getMovieDetails(movie: Movie): Movie {
        try {
            val movieResponse = movieApiService.getMovieDetails(movie.id)
            val _movie = Movie(
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
            movieDatabaseDao.insertMovie(_movie)
            return _movie
        } catch (exception: Exception) {
            Timber.tag("MOVIE_REPOSITORY_MOVIE_DETAILS").d("NETWORK UNREACHABLE, USING LOCAL DATA")
        }
        return movieDatabaseDao.getMovie(movie.id)
    }

    override suspend fun getMovieReviews(movie: Movie): List<Review> {
        return movieApiService.getMovieReviews(movie.id).results
    }

    override suspend fun getMovieVideos(movie: Movie): List<Video> {
        return movieApiService.getMovieVideos(movie.id).results
    }

    override suspend fun saveMovie(movie: Movie) {
        movieDatabaseDao.insertMovieAttribute(MovieAttributes(movie.id, "1",null,null))
    }

    override suspend fun deleteMovie(movie: Movie) {
        movieDatabaseDao.insertMovieAttribute(MovieAttributes(movie.id, "0",null,null))
    }

    override suspend fun getSavedMovies(): List<Movie> {
        return movieDatabaseDao.getSavedMovies()
    }

    override suspend fun isFavoriteMovie(movie: Movie): Boolean {
        return movieDatabaseDao.isFavorite(movie.id)
    }
}