package com.thejuki.kformmaster.model

import androidx.appcompat.widget.AppCompatCheckBox

/**
 * Form CheckBox Element
 *
 * Form element for AppCompatCheckBox
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
open class FormCheckBoxElement<T>(tag: Int = -1) : BaseFormElement<T>(tag) {

    override fun clear() {
        this.value = unCheckedValue
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

    override fun displayNewValue() {
        editView?.let {
            if (it is AppCompatCheckBox) {
                it.isChecked = isChecked()
            }
        }
    }
}