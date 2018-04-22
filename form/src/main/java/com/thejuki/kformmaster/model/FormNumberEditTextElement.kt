package com.thejuki.kformmaster.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Form Number EditText Element
 *
 * Form element for AppCompatEditText
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormNumberEditTextElement : BaseFormElement<String> {

    /**
     * By default, the number field can contain numbers and symbols (.,-).
     * Set to true to only allow numbers.
     */
    var numbersOnly: Boolean = false

    fun setNumbersOnly(numbersOnly: Boolean): FormNumberEditTextElement {
        this.numbersOnly = numbersOnly
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
        /**
         * Creates an instance
         */
        fun createInstance(): FormNumberEditTextElement {
            return FormNumberEditTextElement()
        }

        val CREATOR: Parcelable.Creator<FormNumberEditTextElement> = object : Parcelable.Creator<FormNumberEditTextElement> {
            override fun createFromParcel(source: Parcel): FormNumberEditTextElement {
                return FormNumberEditTextElement(source)
            }

            override fun newArray(size: Int): Array<FormNumberEditTextElement?> {
                return arrayOfNulls(size)
            }
        }
    }
}
