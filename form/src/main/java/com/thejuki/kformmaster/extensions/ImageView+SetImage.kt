package com.thejuki.kformmaster.extensions

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.squareup.picasso.Callback
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import com.squareup.picasso.Transformation
import java.io.File

/**
 * ImageView Extensions
 *
 * Adds "setImage" methods to ImageView.
 *
 * @author **soareseneves** ([GitHub](https://github.com/soareseneves))
 * @version 1.0
 */
fun ImageView.setImage(url: String, transformation: Transformation? = null, defaultImage: Drawable? = null, completionHandler: (() -> Unit)? = null) {
    if (transformation != null) {
        Picasso.get().load(url).memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).transform(transformation).into(this, object : Callback {
            override fun onSuccess() {
                completionHandler?.invoke()
            }

            override fun onError(e: java.lang.Exception?) {
                this@setImage.setImageDrawable(defaultImage)
                completionHandler?.invoke()
            }
        })
    } else {
        Picasso.get().load(url).memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).into(this, object : Callback {
            override fun onSuccess() {
                completionHandler?.invoke()
            }

            override fun onError(e: java.lang.Exception?) {
                this@setImage.setImageDrawable(defaultImage)
                completionHandler?.invoke()
            }
        })
    }
}

fun ImageView.setImage(file: File?, transformation: Transformation? = null, defaultImage: Drawable? = null, completionHandler: (() -> Unit)? = null) {
    if (file != null) {
        if (transformation != null) {
            Picasso.get().load(file).memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).transform(transformation).into(this, object : Callback {
                override fun onSuccess() {
                    completionHandler?.invoke()
                }

                override fun onError(e: java.lang.Exception?) {
                    this@setImage.setImageDrawable(defaultImage)
                    completionHandler?.invoke()
                }
            })
        } else {
            Picasso.get().load(file).memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).into(this, object : Callback {
                override fun onSuccess() {
                    completionHandler?.invoke()
                }

                override fun onError(e: java.lang.Exception?) {
                    this@setImage.setImageDrawable(defaultImage)
                    completionHandler?.invoke()
                }
            })
        }
    }
}

fun ImageView.setImage(resourceId: Int?, transformation: Transformation? = null, defaultImage: Drawable? = null, completionHandler: (() -> Unit)? = null) {
    if (resourceId != null) {
        if (transformation != null) {
            Picasso.get().load(resourceId).memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).transform(transformation).into(this, object : Callback {
                override fun onSuccess() {
                    completionHandler?.invoke()
                }

                override fun onError(e: java.lang.Exception?) {
                    this@setImage.setImageDrawable(defaultImage)
                    completionHandler?.invoke()
                }
            })
        } else {
            Picasso.get().load(resourceId).memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).into(this, object : Callback {
                override fun onSuccess() {
                    completionHandler?.invoke()
                }

                override fun onError(e: java.lang.Exception?) {
                    this@setImage.setImageDrawable(defaultImage)
                    completionHandler?.invoke()
                }
            })
        }
    }
}

