package com.thejuki.kformmaster.view

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import com.github.dhaval2404.imagepicker.ImagePicker
import com.github.vivchar.rendererrecyclerviewadapter.ViewHolder
import com.github.vivchar.rendererrecyclerviewadapter.ViewState
import com.github.vivchar.rendererrecyclerviewadapter.ViewStateProvider
import com.github.vivchar.rendererrecyclerviewadapter.binder.ViewBinder
import com.thejuki.kformmaster.R
import com.thejuki.kformmaster.helper.FormBuildHelper
import com.thejuki.kformmaster.model.FormImageElement
import com.thejuki.kformmaster.state.FormImageViewState
import com.thejuki.kformmaster.widget.ProgressWheel
import android.webkit.URLUtil
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.github.dhaval2404.imagepicker.constant.ImageProvider
import com.thejuki.kformmaster.extensions.setImage
import java.io.File


class FormImageViewBinder(private val context: Context, private val formBuilder: FormBuildHelper, @LayoutRes private val layoutID: Int?) : BaseFormViewBinder() {
    var viewBinder = ViewBinder( layoutID
            ?: R.layout.form_element_image, FormImageElement::class.java, { model, finder, _ ->
        val textViewError = finder.find(R.id.formElementError) as AppCompatTextView
        val mainViewLayout = finder.find(R.id.formElementMainLayout) as? LinearLayout
        val itemView = finder.getRootView() as View
        val dividerView = finder.find(R.id.formElementDivider) as? View
        model.loadingView = finder.find(R.id.loadingView) as ProgressWheel
        baseSetup(model, dividerView, null, textViewError, itemView, mainViewLayout)

        val imageView = finder.find(R.id.formElementValue) as ImageView

        model.loadingView?.resetCount()
        model.loadingView?.setText("")
        model.loadingView?.visibility = View.GONE
        model.itemView?.bringToFront()

        if (model.defaultImage == null){
            model.defaultImage = ContextCompat.getDrawable(context, R.drawable.default_image)
        }

        if (URLUtil.isFileUrl(model.valueAsString)){
            val imageFile = File(model.valueAsString)
            imageView.setImage(imageFile, model.imageTransformation, model.defaultImage)
        } else if (URLUtil.isNetworkUrl(model.valueAsString)){
            imageView.setImage(model.valueAsString, model.imageTransformation, model.defaultImage)
        }

        model.editView = imageView
        model.mOpenImagePicker = {imageProvider ->
            ImagePicker.with(context as AppCompatActivity)
                    .provider(imageProvider)
                    .crop(model.imagePickerOptionsFor(model.defaultPickerOptions).cropX, model.imagePickerOptionsFor(model.defaultPickerOptions).cropY)
                    .maxResultSize(model.imagePickerOptionsFor(model.defaultPickerOptions).maxWidth, model.imagePickerOptionsFor(model.defaultPickerOptions).maxHeight)
                    .compress(model.imagePickerOptionsFor(model.defaultPickerOptions).maxSize)
                    .start { resultCode, data ->
                        when (resultCode) {
                            Activity.RESULT_OK -> {
                                val file = ImagePicker.getFile(data)!!
                                imageView.setImage(file, model.imageTransformation)
                                model.onSelectImage?.invoke(file)
                            }
                            ImagePicker.RESULT_ERROR -> model.onSelectImage?.invoke(null)
                            else -> model.onCancel?.invoke()
                        }
                    }
        }

        model.mClearImage = {
            imageView.setImageDrawable(model.defaultImage)
            model.onClear?.invoke()
        }

        itemView.setOnClickListener {
            if (model.onClickListener == null) {
                val options = arrayOf(context.getString(R.string.form_master_picker_camera), context.getString(R.string.form_master_picker_gallery), context.getString(R.string.form_master_picker_remove), context.getString(R.string.form_master_cancel))

                val builder = AlertDialog.Builder(textViewError.context, model.theme)
                builder.setTitle(context.getString(R.string.form_master_pick_one))
                builder.setItems(options) { _, which ->
                    when (which) {
                        0, 1 -> {
                            model.openImagePicker(if (which == 0) ImageProvider.CAMERA else ImageProvider.GALLERY)
                        }
                        2 -> {
                            model.clearImage()
                        }
                    }
                }
                builder.setMessage(null)
                builder.setPositiveButton(null, null)
                builder.setNegativeButton(null, null)

                val dialog = builder.create()
                dialog.show()
            }
        }

    }, object : ViewStateProvider<FormImageElement, ViewHolder> {
        override fun createViewStateID(model: FormImageElement): Int {
            return model.id
        }

        override fun createViewState(holder: ViewHolder): ViewState<ViewHolder> {
            return FormImageViewState(holder)
        }
    })
}