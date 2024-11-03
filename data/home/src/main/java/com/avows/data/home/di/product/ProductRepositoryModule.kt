package com.avows.data.home.di.product

import com.avows.data.home.api.ProductApi
import com.avows.data.home.repository_impl.ProductRepositoryImpl
import com.avows.domain.home.repository.ProductRepository
import com.avows.utility.qualifier.ProductApiQualifier
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProductRepositoryModule {

    @Provides
    @Singleton
    fun provideProductRepository(
        @ProductApiQualifier productApi: ProductApi,
    ): ProductRepository = ProductRepositoryImpl(
        productApi = productApi
    )

}