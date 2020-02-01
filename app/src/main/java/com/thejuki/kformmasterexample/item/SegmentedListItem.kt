package com.thejuki.kformmasterexample.item

import androidx.annotation.DrawableRes
import com.thejuki.kformmaster.model.FormSegmentedElement
import com.thejuki.kformmaster.widget.SegmentedDrawable

/**
 * List Item
 *
 * An example class used for segmented
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
data class SegmentedListItem(val id: Long? = null,
                             val name: String? = null,
                             @DrawableRes override var drawableRes: Int? = 0,
                             override var drawableDirection: FormSegmentedElement.DrawableDirection? =
                                     FormSegmentedElement.DrawableDirection.Top,
                             override var height: Int? = null,
                             override var width: Int? = null
) : SegmentedDrawable {

    // Text that is displayed in the segmented button
    override fun toString(): String {
        return name.orEmpty()
    }
}