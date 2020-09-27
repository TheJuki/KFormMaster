package com.thejuki.kformmaster.helper

import com.github.vivchar.rendererrecyclerviewadapter.DefaultDiffCallback
import com.thejuki.kformmaster.model.BaseFormElement

/**
 * Element Diff Callback
 *
 * Diff Callback used to check if elements in the list are the same element or not
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class ElementDiffCallback : DefaultDiffCallback<BaseFormElement<*>>() {
    override fun areItemsTheSame(oldItem: BaseFormElement<*>, newItem: BaseFormElement<*>): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: BaseFormElement<*>, newItem: BaseFormElement<*>): Boolean {
        return oldItem == newItem
    }
}