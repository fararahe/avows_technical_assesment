package com.avows.domain.home.model.response

import com.avows.shared_model.ProfileModel

data class ProfileDomain (
    val id: Int,
    val email: String,
    val username: String,
    val password: String,
    val name: NameDomain?,
    val address: AddressDomain?,
    val phone: String
)  {

    fun toModel(): ProfileModel =
        ProfileModel(
            id = id,
            email = email,
            username = username,
            name = name?.toModel(),
            address = address?.toModel(),
            phone = phone
        )

    data class NameDomain(
        val firstName: String,
        val lastName: String,
    ) {
        fun toModel(): ProfileModel.NameModel =
            ProfileModel.NameModel(
                firstName = firstName,
                lastName = lastName
            )
    }

    data class AddressDomain(
        val city: String,
        val street: String,
        val number: Int,
        val zipcode: String,
        val geolocation: GeoLocationDomain?
    ) {
        fun toModel(): ProfileModel.AddressModel =
            ProfileModel.AddressModel(
                city = city,
                street = street,
                number = number,
                zipcode = zipcode,
            )
    }
    
    data class GeoLocationDomain(
        val lat: String,
        val long: String
    )
}