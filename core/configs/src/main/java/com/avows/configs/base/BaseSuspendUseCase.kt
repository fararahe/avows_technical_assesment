package com.avows.configs.base

abstract class BaseSuspendUseCase<REQUEST : BaseSuspendUseCase.RequestValues, RESPONSE : BaseSuspendUseCase.ResponseValues> {

    abstract suspend fun execute(requestValues: REQUEST): RESPONSE

    /**
     * Data passed to a request.
     */
    interface RequestValues

    /**
     * Data received from a request.
     */
    interface ResponseValues
}