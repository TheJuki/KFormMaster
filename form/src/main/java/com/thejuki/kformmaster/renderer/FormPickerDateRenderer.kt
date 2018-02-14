package com.thejuki.kformmaster.renderer

import android.app.DatePickerDialog
import android.content.Context
import com.thejuki.kformmaster.helper.FormBuildHelper
import com.thejuki.kformmaster.holder.FormPickerHolder
import com.thejuki.kformmaster.model.FormPickerDateElement

/**
 * Form Picker Date Renderer
 *
 * Renderer for FormPickerDateElement
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormPickerDateRenderer(type: Int, context: Context, formBuilder: FormBuildHelper) : FormPickerRenderer<FormPickerDateElement>(type, context, formBuilder) {

    internal var date: DatePickerDialog.OnDateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
        if (clickedTag != -1) {
            // get current form element, existing value and new value
            val formElement = formBuilder.getFormElement(clickedTag) as FormPickerDateElement?
            val currentValue = formElement!!.getValue()
            val newValue = FormPickerDateElement.DateHolder(dayOfMonth, monthOfYear + 1, year)
            newValue.dateFormat = currentValue!!.dateFormat

            // trigger event only if the value is changed
            if (!currentValue.equals(newValue)) {
                formElement.setValue(newValue)
                formElement.setError(null) // Reset after value change
                formBuilder.onValueChanged(formElement)
                formBuilder.refreshView()
            }
        }

        clickedTag = -1
    }

    override fun bindView(formElement: FormPickerDateElement, holder: FormPickerHolder) {
        super.bindView(formElement, holder)

        // If no value is set by the user, create a new instance of DateHolder
        if (formElement.getValue() == null) {
            formElement.setValue(FormPickerDateElement.DateHolder())
        }

        formElement.getValue()!!.validOrCurrentDate()

        val datePickerDialog = DatePickerDialog(context,
                date,
                formElement.getValue()!!.year!!,
                formElement.getValue()!!.month!! - 1,
                formElement.getValue()!!.dayOfMonth!!)

        // TODO: this could be used to set a minimum date
        // datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

        setOnClickForHolder(holder, formElement.getTag(), datePickerDialog)
    }
}
