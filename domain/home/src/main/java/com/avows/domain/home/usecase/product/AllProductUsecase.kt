package com.avows.domain.home.usecase.product

import com.avows.configs.base.BaseSuspendUseCase
import com.avows.configs.state.ResultState
import com.avows.domain.home.model.response.ProductDomain
import com.avows.domain.home.repository.ProductRepository
import javax.inject.Inject

class AllProductUsecase @Inject constructor(
    private val productRepository: ProductRepository
) : BaseSuspendUseCase<AllProductUsecase.RequestValues, AllProductUsecase.ResponseValues>() {

    class RequestValues: BaseSuspendUseCase.RequestValues

    class ResponseValues(
        val result: ResultState<List<ProductDomain>?>
    ): BaseSuspendUseCase.ResponseValues

    override suspend fun execute(requestValues: RequestValues): ResponseValues {
        return try {
            ResponseValues(ResultState.Success(productRepository.getAllProduct()))
        } catch (e: Exception) {
            ResponseValues(ResultState.Error(e))
        }
    }
}