package com.avows.technicalassessment.di

import android.content.Context
import androidx.room.Room
import com.avows.data.db.data.dao.BillDao
import com.avows.data.db.data.dao.CartDao
import com.avows.data.db.data.dao.ListProductDao
import com.avows.technicalassessment.database.AppDatabase
import com.avows.utility.qualifier.BillDaoQualifier
import com.avows.utility.qualifier.CartDaoQualifier
import com.avows.utility.qualifier.ListProductDaoQualifier
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import timber.log.Timber
import java.util.concurrent.Executors
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ) : AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "assessment"
        ).setQueryCallback(
            { sqlQuery, bindArgs ->
                Timber.d("SQL Query: %s SQL Args: %s", sqlQuery, bindArgs.joinToString(", "))
            },
            Executors.newSingleThreadExecutor()
        ).build()
    }

    @CartDaoQualifier
    @Provides
    @Singleton
    fun provideCartDao(
        database: AppDatabase
    ): CartDao = database.cartDao()

    @BillDaoQualifier
    @Provides
    @Singleton
    fun provideBillDao(
        database: AppDatabase
    ): BillDao = database.billDao()

    @ListProductDaoQualifier
    @Provides
    @Singleton
    fun provideProductDao(
        database: AppDatabase
    ): ListProductDao = database.productDao()

}