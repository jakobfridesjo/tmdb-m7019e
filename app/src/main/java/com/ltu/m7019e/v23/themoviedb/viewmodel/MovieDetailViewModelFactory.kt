package com.ltu.m7019e.v23.themoviedb.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ltu.m7019e.v23.themoviedb.data.MovieRepository
import com.ltu.m7019e.v23.themoviedb.model.Movie

@Suppress("UNCHECKED_CAST")
class MovieDetailViewModelFactory(
    private val movieRepository: MovieRepository,
    private val application: Application,
    private val movie: Movie
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieDetailViewModel::class.java)) {
            return MovieDetailViewModel(movieRepository, application, movie) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}