package com.thejuki.kformmasterexample.custom.view

import android.text.InputType
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.github.vivchar.rendererrecyclerviewadapter.ViewRenderer
import com.google.android.libraries.places.widget.Autocomplete
import com.thejuki.kformmaster.helper.FormBuildHelper
import com.thejuki.kformmaster.helper.FormViewFinder
import com.thejuki.kformmaster.view.BaseFormViewRenderer
import com.thejuki.kformmasterexample.R
import com.thejuki.kformmasterexample.custom.model.FormPlacesAutoCompleteElement

/**
 * Form Custom ViewRenderer
 *
 * View Binder for [FormPlacesAutoCompleteElement]
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormPlacesAutoCompleteViewRenderer(private val formBuilder: FormBuildHelper, @LayoutRes private val layoutID: Int?, private val fragment: Fragment? = null) : BaseFormViewRenderer() {
    var viewRenderer = ViewRenderer(layoutID
            ?: R.layout.form_element, FormPlacesAutoCompleteElement::class.java) { model, finder: FormViewFinder, _ ->
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
            itemView.context.let { activity ->
                if (activity is FragmentActivity) {
                    val intent = Autocomplete.IntentBuilder(
                            model.autocompleteActivityMode, model.placeFields)
                            .build(activity)

                    model.activityResultLauncher?.launch(intent)
                }
            }
        }

        itemView.setOnClickListener(listener)
        editTextValue.setOnClickListener(listener)
    }
}
