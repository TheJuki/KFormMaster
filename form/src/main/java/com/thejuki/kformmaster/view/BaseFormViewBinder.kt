package com.thejuki.kformmaster.view

import android.app.Dialog
import android.content.Context
import android.content.res.ColorStateList
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
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
    fun setOnClickListener(context: Context, formElement: BaseFormElement<*>, itemView: View, dialog: Dialog) {
        formElement.editView?.isFocusable = false

        // display the dialog on click
        val listener = View.OnClickListener {
            if (!formElement.confirmEdit) {
                dialog.show()
            } else if (formElement.confirmEdit && formElement.value != null) {
                AlertDialog.Builder(context)
                        .setTitle(formElement.confirmTitle
                                ?: context.getString(R.string.form_master_confirm_title))
                        .setMessage(formElement.confirmMessage
                                ?: context.getString(R.string.form_master_confirm_message))
                        // Set the action buttons
                        .setPositiveButton(android.R.string.ok) { _, _ ->
                            dialog.show()
                        }
                        .setNegativeButton(android.R.string.cancel) { _, _ -> }
                        .show()
            }
        }

        itemView.setOnClickListener(listener)
        formElement.editView?.setOnClickListener(listener)
    }

    /**
     * Sets the focus changed listener on the editView to update the form element value
     */
    fun setOnFocusChangeListener(context: Context, formElement: BaseFormElement<*>, formBuilder: FormBuildHelper) {
        if (formElement.titleViewTextColors == null) {
            val states = arrayOf(intArrayOf(android.R.attr.state_focused), intArrayOf())
            val colors = intArrayOf(ContextCompat.getColor(context,
                    R.color.colorFormMasterElementFocusedTitle),
                    formElement.titleView?.textColors?.getColorForState(intArrayOf(),
                            (ContextCompat.getColor(context, R.color.colorFormMasterElementTextTitle)))
                            ?: -1
            )
            formElement.titleViewTextColors = ColorStateList(states, colors)
            formElement.titleView?.setTextColor(formElement.titleViewTextColors)
        }
        formElement.editView?.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                formElement.titleView?.setTextColor(formElement.titleViewTextColors?.getColorForState(intArrayOf(android.R.attr.state_focused),
                        (ContextCompat.getColor(context, R.color.colorFormMasterElementFocusedTitle)))
                        ?: -1)
            } else {
                formElement.titleView?.setTextColor(formElement.titleViewTextColors?.getColorForState(intArrayOf(),
                        (ContextCompat.getColor(context, R.color.colorFormMasterElementTextTitle)))
                        ?: -1)

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