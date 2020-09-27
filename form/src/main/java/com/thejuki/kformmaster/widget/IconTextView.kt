package com.thejuki.kformmaster.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import androidx.appcompat.widget.AppCompatTextView

/**
 * Icon TextView
 *
 * Adds an icon to the TextView with an onClickListener.
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class IconTextView : AppCompatTextView, OnTouchListener {
    var iconLocation: Location = Location.LEFT

    var icon: Drawable? = null

    var iconPadding: Int = 20

    var listener: Listener? = null

    private var onIconTouchListener: OnTouchListener? = null

    private val displayedDrawable: Drawable?
        get() = compoundDrawables[iconLocation.idx]

    enum class Location(val idx: Int) {
        LEFT(0), RIGHT(2)
    }

    interface Listener {
        fun clickedIcon()
    }

    @SuppressLint("ClickableViewAccessibility")
    constructor(context: Context) : super(context) {
        super.setOnTouchListener(this)
    }

    @SuppressLint("ClickableViewAccessibility")
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        super.setOnTouchListener(this)
    }

    @SuppressLint("ClickableViewAccessibility")
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        super.setOnTouchListener(this)
    }

    override fun setOnTouchListener(onTouchListener: OnTouchListener) {
        this.onIconTouchListener = onTouchListener
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        if (displayedDrawable != null) {
            val x = event.x.toInt()
            val y = event.y.toInt()
            val left = if (iconLocation == Location.LEFT) 0 else width - paddingRight - (icon?.intrinsicWidth
                    ?: 0)
            val right = if (iconLocation == Location.LEFT) paddingLeft + (icon?.intrinsicWidth
                    ?: 0) else width
            val tappedIcon = x in left..right && y >= 0 && y <= bottom - top
            if (tappedIcon) {
                if (event.action == MotionEvent.ACTION_UP) {
                    listener?.clickedIcon()
                }
                return true
            }
        }
        return onIconTouchListener?.onTouch(v, event) == true
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