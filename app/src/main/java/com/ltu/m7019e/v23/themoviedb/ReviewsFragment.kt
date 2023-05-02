package com.ltu.m7019e.v23.themoviedb

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.PagerSnapHelper
import com.ltu.m7019e.v23.themoviedb.adapter.ReviewListAdapter
import com.ltu.m7019e.v23.themoviedb.adapter.VideoListAdapter
import com.ltu.m7019e.v23.themoviedb.databinding.FragmentReviewsBinding
import com.ltu.m7019e.v23.themoviedb.model.Movie
import com.ltu.m7019e.v23.themoviedb.network.DataFetchStatus
import com.ltu.m7019e.v23.themoviedb.viewmodel.ReviewListViewModel
import com.ltu.m7019e.v23.themoviedb.viewmodel.ReviewListViewModelFactory
import com.ltu.m7019e.v23.themoviedb.viewmodel.VideoListViewModel
import com.ltu.m7019e.v23.themoviedb.viewmodel.VideoListViewModelFactory

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class ReviewsFragment : Fragment() {

    private var _binding: FragmentReviewsBinding? = null
    private val binding get() = _binding!!

    private lateinit var reviewListViewModel: ReviewListViewModel
    private lateinit var reviewListViewModelFactory: ReviewListViewModelFactory
    private lateinit var videoListViewModel: VideoListViewModel
    private lateinit var videoListViewModelFactory: VideoListViewModelFactory

    private lateinit var movie: Movie

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentReviewsBinding.inflate(inflater)

        movie = ReviewsFragmentArgs.fromBundle(requireArguments()).movie

        val appContainer = TheMovieDataBase.getAppContainer(requireContext())
        val movieRepository = appContainer.movieRepository
        val application = requireNotNull(this.activity).application

        // Setup recycler view for reviews
        reviewListViewModelFactory = ReviewListViewModelFactory(movieRepository, movie, application)
        reviewListViewModel = ViewModelProvider(this, reviewListViewModelFactory)[ReviewListViewModel::class.java]

        // Setup recycler view for videos
        videoListViewModelFactory = VideoListViewModelFactory(movieRepository, movie, application)
        videoListViewModel = ViewModelProvider(this, videoListViewModelFactory)[VideoListViewModel::class.java]

        // Snap reviews when swiping
        val reviewSnapHelper = PagerSnapHelper()
        reviewSnapHelper.attachToRecyclerView(binding.reviewList)

        // Snap videos when swiping
        val videoSnapHelper = PagerSnapHelper()
        videoSnapHelper.attachToRecyclerView(binding.videoList)

        val reviewListAdapter = ReviewListAdapter()
        binding.reviewList.adapter = reviewListAdapter
        reviewListViewModel.reviewList.observe(viewLifecycleOwner) { reviewList ->
            reviewList?.let {
                reviewListAdapter.submitList(reviewList)
            }
        }

        val videoListAdapter = VideoListAdapter()
        binding.videoList.adapter = videoListAdapter
        videoListViewModel.videoList.observe(viewLifecycleOwner) { videoList ->
            videoList?.let {
                videoListAdapter.submitList(videoList)
            }
        }

        reviewListViewModel.dataFetchStatus.observe(viewLifecycleOwner) { status ->
            status?.let {
                when (status) {
                    DataFetchStatus.LOADING -> {
                        binding.statusImageReviews.visibility = View.VISIBLE
                        binding.statusImageReviews.setImageResource(R.drawable.loading_animation)
                    }
                    DataFetchStatus.ERROR -> {
                        binding.statusImageReviews.visibility = View.VISIBLE
                        binding.statusImageReviews.setImageResource(R.drawable.ic_connection_error)
                    }
                    DataFetchStatus.DONE -> {
                        binding.statusImageReviews.visibility = View.GONE
                    }
                }
            }
        }

        videoListViewModel.dataFetchStatus.observe(viewLifecycleOwner) { status ->
            status?.let {
                when (status) {
                    DataFetchStatus.LOADING -> {
                        binding.statusImageVideos.visibility = View.VISIBLE
                        binding.statusImageVideos.setImageResource(R.drawable.loading_animation)
                    }
                    DataFetchStatus.ERROR -> {
                        binding.statusImageVideos.visibility = View.VISIBLE
                        binding.statusImageVideos.setImageResource(R.drawable.ic_connection_error)
                    }
                    DataFetchStatus.DONE -> {
                        binding.statusImageVideos.visibility = View.GONE
                    }
                }
            }
        }

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backToMovieDetails.setOnClickListener {
            findNavController().navigate(ReviewsFragmentDirections.actionReviewsFragmentToMovieDetailFragment(movie))
        }
    }
}