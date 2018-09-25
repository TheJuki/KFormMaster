package com.thejuki.kformmasterexample.custom.state

import androidx.appcompat.widget.AppCompatEditText
import com.github.vivchar.rendererrecyclerviewadapter.ViewHolder
import com.thejuki.kformmaster.R
import com.thejuki.kformmaster.state.BaseFormViewState

/**
 * Form Custom ViewState
 *
 * View State for custom form element
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormCustomViewState(holder: ViewHolder) : BaseFormViewState(holder) {
    private var value: String? = null

    init {
        val editText = holder.viewFinder.find(R.id.formElementValue) as AppCompatEditText
        value = editText.text.toString()
    }

    override fun restore(holder: ViewHolder) {
        super.restore(holder)
        holder.viewFinder.setText(R.id.formElementValue, value)
    }
}