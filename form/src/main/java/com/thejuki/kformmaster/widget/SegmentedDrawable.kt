package com.thejuki.kformmaster.widget

import com.thejuki.kformmaster.model.FormSegmentedElement

/**
 * Segmented Drawable
 *
 * Interface for the [FormSegmentedElement] to set the drawable for each radio button in the Segmented Group
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
interface SegmentedDrawable {
    var drawableRes: Int?

    /**
     * Drawable direction of the drawable in [SegmentedDrawable]
     * Overriding this is optional
     */
    var drawableDirection: FormSegmentedElement.DrawableDirection?
        get() = null
        set(_) = TODO()

    var height: Int?
        get() = null
        set(_) = TODO()

    var width: Int?
        get() = null
        set(_) = TODO()
}