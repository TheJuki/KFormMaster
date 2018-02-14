package com.thejuki.kformmaster.model

import android.os.Parcel
import android.os.Parcelable

import java.io.Serializable

/**
 * Form TextView Element
 *
 * Form element for AppCompatTextView
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormTextViewElement<T : Serializable> : BaseFormElement<T> {

    override fun getType(): Int {
        return BaseFormElement.TYPE_TEXTVIEW
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
        fun createInstance(): FormTextViewElement<String> {
            return FormTextViewElement()
        }

        fun <T : Serializable> createGenericInstance(): FormTextViewElement<T> {
            return FormTextViewElement()
        }

        val CREATOR: Parcelable.Creator<FormTextViewElement<*>> = object : Parcelable.Creator<FormTextViewElement<*>> {
            override fun createFromParcel(source: Parcel): FormTextViewElement<*> {
                return FormTextViewElement<Serializable>(source)
            }

            override fun newArray(size: Int): Array<FormTextViewElement<*>?> {
                return arrayOfNulls(size)
            }
        }
    }
}