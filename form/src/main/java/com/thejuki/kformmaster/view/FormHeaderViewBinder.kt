package com.thejuki.kformmaster.view

import android.content.Context
import android.support.annotation.LayoutRes
import android.support.v7.widget.AppCompatTextView
import android.view.View
import android.widget.LinearLayout
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
        val mainViewLayout = finder.find(R.id.formElementMainLayout) as? LinearLayout
        val itemView = finder.getRootView() as View
        baseSetup(model, null, textViewTitle, null, itemView, mainViewLayout)

        itemView.setOnClickListener {
            if (model.collapsible) {
                model.allCollapsed = !model.allCollapsed

                val index = formBuilder.elements.indexOf(model) + 1
                if (index != formBuilder.elements.size) {
                    for (i in index until formBuilder.elements.size) {
                        if (formBuilder.elements[i] is FormHeader) {
                            break
                        }
                        formBuilder.elements[i].visible = !model.allCollapsed
                    }
                }
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
