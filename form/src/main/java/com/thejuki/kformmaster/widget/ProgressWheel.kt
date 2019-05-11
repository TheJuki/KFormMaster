package com.thejuki.kformmaster.widget

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Paint.Style
import android.graphics.RectF
import android.graphics.Shader
import android.util.AttributeSet
import android.view.View
import com.thejuki.kformmaster.R
import java.util.concurrent.Executors

/**
 * An indicator of progress, similar to Android's ProgressBar.
 *
 * @author Todd Davies
 *
 *
 * See MIT-LICENSE.txt for licence details
 */
class ProgressWheel
/**
 * The constructor for the ProgressWheel
 *
 * @param context
 * @param attrs
 */
    (context: Context, attrs: AttributeSet) : View(context, attrs) {

    //Sizes (with defaults)
    private var layoutHeight = 0
    private var layoutWidth = 0
    private var fullRadius = 100
    var circleRadius = 88
    var barLength = 60
    private var barWidth = 20
    private var rimWidth = 20
    private var textSize = 20
    private var contourSize = 0f

    //Padding (with defaults)
    private var paddingTop = 7
    private var paddingBottom = 7
    private var paddingLeft = 7
    private var paddingRight = 7

    //Colors (with defaults)
    private var barColor = -0x56000000
    private var contourColor = -0x56000000
    private var circleColor = 0x00000000
    private var rimColor = -0x55222223
    private var textColor = -0x1000000

    //Paints
    private val barPaint = Paint()
    private val circlePaint = Paint()
    private val rimPaint = Paint()
    private val textPaint = Paint()
    private val contourPaint = Paint()

    //Rectangles
    private var innerCircleBounds = RectF()
    private var circleBounds = RectF()
    private var circleOuterContour = RectF()
    private var circleInnerContour = RectF()

    private val percentValue : Float = 360f/100

    //Animation
    //The amount of pixels to move the bar by on each draw
    var spinSpeed = 2f
    //The number of milliseconds to wait in between each draw
    var delayMillis = 200
    private var progress = 0f

    val currentProgress : Float
        get() {
            return this.progress/percentValue
        }

    /**
     * Check if the wheel is currently spinning
     */
    var isSpinning = false
        internal set

    //Other
    internal var text: String? = ""
    private var splitText = arrayOf<String>()

    var rimShader: Shader
        get() = rimPaint.shader
        set(shader) {
            this.rimPaint.shader = shader
        }

    init {
        parseAttributes(
            context.obtainStyledAttributes(
                attrs,
                R.styleable.ProgressWheel
            )
        )
    }

    /*
     * When this is called, make the view square.
     * From: http://www.jayway.com/2012/12/12/creating-custom-android-views-part-4-measuring-and-how-to-force-a-view-to-be-square/
     *
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // The first thing that happen is that we call the superclass
        // implementation of onMeasure. The reason for that is that measuring
        // can be quite a complex process and calling the super method is a
        // convenient way to get most of this complexity handled.
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        // We can’t use getWidth() or getHight() here. During the measuring
        // pass the view has not gotten its final size yet (this happens first
        // at the start of the layout pass) so we have to use getMeasuredWidth()
        // and getMeasuredHeight().
        var size = 0
        val width = measuredWidth
        val height = measuredHeight
        val widthWithoutPadding = width - getPaddingLeft() - getPaddingRight()
        val heightWithoutPadding = height - getPaddingTop() - getPaddingBottom()

        // Finally we have some simple logic that calculates the size of the view
        // and calls setMeasuredDimension() to set that size.
        // Before we compare the width and height of the view, we remove the padding,
        // and when we set the dimension we add it back again. Now the actual content
        // of the view will be square, but, depending on the padding, the total dimensions
        // of the view might not be.
        val heightMode = View.MeasureSpec.getMode(heightMeasureSpec)
        val widthMode = View.MeasureSpec.getMode(widthMeasureSpec)
        if (heightMode != View.MeasureSpec.UNSPECIFIED && widthMode != View.MeasureSpec.UNSPECIFIED) {
            if (widthWithoutPadding > heightWithoutPadding) {
                size = heightWithoutPadding
            } else {
                size = widthWithoutPadding
            }
        } else {
            size = Math.max(heightWithoutPadding, widthWithoutPadding)
        }


        // If you override onMeasure() you have to call setMeasuredDimension().
        // This is how you report back the measured size.  If you don’t call
        // setMeasuredDimension() the parent will throw an exception and your
        // application will crash.
        // We are calling the onMeasure() method of the superclass so we don’t
        // actually need to call setMeasuredDimension() since that takes care
        // of that. However, the purpose with overriding onMeasure() was to
        // change the default behaviour and to do that we need to call
        // setMeasuredDimension() with our own values.
        setMeasuredDimension(
            size + getPaddingLeft() + getPaddingRight(),
            size + getPaddingTop() + getPaddingBottom()
        )
    }

    /**
     * Use onSizeChanged instead of onAttachedToWindow to get the dimensions of the view,
     * because this method is called after measuring the dimensions of MATCH_PARENT & WRAP_CONTENT.
     * Use this dimensions to setup the bounds and paints.
     */
    override fun onSizeChanged(newWidth: Int, newHeight: Int, oldWidth: Int, oldHeight: Int) {
        super.onSizeChanged(newWidth, newHeight, oldWidth, oldHeight)
        layoutWidth = newWidth
        layoutHeight = newHeight
        setupBounds()
        setupPaints()
        invalidate()
    }

    /**
     * Set the properties of the paints we're using to
     * draw the progress wheel
     */
    private fun setupPaints() {
        barPaint.color = barColor
        barPaint.isAntiAlias = true
        barPaint.style = Style.STROKE
        barPaint.strokeWidth = barWidth.toFloat()

        rimPaint.color = rimColor
        rimPaint.isAntiAlias = true
        rimPaint.style = Style.STROKE
        rimPaint.strokeWidth = rimWidth.toFloat()

        circlePaint.color = circleColor
        circlePaint.isAntiAlias = true
        circlePaint.style = Style.FILL

        textPaint.color = textColor
        textPaint.style = Style.FILL
        textPaint.isAntiAlias = true
        textPaint.textSize = textSize.toFloat()

        contourPaint.color = contourColor
        contourPaint.isAntiAlias = true
        contourPaint.style = Style.STROKE
        contourPaint.strokeWidth = contourSize
    }

    /**
     * Set the bounds of the component
     */
    private fun setupBounds() {
        // Add the offset
        paddingTop -= barWidth
        paddingBottom -= barWidth
        paddingLeft -= barWidth
        paddingRight -= barWidth

        val width = width
        val height = height

        innerCircleBounds = RectF(
            paddingLeft + 1.5f * barWidth,
            paddingTop + 1.5f * barWidth,
            width.toFloat() - paddingRight.toFloat() - 1.5f * barWidth,
            height.toFloat() - paddingBottom.toFloat() - 1.5f * barWidth
        )
        circleBounds = RectF(
            (paddingLeft + barWidth).toFloat(),
            (paddingTop + barWidth).toFloat(),
            (width - paddingRight - barWidth).toFloat(),
            (height - paddingBottom - barWidth).toFloat()
        )
        circleInnerContour = RectF(
            circleBounds.left + rimWidth / 2.0f + contourSize / 2.0f,
            circleBounds.top + rimWidth / 2.0f + contourSize / 2.0f,
            circleBounds.right - rimWidth / 2.0f - contourSize / 2.0f,
            circleBounds.bottom - rimWidth / 2.0f - contourSize / 2.0f
        )
        circleOuterContour = RectF(
            circleBounds.left - rimWidth / 2.0f - contourSize / 2.0f,
            circleBounds.top - rimWidth / 2.0f - contourSize / 2.0f,
            circleBounds.right + rimWidth / 2.0f + contourSize / 2.0f,
            circleBounds.bottom + rimWidth / 2.0f + contourSize / 2.0f
        )

        fullRadius = (width - paddingRight - barWidth) / 2
        circleRadius = fullRadius - barWidth + 1
    }

    /**
     * Parse the attributes passed to the view from the XML
     *
     * @param a the attributes to parse
     */
    private fun parseAttributes(a: TypedArray) {
        barWidth = a.getDimension(R.styleable.ProgressWheel_pwBarWidth, barWidth.toFloat()).toInt()
        rimWidth = a.getDimension(R.styleable.ProgressWheel_pwRimWidth, rimWidth.toFloat()).toInt()
        spinSpeed = a.getDimension(R.styleable.ProgressWheel_pwSpinSpeed, spinSpeed).toInt().toFloat()
        barLength = a.getDimension(R.styleable.ProgressWheel_pwBarLength, barLength.toFloat()).toInt()

        delayMillis = a.getInteger(R.styleable.ProgressWheel_pwDelayMillis, delayMillis)
        if (delayMillis < 0) {
            delayMillis = 10
        }

        // Only set the text if it is explicitly defined
        if (a.hasValue(R.styleable.ProgressWheel_pwText)) {
            setText(a.getString(R.styleable.ProgressWheel_pwText))
        }

        barColor = a.getColor(R.styleable.ProgressWheel_pwBarColor, barColor)
        textColor = a.getColor(R.styleable.ProgressWheel_pwTextColor, textColor)
        rimColor = a.getColor(R.styleable.ProgressWheel_pwRimColor, rimColor)
        circleColor = a.getColor(R.styleable.ProgressWheel_pwCircleColor, circleColor)
        contourColor = a.getColor(R.styleable.ProgressWheel_pwContourColor, contourColor)

        textSize = a.getDimension(R.styleable.ProgressWheel_pwTextSize, textSize.toFloat()).toInt()
        contourSize = a.getDimension(R.styleable.ProgressWheel_pwContourSize, contourSize)

        a.recycle()
    }

    //----------------------------------
    //Animation stuff
    //----------------------------------

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //Draw the inner circle
        canvas.drawArc(innerCircleBounds, 360f, 360f, false, circlePaint)
        //Draw the rim
        canvas.drawArc(circleBounds, 360f, 360f, false, rimPaint)
        canvas.drawArc(circleOuterContour, 360f, 360f, false, contourPaint)
        //canvas.drawArc(circleInnerContour, 360, 360, false, contourPaint);
        //Draw the bar
        if (isSpinning) {
            canvas.drawArc(circleBounds, progress - 90, barLength.toFloat(), false, barPaint)
        } else {
            canvas.drawArc(circleBounds, -90f, progress, false, barPaint)
        }
        //Draw the text (attempts to center it horizontally and vertically)
        val textHeight = textPaint.descent() - textPaint.ascent()
        val verticalTextOffset = textHeight / 2 - textPaint.descent()

        for (line in splitText) {
            val horizontalTextOffset = textPaint.measureText(line) / 2
            canvas.drawText(
                line,
                this.width / 2 - horizontalTextOffset,
                this.height / 2 + verticalTextOffset,
                textPaint
            )
        }
        if (isSpinning) {
            scheduleRedraw()
        }
    }

    private fun scheduleRedraw() {
        progress += spinSpeed
        if (progress > 360) {
            progress = 0f
        }
        postInvalidateDelayed(delayMillis.toLong())
    }

    /**
     * Reset the count (in increment mode)
     */
    fun resetCount() {
        progress = 0f
        setText("0%")
        invalidate()
    }

    /**
     * Turn off startSpinning mode
     */
    fun stopSpinning() {
        isSpinning = false
        progress = 0f
        postInvalidate()
    }


    /**
     * Puts the view on spin mode
     */
    fun startSpinning() {
        isSpinning = true
        postInvalidate()
    }

    fun incrementProgress(amount: Float) {
        isSpinning = false
        progress += amount
        if (progress > 360)
            progress %= 360f
        postInvalidate()
    }

    /**
     * Set the progress to a specific value
     */
    private fun mSetProgress(i: Float?) {
        isSpinning = false
        progress = i!!
        postInvalidate()
    }

    //----------------------------------
    //Getters + setters
    //----------------------------------

    /**
     * Set the text in the progress bar
     * Doesn't invalidate the view
     *
     * @param text the text to show ('\n' constitutes a new line)
     */
    fun setText(text: String?) {
        this.text = text
        splitText = this.text!!.split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
    }

    fun getBarWidth(): Int {
        return barWidth
    }

    fun setBarWidth(barWidth: Int) {
        this.barWidth = barWidth

        if (this.barPaint != null) {
            this.barPaint.strokeWidth = this.barWidth.toFloat()
        }
    }

    fun getTextSize(): Int {
        return textSize
    }

    fun setTextSize(textSize: Int) {
        this.textSize = textSize

        if (this.textPaint != null) {
            this.textPaint.textSize = this.textSize.toFloat()
        }
    }

    override fun getPaddingTop(): Int {
        return paddingTop
    }

    fun setPaddingTop(paddingTop: Int) {
        this.paddingTop = paddingTop
    }

    override fun getPaddingBottom(): Int {
        return paddingBottom
    }

    fun setPaddingBottom(paddingBottom: Int) {
        this.paddingBottom = paddingBottom
    }

    override fun getPaddingLeft(): Int {
        return paddingLeft
    }

    fun setPaddingLeft(paddingLeft: Int) {
        this.paddingLeft = paddingLeft
    }

    override fun getPaddingRight(): Int {
        return paddingRight
    }

    fun setPaddingRight(paddingRight: Int) {
        this.paddingRight = paddingRight
    }

    fun getBarColor(): Int {
        return barColor
    }

    fun setBarColor(barColor: Int) {
        this.barColor = barColor

        if (this.barPaint != null) {
            this.barPaint.color = this.barColor
        }
    }

    fun getCircleColor(): Int {
        return circleColor
    }

    fun setCircleColor(circleColor: Int) {
        this.circleColor = circleColor

        if (this.circlePaint != null) {
            this.circlePaint.color = this.circleColor
        }
    }

    fun getRimColor(): Int {
        return rimColor
    }

    fun setRimColor(rimColor: Int) {
        this.rimColor = rimColor

        if (this.rimPaint != null) {
            this.rimPaint.color = this.rimColor
        }
    }

    fun getTextColor(): Int {
        return textColor
    }

    fun setTextColor(textColor: Int) {
        this.textColor = textColor

        if (this.textPaint != null) {
            this.textPaint.color = this.textColor
        }
    }

    fun getRimWidth(): Int {
        return rimWidth
    }

    fun setRimWidth(rimWidth: Int) {
        this.rimWidth = rimWidth

        if (this.rimPaint != null) {
            this.rimPaint.strokeWidth = this.rimWidth.toFloat()
        }
    }

    fun getContourColor(): Int {
        return contourColor
    }

    fun setContourColor(contourColor: Int) {
        this.contourColor = contourColor

        if (contourPaint != null) {
            this.contourPaint.color = this.contourColor
        }
    }

    fun getContourSize(): Float {
        return this.contourSize
    }

    fun setContourSize(contourSize: Float) {
        this.contourSize = contourSize

        if (contourPaint != null) {
            this.contourPaint.strokeWidth = this.contourSize
        }
    }

    private val scheduler = Executors.newSingleThreadScheduledExecutor()

    fun setProgress(progress : Float){
        if (this.progress < 360) {
            this@ProgressWheel.mSetProgress(progress * percentValue)
            this@ProgressWheel.setText("")
        }
    }

    fun setProgressAndText(progress : Float, decimalNumbers: Int = 0){
        if (this.progress < 360 && (this.text ?: "").isNotEmpty()) {
            this@ProgressWheel.mSetProgress(progress * percentValue)
            if (decimalNumbers == 0)
                this@ProgressWheel.setText(percentValue.toInt().toString() + "%")
            else
                this@ProgressWheel.setText("%.${decimalNumbers}f".format(percentValue) + "%")
        }
    }

    fun toggle(state : Boolean){
        if (state) {
            this.resetCount()
            this.bringToFront()
        } else {

            //this makes the progress go to max before disappearing
            while (this.progress != 100f){
                if (this@ProgressWheel.progress != 100f && this@ProgressWheel.progress != 0f) //It's always safe to check again, specially after five seconds :P
                    this@ProgressWheel.setProgressAndText(100f)
            }

            this.resetCount()
            this.setText("")
        }
    }
}
/**
 * Increment the progress by 1 (of 360)
 */