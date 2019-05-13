package com.thejuki.kformmaster.extensions

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.squareup.picasso.Callback
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import com.squareup.picasso.Transformation
import java.io.File

fun ImageView.setImage(url: String, transformation: Transformation? = null, defaultImage: Drawable? = null, completionHandler: (() -> Unit)? = null){
    if (transformation!= null){
        Picasso.get().load(url).fit().centerCrop().memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).transform(transformation).into(this, object: Callback {
            override fun onSuccess() {
                completionHandler?.invoke()
            }

            override fun onError(e: java.lang.Exception?) {
                this@setImage.setImageDrawable(defaultImage)
            }
        })
    } else {
        Picasso.get().load(url).fit().centerCrop().memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).into(this, object : Callback {
            override fun onSuccess() {
                completionHandler?.invoke()
            }

            override fun onError(e: java.lang.Exception?) {
                this@setImage.setImageDrawable(defaultImage)
            }
        })
    }
}

fun ImageView.setImage(file: File?, transformation: Transformation? = null, defaultImage: Drawable? = null, completionHandler: (() -> Unit)? = null){
    if (file != null) {
        if (transformation != null) {
            Picasso.get().load(file).fit().centerCrop().memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).transform(transformation).into(this, object : Callback {
                override fun onSuccess() {
                    completionHandler?.invoke()
                }

                override fun onError(e: java.lang.Exception?) {
                    this@setImage.setImageDrawable(defaultImage)
                }
            })
        } else {
            Picasso.get().load(file).fit().centerCrop().memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).into(this, object : Callback {
                override fun onSuccess() {
                    completionHandler?.invoke()
                }

                override fun onError(e: java.lang.Exception?) {
                    this@setImage.setImageDrawable(defaultImage)
                }
            })
        }
    }
}

fun ImageView.setImage(resourceId: Int, transformation: Transformation? = null, completionHandler: (() -> Unit)? = null){
    if (transformation != null) {
        Picasso.get().load(resourceId).fit().centerCrop().memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).transform(transformation).into(this, object : Callback {
            override fun onSuccess() {
                completionHandler?.invoke()
            }

            override fun onError(e: java.lang.Exception?) {}
        })
    } else {
        Picasso.get().load(resourceId).fit().centerCrop().memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).into(this, object : Callback {
            override fun onSuccess() {
                completionHandler?.invoke()
            }

            override fun onError(e: java.lang.Exception?) {}
        })
    }
}

