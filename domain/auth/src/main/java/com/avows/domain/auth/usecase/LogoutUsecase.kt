package com.avows.domain.auth.usecase

import com.avows.configs.base.BaseSuspendUseCase
import com.avows.configs.state.ResultState
import com.avows.domain.auth.repository.AuthRepository
import javax.inject.Inject

class LogoutUsecase @Inject constructor(
    private val authRepository: AuthRepository
) : BaseSuspendUseCase<LogoutUsecase.RequestValues, LogoutUsecase.ResponseValues>() {

    class RequestValues : BaseSuspendUseCase.RequestValues

    class ResponseValues(val result: ResultState<Unit>): BaseSuspendUseCase.ResponseValues

    override suspend fun execute(requestValues: RequestValues): ResponseValues {
        return try {
            val isExist = authRepository.postLogout()
            ResponseValues(ResultState.Success(isExist))
        } catch (e: Exception) {
            ResponseValues(ResultState.Error(e))
        }
    }
}