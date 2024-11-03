package com.avows.domain.home.model.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductDomain(
    val id: Int,
    val title: String,
    val price: Double,
    val category: String,
    val description: String,
    val image: String
): Parcelable
