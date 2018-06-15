package com.thejuki.kformmaster.view

import android.app.Dialog
import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.AppCompatEditText
import android.support.v7.widget.AppCompatTextView
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import com.thejuki.kformmaster.R
import com.thejuki.kformmaster.helper.FormBuildHelper
import com.thejuki.kformmaster.model.BaseFormElement

/**
 * Base Form ViewBinder
 *
 * Base setup for title, error, and visibility
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
abstract class BaseFormViewBinder {

    /**
     * Initializes the base form fields
     */
    fun baseSetup(formElement: BaseFormElement<*>, textViewTitle: AppCompatTextView?,
                  textViewError: AppCompatTextView?,
                  itemView: View) {

        formElement.itemView = itemView
        formElement.titleView = textViewTitle
        formElement.errorView = textViewError
    }

    /**
     * Shows the [dialog] when the form element is clicked
     */
    fun setOnClickListener(editTextValue: AppCompatEditText, itemView: View, dialog: Dialog) {
        editTextValue.isFocusable = false

        // display the dialog on click
        val listener = View.OnClickListener {
            dialog.show()
        }

        itemView.setOnClickListener(listener)
        editTextValue.setOnClickListener(listener)
    }

    /**
     * Sets the focus changed listener on the editView to update the form element value
     */
    fun setOnFocusChangeListener(context: Context, formElement: BaseFormElement<*>, formBuilder: FormBuildHelper) {
        formElement.editView?.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                formElement.titleView?.setTextColor(ContextCompat.getColor(context,
                        R.color.colorFormMasterElementFocusedTitle))
            } else {
                formElement.titleView?.setTextColor(ContextCompat.getColor(context,
                        R.color.colorFormMasterElementTextTitle))

                (formElement.editView as? AppCompatEditText)?.let {
                    if (it.text.toString() != formElement.valueAsString) {
                        formElement.setValue(it.text.toString())
                        formElement.error = null
                        formBuilder.onValueChanged(formElement)
                    }
                }
            }
        }
    }

    /**
     * Adds a text changed listener to the editView to update the form element value
     */
    fun addTextChangedListener(formElement: BaseFormElement<*>, formBuilder: FormBuildHelper) {
        if (!formElement.updateOnFocusChange) {
            (formElement.editView as? AppCompatEditText)?.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(charSequence: CharSequence, i: Int, i2: Int, i3: Int) {}

                override fun onTextChanged(charSequence: CharSequence, i: Int, i2: Int, i3: Int) {

                    // get current form element, existing value and new value
                    val currentValue = formElement.valueAsString
                    val newValue = charSequence.toString()

                    // trigger event only if the value is changed
                    if (currentValue != newValue) {
                        formElement.setValue(newValue)
                        formElement.error = null
                        formBuilder.onValueChanged(formElement)
                    }
                }

                override fun afterTextChanged(editable: Editable) {}
            })
        }
    }

    /**
     * Sets the Done action listener to update the form element value
     */
    fun setOnEditorActionListener(formElement: BaseFormElement<*>, formBuilder: FormBuildHelper) {
        (formElement.editView as? AppCompatEditText)?.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                formElement.setValue((formElement.editView as? AppCompatEditText)?.text.toString())
                formElement.error = null
                formBuilder.onValueChanged(formElement)
            }
            false
        }
    }
}