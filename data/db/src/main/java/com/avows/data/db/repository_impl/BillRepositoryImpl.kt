package com.avows.data.db.repository_impl

import com.avows.data.db.data.dao.BillDao
import com.avows.data.db.data.entity.BillEntity
import com.avows.domain.db.model.BillEntityDomain
import com.avows.domain.db.repository.BillRepository
import javax.inject.Inject

class BillRepositoryImpl @Inject constructor(
    private val billDao: BillDao
) : BillRepository {

    override suspend fun insertBill(bill: BillEntityDomain): Pair<Long, Double> =
        Pair(
            billDao.insertBill(
                BillEntity(
                    dateTime = bill.dateTime,
                    itemSold = bill.itemSold,
                    totalPrice = bill.totalPrice
                )
            ),
            bill.totalPrice
        )

    override suspend fun getBillById(billId: Int): BillEntityDomain? =
        billDao.getBillById(billId)?.let { data ->
            BillEntityDomain(
                billId = data.billId,
                dateTime = data.dateTime,
                itemSold = data.itemSold,
                totalPrice = data.totalPrice
            )
        }
}