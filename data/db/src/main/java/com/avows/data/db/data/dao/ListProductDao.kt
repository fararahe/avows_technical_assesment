package com.avows.data.db.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.avows.data.db.data.entity.ListProductEntity

@Dao
interface ListProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: List<ListProductEntity>)

    @Query("SELECT * FROM table_list_product WHERE product_id = :productId")
    suspend fun getProductById(productId: Int): ListProductEntity?

    @Query("SELECT * FROM table_list_product")
    suspend fun getAllProduct(): List<ListProductEntity>?
}