package com.thejuki.kformmaster.view

import android.content.Context
import android.support.annotation.LayoutRes
import android.support.v7.widget.AppCompatTextView
import android.view.View
import com.github.vivchar.rendererrecyclerviewadapter.ViewHolder
import com.github.vivchar.rendererrecyclerviewadapter.ViewState
import com.github.vivchar.rendererrecyclerviewadapter.ViewStateProvider
import com.github.vivchar.rendererrecyclerviewadapter.binder.ViewBinder
import com.thejuki.kformmaster.R
import com.thejuki.kformmaster.helper.FormBuildHelper
import com.thejuki.kformmaster.model.FormLabelElement
import com.thejuki.kformmaster.state.FormLabelViewState

/**
 * Form Header ViewBinder
 *
 * View Binder for [FormLabelElement]
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormLabelViewBinder(private val context: Context, private val formBuilder: FormBuildHelper, @LayoutRes private val layoutID: Int?) : BaseFormViewBinder() {
    var viewBinder = ViewBinder(layoutID
            ?: R.layout.form_element_label, FormLabelElement::class.java, { model, finder, _ ->
        val textViewTitle = finder.find(R.id.formElementTitle) as? AppCompatTextView
        val itemView = finder.getRootView() as View
        baseSetup(model, textViewTitle, null, itemView)

    }, object : ViewStateProvider<FormLabelElement, ViewHolder> {
        override fun createViewStateID(model: FormLabelElement): Int {
            return model.id
        }

        override fun createViewState(holder: ViewHolder): ViewState<ViewHolder> {
            return FormLabelViewState(holder)
        }
    })
}
