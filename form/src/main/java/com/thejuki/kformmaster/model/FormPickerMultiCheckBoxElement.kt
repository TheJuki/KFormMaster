package com.thejuki.kformmaster.model

import android.os.Parcel
import android.os.Parcelable

import java.io.Serializable

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
        get() = !required || (optionsSelected != null && !optionsSelected!!.isEmpty())

    /**
     * Alert Dialog Title
     * (optional - uses R.string.form_master_pick_one_or_more)
     */
    var dialogTitle: String? = null

    fun setDialogTitle(dialogTitle: String?): FormPickerMultiCheckBoxElement<T>
    {
        this.dialogTitle = dialogTitle
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

    constructor(`in`: Parcel) : super(`in`) {}

    companion object {
        fun createInstance(): FormPickerMultiCheckBoxElement<String> {
            return FormPickerMultiCheckBoxElement()
        }

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
