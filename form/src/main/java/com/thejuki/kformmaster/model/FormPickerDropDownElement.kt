package com.thejuki.kformmaster.model

import android.app.Dialog
import android.content.Context
import android.support.v7.app.AlertDialog
import android.support.v7.widget.AppCompatEditText
import android.view.Gravity
import android.view.View
import android.widget.ArrayAdapter
import android.widget.TextView
import com.thejuki.kformmaster.R
import com.thejuki.kformmaster.helper.FormBuildHelper


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
     * dialogTitle builder setter
     */
    fun setDialogTitle(dialogTitle: String?): FormPickerDropDownElement<T> {
        this.dialogTitle = dialogTitle
        return this
    }

    /**
     * dialogEmptyMessage builder setter
     */
    fun setDialogEmptyMessage(dialogEmptyMessage: String?): FormPickerDropDownElement<T> {
        this.dialogEmptyMessage = dialogEmptyMessage
        return this
    }

    /**
     * arrayAdapter builder setter
     */
    fun setArrayAdapter(arrayAdapter: ArrayAdapter<*>?): FormPickerDropDownElement<T> {
        this.arrayAdapter = arrayAdapter
        return this
    }

    /**
     * Options builder setter
     */
    fun setOptions(options: List<T>): BaseFormElement<T> {
        this.options = options
        return this
    }

    /**
     * Re-initializes the dialog
     * Should be called after the options list changes
     */
    fun reInitDialog(context: Context? = null, formBuilder: FormBuildHelper? = null) {
        // reformat the options in format needed
        val options = arrayOfNulls<CharSequence>(this.options?.size ?: 0)

        this.options?.let {
            for (i in it.indices) {
                options[i] = it[i].toString()
            }
        }

        var alertDialog: Dialog? = null

        val editTextView = this.editView as? AppCompatEditText

        if (alertDialogBuilder == null && context != null) {
            alertDialogBuilder = AlertDialog.Builder(context)
            if (this.dialogTitle == null) {
                this.dialogTitle = context.getString(R.string.form_master_pick_one)
            }
            if (this.dialogEmptyMessage == null) {
                this.dialogEmptyMessage = context.getString(R.string.form_master_empty)
            }
        }

        alertDialogBuilder?.let {
            if (this.arrayAdapter != null) {
                this.arrayAdapter?.apply {
                    it.setTitle(this@FormPickerDropDownElement.dialogTitle)
                            .setMessage(null)
                            .setAdapter(this) { _, which ->
                                editTextView?.setText(this.getItem(which).toString())
                                this@FormPickerDropDownElement.setValue(this.getItem(which))
                                this@FormPickerDropDownElement.error = null
                                formBuilder?.onValueChanged(this@FormPickerDropDownElement)

                                editTextView?.setText(this@FormPickerDropDownElement.valueAsString)
                            }
                }
            } else {
                if (this.options?.isEmpty() == true) {
                    it.setTitle(this.dialogTitle).setMessage(dialogEmptyMessage)
                } else {
                    it.setTitle(this.dialogTitle)
                            .setMessage(null)
                            .setItems(options) { _, which ->
                                editTextView?.setText(options[which])
                                this.options?.let {
                                    this.setValue(it[which])
                                }
                                this.error = null
                                formBuilder?.onValueChanged(this)

                                editTextView?.setText(this.valueAsString)
                            }
                }
            }

            alertDialog = it.create()
        }

        // display the dialog on click
        val listener = View.OnClickListener {
            alertDialog?.show()

            if (this.options?.isEmpty() == true) {
                val messageView = alertDialog?.findViewById(android.R.id.message) as? TextView
                messageView?.gravity = Gravity.CENTER
            }
        }

        itemView?.setOnClickListener(listener)
        editTextView?.setOnClickListener(listener)
    }
}
