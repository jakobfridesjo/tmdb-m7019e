package com.ltu.m7019e.v23.themoviedb.network

import com.ltu.m7019e.v23.themoviedb.utils.Constants
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Add a httpclient logger for debugging purpose
 * object.
 */
fun getLoggerIntercepter(): HttpLoggingInterceptor {
    val logging = HttpLoggingInterceptor()
    logging.level = HttpLoggingInterceptor.Level.BODY
    return logging
}

interface TMDBApiService {
    @GET("popular")
    suspend fun getPopularMovies(
        @Query("api_key")
        apiKey: String = Constants.API_KEY
    ): MovieResponse

    @GET("top_rated")
    suspend fun getTopRatedMovies(
        @Query("api_key")
        apiKey: String = Constants.API_KEY
    ): MovieResponse

    @GET("{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id")
        movieId: Long,
        @Query("api_key")
        apiKey: String = Constants.API_KEY
    ): MovieDetailsResponse

    @GET("{movie_id}/reviews")
    suspend fun getMovieReviews(
        @Path("movie_id")
            movieId: Long,
        @Query("api_key")
            apiKey: String = Constants.API_KEY
    ): ReviewResponse

    @GET("{movie_id}/videos")
    suspend fun getMovieVideos(
        @Path("movie_id")
            movieId: Long,
        @Query("api_key")
            apiKey: String = Constants.API_KEY
    ): VideoResponse
}