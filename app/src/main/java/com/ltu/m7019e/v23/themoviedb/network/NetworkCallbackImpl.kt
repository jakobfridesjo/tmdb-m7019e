package com.ltu.m7019e.v23.themoviedb.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import com.ltu.m7019e.v23.themoviedb.data.MovieRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NetworkCallbackImpl(val movieRepository: MovieRepository, private val context: Context) : ConnectivityManager.NetworkCallback() {

    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    init {
        val networkRequest = NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .build()
        connectivityManager.registerNetworkCallback(networkRequest, this)
    }

    /**
     * Unregisters the network listener
     */
    fun unregister() {
        connectivityManager.unregisterNetworkCallback(this)
    }

    /**
     * Updates repository when network is available
     */
    override fun onAvailable(network: Network) {
        super.onAvailable(network)
        if (isConnected()) {
            CoroutineScope(Dispatchers.IO).launch {
                movieRepository.getTopRatedMovies()
            }
            CoroutineScope(Dispatchers.IO).launch {
                movieRepository.getPopularMovies()
            }
        }
    }

    /**
     * Check if we have an internet connection
     */
    private fun isConnected(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
        return networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) ?: false
    }
}