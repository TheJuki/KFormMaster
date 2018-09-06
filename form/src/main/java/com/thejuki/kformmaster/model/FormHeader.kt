package com.thejuki.kformmaster.model

/**
 * Form Header
 *
 * Form element for Header TextView
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormHeader(tag: Int = -1) : BaseFormElement<String>(tag) {

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
}