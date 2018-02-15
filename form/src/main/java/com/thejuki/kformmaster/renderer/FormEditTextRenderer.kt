package com.thejuki.kformmaster.renderer

import android.content.Context
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.View.inflate
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.thejuki.kformmaster.R
import com.thejuki.kformmaster.helper.FormBuildHelper
import com.thejuki.kformmaster.holder.FormEditTextHolder
import com.thejuki.kformmaster.model.BaseFormElement
import com.thejuki.kformmaster.model.FormEditTextElement

/**
 * Form EditText Renderer
 *
 * Renderer for FormEditTextElement
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormEditTextRenderer(type: Int, context: Context, private val formBuilder: FormBuildHelper) : BaseFormRenderer<FormEditTextElement<*>, FormEditTextHolder>(type, context) {

    override fun bindView(formElement: FormEditTextElement<*>, holder: FormEditTextHolder) {
        super.bindView(formElement, holder)

        holder.mFormCustomEditTextListener.updateTag(formElement.getTag())

        holder.mEditTextValue!!.setText(formElement.valueAsString)
        holder.mEditTextValue!!.hint = formElement.mHint ?: ""

        setEditTextFocusEnabled(holder)

        holder.mEditTextValue!!.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                holder.mTextViewTitle!!.setTextColor(ContextCompat.getColor(context,
                        R.color.colorFormMasterElementFocusedTitle))
            } else {
                holder.mTextViewTitle!!.setTextColor(ContextCompat.getColor(context,
                        R.color.colorFormMasterElementTextTitle))
            }
        }

        when (formElement.getType()) {
            BaseFormElement.TYPE_EDITTEXT_TEXT_SINGLELINE -> holder.mEditTextValue!!.maxLines = 1
            BaseFormElement.TYPE_EDITTEXT_TEXT_MULTILINE -> {
                holder.mEditTextValue!!.setSingleLine(false)
                holder.mEditTextValue!!.maxLines = 4
            }
            BaseFormElement.TYPE_EDITTEXT_NUMBER -> holder.mEditTextValue!!.setRawInputType(InputType.TYPE_CLASS_NUMBER or InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS)
            BaseFormElement.TYPE_EDITTEXT_EMAIL -> holder.mEditTextValue!!.setRawInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS or InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS)
            BaseFormElement.TYPE_EDITTEXT_PHONE -> holder.mEditTextValue!!.setRawInputType(InputType.TYPE_CLASS_PHONE or InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS)
            BaseFormElement.TYPE_EDITTEXT_PASSWORD -> holder.mEditTextValue!!.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            else -> {
            }
        }
    }

    /**
     * brings focus when clicked on the whole container
     *
     * @param holder
     */
    private fun setEditTextFocusEnabled(holder: FormEditTextHolder) {
        holder.itemView.setOnClickListener {
            holder.mEditTextValue!!.requestFocus()
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            holder.mEditTextValue!!.setSelection(holder.mEditTextValue!!.text.length)
            imm.showSoftInput(holder.mEditTextValue, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    override fun createViewHolder(parent: ViewGroup?): FormEditTextHolder {
        val listener = FormCustomEditTextListener()
        val holder = FormEditTextHolder(inflate(R.layout.form_element, parent), listener)

        listener.updateViewHolder(holder)

        return holder
    }

    /**
     * Text watcher for Edit texts
     */
    inner class FormCustomEditTextListener : TextWatcher {
        private var formViewHolder: FormEditTextHolder? = null
        private var tag: Int = 0

        fun updateViewHolder(formViewHolder: FormEditTextHolder) {
            this.formViewHolder = formViewHolder
        }

        fun updateTag(tag: Int) {
            this.tag = tag
        }

        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i2: Int, i3: Int) {

        }

        override fun onTextChanged(charSequence: CharSequence, i: Int, i2: Int, i3: Int) {

            // get current form element, existing value and new value
            val formElement = formBuilder.getFormElement(tag)
            val currentValue = formElement!!.valueAsString
            val newValue = charSequence.toString()

            // trigger event only if the value is changed
            if (currentValue != newValue) {
                formElement.setValue(newValue)
                formElement.setError(null)
                setError(formViewHolder!!, null)

                formBuilder.onValueChanged(formElement)
            }
        }

        override fun afterTextChanged(editable: Editable) {

        }
    }
}
