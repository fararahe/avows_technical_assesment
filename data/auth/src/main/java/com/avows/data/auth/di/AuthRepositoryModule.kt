package com.avows.data.auth.di

import com.avows.data.auth.api.AuthApi
import com.avows.data.auth.repository_impl.AuthRepositoryImpl
import com.avows.domain.auth.repository.AuthRepository
import com.avows.domain.db.repository.CartRepository
import com.avows.pref_data_store.pref.PrefDataStoreManager
import com.avows.utility.qualifier.AuthApiQualifier
import com.avows.utility.qualifier.PrefDataStoreManagerQualifier
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthRepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRepository(
        @AuthApiQualifier authApi: AuthApi,
        @PrefDataStoreManagerQualifier prefDataStoreManager: PrefDataStoreManager,
        cartRepository: CartRepository
    ): AuthRepository {
        return AuthRepositoryImpl(
            authApi = authApi,
            prefDataStoreManager = prefDataStoreManager,
            cartRepository = cartRepository
        )
    }
}