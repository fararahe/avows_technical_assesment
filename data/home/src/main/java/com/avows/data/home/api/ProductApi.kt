package com.avows.data.home.api

import com.avows.data.home.dto.ProductDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductApi {

    @GET("products")
    suspend fun getAllProducts(
        @Query("limit") limit: Int
    ): Response<List<ProductDto>>

}