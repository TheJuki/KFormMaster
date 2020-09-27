package com.thejuki.kformmaster.view

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.github.vivchar.rendererrecyclerviewadapter.ViewRenderer
import com.thejuki.kformmaster.R
import com.thejuki.kformmaster.helper.FormBuildHelper
import com.thejuki.kformmaster.helper.FormViewFinder
import com.thejuki.kformmaster.model.FormTokenAutoCompleteElement
import com.thejuki.kformmaster.token.ItemsCompletionView
import com.tokenautocomplete.TokenCompleteTextView

/**
 * Form TokenAutoComplete ViewRenderer
 *
 * View Binder for [FormTokenAutoCompleteElement]
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormTokenAutoCompleteViewRenderer(private val formBuilder: FormBuildHelper, @LayoutRes private val layoutID: Int?) : BaseFormViewRenderer() {
    val viewRenderer = ViewRenderer(layoutID
            ?: R.layout.form_element_token_auto_complete, FormTokenAutoCompleteElement::class.java) { model, finder: FormViewFinder, _ ->
        val textViewTitle = finder.find(R.id.formElementTitle) as? AppCompatTextView
        val mainViewLayout = finder.find(R.id.formElementMainLayout) as? LinearLayout
        val textViewError = finder.find(R.id.formElementError) as? AppCompatTextView
        val dividerView = finder.find(R.id.formElementDivider) as? View
        val itemView = finder.getRootView() as View
        val itemsCompletionView = finder.find(R.id.formElementValue) as ItemsCompletionView
        baseSetup(model, dividerView, textViewTitle, textViewError, itemView, mainViewLayout, itemsCompletionView)

        model.value?.forEach { item ->
            itemsCompletionView.addObjectAsync(item)
        }

        itemsCompletionView.hint = model.hint ?: ""

        val itemsAdapter = if (model.arrayAdapter != null)
            model.arrayAdapter
        else
            ArrayAdapter(itemView.context, android.R.layout.simple_list_item_1, model.options
                    ?: listOf())
        itemsCompletionView.setAdapter<ArrayAdapter<*>>(itemsAdapter)

        model.dropdownWidth?.let {
            itemsCompletionView.dropDownWidth = it
        }

        itemsCompletionView.setTokenClickStyle(TokenCompleteTextView.TokenClickStyle.Select)

        setEditTextFocusEnabled(model, itemsCompletionView, itemView)

        itemsCompletionView.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                textViewTitle?.setTextColor(ContextCompat.getColor(itemView.context,
                        R.color.colorFormMasterElementFocusedTitle))
            } else {
                textViewTitle?.setTextColor(ContextCompat.getColor(itemView.context,
                        R.color.colorFormMasterElementTextTitle))

                model.error = null
                model.setValue(itemsCompletionView.objects)
                formBuilder.onValueChanged(model)
            }
        }
    }

    private fun setEditTextFocusEnabled(model: FormTokenAutoCompleteElement<*>,
                                        itemsCompletionView: ItemsCompletionView,
                                        itemView: View) {
        itemView.setOnClickListener {
            // Invoke onClick Unit
            model.onClick?.invoke()

            itemsCompletionView.requestFocus()
            if (itemsCompletionView.text.isNotEmpty()) {
                itemsCompletionView.selectAll()
            }
            val imm = itemView.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            itemsCompletionView.setSelection(itemsCompletionView.text.length)
            imm.showSoftInput(itemsCompletionView, InputMethodManager.SHOW_IMPLICIT)
        }
    }
}
