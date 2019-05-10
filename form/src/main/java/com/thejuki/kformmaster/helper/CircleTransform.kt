package com.thejuki.kformmaster.helper

import android.graphics.*
import com.squareup.picasso.Transformation

class CircleTransform : Transformation {
    private var x: Int = 0
    private var y: Int = 0
    private var borderColor : Int? = null
    private var borderRadius : Int? = null

    constructor(borderColor: Int = Color.WHITE, borderRadius: Int = 2){
        this.borderColor = borderColor
        this.borderRadius = borderRadius
    }

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

        if (borderColor != null && borderRadius != null) {
            val r = size / 2f

            // Prepare the background
            val paintBg = Paint()
            paintBg.color = borderColor!!
            paintBg.isAntiAlias = true

            // Draw the background circle
            canvas.drawCircle(r, r, r, paintBg)

            // Draw the image smaller than the background so a little border will be seen
            canvas.drawCircle(r, r, r - borderRadius!!, paint)
        }

        squaredBitmap.recycle()
        return bitmap
    }

    override fun key() = "circle(x=$x,y=$y)"
}