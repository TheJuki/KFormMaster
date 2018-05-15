package com.thejuki.kformmaster.view

import android.app.TimePickerDialog
import android.content.Context
import android.support.v7.widget.AppCompatEditText
import android.support.v7.widget.AppCompatTextView
import android.text.InputType
import android.view.View
import com.github.vivchar.rendererrecyclerviewadapter.ViewHolder
import com.github.vivchar.rendererrecyclerviewadapter.ViewState
import com.github.vivchar.rendererrecyclerviewadapter.ViewStateProvider
import com.github.vivchar.rendererrecyclerviewadapter.binder.ViewBinder
import com.thejuki.kformmaster.R
import com.thejuki.kformmaster.helper.FormBuildHelper
import com.thejuki.kformmaster.model.FormPickerTimeElement
import com.thejuki.kformmaster.state.FormEditTextViewState

/**
 * Form Picker Time ViewBinder
 *
 * View Binder for [FormPickerTimeElement]
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormPickerTimeViewBinder(private val context: Context, private val formBuilder: FormBuildHelper) : BaseFormViewBinder() {
    var viewBinder = ViewBinder(R.layout.form_element, FormPickerTimeElement::class.java, { model, finder, _ ->
        val textViewTitle = finder.find(R.id.formElementTitle) as AppCompatTextView
        val textViewError = finder.find(R.id.formElementError) as AppCompatTextView
        val itemView = finder.getRootView() as View
        baseSetup(model, textViewTitle, textViewError, itemView)

        val editTextValue = finder.find(R.id.formElementValue) as AppCompatEditText

        editTextValue.setText(model.valueAsString)
        editTextValue.hint = model.hint ?: ""

        model.editView = editTextValue

        editTextValue.setRawInputType(InputType.TYPE_NULL)

        // If no value is set by the user, create a new instance of TimeHolder
        with(model.value)
        {
            if (this == null) {
                model.setValue(FormPickerTimeElement.TimeHolder())
            }
            this?.validOrCurrentTime()
        }

        val timePickerDialog = TimePickerDialog(context,
                timeDialogListener(model, editTextValue, textViewError),
                model.value?.hourOfDay ?: 0,
                model.value?.minute ?: 0,
                false)

        setOnClickListener(editTextValue, itemView, timePickerDialog)
    }, object : ViewStateProvider<FormPickerTimeElement, ViewHolder> {
        override fun createViewStateID(model: FormPickerTimeElement): Int {
            return model.id
        }

        override fun createViewState(holder: ViewHolder): ViewState<ViewHolder> {
            return FormEditTextViewState(holder)
        }
    })

    private fun timeDialogListener(model: FormPickerTimeElement,
                                   editTextValue: AppCompatEditText,
                                   textViewError: AppCompatTextView): TimePickerDialog.OnTimeSetListener {
        return TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
            var timeChanged = false
            with(model.value)
            {
                timeChanged = !(this?.hourOfDay == hourOfDay && this.minute == minute)
                this?.hourOfDay = hourOfDay
                this?.minute = minute
                this?.isEmptyTime = false
            }

            if (timeChanged) {
                model.setError(null) // Reset after value change
                formBuilder.onValueChanged(model)
                model.valueObservers.forEach { it(model.value, model) }
                editTextValue.setText(model.valueAsString)
                setError(textViewError, null)
            }
        }
    }
}
