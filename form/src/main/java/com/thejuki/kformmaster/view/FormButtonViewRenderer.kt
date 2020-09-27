package com.thejuki.kformmaster.view

import android.view.View
import androidx.annotation.LayoutRes
import com.github.vivchar.rendererrecyclerviewadapter.ViewRenderer
import com.thejuki.kformmaster.R
import com.thejuki.kformmaster.helper.FormBuildHelper
import com.thejuki.kformmaster.helper.FormViewFinder
import com.thejuki.kformmaster.model.FormButtonElement
import com.thejuki.kformmaster.widget.IconButton

/**
 * Form Button ViewRenderer
 *
 * View Binder for [FormButtonElement]
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormButtonViewRenderer(private val formBuilder: FormBuildHelper, @LayoutRes private val layoutID: Int?) : BaseFormViewRenderer() {
    val viewRenderer = ViewRenderer(layoutID
            ?: R.layout.form_element_button, FormButtonElement::class.java) { model, finder: FormViewFinder, _ ->
        val itemView = finder.getRootView() as View
        val dividerView = finder.find(R.id.formElementDivider) as? View
        val button = finder.find(R.id.formElementValue) as IconButton
        baseSetup(model, dividerView, itemView = itemView, editView = button)

        button.text = model.valueAsString

        button.setOnClickListener {
            // Invoke onClick Unit
            model.onClick?.invoke()

            model.setValue(model.value)
            formBuilder.onValueChanged(model)
        }

        button.iconLocation = IconButton.Location.valueOf(model.titleIconLocation.toString())
        button.icon = model.titleIcon
        button.iconPadding = model.titleIconPadding

        button.reInitIcon()
    }
}
