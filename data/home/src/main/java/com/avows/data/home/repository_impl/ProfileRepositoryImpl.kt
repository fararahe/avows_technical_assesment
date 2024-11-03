package com.avows.data.home.repository_impl

import com.avows.configs.functional.handler.ApiHandler
import com.avows.data.home.api.ProfileApi
import com.avows.domain.home.model.response.ProfileDomain
import com.avows.domain.home.repository.ProfileRepository
import com.avows.pref_data_store.pref.PrefDataStoreManager
import com.avows.shared_model.ProfileModel
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val profileApi: ProfileApi,
    private val prefDataStoreManager: PrefDataStoreManager
) : ProfileRepository {

    override suspend fun getProfile(token: String): ProfileDomain? = ApiHandler.handleApi {
            profileApi.getSingleUser()
        }?.toDomain().also {
            prefDataStoreManager.storeLoginData(
                token = token,
                data = it?.toModel()
            )
        }

    override suspend fun getProfilePref(): ProfileModel? =
        prefDataStoreManager.getUserData().first()

    override suspend fun getToken(): String =
        prefDataStoreManager.getUserToken().first()
}