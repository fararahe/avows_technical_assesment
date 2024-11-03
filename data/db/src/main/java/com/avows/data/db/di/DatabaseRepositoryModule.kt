package com.avows.data.db.di

import com.avows.data.db.data.dao.BillDao
import com.avows.data.db.data.dao.CartDao
import com.avows.data.db.data.dao.ListProductDao
import com.avows.data.db.repository_impl.BillRepositoryImpl
import com.avows.data.db.repository_impl.CartRepositoryImpl
import com.avows.data.db.repository_impl.ListProductRepositoryImpl
import com.avows.domain.db.repository.BillRepository
import com.avows.domain.db.repository.CartRepository
import com.avows.domain.db.repository.ListProductRepository
import com.avows.utility.qualifier.BillDaoQualifier
import com.avows.utility.qualifier.CartDaoQualifier
import com.avows.utility.qualifier.ListProductDaoQualifier
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseRepositoryModule {

    @Provides
    @Singleton
    fun provideCartRepository(
        @CartDaoQualifier cartDao: CartDao
    ): CartRepository = CartRepositoryImpl(cartDao)

    @Provides
    @Singleton
    fun provideBillRepository(
        @BillDaoQualifier billDao: BillDao
    ): BillRepository = BillRepositoryImpl(billDao)

    @Provides
    @Singleton
    fun provideListProductRepository(
        @ListProductDaoQualifier listProductDao: ListProductDao
    ): ListProductRepository = ListProductRepositoryImpl(listProductDao)
}