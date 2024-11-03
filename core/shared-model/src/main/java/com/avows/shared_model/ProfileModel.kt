package com.avows.shared_model

import com.avows.utility.extensions.toCapitalize

data class ProfileModel(
    val id: Int,
    val email: String,
    val username: String,
    val name: NameModel?,
    val address: AddressModel?,
    val phone: String
) {
    data class NameModel(
        val firstName: String,
        val lastName: String,
    ) {
        fun toModelString(): String = "${firstName.toCapitalize()} ${lastName.toCapitalize()}"
    }

    data class AddressModel(
        val city: String,
        val street: String,
        val number: Int,
        val zipcode: String,
    )
}
