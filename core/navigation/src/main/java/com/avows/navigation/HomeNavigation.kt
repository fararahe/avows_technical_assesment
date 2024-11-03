package com.avows.navigation

import android.app.Activity
import com.avows.domain.db.model.CartEntityDomain
import com.avows.domain.home.model.response.ProductDomain

interface HomeNavigation {

    fun navigateToHome(
        activity: Activity,
        finishActivity: Boolean,
    )

    fun navigateToDetailProduct(
        activity: Activity,
        product: ProductDomain
    )

    fun navigateToCartPage(
        activity: Activity,
    )

    fun navigateToSummary(
        activity: Activity,
        data: List<CartEntityDomain>
    )

}