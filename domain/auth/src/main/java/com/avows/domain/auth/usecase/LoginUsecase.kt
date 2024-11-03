package com.avows.domain.auth.usecase

import com.avows.configs.base.BaseSuspendUseCase
import com.avows.configs.state.ResultState
import com.avows.domain.auth.model.domain.LoginDomain
import com.avows.domain.auth.model.request.LoginRequest
import com.avows.domain.auth.repository.AuthRepository
import javax.inject.Inject

class LoginUsecase @Inject constructor(
    private val authRepository: AuthRepository
) : BaseSuspendUseCase<LoginUsecase.RequestValues, LoginUsecase.ResponseValues>() {

    class RequestValues(val request: LoginRequest) : BaseSuspendUseCase.RequestValues

    class ResponseValues(val result: ResultState<LoginDomain?>): BaseSuspendUseCase.ResponseValues

    override suspend fun execute(requestValues: RequestValues): ResponseValues {
        return try {
            val isExist = authRepository.postLogin(requestValues.request)
            ResponseValues(ResultState.Success(isExist))
        } catch (e: Exception) {
            ResponseValues(ResultState.Error(e))
        }
    }
}