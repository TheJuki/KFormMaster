package com.thejuki.kformmaster.extensions

import android.graphics.Bitmap
import android.net.Uri
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

/**
 * ImageView Extensions
 *
 * Adds "setImage" methods to ImageView.
 *
 * @author **soareseneves** ([GitHub](https://github.com/soareseneves))
 * @version 1.0
 */

fun ImageView.setDrawableImage(@DrawableRes resource: Int, applyCircle: Boolean = false, completionHandler: (() -> Unit)? = null) {
    val glide = Glide.with(this).load(resource).dontTransform()
    if (applyCircle) {
        glide.apply(RequestOptions.circleCropTransform()).into(this)
    } else {
        glide.into(this)
    }

    completionHandler?.invoke()
}

fun ImageView.setLocalImage(uri: Uri, applyCircle: Boolean = false, completionHandler: (() -> Unit)? = null) {
    val glide = Glide.with(this).load(uri)
    if (applyCircle) {
        glide.apply(RequestOptions.circleCropTransform()).into(this)
    } else {
        glide.into(this)
    }

    completionHandler?.invoke()
}

fun ImageView.setNetworkImage(url: String, applyCircle: Boolean = false, completionHandler: (() -> Unit)? = null) {
    val glide = Glide.with(this).load(url)
    if (applyCircle) {
        glide.apply(RequestOptions.circleCropTransform()).into(this)
    } else {
        glide.into(this)
    }

    completionHandler?.invoke()
}

fun ImageView.setBitmapImage(bitmap: Bitmap, applyCircle: Boolean = false, completionHandler: (() -> Unit)? = null) {
    val glide = Glide.with(this).load(bitmap)
    if (applyCircle) {
        glide.apply(RequestOptions.circleCropTransform()).into(this)
    } else {
        glide.into(this)
    }

    completionHandler?.invoke()
}
