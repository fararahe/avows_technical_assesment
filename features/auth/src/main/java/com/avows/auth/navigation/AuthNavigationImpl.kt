package com.avows.auth.navigation

import android.app.Activity
import android.content.Intent
import com.avows.auth.ui.LoginActivity
import com.avows.navigation.AuthNavigation
import com.avows.utility.extensions.startActivityExt
import javax.inject.Inject

class AuthNavigationImpl @Inject constructor(): AuthNavigation {

    override fun navigateToLoginPage(activity: Activity, finishActivity: Boolean) {
        activity.startActivityExt(
            LoginActivity::class.java,
            finishCallingActivity = finishActivity,
        ) {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        }
    }
}