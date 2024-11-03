package com.avows.data.auth.di

import com.avows.data.auth.api.AuthApi
import com.avows.utility.consts.AppConst
import com.avows.utility.qualifier.AuthApiQualifier
import com.avows.utility.qualifier.OkhttpQualifier
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthApiModule {

    @AuthApiQualifier
    @Provides
    @Singleton
    fun provideAuthAPI(
        @OkhttpQualifier okHttpClient: OkHttpClient
    ): AuthApi {
        return Retrofit.Builder()
            .baseUrl(AppConst.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(AuthApi::class.java)
    }
}