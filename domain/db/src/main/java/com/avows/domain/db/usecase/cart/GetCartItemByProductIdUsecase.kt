package com.avows.domain.db.usecase.cart

import com.avows.configs.base.BaseSuspendUseCase
import com.avows.configs.state.ResultState
import com.avows.domain.db.model.CartEntityDomain
import com.avows.domain.db.repository.CartRepository
import javax.inject.Inject

class GetCartItemByProductIdUsecase @Inject constructor(
    private val cartRepository: CartRepository
) : BaseSuspendUseCase<GetCartItemByProductIdUsecase.RequestValues, GetCartItemByProductIdUsecase.ResponseValues>() {

    class RequestValues(
        val productId: Int
    ): BaseSuspendUseCase.RequestValues

    class ResponseValues(
        val result: ResultState<CartEntityDomain?>
    ): BaseSuspendUseCase.ResponseValues

    override suspend fun execute(requestValues: RequestValues): ResponseValues {
        return try {
            val isExist = cartRepository.getCartItemByProductId(requestValues.productId)
            ResponseValues(ResultState.Success(isExist))
        } catch (e: Exception) {
            ResponseValues(ResultState.Error(e))
        }
    }
}