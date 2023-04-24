package com.ltu.m7019e.v23.themoviedb

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ltu.m7019e.v23.themoviedb.databinding.FragmentMovieDetailBinding
import com.ltu.m7019e.v23.themoviedb.model.Movie
import com.ltu.m7019e.v23.themoviedb.network.DataFetchStatus
import com.ltu.m7019e.v23.themoviedb.utils.Constants.IMDB_BASE_URL
import com.ltu.m7019e.v23.themoviedb.viewmodel.MovieDetailViewModel
import com.ltu.m7019e.v23.themoviedb.viewmodel.MovieDetailViewModelFactory

class MovieDetailFragment : Fragment() {

    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MovieDetailViewModel
    private lateinit var viewModelFactory: MovieDetailViewModelFactory

    private lateinit var movie: Movie

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movie = MovieDetailFragmentArgs.fromBundle(requireArguments()).movie

        viewModelFactory = MovieDetailViewModelFactory(movie.id, requireActivity().application)
        viewModel = ViewModelProvider(this, viewModelFactory)[MovieDetailViewModel::class.java]

        viewModel.movie.observe(viewLifecycleOwner) { movie ->
            binding.movie = movie
            this.movie.imdbId = movie.imdbId
            this.movie.homepage = movie.homepage
        }

        viewModel.dataFetchStatus.observe(viewLifecycleOwner) { status ->
            status?.let {
                when (status) {
                    DataFetchStatus.LOADING -> {
                        binding.statusImage.visibility = View.VISIBLE
                        binding.statusImage.setImageResource(R.drawable.loading_animation)
                    }

                    DataFetchStatus.ERROR -> {
                        binding.statusImage.visibility = View.VISIBLE
                        binding.statusImage.setImageResource(R.drawable.ic_connection_error)
                    }

                    DataFetchStatus.DONE -> {
                        binding.statusImage.visibility = View.GONE
                    }
                }
            }
        }

        binding.backToMovieList.setOnClickListener {
            findNavController().navigate(MovieDetailFragmentDirections.actionMovieDetailFragmentToMovieListFragment())
        }

        binding.toThirdFragment.setOnClickListener {
            findNavController().navigate(MovieDetailFragmentDirections.actionMovieDetailFragmentToThirdFragment(movie))
        }

        binding.movieDetailsImdb.setOnClickListener {
            val uri = Uri.parse("${IMDB_BASE_URL}${movie.imdbId}")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            context?.startActivity(intent)
        }

        binding.movieDetailsPage.setOnClickListener {
            val uri = Uri.parse(movie.homepage)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            context?.startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}