package com.thejuki.kformmaster.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Form Phone EditText Element
 *
 * Form element for AppCompatEditText
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormPhoneEditTextElement : BaseFormElement<String> {

    /**
     * Parcelable boilerplate
     */
    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        super.writeToParcel(dest, flags)
    }

    constructor(tag: Int = 0) : super(tag)

    constructor(`in`: Parcel) : super(`in`) {}

    companion object {
        fun createInstance(): FormPhoneEditTextElement {
            return FormPhoneEditTextElement()
        }

        val CREATOR: Parcelable.Creator<FormPhoneEditTextElement> = object : Parcelable.Creator<FormPhoneEditTextElement> {
            override fun createFromParcel(source: Parcel): FormPhoneEditTextElement {
                return FormPhoneEditTextElement(source)
            }

            override fun newArray(size: Int): Array<FormPhoneEditTextElement?> {
                return arrayOfNulls(size)
            }
        }
    }
}
