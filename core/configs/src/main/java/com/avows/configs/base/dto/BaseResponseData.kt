package com.avows.configs.base.dto

data class BaseResponseData<T>(
    var code: Int = 0,
    var message: String? = null,
    var data: T? = null
)
