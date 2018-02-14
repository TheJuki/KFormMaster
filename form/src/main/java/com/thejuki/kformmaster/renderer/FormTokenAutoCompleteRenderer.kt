package com.thejuki.kformmaster.renderer

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import com.thejuki.kformmaster.R
import com.thejuki.kformmaster.helper.FormBuildHelper
import com.thejuki.kformmaster.holder.FormTokenAutoCompleteHolder
import com.thejuki.kformmaster.model.FormTokenAutoCompleteElement
import com.tokenautocomplete.TokenCompleteTextView

/**
 * Form TokenAutoComplete Renderer
 *
 * Renderer for FormTokenAutoCompleteHolder
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormTokenAutoCompleteRenderer(type: Int, context: Context, private val formBuilder: FormBuildHelper) : BaseFormRenderer<FormTokenAutoCompleteElement<*>, FormTokenAutoCompleteHolder>(type, context) {

    override fun bindView(formElement: FormTokenAutoCompleteElement<*>, holder: FormTokenAutoCompleteHolder) {
        super.bindView(formElement, holder)

        if (formElement.valueAsString != formElement.typedString) { // If the typedString is not one of the values, keep it
            holder.mEditTextValue!!.setText(formElement.typedString)
        } else {
            holder.mEditTextValue!!.setText(formElement.valueAsString)
        }
        holder.mEditTextValue!!.hint = formElement.mHint ?: ""

        setEditTextFocusEnabled(holder)

        val itemsAdapter = if (formElement.arrayAdapter != null)
            formElement.arrayAdapter
        else
            ArrayAdapter(context, android.R.layout.simple_list_item_1, formElement.mOptions)
        holder.mEditTextValue!!.setAdapter<ArrayAdapter<*>>(itemsAdapter)

        if (formElement.dropdownWidth != null) {
            holder.mEditTextValue!!.dropDownWidth = formElement.dropdownWidth!!
        }

        holder.mEditTextValue!!.setTokenClickStyle(TokenCompleteTextView.TokenClickStyle.Select)
        holder.mEditTextValue!!.allowDuplicates(false)

        holder.mEditTextValue!!.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                holder.mTextViewTitle!!.setTextColor(ContextCompat.getColor(context,
                        R.color.colorFormMasterElementFocusedTitle))
            } else {
                holder.mTextViewTitle!!.setTextColor(ContextCompat.getColor(context,
                        R.color.colorFormMasterElementTextTitle))
                formElement.setValue(holder.mEditTextValue!!.objects)
                formElement.setError(null)
                setError(holder, null)

                formBuilder.onValueChanged(formElement)
            }
        }

    }

    /**
     * brings focus when clicked on the whole container
     *
     * @param holder
     */
    private fun setEditTextFocusEnabled(holder: FormTokenAutoCompleteHolder) {
        holder.itemView.setOnClickListener {

            holder.mEditTextValue!!.requestFocus()
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(holder.mEditTextValue, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    override fun createViewHolder(parent: ViewGroup?): FormTokenAutoCompleteHolder {
        val holder = FormTokenAutoCompleteHolder(inflate(R.layout.form_element_token_auto_complete, parent))

        return holder
    }
}
