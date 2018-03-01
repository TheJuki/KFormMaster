package com.thejuki.kformmaster.model

import android.os.Parcel
import java.io.Serializable

/**
 * Form CheckBox Element
 *
 * Form element for AppCompatCheckBox
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
open class FormCheckBoxElement<T : Serializable> : BaseFormElement<T> {

    /**
     * Sets value to this when checked
     */
    var checkedValue: T? = null

    /**
     * Sets value to this when unchecked
     */
    var unCheckedValue: T? = null

    fun isChecked(): Boolean {
        if (checkedValue == null || value == null)
            return false
        return checkedValue!! == value!!
    }

    fun setCheckedValue(checkedValue: T?): FormCheckBoxElement<T> {
        this.checkedValue = checkedValue
        return this
    }

    fun setUnCheckedValue(unCheckedValue: T?): FormCheckBoxElement<T> {
        this.unCheckedValue = unCheckedValue
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

    protected constructor(`in`: Parcel) : super(`in`) {}

    companion object {
        fun createInstance(): FormCheckBoxElement<String> {
            return FormCheckBoxElement()
        }

        fun <T : Serializable> createGenericInstance(): FormCheckBoxElement<T> {
            return FormCheckBoxElement()
        }
    }
}