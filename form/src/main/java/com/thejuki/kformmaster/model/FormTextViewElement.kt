package com.thejuki.kformmaster.model

/**
 * Form TextView Element
 *
 * Form element for AppCompatTextView
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormTextViewElement(tag: Int = -1) : BaseFormElement<String>(tag) {

    /**
     * Nothing to clear
     */
    override fun clear() {}

    /**
     * No validation needed
     */
    override val isValid: Boolean
        get() = validityCheck()

    override var validityCheck = { true }
}