package com.thejuki.kformmaster.view

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.res.ColorStateList
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.redmadrobot.inputmask.MaskedTextChangedListener.Companion.installOn
import com.thejuki.kformmaster.R
import com.thejuki.kformmaster.helper.FormBuildHelper
import com.thejuki.kformmaster.model.BaseFormElement
import com.thejuki.kformmaster.widget.ClearableEditText
import com.thejuki.kformmaster.widget.IconTextView


/**
 * Base Form ViewRenderer
 *
 * Base setup for title, error, and visibility
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
abstract class BaseFormViewRenderer {

    /**
     * Initializes the base form fields
     */
    @SuppressLint("ClickableViewAccessibility")
    fun baseSetup(formElement: BaseFormElement<*>,
                  dividerView: View? = null,
                  textViewTitle: AppCompatTextView? = null,
                  textViewError: AppCompatTextView? = null,
                  itemView: View,
                  mainViewLayout: View? = null,
                  editView: View?) {

        formElement.itemView = itemView
        formElement.dividerView = dividerView
        formElement.titleView = textViewTitle
        formElement.errorView = textViewError
        formElement.mainLayoutView = mainViewLayout
        formElement.editView = editView

        val onTouchListener = View.OnTouchListener { view, event ->
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> formElement.onTouchDown?.invoke()
                MotionEvent.ACTION_UP -> formElement.onTouchUp?.invoke()
            }

            view?.onTouchEvent(event) ?: true
        }

        formElement.itemView?.setOnTouchListener(onTouchListener)
        formElement.editView?.setOnTouchListener(onTouchListener)

        if (formElement.editView != null
                && formElement.editView is EditText
                && formElement.inputMaskOptions != null) {
            formElement.inputMaskOptions?.let {
                val maskedTextChangedListener = installOn(
                        formElement.editView as EditText,
                        it.primaryFormat,
                        it.affineFormats,
                        it.customNotations,
                        it.affinityCalculationStrategy,
                        it.autocomplete,
                        it.autoSkip,
                        it.listener,
                        it.valueListener
                )

                maskedTextChangedListener.rightToLeft = it.rightToLeft
            }
        }

        formElement.titleView?.let {
            if (it is IconTextView) {
                it.iconLocation = formElement.titleIconLocation
                it.icon = formElement.titleIcon
                it.iconPadding = formElement.titleIconPadding
                it.listener = object : IconTextView.Listener {
                    override fun clickedIcon() {
                        formElement.onTitleIconClick?.invoke()
                    }
                }
                it.reInitIcon()
            }
        }
    }

    /**
     * Sets the [ClearableEditText.Listener] to clear the form element
     */
    fun setClearableListener(formElement: BaseFormElement<*>) {
        formElement.editView?.let {
            if (it is ClearableEditText) {
                it.displayClear = formElement.clearable
                it.setListener(object : ClearableEditText.Listener {
                    override fun didClearText() {
                        formElement.clear()
                    }
                })
            }
        }
    }

    /**
     * Shows the [dialog] when the form element is clicked
     */
    fun setOnClickListener(formElement: BaseFormElement<*>, itemView: View, dialog: Dialog) {
        val context = formElement.editView?.context ?: return

        formElement.editView?.isFocusable = false

        // display the dialog on click
        val listener = View.OnClickListener {
            // Invoke onClick Unit
            formElement.onClick?.invoke()

            if (!formElement.confirmEdit || formElement.valueAsString.isEmpty()) {
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
    fun setOnFocusChangeListener(formElement: BaseFormElement<*>, formBuilder: FormBuildHelper) {
        val context = formElement.editView?.context ?: return
        val states = arrayOf(intArrayOf(android.R.attr.state_focused), intArrayOf())
        val colors = intArrayOf(formElement.titleFocusedTextColor ?: ContextCompat.getColor(context,
                R.color.colorFormMasterElementFocusedTitle),
                formElement.titleTextColor
                        ?: formElement.titleView?.textColors?.getColorForState(intArrayOf(),
                                ContextCompat.getColor(context, R.color.colorFormMasterElementTextTitle))
                        ?: -1
        )
        formElement.titleView?.setTextColor(ColorStateList(states, colors))

        formElement.editView?.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                // Invoke onFocus Unit
                formElement.onFocus?.invoke()

                if (formElement.clearOnFocus) {
                    formElement.value = null
                }

                formElement.titleView?.setTextColor(formElement.titleFocusedTextColor
                        ?: ContextCompat.getColor(context, R.color.colorFormMasterElementFocusedTitle))
            } else {
                formElement.titleView?.setTextColor(formElement.titleTextColor
                        ?: ContextCompat.getColor(context, R.color.colorFormMasterElementTextTitle))

                (formElement.editView as? AppCompatEditText)?.let {
                    if (it.text.toString() != formElement.valueAsString) {
                        formElement.error = null
                        formElement.setValue(it.text.toString())
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
                        formElement.error = null
                        formElement.setValue(newValue)
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
                formElement.error = null
                formElement.setValue((formElement.editView as? AppCompatEditText)?.text.toString())
                formBuilder.onValueChanged(formElement)
            }
            false
        }
    }
}