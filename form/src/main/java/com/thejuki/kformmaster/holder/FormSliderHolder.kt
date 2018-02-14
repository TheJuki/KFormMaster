package com.thejuki.kformmaster.holder

import android.support.v7.widget.AppCompatTextView
import android.view.View
import android.widget.SeekBar
import com.thejuki.kformmaster.R
import com.thejuki.kformmaster.renderer.FormSliderRenderer

/**
 * Form Slider Holder
 *
 * View Holder for SeekBar
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormSliderHolder(v: View, var mFormOnSeekBarChangeListener: FormSliderRenderer.FormOnSeekBarChangeListener) : BaseFormHolder(v) {
    var mSliderValue: SeekBar? = null
    var mProgressValue: AppCompatTextView? = null

    init {
        mSliderValue = v.findViewById(R.id.formElementValue) as SeekBar
        mProgressValue = v.findViewById(R.id.formElementProgress) as AppCompatTextView

        if (mSliderValue != null)
            mSliderValue!!.setOnSeekBarChangeListener(mFormOnSeekBarChangeListener)
    }
}