package com.avows.configs.exception

import java.net.SocketTimeoutException
import java.net.UnknownHostException

object ErrorApiStateHandler {

    /*
    * Support handle time while the response is error
    * */
    fun handleErrorState(
        throwable: Throwable,
        onHandleableException: (error: BadRequestException) -> Unit,
        onGeneralException: (error: Throwable) -> Unit,
        onTokenExpired: () -> Unit,
    ) {
        when (throwable) {
            is UnauthorizedException -> onTokenExpired()
            is UnknownHostException -> onGeneralException(throwable)
            is SocketTimeoutException -> onGeneralException(throwable)
            is BadRequestException -> {
                onHandleableException.invoke(throwable)
            }

            is ServerErrorException -> onGeneralException(throwable)
            else -> onGeneralException(throwable)
        }
    }

}