package com.avows.domain.home.repository

import com.avows.domain.home.model.response.ProductDomain

interface ProductRepository {

    suspend fun getAllProduct(): List<ProductDomain>?

}