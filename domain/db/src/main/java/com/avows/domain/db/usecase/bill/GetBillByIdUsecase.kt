package com.avows.domain.db.usecase.bill

import com.avows.configs.base.BaseSuspendUseCase
import com.avows.configs.state.ResultState
import com.avows.domain.db.model.BillEntityDomain
import com.avows.domain.db.repository.BillRepository
import javax.inject.Inject

class GetBillByIdUsecase @Inject constructor(
    private val billRepository: BillRepository
) : BaseSuspendUseCase<GetBillByIdUsecase.RequestValues, GetBillByIdUsecase.ResponseValues>() {

    class RequestValues(
        val billId: Int
    ): BaseSuspendUseCase.RequestValues

    class ResponseValues(
        val result: ResultState<BillEntityDomain?>
    ): BaseSuspendUseCase.ResponseValues

    override suspend fun execute(requestValues: RequestValues): ResponseValues {
        return try {
            val isExit = billRepository.getBillById(requestValues.billId)
            ResponseValues(ResultState.Success(isExit))
        } catch (e: Exception) {
            ResponseValues(ResultState.Error(e))
        }
    }
}