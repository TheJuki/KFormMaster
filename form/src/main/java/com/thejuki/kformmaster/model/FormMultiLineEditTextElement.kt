package com.thejuki.kformmaster.model

import android.os.Parcel
import android.os.Parcelable

import java.io.Serializable

/**
 * Form EditText Element
 *
 * Form element for AppCompatEditText
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormMultiLineEditTextElement<T : Serializable> : BaseFormElement<T> {

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
        fun createInstance(): FormMultiLineEditTextElement<String> {
            return FormMultiLineEditTextElement()
        }

        fun <T : Serializable> createGenericInstance(): FormMultiLineEditTextElement<T> {
            return FormMultiLineEditTextElement()
        }

        val CREATOR: Parcelable.Creator<FormMultiLineEditTextElement<*>> = object : Parcelable.Creator<FormMultiLineEditTextElement<*>> {
            override fun createFromParcel(source: Parcel): FormMultiLineEditTextElement<*> {
                return FormMultiLineEditTextElement<Serializable>(source)
            }

            override fun newArray(size: Int): Array<FormMultiLineEditTextElement<*>?> {
                return arrayOfNulls(size)
            }
        }
    }
}
