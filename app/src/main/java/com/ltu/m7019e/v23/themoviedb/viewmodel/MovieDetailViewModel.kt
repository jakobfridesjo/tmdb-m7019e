package com.ltu.m7019e.v23.themoviedb.viewmodel

import android.app.Application
import com.ltu.m7019e.v23.themoviedb.database.MovieDatabaseDao
import com.ltu.m7019e.v23.themoviedb.model.Movie
import com.ltu.m7019e.v23.themoviedb.network.DataFetchStatus
import kotlinx.coroutines.launch
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ltu.m7019e.v23.themoviedb.network.MovieDetailsResponse
import com.ltu.m7019e.v23.themoviedb.network.TMDBApi
import kotlinx.coroutines.launch

class MovieDetailViewModel(
    private val movieDatabaseDao: MovieDatabaseDao,
    application: Application,
    private val movieArg: Movie
) : AndroidViewModel(application){

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

    private fun getMovieDetails() {
        viewModelScope.launch {
            try {
                val movieDetailsResponse: MovieDetailsResponse =
                    TMDBApi.movieDetailsRetrofitService.getMovieDetails(movieArg.id)
                _movie.value = Movie(
                    movieArg.id,
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

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean>
        get() {
            return _isFavorite
        }

    init {
        setIsFavorite(movieArg)
    }

    private fun setIsFavorite(movie: Movie) {
        viewModelScope.launch {
            _isFavorite.value = movieDatabaseDao.isFavorite(movie.id)
        }
    }

    fun onSaveMovieButtonClicked(movie: Movie) {
        viewModelScope.launch {
            movieDatabaseDao.insert(movie)
            setIsFavorite(movie)
        }
    }

    fun onRemoveMovieButtonClicked(movie: Movie) {
        viewModelScope.launch {
            movieDatabaseDao.delete(movie)
            setIsFavorite(movie)
        }
    }
}