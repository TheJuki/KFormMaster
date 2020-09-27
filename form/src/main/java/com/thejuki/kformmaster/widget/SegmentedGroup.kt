package com.thejuki.kformmaster.widget

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.*
import android.util.AttributeSet
import android.util.StateSet
import android.util.TypedValue
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.thejuki.kformmaster.R
import java.util.*

/**
 * Segmented Group
 *
 * Creates a group of radio buttons
 * @see ([android-segmented-control](https://github.com/Kaopiz/android-segmented-control/blob/master/library/src/main/java/info/hoang8f/android/segmented/SegmentedGroup.java))
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class SegmentedGroup : RadioGroup {

    var holdup: Boolean = true

    private var mMarginDp: Int = 0
    private var mTintColor: Int = 0
    private var mDisabledColor: Int = 0
    private var mTextSize: Float = 0f
    private var mPadding: Int = 0
    private var mUnCheckedTintColor: Int = 0
    private var mCheckedTextColor = Color.WHITE
    private var mLayoutSelector: LayoutSelector? = null
    private var mCornerRadius: Float = 0f
    private var mCheckedChangeListener: RadioGroup.OnCheckedChangeListener? = null
    private var mDrawableMap: HashMap<Int, TransitionDrawable> = HashMap()
    private var mLastCheckId: Int = 0

    constructor(context: Context) : super(context) {
        mTintColor = ResourcesCompat.getColor(resources, R.color.colorFormMasterElementRadioSelected, null)
        mDisabledColor = ResourcesCompat.getColor(resources, R.color.colorFormMasterElementTextDisabled, null)
        mUnCheckedTintColor = ResourcesCompat.getColor(resources, R.color.colorFormMasterElementRadioUnSelected, null)
        mMarginDp = resources.getDimension(R.dimen.elementRadioStrokeBorder).toInt()
        mCornerRadius = resources.getDimension(R.dimen.elementRadioCornerRadius)
        mTextSize = resources.getDimension(R.dimen.elementTextValueSize)
        mPadding = resources.getDimension(R.dimen.elementRadioPadding).toInt()
        mLayoutSelector = LayoutSelector(mCornerRadius)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        mTintColor = ResourcesCompat.getColor(resources, R.color.colorFormMasterElementRadioSelected, null)
        mDisabledColor = ResourcesCompat.getColor(resources, R.color.colorFormMasterElementTextDisabled, null)
        mUnCheckedTintColor = ResourcesCompat.getColor(resources, R.color.colorFormMasterElementRadioUnSelected, null)
        mMarginDp = resources.getDimension(R.dimen.elementRadioStrokeBorder).toInt()
        mCornerRadius = resources.getDimension(R.dimen.elementRadioCornerRadius)
        mTextSize = resources.getDimension(R.dimen.elementTextValueSize)
        mPadding = resources.getDimension(R.dimen.elementRadioPadding).toInt()
        mLayoutSelector = LayoutSelector(mCornerRadius)
        initAttrs(attrs)
    }

    /* Reads the attributes from the layout */
    private fun initAttrs(attrs: AttributeSet) {
        val typedArray = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.SegmentedGroup,
                0, 0)

        try {
            mMarginDp = typedArray.getDimension(
                    R.styleable.SegmentedGroup_sc_border_width,
                    resources.getDimension(R.dimen.elementRadioStrokeBorder)).toInt()

            mCornerRadius = typedArray.getDimension(
                    R.styleable.SegmentedGroup_sc_corner_radius,
                    resources.getDimension(R.dimen.elementRadioCornerRadius))

            mPadding = typedArray.getDimension(
                    R.styleable.SegmentedGroup_sc_padding,
                    resources.getDimension(R.dimen.elementRadioPadding)).toInt()

            mTextSize = typedArray.getDimension(
                    R.styleable.SegmentedGroup_sc_text_size,
                    resources.getDimension(R.dimen.elementTextValueSize))

            mTintColor = typedArray.getColor(
                    R.styleable.SegmentedGroup_sc_tint_color,
                    ResourcesCompat.getColor(resources, R.color.colorFormMasterElementRadioSelected, null))

            mDisabledColor = typedArray.getColor(
                    R.styleable.SegmentedGroup_sc_disabled_color,
                    ResourcesCompat.getColor(resources, R.color.colorFormMasterElementTextDisabled, null))

            mCheckedTextColor = typedArray.getColor(
                    R.styleable.SegmentedGroup_sc_checked_text_color,
                    ResourcesCompat.getColor(resources, android.R.color.white, null))

            mUnCheckedTintColor = typedArray.getColor(
                    R.styleable.SegmentedGroup_sc_unchecked_tint_color,
                    ResourcesCompat.getColor(resources, R.color.colorFormMasterElementRadioUnSelected, null))
        } finally {
            typedArray.recycle()
        }
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)

        for (i in 0 until this.childCount) {
            val child = getChildAt(i)
            child.y = 0f
        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        //Use holo light for default
        updateBackground()
    }

    fun setProperties(marginDp: Int?, cornerRadius: Float?, tintColor: Int?,
                      checkedTextColor: Int?, unCheckedTintColor: Int?,
                      padding: Int?, textSize: Float?) {
        marginDp?.let {
            mMarginDp = it
        }
        cornerRadius?.let {
            mCornerRadius = it
            mLayoutSelector = LayoutSelector(mCornerRadius)
        }
        tintColor?.let {
            mTintColor = it
        }
        checkedTextColor?.let {
            mCheckedTextColor = it
        }
        unCheckedTintColor?.let {
            mUnCheckedTintColor = it
        }
        padding?.let {
            mPadding = it
        }
        textSize?.let {
            mTextSize = spToPx(it)
        }
    }

    fun setMarginDp(marginDp: Int) {
        mMarginDp = marginDp
        updateBackground()
    }

    fun setCornerRadius(cornerRadius: Float) {
        mCornerRadius = cornerRadius
        mLayoutSelector = LayoutSelector(mCornerRadius)
        updateBackground()
    }

    fun setTintColor(tintColor: Int) {
        mTintColor = tintColor
        updateBackground()
    }

    fun setCheckedTextColor(checkedTextColor: Int) {
        mCheckedTextColor = checkedTextColor
        updateBackground()
    }

    fun setUnCheckedTintColor(unCheckedTintColor: Int) {
        mUnCheckedTintColor = unCheckedTintColor
        updateBackground()
    }

    fun setPadding(padding: Int) {
        mPadding = padding
        updateBackground()
    }

    fun setTextSize(textSize: Float) {
        mTextSize = spToPx(textSize)
        updateBackground()
    }

    fun checkChild(value: String?) {
        if (holdup) {
            holdup = false
            return
        }
        for (i in 0 until this.childCount) {
            val child = this.getChildAt(i)
            if (child is RadioButton) {
                child.isChecked = child.text == value
            }
        }
        updateBackground()
    }

    fun clear() {
        clearCheck()
        updateBackground()
    }

    fun updateBackground() {
        mDrawableMap = HashMap()
        val count = super.getChildCount()
        for (i in 0 until count) {
            val child = getChildAt(i)
            updateBackground(child)

            // If this is the last view, don't set LayoutParams
            if (i == count - 1) break

            val initParams = child.layoutParams as LayoutParams
            val params = LayoutParams(initParams.width, initParams.height, initParams.weight)
            // Check orientation for proper margins
            if (orientation == LinearLayout.HORIZONTAL) {
                params.setMargins(0, 0, -mMarginDp, 0)
            } else {
                params.setMargins(0, 0, 0, -mMarginDp)
            }
            child.layoutParams = params
        }
    }

    private fun updateBackground(view: View) {
        mLayoutSelector?.let { layoutSelector ->
            val checked = layoutSelector.selected
            val unchecked = layoutSelector.unselected

            val tintColor = if (view.isEnabled) mTintColor else mDisabledColor

            //Set text color
            val colorStateList = ColorStateList(arrayOf(intArrayOf(-android.R.attr.state_checked), intArrayOf(android.R.attr.state_checked)),
                    intArrayOf(tintColor, mCheckedTextColor))
            (view as Button).setTextColor(colorStateList)

            view.compoundDrawables.iterator().forEach { drawable ->
                drawable?.let {
                    DrawableCompat.setTintList(it.mutate(), colorStateList)
                }
            }

            if (view is RadioButtonCenter) {
                view.buttonCenterDrawable?.let {
                    DrawableCompat.setTintList(it.mutate(), colorStateList)
                }
            }

            view.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize)
            view.setPadding(mPadding, mPadding, mPadding, mPadding)

            //Redraw with tint color
            val checkedDrawable = ResourcesCompat.getDrawable(resources, checked, null)?.mutate()
            val uncheckedDrawable = ResourcesCompat.getDrawable(resources, unchecked, null)?.mutate()
            (checkedDrawable as GradientDrawable).setColor(tintColor)
            checkedDrawable.setStroke(mMarginDp, tintColor)
            (uncheckedDrawable as GradientDrawable).setStroke(mMarginDp, tintColor)
            uncheckedDrawable.setColor(mUnCheckedTintColor)
            //Set proper radius
            checkedDrawable.cornerRadii = layoutSelector.getChildRadii(view)
            uncheckedDrawable.cornerRadii = layoutSelector.getChildRadii(view)

            val maskDrawable = ResourcesCompat.getDrawable(resources, unchecked, null)?.mutate() as GradientDrawable
            maskDrawable.setStroke(mMarginDp, tintColor)
            maskDrawable.setColor(mUnCheckedTintColor)
            maskDrawable.cornerRadii = layoutSelector.getChildRadii(view)
            val maskColor = Color.argb(50, Color.red(tintColor), Color.green(tintColor), Color.blue(tintColor))
            maskDrawable.setColor(maskColor)
            val pressedDrawable = LayerDrawable(arrayOf<Drawable>(uncheckedDrawable, maskDrawable))

            val drawables = arrayOf<Drawable>(uncheckedDrawable, checkedDrawable)
            val transitionDrawable = TransitionDrawable(drawables)
            if ((view as RadioButton).isChecked) {
                transitionDrawable.reverseTransition(0)
            }

            val stateListDrawable = StateListDrawable()
            stateListDrawable.addState(intArrayOf(-android.R.attr.state_checked, android.R.attr.state_pressed), pressedDrawable)
            stateListDrawable.addState(StateSet.WILD_CARD, transitionDrawable)

            mDrawableMap[view.getId()] = transitionDrawable

            //Set button background
            view.setBackground(stateListDrawable)

            super.setOnCheckedChangeListener { group, checkedId ->
                val current = mDrawableMap[checkedId]
                current?.reverseTransition(200)
                if (mLastCheckId != 0) {
                    val last = mDrawableMap[mLastCheckId]
                    last?.reverseTransition(200)
                }
                mLastCheckId = checkedId

                mCheckedChangeListener?.onCheckedChanged(group, checkedId)
            }
        }
    }

    private fun spToPx(sp: Float): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.resources.displayMetrics)
    }

    override fun onViewRemoved(child: View) {
        super.onViewRemoved(child)
        mDrawableMap.remove(child.id)
    }

    override fun setOnCheckedChangeListener(listener: OnCheckedChangeListener) {
        mCheckedChangeListener = listener
    }

    /*
         * This class is used to provide the proper layout based on the view.
         * Also provides the proper radius for corners.
         * The layout is the same for each selected left/top middle or right/bottom button.
         * float tables for setting the radius via Gradient.setCornerRadii are used instead
         * of multiple xml drawables.
         */
    private inner class LayoutSelector(r: Float    //this is the radios read by attributes or xml dimens
    ) {

        private var children: Int = 0
        private var child: Int = 0

        /* Returns the selected layout id based on view */
        val selected = R.drawable.radio_checked

        /* Returns the unselected layout id based on view */
        val unselected = R.drawable.radio_unchecked
        private val r1 = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0.1f, resources.displayMetrics)    //0.1 dp to px
        private val rLeft: FloatArray    // left radio button
        private val rRight: FloatArray   // right radio button
        private val rMiddle: FloatArray  // middle radio button
        private val rDefault: FloatArray // default radio button
        private val rTop: FloatArray     // top radio button
        private val rBot: FloatArray     // bot radio button
        private var radii: FloatArray? = null          // result radii float table

        init {
            children = -1 // Init this to force setChildRadii() to enter for the first time.
            child = -1 // Init this to force setChildRadii() to enter for the first time
            rLeft = floatArrayOf(r, r, r1, r1, r1, r1, r, r)
            rRight = floatArrayOf(r1, r1, r, r, r, r, r1, r1)
            rMiddle = floatArrayOf(r1, r1, r1, r1, r1, r1, r1, r1)
            rDefault = floatArrayOf(r, r, r, r, r, r, r, r)
            rTop = floatArrayOf(r, r, r, r, r1, r1, r1, r1)
            rBot = floatArrayOf(r1, r1, r1, r1, r, r, r, r)
        }

        private fun getChildren(): Int {
            return this@SegmentedGroup.childCount
        }

        private fun getChildIndex(view: View): Int {
            return this@SegmentedGroup.indexOfChild(view)
        }

        private fun setChildRadii(newChildren: Int, newChild: Int) {

            // If same values are passed, just return. No need to update anything
            if (children == newChildren && child == newChild)
                return

            // Set the new values
            children = newChildren
            child = newChild

            // if there is only one child provide the default radio button
            radii = when {
                children == 1 -> rDefault
                child == 0 -> //left or top
                    if (orientation == LinearLayout.HORIZONTAL) rLeft else rTop
                child == children - 1 -> //right or bottom
                    if (orientation == LinearLayout.HORIZONTAL) rRight else rBot
                else -> //middle
                    rMiddle
            }
        }

        /* Returns the radii float table based on view for Gradient.setRadii()*/
        fun getChildRadii(view: View): FloatArray? {
            val newChildren = getChildren()
            val newChild = getChildIndex(view)
            setChildRadii(newChildren, newChild)
            return radii
        }
    }
}