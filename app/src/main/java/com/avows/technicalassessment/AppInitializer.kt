package com.avows.technicalassessment

import timber.log.Timber
import javax.inject.Inject

class AppInitializer @Inject constructor() {

    private fun setupTimber() {
        Timber.plant(Timber.DebugTree())
    }

    fun onCreate() {
        setupTimber()
    }
}