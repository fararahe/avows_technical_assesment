package com.avows.domain.home.usecase.category

import com.avows.configs.base.BaseSuspendUseCase
import com.avows.configs.state.ResultState
import com.avows.domain.home.repository.CategoryRepository
import javax.inject.Inject

class CategoriesUsecase @Inject constructor(
    private val categoryRepository: CategoryRepository
) : BaseSuspendUseCase<CategoriesUsecase.RequestValues, CategoriesUsecase.ResponseValues>() {

    class RequestValues: BaseSuspendUseCase.RequestValues

    class ResponseValues(
        val result: ResultState<List<String>?>
    ): BaseSuspendUseCase.ResponseValues

    override suspend fun execute(requestValues: RequestValues): ResponseValues {
        return try {
            val isExist = categoryRepository.getAllCategories()
            ResponseValues(ResultState.Success(isExist))
        } catch (e: Exception) {
            ResponseValues(ResultState.Error(e))
        }
    }
}