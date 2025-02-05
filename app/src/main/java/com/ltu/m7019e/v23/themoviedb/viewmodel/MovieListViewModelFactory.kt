package com.ltu.m7019e.v23.themoviedb.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ltu.m7019e.v23.themoviedb.data.MovieRepository
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class MovieListViewModelFactory(
    private val movieRepository: MovieRepository,
    private val application: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MovieListViewModel::class.java)) {
            return MovieListViewModel(movieRepository, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}