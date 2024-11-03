package com.avows.configs.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.avows.configs.exception.BadRequestException
import com.avows.configs.exception.ErrorApiStateHandler
import com.avows.configs.exception.provider.ErrorHandlerProvider
import com.avows.utility.extensions.toastShortExt
import javax.inject.Inject

abstract class BaseActivityBinding<VIEW_BINDING: ViewBinding>: AppCompatActivity() {

    @Inject
    lateinit var errorHandlerProvider: ErrorHandlerProvider

    private var bind: VIEW_BINDING? = null
    protected val binding: VIEW_BINDING
        get() = bind ?: throw IllegalStateException("Binding is only valid between onCreateView and onDestroyView")

    abstract val bindingInflater: (LayoutInflater) -> VIEW_BINDING


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = bindingInflater.invoke(LayoutInflater.from(this))
        setContentView(binding.root)
        setupViews()
    }

    protected abstract fun setupViews()

    protected fun handleErrorApiState(
        throwable: Throwable,
        onHandleableError: (error: BadRequestException) -> Unit,
    ) {
        ErrorApiStateHandler.handleErrorState(
            throwable = throwable,
            onHandleableException = onHandleableError,
            onGeneralException = {
                errorHandlerProvider.generalError(this, lifecycle, throwable)
            },
            onTokenExpired = { errorHandlerProvider.actionLogout(this, lifecycle) },
        )
    }

    protected fun handleErrorDefault(error: Throwable) {
        handleErrorApiState(error) { message ->
            toastShortExt(message.message)
        }
    }

    protected fun handleErrorDB(error: Throwable) {
        toastShortExt(error.message.toString())
    }
}