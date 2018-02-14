package com.thejuki.kformmaster.renderer

import android.app.Dialog
import android.content.Context
import android.support.v7.app.AlertDialog
import com.thejuki.kformmaster.R
import com.thejuki.kformmaster.helper.FormBuildHelper
import com.thejuki.kformmaster.holder.FormPickerHolder
import com.thejuki.kformmaster.model.FormPickerDropDownElement

/**
 * Form Picker Dropdown Renderer
 *
 * Renderer for FormPickerDropDownElement
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormPickerDropDownRenderer(type: Int, context: Context, formBuilder: FormBuildHelper) : FormPickerRenderer<FormPickerDropDownElement<*>>(type, context, formBuilder) {

    override fun bindView(formElement: FormPickerDropDownElement<*>, holder: FormPickerHolder) {
        super.bindView(formElement, holder)

        setSingleOptionsDialog(holder, formElement.getTag(), formElement.dialogTitle)
    }

    private fun setSingleOptionsDialog(holder: FormPickerHolder, tag: Int, dialogTitle: String?) {
        // get the element
        val currentObj: FormPickerDropDownElement<*> = formBuilder.getFormElement(tag) as FormPickerDropDownElement<*>

        // reformat the options in format needed

        val options = arrayOfNulls<CharSequence>(currentObj.mOptions?.size ?: 0)

        for (i in currentObj.mOptions!!.indices) {
            options[i] = currentObj.mOptions!![i].toString()
        }

        // prepare the dialog
        val alertDialog: Dialog?

        if (currentObj.arrayAdapter != null) {
            alertDialog = AlertDialog.Builder(context)
                    .setTitle(dialogTitle ?: context.getString(R.string.form_master_pick_one))
                    .setAdapter(currentObj.arrayAdapter, { _, which ->
                        holder.mEditTextValue.setText(currentObj.arrayAdapter!!.getItem(which).toString())
                        val element = formBuilder.getFormElement(tag)
                        element!!.setValue(currentObj.arrayAdapter!!.getItem(which))
                        element.setError(null) // Reset after value change
                        setError(holder, null) // Reset after value change
                        formBuilder.onValueChanged(element)
                        formBuilder.refreshView()
                    })
                    .create()
        } else {
            alertDialog = AlertDialog.Builder(context)
                    .setTitle(dialogTitle)
                    .setItems(options) { _, which ->
                        holder.mEditTextValue.setText(options[which])
                        val element = formBuilder.getFormElement(tag)
                        element!!.setValue(currentObj.mOptions!![which])
                        element.setError(null) // Reset after value change
                        setError(holder, null) // Reset after value change
                        formBuilder.onValueChanged(element)
                        formBuilder.refreshView()
                    }
                    .create()
        }

        setOnClickForHolder(holder, tag, alertDialog)
    }
}
