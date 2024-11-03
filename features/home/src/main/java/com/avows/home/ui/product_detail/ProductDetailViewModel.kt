package com.avows.home.ui.product_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avows.configs.state.ResultState
import com.avows.domain.db.model.CartEntityDomain
import com.avows.domain.db.usecase.cart.GetCartItemByProductIdUsecase
import com.avows.domain.db.usecase.cart.InsertCartUsecase
import com.avows.domain.db.usecase.cart.UpdateCartItemQty
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val insertCartUsecase: InsertCartUsecase,
    private val getCartItemByProductIdUsecase: GetCartItemByProductIdUsecase,
    private val updateCartItemQty: UpdateCartItemQty
) : ViewModel() {

    private val _resultInsertCart by lazy { MutableSharedFlow<ResultState<Long>>() }
    val resultInsertCart: SharedFlow<ResultState<Long>> = _resultInsertCart

    fun insertCart(
        request: CartEntityDomain
    ) {
        viewModelScope.launch {
            insertCartUsecase.execute(InsertCartUsecase.RequestValues(request)).result.let {
                _resultInsertCart.emit(it)
            }
        }
    }

    private val _resultGetCartItemByProductId by lazy { MutableSharedFlow<ResultState<CartEntityDomain?>>() }
    val resultGetCartItemByProductId: SharedFlow<ResultState<CartEntityDomain?>> = _resultGetCartItemByProductId

    fun getCartItemByProductId(
        productId: Int,
    ) {
        viewModelScope.launch {
            getCartItemByProductIdUsecase.execute(
                GetCartItemByProductIdUsecase.RequestValues(productId)
            ).result.let {
                _resultGetCartItemByProductId.emit(it)
            }
        }
    }

    private val _resultUpdateCartQty by lazy { MutableSharedFlow<ResultState<Int>>() }
    val resultUpdateCartQty: SharedFlow<ResultState<Int>> = _resultUpdateCartQty

    fun updateCartQty(
        productId: Int,
        newQty: Int
    ) {
        viewModelScope.launch {
            updateCartItemQty.execute(UpdateCartItemQty.RequestValues(
                productId = productId, newQty = newQty
            )).result.let {
                _resultUpdateCartQty.emit(it)
            }
        }
    }
}