package com.thejuki.kformmaster.view

import android.content.Context
import android.text.InputType
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.AppCompatTextView
import com.github.vivchar.rendererrecyclerviewadapter.ViewHolder
import com.github.vivchar.rendererrecyclerviewadapter.ViewState
import com.github.vivchar.rendererrecyclerviewadapter.ViewStateProvider
import com.github.vivchar.rendererrecyclerviewadapter.binder.ViewBinder
import com.thejuki.kformmaster.R
import com.thejuki.kformmaster.helper.FormBuildHelper
import com.thejuki.kformmaster.model.FormPickerDropDownElement
import com.thejuki.kformmaster.state.FormEditTextViewState

/**
 * Form Picker DropDown ViewBinder
 *
 * View Binder for [FormPickerDropDownElement]
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormPickerDropDownViewBinder(private val context: Context, private val formBuilder: FormBuildHelper, @LayoutRes private val layoutID: Int?) : BaseFormViewBinder() {
    val viewBinder = ViewBinder(layoutID
            ?: R.layout.form_element, FormPickerDropDownElement::class.java, { model, finder, _ ->
        val textViewTitle = finder.find(R.id.formElementTitle) as? AppCompatTextView
        val mainViewLayout = finder.find(R.id.formElementMainLayout) as? LinearLayout
        val textViewError = finder.find(R.id.formElementError) as? AppCompatTextView
        val dividerView = finder.find(R.id.formElementDivider) as? View
        val itemView = finder.getRootView() as View
        baseSetup(model, dividerView, textViewTitle, textViewError, itemView, mainViewLayout)

        val editTextValue = finder.find(R.id.formElementValue) as com.thejuki.kformmaster.widget.ClearableEditText

        editTextValue.setText(model.valueAsString)
        editTextValue.hint = model.hint ?: ""
        editTextValue.alwaysShowClear = true

        model.editView = editTextValue

        editTextValue.setRawInputType(InputType.TYPE_NULL)
        editTextValue.isFocusable = false

        model.reInitDialog(context, formBuilder)
        setClearableListener(model)

    }, object : ViewStateProvider<FormPickerDropDownElement<*>, ViewHolder> {
        override fun createViewStateID(model: FormPickerDropDownElement<*>): Int {
            return model.id
        }

        override fun createViewState(holder: ViewHolder): ViewState<ViewHolder> {
            return FormEditTextViewState(holder)
        }
    })
}
