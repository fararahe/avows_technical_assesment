package com.avows.auth.ui

import android.os.Build
import android.view.LayoutInflater
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import com.avows.auth.BuildConfig
import com.avows.auth.R
import com.avows.auth.databinding.ActivityLoginBinding
import com.avows.auth.util.PermissionHandler
import com.avows.configs.base.BaseActivityBinding
import com.avows.navigation.HomeNavigation
import com.avows.utility.extensions.setOnSafeClickListener
import com.avows.utility.extensions.toastShortExt
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : BaseActivityBinding<ActivityLoginBinding>() {

    @Inject
    lateinit var homeNavigation: HomeNavigation

    @Inject
    lateinit var permissionHandler: PermissionHandler

    private val viewModel: LoginViewModel by viewModels()

    override val bindingInflater: (LayoutInflater) -> ActivityLoginBinding
        get() = ActivityLoginBinding::inflate

    private val requestPermissionLauncher: ActivityResultLauncher<String> by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                if (isGranted) return@registerForActivityResult

                if (permissionHandler.isShouldShowPermissionRationale(this@LoginActivity)) { // show rationale why shouldn't be ignored
                    permissionHandler.showPermissionRationale(
                        this@LoginActivity,
                        requestPermissionLauncher
                    )
                } else {
                    permissionHandler.showPermissionDeniedPermanentlyRationale(this@LoginActivity)
                }
            }
        } else {
            throw IllegalStateException(getString(R.string.label_error_min_api_33))
        }
    }

    private fun observeLogin() {
        lifecycleScope.launch {
            launch {
                viewModel.resultLogin.collectLatest { result ->
                    result.onSuccess { data ->
                        data?.let {
                            viewModel.getProfile(it.token)
                        }
                    }.onError { error ->
                        handleErrorApiState(error) {
                            toastShortExt(it.message)
                        }
                    }
                }
            }

            launch {
                viewModel.resultGetProfile.collectLatest { result ->
                    result.onSuccess {
                        homeNavigation.navigateToHome(
                            activity = this@LoginActivity,
                            finishActivity = true
                        )
                    }.onError { error ->
                        handleErrorApiState(error) {
                            toastShortExt(it.message)
                        }
                    }
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun setupViews() {
        with(binding) {
            requestNotificationPermission()
            observeLogin()
            tvVersion.text = getString(R.string.label_version_string, BuildConfig.version_name)

            ftfUsername.getTextInputCorrect {
                validateLogin()
            }

            ftfPassword.getTextInputCorrect {
                validateLogin()
            }

            btnLogin.setOnSafeClickListener {
                viewModel.postLogin(
                    username = ftfUsername.getText(),
                    password = ftfPassword.getText()
                )
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun requestNotificationPermission() {
        permissionHandler.requestNotificationPermission(this@LoginActivity, requestPermissionLauncher)
    }

    private fun validateLogin() {
        with(binding) {
            val isValidUsername = ftfUsername.getTextInputStatus()
            val isValidPassword = ftfPassword.getTextInputStatus()
            btnLogin.isEnabled = isValidUsername && isValidPassword
        }
    }
}