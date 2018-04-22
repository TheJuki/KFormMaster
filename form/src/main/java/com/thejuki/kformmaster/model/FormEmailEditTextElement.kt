package com.thejuki.kformmaster.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Form EditText Element
 *
 * Form element for AppCompatEditText
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormEmailEditTextElement : BaseFormElement<String> {

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

    constructor(tag: Int = -1) : super(tag)

    constructor(`in`: Parcel) : super(`in`) {}

    companion object {
        /**
         * Creates an instance
         */
        fun createInstance(): FormEmailEditTextElement {
            return FormEmailEditTextElement()
        }

        val CREATOR: Parcelable.Creator<FormEmailEditTextElement> = object : Parcelable.Creator<FormEmailEditTextElement> {
            override fun createFromParcel(source: Parcel): FormEmailEditTextElement {
                return FormEmailEditTextElement(source)
            }

            override fun newArray(size: Int): Array<FormEmailEditTextElement?> {
                return arrayOfNulls(size)
            }
        }
    }
}
