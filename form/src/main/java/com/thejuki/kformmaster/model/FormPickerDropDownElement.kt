package com.thejuki.kformmaster.model

import android.app.AlertDialog
import android.view.View
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.widget.AppCompatEditText
import com.thejuki.kformmaster.R
import com.thejuki.kformmaster.helper.FormBuildHelper
import com.thejuki.kformmaster.listener.OnFormElementValueChangedListener


/**
 * Form Picker Dropdown Element
 *
 * Form element for AppCompatEditText (which on click opens a Single Choice dialog)
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormPickerDropDownElement<T>(tag: Int = -1) : FormPickerElement<T>(tag) {

    /**
     * Form Element Options
     */
    var options: List<T>? = null
        set(value) {
            field = value
            reInitDialog()
        }

    override fun clear() {
        super.clear()
        reInitDialog()
    }

    /**
     * Enable to display the radio buttons
     */
    var displayRadioButtons: Boolean = false

    /**
     * Alert Dialog Builder
     * Used to call reInitDialog without needing context again.
     */
    private var alertDialogBuilder: AlertDialog.Builder? = null

    /**
     * Alert Dialog Title
     * (optional - uses R.string.form_master_pick_one)
     */
    var dialogTitle: String? = null

    /**
     * Alert Dialog Empty Message
     * (optional - uses R.string.form_master_empty)
     */
    var dialogEmptyMessage: String? = null

    /**
     * ArrayAdapter for Alert Dialog
     * (optional - uses setItems(options))
     */
    var arrayAdapter: ArrayAdapter<*>? = null

    /**
     * Hold the [OnFormElementValueChangedListener] from [FormBuildHelper]
     */
    private var listener: OnFormElementValueChangedListener? = null

    /**
     * Theme
     */
    var theme: Int = 0

    /**
     * Display Value For
     * Used to specify a string value to be displayed
     */
    var displayValueFor: ((T?) -> String?) = {
        it?.toString() ?: ""
    }

    override val valueAsString: String
        get() = this.displayValueFor(this.value) ?: ""

    /**
     * Alert Dialog Title Custom View
     * If set, this will set the custom title for the alert dialog
     */
    var dialogTitleCustomView: View? = null

    /**
     * Re-initializes the dialog
     * Should be called after the options list changes
     */
    @Suppress("UNCHECKED_CAST")
    fun reInitDialog(formBuilder: FormBuildHelper? = null) {
        // reformat the options in format needed
        val options = arrayOfNulls<CharSequence>(this.options?.size ?: 0)
        val selectedIndex: Int = this.options?.indexOf(this.value) ?: -1

        this.options?.let {
            for (i in it.indices) {
                options[i] = this.displayValueFor(it[i])
            }
        }

        if (formBuilder != null) {
            listener = formBuilder.listener
        }

        lateinit var alertDialog: AlertDialog

        val editTextView = this.editView as? AppCompatEditText

        if (alertDialogBuilder == null && editTextView?.context != null) {
            alertDialogBuilder = AlertDialog.Builder(editTextView.context, theme)
            if (this.dialogTitle == null) {
                this.dialogTitle = editTextView.context.getString(R.string.form_master_pick_one)
            }
            if (this.dialogEmptyMessage == null) {
                this.dialogEmptyMessage = editTextView.context.getString(R.string.form_master_empty)
            }
            if (this.confirmTitle == null) {
                this.confirmTitle = editTextView.context.getString(R.string.form_master_confirm_title)
            }
            if (this.confirmMessage == null) {
                this.confirmMessage = editTextView.context.getString(R.string.form_master_confirm_message)
            }
        }

        alertDialogBuilder?.let {
            if (this.arrayAdapter != null) {
                this.arrayAdapter?.apply {
                    it.setTitle(this@FormPickerDropDownElement.dialogTitle)
                            .setMessage(null)
                            .setPositiveButton(null, null)
                            .setNegativeButton(null, null)

                    if (displayRadioButtons) {
                        it.setSingleChoiceItems(this, selectedIndex) { dialogInterface, which ->
                            this@FormPickerDropDownElement.error = null
                            this@FormPickerDropDownElement.setValue(this.getItem(which))
                            listener?.onValueChanged(this@FormPickerDropDownElement)
                            dialogInterface.dismiss()
                        }
                    } else {
                        it.setAdapter(this) { _, which ->
                            this@FormPickerDropDownElement.error = null
                            this@FormPickerDropDownElement.setValue(this.getItem(which))
                            listener?.onValueChanged(this@FormPickerDropDownElement)
                        }
                    }
                }
            } else {
                if (this.options?.isEmpty() == true) {
                    it.setTitle(this.dialogTitle).setMessage(dialogEmptyMessage)
                } else {
                    it.setTitle(this.dialogTitle).setMessage(null)
                            .setPositiveButton(null, null)
                            .setNegativeButton(null, null)

                    if (displayRadioButtons) {
                        it.setSingleChoiceItems(options, selectedIndex) { dialogInterface, which ->
                            this.error = null

                            this.options?.let { option ->
                                this.setValue(option[which])
                            }
                            listener?.onValueChanged(this)

                            dialogInterface.dismiss()
                        }
                    } else {
                        it.setItems(options) { _, which ->
                            this.error = null
                            this.options?.let { option ->
                                this.setValue(option[which])
                            }
                            listener?.onValueChanged(this)
                        }
                    }
                }
            }

            alertDialog = it.create()

            if (dialogTitleCustomView != null) {
                alertDialog.setCustomTitle(dialogTitleCustomView)
            }
        }

        // display the dialog on click
        val listener = View.OnClickListener {
            // Invoke onClick Unit
            this.onClick?.invoke()

            if (!confirmEdit || valueAsString.isEmpty()) {
                alertDialog.show()
            } else if (confirmEdit && value != null) {
                alertDialogBuilder
                        ?.setTitle(confirmTitle)
                        ?.setMessage(confirmMessage)
                        ?.setPositiveButton(android.R.string.ok) { _, _ ->
                            alertDialog.show()
                        }?.setNegativeButton(android.R.string.cancel) { _, _ -> }?.show()
            }
        }

        itemView?.setOnClickListener(listener)
        editTextView?.setOnClickListener(listener)
    }

    override fun displayNewValue() {
        editView?.let {
            if (it is TextView) {
                it.text = valueAsString
            }
        }
    }
}

