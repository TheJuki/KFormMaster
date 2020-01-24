package com.thejuki.kformmaster.widget

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton

/**
 * Icon Button
 *
 * Adds an icon to the Button.
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class IconButton @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatButton(context, attrs, defStyleAttr) {
    var iconLocation: Location = Location.LEFT

    var icon: Drawable? = null

    var iconPadding: Int = 20

    enum class Location(val idx: Int) {
        LEFT(0), RIGHT(2)
    }

    override fun setCompoundDrawables(left: Drawable?, top: Drawable?, right: Drawable?, bottom: Drawable?) {
        super.setCompoundDrawables(left, top, right, bottom)
        initIcon()
    }

    fun reInitIcon() {
        initIcon()
        setIconVisible()
    }

    private fun initIcon() {
        icon?.setBounds(0, 0, (icon?.intrinsicWidth ?: 0), (icon?.intrinsicHeight
                ?: 0))
        val min = paddingTop + (icon?.intrinsicHeight ?: 0) + paddingBottom
        if (suggestedMinimumHeight < min) {
            minimumHeight = min
        }
    }

    private fun setIconVisible() {
        val cd = compoundDrawables

        // Reset icons
        if (cd[0] == icon) {
            cd[0] = null
        }
        if (cd[2] == icon) {
            cd[2] = null
        }

        super.setCompoundDrawables(if (iconLocation == Location.LEFT) icon else cd[0], cd[1], if (iconLocation == Location.RIGHT) icon else cd[2],
                cd[3])
        super.setCompoundDrawablePadding(iconPadding)
    }
}