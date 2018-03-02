package com.thejuki.kformmaster.token

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet

import com.thejuki.kformmaster.R

/**
 * Token Text View
 *
 * AppCompatTextView with close/delete button
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class TokenTextView : AppCompatTextView {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override fun setSelected(selected: Boolean) {
        super.setSelected(selected)

        if (selected) {
            setTextColor(Color.WHITE)
        } else {
            setTextColor(Color.BLACK)
        }

        setCompoundDrawablesWithIntrinsicBounds(0, 0, if (selected) R.drawable.ic_close_black_24dp else 0, 0)
    }
}
