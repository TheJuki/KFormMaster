package com.thejuki.kformmaster.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import android.widget.RadioButton

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

    var buttonCenterDrawable: Drawable? = null

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        buttonCenterDrawable?.let {
            it.state = drawableState
            val verticalGravity = gravity and Gravity.VERTICAL_GRAVITY_MASK
            val height = it.intrinsicHeight

            var y = 0

            when (verticalGravity) {
                Gravity.BOTTOM -> y = getHeight() - height
                Gravity.CENTER_VERTICAL -> y = (getHeight() - height) / 2
            }

            val buttonWidth = it.intrinsicWidth
            val buttonLeft = (width - buttonWidth) / 2
            it.setBounds(buttonLeft, y, buttonLeft + buttonWidth, y + height)
            it.draw(canvas)
        }
    }

}