package com.ltu.m7019e.v23.themoviedb.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ltu.m7019e.v23.themoviedb.data.MovieRepository
import com.ltu.m7019e.v23.themoviedb.model.Movie
import com.ltu.m7019e.v23.themoviedb.network.DataFetchStatus
import kotlinx.coroutines.launch

class MovieDetailViewModel(
    private val movieRepository: MovieRepository,
    application: Application,
    private val movieArg: Movie
) : AndroidViewModel(application) {

    private val _dataFetchStatus = MutableLiveData<DataFetchStatus>()
    val dataFetchStatus: LiveData<DataFetchStatus>
        get() = _dataFetchStatus

    private val _movie = MutableLiveData<Movie>()
    val movie: LiveData<Movie>
        get() = _movie

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean>
        get() = _isFavorite

    init {
        getMovieDetails()
        _dataFetchStatus.value = DataFetchStatus.LOADING
        setIsFavorite(movieArg)
    }

    private fun getMovieDetails() {
        viewModelScope.launch {
            try {
                _movie.value = movieRepository.getMovieDetails(movieArg.id)
                _dataFetchStatus.value = DataFetchStatus.DONE
            } catch (e: Exception) {
                println(e.message)
                _dataFetchStatus.value = DataFetchStatus.ERROR
            }
        }
    }

    private fun setIsFavorite(movie: Movie) {
        viewModelScope.launch {
            _isFavorite.value = movieRepository.isFavorite(movie)
        }
    }

    fun onSaveMovieButtonClicked(movie: Movie) {
        viewModelScope.launch {
            movieRepository.saveMovie(movie)
            setIsFavorite(movie)
        }
    }

    fun onRemoveMovieButtonClicked(movie: Movie) {
        viewModelScope.launch {
            movieRepository.deleteMovie(movie)
            setIsFavorite(movie)
        }
    }
}