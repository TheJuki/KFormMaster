package com.thejuki.kformmaster.view

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.AppCompatAutoCompleteTextView
import android.support.v7.widget.AppCompatTextView
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.github.vivchar.rendererrecyclerviewadapter.binder.ViewBinder
import com.thejuki.kformmaster.R
import com.thejuki.kformmaster.helper.FormBuildHelper
import com.thejuki.kformmaster.model.FormAutoCompleteElement

/**
 * Form AutoComplete ViewBinder
 *
 * Renderer for FormEditTextElement
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormAutoCompleteViewBinder(private val context: Context, private val formBuilder: FormBuildHelper) : BaseFormViewBinder() {
    var viewBinder = ViewBinder(R.layout.form_element_auto_complete, FormAutoCompleteElement::class.java) { model, finder, _ ->
        val textViewTitle = finder.find(R.id.formElementTitle) as AppCompatTextView
        val textViewError = finder.find(R.id.formElementError) as AppCompatTextView
        val itemView = finder.getRootView() as View
        baseSetup(model, textViewTitle, textViewError, itemView)

        val autoCompleteTextView = finder.find(R.id.formElementValue) as AppCompatAutoCompleteTextView

        if (model.valueAsString != model.typedString) { // If the typedString is not one of the values, keep it
            autoCompleteTextView.setText(model.typedString)
        } else {
            autoCompleteTextView.setText(model.valueAsString)
        }
        autoCompleteTextView.hint = model.hint ?: ""

        // Select all text when focused for easy removal
        if (autoCompleteTextView.text.isNotEmpty()) {
            autoCompleteTextView.setSelectAllOnFocus(true)
        }

        // Set threshold (the number of characters to type before the drop down is shown)
        autoCompleteTextView.threshold = 1

        setEditTextFocusEnabled(autoCompleteTextView, itemView)

        val itemsAdapter = if (model.arrayAdapter != null)
            model.arrayAdapter
        else
            ArrayAdapter(context, android.R.layout.simple_list_item_1, model.options)
        autoCompleteTextView.setAdapter<ArrayAdapter<*>>(itemsAdapter)

        if (model.dropdownWidth != null) {
            autoCompleteTextView.dropDownWidth = model.dropdownWidth!!
        }

        // Support for custom adapter with custom options
        if (model.arrayAdapter != null) {
            autoCompleteTextView.onItemClickListener = AdapterView.OnItemClickListener { adapterView, _, position, _ ->
                // Set options to selected option
                model.setResultOption(adapterView.getItemAtPosition(position))
                // Jump to beginning of text view
                autoCompleteTextView.setSelection(0)
                // Cause onTextChanged to be called
                autoCompleteTextView.text = autoCompleteTextView.text
            }
        }

        setEditTextFocusEnabled(autoCompleteTextView, itemView)

        autoCompleteTextView.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                textViewTitle.setTextColor(ContextCompat.getColor(context,
                        R.color.colorFormMasterElementFocusedTitle))
            } else {
                textViewTitle.setTextColor(ContextCompat.getColor(context,
                        R.color.colorFormMasterElementTextTitle))
            }
        }

        autoCompleteTextView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i2: Int, i3: Int) {}

            override fun onTextChanged(charSequence: CharSequence, i: Int, i2: Int, i3: Int) {

                val newValue = charSequence.toString()
                model.typedString = newValue

                // trigger only if the value exists as one of the string options
                if (model.stringOptions.contains(newValue)) {
                    model.setValue(model.options?.firstOrNull { it.toString() == newValue })
                    model.setError(null)
                    setError(textViewError, null)

                    formBuilder.onValueChanged(model)
                }

                // If field is blank set form value to null
                if (newValue.isBlank()) {
                    model.setValue(null)
                    model.setError(null)
                    setError(textViewError, null)

                    formBuilder.onValueChanged(model)
                }
            }

            override fun afterTextChanged(editable: Editable) {}
        })
    }

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
