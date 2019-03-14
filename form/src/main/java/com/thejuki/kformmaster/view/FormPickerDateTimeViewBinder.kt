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
import com.thejuki.kformmaster.model.FormPickerDateTimeElement
import com.thejuki.kformmaster.state.FormEditTextViewState

/**
 * Form Picker DateTime ViewBinder
 *
 * View Binder for [FormPickerDateTimeElement]
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormPickerDateTimeViewBinder(private val context: Context, private val formBuilder: FormBuildHelper, @LayoutRes private val layoutID: Int?) : BaseFormViewBinder() {
    val viewBinder = ViewBinder(layoutID
            ?: R.layout.form_element, FormPickerDateTimeElement::class.java, { model, finder, _ ->
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

        // If no value is set by the user, create a new instance of DateTimeHolder
        with(model.value)
        {
            if (this == null) {
                model.setValue(FormPickerDateTimeElement.DateTimeHolder(model.dateValue, model.dateFormat))
            }
            this?.validOrCurrentDate()
        }

        model.reInitDialog(formBuilder)

        setClearableListener(model)

    }, object : ViewStateProvider<FormPickerDateTimeElement, ViewHolder> {
        override fun createViewStateID(model: FormPickerDateTimeElement): Int {
            return model.id
        }

        override fun createViewState(holder: ViewHolder): ViewState<ViewHolder> {
            return FormEditTextViewState(holder)
        }
    })
}
