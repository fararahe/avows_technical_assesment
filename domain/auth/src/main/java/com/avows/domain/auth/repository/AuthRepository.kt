package com.avows.domain.auth.repository

import com.avows.domain.auth.model.domain.LoginDomain
import com.avows.domain.auth.model.request.LoginRequest
import com.avows.domain.auth.model.state.SplashScreenState

interface AuthRepository {

    suspend fun validateSplashState(): SplashScreenState

    suspend fun postLogin(request: LoginRequest): LoginDomain?

    suspend fun postLogout()
}