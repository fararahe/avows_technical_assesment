package com.avows.utility.extensions

import com.avows.utility.consts.PatternConst
import java.util.Locale
import java.util.regex.Pattern
import kotlin.jvm.internal.Intrinsics

fun String.validateEmail(): Boolean {
    Intrinsics.checkNotNullParameter(this, "<this>")
    val regex = PatternConst.EMAIL
    val pattern = Pattern.compile(regex, 2)
    val matcher = pattern.matcher(this as CharSequence)
    return matcher.matches()
}

fun String.validatePassword(): Boolean {
    val regex = Regex("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[\\W_]).{5,}")
    return regex.containsMatchIn(this)
}

fun String.toCapitalize(): String {
    return this.replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(
            Locale.getDefault()
        ) else it.toString()
    }
}