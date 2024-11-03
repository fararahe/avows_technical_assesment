package com.avows.auth.di

import com.avows.auth.util.PermissionHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object PermissionModule {

    @Provides
    fun providePermissionHandler() : PermissionHandler = PermissionHandler()
}