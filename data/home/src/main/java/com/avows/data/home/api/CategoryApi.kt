package com.avows.data.home.api

import retrofit2.Response
import retrofit2.http.GET

interface CategoryApi {

    @GET("products/categories")
    suspend fun getAllCategories(): Response<List<String>>

}