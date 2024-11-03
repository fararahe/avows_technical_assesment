package com.avows.domain.home.usecase.profile

import com.avows.configs.base.BaseSuspendUseCase
import com.avows.configs.state.ResultState
import com.avows.domain.home.repository.ProfileRepository
import com.avows.shared_model.ProfileModel
import javax.inject.Inject

class GetProfilePrefUsecase @Inject constructor(
    private val profileRepository: ProfileRepository
) : BaseSuspendUseCase<GetProfilePrefUsecase.RequestValues, GetProfilePrefUsecase.ResponseValues>() {

    class RequestValues: BaseSuspendUseCase.RequestValues

    class ResponseValues(
        val result: ResultState<ProfileModel?>
    ): BaseSuspendUseCase.ResponseValues

    override suspend fun execute(requestValues: RequestValues): ResponseValues {
        return try {
            val isExist = profileRepository.getProfilePref()
            ResponseValues(ResultState.Success(isExist))
        } catch (e: Exception) {
            ResponseValues(ResultState.Error(e))
        }
    }
}