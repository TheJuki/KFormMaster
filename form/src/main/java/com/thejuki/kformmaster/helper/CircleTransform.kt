package com.thejuki.kformmaster.helper

import android.graphics.*
import com.squareup.picasso.Transformation

/**
 * Circle Transform
 *
 * Used to transform a bitmap into a circle shape
 *
 * @author **soareseneves** ([GitHub](https://github.com/soareseneves))
 * @version 1.0
 */
class CircleTransform(private val borderColor: Int? = null, private var borderRadius: Int = 2) : Transformation {
    private var x: Int = 0
    private var y: Int = 0

    override fun transform(source: Bitmap): Bitmap {
        val size = Math.min(source.width, source.height)

        x = (source.width - size) / 2
        y = (source.height - size) / 2

        val squaredBitmap = Bitmap.createBitmap(source, x, y, size, size)
        if (squaredBitmap != source) {
            source.recycle()
        }

        val bitmap = Bitmap.createBitmap(size, size, source.config)

        val canvas = Canvas(bitmap)
        val paint = Paint()
        val shader = BitmapShader(squaredBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        paint.shader = shader
        paint.isAntiAlias = true

        val r = size / 2f
        if (borderColor != null) {
            val paintBg = Paint()
            paintBg.color = borderColor
            paintBg.isAntiAlias = true
            canvas.drawCircle(r, r, r, paintBg)
        } else {
            borderRadius = 0
        }

        canvas.drawCircle(r, r, r - borderRadius, paint)

        squaredBitmap.recycle()
        return bitmap
    }

    override fun key() = "circle(x=$x,y=$y)"
}