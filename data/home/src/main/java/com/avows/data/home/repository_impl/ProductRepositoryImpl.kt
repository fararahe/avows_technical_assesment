package com.avows.data.home.repository_impl

import com.avows.configs.functional.handler.ApiHandler
import com.avows.data.home.api.ProductApi
import com.avows.domain.home.model.response.ProductDomain
import com.avows.domain.home.repository.ProductRepository
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productApi: ProductApi,
) : ProductRepository {

    override suspend fun getAllProduct(): List<ProductDomain>? = ApiHandler.handleApi {
        productApi.getAllProducts(20)
    }?.map { it.toDomain() }
}