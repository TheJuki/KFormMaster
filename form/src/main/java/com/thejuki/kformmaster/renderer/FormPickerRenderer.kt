package com.thejuki.kformmaster.renderer

import android.app.Dialog
import android.content.Context
import android.view.View
import android.view.ViewGroup

import com.thejuki.kformmaster.R
import com.thejuki.kformmaster.helper.FormBuildHelper
import com.thejuki.kformmaster.holder.FormPickerHolder
import com.thejuki.kformmaster.model.FormPickerElement

/**
 * Form Picker Renderer
 *
 * Base Renderer for FormPickerElement
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
open class FormPickerRenderer<M : FormPickerElement<*>>(type: Int, context: Context, val formBuilder: FormBuildHelper) : BaseFormRenderer<M, FormPickerHolder>(type, context) {
    var clickedTag: Int = 0

    override fun bindView(formElement: M, holder: FormPickerHolder) {
        super.bindView(formElement, holder)

        holder.mEditTextValue.setText(formElement.valueAsString)
        holder.mEditTextValue.hint = formElement.mHint ?: ""
    }

    fun setOnClickForHolder(holder: FormPickerHolder, tag: Int, d: Dialog) {
        holder.mEditTextValue.isFocusable = false

        // display the dialog on click
        val listener = View.OnClickListener {
            clickedTag = tag
            d.show()
        }

        holder.itemView.setOnClickListener(listener)
        holder.mEditTextValue.setOnClickListener(listener)
    }

    override fun createViewHolder(parent: ViewGroup?): FormPickerHolder {
        return FormPickerHolder(inflate(R.layout.form_element, parent))
    }
}