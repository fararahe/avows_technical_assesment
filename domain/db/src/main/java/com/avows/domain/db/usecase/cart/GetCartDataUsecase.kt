package com.avows.domain.db.usecase.cart

import com.avows.configs.base.BaseSuspendUseCase
import com.avows.configs.state.ResultState
import com.avows.domain.db.model.CartEntityDomain
import com.avows.domain.db.repository.CartRepository
import javax.inject.Inject

class GetCartDataUsecase @Inject constructor(
    private val cartRepository: CartRepository
) : BaseSuspendUseCase<GetCartDataUsecase.RequestValues, GetCartDataUsecase.ResponseValues>() {

    class RequestValues(): BaseSuspendUseCase.RequestValues

    class ResponseValues(
        val result: ResultState<List<CartEntityDomain>>
    ): BaseSuspendUseCase.ResponseValues

    override suspend fun execute(requestValues: RequestValues): ResponseValues {
        return try {
            val isExist = cartRepository.getCartData()
            ResponseValues(ResultState.Success(isExist))
        } catch (e: Exception) {
            ResponseValues(ResultState.Error(e))
        }
    }
}