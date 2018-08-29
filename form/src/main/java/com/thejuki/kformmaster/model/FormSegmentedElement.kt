package com.thejuki.kformmaster.model

import android.content.Context
import android.support.v4.view.ViewCompat
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import com.thejuki.kformmaster.R
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

    override fun clear() {
        this.value = null
        (this.editView as? SegmentedGroup)?.clear()
    }

    /**
     * Disable to stack the radio buttons vertically
     */
    var horizontal: Boolean = true

    /**
     * Enable to fill the whole width
     */
    var fillSpace: Boolean = false

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
    fun setOptions(options: List<T>): FormSegmentedElement<T> {
        this.options = options
        return this
    }

    /**
     * Horizontal builder setter
     */
    fun setHorizontal(horizontal: Boolean): FormSegmentedElement<T> {
        this.horizontal = horizontal
        return this
    }

    /**
     * Fill Space builder setter
     */
    fun setFillSpace(fillSpace: Boolean): FormSegmentedElement<T> {
        this.fillSpace = fillSpace
        return this
    }

    /**
     * Re-initializes the group
     * Should be called after the options list changes
     */
    fun reInitGroup(context: Context? = null) {
        editView?.let {
            if (it is SegmentedGroup) {
                it.orientation = if (this@FormSegmentedElement.horizontal) LinearLayout.HORIZONTAL else LinearLayout.VERTICAL
                it.removeAllViews()

                options?.forEach { item ->
                    val rb = LayoutInflater.from(context).inflate(R.layout.template_radiobutton, null) as RadioButton
                    rb.text = item.toString()
                    rb.id = ViewCompat.generateViewId()
                    rb.isChecked = item == this@FormSegmentedElement.value

                    if (fillSpace) {
                        it.addView(rb, RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT,
                                RadioGroup.LayoutParams.WRAP_CONTENT,
                                1.0f))
                    } else {
                        it.addView(rb)
                    }
                }

                it.updateBackground()
            }
        }
    }
}
