package com.ltu.m7019e.v23.themoviedb.data

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.ltu.m7019e.v23.themoviedb.database.MovieDatabase
import com.ltu.m7019e.v23.themoviedb.database.MovieDatabaseDao
import com.ltu.m7019e.v23.themoviedb.network.TMDBApiService
import com.ltu.m7019e.v23.themoviedb.network.getLoggerIntercepter
import com.ltu.m7019e.v23.themoviedb.utils.Constants
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

interface AppContainer {
    val movieDatabaseDao: MovieDatabaseDao
    val movieRepository: MovieRepository
}

class DefaultAppContainer(context: Context) : AppContainer {

    private val movieDatabase = MovieDatabase.getInstance(context)
    override val movieDatabaseDao: MovieDatabaseDao
        get() = movieDatabase.movieDatabaseDao

    private fun isOnline(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
    }

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofit = Retrofit.Builder()
        .client(
            OkHttpClient.Builder()
                .addInterceptor(getLoggerIntercepter())
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build()
        )
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(Constants.MOVIE_LIST_BASE_URL)
        .build()

    private val retrofitService: TMDBApiService by lazy {
        retrofit.create(TMDBApiService::class.java)
    }

    override val movieRepository: MovieRepository by lazy {
        DefaultMovieRepository(
            movieDatabaseDao,
            retrofitService
        )
    }
}