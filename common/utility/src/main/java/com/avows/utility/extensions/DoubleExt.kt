package com.avows.utility.extensions

import com.avows.utility.consts.IndonesiaLocaleConst.COUNTRY
import com.avows.utility.consts.IndonesiaLocaleConst.LANGUAGE
import java.text.NumberFormat
import java.util.Locale

fun Double?.orZero(): Double = this ?: 0.0

fun Double.getFormattedNumber(): String {
    return "Rp " + getFormattedNumberNoCurrency()
}

fun indonesiaLocale() = Locale(LANGUAGE, COUNTRY)

fun Double.getFormattedNumberNoCurrency(): String {
    val numberFormat = NumberFormat.getCurrencyInstance(indonesiaLocale())
    numberFormat.maximumFractionDigits = 0
    val formatted = numberFormat.format(this)
    val subStr = formatted.substring(2, formatted.length)
    return subStr.replace(",", ".")
}