package com.thejuki.kformmaster.model

import android.app.AlertDialog
import android.widget.ImageView
import com.github.dhaval2404.imagepicker.constant.ImageProvider
import com.squareup.picasso.Transformation
import com.thejuki.kformmaster.R
import com.thejuki.kformmaster.helper.CircleTransform
import com.thejuki.kformmaster.helper.ImagePickerOptions
import com.thejuki.kformmaster.widget.ProgressWheel
import java.io.File

open class FormImageElement: BaseFormElement<String> {
    var onSelectImage: ((image: File?) -> Unit)? = null
    var onCancel: (() -> Unit)? = null
    var onClear: (() -> Unit)? = null
    var loadingView : ProgressWheel? = null
    var defaultImage : Int? = 0
    var imageTransformation: Transformation? = CircleTransform()
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

    var theme: Int = 0

    private var alertDialogBuilder: AlertDialog.Builder? = null

    fun initDialog() {
        val imageView = this.editView as ImageView

        // reformat the options in format needed
        val options = arrayOf(imageView.context.getString(R.string.form_master_picker_camera), imageView.context.getString(R.string.form_master_picker_gallery), imageView.context.getString(R.string.form_master_picker_remove), imageView.context.getString(R.string.form_master_cancel))
        val selectedIndex: Int = options.indexOf(this.value)

        lateinit var alertDialog: AlertDialog

        if (alertDialogBuilder == null && imageView.context != null) {
            alertDialogBuilder = AlertDialog.Builder(imageView.context, theme)
        }

        alertDialogBuilder?.let {
            it.setTitle(imageView.context.getString(R.string.form_master_pick_one)).setMessage(null)
                .setPositiveButton(null, null)
                .setNegativeButton(null, null)

                it.setItems(options) { _, which ->
                    this.error = null
                    when (which) {
                        0, 1 -> {
                            openImagePicker(if (which == 0) ImageProvider.CAMERA else ImageProvider.GALLERY)
                        }
                        2 -> {
                            clearImage()
                        }
                    }
                }

            alertDialog = it.create()
        }

        itemView?.setOnClickListener{
            if (onClickListener == null) {
                alertDialog.show()
            } else {
                onClickListener?.invoke()
            }
        }
    }

    constructor() : super()
    constructor(tag: Int) : super()
}