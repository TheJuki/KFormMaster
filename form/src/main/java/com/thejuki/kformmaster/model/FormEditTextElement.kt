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
class FormEditTextElement<T : Serializable> : BaseFormElement<T> {

    override fun getType(): Int {
        return if (this.mType == 0) BaseFormElement.TYPE_EDITTEXT_TEXT_SINGLELINE else this.mType
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

    constructor(tag: Int = 0) : super(tag)

    constructor(`in`: Parcel) : super(`in`) {}

    companion object {
        fun createInstance(): FormEditTextElement<String> {
            return FormEditTextElement()
        }

        fun <T : Serializable> createGenericInstance(): FormEditTextElement<T> {
            return FormEditTextElement()
        }

        val CREATOR: Parcelable.Creator<FormEditTextElement<*>> = object : Parcelable.Creator<FormEditTextElement<*>> {
            override fun createFromParcel(source: Parcel): FormEditTextElement<*> {
                return FormEditTextElement<Serializable>(source)
            }

            override fun newArray(size: Int): Array<FormEditTextElement<*>?> {
                return arrayOfNulls(size)
            }
        }
    }
}
