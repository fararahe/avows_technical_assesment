package com.avows.domain.home.repository

import com.avows.domain.home.model.response.ProfileDomain
import com.avows.shared_model.ProfileModel

interface ProfileRepository {

    suspend fun getProfile(token: String): ProfileDomain?

    suspend fun getProfilePref(): ProfileModel?

    suspend fun getToken(): String
}