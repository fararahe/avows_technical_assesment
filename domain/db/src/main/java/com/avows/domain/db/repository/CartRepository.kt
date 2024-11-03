package com.avows.domain.db.repository

import com.avows.domain.db.model.CartEntityDomain

interface CartRepository {

    suspend fun insertCart(item: CartEntityDomain): Long

    suspend fun getCartData(): List<CartEntityDomain>

    suspend fun getCartItemByProductId(productId: Int): CartEntityDomain?

    suspend fun updateCartItemQty(productId: Int, newQty: Int): Int

    suspend fun deleteCartItemById(productId: Int)

    suspend fun clearAllCartItems()

}