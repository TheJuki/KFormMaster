package com.thejuki.kformmaster.view

import android.app.Activity
import android.graphics.BitmapFactory
import android.graphics.drawable.PictureDrawable
import android.util.Base64
import android.view.View
import android.webkit.URLUtil
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.AppCompatTextView
import com.caverock.androidsvg.SVG
import com.github.dhaval2404.imagepicker.ImagePicker
import com.github.vivchar.rendererrecyclerviewadapter.ViewRenderer
import com.thejuki.kformmaster.R
import com.thejuki.kformmaster.extensions.dpToPx
import com.thejuki.kformmaster.extensions.setBitmapImage
import com.thejuki.kformmaster.extensions.setDrawableImage
import com.thejuki.kformmaster.extensions.setNetworkImage
import com.thejuki.kformmaster.helper.FormBuildHelper
import com.thejuki.kformmaster.helper.FormViewFinder
import com.thejuki.kformmaster.model.FormImageElement
import java.nio.charset.Charset

/**
 * Form Image ViewRenderer
 *
 * View Binder for [FormImageElement]
 *
 * @author **soareseneves** ([GitHub](https://github.com/soareseneves))
 * @version 1.0
 */
class FormImageViewRenderer(private val formBuilder: FormBuildHelper, @LayoutRes private val layoutID: Int?) : BaseFormViewRenderer() {
    var viewRenderer = ViewRenderer(layoutID
            ?: R.layout.form_element_image, FormImageElement::class.java) { model, finder: FormViewFinder, _ ->
        val textViewError = finder.find(R.id.formElementError) as AppCompatTextView
        val mainViewLayout = finder.find(R.id.formElementMainLayout) as? LinearLayout
        val itemView = finder.getRootView() as View
        val dividerView = finder.find(R.id.formElementDivider) as? View
        val imageView = finder.find(R.id.formElementValue) as ImageView
        val changeImageLabelView = finder.find(R.id.formElementImageLabel) as AppCompatTextView
        baseSetup(model, dividerView, null, textViewError, itemView, mainViewLayout, imageView)

        model.changeImageLabelView = changeImageLabelView

        model.itemView?.bringToFront()

        model.editView?.let {
            val imageViewLayoutParams = it.layoutParams
            if (model.displayImageHeight != null) {
                imageViewLayoutParams.height = (model.displayImageHeight ?: 0).dpToPx()
            }
            if (model.displayImageWidth != null) {
                imageViewLayoutParams.width = (model.displayImageWidth ?: 0).dpToPx()
            }
            it.layoutParams = imageViewLayoutParams
        }

        if (model.defaultImage == null) {
            model.defaultImage = R.drawable.default_image
        }

        if (model.value != null) {
            imageView.setLayerType(View.LAYER_TYPE_HARDWARE, null)

            if (URLUtil.isFileUrl(model.valueAsString) || URLUtil.isNetworkUrl(model.valueAsString)) {
                imageView.setNetworkImage(model.valueAsString, model.applyCircleCrop)
                { model.onInitialImageLoaded?.invoke() }
            } else if (URLUtil.isDataUrl(model.valueAsString)) {
                val pureBase64Encoded = model.valueAsString.substring(model.valueAsString.indexOf(",") + 1)
                val decodedBytes = Base64.decode(pureBase64Encoded, Base64.DEFAULT)

                if (model.valueAsString.substring(0, model.valueAsString.indexOf(",")) == "data:image/svg+xml;base64") {
                    imageView.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
                    val decodedString = String(decodedBytes, Charset.forName("UTF-8"))
                    val pictureDrawable = PictureDrawable(SVG.getFromString(decodedString).renderToPicture())
                    imageView.setImageDrawable(pictureDrawable)
                } else {
                    val decodedBitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
                    imageView.setBitmapImage(decodedBitmap, model.applyCircleCrop)
                }

                model.onInitialImageLoaded?.invoke()
            } else {
                if (model.defaultImage != null) {
                    imageView.setDrawableImage(model.defaultImage ?: 0, model.applyCircleCrop)
                    { model.onInitialImageLoaded?.invoke() }
                }
            }
        } else {
            if (model.defaultImage != null) {
                imageView.setDrawableImage(model.defaultImage ?: 0, model.applyCircleCrop)
                { model.onInitialImageLoaded?.invoke() }
            }
        }

        model.mOpenImagePicker = { imageProvider ->
            ImagePicker.with(itemView.context as Activity)
                    .provider(imageProvider)
                    .crop(model.imagePickerOptionsFor(model.defaultPickerOptions).cropX, model.imagePickerOptionsFor(model.defaultPickerOptions).cropY)
                    .maxResultSize(model.imagePickerOptionsFor(model.defaultPickerOptions).maxWidth, model.imagePickerOptionsFor(model.defaultPickerOptions).maxHeight)
                    .compress(model.imagePickerOptionsFor(model.defaultPickerOptions).maxSize)
                    .createIntent {
                        model.activityResultLauncher?.launch(it)
                    }
        }

        model.mClearImage = {
            if (model.defaultImage != null) {
                imageView.setDrawableImage(model.defaultImage ?: 0, model.applyCircleCrop)
                { model.onClear?.invoke() }
            } else {
                imageView.setImageDrawable(null)
                model.onClear?.invoke()
            }
        }

        model.initDialog()
    }
}