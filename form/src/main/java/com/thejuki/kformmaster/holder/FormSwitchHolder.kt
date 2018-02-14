package com.thejuki.kformmaster.holder

import android.support.v7.widget.SwitchCompat
import android.view.View
import com.thejuki.kformmaster.R
import com.thejuki.kformmaster.renderer.FormSwitchRenderer

/**
 * Form Switch Holder
 *
 * View Holder for SwitchCompat
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormSwitchHolder(v: View, var mFormOnCheckedChangeListener: FormSwitchRenderer.FormOnCheckedChangeListener) : BaseFormHolder(v) {
    var mSwitchValue: SwitchCompat? = null

    init {
        mSwitchValue = v.findViewById(R.id.formElementValue) as SwitchCompat

        if (mSwitchValue != null)
            mSwitchValue!!.setOnCheckedChangeListener(mFormOnCheckedChangeListener)
    }
}