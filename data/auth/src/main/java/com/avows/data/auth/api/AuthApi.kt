package com.avows.data.auth.api

import com.avows.data.auth.dto.LoginDto
import com.avows.domain.auth.model.request.LoginRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("auth/login")
    suspend fun postLogin(@Body request: LoginRequest): Response<LoginDto>

}