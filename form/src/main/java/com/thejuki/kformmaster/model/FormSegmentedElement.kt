package com.thejuki.kformmaster.model

import android.content.Context
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.RadioGroup
import androidx.annotation.ColorInt
import androidx.core.view.ViewCompat
import com.thejuki.kformmaster.R
import com.thejuki.kformmaster.widget.RadioButtonCenter
import com.thejuki.kformmaster.widget.SegmentedDrawable
import com.thejuki.kformmaster.widget.SegmentedGroup


/**
 * Form Segmented Element
 *
 * Form element for SegmentedGroup
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormSegmentedElement<T>(tag: Int = -1) : BaseFormElement<T>(tag) {

    override fun clear() {
        this.value = null
        (this.editView as? SegmentedGroup)?.clear()
    }

    /**
     * Drawable Direction
     */
    enum class DrawableDirection {
        Left,
        Right,
        Top,
        Bottom,
        Center
    }

    /**
     * Drawable direction of the drawable in [SegmentedDrawable]
     */
    var drawableDirection: DrawableDirection = DrawableDirection.Top

    /**
     * Disable to stack the radio buttons vertically
     */
    var horizontal: Boolean = true

    /**
     * Enable to fill the whole width
     */
    var fillSpace: Boolean = false

    /**
     * Form Element Options
     */
    var options: List<T>? = null
        set(value) {
            field = value
            reInitGroup()
        }

    /**
     * SegmentedGroup properties
     */


    /**
     * Margin for each radio button (stroke)
     */
    var marginDp: Int? = null
        set(value) {
            field = value
            editView?.let {
                if (it is SegmentedGroup && value != null) {
                    it.setMarginDp(value)
                }
            }
        }

    /**
     * Tint color for each radio button
     */
    @ColorInt
    var tintColor: Int? = null
        set(value) {
            field = value
            editView?.let {
                if (it is SegmentedGroup && value != null) {
                    it.setTintColor(value)
                }
            }
        }

    /**
     * Unchecked tint color for each radio button
     */
    @ColorInt
    var unCheckedTintColor: Int? = null
        set(value) {
            field = value
            editView?.let {
                if (it is SegmentedGroup && value != null) {
                    it.setUnCheckedTintColor(value)
                }
            }
        }

    /**
     * Checked text color for each radio button
     */
    @ColorInt
    var checkedTextColor: Int? = null
        set(value) {
            field = value
            editView?.let {
                if (it is SegmentedGroup && value != null) {
                    it.setCheckedTextColor(value)
                }
            }
        }

    /**
     * Corner radius for each radio button
     */
    var cornerRadius: Float? = null
        set(value) {
            field = value
            editView?.let {
                if (it is SegmentedGroup && value != null) {
                    it.setCornerRadius(value)
                }
            }
        }

    /**
     * Text Size (In SP) for each radio button
     */
    var textSize: Float? = null
        set(value) {
            field = value
            editView?.let {
                if (it is SegmentedGroup && value != null) {
                    it.setTextSize(value)
                }
            }
        }

    /**
     * Padding for each radio button
     */
    var padding: Int? = null
        set(value) {
            field = value
            editView?.let {
                if (it is SegmentedGroup && value != null) {
                    it.setPadding(value)
                }
            }
        }

    /**
     * Re-initializes the group
     * Should be called after the options list changes
     */
    fun reInitGroup() {
        editView?.let {
            if (it is SegmentedGroup) {
                it.orientation = if (this@FormSegmentedElement.horizontal) LinearLayout.HORIZONTAL else LinearLayout.VERTICAL
                it.removeAllViews()

                options?.forEach { item ->
                    val rb = LayoutInflater.from(it.context).inflate(R.layout.template_radiobutton_center, null) as RadioButtonCenter
                    rb.text = item.toString()
                    rb.id = ViewCompat.generateViewId()
                    rb.isChecked = item == this@FormSegmentedElement.value

                    if (item is SegmentedDrawable) {
                        if (drawableDirection == DrawableDirection.Center) {
                            rb.text = null
                            rb.buttonCenterDrawable = ResourcesCompat.getDrawable(it.context.resources, item.drawableRes
                                    ?: 0, null)?.mutate()
                        } else {
                            rb.setCompoundDrawablesWithIntrinsicBounds(
                                    if (drawableDirection == DrawableDirection.Left) (item.drawableRes
                                            ?: 0) else 0,
                                    if (drawableDirection == DrawableDirection.Top) (item.drawableRes
                                            ?: 0) else 0,
                                    if (drawableDirection == DrawableDirection.Right) (item.drawableRes
                                            ?: 0) else 0,
                                    if (drawableDirection == DrawableDirection.Bottom) (item.drawableRes
                                            ?: 0) else 0)
                        }
                    }

                    if (fillSpace) {
                        it.addView(rb, RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT,
                                RadioGroup.LayoutParams.WRAP_CONTENT,
                                1.0f))
                    } else {
                        it.addView(rb)
                    }
                }

                it.updateBackground()
            }
        }
    }
}
