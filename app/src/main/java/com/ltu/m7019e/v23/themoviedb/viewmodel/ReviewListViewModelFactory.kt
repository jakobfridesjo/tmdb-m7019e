package com.ltu.m7019e.v23.themoviedb.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class ReviewListViewModelFactory(private val movieId: Long, private val application: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ReviewListViewModel::class.java)) {
            return ReviewListViewModel(movieId, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}