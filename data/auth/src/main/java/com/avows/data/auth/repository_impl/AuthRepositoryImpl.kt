package com.avows.data.auth.repository_impl

import com.avows.configs.functional.handler.ApiHandler
import com.avows.data.auth.api.AuthApi
import com.avows.domain.auth.model.domain.LoginDomain
import com.avows.domain.auth.model.request.LoginRequest
import com.avows.domain.auth.model.state.SplashScreenState
import com.avows.domain.auth.repository.AuthRepository
import com.avows.domain.db.repository.CartRepository
import com.avows.pref_data_store.pref.PrefDataStoreManager
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi,
    private val prefDataStoreManager: PrefDataStoreManager,
    private val cartRepository: CartRepository
) : AuthRepository {

    override suspend fun validateSplashState(): SplashScreenState {
        val token = prefDataStoreManager.getUserToken().first()
        val user = prefDataStoreManager.getUserData().first()
        return if(user != null && token.isNotEmpty()) {
                SplashScreenState.ToMain
            } else {
                SplashScreenState.ToLogin
            }
    }

    override suspend fun postLogin(request: LoginRequest): LoginDomain? = ApiHandler.handleApi {
        authApi.postLogin(request)
    }?.toDomain()

    override suspend fun postLogout() {
        prefDataStoreManager.clearDataStore()
        cartRepository.clearAllCartItems()
    }
}