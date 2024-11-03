package com.avows.shared_resource.form

import android.content.Context
import android.content.res.TypedArray
import android.text.Editable
import android.text.InputFilter
import android.text.InputType
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import com.avows.shared_resource.R
import com.avows.utility.extensions.gone
import com.avows.utility.extensions.validateEmail
import com.avows.utility.extensions.validatePassword
import com.avows.utility.extensions.visible
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class FormTextField @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : LinearLayoutCompat(context, attrs, defStyleAttr) {
    // region View
    private val viewTitle: TextView
    private val viewInputLayout: TextInputLayout
    private val viewInput: TextInputEditText
    private val viewHelper: TextView
    // endregion View

    // region Attributes
    private var titleLabelText: String? = null
    private var mMaxLines: Int = -1
    private var mMaxLengths: Int = MAX_LENGTH_FIELD
    private var mLines: Int = -1
    private var mHints: String? = null
    private var helperText: String? = null
    private var showHelperText: Boolean = false
    private var minimalWord: Int = MIN_WORD
    private var isInputTypeFormat: InputTypeFormat = InputTypeFormat.TEXT
    // endregion Attributes

    // region Text
    private var onTextInputCorrect: (Boolean) -> Unit = {}
    private var onUpdateText: () -> Unit = {}
    private var onTextInputCorrectState: Boolean = false
    // endregion Text

    private var textWatcher: FormTextWatcher? = null

    enum class InputTypeFormat {
        TEXT,
        PASSWORD,
        EMAIL,
    }

    init {
        View.inflate(context, R.layout.form_text_field, this)
        viewTitle = findViewById(R.id.tvLabel)
        viewInputLayout = findViewById(R.id.tilUsername)
        viewInput = findViewById(R.id.etUsername)
        viewHelper = findViewById(R.id.tvHelper)
        setAttributes(attrs)
    }

    private fun setAttributes(attrs: AttributeSet?) {
        context.obtainStyledAttributes(attrs, R.styleable.FormTextField).run {
            initAttributes(this)
            recycle()
        }
    }

    private fun initAttributes(array: TypedArray) {
        titleLabelText = array.getString(R.styleable.FormTextField_labelTitle)
        mHints = array.getString(R.styleable.FormTextField_android_hint).orEmpty()
        mMaxLines = array.getInt(R.styleable.FormTextField_android_maxLines, -1)
        mLines = array.getInt(R.styleable.FormTextField_android_lines, -1)
        mMaxLengths = array.getInt(R.styleable.FormTextField_android_maxLength, MAX_LENGTH_FIELD)
        minimalWord = array.getInt(R.styleable.FormTextField_minimalWord, MIN_WORD)
        showHelperText = array.getBoolean(R.styleable.FormTextField_displayHelper, showHelperText)
        val its: Array<InputTypeFormat> = InputTypeFormat.entries.toTypedArray()
        isInputTypeFormat = its[array.getInt(R.styleable.FormTextField_inputFormat, 0)]

        when(isInputTypeFormat) {
            InputTypeFormat.TEXT -> {
                viewInputLayout.endIconMode = TextInputLayout.END_ICON_NONE
            }

            InputTypeFormat.EMAIL -> {
                viewInputLayout.endIconMode = TextInputLayout.END_ICON_NONE
            }

            else -> {
                // Optional: implement default behavior here
            }
        }

        applyAttributes()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        when (isInputTypeFormat) {
            InputTypeFormat.TEXT -> viewInput.inputType = InputType.TYPE_CLASS_TEXT
            InputTypeFormat.EMAIL -> viewInput.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
            InputTypeFormat.PASSWORD -> {
                Unit
            }
        }
    }

    private fun applyAttributes() {
        viewTitle.text = titleLabelText

        with(viewInput) {
            if (mMaxLines > -1) maxLines = mMaxLines
            if (mLines > -1) setLines(mLines)
            if (mMaxLengths > -1) filters = arrayOf(InputFilter.LengthFilter(mMaxLengths))
        }

        setHintText(mHints.toString())
        setHelperText(helperText)
        initTextWatcher()
    }

    private fun setHintText(value: String) {
        viewInput.hint = value
    }

    private fun setHelperText(value: String?) {
        viewHelper.text = value
    }

    private fun initTextWatcher() {
        setTextWatcher(object : FormTextWatcher() {})
    }

    private fun setTextWatcher(textWatcher: FormTextWatcher) {
        this.textWatcher = textWatcher

        this.textWatcher =
            object : FormTextWatcher() {
                override fun afterTextChanged(s: Editable?) {
                    super.afterTextChanged(s)
                    val text = s.toString()
                    onUpdateText.invoke()

                    when (isInputTypeFormat) {
                        InputTypeFormat.EMAIL -> {
                            handleEmailInput(text, onTextInputCorrect)
                        }

                        else -> {
                            handleTextInput(text, onTextInputCorrect)
                        }
                    }
                }
            }

        viewInput.addTextChangedListener(this.textWatcher)
    }

    fun getText(): String = viewInput.text.toString()

    fun getTextInputCorrect(listener: (Boolean) -> Unit) {
        this.onTextInputCorrect = listener
    }

    fun getTextInputStatus(): Boolean {
        return this.onTextInputCorrectState
    }

    private fun handleTextInput(
        value: String,
        listener: (Boolean) -> Unit,
    ) {
        if (value.isBlank()) {
            setErrorHelper(listener = listener)
        } else if (value.length < minimalWord) {
            setErrorHelper(
                context.getString(R.string.label_min_word_int, minimalWord),
                listener,
            )
        } else {
            when (isInputTypeFormat) {
                InputTypeFormat.PASSWORD -> {
                    if (!value.validatePassword()) {
                        setErrorHelper(
                            context.getString(R.string.label_password_hint),
                            listener,
                        )
                    } else {
                        handleInputCorrect(listener)
                    }
                }

                InputTypeFormat.EMAIL -> {
                    handleEmailInput(value, listener)
                }

                else -> {
                    handleInputCorrect(listener)
                }
            }
        }
    }

    private fun handleInputCorrect(listener: (Boolean) -> Unit) {
        viewHelper.gone()
        onTextInputCorrectState = true
        listener.invoke(true)
    }

    private fun handleEmailInput(
        value: String,
        listener: (Boolean) -> Unit,
    ) {
        val validateEmail = value.validateEmail()
        if (validateEmail) {
            handleInputCorrect(listener)
        } else {
            setErrorHelper(context.getString(R.string.label_error_email_format), listener)
        }
    }

    private fun setErrorHelper(
        errorText: String? = null,
        listener: (Boolean) -> Unit,
    ) {
        viewHelper.visible()
        errorText?.let { error ->
            viewHelper.text = error
            viewHelper.setTextColor(
                ContextCompat.getColor(context, R.color.md_theme_onError)
            )
        }
        onTextInputCorrectState = false
        listener.invoke(false)
        return
    }

    companion object {
        const val MIN_WORD = 1
        const val MAX_LENGTH_FIELD = -1
    }
}