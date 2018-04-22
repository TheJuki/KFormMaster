package com.thejuki.kformmaster.view

import android.content.Context
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.SwitchCompat
import android.view.View
import com.github.vivchar.rendererrecyclerviewadapter.ViewHolder
import com.github.vivchar.rendererrecyclerviewadapter.ViewState
import com.github.vivchar.rendererrecyclerviewadapter.ViewStateProvider
import com.github.vivchar.rendererrecyclerviewadapter.binder.ViewBinder
import com.thejuki.kformmaster.R
import com.thejuki.kformmaster.helper.FormBuildHelper
import com.thejuki.kformmaster.model.FormSwitchElement
import com.thejuki.kformmaster.state.FormSwitchViewState

/**
 * Form Switch Binder
 *
 * View Binder for [FormSwitchElement]
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormSwitchViewBinder(private val context: Context, private val formBuilder: FormBuildHelper) : BaseFormViewBinder() {
    var viewBinder = ViewBinder(R.layout.form_element_switch, FormSwitchElement::class.java, { model, finder, _ ->
        val textViewTitle = finder.find(R.id.formElementTitle) as AppCompatTextView
        val textViewError = finder.find(R.id.formElementError) as AppCompatTextView
        val itemView = finder.getRootView() as View
        baseSetup(model, textViewTitle, textViewError, itemView)

        val switch = finder.find(R.id.formElementValue) as SwitchCompat
        switch.isChecked = model.isOn()

        model.editView = switch

        setSwitchFocusEnabled(itemView, switch)

        switch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                model.setValue(model.onValue)
            } else {
                model.setValue(model.offValue)
            }
            formBuilder.onValueChanged(model)
            setError(textViewError, null)
        }
    }, object : ViewStateProvider<FormSwitchElement<*>, ViewHolder> {
        override fun createViewStateID(model: FormSwitchElement<*>): Int {
            return model.id
        }

        override fun createViewState(holder: ViewHolder): ViewState<ViewHolder> {
            return FormSwitchViewState(holder)
        }
    })

    private fun setSwitchFocusEnabled(itemView: View, switch: SwitchCompat) {
        itemView.setOnClickListener {
            switch.isChecked = !switch.isChecked
        }
    }
}
