package com.thejuki.kformmaster.model

import android.app.AlertDialog
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.graphics.drawable.PictureDrawable
import android.util.Base64
import android.view.View
import android.webkit.URLUtil
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.caverock.androidsvg.SVG
import com.github.dhaval2404.imagepicker.constant.ImageProvider
import com.squareup.picasso.Transformation
import com.thejuki.kformmaster.R
import com.thejuki.kformmaster.extensions.setImage
import com.thejuki.kformmaster.helper.CircleTransform
import com.thejuki.kformmaster.helper.ImagePickerOptions
import java.io.File
import java.nio.charset.Charset

/**
 * Form Image Element
 *
 * Form element for Header TextView
 *
 * @author **soareseneves** ([GitHub](https://github.com/soareseneves))
 * @version 1.0
 */
class FormImageElement(tag: Int = -1) : BaseFormElement<String>(tag) {

    /**
     * Unit called when an image is selected. A File object is returned.
     */
    var onSelectImage: ((image: File?) -> Unit)? = null

    /**
     * Unit called when the initial image is loaded from value or defaultImage.
     */
    var onInitialImageLoaded: (() -> Unit)? = null

    /**
     * Unit called when an image selection is cancelled.
     */
    var onCancel: (() -> Unit)? = null

    /**
     * Unit called when the image is cleared.
     */
    var onClear: (() -> Unit)? = null

    /**
     * The default image to display when the initial value is null or invalid.
     */
    var defaultImage: Int? = null

    /**
     * The Picasso Image Transformation.
     * By default this is [CircleTransform].
     */
    var imageTransformation: Transformation? = CircleTransform()

    /**
     * Default Image Picker Options
     */
    internal var defaultPickerOptions = ImagePickerOptions()

    /**
     * Default Image Picker Handler
     */
    internal var imagePickerOptionsFor: ((ImagePickerOptions) -> ImagePickerOptions) = {
        if (imagePickerOptions != null)
            imagePickerOptions?.invoke(it)

        defaultPickerOptions
    }

    /**
     * Image Picker Options
     */
    var imagePickerOptions: ((ImagePickerOptions) -> Unit)? = null

    /**
     * Internal ImageProvider unit
     */
    internal var mOpenImagePicker: ((ImageProvider) -> Unit)? = null

    /**
     * Invokes the internal ImageProvider unit
     */
    fun openImagePicker(provider: ImageProvider) {
        mOpenImagePicker?.invoke(provider)
    }

    /**
     * Internal clear image unit
     */
    internal var mClearImage: (() -> Unit)? = null

    /**
     * Invokes the internal clear image unit
     */
    fun clearImage() {
        mClearImage?.invoke()
    }

    /**
     * Theme
     */
    var theme: Int = 0

    /**
     * Alert Dialog Builder
     * Used to call reInitDialog without needing context again.
     */
    private var alertDialogBuilder: AlertDialog.Builder? = null

    /**
     * Re-initializes the dialog
     */
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

        itemView?.setOnClickListener {
            // Invoke onClick Unit
            if (this.onClick == null) {
                alertDialog.show()
            } else {
                this.onClick?.invoke()
            }
        }
    }

    override fun displayNewValue() {
        editView?.let {
            if (it is ImageView) {
                var defaultImageDrawable: Drawable? = null

                this.defaultImage?.let { image ->
                    defaultImageDrawable = ContextCompat.getDrawable(it.context, image)
                }

                if (this.value != null) {
                    it.setLayerType(View.LAYER_TYPE_HARDWARE, null)

                    if (URLUtil.isFileUrl(this.valueAsString) || URLUtil.isNetworkUrl(this.valueAsString)) {
                        it.setImage(this.valueAsString, this.imageTransformation, defaultImageDrawable)
                        { this.onInitialImageLoaded?.invoke() }
                    } else if (URLUtil.isDataUrl(this.valueAsString)) {
                        val pureBase64Encoded = this.valueAsString.substring(this.valueAsString.indexOf(",") + 1)
                        val decodedBytes = Base64.decode(pureBase64Encoded, Base64.DEFAULT)

                        if (this.valueAsString.substring(0, this.valueAsString.indexOf(",")) == "data:image/svg+xml;base64") {
                            it.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
                            val decodedString = String(decodedBytes, Charset.forName("UTF-8"))
                            val pictureDrawable = PictureDrawable(SVG.getFromString(decodedString).renderToPicture())
                            it.setImageDrawable(pictureDrawable)
                        } else {
                            var decodedBitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
                            this.imageTransformation?.let { transformation ->
                                decodedBitmap = transformation.transform(decodedBitmap)
                            }
                            it.setImageBitmap(decodedBitmap)
                        }

                        this.onInitialImageLoaded?.invoke()
                    } else {
                        if (this.defaultImage != null) {
                            it.setImage(this.defaultImage, this.imageTransformation, defaultImageDrawable)
                            { this.onInitialImageLoaded?.invoke() }
                        }
                    }
                } else {
                    if (this.defaultImage != null) {
                        it.setImage(this.defaultImage, this.imageTransformation, defaultImageDrawable)
                        { this.onInitialImageLoaded?.invoke() }
                    }
                }
            }
        }
    }
}