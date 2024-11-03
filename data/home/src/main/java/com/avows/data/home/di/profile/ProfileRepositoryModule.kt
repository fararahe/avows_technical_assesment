package com.avows.data.home.di.profile

import com.avows.data.home.api.ProfileApi
import com.avows.data.home.repository_impl.ProfileRepositoryImpl
import com.avows.domain.home.repository.ProfileRepository
import com.avows.pref_data_store.pref.PrefDataStoreManager
import com.avows.utility.qualifier.PrefDataStoreManagerQualifier
import com.avows.utility.qualifier.ProfileApiQualifier
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProfileRepositoryModule {

    @Provides
    @Singleton
    fun provideProfileRepository(
        @ProfileApiQualifier profileApi: ProfileApi,
        @PrefDataStoreManagerQualifier prefDataStoreManager: PrefDataStoreManager
    ) : ProfileRepository = ProfileRepositoryImpl(profileApi, prefDataStoreManager)

}