package com.thejuki.kformmaster.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.View.OnTouchListener
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.thejuki.kformmaster.R

/**
 * Clearable EditText
 *
 * Adds an X to clear the field and set the form element value to null.
 * @see ([droidparts](https://github.com/droidparts/droidparts/blob/master/droidparts-misc/src/org/droidparts/widget/ClearableEditText.java))
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class ClearableEditText : AppCompatEditText, OnTouchListener, OnFocusChangeListener, TextWatcher {
    override fun afterTextChanged(s: Editable?) {}

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    var displayClear: Boolean = false
        set(value) {
            field = value
            if (value) {
                setClearIconVisible(value)
            }
        }

    var alwaysShowClear: Boolean = false
        set(value) {
            field = value
            if (value) {
                setClearIconVisible(displayClear && alwaysShowClear)
            }
        }
    private var clearIconLocation: Location? = Location.RIGHT

    private var clearIcon: Drawable? = null
    private var listener: Listener? = null

    private var onClearableEditTextTouchListener: OnTouchListener? = null
    private var onClearableEditTextFocusChangeListener: OnFocusChangeListener? = null

    private val displayedDrawable: Drawable?
        get() = if (clearIconLocation != null) compoundDrawables[clearIconLocation?.idx
                ?: 0] else null

    enum class Location(val idx: Int) {
        LEFT(0), RIGHT(2)
    }

    interface Listener {
        fun didClearText()
    }

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init()
    }

    fun setListener(listener: Listener) {
        this.listener = listener
    }

    /**
     * null disables the icon
     */
    fun setClearIconLocation(clearIconLocation: Location?) {
        this.clearIconLocation = clearIconLocation
        initIcon()
    }

    override fun setOnTouchListener(onTouchListener: OnTouchListener) {
        this.onClearableEditTextTouchListener = onTouchListener
    }

    override fun setOnFocusChangeListener(onFocusChangeListener: OnFocusChangeListener) {
        this.onClearableEditTextFocusChangeListener = onFocusChangeListener
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        if (displayedDrawable != null) {
            val x = event.x.toInt()
            val y = event.y.toInt()
            val left = if (clearIconLocation == Location.LEFT) 0 else width - paddingRight - (clearIcon?.intrinsicWidth
                    ?: 0)
            val right = if (clearIconLocation == Location.LEFT) paddingLeft + (clearIcon?.intrinsicWidth
                    ?: 0) else width
            val tappedX = x in left..right && y >= 0 && y <= bottom - top
            if (tappedX) {
                if (event.action == MotionEvent.ACTION_UP) {
                    listener?.didClearText()
                }
                return true
            }
        }
        return onClearableEditTextTouchListener?.onTouch(v, event) == true
    }

    override fun onFocusChange(v: View, hasFocus: Boolean) {
        if (!alwaysShowClear) {
            if (hasFocus) {
                setClearIconVisible(displayClear && (text?.isNotEmpty() ?: false))
            } else {
                setClearIconVisible(false)
            }
        }

        onClearableEditTextFocusChangeListener?.onFocusChange(v, hasFocus)
    }

    override fun onTextChanged(charSequence: CharSequence, i: Int, i2: Int, i3: Int) {
        setClearIconVisible(displayClear && (text?.isNotEmpty() ?: false))
    }

    override fun setCompoundDrawables(left: Drawable?, top: Drawable?, right: Drawable?, bottom: Drawable?) {
        super.setCompoundDrawables(left, top, right, bottom)
        initIcon()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun init() {
        super.setOnTouchListener(this)
        super.setOnFocusChangeListener(this)
        addTextChangedListener(this)
        initIcon()
        setClearIconVisible(displayClear && alwaysShowClear)
    }

    private fun initIcon() {
        clearIcon = null
        if (clearIconLocation != null) {
            clearIcon = compoundDrawables[clearIconLocation?.idx ?: 0]
        }
        if (clearIcon == null) {
            clearIcon = ContextCompat.getDrawable(context, R.drawable.ic_close_black_24dp)
        }
        clearIcon?.setBounds(0, 0, (clearIcon?.intrinsicWidth ?: 0), (clearIcon?.intrinsicHeight
                ?: 0))
        val min = paddingTop + (clearIcon?.intrinsicHeight ?: 0) + paddingBottom
        if (suggestedMinimumHeight < min) {
            minimumHeight = min
        }
    }

    private fun setClearIconVisible(visible: Boolean) {
        val cd = compoundDrawables
        val displayed = displayedDrawable
        val wasVisible = displayed != null
        if (visible != wasVisible) {
            val x = if (visible) clearIcon else null
            super.setCompoundDrawables(if (clearIconLocation == Location.LEFT) x else cd[0], cd[1], if (clearIconLocation == Location.RIGHT) x else cd[2],
                    cd[3])
        }
    }
}