package com.avows.home.ui.cart

import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.avows.configs.base.BaseActivityBinding
import com.avows.domain.db.model.BillEntityDomain
import com.avows.domain.db.model.ListProductEntityDomain
import com.avows.home.R
import com.avows.home.databinding.ActivityCartBinding
import com.avows.home.ui.cart.adapter.CartAdapter
import com.avows.navigation.HomeNavigation
import com.avows.utility.extensions.setOnSafeClickListener
import com.avows.utility.extensions.toastShortExt
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CartActivity : BaseActivityBinding<ActivityCartBinding>() {

    @Inject
    lateinit var homeNavigation: HomeNavigation

    private val viewModel: CartViewModel by viewModels()

    override val bindingInflater: (LayoutInflater) -> ActivityCartBinding
        get() = ActivityCartBinding::inflate

    private val cartAdapter: CartAdapter by lazy {
        CartAdapter(
            listData = arrayListOf(),
            onPlusClicked = { (productId, qty) ->
                viewModel.updateCartItemQty(productId, qty)
            },
            onMinusClicked = { (productId, qty) ->
                viewModel.updateCartItemQty(productId, qty)
            },
            onDeleteClicked = {
                viewModel.deleteCartItemById(it)
            }
        )
    }

    private fun observeCart() {
        lifecycleScope.launch {
            launch {
                viewModel.resultGetCartData.collectLatest { result ->
                    result.onSuccess { data ->
                        cartAdapter.updateData(data)
                    }.onError { error ->
                        handleErrorDB(error)
                    }
                }
            }

            launch {
                viewModel.resultUpdateCartItemQty.collectLatest { result ->
                    result.onSuccess { productId ->
                        viewModel.getCartItemByProductId(productId)
                    }.onError { error ->
                        handleErrorDB(error)
                    }
                }
            }

            launch {
                viewModel.resultGetCartItemByProductId.collectLatest { result ->
                    result.onSuccess { data ->
                        data?.let { item ->
                            cartAdapter.updateItem(item)
                        }
                    }.onError { error ->
                        handleErrorDB(error)
                    }
                }
            }

            launch {
                viewModel.resultDeleteCartItemById.collectLatest { result ->
                    result.onSuccess {
                        viewModel.getCartData()
                        toastShortExt(getString(R.string.label_product_successfully_deleted))
                    }.onError { error ->
                        handleErrorDB(error)
                    }
                }
            }

            launch {
                viewModel.resultClearAllCartItem.collectLatest { result ->
                    result.onSuccess {

                    }.onError { error ->
                        handleErrorDB(error)
                    }
                }
            }
        }
    }

    private fun observeBill() {
        lifecycleScope.launch {
            launch {
                viewModel.resultInsertBill.collectLatest { result ->
                    result.onSuccess { (billId, totalPrice) ->
                        viewModel.insertListProduct(
                            cartAdapter.getCurrentData().map {
                                ListProductEntityDomain(
                                    listProductId = null,
                                    billId = billId,
                                    productId = it.productId,
                                    title = it.title,
                                    category = it.category,
                                    description = it.description,
                                    image = it.image,
                                    price = it.price,
                                    qty = it.qty,
                                    totalPrice = totalPrice
                                )
                            }
                        )
                    }.onError { error ->
                        handleErrorDefault(error)
                    }
                }
            }

            launch {
                viewModel.resultInsertListProduct.collectLatest { result ->
                    result.onSuccess {
                        viewModel.clearAllCartItem()
                        homeNavigation.navigateToSummary(
                            activity = this@CartActivity,
                            data = cartAdapter.getCurrentData()
                        )
                    }.onError { error ->
                        handleErrorDefault(error)
                    }
                }
            }
        }
    }

    override fun setupViews() {
        with(binding) {
            observeCart()
            observeBill()
            rvProducts.adapter = cartAdapter
            ibBack.setOnSafeClickListener {
                onBackPressedDispatcher.onBackPressed()
            }

            btnAddToCart.setOnSafeClickListener {
                val currentData = cartAdapter.getCurrentData()
                viewModel.insertBill(
                    BillEntityDomain(
                        billId = null,
                        dateTime = "",
                        itemSold = currentData.sumOf { it.qty },
                        totalPrice = currentData.sumOf { it.price * it.qty }
                    )
                )
            }
        }
    }
}