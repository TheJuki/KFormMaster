package com.thejuki.kformmaster.renderer

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import com.thejuki.kformmaster.helper.FormBuildHelper
import com.thejuki.kformmaster.holder.FormPickerHolder
import com.thejuki.kformmaster.model.FormPickerDateTimeElement

/**
 * Form Picker DateTime Renderer
 *
 * Renderer for FormPickerDateTimeElement
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormPickerDateTimeRenderer(type: Int, context: Context, formBuilder: FormBuildHelper) : FormPickerRenderer<FormPickerDateTimeElement>(type, context, formBuilder) {

    internal var date: DatePickerDialog.OnDateSetListener = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
        if (clickedTag >= 0) {
            // get current form element, existing value and new value
            val formElement = formBuilder.getFormElement(clickedTag) as FormPickerDateTimeElement?
            formElement?.getValue()!!.year = year
            formElement.getValue()!!.month = monthOfYear + 1
            formElement.getValue()!!.dayOfMonth = dayOfMonth

            // Now show time picker
            TimePickerDialog(context, time,
                    formElement.getValue()!!.hourOfDay!!,
                    formElement.getValue()!!.minute!!,
                    false).show()
        }
    }

    internal var time: TimePickerDialog.OnTimeSetListener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
        // act only if clicked position is a valid index
        if (clickedTag >= 0) {
            // get current form element, existing value and new value
            val formElement = formBuilder.getFormElement(clickedTag) as FormPickerDateTimeElement?
            formElement?.getValue()!!.hourOfDay = hourOfDay
            formElement.getValue()!!.minute = minute
            formElement.getValue()!!.isEmptyDateTime = false
            formElement.setError(null) // Reset after value change
            formBuilder.onValueChanged(formElement)
            formBuilder.refreshView()

            // change clicked position to default value
            clickedTag = -1
        }
    }

    override fun bindView(formElement: FormPickerDateTimeElement, holder: FormPickerHolder) {
        super.bindView(formElement, holder)

        // If no value is set by the user, create a new instance of DateTimeHolder
        if (formElement.getValue() == null) {
            formElement.setValue(FormPickerDateTimeElement.DateTimeHolder())
        }

        formElement.getValue()!!.validOrCurrentDate()

        val datePickerDialog = DatePickerDialog(context,
                date,
                formElement.getValue()!!.year!!,
                formElement.getValue()!!.month!! - 1,
                formElement.getValue()!!.dayOfMonth!!)

        setOnClickForHolder(holder, formElement.getTag(), datePickerDialog)
    }
}
