package com.ltu.m7019e.v23.themoviedb.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ltu.m7019e.v23.themoviedb.model.Movie
import com.ltu.m7019e.v23.themoviedb.network.DataFetchStatus
import com.ltu.m7019e.v23.themoviedb.network.MovieDetailsResponse
import com.ltu.m7019e.v23.themoviedb.network.MovieResponse
import com.ltu.m7019e.v23.themoviedb.network.TMDBApi
import kotlinx.coroutines.launch

class MovieDetailViewModel(private val movieId: Long, application: Application) : AndroidViewModel(application) {

    private val _dataFetchStatus = MutableLiveData<DataFetchStatus>()
    val dataFetchStatus: LiveData<DataFetchStatus>
        get() {
            return _dataFetchStatus
        }

    private val _movie = MutableLiveData<Movie>()
    val movie: LiveData<Movie>
        get() {
            return _movie
        }

    init {
        getMovieDetails()
        _dataFetchStatus.value = DataFetchStatus.LOADING
    }

    fun getMovieDetails() {
        viewModelScope.launch {
            try {
                val movieDetailsResponse: MovieDetailsResponse =
                    TMDBApi.movieDetailsRetrofitService.getMovieDetails(movieId)
                _movie.value = Movie(
                    movieId,
                    movieDetailsResponse.title,
                    movieDetailsResponse.posterPath,
                    movieDetailsResponse.backdropPath,
                    movieDetailsResponse.releaseDate,
                    movieDetailsResponse.overview,
                    movieDetailsResponse.genres.joinToString(", ") { it.name },
                    movieDetailsResponse.imdbId,
                    movieDetailsResponse.homepage
                )
                _dataFetchStatus.value = DataFetchStatus.DONE
            } catch (e: Exception) {
                println("---------------------------------------ERROR---------------------------------------")
                println(e.message)
                _dataFetchStatus.value = DataFetchStatus.ERROR
            }
        }
    }
}