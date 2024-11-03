package com.avows.configs.functional.handler

import com.avows.configs.exception.BadRequestException
import com.avows.configs.functional.DefaultErrorResponse
import okhttp3.ResponseBody

/**
 * Lets say the Response Error looks like this :
{
"code": 400 -> Int,
"message": "Error Message Here" -> String,
"flag": "This Error FLAG" -> String,
}
 * */
class DefaultErrorResponseHandler: ErrorResponseHandler() {

    override fun handleBadRequestError(responseBody: ResponseBody): BadRequestException {
        val errorResponseType = DefaultErrorResponse::class.java
        val errorWrapper = responseBody.errorBodyParser<DefaultErrorResponse>(errorResponseType)

        return BadRequestException(
            code = errorWrapper?.code ?: 0,
            message = errorWrapper?.message.orEmpty(),
            errorFlag = errorWrapper?.flag.orEmpty()
        )
    }
}