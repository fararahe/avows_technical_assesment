package com.avows.domain.db.model

data class BillEntityDomain(
    val billId: Int?,
    val dateTime: String,
    val itemSold: Int,
    val totalPrice: Double
)
