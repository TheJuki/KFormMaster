package com.thejuki.kformmaster.model

import androidx.appcompat.widget.SwitchCompat

/**
 * Form Switch Element
 *
 * Form element for Switch
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
open class FormSwitchElement<T>(tag: Int = -1) : BaseFormElement<T>(tag) {

    override fun clear() {
        this.value = offValue
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

    override fun displayNewValue() {
        editView?.let {
            if (it is SwitchCompat) {
                it.isChecked = isOn()
            }
        }
    }
}