package com.avows.technicalassessment.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avows.configs.state.ResultState
import com.avows.domain.auth.model.state.SplashScreenState
import com.avows.domain.auth.usecase.SplashStateUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val splashStateUsecase: SplashStateUsecase
): ViewModel() {

    private val _resultValidateState by lazy { MutableSharedFlow<ResultState<SplashScreenState?>>() }
    val resultValidateState: SharedFlow<ResultState<SplashScreenState?>> = _resultValidateState

    fun validateState() {
        viewModelScope.launch {
            delay(2000)
            splashStateUsecase.execute(SplashStateUsecase.RequestValues()).result.let {
                _resultValidateState.emit(it)
            }
        }
    }
}