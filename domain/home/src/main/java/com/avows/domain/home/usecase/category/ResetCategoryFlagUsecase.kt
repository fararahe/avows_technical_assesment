package com.avows.domain.home.usecase.category

import com.avows.configs.base.BaseSuspendUseCase
import com.avows.configs.state.ResultState
import com.avows.domain.home.repository.CategoryRepository
import javax.inject.Inject

class ResetCategoryFlagUsecase @Inject constructor(
    private val categoryRepository: CategoryRepository
) : BaseSuspendUseCase<ResetCategoryFlagUsecase.RequestValues, ResetCategoryFlagUsecase.ResponseValues>() {
    class RequestValues(): BaseSuspendUseCase.RequestValues

    class ResponseValues(val result: ResultState<Unit>): BaseSuspendUseCase.ResponseValues

    override suspend fun execute(requestValues: RequestValues): ResponseValues {
        return try {
            val isExist = categoryRepository.resetLoadedCategoryFlag()
            ResponseValues(ResultState.Success(isExist))
        } catch (e: Exception) {
            ResponseValues(ResultState.Error(e))
        }
    }
}