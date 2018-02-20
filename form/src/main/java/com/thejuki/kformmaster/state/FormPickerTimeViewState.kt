package com.thejuki.kformmaster.state

import android.support.v7.widget.AppCompatEditText
import com.github.vivchar.rendererrecyclerviewadapter.ViewHolder
import com.thejuki.kformmaster.R
import com.thejuki.kformmaster.model.FormPickerTimeElement

/**
 * Form Picker Time ViewState
 *
 * View State for [FormPickerTimeElement]
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormPickerTimeViewState(holder: ViewHolder) : BaseFormViewState(holder) {
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