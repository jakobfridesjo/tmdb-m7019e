package com.ltu.m7019e.v23.themoviedb.data

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

    /**
     * Gets the top rated movies, foremost from online, otherwise from the local database
     */
    override suspend fun getTopRatedMovies(): List<Movie> {
        try {
            val movies = movieApiService.getTopRatedMovies().results
            movieDatabaseDao.resetTopRatedAttribute()
            movies.forEach { movie ->
                movieDatabaseDao.insertMovieAttribute(MovieAttributes(movie.id,null,null,null))
                movieDatabaseDao.setTopRated(movie.id, "1")
                movieDatabaseDao.insertMovie(movie)
            }
            return movies
        } catch (exception: Exception) {
            Timber.tag("MOVIE_REPOSITORY_TOP_RATED").d("NETWORK UNREACHABLE, USING LOCAL DATA")
        }
        return movieDatabaseDao.getTopRatedMovies()
    }

    /**
     * Gets the popular movies, foremost from online, otherwise from the local database
     */
    override suspend fun getPopularMovies(): List<Movie> {
        try {
            val movies = movieApiService.getPopularMovies().results
            movieDatabaseDao.resetPopularAttribute()
            movies.forEach { movie ->
                movieDatabaseDao.insertMovieAttribute(MovieAttributes(movie.id))
                movieDatabaseDao.setPopular(movie.id, "1")
                movieDatabaseDao.insertMovie(movie)
            }
            return movies
        } catch (exception: Exception) {
            Timber.tag("MOVIE_REPOSITORY_POPULAR").d("NETWORK UNREACHABLE, USING LOCAL DATA")
        }
        return movieDatabaseDao.getPopularMovies()
    }

    /**
     * Gets details for a movie, foremost from online, and else wise from the local database
     */
    override suspend fun getMovieDetails(movie: Movie): Movie {
        try {
            val movieResponse = movieApiService.getMovieDetails(movie.id)
            val mMovie = Movie(
                movieResponse.id,
                movieResponse.title,
                movieResponse.posterPath,
                movieResponse.backdropPath,
                movieResponse.releaseDate,
                movieResponse.overview,
                movieResponse.genres.joinToString(", ") { it.name },
                movieResponse.runtime,
                movieResponse.imdbId,
                movieResponse.homepage
            )
            movieDatabaseDao.insertMovie(mMovie)
            return mMovie
        } catch (exception: Exception) {
            Timber.tag("MOVIE_REPOSITORY_MOVIE_DETAILS").d("NETWORK UNREACHABLE, USING LOCAL DATA")
        }
        movieDatabaseDao.insertMovie(movie)
        return movieDatabaseDao.getMovie(movie.id)
    }

    /**
     * Gets reviews for a movie
     */
    override suspend fun getMovieReviews(movie: Movie): List<Review> {
        return movieApiService.getMovieReviews(movie.id).results
    }

    /**
     * Gets videos for a movie
     */
    override suspend fun getMovieVideos(movie: Movie): List<Video> {
        return movieApiService.getMovieVideos(movie.id).results
    }

    /**
     * Favorites a movie in the local database
     */
    override suspend fun saveMovie(movie: Movie) {
        movieDatabaseDao.setFavorite(movie.id, "1")
    }

    /**
     * Unfavorites a movie in the local database
     */
    override suspend fun deleteMovie(movie: Movie) {
        movieDatabaseDao.setFavorite(movie.id, "0")
    }

    /**
     * Gets all movies in the local database
     */
    override suspend fun getSavedMovies(): List<Movie> {
        return movieDatabaseDao.getSavedMovies()
    }

    /**
     * Checks if a movie is marked as a favorite
     */
    override suspend fun isFavoriteMovie(movie: Movie): Boolean {
        return movieDatabaseDao.isFavorite(movie.id)
    }
}