package com.avows.data.db.repository_impl

import com.avows.data.db.data.dao.CartDao
import com.avows.data.db.data.entity.CartEntity
import com.avows.domain.db.model.CartEntityDomain
import com.avows.domain.db.repository.CartRepository
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val cartDao: CartDao
) : CartRepository {

    override suspend fun insertCart(item: CartEntityDomain): Long =
        cartDao.insertCart(
            CartEntity(
                productId = item.productId,
                title = item.title,
                category = item.category,
                description = item.description,
                image = item.image,
                price = item.price,
                qty = item.qty
            )
        )

    override suspend fun getCartData(): List<CartEntityDomain> =
        cartDao.getCartData()?.map { data ->
            CartEntityDomain(
                productId = data.productId,
                title = data.title,
                category = data.category,
                description = data.description,
                image = data.image,
                price = data.price,
                qty = data.qty
            )
        } ?: emptyList()

    override suspend fun getCartItemByProductId(productId: Int): CartEntityDomain? =
        cartDao.getCartItemByProductId(productId)?.let { data ->
            CartEntityDomain(
                productId = data.productId,
                title = data.title,
                category = data.category,
                description = data.description,
                image = data.image,
                price = data.price,
                qty = data.qty
            )
        }

    override suspend fun updateCartItemQty(productId: Int, newQty: Int): Int {
        cartDao.updateCartItemQty(productId = productId, newQty = newQty)
        return productId
    }

    override suspend fun deleteCartItemById(productId: Int) =
        cartDao.deleteCartItemById(productId)

    override suspend fun clearAllCartItems() = cartDao.clearAllItems()
}