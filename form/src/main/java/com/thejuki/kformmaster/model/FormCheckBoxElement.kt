package com.thejuki.kformmaster.model

import android.os.Parcel
import android.support.v7.widget.AppCompatCheckBox
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

    override fun clear() {
        this.value = unCheckedValue
        (this.editView as? AppCompatCheckBox)?.isChecked = false
    }

    /**
     * Sets value to this when checked
     */
    var checkedValue: T? = null

    /**
     * Sets value to this when unchecked
     */
    var unCheckedValue: T? = null

    /**
     * Indicates if the checkbox should be checked
     */
    fun isChecked(): Boolean {
        if (checkedValue == null || value == null)
            return false
        return checkedValue == value
    }

    /**
     * checkedValue builder setter
     */
    fun setCheckedValue(checkedValue: T?): FormCheckBoxElement<T> {
        this.checkedValue = checkedValue
        return this
    }

    /**
     * unCheckedValue builder setter
     */
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

    constructor(`in`: Parcel) : super(`in`) {}

    companion object {
        /**
         * Creates an instance
         */
        fun createInstance(): FormCheckBoxElement<String> {
            return FormCheckBoxElement()
        }

        /**
         * Creates a generic instance
         */
        fun <T : Serializable> createGenericInstance(): FormCheckBoxElement<T> {
            return FormCheckBoxElement()
        }
    }
}