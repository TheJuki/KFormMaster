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
        get() = validityCheck()

    override var validityCheck = { !required || (value != null && value?.isEmpty() == false) }

    override fun clear() {
        this.value = null
        (this.editView as? ItemsCompletionView)?.clearAsync()
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

    override fun displayNewValue() {
        editView?.let {
            if (it is ItemsCompletionView) {
                it.clearAsync()
                value?.forEach { item ->
                    it.addObjectAsync(item)
                }
            }
        }
    }
}
