package com.avows.home.ui.product_detail

import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.avows.configs.base.BaseActivityBinding
import com.avows.domain.db.model.CartEntityDomain
import com.avows.domain.home.model.response.ProductDomain
import com.avows.home.R
import com.avows.home.databinding.ActivityProductDetailBinding
import com.avows.utility.consts.ExtraConst
import com.avows.utility.extensions.getFormattedNumber
import com.avows.utility.extensions.loadImage
import com.avows.utility.extensions.parcelable
import com.avows.utility.extensions.setOnSafeClickListener
import com.avows.utility.extensions.toCapitalize
import com.avows.utility.extensions.toStringFormat
import com.avows.utility.extensions.toastShortExt
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductDetailActivity : BaseActivityBinding<ActivityProductDetailBinding>() {

    private val viewModel: ProductDetailViewModel by viewModels()

    override val bindingInflater: (LayoutInflater) -> ActivityProductDetailBinding
        get() = ActivityProductDetailBinding::inflate

    private val product: ProductDomain? by lazy {
        intent.parcelable(ExtraConst.EXTRA_PRODUCT)
    }

    private var newQty: Int = 1

    private fun observeViewModel() {
        lifecycleScope.launch {
            launch {
                viewModel.resultInsertCart.collectLatest { result ->
                    result.onSuccess {
                        toastShortExt(getString(R.string.label_product_added_to_cart))
                    }.onError {
                        product?.id?.let {
                            viewModel.getCartItemByProductId(it)
                        }
                    }
                }
            }

            launch {
                viewModel.resultGetCartItemByProductId.collectLatest { result ->
                    result.onSuccess { data ->
                        data?.let { item ->
                            viewModel.updateCartQty(
                                productId = item.productId,
                                newQty = item.qty.plus(newQty)
                            )
                        }
                    }.onError { error ->
                        handleErrorDB(error)
                    }
                }
            }

            launch {
                viewModel.resultUpdateCartQty.collectLatest { result ->
                    result.onSuccess {
                        newQty = 1
                        binding.tvCount.text = newQty.toStringFormat()
                        toastShortExt(getString(R.string.label_product_added_to_cart))
                    }.onError { error ->
                        handleErrorDB(error)
                    }
                }
            }
        }
    }

    override fun setupViews() {
        with(binding) {
            observeViewModel()
            ibBack.setOnSafeClickListener {
                onBackPressedDispatcher.onBackPressed()
            }

            product?.let { item ->
                ivProduct.loadImage(
                    url = item.image,
                    errorDrawable = R.drawable.ic_box,
                    placeholderDrawable = R.drawable.ic_load
                )
                tvProductName.text = item.title
                tvCategory.text = item.category.toCapitalize()
                tvPrice.text = item.price.getFormattedNumber()
                tvDescription.text = item.description

                ibPlus.setOnClickListener {
                    val currentCount = tvCount.text.toString().toIntOrNull() ?: 1
                    newQty = currentCount.plus(1)
                    tvCount.text = newQty.toStringFormat()
                }

                ibMinus.setOnClickListener {
                    val currentCount = tvCount.text.toString().toIntOrNull() ?: 1
                    if (currentCount > 1) {
                        newQty = currentCount.minus(1)
                        tvCount.text = newQty.toStringFormat()
                    }
                }

                btnAddToCart.setOnSafeClickListener {
                    viewModel.insertCart(
                        CartEntityDomain(
                            productId = item.id,
                            title = item.title,
                            category = item.category,
                            description = item.description,
                            image = item.image,
                            price = item.price,
                            qty = newQty
                        )
                    )
                }
            }
        }
    }
}