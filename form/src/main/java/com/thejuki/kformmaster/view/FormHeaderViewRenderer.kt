package com.thejuki.kformmaster.view

import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.AppCompatTextView
import com.github.vivchar.rendererrecyclerviewadapter.ViewRenderer
import com.thejuki.kformmaster.R
import com.thejuki.kformmaster.helper.FormBuildHelper
import com.thejuki.kformmaster.helper.FormViewFinder
import com.thejuki.kformmaster.model.FormHeader

/**
 * Form Header ViewRenderer
 *
 * View Binder for [FormHeader]
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormHeaderViewRenderer(private val formBuilder: FormBuildHelper, @LayoutRes private val layoutID: Int?) : BaseFormViewRenderer() {
    val viewRenderer = ViewRenderer(layoutID
            ?: R.layout.form_element_header, FormHeader::class.java) { model, finder: FormViewFinder, _ ->
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
    }
}
