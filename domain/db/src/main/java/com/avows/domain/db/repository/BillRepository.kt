package com.avows.domain.db.repository

import com.avows.domain.db.model.BillEntityDomain

interface BillRepository {

    suspend fun insertBill(bill: BillEntityDomain): Pair<Long, Double>

    suspend fun getBillById(billId: Int): BillEntityDomain?

}