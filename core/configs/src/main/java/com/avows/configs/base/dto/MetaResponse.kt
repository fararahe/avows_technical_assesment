package com.avows.configs.base.dto


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MetaResponse(
    @Json(name = "code")
    val code: Int? = null,
    @Json(name = "error")
    val error: String? = null,
    @Json(name = "errorDetail")
    val errorDetail: ErrorDetail? = null,
    @Json(name = "message")
    val message: String? = null
) {
    @JsonClass(generateAdapter = true)
    data class ErrorDetail(
        @Json(name = "additionalProp1")
        val additionalProp1: AdditionalProp1? = null,
        @Json(name = "additionalProp2")
        val additionalProp2: AdditionalProp2? = null,
        @Json(name = "additionalProp3")
        val additionalProp3: AdditionalProp3? = null
    ) {
        @JsonClass(generateAdapter = true)
        class AdditionalProp1

        @JsonClass(generateAdapter = true)
        class AdditionalProp2

        @JsonClass(generateAdapter = true)
        class AdditionalProp3
    }
}