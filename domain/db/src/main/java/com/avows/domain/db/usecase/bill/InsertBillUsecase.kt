package com.avows.domain.db.usecase.bill

import com.avows.configs.base.BaseSuspendUseCase
import com.avows.configs.state.ResultState
import com.avows.domain.db.model.BillEntityDomain
import com.avows.domain.db.repository.BillRepository
import javax.inject.Inject

class InsertBillUsecase @Inject constructor(
    private val billRepository: BillRepository
) : BaseSuspendUseCase<InsertBillUsecase.RequestValues, InsertBillUsecase.ResponseValues>() {

    class RequestValues(
        val request: BillEntityDomain
    ) : BaseSuspendUseCase.RequestValues

    class ResponseValues(
        val result: ResultState<Pair<Long, Double>>
    ): BaseSuspendUseCase.ResponseValues

    override suspend fun execute(requestValues: RequestValues): ResponseValues {
        return try {
            val isExist = billRepository.insertBill(requestValues.request)
            ResponseValues(ResultState.Success(isExist))
        } catch (e: Exception) {
            ResponseValues(ResultState.Error(e))
        }
    }
}