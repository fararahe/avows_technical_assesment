package com.avows.domain.db.usecase.cart

import com.avows.configs.base.BaseSuspendUseCase
import com.avows.configs.state.ResultState
import com.avows.domain.db.model.CartEntityDomain
import com.avows.domain.db.repository.CartRepository
import javax.inject.Inject

class InsertCartUsecase @Inject constructor(
    private val cartRepository: CartRepository
) : BaseSuspendUseCase<InsertCartUsecase.RequestValues, InsertCartUsecase.ResponseValues>(){

    class RequestValues(
        val request: CartEntityDomain
    ): BaseSuspendUseCase.RequestValues

    class ResponseValues(
        val result: ResultState<Long>
    ): BaseSuspendUseCase.ResponseValues

    override suspend fun execute(requestValues: RequestValues): ResponseValues {
        return try {
            val isExist = cartRepository.insertCart(requestValues.request)
            ResponseValues(ResultState.Success(isExist))
        } catch (e: Exception) {
            ResponseValues(ResultState.Error(e))
        }
    }
}