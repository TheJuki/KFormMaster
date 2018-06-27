package com.thejuki.kformmaster.model

import android.content.Context
import android.support.v7.app.AlertDialog
import android.support.v7.widget.AppCompatEditText
import android.view.View
import com.thejuki.kformmaster.R
import com.thejuki.kformmaster.helper.FormBuildHelper

/**
 * Form Picker MultiCheckBox Element
 *
 * Form element for AppCompatEditText (which on click opens a MultiCheckBox dialog)
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormPickerMultiCheckBoxElement<T : List<*>>(tag: Int = -1) : FormPickerElement<T>(tag) {

    override val isValid: Boolean
        get() = !required || (value != null && value?.isEmpty() == false)

    override fun clear() {
        super.clear()
        reInitDialog()
    }

    /**
     * Form Element Options
     */
    var options: T? = null
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
     * Options builder setter
     */
    fun setOptions(options: T): BaseFormElement<T> {
        this.options = options
        return this
    }

    /**
     * Alert Dialog Title
     * (optional - uses R.string.form_master_pick_one_or_more)
     */
    var dialogTitle: String? = null

    /**
     * Alert Dialog Empty Message
     * (optional - uses R.string.form_master_empty)
     */
    var dialogEmptyMessage: String? = null

    /**
     * dialogTitle builder setter
     */
    fun setDialogTitle(dialogTitle: String?): FormPickerMultiCheckBoxElement<T> {
        this.dialogTitle = dialogTitle
        return this
    }

    /**
     * Re-initializes the dialog
     * Should be called after the options list changes
     */
    fun reInitDialog(context: Context? = null, formBuilder: FormBuildHelper? = null) {
        // reformat the options in format needed
        val options = arrayOfNulls<CharSequence>(this.options?.size ?: 0)
        val optionsSelected = BooleanArray(this.options?.size ?: 0)
        val mSelectedItems = ArrayList<Int>()

        this.options?.let {
            for (i in it.indices) {
                val obj = it[i]

                options[i] = obj.toString()
                optionsSelected[i] = false

                if (this.value?.contains(obj) == true) {
                    optionsSelected[i] = true
                    mSelectedItems.add(i)
                }
            }
        }

        var selectedItems = ""
        for (i in mSelectedItems.indices) {
            selectedItems += options[mSelectedItems[i]]

            if (i < mSelectedItems.size - 1) {
                selectedItems += ", "
            }
        }

        val editTextView = this.editView as? AppCompatEditText

        editTextView?.setText(getSelectedItemsText())

        if (alertDialogBuilder == null && context != null) {
            alertDialogBuilder = AlertDialog.Builder(context)
            if (this.dialogTitle == null) {
                this.dialogTitle = context.getString(R.string.form_master_pick_one_or_more)
            }
            if (this.dialogEmptyMessage == null) {
                this.dialogEmptyMessage = context.getString(R.string.form_master_empty)
            }
            if (this.confirmTitle == null) {
                this.confirmTitle = context.getString(R.string.form_master_confirm_title)
            }
            if (this.confirmMessage == null) {
                this.confirmMessage = context.getString(R.string.form_master_confirm_message)
            }
        }

        alertDialogBuilder?.let { builder ->
            if (this.options?.isEmpty() == true) {
                builder.setTitle(this.dialogTitle)
                        .setMessage(dialogEmptyMessage)
                        .setPositiveButton(null, null)
                        .setNegativeButton(null, null)
            } else {
                builder.setTitle(this.dialogTitle)
                        .setMessage(null)
                        .setMultiChoiceItems(options, optionsSelected) { _, which, isChecked ->
                            if (isChecked) {
                                // If the user checked the item, add it to the selected items
                                mSelectedItems.add(which)
                            } else if (mSelectedItems.contains(which)) {
                                // Else, if the item is already in the array, remove it
                                mSelectedItems.remove(which)
                            }
                        }
                        // Set the action buttons
                        .setPositiveButton(android.R.string.ok) { _, _ ->
                            this.options?.let {
                                val selectedOptions = mSelectedItems.indices
                                        .map { mSelectedItems[it] }
                                        .map { x -> it[x] }

                                this.setValue(selectedOptions)
                                this.error = null
                                formBuilder?.onValueChanged(this)
                                editTextView?.setText(getSelectedItemsText())
                            }
                        }
                        .setNegativeButton(android.R.string.cancel) { _, _ -> }
            }

            val alertDialog = builder.create()

            // display the dialog on click
            val listener = View.OnClickListener {
                if (!confirmEdit) {
                    alertDialog.show()
                } else if (confirmEdit && value != null) {
                    builder.setTitle(confirmTitle)
                            .setMessage(confirmMessage)
                            // Set the action buttons
                            .setPositiveButton(android.R.string.ok) { _, _ ->
                                alertDialog.show()
                            }
                            .setNegativeButton(android.R.string.cancel) { _, _ -> }
                            .show()
                }
            }

            itemView?.setOnClickListener(listener)
            editTextView?.setOnClickListener(listener)
        }
    }

    private fun getSelectedItemsText(): String {
        val options = arrayOfNulls<CharSequence>(this.options?.size ?: 0)
        val optionsSelected = BooleanArray(this.options?.size ?: 0)
        val mSelectedItems = ArrayList<Int>()

        this.options?.let {
            for (i in it.indices) {
                val obj = it[i]

                options[i] = obj.toString()
                optionsSelected[i] = false

                if (this.value?.contains(obj) == true) {
                    optionsSelected[i] = true
                    mSelectedItems.add(i)
                }
            }
        }

        var selectedItems = ""
        for (i in mSelectedItems.indices) {
            selectedItems += options[mSelectedItems[i]]

            if (i < mSelectedItems.size - 1) {
                selectedItems += ", "
            }
        }

        return selectedItems
    }
}
