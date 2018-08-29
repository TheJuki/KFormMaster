package com.thejuki.kformmaster.view

import android.content.Context
import android.support.annotation.LayoutRes
import android.support.v7.widget.AppCompatSeekBar
import android.support.v7.widget.AppCompatTextView
import android.view.View
import android.widget.SeekBar
import com.github.vivchar.rendererrecyclerviewadapter.ViewHolder
import com.github.vivchar.rendererrecyclerviewadapter.ViewState
import com.github.vivchar.rendererrecyclerviewadapter.ViewStateProvider
import com.github.vivchar.rendererrecyclerviewadapter.binder.ViewBinder
import com.thejuki.kformmaster.R
import com.thejuki.kformmaster.helper.FormBuildHelper
import com.thejuki.kformmaster.model.FormSliderElement
import com.thejuki.kformmaster.state.FormSliderViewState
import kotlin.math.roundToInt

/**
 * Form Slider Binder
 *
 * View Binder for [FormSliderElement]
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormSliderViewBinder(private val context: Context, private val formBuilder: FormBuildHelper, @LayoutRes private val layoutID: Int?) : BaseFormViewBinder() {
    val viewBinder = ViewBinder(layoutID
            ?: R.layout.form_element_slider, FormSliderElement::class.java, { model, finder, _ ->
        val textViewTitle = finder.find(R.id.formElementTitle) as? AppCompatTextView
        val textViewError = finder.find(R.id.formElementError) as? AppCompatTextView
        val itemView = finder.getRootView() as View
        baseSetup(model, textViewTitle, textViewError, itemView)

        val slider = finder.find(R.id.formElementValue) as AppCompatSeekBar
        val progressValue = finder.find(R.id.formElementProgress) as AppCompatTextView

        model.editView = slider

        if (model.value == null) {
            model.value = model.min
        }

        slider.progress = model.value as Int
        slider.max = model.max

        progressValue.text = model.value.toString()

        slider.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {}
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                val sliderValue = seekBar?.progress ?: 0
                var roundedValue: Int = 0
                val maximumValue: Double = model.max.toDouble()
                val minimumValue: Double = model.min.toDouble()

                if (model.steps != null) {
                    model.steps?.let {
                        val steps: Double = it.toDouble()
                        val stepValue: Int = ((sliderValue - minimumValue) / (maximumValue - minimumValue) * steps).roundToInt()
                        val stepAmount: Int = ((maximumValue - minimumValue) / steps).roundToInt()
                        roundedValue = stepValue * stepAmount + model.min
                    }
                } else if (model.steps == null && model.incrementBy != null) {
                    model.incrementBy?.let {
                        val offset = minimumValue % it
                        val stepValue: Int = ((sliderValue - offset) / it).roundToInt()
                        roundedValue = stepValue * it + offset.roundToInt()
                    }
                } else if (model.steps == null && model.incrementBy == null) {
                    roundedValue = sliderValue
                }

                if (roundedValue < model.min) {
                    roundedValue = model.min
                } else if (roundedValue > model.max) {
                    roundedValue = model.max
                }

                model.error = null
                model.setValue(roundedValue)
                formBuilder.onValueChanged(model)

                slider.progress = model.value as Int
                progressValue.text = model.value.toString()
            }
        })
    }, object : ViewStateProvider<FormSliderElement, ViewHolder> {
        override fun createViewStateID(model: FormSliderElement): Int {
            return model.id
        }

        override fun createViewState(holder: ViewHolder): ViewState<ViewHolder> {
            return FormSliderViewState(holder)
        }
    })
}
