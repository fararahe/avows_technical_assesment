package com.avows.domain.db.repository

import com.avows.domain.db.model.ListProductEntityDomain

interface ListProductRepository {

    suspend fun insertProduct(product: List<ListProductEntityDomain>)

    suspend fun getProductsById(productId: Int): ListProductEntityDomain?

    suspend fun getAllProduct(): List<ListProductEntityDomain>

}