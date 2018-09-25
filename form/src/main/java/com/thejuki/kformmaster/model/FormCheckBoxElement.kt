package com.thejuki.kformmaster.model

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
}