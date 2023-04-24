package com.ltu.m7019e.v23.themoviedb.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ltu.m7019e.v23.themoviedb.model.Video
import com.ltu.m7019e.v23.themoviedb.network.DataFetchStatus
import com.ltu.m7019e.v23.themoviedb.network.TMDBApi
import com.ltu.m7019e.v23.themoviedb.network.VideoResponse
import kotlinx.coroutines.launch

class VideoListViewModel(movieId: Long, application: Application) : AndroidViewModel(application) {

    val movieId = movieId

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

    fun getMovieVideos() {
        viewModelScope.launch {
            try {
                val videoResponse: VideoResponse =
                    TMDBApi.videoListRetrofitService.getMovieVideos(movieId)
                _videoList.value = videoResponse.results
                _dataFetchStatus.value = DataFetchStatus.DONE
            } catch (e: Exception) {
                println(e.message)
                _dataFetchStatus.value = DataFetchStatus.ERROR
            }
        }
    }
}