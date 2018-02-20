package com.thejuki.kformmaster.state

import android.support.v7.widget.AppCompatAutoCompleteTextView
import com.github.vivchar.rendererrecyclerviewadapter.ViewHolder
import com.thejuki.kformmaster.R
import com.thejuki.kformmaster.model.FormAutoCompleteElement

/**
 * Form AutoComplete ViewState
 *
 * View State for [FormAutoCompleteElement]
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormAutoCompleteViewState(holder: ViewHolder) : BaseFormViewState(holder) {
    private var value: String? = null

    init {
        val autoCompleteTextView = holder.viewFinder.find(R.id.formElementValue) as AppCompatAutoCompleteTextView
        value = autoCompleteTextView.text.toString()
    }

    override fun restore(holder: ViewHolder) {
        super.restore(holder)
        holder.viewFinder.setText(R.id.formElementValue, value)
    }
}