package com.thejuki.kformmaster.state

import androidx.appcompat.widget.AppCompatSeekBar
import androidx.appcompat.widget.AppCompatTextView
import com.github.vivchar.rendererrecyclerviewadapter.ViewHolder
import com.thejuki.kformmaster.R
import com.thejuki.kformmaster.model.FormSliderElement

/**
 * Form Slider ViewState
 *
 * View State for [FormSliderElement]
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormSliderViewState(holder: ViewHolder) : BaseFormViewState(holder) {
    private var progressValue: String? = null
    private var sliderValue: Int? = null

    init {
        val slider = holder.viewFinder.find(R.id.formElementValue) as AppCompatSeekBar
        sliderValue = slider.progress
        val progressValueTextView = holder.viewFinder.find(R.id.formElementProgress) as AppCompatTextView
        progressValue = progressValueTextView.text.toString()
    }

    override fun restore(holder: ViewHolder) {
        super.restore(holder)
        holder.viewFinder.setProgress(R.id.formElementValue, sliderValue ?: 0)
        holder.viewFinder.setText(R.id.formElementProgress, progressValue)
    }
}