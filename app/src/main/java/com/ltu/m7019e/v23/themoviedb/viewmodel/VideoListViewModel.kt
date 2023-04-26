package com.ltu.m7019e.v23.themoviedb.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ltu.m7019e.v23.themoviedb.data.MovieRepository
import com.ltu.m7019e.v23.themoviedb.model.Video
import com.ltu.m7019e.v23.themoviedb.network.DataFetchStatus
import kotlinx.coroutines.launch

class VideoListViewModel(
    private val movieRepository: MovieRepository,
    private val movieId: Long,
    application: Application
) : AndroidViewModel(application) {

    private val _dataFetchStatus = MutableLiveData<DataFetchStatus>()
    val dataFetchStatus: LiveData<DataFetchStatus>
        get() {
            return _dataFetchStatus
        }

    private val _videoList = MutableLiveData<List<Video>>()
    val videoList: LiveData<List<Video>>
        get() {
            return _videoList
        }

    init {
        getMovieVideos()
        _dataFetchStatus.value = DataFetchStatus.LOADING
    }

    fun onVideoListItemClicked(video: Video) {
        //_navigateToMovieDetail.value = movie
    }

    private fun getMovieVideos() {
        viewModelScope.launch {
            try {
                _videoList.value = movieRepository.getMovieVideos(movieId)
                _dataFetchStatus.value = DataFetchStatus.DONE
            } catch (e: Exception) {
                println(e.message)
                _dataFetchStatus.value = DataFetchStatus.ERROR
            }
        }
    }
}