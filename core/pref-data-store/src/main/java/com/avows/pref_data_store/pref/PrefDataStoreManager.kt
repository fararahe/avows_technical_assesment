package com.avows.pref_data_store.pref

import com.avows.shared_model.ProfileModel
import kotlinx.coroutines.flow.Flow

interface PrefDataStoreManager {

    suspend fun storeLoginData(data: ProfileModel?, token: String)

    fun getUserToken(): Flow<String>

    fun getUserData(): Flow<ProfileModel?>

    suspend fun setCategoryFirstLoaded()

    fun isCategoryFirstLoaded(): Flow<Boolean>

    suspend fun resetLoadedCategoryFlag()

    suspend fun clearDataStore()

}