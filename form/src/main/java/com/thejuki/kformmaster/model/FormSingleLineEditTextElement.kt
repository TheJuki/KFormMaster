package com.thejuki.kformmaster.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Form SingleLine EditText Element
 *
 * Form element for AppCompatEditText
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormSingleLineEditTextElement : BaseFormElement<String> {

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
        fun createInstance(): FormSingleLineEditTextElement {
            return FormSingleLineEditTextElement()
        }

        val CREATOR: Parcelable.Creator<FormSingleLineEditTextElement> = object : Parcelable.Creator<FormSingleLineEditTextElement> {
            override fun createFromParcel(source: Parcel): FormSingleLineEditTextElement {
                return FormSingleLineEditTextElement(source)
            }

            override fun newArray(size: Int): Array<FormSingleLineEditTextElement?> {
                return arrayOfNulls(size)
            }
        }
    }
}
