package com.thejuki.kformmasterexample.custom.model

import android.os.Parcel
import android.os.Parcelable
import com.thejuki.kformmaster.model.BaseFormElement

/**
 * Form Custom Element
 *
 * Form element for AppCompatEditText
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormCustomElement : BaseFormElement<String> {

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
        fun createInstance(): FormCustomElement {
            return FormCustomElement()
        }

        val CREATOR: Parcelable.Creator<FormCustomElement> = object : Parcelable.Creator<FormCustomElement> {
            override fun createFromParcel(source: Parcel): FormCustomElement {
                return FormCustomElement(source)
            }

            override fun newArray(size: Int): Array<FormCustomElement?> {
                return arrayOfNulls(size)
            }
        }
    }
}
