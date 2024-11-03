package com.avows.data.home.dto

import com.avows.domain.home.model.response.ProductDomain
import com.avows.utility.extensions.orZero
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductDto(
    @Json(name = "id")
    val id: Int? = null,
    @Json(name = "title")
    val title: String? = null,
    @Json(name = "price")
    val price: Double? = null,
    @Json(name = "category")
    val category: String? = null,
    @Json(name = "description")
    val description: String? = null,
    @Json(name = "image")
    val image: String? = null,
) {
    fun toDomain(): ProductDomain {
        return ProductDomain(
            id = id.orZero(),
            title = title.orEmpty(),
            price = price.orZero(),
            category = category.orEmpty(),
            description = description.orEmpty(),
            image = image.orEmpty()
        )
    }
}
