package com.thejuki.kformmaster.state

import android.widget.Button
import com.github.vivchar.rendererrecyclerviewadapter.ViewHolder
import com.thejuki.kformmaster.R
import com.thejuki.kformmaster.model.FormButtonElement

/**
 * Form Button ViewState
 *
 * View State for [FormButtonElement]
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormButtonViewState(holder: ViewHolder) : BaseFormViewState(holder) {
    private var value: String? = null

    init {
        val button = holder.viewFinder.find(R.id.formElementValue) as Button
        value = button.text.toString()
    }

    override fun restore(holder: ViewHolder) {
        super.restore(holder)
        holder.viewFinder.setText(R.id.formElementValue, value)
    }
}