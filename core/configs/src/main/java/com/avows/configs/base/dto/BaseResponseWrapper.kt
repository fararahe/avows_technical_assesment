package com.avows.configs.base.dto


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BaseResponseWrapper<T: Any>(
    @Json(name = "data")
    val data: T? = null,
    @Json(name = "meta")
    val meta: MetaResponse? = null
)