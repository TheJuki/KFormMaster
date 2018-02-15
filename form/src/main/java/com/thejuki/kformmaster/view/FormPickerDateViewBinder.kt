package com.thejuki.kformmaster.view

import android.app.DatePickerDialog
import android.content.Context
import android.support.v7.widget.AppCompatEditText
import android.support.v7.widget.AppCompatTextView
import android.view.View
import com.github.vivchar.rendererrecyclerviewadapter.binder.ViewBinder
import com.thejuki.kformmaster.R
import com.thejuki.kformmaster.helper.FormBuildHelper
import com.thejuki.kformmaster.model.FormPickerDateElement

/**
 * Form Picker Date ViewBinder
 *
 * View Binder for [FormPickerDateElement]
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormPickerDateViewBinder(private val context: Context, private val formBuilder: FormBuildHelper) : BaseFormViewBinder() {
    var viewBinder = ViewBinder(R.layout.form_element, FormPickerDateElement::class.java) { model, finder, _ ->
        val textViewTitle = finder.find(R.id.formElementTitle) as AppCompatTextView
        val textViewError = finder.find(R.id.formElementError) as AppCompatTextView
        val itemView = finder.getRootView() as View
        baseSetup(model, textViewTitle, textViewError, itemView)

        val editTextValue = finder.find(R.id.formElementValue) as AppCompatEditText

        editTextValue.setText(model.valueAsString)
        editTextValue.hint = model.hint ?: ""

        // If no value is set by the user, create a new instance of DateHolder
        with(model.value)
        {
            if (this == null) {
                model.setValue(FormPickerDateElement.DateHolder())
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

    private fun dateDialogListener(model: FormPickerDateElement): DatePickerDialog.OnDateSetListener {
        return DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            // get current form element, existing value and new value
            var dateChanged = false
            with(model.value)
            {
                dateChanged = !(this?.year == year && this.month == monthOfYear && this.dayOfMonth == dayOfMonth)
                this?.year = year
                this?.month = monthOfYear + 1
                this?.dayOfMonth = dayOfMonth
                this?.isEmptyDate = false
            }

            if (dateChanged)
            {
                model.setError(null) // Reset after value change
                model.valueChanged?.onValueChanged(model)
                formBuilder.onValueChanged(model)
                formBuilder.refreshView()
            }
        }
    }
}
