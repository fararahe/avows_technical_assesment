package com.avows.domain.db.usecase.cart

import com.avows.configs.base.BaseSuspendUseCase
import com.avows.configs.state.ResultState
import com.avows.domain.db.repository.CartRepository
import javax.inject.Inject

class ClearAllCartItemsUsecase @Inject constructor(
    private val cartRepository: CartRepository
) : BaseSuspendUseCase<ClearAllCartItemsUsecase.RequestValues, ClearAllCartItemsUsecase.ResponseValues>() {

    class RequestValues(): BaseSuspendUseCase.RequestValues

    class ResponseValues(
        val result: ResultState<Unit>
    ): BaseSuspendUseCase.ResponseValues

    override suspend fun execute(requestValues: RequestValues): ResponseValues {
        return try {
            val isExist = cartRepository.clearAllCartItems()
            ResponseValues(ResultState.Success(isExist))
        } catch (e: Exception) {
            ResponseValues(ResultState.Error(e))
        }
    }
}