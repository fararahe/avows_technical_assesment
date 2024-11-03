package com.avows.domain.db.usecase.cart

import com.avows.configs.base.BaseSuspendUseCase
import com.avows.configs.state.ResultState
import com.avows.domain.db.repository.CartRepository
import javax.inject.Inject

class UpdateCartItemQty @Inject constructor(
    private val cartRepository: CartRepository
) : BaseSuspendUseCase<UpdateCartItemQty.RequestValues, UpdateCartItemQty.ResponseValues>(){

    class RequestValues(
        val productId: Int,
        val newQty: Int
    ): BaseSuspendUseCase.RequestValues

    class ResponseValues(
        val result: ResultState<Int>
    ): BaseSuspendUseCase.ResponseValues

    override suspend fun execute(requestValues: RequestValues): ResponseValues {
        return try {
            val isExist =
                cartRepository.updateCartItemQty(requestValues.productId, requestValues.newQty)
            ResponseValues(ResultState.Success(isExist))
        } catch (e: Exception) {
            ResponseValues(ResultState.Error(e))
        }
    }
}