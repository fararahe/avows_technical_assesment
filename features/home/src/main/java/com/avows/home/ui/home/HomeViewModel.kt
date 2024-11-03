package com.avows.home.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avows.configs.state.ResultState
import com.avows.domain.auth.usecase.LogoutUsecase
import com.avows.domain.home.model.response.ProductDomain
import com.avows.domain.home.usecase.category.CategoriesUsecase
import com.avows.domain.home.usecase.category.ResetCategoryFlagUsecase
import com.avows.domain.home.usecase.product.AllProductUsecase
import com.avows.domain.home.usecase.profile.GetProfilePrefUsecase
import com.avows.shared_model.ProfileModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getProfilePrefUsecase: GetProfilePrefUsecase,
    private val categoriesUsecase: CategoriesUsecase,
    private val allProductUsecase: AllProductUsecase,
    private val resetCategoryFlagUsecase: ResetCategoryFlagUsecase,
    private val logoutUsecase: LogoutUsecase
) : ViewModel() {

    init {
        resetCategoryFlag()
        getProfilePref()
        getCategories()
        getAllProduct()
    }

    private val _resultProfilePref by lazy { MutableSharedFlow<ResultState<ProfileModel?>>() }
    val resultProfilePref: SharedFlow<ResultState<ProfileModel?>> = _resultProfilePref

    private fun getProfilePref() {
        viewModelScope.launch {
            getProfilePrefUsecase.execute(GetProfilePrefUsecase.RequestValues()).result.let {
                _resultProfilePref.emit(it)
            }
        }
    }

    private val _resultCategories by lazy { MutableSharedFlow<ResultState<List<String>?>>() }
    val resultCategories: SharedFlow<ResultState<List<String>?>> = _resultCategories

    private fun getCategories() {
        viewModelScope.launch {
            categoriesUsecase.execute(CategoriesUsecase.RequestValues()).result.let {
                _resultCategories.emit(it)
            }
        }
    }

    private val _resultAllProduct by lazy { MutableSharedFlow<ResultState<List<ProductDomain>?>>() }
    val resultAllProduct: SharedFlow<ResultState<List<ProductDomain>?>> get()  = _resultAllProduct

    private fun getAllProduct() {
        viewModelScope.launch {
            allProductUsecase.execute(AllProductUsecase.RequestValues()).result.let {
                _resultAllProduct.emit(it)
            }
        }
    }

    private fun resetCategoryFlag() {
        viewModelScope.launch {
            resetCategoryFlagUsecase.execute(ResetCategoryFlagUsecase.RequestValues())
        }
    }

    private val _resultLogout by lazy { MutableSharedFlow<ResultState<Unit>>() }
    val resultLogout: SharedFlow<ResultState<Unit>> = _resultLogout

    fun logout() {
        viewModelScope.launch {
            logoutUsecase.execute(LogoutUsecase.RequestValues()).result.let {
                _resultLogout.emit(it)
            }
        }
    }
}