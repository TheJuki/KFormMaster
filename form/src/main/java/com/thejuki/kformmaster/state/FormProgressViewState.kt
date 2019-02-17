package com.thejuki.kformmaster.state

import android.widget.ProgressBar
import com.github.vivchar.rendererrecyclerviewadapter.ViewHolder
import com.thejuki.kformmaster.R
import com.thejuki.kformmaster.model.FormProgressElement

/**
 * Form Progress ViewState
 *
 * View State for [FormProgressElement]
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormProgressViewState(holder: ViewHolder) : BaseFormViewState(holder) {
    private var progressValue: Int? = null

    init {
        val progressBar = holder.viewFinder.find(R.id.formElementValue) as ProgressBar
        progressValue = progressBar.progress
    }

    override fun restore(holder: ViewHolder) {
        super.restore(holder)
        holder.viewFinder.setProgress(R.id.formElementValue, progressValue ?: 0)
    }
}