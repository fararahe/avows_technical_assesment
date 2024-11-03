package com.avows.technicalassessment.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.avows.auth.BuildConfig
import com.avows.configs.base.BaseActivityBinding
import com.avows.domain.auth.model.state.SplashScreenState
import com.avows.navigation.AuthNavigation
import com.avows.navigation.HomeNavigation
import com.avows.technicalassessment.databinding.ActivitySplashScreenBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.avows.auth.R as SharedRes

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashScreenActivity : BaseActivityBinding<ActivitySplashScreenBinding>() {

    @Inject
    lateinit var authNavigation: AuthNavigation

    @Inject
    lateinit var homeNavigation: HomeNavigation

    private val viewModel: SplashScreenViewModel by viewModels()

    override val bindingInflater: (LayoutInflater) -> ActivitySplashScreenBinding
        get() = ActivitySplashScreenBinding::inflate

    private fun observeScreenState() {
        lifecycleScope.launch {
            launch {
                viewModel.resultValidateState.collectLatest { result ->
                    result.onSuccess { data ->
                        data?.let {
                            when(it) {
                                SplashScreenState.ToMain -> {
                                    homeNavigation.navigateToHome(
                                        activity = this@SplashScreenActivity,
                                        finishActivity = true)
                                }

                                SplashScreenState.ToLogin -> {
                                    authNavigation.navigateToLoginPage(
                                        activity = this@SplashScreenActivity,
                                        finishActivity = true)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    override fun setupViews() {
        observeScreenState()
        binding.tvVersion.text = getString(SharedRes.string.label_version_string, BuildConfig.version_name)
        viewModel.validateState()
    }
}