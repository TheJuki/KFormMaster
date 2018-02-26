package com.thejuki.kformmaster.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Form Header
 *
 * Form element for Header TextView
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormHeader : BaseFormElement<String> {

    override val isHeader: Boolean
        get() = true

    constructor() : super()

    constructor(tag: Int) : super(tag)

    /**
     * Parcelable boilerplate
     */
    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        super.writeToParcel(dest, flags)
    }

    protected constructor(`in`: Parcel) : super(`in`) {}

    companion object {

        fun createInstance(title: String): FormHeader {
            val formHeader = FormHeader()
            formHeader.setTitle(title)
            return formHeader
        }

        val CREATOR: Parcelable.Creator<FormHeader> = object : Parcelable.Creator<FormHeader> {
            override fun createFromParcel(source: Parcel): FormHeader {
                return FormHeader(source)
            }

            override fun newArray(size: Int): Array<FormHeader?> {
                return arrayOfNulls(size)
            }
        }
    }
}