package com.thejuki.kformmaster.model

import android.widget.ArrayAdapter
import android.widget.TextView

/**
 * Form AutoComplete Element
 *
 * Form element for AppCompatAutoCompleteTextView
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormAutoCompleteElement<T>(tag: Int = -1) : BaseFormElement<T>(tag) {

    override fun clear() {
        this.setValue(null)
    }

    /**
     * Form Element Options
     */
    var options: List<T>? = null

    /**
     * Because the written text in the EditText not always matched the
     * Values in options array, we keep the TypedString: what the user typed.
     */
    var typedString: String? = null

    /**
     * Override the default array adapter
     * This is useful for a custom asynchronous adapter
     */
    var arrayAdapter: ArrayAdapter<*>? = null

    /**
     * Override the default dropdown width
     */
    var dropdownWidth: Int? = null

    /**
     * Sets the value and typedString
     */
    override fun setValue(value: Any?): BaseFormElement<T> {
        typedString = value?.toString() ?: ""

        return super.setValue(value)
    }

    override fun displayNewValue() {
        editView?.let {
            if (it is TextView) {
                it.text = valueAsString
            }
        }
    }
}
