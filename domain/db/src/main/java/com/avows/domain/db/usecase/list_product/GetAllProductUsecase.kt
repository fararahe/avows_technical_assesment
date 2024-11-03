package com.avows.domain.db.usecase.list_product

import com.avows.configs.base.BaseSuspendUseCase
import com.avows.configs.state.ResultState
import com.avows.domain.db.model.ListProductEntityDomain
import com.avows.domain.db.repository.ListProductRepository
import javax.inject.Inject

class GetAllProductUsecase @Inject constructor(
    private val listProductRepository: ListProductRepository
) : BaseSuspendUseCase<GetAllProductUsecase.RequestValues, GetAllProductUsecase.ResponseValues>() {

    class RequestValues(): BaseSuspendUseCase.RequestValues

    class ResponseValues(
        val result: ResultState<List<ListProductEntityDomain>>
    ): BaseSuspendUseCase.ResponseValues

    override suspend fun execute(requestValues: RequestValues): ResponseValues {
        return try {
            val isExist = listProductRepository.getAllProduct()
            ResponseValues(ResultState.Success(isExist))
        } catch (e: Exception) {
            ResponseValues(ResultState.Error(e))
        }
    }
}