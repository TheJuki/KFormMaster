package com.thejuki.kformmaster.extensions

import android.view.View
import android.view.ViewGroup

/**
 * View Extensions
 *
 * Adds "setMargins" method to View.
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */

/**
 * Sets the margins of a view
 */
fun View?.setMargins(left: Int, top: Int, right: Int, bottom: Int) {
    if (this?.layoutParams is ViewGroup.MarginLayoutParams) {
        val p = this.layoutParams as ViewGroup.MarginLayoutParams
        p.setMargins(left, top, right, bottom)
        this.requestLayout()
    }
}