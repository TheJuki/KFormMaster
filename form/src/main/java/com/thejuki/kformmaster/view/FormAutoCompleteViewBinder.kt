package com.thejuki.kformmaster.view

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.AppCompatAutoCompleteTextView
import android.support.v7.widget.AppCompatTextView
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.github.vivchar.rendererrecyclerviewadapter.ViewHolder
import com.github.vivchar.rendererrecyclerviewadapter.ViewState
import com.github.vivchar.rendererrecyclerviewadapter.ViewStateProvider
import com.github.vivchar.rendererrecyclerviewadapter.binder.ViewBinder
import com.thejuki.kformmaster.R
import com.thejuki.kformmaster.helper.FormBuildHelper
import com.thejuki.kformmaster.model.FormAutoCompleteElement
import com.thejuki.kformmaster.state.FormAutoCompleteViewState

/**
 * Form AutoComplete ViewBinder
 *
 * View Binder for [FormAutoCompleteElement]
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormAutoCompleteViewBinder(private val context: Context, private val formBuilder: FormBuildHelper) : BaseFormViewBinder() {
    var viewBinder = ViewBinder(R.layout.form_element_auto_complete, FormAutoCompleteElement::class.java, { model, finder, _ ->
        val textViewTitle = finder.find(R.id.formElementTitle) as AppCompatTextView
        val textViewError = finder.find(R.id.formElementError) as AppCompatTextView
        val itemView = finder.getRootView() as View
        baseSetup(model, textViewTitle, textViewError, itemView)

        val autoCompleteTextView = finder.find(R.id.formElementValue) as AppCompatAutoCompleteTextView

        autoCompleteTextView.hint = model.hint ?: ""

        // Set threshold (the number of characters to type before the drop down is shown)
        autoCompleteTextView.threshold = 1

        model.editView = autoCompleteTextView

        if (model.typedString != null) {
            autoCompleteTextView.setText(model.typedString)
        } else {
            autoCompleteTextView.setText(model.valueAsString)
        }

        // Select all text when focused for easy removal
        if (autoCompleteTextView.text.isNotEmpty()) {
            autoCompleteTextView.setSelectAllOnFocus(true)
        }

        val itemsAdapter = if (model.arrayAdapter != null)
            model.arrayAdapter
        else
            ArrayAdapter(context, android.R.layout.simple_list_item_1, model.options)

        autoCompleteTextView.setAdapter<ArrayAdapter<*>>(itemsAdapter)

        model.dropdownWidth?.let {
            autoCompleteTextView.dropDownWidth = it
        }

        autoCompleteTextView.onItemClickListener = AdapterView.OnItemClickListener { adapterView, _, position, _ ->
            model.setValue(adapterView.getItemAtPosition(position))
            model.error = null

            formBuilder.onValueChanged(model)
        }

        setEditTextFocusEnabled(autoCompleteTextView, itemView)

        autoCompleteTextView.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                textViewTitle.setTextColor(ContextCompat.getColor(context,
                        R.color.colorFormMasterElementFocusedTitle))
            } else {
                textViewTitle.setTextColor(ContextCompat.getColor(context,
                        R.color.colorFormMasterElementTextTitle))

                if (autoCompleteTextView.text.toString() != model.valueAsString) {
                    model.setValue(autoCompleteTextView.text.toString())
                    model.error = null
                    formBuilder.onValueChanged(model)
                }
            }
        }
    }, object : ViewStateProvider<FormAutoCompleteElement<*>, ViewHolder> {
        override fun createViewStateID(model: FormAutoCompleteElement<*>): Int {
            return model.id
        }

        override fun createViewState(holder: ViewHolder): ViewState<ViewHolder> {
            return FormAutoCompleteViewState(holder)
        }
    })

    private fun setEditTextFocusEnabled(autoCompleteTextView: AppCompatAutoCompleteTextView, itemView: View) {
        itemView.setOnClickListener {
            autoCompleteTextView.requestFocus()
            if (autoCompleteTextView.text.isNotEmpty()) {
                autoCompleteTextView.selectAll()
            }
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            autoCompleteTextView.setSelection(autoCompleteTextView.text.length)
            imm.showSoftInput(autoCompleteTextView, InputMethodManager.SHOW_IMPLICIT)
        }
    }
}
