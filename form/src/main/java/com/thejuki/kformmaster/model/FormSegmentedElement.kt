package com.thejuki.kformmaster.model

import android.content.Context
import android.widget.LinearLayout
import android.widget.RadioButton
import com.thejuki.kformmaster.R
import com.thejuki.kformmaster.helper.FormBuildHelper
import com.thejuki.kformmaster.widget.SegmentedGroup


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
        editView?.let {
            if (it is SegmentedGroup) {
                it.orientation = LinearLayout.HORIZONTAL
                it.removeAllViews()

                options?.apply {
                    for ((index, value) in this.withIndex()) {
                        val rb = RadioButton(context, null, R.style.RadioButton)
                        rb.text = value.toString()
                        rb.id = 100 + index
                        rb.isChecked = value == this@FormSegmentedElement.value

                        it.addView(rb)
                    }
                }
            }
        }
    }
}
