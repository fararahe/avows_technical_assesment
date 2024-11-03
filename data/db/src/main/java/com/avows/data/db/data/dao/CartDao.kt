package com.avows.data.db.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.avows.data.db.data.entity.CartEntity

@Dao
interface CartDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertCart(item: CartEntity): Long

    @Query("SELECT * FROM table_cart")
    suspend fun getCartData(): List<CartEntity>?

    @Query("SELECT * FROM table_cart WHERE product_id = :productId")
    suspend fun getCartItemByProductId(productId: Int): CartEntity?

    @Query("UPDATE table_cart SET qty = :newQty WHERE product_id = :productId")
    suspend fun updateCartItemQty(productId: Int, newQty: Int)

    @Query("DELETE FROM table_cart WHERE product_id = :productId")
    suspend fun deleteCartItemById(productId: Int)

    @Query("DELETE FROM table_cart")
    suspend fun clearAllItems()
}