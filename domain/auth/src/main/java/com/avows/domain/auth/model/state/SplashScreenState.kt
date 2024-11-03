package com.avows.domain.auth.model.state

sealed class SplashScreenState {
    data object ToLogin : SplashScreenState()

    data object ToMain : SplashScreenState()
}