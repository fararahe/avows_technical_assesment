package com.avows.home.ui.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avows.configs.state.ResultState
import com.avows.domain.db.model.BillEntityDomain
import com.avows.domain.db.model.CartEntityDomain
import com.avows.domain.db.model.ListProductEntityDomain
import com.avows.domain.db.usecase.bill.InsertBillUsecase
import com.avows.domain.db.usecase.cart.ClearAllCartItemsUsecase
import com.avows.domain.db.usecase.cart.DeleteCartItemByIdUsecase
import com.avows.domain.db.usecase.cart.GetCartDataUsecase
import com.avows.domain.db.usecase.cart.GetCartItemByProductIdUsecase
import com.avows.domain.db.usecase.cart.UpdateCartItemQty
import com.avows.domain.db.usecase.list_product.InsertListProductUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val getCartDataUsecase: GetCartDataUsecase,
    private val updateCartItemQty: UpdateCartItemQty,
    private val getCartItemByProductIdUsecase: GetCartItemByProductIdUsecase,
    private val deleteCartItemByIdUsecase: DeleteCartItemByIdUsecase,
    private val insertBillUsecase: InsertBillUsecase,
    private val insertListProductUsecase: InsertListProductUsecase,
    private val clearAllCartItemsUsecase: ClearAllCartItemsUsecase
) : ViewModel() {

    init {
        getCartData()
    }

    private val _resultGetCartData by lazy { MutableSharedFlow<ResultState<List<CartEntityDomain>>>() }
    val resultGetCartData: SharedFlow<ResultState<List<CartEntityDomain>>> = _resultGetCartData

    fun getCartData() {
        viewModelScope.launch {
            getCartDataUsecase.execute(GetCartDataUsecase.RequestValues()).result.let {
                _resultGetCartData.emit(it)
            }
        }
    }

    private val _resultUpdateCartItemQty by lazy { MutableSharedFlow<ResultState<Int>>() }
    val resultUpdateCartItemQty: SharedFlow<ResultState<Int>> = _resultUpdateCartItemQty

    fun updateCartItemQty(
        productId: Int,
        newQty: Int
    ) {
        viewModelScope.launch {
            updateCartItemQty.execute(
                UpdateCartItemQty.RequestValues(
                    productId = productId,
                    newQty = newQty
                )
            ).result.let {
                _resultUpdateCartItemQty.emit(it)
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

    private val _resultDeleteCartItemById by lazy { MutableSharedFlow<ResultState<Unit>>() }
    val resultDeleteCartItemById: SharedFlow<ResultState<Unit>> = _resultDeleteCartItemById

    fun deleteCartItemById(
        productId: Int
    ) {
        viewModelScope.launch {
            deleteCartItemByIdUsecase.execute(
                DeleteCartItemByIdUsecase.RequestValues(productId)
            ).result.let {
                _resultDeleteCartItemById.emit(it)
            }
        }
    }

    private val _resultInsertBill by lazy { MutableSharedFlow<ResultState<Pair<Long, Double>>>() }
    val resultInsertBill: SharedFlow<ResultState<Pair<Long, Double>>> = _resultInsertBill

    fun insertBill(
        bill: BillEntityDomain
    ) {
        viewModelScope.launch {
            insertBillUsecase.execute(
                InsertBillUsecase.RequestValues(bill)
            ).result.let {
                _resultInsertBill.emit(it)
            }
        }
    }

    private val _resultInsertListProduct by lazy { MutableSharedFlow<ResultState<Unit>>() }
    val resultInsertListProduct: SharedFlow<ResultState<Unit>> = _resultInsertListProduct

    fun insertListProduct(
        list: List<ListProductEntityDomain>
    ) {
        viewModelScope.launch {
            insertListProductUsecase.execute(
                InsertListProductUsecase.RequestValues(list)
            ).result.let {
                _resultInsertListProduct.emit(it)
            }
        }
    }

    private val _resultClearCartAllItem by lazy { MutableSharedFlow<ResultState<Unit>>() }
    val resultClearAllCartItem: SharedFlow<ResultState<Unit>> = _resultClearCartAllItem

    fun clearAllCartItem() {
        viewModelScope.launch {
            clearAllCartItemsUsecase.execute(
                ClearAllCartItemsUsecase.RequestValues()
            ).result.let {
                _resultClearCartAllItem.emit(it)
            }
        }
    }
}