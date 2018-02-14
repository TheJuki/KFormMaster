package com.thejuki.kformmaster.renderer

import android.content.Context
import android.support.v7.app.AlertDialog
import com.thejuki.kformmaster.R
import com.thejuki.kformmaster.helper.FormBuildHelper
import com.thejuki.kformmaster.holder.FormPickerHolder
import com.thejuki.kformmaster.model.FormPickerMultiCheckBoxElement
import java.util.*

/**
 * Form MultiCheckBox Renderer
 *
 * Renderer for FormPickerMultiCheckBoxElement
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormPickerMultiCheckBoxRenderer(type: Int, context: Context, formBuilder: FormBuildHelper) : FormPickerRenderer<FormPickerMultiCheckBoxElement<*>>(type, context, formBuilder) {

    override fun bindView(formElement: FormPickerMultiCheckBoxElement<*>, holder: FormPickerHolder) {
        super.bindView(formElement, holder)

        setMultipleOptionsDialog(holder, formElement.getTag(), formElement.dialogTitle)
    }

    private fun setMultipleOptionsDialog(holder: FormPickerHolder, tag: Int, dialogTitle: String?) {
        // get the element
        val currentObj = formBuilder.getFormElement(tag)

        // reformat the options in format needed
        val options = arrayOfNulls<CharSequence>(currentObj!!.mOptions?.size ?: 0)
        val optionsSelected = BooleanArray(currentObj.mOptions?.size ?: 0)
        val mSelectedItems = ArrayList<Int>()

        for (i in currentObj.mOptions!!.indices) {
            val obj = currentObj.mOptions!![i]

            options[i] = obj.toString()
            optionsSelected[i] = false

            if (currentObj.mOptionsSelected?.contains(obj) == true) {
                optionsSelected[i] = true
                mSelectedItems.add(i)
            }
        }

        var s = ""
        for (i in mSelectedItems.indices) {
            s += options[mSelectedItems[i]]

            if (i < mSelectedItems.size - 1) {
                s += ", "
            }
        }

        holder.mEditTextValue.setText(s)

        // prepare the dialog
        val dialog = AlertDialog.Builder(context)
                .setTitle(dialogTitle ?: context.getString(R.string.form_master_pick_one_or_more))
                .setMultiChoiceItems(options, optionsSelected
                ) { _, which, isChecked ->
                    if (isChecked) {
                        // If the user checked the item, add it to the selected items
                        mSelectedItems.add(which)
                    } else if (mSelectedItems.contains(which)) {
                        // Else, if the item is already in the array, remove it
                        mSelectedItems.remove(Integer.valueOf(which))
                    }
                }
                // Set the action buttons
                .setPositiveButton(android.R.string.ok) { _, _ ->
                    val element = formBuilder.getFormElement(tag)

                    val selectedOptions = mSelectedItems.indices
                            .map { mSelectedItems[it] }
                            .map { element!!.mOptions!![it] }

                    element!!.setOptionsSelected(selectedOptions)
                    element.setError(null)
                    formBuilder.onValueChanged(element)
                    formBuilder.refreshView()
                }
                .setNegativeButton(android.R.string.cancel) { _, _ -> }
                .create()

        setOnClickForHolder(holder, tag, dialog)
    }
}
