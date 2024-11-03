package com.avows.domain.home.usecase.profile

import com.avows.configs.base.BaseSuspendUseCase
import com.avows.configs.state.ResultState
import com.avows.domain.home.model.response.ProfileDomain
import com.avows.domain.home.repository.ProfileRepository
import javax.inject.Inject

class GetProfileUsecase @Inject constructor(
    private val profileRepository: ProfileRepository
) : BaseSuspendUseCase<GetProfileUsecase.RequestValues, GetProfileUsecase.ResponseValues>() {

    class RequestValues(
        val token: String
    ): BaseSuspendUseCase.RequestValues

    class ResponseValues(
        val result: ResultState<ProfileDomain?>
    ): BaseSuspendUseCase.ResponseValues

    override suspend fun execute(requestValues: RequestValues): ResponseValues {
        return try {
            val isExist = profileRepository.getProfile(requestValues.token)
            ResponseValues(ResultState.Success(isExist))
        } catch (e: Exception) {
            ResponseValues(ResultState.Error(e))
        }
    }
}