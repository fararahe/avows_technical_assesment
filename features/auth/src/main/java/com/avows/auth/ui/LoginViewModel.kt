package com.avows.auth.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avows.configs.state.ResultState
import com.avows.domain.auth.model.domain.LoginDomain
import com.avows.domain.auth.model.request.LoginRequest
import com.avows.domain.auth.usecase.LoginUsecase
import com.avows.domain.home.model.response.ProfileDomain
import com.avows.domain.home.usecase.profile.GetProfileUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUsecase: LoginUsecase,
    private val getProfileUsecase: GetProfileUsecase
) : ViewModel() {

    private val _resultLogin by lazy { MutableSharedFlow<ResultState<LoginDomain?>>() }
    val resultLogin: SharedFlow<ResultState<LoginDomain?>> =
        _resultLogin

    fun postLogin(username: String, password: String) {
        val request = LoginRequest(
            username = username,
            password = password
        )

        viewModelScope.launch {
            loginUsecase.execute(LoginUsecase.RequestValues(request)).result.let {
                _resultLogin.emit(it)
            }
        }
    }

    private val _resultGetProfile by lazy { MutableSharedFlow<ResultState<ProfileDomain?>>() }
    val resultGetProfile: SharedFlow<ResultState<ProfileDomain?>> =
        _resultGetProfile

    fun getProfile(
        token: String
    ) {
        viewModelScope.launch {
            getProfileUsecase.execute(GetProfileUsecase.RequestValues(token)).result.let {
                _resultGetProfile.emit(it)
            }
        }
    }
}