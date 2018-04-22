package com.thejuki.kformmaster.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Form MultiLine EditText Element
 *
 * Form element for AppCompatEditText
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormMultiLineEditTextElement : BaseFormElement<String> {

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
        fun createInstance(): FormMultiLineEditTextElement {
            return FormMultiLineEditTextElement()
        }

        val CREATOR: Parcelable.Creator<FormMultiLineEditTextElement> = object : Parcelable.Creator<FormMultiLineEditTextElement> {
            override fun createFromParcel(source: Parcel): FormMultiLineEditTextElement {
                return FormMultiLineEditTextElement(source)
            }

            override fun newArray(size: Int): Array<FormMultiLineEditTextElement?> {
                return arrayOfNulls(size)
            }
        }
    }
}
