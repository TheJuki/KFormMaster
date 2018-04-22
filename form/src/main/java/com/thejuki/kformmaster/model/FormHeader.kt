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

    /**
     * Enable to collapse/un-collapse elements below the header
     * when the header is tapped
     */
    var collapsible: Boolean = false

    /**
     * Indicates if elements under header are collapsed or not
     */
    var allCollapsed: Boolean = false

    /**
     * No validation needed
     */
    override val isValid: Boolean
        get() = true

    /**
     * Collapsible builder setter
     */
    fun setCollapsible(collapsible: Boolean): FormHeader {
        this.collapsible = collapsible
        return this
    }

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

    constructor(`in`: Parcel) : super(`in`) {}

    companion object {

        /**
         * Creates an instance
         */
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