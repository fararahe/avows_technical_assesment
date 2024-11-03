package com.avows.data.auth.dto

import com.avows.domain.auth.model.domain.LoginDomain
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginDto(
    @Json(name = "token")
    val token: String? = null,
) {
    fun toDomain(): LoginDomain = LoginDomain(token.orEmpty())
}