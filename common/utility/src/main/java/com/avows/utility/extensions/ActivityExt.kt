package com.avows.utility.extensions

import android.app.Activity
import android.content.Intent

fun Activity.startActivityExt(
    cls: Class<*>,
    finishCallingActivity: Boolean = false,
    finishAffinityCallingActivity: Boolean = false,
    block: (Intent.() -> Unit)? = null,
) {
    val intent = Intent(this, cls)
    block?.invoke(intent)
    startActivity(intent)
    if (finishCallingActivity) finish()
    if (finishAffinityCallingActivity) finishAffinity()
}