package com.avows.technicalassessment.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.avows.data.db.data.dao.BillDao
import com.avows.data.db.data.dao.CartDao
import com.avows.data.db.data.dao.ListProductDao
import com.avows.data.db.data.entity.BillEntity
import com.avows.data.db.data.entity.CartEntity
import com.avows.data.db.data.entity.ListProductEntity

@Database(
    entities = [
        CartEntity::class,
        BillEntity::class,
        ListProductEntity::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun cartDao(): CartDao
    abstract fun billDao(): BillDao
    abstract fun productDao(): ListProductDao
}