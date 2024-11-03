package com.avows.data.db.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "table_list_product",
    foreignKeys = [
        ForeignKey(
            entity = BillEntity::class,
            parentColumns = arrayOf("bill_id"),
            childColumns = arrayOf("bill_id"),
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["bill_id"])
    ]
)
data class ListProductEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "product_list_id") val productListId: Int = 0,
    @ColumnInfo(name = "bill_id") val billId: Long,
    @ColumnInfo(name = "product_id") val productId: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "category") val category: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "image") val image: String,
    @ColumnInfo(name = "price") val price: Double,
    @ColumnInfo(name = "qty") val qty: Int,
    @ColumnInfo(name = "total_price") val totalPrice: Double,
)