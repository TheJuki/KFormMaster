package com.thejuki.kformmaster.model

import android.os.Parcel
import android.os.Parcelable
import android.widget.ArrayAdapter

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

    fun setDialogTitle(dialogTitle: String?): FormPickerDropDownElement<T>
    {
        this.dialogTitle = dialogTitle
        return this
    }

    fun setArrayAdapter(arrayAdapter: ArrayAdapter<*>?): FormPickerDropDownElement<T> {
        this.arrayAdapter = arrayAdapter
        return this
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

    protected constructor(`in`: Parcel) : super(`in`) {}

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
