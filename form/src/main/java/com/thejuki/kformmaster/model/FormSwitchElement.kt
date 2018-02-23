package com.thejuki.kformmaster.model

import android.os.Parcel
import java.io.Serializable

/**
 * Form Switch Element
 *
 * Form element for Switch
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
open class FormSwitchElement<T : Serializable> : BaseFormElement<T> {

    var onValue: T? = null
    var offValue: T? = null

    fun isOn(): Boolean {
        if (onValue == null || value == null)
            return false
        return onValue!! == value!!
    }

    fun setOnValue(onValue: T?): FormSwitchElement<T>
    {
        this.onValue = onValue
        return this
    }

    fun setOffValue(offValue: T?): FormSwitchElement<T>
    {
        this.offValue = offValue
        return this
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

    constructor(tag: Int = -1) : super(tag)

    protected constructor(`in`: Parcel) : super(`in`) {}

    companion object {
        fun createInstance(): FormSwitchElement<String> {
            return FormSwitchElement()
        }

        fun <T : Serializable> createGenericInstance(): FormSwitchElement<T> {
            return FormSwitchElement()
        }
    }
}