package com.ltu.m7019e.v23.themoviedb.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ltu.m7019e.v23.themoviedb.database.MovieDatabaseDao
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class MovieDetailViewModelFactory(private val movieId: Long, private val application: Application):
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MovieDetailViewModel::class.java)) {
            return MovieDetailViewModel(movieId, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}