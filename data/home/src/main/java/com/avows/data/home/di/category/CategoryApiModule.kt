package com.avows.data.home.di.category

import com.avows.data.home.api.CategoryApi
import com.avows.utility.consts.AppConst
import com.avows.utility.qualifier.CategoriesApiQualifier
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
object CategoryApiModule {

    @CategoriesApiQualifier
    @Provides
    @Singleton
    fun provideCategoryApi(
        @OkhttpQualifier okHttpClient: OkHttpClient
    ): CategoryApi = Retrofit.Builder()
        .baseUrl(AppConst.BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .client(okHttpClient)
        .build()
        .create(CategoryApi::class.java)
}