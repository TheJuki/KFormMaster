package com.thejuki.kformmaster.state

import android.support.v7.widget.SwitchCompat
import com.github.vivchar.rendererrecyclerviewadapter.ViewHolder
import com.thejuki.kformmaster.R
import com.thejuki.kformmaster.model.FormSwitchElement

/**
 * Form Switch ViewState
 *
 * View State for [FormSwitchElement]
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormSwitchViewState(holder: ViewHolder) : BaseFormViewState(holder) {
    private var value: Boolean? = null

    init {
        val switch = holder.viewFinder.find(R.id.formElementValue) as SwitchCompat
        value = switch.isChecked
    }

    override fun restore(holder: ViewHolder) {
        super.restore(holder)
        holder.viewFinder.setChecked(R.id.formElementValue, value ?: false)
    }
}