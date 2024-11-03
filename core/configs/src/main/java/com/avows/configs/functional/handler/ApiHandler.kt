package com.avows.configs.functional.handler

import retrofit2.Response

object ApiHandler {
    suspend fun <T : Any> handleApi(
        errorHandler: ErrorResponseHandler = DefaultErrorResponseHandler(),
        block: suspend () -> Response<T>
    ): T? {
        try {
            val response = block.invoke()

            if (response.isSuccessful) {
                return response.body()
            }

            // When Error
            throw errorHandler.handle(response.errorBody(), response.code())
        } catch (e: Exception) {
            throw e
        }
    }
}