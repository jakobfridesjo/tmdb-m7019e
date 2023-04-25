package com.ltu.m7019e.v23.themoviedb

import android.app.Application
import timber.log.Timber

class TheMovieDataBase : Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }
}