package com.thejuki.kformmasterexample.item

import android.support.annotation.DrawableRes
import com.thejuki.kformmaster.widget.SegmentedDrawable

/**
 * List Item
 *
 * An example class used for dropDown and multiCheckBox
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
data class SegmentedListItem(val id: Long? = null,
                             val name: String? = null,
                             @DrawableRes override var drawable: Int? = 0
) : SegmentedDrawable {
    override fun toString(): String {
        return name.orEmpty()
    }
}