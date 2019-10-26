package com.thejuki.kformmaster.view

import android.content.Context
import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.AppCompatTextView
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
class FormHeaderViewBinder(private val context: Context, private val formBuilder: FormBuildHelper, @LayoutRes private val layoutID: Int?) : BaseFormViewBinder() {
    val viewBinder = ViewBinder(layoutID
            ?: R.layout.form_element_header, FormHeader::class.java, { model, finder, _ ->
        val textViewTitle = finder.find(R.id.formElementTitle) as? AppCompatTextView
        val itemView = finder.getRootView() as View
        baseSetup(model, textViewTitle = textViewTitle, itemView = itemView, editView = null)

        if (model.allCollapsed) {
            model.setAllCollapsed(model.allCollapsed, formBuilder)
        }

        itemView.setOnClickListener {
            // Invoke onClick Unit
            model.onClick?.invoke()

            if (model.collapsible) {
                model.setAllCollapsed(!model.allCollapsed, formBuilder)
            }
        }

    }, object : ViewStateProvider<FormHeader, ViewHolder> {
        override fun createViewStateID(model: FormHeader): Int {
            return model.id
        }

        override fun createViewState(holder: ViewHolder): ViewState<ViewHolder> {
            return FormHeaderViewState(holder)
        }
    })
}
