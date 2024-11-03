package com.avows.shared_resource.form

import android.text.Editable
import android.text.TextWatcher

abstract class FormTextWatcher : TextWatcher {
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        // Optional: implement default behavior here
    }

    override fun afterTextChanged(s: Editable?) {
        // Optional: implement default behavior here
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        // Optional: implement default behavior here
    }
}