package com.thejuki.kformmaster.model

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioGroup
import androidx.annotation.ColorInt
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import com.thejuki.kformmaster.R
import com.thejuki.kformmaster.extensions.dpToPx
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
open class FormSegmentedElement<T>(tag: Int = -1) : BaseFormElement<T>(tag) {

    override fun clear() {
        this.value = null
        (this.editView as? SegmentedGroup)?.clear()
    }

    /**
     * Form Element Enabled
     */
    override var enabled: Boolean = true
        set(value) {
            field = value
            reInitGroup()
            onEnabled(value)
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
     * By default this is DrawableDirection.Top.
     */
    var drawableDirection: DrawableDirection = DrawableDirection.Top
        set(value) {
            field = value
            reInitGroup()
        }

    /**
     * Disable to stack the radio buttons vertically
     */
    var horizontal: Boolean = true
        set(value) {
            field = value
            reInitGroup()
        }

    /**
     * Enable to fill the whole width
     */
    var fillSpace: Boolean = false
        set(value) {
            field = value
            reInitGroup()
        }

    /**
     * Enable to set the radio group layout_width to "wrap_content"
     */
    var radioGroupWrapContent: Boolean = false
        set(value) {
            field = value
            reInitGroup()
        }

    /**
     * Width of each radio button
     * By default, this is null which does not set the width.
     */
    var radioButtonWidth: Int? = null
        set(value) {
            field = value
            reInitGroup()
        }

    /**
     * Height of each radio button
     * By default, this is null which does not set the height.
     */
    var radioButtonHeight: Int? = null
        set(value) {
            field = value
            reInitGroup()
        }

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
    var radioButtonPadding: Int? = null
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
                if (radioGroupWrapContent) {
                    val segmentedGroupLayoutParams = it.layoutParams
                    segmentedGroupLayoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT
                    it.layoutParams = segmentedGroupLayoutParams
                }

                it.orientation = if (this@FormSegmentedElement.horizontal) LinearLayout.HORIZONTAL else LinearLayout.VERTICAL
                it.removeAllViews()

                options?.forEach { item ->
                    var hasSeparateHeights = false
                    val rb = LayoutInflater.from(it.context).inflate(R.layout.template_radiobutton_center, null) as RadioButtonCenter
                    rb.text = item.toString()
                    rb.id = ViewCompat.generateViewId()

                    if (!this.enabled) {
                        it.holdup = true
                    }

                    rb.isChecked = item == this@FormSegmentedElement.value
                    rb.isEnabled = this@FormSegmentedElement.enabled

                    radioButtonHeight?.let { height ->
                        rb.height = height.dpToPx()
                    }

                    radioButtonWidth?.let { width ->
                        rb.width = width.dpToPx()
                    }

                    if (item is SegmentedDrawable) {
                        val direction = item.drawableDirection ?: this.drawableDirection

                        if (direction == DrawableDirection.Center) {
                            rb.text = null
                            rb.buttonCenterDrawable = ResourcesCompat.getDrawable(it.context.resources, item.drawableRes
                                    ?: 0, null)?.mutate()
                        } else {
                            rb.setCompoundDrawablesWithIntrinsicBounds(
                                    if (direction == DrawableDirection.Left)
                                        item.drawableRes ?: 0 else 0,
                                    if (direction == DrawableDirection.Top)
                                        item.drawableRes ?: 0 else 0,
                                    if (direction == DrawableDirection.Right)
                                        item.drawableRes ?: 0 else 0,
                                    if (direction == DrawableDirection.Bottom)
                                        item.drawableRes ?: 0 else 0
                            )
                        }

                        item.height?.let { height ->
                            hasSeparateHeights = true
                            rb.height = height.dpToPx()
                        }

                        item.width?.let { width ->
                            rb.width = width.dpToPx()
                        }
                    }

                    if (fillSpace) {
                        if (hasSeparateHeights) {
                            it.addView(rb, RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT,
                                    RadioGroup.LayoutParams.WRAP_CONTENT,
                                    1.0f))
                        } else {
                            it.addView(rb, RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT,
                                    RadioGroup.LayoutParams.MATCH_PARENT,
                                    1.0f))
                        }
                    } else {
                        it.addView(rb)
                    }
                }

                it.updateBackground()
            }
        }
    }

    override fun displayNewValue() {
        editView?.let {
            if (it is SegmentedGroup) {
                it.checkChild(valueAsString)
            }
        }
    }
}
