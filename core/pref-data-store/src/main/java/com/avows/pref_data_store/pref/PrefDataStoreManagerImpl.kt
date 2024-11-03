package com.avows.pref_data_store.pref

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.avows.shared_model.ProfileModel
import com.avows.utility.qualifier.PrefDataStoreQualifier
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PrefDataStoreManagerImpl @Inject constructor(
    @PrefDataStoreQualifier private val preferences: DataStore<Preferences>,
) : PrefDataStoreManager {

    private val USER_TOKEN = stringPreferencesKey(USER_TOKEN_PREF)
    private val USER = stringPreferencesKey(USER_PREF)
    private val IS_CATEGORY_LOADED = booleanPreferencesKey(IS_CATEGORY_LOADED_PREF)

    override suspend fun storeLoginData(data: ProfileModel?, token: String) {
        val result = moshi.adapter(ProfileModel::class.java).toJson(data)
        preferences.edit {
            it[USER_TOKEN] = token
            it[USER] = result
        }
    }

    override fun getUserToken(): Flow<String> = preferences.data.map {
        it[USER_TOKEN] ?: ""
    }

    override fun getUserData(): Flow<ProfileModel?> {
        return preferences.data.map {
            it[USER]?.let { profile ->
                moshi.adapter(ProfileModel::class.java).fromJson(profile)
            }
        }
    }

    override suspend fun setCategoryFirstLoaded() {
        preferences.edit {
            it[IS_CATEGORY_LOADED] = true
        }
    }

    override fun isCategoryFirstLoaded(): Flow<Boolean> = preferences.data.map {
        it[IS_CATEGORY_LOADED] ?: false
    }

    override suspend fun resetLoadedCategoryFlag() {
        preferences.edit {
            it[IS_CATEGORY_LOADED] = false
        }
    }

    override suspend fun clearDataStore() {
        preferences.edit {
            it.clear()
        }
    }

    companion object {
        private const val USER_TOKEN_PREF = "USER_TOKEN_PREF"
        private const val USER_PREF = "USER_PREF"
        private const val IS_CATEGORY_LOADED_PREF = "IS_CATEGORY_LOADED_PREF"
        private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    }
}