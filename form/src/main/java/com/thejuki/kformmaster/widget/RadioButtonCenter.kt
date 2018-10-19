package com.thejuki.kformmaster.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import android.widget.RadioButton
import com.thejuki.kformmaster.R

/**
 * RadioButton Center
 *
 * Creates a group of radio buttons
 * @see ([Reuben Scratton's answer](https://stackoverflow.com/questions/4407553/android-radiobutton-button-drawable-gravity/4407803#4407803))
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class RadioButtonCenter(context: Context, attrs: AttributeSet) : RadioButton(context, attrs) {

    var buttonCenterDrawable: Drawable

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.RadioButtonCenter, 0, 0)
        @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
        buttonCenterDrawable = a.getDrawable(R.styleable.RadioButtonCenter_android_button)
        setButtonDrawable(android.R.color.transparent)
        a.recycle()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        buttonCenterDrawable.state = drawableState
        val verticalGravity = gravity and Gravity.VERTICAL_GRAVITY_MASK
        val height = buttonCenterDrawable.intrinsicHeight

        var y = 0

        when (verticalGravity) {
            Gravity.BOTTOM -> y = getHeight() - height
            Gravity.CENTER_VERTICAL -> y = (getHeight() - height) / 2
        }

        val buttonWidth = buttonCenterDrawable.intrinsicWidth
        val buttonLeft = (width - buttonWidth) / 2
        buttonCenterDrawable.setBounds(buttonLeft, y, buttonLeft + buttonWidth, y + height)
        buttonCenterDrawable.draw(canvas)
    }

}