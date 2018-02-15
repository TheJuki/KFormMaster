package com.thejuki.kformmaster.model

import android.os.Parcel
import android.os.Parcelable

import java.io.Serializable

/**
 * Form Password EditText Element
 *
 * Form element for AppCompatEditText
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormPasswordEditTextElement<T : Serializable> : BaseFormElement<T> {

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
        fun createInstance(): FormPasswordEditTextElement<String> {
            return FormPasswordEditTextElement()
        }

        fun <T : Serializable> createGenericInstance(): FormPasswordEditTextElement<T> {
            return FormPasswordEditTextElement()
        }

        val CREATOR: Parcelable.Creator<FormPasswordEditTextElement<*>> = object : Parcelable.Creator<FormPasswordEditTextElement<*>> {
            override fun createFromParcel(source: Parcel): FormPasswordEditTextElement<*> {
                return FormPasswordEditTextElement<Serializable>(source)
            }

            override fun newArray(size: Int): Array<FormPasswordEditTextElement<*>?> {
                return arrayOfNulls(size)
            }
        }
    }
}
