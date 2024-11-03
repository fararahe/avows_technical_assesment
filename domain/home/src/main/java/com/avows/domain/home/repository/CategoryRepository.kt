package com.avows.domain.home.repository

interface CategoryRepository {

    suspend fun getAllCategories(): List<String>?

    suspend fun isCategoryFirstLoaded(): Boolean

    suspend fun resetLoadedCategoryFlag()

}