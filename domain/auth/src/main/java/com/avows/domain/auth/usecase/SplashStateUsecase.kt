package com.avows.domain.auth.usecase

import com.avows.configs.base.BaseSuspendUseCase
import com.avows.configs.state.ResultState
import com.avows.domain.auth.model.state.SplashScreenState
import com.avows.domain.auth.repository.AuthRepository
import javax.inject.Inject

class SplashStateUsecase @Inject constructor(
    private val authRepository: AuthRepository
): BaseSuspendUseCase<SplashStateUsecase.RequestValues, SplashStateUsecase.Responsevalues>() {

    class RequestValues: BaseSuspendUseCase.RequestValues

    class Responsevalues(
        val result: ResultState<SplashScreenState>
    ): ResponseValues

    override suspend fun execute(requestValues: RequestValues): Responsevalues {
        return try {
            val isExist = authRepository.validateSplashState()
            Responsevalues(ResultState.Success(isExist))
        } catch (e: Exception) {
            Responsevalues(ResultState.Error(e))
        }
    }
}