package com.thejuki.kformmasterexample.custom.view

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.github.vivchar.rendererrecyclerviewadapter.ViewHolder
import com.github.vivchar.rendererrecyclerviewadapter.ViewState
import com.github.vivchar.rendererrecyclerviewadapter.ViewStateProvider
import com.github.vivchar.rendererrecyclerviewadapter.binder.ViewBinder
import com.thejuki.kformmaster.helper.FormBuildHelper
import com.thejuki.kformmaster.view.BaseFormViewBinder
import com.thejuki.kformmasterexample.R
import com.thejuki.kformmasterexample.custom.model.FormCustomElement
import com.thejuki.kformmasterexample.custom.state.FormCustomViewState

/**
 * Form Custom ViewBinder
 *
 * View Binder for [FormCustomElement]
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class CustomViewBinder(private val context: Context, private val formBuilder: FormBuildHelper) : BaseFormViewBinder() {
    var viewBinder = ViewBinder(R.layout.form_element_custom, FormCustomElement::class.java, { model, finder, _ ->
        val textViewTitle = finder.find(R.id.formElementTitle) as AppCompatTextView
        val textViewError = finder.find(R.id.formElementError) as AppCompatTextView
        val dividerView = finder.find(R.id.formElementDivider) as? View
        val itemView = finder.getRootView() as View
        baseSetup(model, dividerView, textViewTitle, textViewError, itemView)

        val editTextValue = finder.find(R.id.formElementValue) as com.thejuki.kformmaster.widget.ClearableEditText

        editTextValue.setText(model.valueAsString)
        editTextValue.hint = model.hint ?: ""

        model.editView = editTextValue

        // Initially use 4 lines
        // unless a different number was provided
        if (model.maxLines == 1) {
            model.maxLines = 4
        }

        // If an InputType is provided, use it instead
        model.inputType?.let { editTextValue.setRawInputType(it) }

        // If imeOptions are provided, use them instead of creating a new line
        model.imeOptions?.let { editTextValue.imeOptions = it }

        setEditTextFocusEnabled(editTextValue, itemView)
        setOnFocusChangeListener(context, model, formBuilder)
        addTextChangedListener(model, formBuilder)
        setOnEditorActionListener(model, formBuilder)

    }, object : ViewStateProvider<FormCustomElement, ViewHolder> {
        override fun createViewStateID(model: FormCustomElement): Int {
            return model.id
        }

        override fun createViewState(holder: ViewHolder): ViewState<ViewHolder> {
            return FormCustomViewState(holder)
        }
    })

    private fun setEditTextFocusEnabled(editTextValue: AppCompatEditText, itemView: View) {
        itemView.setOnClickListener {
            editTextValue.requestFocus()
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            editTextValue.setSelection(editTextValue.text.length)
            imm.showSoftInput(editTextValue, InputMethodManager.SHOW_IMPLICIT)
        }
    }
}
