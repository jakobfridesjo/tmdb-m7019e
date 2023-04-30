package com.ltu.m7019e.v23.themoviedb

import android.app.Application
import android.content.Context
import com.ltu.m7019e.v23.themoviedb.data.AppContainer
import com.ltu.m7019e.v23.themoviedb.data.DefaultAppContainer
import timber.log.Timber

class TheMovieDataBase : Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this)
        Timber.plant(Timber.DebugTree())
    }

    companion object {
        fun getAppContainer(context: Context): AppContainer {
            return (context.applicationContext as TheMovieDataBase).container
        }
    }
}