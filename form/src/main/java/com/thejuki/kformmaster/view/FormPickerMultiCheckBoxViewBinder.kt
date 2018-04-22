package com.thejuki.kformmaster.view

import android.content.Context
import android.support.v7.app.AlertDialog
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
import com.thejuki.kformmaster.model.FormPickerMultiCheckBoxElement
import com.thejuki.kformmaster.state.FormEditTextViewState
import java.util.*

/**
 * Form Picker MultiCheckBox ViewBinder
 *
 * View Binder for [FormPickerMultiCheckBoxElement]
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormPickerMultiCheckBoxViewBinder(private val context: Context, private val formBuilder: FormBuildHelper) : BaseFormViewBinder() {
    var viewBinder = ViewBinder(R.layout.form_element, FormPickerMultiCheckBoxElement::class.java, { model, finder, _ ->
        val textViewTitle = finder.find(R.id.formElementTitle) as AppCompatTextView
        val textViewError = finder.find(R.id.formElementError) as AppCompatTextView
        val itemView = finder.getRootView() as View
        baseSetup(model, textViewTitle, textViewError, itemView)

        val editTextValue = finder.find(R.id.formElementValue) as AppCompatEditText

        editTextValue.setText(model.valueAsString)
        editTextValue.hint = model.hint ?: ""

        model.editView = editTextValue

        editTextValue.setRawInputType(InputType.TYPE_NULL)

        // reformat the options in format needed
        val options = arrayOfNulls<CharSequence>(model.options?.size ?: 0)
        val optionsSelected = BooleanArray(model.options?.size ?: 0)
        val mSelectedItems = ArrayList<Int>()

        for (i in model.options!!.indices) {
            val obj = model.options!![i]

            options[i] = obj.toString()
            optionsSelected[i] = false

            if (model.optionsSelected?.contains(obj) == true) {
                optionsSelected[i] = true
                mSelectedItems.add(i)
            }
        }

        var selectedItems = ""
        for (i in mSelectedItems.indices) {
            selectedItems += options[mSelectedItems[i]]

            if (i < mSelectedItems.size - 1) {
                selectedItems += ", "
            }
        }

        editTextValue.setText(getSelectedItemsText(model))

        // prepare the dialog
        val alertDialog = AlertDialog.Builder(context)
                .setTitle(model.dialogTitle
                        ?: context.getString(R.string.form_master_pick_one_or_more))
                .setMultiChoiceItems(options, optionsSelected
                ) { _, which, isChecked ->
                    if (isChecked) {
                        // If the user checked the item, add it to the selected items
                        mSelectedItems.add(which)
                    } else if (mSelectedItems.contains(which)) {
                        // Else, if the item is already in the array, remove it
                        mSelectedItems.remove(which)
                    }
                }
                // Set the action buttons
                .setPositiveButton(android.R.string.ok) { _, _ ->
                    val selectedOptions = mSelectedItems.indices
                            .map { mSelectedItems[it] }
                            .map { model.options!![it] }

                    model.setOptionsSelected(selectedOptions)
                    model.setError(null)
                    formBuilder.onValueChanged(model)
                    editTextValue.setText(getSelectedItemsText(model))
                    setError(textViewError, null)
                }
                .setNegativeButton(android.R.string.cancel) { _, _ -> }
                .create()

        setOnClickListener(editTextValue, itemView, alertDialog)
    }, object : ViewStateProvider<FormPickerMultiCheckBoxElement<*>, ViewHolder> {
        override fun createViewStateID(model: FormPickerMultiCheckBoxElement<*>): Int {
            return model.id
        }

        override fun createViewState(holder: ViewHolder): ViewState<ViewHolder> {
            return FormEditTextViewState(holder)
        }
    })

    private fun getSelectedItemsText(model: FormPickerMultiCheckBoxElement<*>): String {
        val options = arrayOfNulls<CharSequence>(model.options?.size ?: 0)
        val optionsSelected = BooleanArray(model.options?.size ?: 0)
        val mSelectedItems = ArrayList<Int>()

        for (i in model.options!!.indices) {
            val obj = model.options!![i]

            options[i] = obj.toString()
            optionsSelected[i] = false

            if (model.optionsSelected?.contains(obj) == true) {
                optionsSelected[i] = true
                mSelectedItems.add(i)
            }
        }

        var selectedItems = ""
        for (i in mSelectedItems.indices) {
            selectedItems += options[mSelectedItems[i]]

            if (i < mSelectedItems.size - 1) {
                selectedItems += ", "
            }
        }

        return selectedItems
    }
}
