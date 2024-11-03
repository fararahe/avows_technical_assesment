package com.avows.data.home.di.category

import com.avows.data.home.api.CategoryApi
import com.avows.data.home.repository_impl.CategoryRepositoryImpl
import com.avows.domain.home.repository.CategoryRepository
import com.avows.pref_data_store.pref.PrefDataStoreManager
import com.avows.utility.qualifier.CategoriesApiQualifier
import com.avows.utility.qualifier.PrefDataStoreManagerQualifier
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CategoryRepositoryModule {

    @Provides
    @Singleton
    fun provideCategoryRepository(
        @CategoriesApiQualifier categoryApi: CategoryApi,
        @PrefDataStoreManagerQualifier prefDataStoreManager: PrefDataStoreManager
    ): CategoryRepository = CategoryRepositoryImpl(
        categoryApi = categoryApi,
        prefDataStoreManager = prefDataStoreManager
    )

}