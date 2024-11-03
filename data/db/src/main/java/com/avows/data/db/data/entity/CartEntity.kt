package com.avows.data.db.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "table_cart"
)
data class CartEntity(
    @PrimaryKey
    @ColumnInfo(name = "product_id") val productId: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "category") val category: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "image") val image: String,
    @ColumnInfo(name = "price") val price: Double,
    @ColumnInfo(name = "qty") val qty: Int,
)