package com.thejuki.kformmaster.view

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.AppCompatAutoCompleteTextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.github.vivchar.rendererrecyclerviewadapter.ViewRenderer
import com.thejuki.kformmaster.R
import com.thejuki.kformmaster.helper.FormBuildHelper
import com.thejuki.kformmaster.helper.FormViewFinder
import com.thejuki.kformmaster.model.FormAutoCompleteElement

/**
 * Form AutoComplete ViewRenderer
 *
 * View Binder for [FormAutoCompleteElement]
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormAutoCompleteViewRenderer(private val formBuilder: FormBuildHelper, @LayoutRes private val layoutID: Int?) : BaseFormViewRenderer() {
    val viewRenderer = ViewRenderer(layoutID
            ?: R.layout.form_element_auto_complete, FormAutoCompleteElement::class.java) { model, finder: FormViewFinder, _ ->
        val textViewTitle = finder.find(R.id.formElementTitle) as? AppCompatTextView
        val mainViewLayout = finder.find(R.id.formElementMainLayout) as? LinearLayout
        val textViewError = finder.find(R.id.formElementError) as? AppCompatTextView
        val dividerView = finder.find(R.id.formElementDivider) as? View
        val itemView = finder.getRootView() as View
        val autoCompleteTextView = finder.find(R.id.formElementValue) as AppCompatAutoCompleteTextView
        baseSetup(model, dividerView, textViewTitle, textViewError, itemView, mainViewLayout, autoCompleteTextView)

        autoCompleteTextView.hint = model.hint ?: ""

        // Set threshold (the number of characters to type before the drop down is shown)
        autoCompleteTextView.threshold = 1

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
            ArrayAdapter(itemView.context, android.R.layout.simple_list_item_1, model.options
                    ?: listOf())

        autoCompleteTextView.setAdapter<ArrayAdapter<*>>(itemsAdapter)

        model.dropdownWidth?.let {
            autoCompleteTextView.dropDownWidth = it
        }

        autoCompleteTextView.onItemClickListener = AdapterView.OnItemClickListener { adapterView, _, position, _ ->
            model.error = null
            model.setValue(adapterView.getItemAtPosition(position))

            formBuilder.onValueChanged(model)
        }

        setEditTextFocusEnabled(model, autoCompleteTextView, itemView)

        autoCompleteTextView.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                textViewTitle?.setTextColor(ContextCompat.getColor(itemView.context,
                        R.color.colorFormMasterElementFocusedTitle))
            } else {
                textViewTitle?.setTextColor(ContextCompat.getColor(itemView.context,
                        R.color.colorFormMasterElementTextTitle))
            }
        }

        autoCompleteTextView.setOnClickListener {
            // Invoke onClick Unit
            model.onClick?.invoke()
        }
    }

    private fun setEditTextFocusEnabled(model: FormAutoCompleteElement<*>,
                                        autoCompleteTextView: AppCompatAutoCompleteTextView,
                                        itemView: View) {
        itemView.setOnClickListener {
            // Invoke onClick Unit
            model.onClick?.invoke()

            autoCompleteTextView.requestFocus()
            if (autoCompleteTextView.text.isNotEmpty()) {
                autoCompleteTextView.selectAll()
            }
            val imm = itemView.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            autoCompleteTextView.setSelection(autoCompleteTextView.text?.length ?: 0)
            imm.showSoftInput(autoCompleteTextView, InputMethodManager.SHOW_IMPLICIT)
        }
    }
}
