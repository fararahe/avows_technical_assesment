package com.avows.data.db.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "table_bill",
)
class BillEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "bill_id") val billId: Int = 0,
    @ColumnInfo(name = "datetime") val dateTime: String,
    @ColumnInfo(name = "item_sold") val itemSold: Int,
    @ColumnInfo(name = "total_price") val totalPrice: Double,
)