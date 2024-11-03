package com.avows.home.ui.home

import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.avows.configs.base.BaseActivityBinding
import com.avows.home.R
import com.avows.home.databinding.ActivityHomeBinding
import com.avows.home.ui.home.adapter.CategorySpinnerAdapter
import com.avows.home.ui.home.adapter.ProductAdapter
import com.avows.home.ui.home.bottom_sheet.ProfileBottomSheet
import com.avows.navigation.AuthNavigation
import com.avows.navigation.HomeNavigation
import com.avows.utility.extensions.setOnSafeClickListener
import com.avows.utility.extensions.toCapitalize
import com.avows.utility.extensions.toastShortExt
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : BaseActivityBinding<ActivityHomeBinding>() {

    @Inject
    lateinit var authNavigation: AuthNavigation

    @Inject
    lateinit var homeNavigation: HomeNavigation

    private val viewModel: HomeViewModel by viewModels()

    private val productAdapter: ProductAdapter by lazy {
        ProductAdapter { item ->
            homeNavigation.navigateToDetailProduct(
                activity = this@HomeActivity,
                product = item
            )
        }
    }

    override val bindingInflater: (LayoutInflater) -> ActivityHomeBinding
        get() = ActivityHomeBinding::inflate

    private lateinit var bsProfile: ProfileBottomSheet

    private fun observeProfile() {
        lifecycleScope.launch {
            launch {
                viewModel.resultProfilePref.collectLatest { result ->
                    result.onSuccess { data ->
                        data?.let { profile ->
                            bsProfile = ProfileBottomSheet(
                                data = profile,
                                showBtnLogout = true,
                                btnLogoutListener = {
                                    viewModel.logout()
                                }
                            )

                            binding.tvName.text = profile.name?.toModelString()
                            binding.ibProfile.setOnSafeClickListener {
                                bsProfile.show(supportFragmentManager, BS_PROFILE)
                            }
                        }
                    }
                }
            }

            launch {
                viewModel.resultLogout.collectLatest { result ->
                    result.onSuccess {
                        authNavigation.navigateToLoginPage(
                            activity = this@HomeActivity,
                            finishActivity = true
                        )
                    }
                }
            }
        }
    }

    private fun observeCategories() {
        lifecycleScope.launch {
            launch {
                viewModel.resultCategories.collectLatest { result ->
                    result.onSuccess { data ->
                        data?.let { item ->
                            val itemsWithPlaceholder = listOf(
                                getString(R.string.label_select_category)
                            ).plus(
                                item.map { it.toCapitalize() }
                            )
                            initSpinner(itemsWithPlaceholder)
                        }
                    }.onError { error ->
                        handleErrorApiState(error) {
                            toastShortExt(it.message)
                        }
                    }
                }
            }
        }
    }

    private fun observeProducts() {
        lifecycleScope.launch {
            launch {
                viewModel.resultAllProduct.collectLatest { result ->
                    result.onSuccess { data ->
                        data?.let { listProduct ->
                            productAdapter.updateData(listProduct)
                        }
                    }.onError { error ->
                        handleErrorDefault(error)
                    }
                }
            }
        }
    }

    private fun initObserver() {
        observeProfile()
        observeCategories()
        observeProducts()
    }

    override fun setupViews() {
        with(binding) {
            initObserver()
            rvProducts.adapter = productAdapter

            ibCart.setOnSafeClickListener {
                homeNavigation.navigateToCartPage(this@HomeActivity)
            }
        }
    }

    private fun initSpinner(data: List<String>) {
        with(binding) {
            val adapter = CategorySpinnerAdapter(this@HomeActivity, data) { selectedCategory ->
                if (selectedCategory != getString(R.string.label_select_category))
                    productAdapter.filterByCategory(
                    category = selectedCategory.lowercase()
                )
            }
            spinCategories.adapter = adapter
            spinCategories.onItemSelectedListener = adapter
        }
    }

    companion object {
        const val BS_PROFILE = "Profile"
    }
}