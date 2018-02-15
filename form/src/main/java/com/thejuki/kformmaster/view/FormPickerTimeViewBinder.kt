package com.thejuki.kformmaster.view

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.support.v7.widget.AppCompatEditText
import android.support.v7.widget.AppCompatTextView
import android.view.View
import com.github.vivchar.rendererrecyclerviewadapter.binder.ViewBinder
import com.thejuki.kformmaster.R
import com.thejuki.kformmaster.helper.FormBuildHelper
import com.thejuki.kformmaster.model.FormPickerDateTimeElement
import com.thejuki.kformmaster.model.FormPickerTimeElement

/**
 * Form EditText Binder
 *
 * Renderer for FormEditTextElement
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormPickerTimeViewBinder(private val context: Context, private val formBuilder: FormBuildHelper) : BaseFormViewBinder() {
    var viewBinder = ViewBinder(R.layout.form_element, FormPickerTimeElement::class.java) { model, finder, _ ->
        val textViewTitle = finder.find(R.id.formElementTitle) as AppCompatTextView
        val textViewError = finder.find(R.id.formElementError) as AppCompatTextView
        val itemView = finder.getRootView() as View
        baseSetup(model, textViewTitle, textViewError, itemView)

        val editTextValue = finder.find(R.id.text) as AppCompatEditText

        // If no value is set by the user, create a new instance of DateTimeHolder
        with(model.getValue())
        {
            if (this == null) {
                model.setValue(FormPickerDateTimeElement.DateTimeHolder())
            }
            this?.validOrCurrentTime()
        }

        val datePickerDialog = DatePickerDialog(context,
                dateDialogListener(model),
                model.getValue()?.year!!,
                model.getValue()?.month!! - 1,
                model.getValue()?.dayOfMonth!!)

        setOnClickListener(editTextValue, itemView, datePickerDialog)
    }

    private fun dateDialogListener(model: FormPickerDateTimeElement): DatePickerDialog.OnDateSetListener {
        return DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            // get current form element, existing value and new value

            with(model.getValue())
            {
                this?.year = year
                this?.month = monthOfYear + 1
                this?.dayOfMonth = dayOfMonth
            }

            // Now show time picker
            TimePickerDialog(context, timeDialogListener(model),
                    model.getValue()!!.hourOfDay!!,
                    model.getValue()!!.minute!!,
                    false).show()
        }
    }

    private fun timeDialogListener(model: FormPickerDateTimeElement): TimePickerDialog.OnTimeSetListener {
        return TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
            with(model.getValue())
            {
                this?.hourOfDay = hourOfDay
                this?.minute = minute
                this?.isEmptyDateTime = false
            }
            model.setError(null) // Reset after value change
            formBuilder.onValueChanged(model)
            formBuilder.refreshView()
        }
    }

    fun setOnClickListener(editTextValue: AppCompatEditText, itemView: View, dialog: Dialog) {
        editTextValue.isFocusable = false

        // display the dialog on click
        val listener = View.OnClickListener {
            dialog.show()
        }

        itemView.setOnClickListener(listener)
        editTextValue.setOnClickListener(listener)
    }
}
