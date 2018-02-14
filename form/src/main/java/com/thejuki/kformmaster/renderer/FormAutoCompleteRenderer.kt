package com.thejuki.kformmaster.renderer

import android.content.Context
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.text.TextWatcher
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.thejuki.kformmaster.R
import com.thejuki.kformmaster.helper.FormBuildHelper
import com.thejuki.kformmaster.holder.FormAutoCompleteHolder
import com.thejuki.kformmaster.model.BaseFormElement
import com.thejuki.kformmaster.model.FormAutoCompleteElement

/**
 * Form AutoComplete Renderer
 *
 * Renderer for FormAutoCompleteElement
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormAutoCompleteRenderer(type: Int, context: Context, private val formBuilder: FormBuildHelper) : BaseFormRenderer<FormAutoCompleteElement<*>, FormAutoCompleteHolder>(type, context) {

    override fun bindView(formElement: FormAutoCompleteElement<*>, holder: FormAutoCompleteHolder) {
        super.bindView(formElement, holder)

        holder.mFormCustomEditTextListener.updateTag(formElement.getTag())
        if (formElement.valueAsString != formElement.typedString) { // If the typedString is not one of the values, keep it
            holder.mEditTextValue!!.setText(formElement.typedString)
        } else {
            holder.mEditTextValue!!.setText(formElement.valueAsString)
        }
        holder.mEditTextValue!!.hint = formElement.mHint ?: ""

        // Select all text when focused for easy removal
        holder.mEditTextValue!!.setSelectAllOnFocus(true)

        // Set threshold (the number of characters to type before the drop down is shown)
        holder.mEditTextValue!!.threshold = 1

        setEditTextFocusEnabled(holder)

        val itemsAdapter = if (formElement.arrayAdapter != null)
            formElement.arrayAdapter
        else
            ArrayAdapter(context, android.R.layout.simple_list_item_1, formElement.mOptions)
        holder.mEditTextValue!!.setAdapter<ArrayAdapter<*>>(itemsAdapter)

        if (formElement.dropdownWidth != null) {
            holder.mEditTextValue!!.dropDownWidth = formElement.dropdownWidth!!
        }

        // Support for custom adapter with custom options
        if (formElement.arrayAdapter != null) {
            holder.mEditTextValue!!.onItemClickListener = AdapterView.OnItemClickListener { adapterView, _, position, _ ->
                // Set options to selected option
                formElement.setResultOption(adapterView.getItemAtPosition(position))
                // Jump to beginning of text view
                holder.mEditTextValue!!.setSelection(0)
                // Cause onTextChanged to be called
                holder.mEditTextValue!!.text = holder.mEditTextValue!!.text
            }
        }

        holder.mEditTextValue!!.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                // Show dropdown on focus. Overrides threshold 0
                holder.mEditTextValue!!.showDropDown()

                holder.mTextViewTitle!!.setTextColor(ContextCompat.getColor(context,
                        R.color.colorFormMasterElementFocusedTitle))
            } else {
                holder.mTextViewTitle!!.setTextColor(ContextCompat.getColor(context,
                        R.color.colorFormMasterElementTextTitle))
            }
        }

    }

    /**
     * brings focus when clicked on the whole container
     *
     * @param holder
     */
    private fun setEditTextFocusEnabled(holder: FormAutoCompleteHolder) {
        holder.itemView.setOnClickListener {

            holder.mEditTextValue!!.requestFocus()
            holder.mEditTextValue!!.selectAll()
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(holder.mEditTextValue, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    override fun createViewHolder(parent: ViewGroup?): FormAutoCompleteHolder {
        val listener = FormCustomEditTextListener()
        val holder = FormAutoCompleteHolder(inflate(R.layout.form_element_auto_complete, parent), listener)

        listener.updateViewHolder(holder)

        return holder
    }

    /**
     * Text watcher for Edit texts
     */
    inner class FormCustomEditTextListener : TextWatcher {
        private var formViewHolder: FormAutoCompleteHolder? = null
        private var tag: Int = 0

        fun updateViewHolder(formViewHolder: FormAutoCompleteHolder) {
            this.formViewHolder = formViewHolder
        }

        fun updateTag(tag: Int) {
            this.tag = tag
        }

        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i2: Int, i3: Int) {

        }

        fun getElementWithString(element: BaseFormElement<*>, stringVal: String): Any? {

            return element.mOptions?.firstOrNull { it.toString() == stringVal }
        }

        override fun onTextChanged(charSequence: CharSequence, i: Int, i2: Int, i3: Int) {

            // get current form element, existing value and new value
            val formElement = formBuilder.getFormElement(tag) as FormAutoCompleteElement<*>?
            val newValue = charSequence.toString()

            formElement!!.typedString = newValue

            // trigger only if the value exists as one of the string options
            if (formElement.stringOptions.contains(newValue)) {
                formElement.setValue(getElementWithString(formElement, newValue))
                formElement.setError(null)
                setError(formViewHolder!!, null)

                formBuilder.onValueChanged(formElement)
            }

            // If field is blank set form value to null
            if (newValue.isBlank()) {
                formElement.setValue(null)
                formElement.setError(null)
                setError(formViewHolder!!, null)

                formBuilder.onValueChanged(formElement)
            }
        }

        override fun afterTextChanged(editable: Editable) {

        }
    }
}
