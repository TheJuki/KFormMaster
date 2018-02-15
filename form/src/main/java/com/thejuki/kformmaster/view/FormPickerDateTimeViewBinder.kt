package com.thejuki.kformmaster.view

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.support.v7.widget.AppCompatEditText
import android.support.v7.widget.AppCompatTextView
import android.view.View
import com.github.vivchar.rendererrecyclerviewadapter.binder.ViewBinder
import com.thejuki.kformmaster.R
import com.thejuki.kformmaster.helper.FormBuildHelper
import com.thejuki.kformmaster.model.FormPickerDateTimeElement

/**
 * Form Picker DateTime ViewBinder
 *
 * Renderer for FormEditTextElement
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormPickerDateTimeViewBinder(private val context: Context, private val formBuilder: FormBuildHelper) : BaseFormViewBinder() {
    var viewBinder = ViewBinder(R.layout.form_element, FormPickerDateTimeElement::class.java) { model, finder, _ ->
        val textViewTitle = finder.find(R.id.formElementTitle) as AppCompatTextView
        val textViewError = finder.find(R.id.formElementError) as AppCompatTextView
        val itemView = finder.getRootView() as View
        baseSetup(model, textViewTitle, textViewError, itemView)

        val editTextValue = finder.find(R.id.formElementValue) as AppCompatEditText

        editTextValue.setText(model.valueAsString)
        editTextValue.hint = model.hint ?: ""

        // If no value is set by the user, create a new instance of DateTimeHolder
        with(model.value)
        {
            if (this == null) {
                model.setValue(FormPickerDateTimeElement.DateTimeHolder())
            }
            this?.validOrCurrentDate()
        }

        val datePickerDialog = DatePickerDialog(context,
                dateDialogListener(model),
                model.value?.year!!,
                model.value?.month!! - 1,
                model.value?.dayOfMonth!!)

        setOnClickListener(editTextValue, itemView, datePickerDialog)
    }

    private fun dateDialogListener(model: FormPickerDateTimeElement): DatePickerDialog.OnDateSetListener {
        return DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            // get current form element, existing value and new value

            with(model.value)
            {
                this?.year = year
                this?.month = monthOfYear + 1
                this?.dayOfMonth = dayOfMonth
            }

            // Now show time picker
            TimePickerDialog(context, timeDialogListener(model),
                    model.value!!.hourOfDay!!,
                    model.value!!.minute!!,
                    false).show()
        }
    }

    private fun timeDialogListener(model: FormPickerDateTimeElement): TimePickerDialog.OnTimeSetListener {
        return TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
            with(model.value)
            {
                this?.hourOfDay = hourOfDay
                this?.minute = minute
                this?.isEmptyDateTime = false
            }
            model.setError(null) // Reset after value change
            model.valueChanged?.onValueChanged(model)
            formBuilder.onValueChanged(model)
            formBuilder.refreshView()
        }
    }
}
