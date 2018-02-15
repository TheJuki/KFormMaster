package com.thejuki.kformmaster.view

import android.content.Context
import android.view.View
import android.widget.Button
import com.github.vivchar.rendererrecyclerviewadapter.binder.ViewBinder
import com.thejuki.kformmaster.R
import com.thejuki.kformmaster.helper.FormBuildHelper
import com.thejuki.kformmaster.model.FormButtonElement

/**
 * Form Button ViewBinder
 *
 * Renderer for FormEditTextElement
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormButtonViewBinder(private val context: Context, private val formBuilder: FormBuildHelper) : BaseFormViewBinder() {
    var viewBinder = ViewBinder(R.layout.form_element_button, FormButtonElement::class.java) { model, finder, _ ->
        val itemView = finder.getRootView() as View
        baseSetup(model, null, null, itemView)

        val button = finder.find(R.id.formElementValue) as Button
        button.text = model.valueAsString

        button.setOnClickListener {
            model.valueChanged?.onValueChanged(model)
            formBuilder.onValueChanged(model)
        }
    }
}
