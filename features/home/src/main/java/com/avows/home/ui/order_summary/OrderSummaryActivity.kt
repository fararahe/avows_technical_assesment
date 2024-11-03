package com.avows.home.ui.order_summary

import android.view.LayoutInflater
import com.avows.configs.base.BaseActivityBinding
import com.avows.domain.db.model.CartEntityDomain
import com.avows.home.R
import com.avows.home.databinding.ActivityOrderSummaryBinding
import com.avows.navigation.HomeNavigation
import com.avows.utility.consts.ExtraConst
import com.avows.utility.extensions.getFormattedNumber
import com.avows.utility.extensions.parcelableArrayList
import com.avows.utility.extensions.setOnSafeClickListener
import com.avows.utility.extensions.toStringFormat
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OrderSummaryActivity : BaseActivityBinding<ActivityOrderSummaryBinding>() {

    @Inject
    lateinit var homeNavigation: HomeNavigation

    override val bindingInflater: (LayoutInflater) -> ActivityOrderSummaryBinding
        get() = ActivityOrderSummaryBinding::inflate

    private val listProduct: ArrayList<CartEntityDomain>? by lazy {
        intent.parcelableArrayList(ExtraConst.EXTRA_PRODUCT)
    }

    override fun setupViews() {
        with(binding) {
            listProduct?.toMutableList()?.let {
                val adapter = SummaryAdapter(it)
                rvSummary.adapter = adapter
                tvTotalPrice.text = getString(
                    R.string.label_total_price_string,
                    it.sumOf { it.price.times(it.qty) }.getFormattedNumber()
                )
                tvSoldItem.text =
                    getString(R.string.label_sold_item_string, it.sumOf { it.qty }.toStringFormat())
            }

            btnClose.setOnSafeClickListener {
                homeNavigation.navigateToHome(
                    activity = this@OrderSummaryActivity,
                    finishActivity = true
                )
            }
        }
    }
}