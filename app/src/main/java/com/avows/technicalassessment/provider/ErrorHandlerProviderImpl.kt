package com.avows.technicalassessment.provider

import android.app.Activity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import com.avows.configs.exception.ServerErrorException
import com.avows.configs.exception.provider.ErrorHandlerProvider
import com.avows.navigation.AuthNavigation
import com.avows.pref_data_store.pref.PrefDataStoreManager
import com.avows.technicalassessment.R
import com.avows.utility.extensions.toastShortExt
import com.squareup.moshi.JsonDataException
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class ErrorHandlerProviderImpl @Inject constructor(
    private val prefDataStoreManager: PrefDataStoreManager,
    private val authNavigation: AuthNavigation,
) : ErrorHandlerProvider {

    private var toastJob: Job? = null
    private var logoutJob: Job? = null

    override fun generalError(
        activity: Activity,
        lifecycle: Lifecycle,
        throwable: Throwable,
    ) = with(activity) {
        // Log the error for debugging purposes
        Timber.e("ErrorHandling", "Error occurred", throwable)

        val message = when (throwable) {
            is UnknownHostException, is SocketTimeoutException ->
                getString(R.string.label_no_internet_error_message)

            is ServerErrorException ->
                getString(R.string.label_server_error_message)

            is JsonDataException ->
                getString(R.string.label_parsing_error_message)

            else -> {
                getString(R.string.label_server_error_message)
            }
        }

        safeErrorMessage(
            this,
            lifecycle,
            message
        )
    }

    override fun actionLogout(activity: Activity, lifecycle: Lifecycle) {
        with(activity) {
            lifecycle.coroutineScope.launch {
                logoutJob?.cancel()
                logoutJob =
                    launch {
                        delay(JOB_DELAY)
                        toastShortExt(getString(R.string.label_session_over_message))
                        prefDataStoreManager.clearDataStore()
                        authNavigation.navigateToLoginPage(
                            activity = activity,
                            finishActivity = true
                        )
                    }
            }
        }
    }

    private fun safeErrorMessage(
        activity: Activity,
        lifecycle: Lifecycle,
        message: String,
    ) {
        lifecycle.coroutineScope.launch {
            toastJob?.cancel()
            toastJob =
                launch {
                    delay(JOB_DELAY)
                    activity.toastShortExt(message)
                }
        }
    }

    companion object {
        private const val JOB_DELAY = 1000L
    }
}