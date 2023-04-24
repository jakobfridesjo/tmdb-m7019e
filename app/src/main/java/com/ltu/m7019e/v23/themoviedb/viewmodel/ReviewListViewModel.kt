package com.ltu.m7019e.v23.themoviedb.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ltu.m7019e.v23.themoviedb.model.Review
import com.ltu.m7019e.v23.themoviedb.network.DataFetchStatus
import com.ltu.m7019e.v23.themoviedb.network.ReviewResponse
import com.ltu.m7019e.v23.themoviedb.network.TMDBApi
import kotlinx.coroutines.launch

class ReviewListViewModel(movieId: Long, application: Application) : AndroidViewModel(application) {

    private val movieId = movieId
    private val _dataFetchStatus = MutableLiveData<DataFetchStatus>()
    val dataFetchStatus: LiveData<DataFetchStatus>
        get() {
            return _dataFetchStatus
        }

    private val _reviewList = MutableLiveData<List<Review>>()
    val reviewList: LiveData<List<Review>>
        get() {
            return _reviewList
        }


    init {
        getMovieReviews()
        _dataFetchStatus.value = DataFetchStatus.LOADING
    }

    fun onReviewListItemClicked(review: Review) {
        //_navigateToMovieDetail.value = movie
    }

    fun getMovieReviews() {
        viewModelScope.launch {
            try {
                val reviewResponse: ReviewResponse =
                    TMDBApi.reviewListRetrofitService.getMovieReviews(movieId)

                _reviewList.value = reviewResponse.results
                println("TESTING HERE")
                println(_reviewList.value)
                println("DONE TESTING")
                _dataFetchStatus.value = DataFetchStatus.DONE
            } catch (e: Exception) {
                println(e.message)
                _dataFetchStatus.value = DataFetchStatus.ERROR
            }
        }
    }
}