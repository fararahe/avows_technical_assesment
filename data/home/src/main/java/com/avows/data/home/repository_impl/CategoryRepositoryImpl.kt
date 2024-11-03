package com.avows.data.home.repository_impl

import com.avows.configs.functional.handler.ApiHandler
import com.avows.data.home.api.CategoryApi
import com.avows.domain.home.repository.CategoryRepository
import com.avows.pref_data_store.pref.PrefDataStoreManager
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val categoryApi: CategoryApi,
    private val prefDataStoreManager: PrefDataStoreManager
) : CategoryRepository {

    override suspend fun getAllCategories(): List<String>? = ApiHandler.handleApi {
        categoryApi.getAllCategories()
    }

    override suspend fun isCategoryFirstLoaded(): Boolean = prefDataStoreManager.isCategoryFirstLoaded().first()

    override suspend fun resetLoadedCategoryFlag() {
        prefDataStoreManager.resetLoadedCategoryFlag()
    }
}