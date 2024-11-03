package com.avows.configs.exception.provider

import android.app.Activity
import androidx.lifecycle.Lifecycle

interface ErrorHandlerProvider {
    fun generalError(activity: Activity, lifecycle: Lifecycle, throwable: Throwable)
    fun actionLogout(activity: Activity, lifecycle: Lifecycle)
}