package com.avows.configs.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.google.android.material.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseComponentBottomSheet<VIEW_BINDING: ViewBinding>: BottomSheetDialogFragment(){

    private var bind: VIEW_BINDING? = null
    protected val binding: VIEW_BINDING
        get() = bind ?: throw IllegalStateException("Binding is only valid between onCreateView and onDestroyView")

    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VIEW_BINDING

    abstract fun setupViews()

    override fun onStart() {
        super.onStart()
        dialog?.let { dialog ->
            val bottomSheet = dialog.findViewById<View>(R.id.design_bottom_sheet)
            bottomSheet?.let { bs ->
                val behavior = BottomSheetBehavior.from(bs)
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
                behavior.isHideable = true
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bind = bindingInflater(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupViews()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bind = null
    }
}