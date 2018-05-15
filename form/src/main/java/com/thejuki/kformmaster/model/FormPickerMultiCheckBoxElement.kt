package com.thejuki.kformmaster.model

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.support.v7.app.AlertDialog
import android.support.v7.widget.AppCompatEditText
import android.view.View
import com.thejuki.kformmaster.R
import com.thejuki.kformmaster.helper.FormBuildHelper
import java.io.Serializable
import java.util.*

/**
 * Form Picker MultiCheckBox Element
 *
 * Form element for AppCompatEditText (which on click opens a MultiCheckBox dialog)
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormPickerMultiCheckBoxElement<T : Serializable> : FormPickerElement<T> {

    override val isValid: Boolean
        get() = !required || (optionsSelected != null && optionsSelected?.isEmpty() == false)

    override fun clear() {
        super.clear()
        this.optionsSelected = null
    }

    /**
     * Alert Dialog Title
     * (optional - uses R.string.form_master_pick_one_or_more)
     */
    var dialogTitle: String? = null

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
    fun reInitDialog(context: Context, formBuilder: FormBuildHelper) {
        // reformat the options in format needed
        val options = arrayOfNulls<CharSequence>(this.options?.size ?: 0)
        val optionsSelected = BooleanArray(this.options?.size ?: 0)
        val mSelectedItems = ArrayList<Int>()

        this.options?.let {
            for (i in it.indices) {
                val obj = it[i]

                options[i] = obj.toString()
                optionsSelected[i] = false

                if (this.optionsSelected?.contains(obj) == true) {
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

        val alertDialog = AlertDialog.Builder(context)
                .setTitle(this.dialogTitle
                        ?: context.getString(R.string.form_master_pick_one_or_more))
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

                        this.setOptionsSelected(selectedOptions)
                        this.error = null
                        formBuilder.onValueChanged(this)
                        editTextView?.setText(getSelectedItemsText())
                    }
                }
                .setNegativeButton(android.R.string.cancel) { _, _ -> }
                .create()

        // display the dialog on click
        val listener = View.OnClickListener {
            alertDialog.show()
        }

        itemView?.setOnClickListener(listener)
        editTextView?.setOnClickListener(listener)
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

                if (this.optionsSelected?.contains(obj) == true) {
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

    /**
     * Parcelable boilerplate
     */
    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        super.writeToParcel(dest, flags)
    }

    constructor() : super()

    constructor(tag: Int) : super(tag)

    constructor(`in`: Parcel) : super(`in`) {}

    companion object {
        /**
         * Creates an instance
         */
        fun createInstance(): FormPickerMultiCheckBoxElement<String> {
            return FormPickerMultiCheckBoxElement()
        }

        /**
         * Creates a generic instance
         */
        fun <T : Serializable> createGenericInstance(): FormPickerMultiCheckBoxElement<T> {
            return FormPickerMultiCheckBoxElement()
        }

        val CREATOR: Parcelable.Creator<FormPickerMultiCheckBoxElement<*>> = object : Parcelable.Creator<FormPickerMultiCheckBoxElement<*>> {
            override fun createFromParcel(source: Parcel): FormPickerMultiCheckBoxElement<*> {
                return FormPickerMultiCheckBoxElement<Serializable>(source)
            }

            override fun newArray(size: Int): Array<FormPickerMultiCheckBoxElement<*>?> {
                return arrayOfNulls(size)
            }
        }
    }
}
