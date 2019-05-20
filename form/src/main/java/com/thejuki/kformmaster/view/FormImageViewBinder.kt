package com.thejuki.kformmaster.view

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.webkit.URLUtil
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.github.dhaval2404.imagepicker.ImagePicker
import com.github.vivchar.rendererrecyclerviewadapter.ViewHolder
import com.github.vivchar.rendererrecyclerviewadapter.ViewState
import com.github.vivchar.rendererrecyclerviewadapter.ViewStateProvider
import com.github.vivchar.rendererrecyclerviewadapter.binder.ViewBinder
import com.thejuki.kformmaster.R
import com.thejuki.kformmaster.extensions.setImage
import com.thejuki.kformmaster.helper.FormBuildHelper
import com.thejuki.kformmaster.model.FormImageElement
import com.thejuki.kformmaster.state.FormImageViewState
import java.io.File

/**
 * Form Image ViewBinder
 *
 * View Binder for [FormImageElement]
 *
 * @author **soareseneves** ([GitHub](https://github.com/soareseneves))
 * @version 1.0
 */
class FormImageViewBinder(private val context: Context, private val formBuilder: FormBuildHelper, @LayoutRes private val layoutID: Int?) : BaseFormViewBinder() {
    var viewBinder = ViewBinder( layoutID
            ?: R.layout.form_element_image, FormImageElement::class.java, { model, finder, _ ->
        val textViewError = finder.find(R.id.formElementError) as AppCompatTextView
        val mainViewLayout = finder.find(R.id.formElementMainLayout) as? LinearLayout
        val itemView = finder.getRootView() as View
        val dividerView = finder.find(R.id.formElementDivider) as? View
        baseSetup(model, dividerView, null, textViewError, itemView, mainViewLayout)

        val imageView = finder.find(R.id.formElementValue) as ImageView

        model.itemView?.bringToFront()

        if (model.defaultImage == null) {
            model.defaultImage = R.drawable.default_image
        }

        var defaultImageDrawable: Drawable? = null

        model.defaultImage?.let {
            defaultImageDrawable = ContextCompat.getDrawable(context, it)
        }

        if (model.value != null) {
            if (URLUtil.isFileUrl(model.valueAsString)) {
                val imageFile = File(model.valueAsString)
                imageView.setImage(imageFile, model.imageTransformation, defaultImageDrawable)
            } else if (URLUtil.isNetworkUrl(model.valueAsString)) {
                imageView.setImage(model.valueAsString, model.imageTransformation, defaultImageDrawable)
            } else {
                if (model.defaultImage != null) {
                    imageView.setImage(model.defaultImage, model.imageTransformation, defaultImageDrawable)
                }
            }
        } else {
            if (model.defaultImage != null) {
                imageView.setImage(model.defaultImage, model.imageTransformation, defaultImageDrawable)
            }
        }

        model.editView = imageView
        model.mOpenImagePicker = {imageProvider ->
            ImagePicker.with(context as Activity)
                    .provider(imageProvider)
                    .crop(model.imagePickerOptionsFor(model.defaultPickerOptions).cropX, model.imagePickerOptionsFor(model.defaultPickerOptions).cropY)
                    .maxResultSize(model.imagePickerOptionsFor(model.defaultPickerOptions).maxWidth, model.imagePickerOptionsFor(model.defaultPickerOptions).maxHeight)
                    .compress(model.imagePickerOptionsFor(model.defaultPickerOptions).maxSize)
                    .start { resultCode, data ->
                        when (resultCode) {
                            Activity.RESULT_OK -> {
                                val file = ImagePicker.getFile(data)
                                imageView.setImage(file, model.imageTransformation)
                                model.onSelectImage?.invoke(file)
                            }
                            ImagePicker.RESULT_ERROR -> model.onSelectImage?.invoke(null)
                            else -> model.onCancel?.invoke()
                        }
                    }
        }

        model.mClearImage = {
            if (model.defaultImage != null) {
                imageView.setImage(model.defaultImage, model.imageTransformation)
            } else {
                imageView.setImageDrawable(null)
            }
            model.onClear?.invoke()
        }

        model.initDialog()
    }, object : ViewStateProvider<FormImageElement, ViewHolder> {
        override fun createViewStateID(model: FormImageElement): Int {
            return model.id
        }

        override fun createViewState(holder: ViewHolder): ViewState<ViewHolder> {
            return FormImageViewState(holder)
        }
    })
}