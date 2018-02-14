package com.thejuki.kformmaster.renderer

import android.app.TimePickerDialog
import android.content.Context
import com.thejuki.kformmaster.helper.FormBuildHelper
import com.thejuki.kformmaster.holder.FormPickerHolder
import com.thejuki.kformmaster.model.FormPickerTimeElement

/**
 * Form Picker Time Renderer
 *
 * Renderer for FormPickerTimeElement
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormPickerTimeRenderer(type: Int, context: Context, formBuilder: FormBuildHelper) : FormPickerRenderer<FormPickerTimeElement>(type, context, formBuilder) {

    internal var time: TimePickerDialog.OnTimeSetListener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
        // act only if clicked position is a valid index
        if (clickedTag >= 0) {
            // get current form element, existing value and new value
            val formElement = formBuilder.getFormElement(clickedTag) as FormPickerTimeElement?
            val currentValue = formElement!!.getValue()
            val newValue = FormPickerTimeElement.TimeHolder(hourOfDay, minute)
            newValue.dateFormat = currentValue!!.dateFormat

            // trigger event only if the value is changed
            if (!currentValue.equals(newValue)) {
                formElement.setValue(newValue)
                formElement.setError(null) // Reset after value change
                formBuilder.onValueChanged(formElement)
                formBuilder.refreshView()
            }

            // change clicked position to default value
            clickedTag = -1
        }
    }

    override fun bindView(formElement: FormPickerTimeElement, holder: FormPickerHolder) {
        super.bindView(formElement, holder)

        // If no value is set by the user, create a new instance of DateHolder
        if (formElement.getValue() == null) {
            formElement.setValue(FormPickerTimeElement.TimeHolder())
        }

        formElement.getValue()!!.validOrCurrentTime()

        val timePickerDialog = TimePickerDialog(context,
                time,
                formElement.getValue()!!.hourOfDay!!,
                formElement.getValue()!!.minute!!,
                true)

        setOnClickForHolder(holder, formElement.getTag(), timePickerDialog)
    }
}
