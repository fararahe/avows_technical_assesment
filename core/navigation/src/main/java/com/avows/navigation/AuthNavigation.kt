package com.avows.navigation

import android.app.Activity

interface AuthNavigation {

    fun navigateToLoginPage(
        activity: Activity,
        finishActivity: Boolean,
    )

}
