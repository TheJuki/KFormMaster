package com.thejuki.kformmaster.view

import android.content.Context
import android.support.v7.widget.AppCompatTextView
import com.github.vivchar.rendererrecyclerviewadapter.ViewHolder
import com.github.vivchar.rendererrecyclerviewadapter.ViewState
import com.github.vivchar.rendererrecyclerviewadapter.ViewStateProvider
import com.github.vivchar.rendererrecyclerviewadapter.binder.ViewBinder
import com.thejuki.kformmaster.R
import com.thejuki.kformmaster.helper.FormBuildHelper
import com.thejuki.kformmaster.model.FormHeader
import com.thejuki.kformmaster.state.FormHeaderViewState

/**
 * Form Header ViewBinder
 *
 * View Binder for [FormHeader]
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormHeaderViewBinder(private val context: Context, private val formBuilder: FormBuildHelper) : BaseFormViewBinder() {
    var viewBinder = ViewBinder(R.layout.form_element_header, FormHeader::class.java, { model, finder, _ ->
        val textViewTitle = finder.find(R.id.formElementTitle) as AppCompatTextView

        textViewTitle.text = model.title

        model.titleView = textViewTitle

    }, object : ViewStateProvider<FormHeader, ViewHolder> {
        override fun createViewStateID(model: FormHeader): Int {
            return model.id
        }

        override fun createViewState(holder: ViewHolder): ViewState<ViewHolder> {
            return FormHeaderViewState(holder)
        }
    })
}
