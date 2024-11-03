package com.avows.home.di

import com.avows.home.navigation.HomeNavigationImpl
import com.avows.navigation.HomeNavigation
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomeNavigationImpl {

    @Provides
    @Singleton
    fun provideHomeNavigation(): HomeNavigation = HomeNavigationImpl()
}