package com.thejuki.kformmaster.view

import android.content.Context
import android.support.annotation.LayoutRes
import android.support.v7.widget.AppCompatEditText
import android.support.v7.widget.AppCompatTextView
import android.text.InputType
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.github.vivchar.rendererrecyclerviewadapter.ViewHolder
import com.github.vivchar.rendererrecyclerviewadapter.ViewState
import com.github.vivchar.rendererrecyclerviewadapter.ViewStateProvider
import com.github.vivchar.rendererrecyclerviewadapter.binder.ViewBinder
import com.thejuki.kformmaster.R
import com.thejuki.kformmaster.helper.FormBuildHelper
import com.thejuki.kformmaster.model.FormEmailEditTextElement
import com.thejuki.kformmaster.state.FormEditTextViewState


/**
 * Form Email EditText ViewBinder
 *
 * View Binder for [FormEmailEditTextElement]
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormEmailEditTextViewBinder(private val context: Context, private val formBuilder: FormBuildHelper, @LayoutRes private val layoutID: Int?) : BaseFormViewBinder() {
    var viewBinder = ViewBinder(layoutID
            ?: R.layout.form_element, FormEmailEditTextElement::class.java, { model, finder, _ ->
        val textViewTitle = finder.find(R.id.formElementTitle) as AppCompatTextView
        val textViewError = finder.find(R.id.formElementError) as AppCompatTextView
        val itemView = finder.getRootView() as View
        baseSetup(model, textViewTitle, textViewError, itemView)

        val editTextValue = finder.find(R.id.formElementValue) as AppCompatEditText

        editTextValue.setText(model.valueAsString)
        editTextValue.hint = model.hint ?: ""

        model.editView = editTextValue
        // Email
        editTextValue.setRawInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS or InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS)

        // If an InputType is provided, use it instead
        model.inputType?.let { editTextValue.setRawInputType(it) }

        // If imeOptions are provided, use them instead of actionNext
        model.imeOptions?.let { editTextValue.imeOptions = it }

        setEditTextFocusEnabled(editTextValue, itemView)
        setOnFocusChangeListener(context, model, formBuilder)
        addTextChangedListener(model, formBuilder)
        setOnEditorActionListener(model, formBuilder)

    }, object : ViewStateProvider<FormEmailEditTextElement, ViewHolder> {
        override fun createViewStateID(model: FormEmailEditTextElement): Int {
            return model.id
        }

        override fun createViewState(holder: ViewHolder): ViewState<ViewHolder> {
            return FormEditTextViewState(holder)
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
