package com.avows.utility.extensions

import android.content.Context
import android.widget.Toast

fun Context.toastShortExt(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

