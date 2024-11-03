package com.avows.domain.db.model

data class ListProductEntityDomain(
    val listProductId: Int?,
    val billId: Long,
    val productId: Int,
    val title: String,
    val category: String,
    val description: String,
    val image: String,
    val price: Double,
    val qty: Int,
    val totalPrice: Double
)