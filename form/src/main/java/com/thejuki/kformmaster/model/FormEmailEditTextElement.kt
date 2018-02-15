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
class FormEmailEditTextElement<T : Serializable> : BaseFormElement<T> {

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
        fun createInstance(): FormEmailEditTextElement<String> {
            return FormEmailEditTextElement()
        }

        fun <T : Serializable> createGenericInstance(): FormEmailEditTextElement<T> {
            return FormEmailEditTextElement()
        }

        val CREATOR: Parcelable.Creator<FormEmailEditTextElement<*>> = object : Parcelable.Creator<FormEmailEditTextElement<*>> {
            override fun createFromParcel(source: Parcel): FormEmailEditTextElement<*> {
                return FormEmailEditTextElement<Serializable>(source)
            }

            override fun newArray(size: Int): Array<FormEmailEditTextElement<*>?> {
                return arrayOfNulls(size)
            }
        }
    }
}
