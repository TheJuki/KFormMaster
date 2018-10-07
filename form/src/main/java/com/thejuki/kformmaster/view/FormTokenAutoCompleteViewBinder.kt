package com.thejuki.kformmaster.view

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.github.vivchar.rendererrecyclerviewadapter.ViewHolder
import com.github.vivchar.rendererrecyclerviewadapter.ViewState
import com.github.vivchar.rendererrecyclerviewadapter.ViewStateProvider
import com.github.vivchar.rendererrecyclerviewadapter.binder.ViewBinder
import com.thejuki.kformmaster.R
import com.thejuki.kformmaster.helper.FormBuildHelper
import com.thejuki.kformmaster.model.FormTokenAutoCompleteElement
import com.thejuki.kformmaster.state.FormTokenAutoCompleteViewState
import com.thejuki.kformmaster.token.ItemsCompletionView
import com.tokenautocomplete.TokenCompleteTextView

/**
 * Form TokenAutoComplete ViewBinder
 *
 * View Binder for [FormTokenAutoCompleteElement]
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormTokenAutoCompleteViewBinder(private val context: Context, private val formBuilder: FormBuildHelper, @LayoutRes private val layoutID: Int?) : BaseFormViewBinder() {
    val viewBinder = ViewBinder(layoutID
            ?: R.layout.form_element_token_auto_complete, FormTokenAutoCompleteElement::class.java, { model, finder, _ ->
        val textViewTitle = finder.find(R.id.formElementTitle) as? AppCompatTextView
        val mainViewLayout = finder.find(R.id.formElementMainLayout) as? LinearLayout
        val textViewError = finder.find(R.id.formElementError) as? AppCompatTextView
        val dividerView = finder.find(R.id.formElementDivider) as? View
        val itemView = finder.getRootView() as View
        baseSetup(model, dividerView, textViewTitle, textViewError, itemView, mainViewLayout)

        val itemsCompletionView = finder.find(R.id.formElementValue) as ItemsCompletionView

        if (model.value != null) {
            (model.value as List<*>).forEach {
                itemsCompletionView.addObject(it)
            }
        }

        itemsCompletionView.hint = model.hint ?: ""

        model.editView = itemsCompletionView

        setEditTextFocusEnabled(itemsCompletionView, itemView)

        val itemsAdapter = if (model.arrayAdapter != null)
            model.arrayAdapter
        else
            ArrayAdapter(context, android.R.layout.simple_list_item_1, model.options ?: listOf())
        itemsCompletionView.setAdapter<ArrayAdapter<*>>(itemsAdapter)

        model.dropdownWidth?.let {
            itemsCompletionView.dropDownWidth = it
        }

        itemsCompletionView.setTokenClickStyle(TokenCompleteTextView.TokenClickStyle.Select)
        itemsCompletionView.allowDuplicates(false)

        setEditTextFocusEnabled(itemsCompletionView, itemView)

        itemsCompletionView.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                textViewTitle?.setTextColor(ContextCompat.getColor(context,
                        R.color.colorFormMasterElementFocusedTitle))
            } else {
                textViewTitle?.setTextColor(ContextCompat.getColor(context,
                        R.color.colorFormMasterElementTextTitle))

                model.error = null
                model.setValue(itemsCompletionView.objects)
                formBuilder.onValueChanged(model)
            }
        }
    }, object : ViewStateProvider<FormTokenAutoCompleteElement<*>, ViewHolder> {
        override fun createViewStateID(model: FormTokenAutoCompleteElement<*>): Int {
            return model.id
        }

        override fun createViewState(holder: ViewHolder): ViewState<ViewHolder> {
            return FormTokenAutoCompleteViewState(holder)
        }
    })

    private fun setEditTextFocusEnabled(itemsCompletionView: ItemsCompletionView, itemView: View) {
        itemView.setOnClickListener {
            itemsCompletionView.requestFocus()
            if (itemsCompletionView.text.isNotEmpty()) {
                itemsCompletionView.selectAll()
            }
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            itemsCompletionView.setSelection(itemsCompletionView.text.length)
            imm.showSoftInput(itemsCompletionView, InputMethodManager.SHOW_IMPLICIT)
        }
    }
}
