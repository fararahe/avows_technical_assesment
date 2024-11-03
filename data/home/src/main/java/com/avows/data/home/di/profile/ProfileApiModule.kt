package com.avows.data.home.di.profile

import com.avows.data.home.api.ProfileApi
import com.avows.utility.consts.AppConst
import com.avows.utility.qualifier.OkhttpQualifier
import com.avows.utility.qualifier.ProfileApiQualifier
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
object ProfileApiModule {

    @ProfileApiQualifier
    @Provides
    @Singleton
    fun provideProfileApi(
        @OkhttpQualifier okHttpClient: OkHttpClient
    ): ProfileApi = Retrofit.Builder()
        .baseUrl(AppConst.BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .client(okHttpClient)
        .build()
        .create(ProfileApi::class.java)

}