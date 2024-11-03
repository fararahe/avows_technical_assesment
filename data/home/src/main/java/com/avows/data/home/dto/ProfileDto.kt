package com.avows.data.home.dto

import com.avows.domain.home.model.response.ProfileDomain
import com.avows.utility.extensions.orZero
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProfileDto (
    @Json(name = "id")
    val id: Int? = null,
    @Json(name = "email")
    val email: String? = null,
    @Json(name = "username")
    val username: String? = null,
    @Json(name = "password")
    val password: String? = null,
    @Json(name = "name")
    val name: NameDto? = null,
    @Json(name = "address")
    val address: AddressDto? = null,
    @Json(name = "phone")
    val phone: String? = null
) {

    fun toDomain(): ProfileDomain =
        ProfileDomain(
            id = id.orZero(),
            email = email.orEmpty(),
            username = username.orEmpty(),
            password = password.orEmpty(),
            name = name?.toDomain(),
            address = address?.toDomain(),
            phone = phone.orEmpty()
        )

    @JsonClass(generateAdapter = true)
    data class NameDto(
        @Json(name = "firstname")
        val firstName: String? = null,
        @Json(name = "lastname")
        val lastName: String? = null,
    ) {
        fun toDomain() : ProfileDomain.NameDomain =
            ProfileDomain.NameDomain(
                firstName = firstName.orEmpty(),
                lastName = lastName.orEmpty()
            )
    }

    @JsonClass(generateAdapter = true)
    data class AddressDto(
        @Json(name = "city")
        val city: String? = null,
        @Json(name = "street")
        val street: String? = null,
        @Json(name = "number")
        val number: Int? = null,
        @Json(name = "zipcode")
        val zipcode: String? = null,
        @Json(name = "geolocation")
        val geolocation: GeoLocationDto? = null
    ) {
        fun toDomain() : ProfileDomain.AddressDomain =
            ProfileDomain.AddressDomain(
                city = city.orEmpty(),
                street = street.orEmpty(),
                number = number.orZero(),
                zipcode = zipcode.orEmpty(),
                geolocation = geolocation?.toDomain()
            )
    }

    @JsonClass(generateAdapter = true)
    data class GeoLocationDto(
        @Json(name = "lat")
        val lat: String? = null,
        @Json(name = "long")
        val long: String? = null
    ) {
        fun toDomain(): ProfileDomain.GeoLocationDomain =
            ProfileDomain.GeoLocationDomain(
                lat = lat.orEmpty(),
                long = long.orEmpty()
            )
    }
}