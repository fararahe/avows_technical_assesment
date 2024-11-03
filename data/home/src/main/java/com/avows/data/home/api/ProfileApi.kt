package com.avows.data.home.api

import com.avows.data.home.dto.ProfileDto
import retrofit2.Response
import retrofit2.http.GET

interface ProfileApi {

    @GET("users/1")
    suspend fun getSingleUser(): Response<ProfileDto>

}