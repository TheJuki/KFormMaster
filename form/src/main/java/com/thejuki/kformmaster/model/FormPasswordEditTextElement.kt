package com.thejuki.kformmaster.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Form Password EditText Element
 *
 * Form element for AppCompatEditText
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormPasswordEditTextElement : BaseFormElement<String> {

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
        fun createInstance(): FormPasswordEditTextElement {
            return FormPasswordEditTextElement()
        }

        val CREATOR: Parcelable.Creator<FormPasswordEditTextElement> = object : Parcelable.Creator<FormPasswordEditTextElement> {
            override fun createFromParcel(source: Parcel): FormPasswordEditTextElement {
                return FormPasswordEditTextElement(source)
            }

            override fun newArray(size: Int): Array<FormPasswordEditTextElement?> {
                return arrayOfNulls(size)
            }
        }
    }
}
