package com.thejuki.kformmaster.renderer

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.ViewGroup

import com.thejuki.kformmaster.R
import com.thejuki.kformmaster.holder.FormPickerHolder
import com.thejuki.kformmaster.model.FormTextViewElement

/**
 * Form TextView Renderer
 *
 * Renderer for FormTextViewElement
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormTextViewRenderer(type: Int, context: Context) : BaseFormRenderer<FormTextViewElement<*>, FormPickerHolder>(type, context) {

    override fun bindView(formElement: FormTextViewElement<*>, holder: FormPickerHolder) {
        super.bindView(formElement, holder)

        holder.mEditTextValue.setText(formElement.valueAsString)
        holder.mEditTextValue.hint = formElement.mHint ?: ""
        holder.mEditTextValue.isEnabled = false
        holder.mEditTextValue.setTextColor(ContextCompat.getColor(context, R.color.colorFormMasterElementTextDisabled))
        holder.mEditTextValue.isFocusable = false
    }

    override fun createViewHolder(parent: ViewGroup?): FormPickerHolder {
        return FormPickerHolder(inflate(R.layout.form_element, parent))
    }
}
