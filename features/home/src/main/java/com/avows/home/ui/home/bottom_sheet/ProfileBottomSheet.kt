package com.avows.home.ui.home.bottom_sheet

import android.view.LayoutInflater
import android.view.ViewGroup
import com.avows.configs.base.BaseComponentBottomSheet
import com.avows.home.R
import com.avows.home.databinding.BottomSheetProfileBinding
import com.avows.shared_model.ProfileModel
import com.avows.utility.extensions.setOnSafeClickListener
import com.avows.utility.extensions.setVisibilty
import com.avows.utility.extensions.toCapitalize

class ProfileBottomSheet(
    private val data: ProfileModel? = null,
    private val showBtnLogout: Boolean = false,
    private val btnLogoutListener: () -> Unit = {}
): BaseComponentBottomSheet<BottomSheetProfileBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> BottomSheetProfileBinding
        get() = BottomSheetProfileBinding::inflate

    override fun setupViews() {
        with(binding) {
            data?.let { user ->
                tvName.text = getString(R.string.label_hi_name_string, user.name?.toModelString() ?: "")
                tvEmail.text = getString(R.string.label_email_string, user.email)
                tvPhone.text = getString(R.string.label_phone_string, user.phone)
                user.address?.let { address ->
                    tvAddress.text =
                        getString(
                            R.string.label_address_string_int,
                            address.street.toCapitalize(),
                            address.number,
                            address.city.toCapitalize(),
                            address.zipcode
                        )
                }
                ibLogout.apply {
                    setVisibilty(showBtnLogout)
                    setOnSafeClickListener {
                        btnLogoutListener.invoke()
                    }
                }
            }
        }
    }
}