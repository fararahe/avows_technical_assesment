package com.avows.data.db.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.avows.data.db.data.entity.BillEntity

@Dao
interface BillDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBill(bill: BillEntity): Long

    @Query("SELECT * FROM table_bill WHERE bill_id = :billId")
    suspend fun getBillById(billId: Int): BillEntity?
}