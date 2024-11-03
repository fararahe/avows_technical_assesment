package com.avows.home.navigation

import android.app.Activity
import android.content.Intent
import com.avows.domain.db.model.CartEntityDomain
import com.avows.domain.home.model.response.ProductDomain
import com.avows.home.ui.cart.CartActivity
import com.avows.home.ui.home.HomeActivity
import com.avows.home.ui.product_detail.ProductDetailActivity
import com.avows.home.ui.order_summary.OrderSummaryActivity
import com.avows.navigation.HomeNavigation
import com.avows.utility.consts.ExtraConst
import com.avows.utility.extensions.startActivityExt
import javax.inject.Inject

class HomeNavigationImpl @Inject constructor(): HomeNavigation {

    override fun navigateToHome(activity: Activity, finishActivity: Boolean) {
        activity.startActivityExt(
            HomeActivity::class.java,
            finishCallingActivity = finishActivity,
        ) {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        }
    }

    override fun navigateToDetailProduct(activity: Activity, product: ProductDomain) {
        activity.startActivityExt(
            ProductDetailActivity::class.java
        ) {
            putExtra(ExtraConst.EXTRA_PRODUCT, product)
        }
    }

    override fun navigateToCartPage(activity: Activity) {
        activity.startActivityExt(
            CartActivity::class.java
        )
    }

    override fun navigateToSummary(activity: Activity, data: List<CartEntityDomain>) {
        activity.startActivityExt(
            OrderSummaryActivity::class.java,
            finishCallingActivity = true
        ) {
            putParcelableArrayListExtra(ExtraConst.EXTRA_PRODUCT, ArrayList(data))
        }
    }
}