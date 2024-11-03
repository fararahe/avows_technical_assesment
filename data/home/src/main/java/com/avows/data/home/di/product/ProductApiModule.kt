package com.avows.data.home.di.product

import com.avows.data.home.api.ProductApi
import com.avows.utility.consts.AppConst
import com.avows.utility.qualifier.OkhttpQualifier
import com.avows.utility.qualifier.ProductApiQualifier
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
object ProductApiModule {

    @ProductApiQualifier
    @Provides
    @Singleton
    fun provideProductApi(
        @OkhttpQualifier okHttpClient: OkHttpClient
    ): ProductApi = Retrofit.Builder()
        .baseUrl(AppConst.BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .client(okHttpClient)
        .build()
        .create(ProductApi::class.java)
}