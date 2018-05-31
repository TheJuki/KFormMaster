package com.thejuki.kformmaster.model

/**
 * Form Button Element
 *
 * Form element for Button
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormButtonElement(tag: Int = -1) : BaseFormElement<String>(tag) {

    /**
     * No validation needed
     */
    override val isValid: Boolean
        get() = true

    /**
     * Nothing to clear
     */
    override fun clear() {}
}
