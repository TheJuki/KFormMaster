package com.thejuki.kformmaster.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Form TextView Element
 *
 * Form element for AppCompatTextView
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormTextViewElement : BaseFormElement<String> {

    /**
     * Nothing to clear
     */
    override fun clear() {}

    /**
     * No validation needed
     */
    override val isValid: Boolean
        get() = true

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
        fun createInstance(): FormTextViewElement {
            return FormTextViewElement()
        }

        val CREATOR: Parcelable.Creator<FormTextViewElement> = object : Parcelable.Creator<FormTextViewElement> {
            override fun createFromParcel(source: Parcel): FormTextViewElement {
                return FormTextViewElement(source)
            }

            override fun newArray(size: Int): Array<FormTextViewElement?> {
                return arrayOfNulls(size)
            }
        }
    }
}