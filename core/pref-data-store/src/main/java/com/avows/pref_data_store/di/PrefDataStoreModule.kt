package com.avows.pref_data_store.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.avows.pref_data_store.pref.PrefDataStoreManager
import com.avows.pref_data_store.pref.PrefDataStoreManagerImpl
import com.avows.utility.qualifier.PrefDataStoreManagerQualifier
import com.avows.utility.qualifier.PrefDataStoreQualifier
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PrefDataStoreModule {

    private const val SESSION_NAME = "TechnicalAssessment"
    private val Context.sessionPreferencesDataStore: DataStore<Preferences> by preferencesDataStore(
        SESSION_NAME
    )

    @Provides
    @Singleton
    @PrefDataStoreQualifier
    fun providesSessionPreferencesDataStore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> = context.sessionPreferencesDataStore

    @Provides
    @Singleton
    @PrefDataStoreManagerQualifier
    fun providesSessionDataStoreManager(
        @PrefDataStoreQualifier preferences: DataStore<Preferences>,
    ): PrefDataStoreManager {
        return PrefDataStoreManagerImpl(preferences)
    }
}