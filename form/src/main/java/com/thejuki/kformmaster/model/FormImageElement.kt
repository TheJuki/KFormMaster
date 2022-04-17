package com.thejuki.kformmaster.model

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.drawable.PictureDrawable
import android.net.Uri
import android.util.Base64
import android.view.View
import android.webkit.URLUtil
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.widget.AppCompatTextView
import com.caverock.androidsvg.SVG
import com.github.dhaval2404.imagepicker.ImagePicker
import com.github.dhaval2404.imagepicker.ImagePickerActivity
import com.github.dhaval2404.imagepicker.constant.ImageProvider
import com.thejuki.kformmaster.R
import com.thejuki.kformmaster.extensions.*
import com.thejuki.kformmaster.helper.ImagePickerOptions
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
     * Change Image Label View
     */
    var changeImageLabelView: AppCompatTextView? = null
        set(value) {
            field = value
            changeImageLabelView?.let {
                it.visibility = if (!showChangeImageLabel) View.GONE else View.VISIBLE
                if (changeImageLabel != null) {
                    it.text = changeImageLabel
                }
            }
        }

    /**
     * Activity Result Launcher for [ImagePickerActivity]
     */
    var activityResultLauncher: ActivityResultLauncher<Intent>? = null

    /**
     * Unit called when an image is selected. A Uri is returned.
     */
    var onSelectImage: ((image: Uri?, error: String?) -> Unit)? = null

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
     * Display Image Width in DP
     * (optional - uses @dimen/elementImageDisplayWidth)
     */
    var displayImageWidth: Int? = null
        set(value) {
            field = value

            if (value != null) {
                editView?.let {
                    if (it is ImageView) {
                        val imageViewLayoutParams = it.layoutParams
                        imageViewLayoutParams.width = value.dpToPx()
                        it.layoutParams = imageViewLayoutParams
                    }
                }
            }
        }

    /**
     * Display Image Height in DP
     * (optional - uses @dimen/elementImageDisplayHeight)
     */
    var displayImageHeight: Int? = null
        set(value) {
            field = value
            if (value != null) {
                editView?.let {
                    if (it is ImageView) {
                        val imageViewLayoutParams = it.layoutParams
                        imageViewLayoutParams.height = value.dpToPx()
                        it.layoutParams = imageViewLayoutParams
                    }
                }
            }
        }

    /**
     * Change Image Label
     * (optional - uses R.string.form_master_picker_change_image)
     */
    var changeImageLabel: String? = null
        set(value) {
            field = value
            changeImageLabelView?.let {
                it.visibility = if (!showChangeImageLabel) View.GONE else View.VISIBLE
                it.text = changeImageLabel
            }
        }

    /**
     * Show the Change Image Label
     * By default, this is true.
     */
    var showChangeImageLabel: Boolean = true
        set(value) {
            field = value
            changeImageLabelView?.let {
                it.visibility = if (!showChangeImageLabel) View.GONE else View.VISIBLE
            }
        }

    /**
     * Apply Circle Crop
     * By default, this is true.
     */
    var applyCircleCrop: Boolean = true

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

    /**
     * Handles the Activity Result for the [ImagePickerActivity]
     */
    fun handleActivityResult(resultCode: Int, data: Intent?) {
        when (resultCode) {
            Activity.RESULT_OK -> {
                data?.data?.let {
                    (editView as ImageView).setLocalImage(it, applyCircleCrop)
                    onSelectImage?.invoke(it, null)
                }
            }
            ImagePicker.RESULT_ERROR -> onSelectImage?.invoke(null, ImagePicker.getError(data))
            else -> onCancel?.invoke()
        }
    }

    override fun displayNewValue() {
        editView?.let {
            if (it is ImageView) {
                if (this.value != null) {
                    it.setLayerType(View.LAYER_TYPE_HARDWARE, null)

                    if (URLUtil.isFileUrl(this.valueAsString) || URLUtil.isNetworkUrl(this.valueAsString)) {
                        it.setNetworkImage(this.valueAsString, applyCircleCrop)
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
                            val decodedBitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
                            it.setBitmapImage(decodedBitmap, applyCircleCrop)
                        }

                        this.onInitialImageLoaded?.invoke()
                    } else {
                        if (this.defaultImage != null) {
                            it.setDrawableImage(defaultImage ?: 0, applyCircleCrop)
                            { this.onInitialImageLoaded?.invoke() }
                        }
                    }
                } else {
                    if (this.defaultImage != null) {
                        it.setDrawableImage(defaultImage ?: 0, applyCircleCrop)
                        { this.onInitialImageLoaded?.invoke() }
                    }
                }
            }
        }
    }
}