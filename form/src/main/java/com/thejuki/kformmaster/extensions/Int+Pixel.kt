package com.thejuki.kformmaster.extensions

import android.content.res.Resources
import android.util.TypedValue

/**
 * Int Extensions
 *
 * Adds "dpToPx" method to Int.
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */

/**
 * Converts DP to PX (Density-independent Pixels to Pixels)
 */
fun Int?.dpToPx(): Int {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
            this?.toFloat() ?: 0f, Resources.getSystem().displayMetrics).toInt()
}
