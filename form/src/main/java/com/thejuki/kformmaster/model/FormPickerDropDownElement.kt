package com.thejuki.kformmaster.model

import android.app.Dialog
import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.support.v7.app.AlertDialog
import android.support.v7.widget.AppCompatEditText
import android.view.View
import android.widget.ArrayAdapter
import com.thejuki.kformmaster.R
import com.thejuki.kformmaster.helper.FormBuildHelper

import java.io.Serializable

/**
 * Form Picker Dropdown Element
 *
 * Form element for AppCompatEditText (which on click opens a Single Choice dialog)
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormPickerDropDownElement<T : Serializable> : FormPickerElement<T> {

    /**
     * Alert Dialog Title
     * (optional - uses R.string.form_master_pick_one)
     */
    var dialogTitle: String? = null

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
     * arrayAdapter builder setter
     */
    fun setArrayAdapter(arrayAdapter: ArrayAdapter<*>?): FormPickerDropDownElement<T> {
        this.arrayAdapter = arrayAdapter
        return this
    }

    /**
     * Re-initializes the dialog
     * Should be called after the options list changes
     */
    fun reInitDialog(context: Context, formBuilder: FormBuildHelper) {
        // reformat the options in format needed
        val options = arrayOfNulls<CharSequence>(this.options?.size ?: 0)

        this.options?.let {
            for (i in it.indices) {
                options[i] = it[i].toString()
            }
        }

        var alertDialog: Dialog? = null

        val editTextView = this.editView as? AppCompatEditText

        if (this.arrayAdapter != null) {
            this.arrayAdapter?.let {
                alertDialog = AlertDialog.Builder(context)
                        .setTitle(this.dialogTitle
                                ?: context.getString(R.string.form_master_pick_one))
                        .setAdapter(it, { _, which ->
                            editTextView?.setText(it.getItem(which).toString())
                            this.setValue(it.getItem(which))
                            this.error = null
                            formBuilder.onValueChanged(this)

                            editTextView?.setText(this.valueAsString)
                        })
                        .create()
            }
        } else {
            alertDialog = AlertDialog.Builder(context)
                    .setTitle(this.dialogTitle ?: context.getString(R.string.form_master_pick_one))
                    .setItems(options) { _, which ->
                        editTextView?.setText(options[which])
                        this.options?.let {
                            this.setValue(it[which])
                        }
                        this.error = null
                        formBuilder.onValueChanged(this)

                        editTextView?.setText(this.valueAsString)
                    }
                    .create()
        }

        // display the dialog on click
        val listener = View.OnClickListener {
            alertDialog?.show()
        }

        itemView?.setOnClickListener(listener)
        editTextView?.setOnClickListener(listener)
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
        fun createInstance(): FormPickerDropDownElement<String> {
            return FormPickerDropDownElement()
        }

        fun <T : Serializable> createGenericInstance(): FormPickerDropDownElement<T> {
            return FormPickerDropDownElement()
        }

        val CREATOR: Parcelable.Creator<FormPickerDropDownElement<*>> = object : Parcelable.Creator<FormPickerDropDownElement<*>> {
            override fun createFromParcel(source: Parcel): FormPickerDropDownElement<*> {
                return FormPickerDropDownElement<Serializable>(source)
            }

            override fun newArray(size: Int): Array<FormPickerDropDownElement<*>?> {
                return arrayOfNulls(size)
            }
        }
    }
}
