package com.ltu.m7019e.v23.themoviedb.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ltu.m7019e.v23.themoviedb.data.MovieRepository
import com.ltu.m7019e.v23.themoviedb.model.Movie

@Suppress("UNCHECKED_CAST")
class VideoListViewModelFactory(
    private val movieRepository: MovieRepository,
    private val movie: Movie,
    private val application: Application
    ): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(VideoListViewModel::class.java)) {
            return VideoListViewModel(movieRepository, movie, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}