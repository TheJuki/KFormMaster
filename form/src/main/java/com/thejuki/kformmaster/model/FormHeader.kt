package com.thejuki.kformmaster.model

import android.view.Gravity
import android.widget.TextView
import com.thejuki.kformmaster.helper.FormBuildHelper

/**
 * Form Header
 *
 * Form element for Header TextView
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormHeader(tag: Int = -1, title: String? = null) : BaseFormElement<String>(tag) {

    constructor(title: String? = null) : this(-1, title)

    init {
        this.title = title
    }

    /**
     * Enable to collapse/un-collapse elements below the header
     * when the header is tapped
     */
    var collapsible: Boolean = false

    /**
     * Indicates if elements under header are collapsed or not
     *
     * Note: Use setAllCollapsed(collapse, formBuilder) to collapse/unCollapse elements after
     * initialization
     */
    var allCollapsed: Boolean = false

    override var editViewGravity: Int = Gravity.START
        set(value) {
            field = value
            editView?.let {
                if (it is TextView) {
                    it.gravity = value
                }
            }
        }

    /**
     * Collapse or Uncollapse all elements under the header until the next header
     */
    fun setAllCollapsed(collapse: Boolean, formBuilder: FormBuildHelper) {
        this.allCollapsed = collapse

        val index = formBuilder.elements.indexOf(this) + 1
        if (index != formBuilder.elements.size) {
            for (i in index until formBuilder.elements.size) {
                if (formBuilder.elements[i] is FormHeader) {
                    break
                }
                formBuilder.elements[i].visible = !collapse
            }
        }
    }

    /**
     * No validation needed
     */
    override val isValid: Boolean
        get() = true
}