package com.thejuki.kformmaster.model

import android.os.Parcel
import android.os.Parcelable

import java.io.Serializable

/**
 * Form Button Element
 *
 * Form element for Button
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormButtonElement<T : Serializable> : BaseFormElement<T> {

    override fun getType(): Int {
        return BaseFormElement.TYPE_BUTTON
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

    protected constructor(`in`: Parcel) : super(`in`) {}

    companion object {
        fun createInstance(): FormButtonElement<String> {
            return FormButtonElement()
        }

        fun <T : Serializable> createGenericInstance(): FormButtonElement<T> {
            return FormButtonElement()
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
