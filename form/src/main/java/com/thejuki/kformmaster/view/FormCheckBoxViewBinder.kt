package com.thejuki.kformmaster.view

import android.content.Context
import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.appcompat.widget.AppCompatTextView
import com.github.vivchar.rendererrecyclerviewadapter.ViewHolder
import com.github.vivchar.rendererrecyclerviewadapter.ViewState
import com.github.vivchar.rendererrecyclerviewadapter.ViewStateProvider
import com.github.vivchar.rendererrecyclerviewadapter.binder.ViewBinder
import com.thejuki.kformmaster.R
import com.thejuki.kformmaster.helper.FormBuildHelper
import com.thejuki.kformmaster.model.FormCheckBoxElement
import com.thejuki.kformmaster.state.FormCheckBoxViewState

/**
 * Form CheckBox Binder
 *
 * View Binder for [FormCheckBoxElement]
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormCheckBoxViewBinder(private val context: Context, private val formBuilder: FormBuildHelper, @LayoutRes private val layoutID: Int?) : BaseFormViewBinder() {
    val viewBinder = ViewBinder(layoutID
            ?: R.layout.form_element_checkbox, FormCheckBoxElement::class.java, { model, finder, _ ->
        val textViewTitle = finder.find(R.id.formElementTitle) as? AppCompatTextView
        val textViewError = finder.find(R.id.formElementError) as? AppCompatTextView
        val dividerView = finder.find(R.id.formElementDivider) as? View
        val itemView = finder.getRootView() as View
        baseSetup(model, dividerView, textViewTitle, textViewError, itemView)

        val checkBox = finder.find(R.id.formElementValue) as AppCompatCheckBox
        checkBox.isChecked = model.isChecked()

        model.editView = checkBox

        setCheckBoxFocusEnabled(itemView, checkBox)

        checkBox.setOnCheckedChangeListener { _, isChecked ->
            model.error = null
            if (isChecked) {
                model.setValue(model.checkedValue)
            } else {
                model.setValue(model.unCheckedValue)
            }
            formBuilder.onValueChanged(model)
        }
    }, object : ViewStateProvider<FormCheckBoxElement<*>, ViewHolder> {
        override fun createViewStateID(model: FormCheckBoxElement<*>): Int {
            return model.id
        }

        override fun createViewState(holder: ViewHolder): ViewState<ViewHolder> {
            return FormCheckBoxViewState(holder)
        }
    })

    private fun setCheckBoxFocusEnabled(itemView: View, checkBox: AppCompatCheckBox) {
        itemView.setOnClickListener {
            checkBox.isChecked = !checkBox.isChecked
        }
    }
}
