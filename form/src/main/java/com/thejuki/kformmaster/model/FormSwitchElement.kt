package com.thejuki.kformmaster.model

import android.os.Parcel
import android.support.v7.widget.SwitchCompat
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

    override fun clear() {
        this.value = offValue
        (this.editView as? SwitchCompat)?.isChecked = false
    }

    /**
     * Sets value to this when on
     */
    var onValue: T? = null

    /**
     * Sets value to this when off
     */
    var offValue: T? = null

    /**
     * Indicates if the switch should be on
     */
    fun isOn(): Boolean {
        if (onValue == null || value == null)
            return false
        return onValue == value
    }

    /**
     * onValue builder setter
     */
    fun setOnValue(onValue: T?): FormSwitchElement<T> {
        this.onValue = onValue
        return this
    }

    /**
     * offValue builder setter
     */
    fun setOffValue(offValue: T?): FormSwitchElement<T> {
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

    constructor() : super()

    constructor(tag: Int) : super(tag)

    constructor(`in`: Parcel) : super(`in`) {}

    companion object {
        /**
         * Creates an instance
         */
        fun createInstance(): FormSwitchElement<String> {
            return FormSwitchElement()
        }

        /**
         * Creates a generic instance
         */
        fun <T : Serializable> createGenericInstance(): FormSwitchElement<T> {
            return FormSwitchElement()
        }
    }
}