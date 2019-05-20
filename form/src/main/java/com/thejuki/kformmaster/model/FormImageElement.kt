package com.thejuki.kformmaster.model

import android.app.AlertDialog
import android.widget.ImageView
import com.github.dhaval2404.imagepicker.constant.ImageProvider
import com.squareup.picasso.Transformation
import com.thejuki.kformmaster.R
import com.thejuki.kformmaster.helper.CircleTransform
import com.thejuki.kformmaster.helper.ImagePickerOptions
import java.io.File

/**
 * Form Image Element
 *
 * Form element for Header TextView
 *
 * @author **soareseneves** ([GitHub](https://github.com/soareseneves))
 * @version 1.0
 */
class FormImageElement(tag: Int = -1) : BaseFormElement<String>(tag) {
    var onSelectImage: ((image: File?) -> Unit)? = null
    var onCancel: (() -> Unit)? = null
    var onClear: (() -> Unit)? = null
    var defaultImage: Int? = null
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
                        0 -> {
                            openImagePicker(ImageProvider.CAMERA)
                        }
                        1 -> {
                            openImagePicker(ImageProvider.GALLERY)
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
}