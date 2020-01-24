package com.thejuki.kformmaster.model

import android.widget.TextView

/**
 * Form Label Element
 *
 * Form element for just displaying a title
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormLabelElement(tag: Int = -1) : BaseFormElement<String>(tag) {
    override fun displayNewValue() {
        editView?.let {
            if (it is TextView) {
                it.text = valueAsString
            }
        }
    }
}
