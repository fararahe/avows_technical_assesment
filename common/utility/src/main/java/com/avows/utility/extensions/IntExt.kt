package com.avows.utility.extensions

import java.util.Locale

fun Int?.orZero():Int = this ?: 0

fun Int.toStringFormat() =  String.format(Locale.getDefault(), "%d", this)