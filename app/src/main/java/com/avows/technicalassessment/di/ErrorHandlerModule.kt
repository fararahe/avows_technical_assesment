package com.avows.technicalassessment.di

import com.avows.configs.exception.provider.ErrorHandlerProvider
import com.avows.navigation.AuthNavigation
import com.avows.pref_data_store.pref.PrefDataStoreManager
import com.avows.technicalassessment.provider.ErrorHandlerProviderImpl
import com.avows.utility.qualifier.PrefDataStoreManagerQualifier
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ErrorHandlerModule {

    @Provides
    @Singleton
    fun provideErrorHandlerProvider(
        @PrefDataStoreManagerQualifier prefDataStoreManager: PrefDataStoreManager,
        authNavigation: AuthNavigation
    ) : ErrorHandlerProvider {
        return ErrorHandlerProviderImpl(
            prefDataStoreManager = prefDataStoreManager,
            authNavigation = authNavigation
        )
    }
}