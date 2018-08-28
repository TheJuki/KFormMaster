package com.thejuki.kformmaster.model

import android.widget.ArrayAdapter
import com.thejuki.kformmaster.token.ItemsCompletionView

/**
 * Form TokenAutoComplete Element
 *
 * Form element for ItemsCompletionView
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormTokenAutoCompleteElement<T : List<*>>(tag: Int = -1) : BaseFormElement<T>(tag) {

    override val isValid: Boolean
        get() = !required || (value != null && value?.isEmpty() == false)

    override fun clear() {
        this.value = null
        (this.editView as? ItemsCompletionView)?.clear()
    }

    /**
     * Form Element Options
     */
    var options: T? = null

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
     * arrayAdapter builder setter
     */
    fun setArrayAdapter(arrayAdapter: ArrayAdapter<*>?): FormTokenAutoCompleteElement<T> {
        this.arrayAdapter = arrayAdapter
        return this
    }

    /**
     * dropdownWidth builder setter
     */
    fun setDropdownWidth(dropdownWidth: Int?): FormTokenAutoCompleteElement<T> {
        this.dropdownWidth = dropdownWidth
        return this
    }

    /**
     * Options builder setter
     */
    fun setOptions(options: T): FormTokenAutoCompleteElement<T> {
        this.options = options
        return this
    }
}
