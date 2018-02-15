package com.thejuki.kformmaster.model

import android.os.Parcel
import android.os.Parcelable

import java.io.Serializable

/**
 * Form Number EditText Element
 *
 * Form element for AppCompatEditText
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormNumberEditTextElement<T : Serializable> : BaseFormElement<T> {

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
        fun createInstance(): FormNumberEditTextElement<String> {
            return FormNumberEditTextElement()
        }

        fun <T : Serializable> createGenericInstance(): FormNumberEditTextElement<T> {
            return FormNumberEditTextElement()
        }

        val CREATOR: Parcelable.Creator<FormNumberEditTextElement<*>> = object : Parcelable.Creator<FormNumberEditTextElement<*>> {
            override fun createFromParcel(source: Parcel): FormNumberEditTextElement<*> {
                return FormNumberEditTextElement<Serializable>(source)
            }

            override fun newArray(size: Int): Array<FormNumberEditTextElement<*>?> {
                return arrayOfNulls(size)
            }
        }
    }
}
