package com.thejuki.kformmasterexample.custom.view

import android.content.Context
import android.text.InputType
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.github.vivchar.rendererrecyclerviewadapter.ViewHolder
import com.github.vivchar.rendererrecyclerviewadapter.ViewState
import com.github.vivchar.rendererrecyclerviewadapter.ViewStateProvider
import com.github.vivchar.rendererrecyclerviewadapter.binder.ViewBinder
import com.google.android.libraries.places.widget.Autocomplete
import com.thejuki.kformmaster.helper.FormBuildHelper
import com.thejuki.kformmaster.state.FormEditTextViewState
import com.thejuki.kformmaster.view.BaseFormViewBinder
import com.thejuki.kformmasterexample.R
import com.thejuki.kformmasterexample.custom.model.FormPlacesAutoCompleteElement

/**
 * Form Custom ViewBinder
 *
 * View Binder for [FormPlacesAutoCompleteElement]
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormPlacesAutoCompleteViewBinder(private val context: Context, private val formBuilder: FormBuildHelper, @LayoutRes private val layoutID: Int?, private val fragment: Fragment? = null) : BaseFormViewBinder() {
    var viewBinder = ViewBinder(layoutID
            ?: R.layout.form_element, FormPlacesAutoCompleteElement::class.java, { model, finder, _ ->
        val textViewTitle = finder.find(R.id.formElementTitle) as AppCompatTextView
        val mainViewLayout = finder.find(R.id.formElementMainLayout) as? LinearLayout
        val textViewError = finder.find(R.id.formElementError) as AppCompatTextView
        val dividerView = finder.find(R.id.formElementDivider) as? View
        val itemView = finder.getRootView() as View
        val editTextValue = finder.find(R.id.formElementValue) as com.thejuki.kformmaster.widget.ClearableEditText
        baseSetup(model, dividerView, textViewTitle, textViewError, itemView, mainViewLayout, editTextValue)

        editTextValue.setText(model.valueAsString)
        editTextValue.hint = model.hint ?: ""
        editTextValue.alwaysShowClear = true

        editTextValue.setRawInputType(InputType.TYPE_NULL)
        editTextValue.isFocusable = false

        setClearableListener(model)

        val listener = View.OnClickListener {
            context.let { activity ->
                if (activity is FragmentActivity) {
                    val intent = Autocomplete.IntentBuilder(
                            model.autocompleteActivityMode, model.placeFields)
                            .build(activity)
                    if (fragment != null) {
                        fragment.startActivityForResult(intent, model.tag)
                    } else {
                        activity.startActivityForResult(intent, model.tag)
                    }
                }
            }
        }

        itemView.setOnClickListener(listener)
        editTextValue.setOnClickListener(listener)

    }, object : ViewStateProvider<FormPlacesAutoCompleteElement, ViewHolder> {
        override fun createViewStateID(model: FormPlacesAutoCompleteElement): Int {
            return model.id
        }

        override fun createViewState(holder: ViewHolder): ViewState<ViewHolder> {
            return FormEditTextViewState(holder)
        }
    })
}
