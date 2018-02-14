package com.thejuki.kformmaster.renderer

import android.content.Context
import android.view.ViewGroup
import android.widget.SeekBar
import com.thejuki.kformmaster.R
import com.thejuki.kformmaster.helper.FormBuildHelper
import com.thejuki.kformmaster.holder.FormSliderHolder
import com.thejuki.kformmaster.model.FormSliderElement
import kotlin.math.roundToInt

/**
 * Form Slider Renderer
 *
 * Renderer for FormSliderElement
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormSliderRenderer(type: Int, context: Context, private val formBuilder: FormBuildHelper) :
        BaseFormRenderer<FormSliderElement, FormSliderHolder>(type, context) {

    override fun bindView(formElement: FormSliderElement, holder: FormSliderHolder) {
        super.bindView(formElement, holder)

        holder.mFormOnSeekBarChangeListener.updateTag(formElement.getTag())

        if (formElement.mValue == null) {
            formElement.mValue = formElement.mMin
        }

        holder.mSliderValue!!.progress = formElement.mValue as Int
        holder.mSliderValue!!.max = formElement.mMax

        holder.mProgressValue!!.text = formElement.mValue.toString()
    }

    override fun createViewHolder(parent: ViewGroup?): FormSliderHolder {
        val listener = FormOnSeekBarChangeListener()

        return FormSliderHolder(inflate(R.layout.form_element_slider, parent), listener)
    }

    inner class FormOnSeekBarChangeListener : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            // Do nothing
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {
            // Do nothing
        }

        override fun onStopTrackingTouch(seekBar: SeekBar?) {
            val formElement = formBuilder.getFormElement(tag) as FormSliderElement

            val sliderValue = seekBar?.progress ?: 0
            val steps: Double = formElement.mSteps.toDouble()
            val maximumValue: Double = formElement.mMax.toDouble()
            val minimumValue: Double = formElement.mMin.toDouble()
            val stepValue: Int = ((sliderValue - minimumValue) / (maximumValue - minimumValue) * steps).roundToInt()
            val stepAmount: Int = ((maximumValue - minimumValue) / steps).roundToInt()
            var roundedValue: Int = stepValue * stepAmount + formElement.mMin

            if (roundedValue < formElement.mMin) {
                roundedValue = formElement.mMin
            } else if (roundedValue > formElement.mMax) {
                roundedValue = formElement.mMax
            }

            formElement.setValue(roundedValue)
            formBuilder.onValueChanged(formElement)
            formBuilder.refreshView()
        }

        private var tag: Int = 0

        fun updateTag(tag: Int) {
            this.tag = tag
        }
    }
}