package com.thejuki.kformmaster.model

import android.os.Parcel
import android.os.Parcelable

import java.io.Serializable

/**
 * Form SingleLine EditText Element
 *
 * Form element for AppCompatEditText
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormSingleLineEditTextElement<T : Serializable> : BaseFormElement<T> {

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
        fun createInstance(): FormSingleLineEditTextElement<String> {
            return FormSingleLineEditTextElement()
        }

        fun <T : Serializable> createGenericInstance(): FormSingleLineEditTextElement<T> {
            return FormSingleLineEditTextElement()
        }

        val CREATOR: Parcelable.Creator<FormSingleLineEditTextElement<*>> = object : Parcelable.Creator<FormSingleLineEditTextElement<*>> {
            override fun createFromParcel(source: Parcel): FormSingleLineEditTextElement<*> {
                return FormSingleLineEditTextElement<Serializable>(source)
            }

            override fun newArray(size: Int): Array<FormSingleLineEditTextElement<*>?> {
                return arrayOfNulls(size)
            }
        }
    }
}
