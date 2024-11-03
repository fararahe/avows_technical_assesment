package com.avows.auth.di

import com.avows.auth.navigation.AuthNavigationImpl
import com.avows.navigation.AuthNavigation
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthNavigationModule {

    @Provides
    @Singleton
    fun provideAuthNavigation(): AuthNavigation {
        return AuthNavigationImpl()
    }

}