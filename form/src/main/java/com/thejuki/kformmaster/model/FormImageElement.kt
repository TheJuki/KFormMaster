package com.thejuki.kformmaster.model

import android.graphics.drawable.Drawable
import com.github.dhaval2404.imagepicker.constant.ImageProvider
import com.squareup.picasso.Transformation
import com.thejuki.kformmaster.helper.ImagePickerOptions
import com.thejuki.kformmaster.widget.ProgressWheel
import java.io.File

open class FormImageElement: BaseFormElement<String> {
    var onSelectImage: ((image: File?) -> Unit)? = null
    var onCancel: (() -> Unit)? = null
    var onClear: (() -> Unit)? = null
    var loadingView : ProgressWheel? = null
    var defaultImage : Drawable? = null
    var imageTransformation: Transformation? = null
    internal var defaultPickerOptions = ImagePickerOptions()
    internal var imagePickerOptionsFor: ((ImagePickerOptions) -> ImagePickerOptions) = {
        if (imagePickerOptions != null)
            imagePickerOptions?.invoke(it)

        defaultPickerOptions
    }
    var imagePickerOptions: ((ImagePickerOptions) -> Unit)? = null

    var onClickListener: (() -> Unit)? = null

    internal var mOpenImagePicker: ((ImageProvider) -> Unit)? = null
    fun openImagePicker(provider: ImageProvider){
        mOpenImagePicker?.invoke(provider)
    }

    internal var mClearImage: (() -> Unit)? = null
    fun clearImage(){
        mClearImage?.invoke()
    }

    constructor() : super()
    constructor(tag: Int) : super()
}