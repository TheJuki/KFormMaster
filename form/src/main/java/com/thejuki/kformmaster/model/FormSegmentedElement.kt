package com.thejuki.kformmaster.model

import android.content.Context
import com.thejuki.kformmaster.helper.FormBuildHelper


/**
 * Form Segmented Element
 *
 * Form element for SegmentedGroup
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormSegmentedElement<T>(tag: Int = -1) : BaseFormElement<T>(tag) {

    /**
     * Form Element Options
     */
    var options: List<T>? = null
        set(value) {
            field = value
            reInitGroup()
        }

    /**
     * Options builder setter
     */
    fun setOptions(options: List<T>): BaseFormElement<T> {
        this.options = options
        return this
    }

    /**
     * Re-initializes the group
     * Should be called after the options list changes
     */
    fun reInitGroup(context: Context? = null, formBuilder: FormBuildHelper? = null) {

    }
}
