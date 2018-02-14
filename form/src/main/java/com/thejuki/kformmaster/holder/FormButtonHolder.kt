package com.thejuki.kformmaster.holder

import android.view.View
import android.widget.Button

import com.thejuki.kformmaster.R
import com.thejuki.kformmaster.renderer.FormButtonRenderer

/**
 * Form Button Holder
 *
 * View Holder for Button
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormButtonHolder(v: View, var mFormCustomOnClickListener: FormButtonRenderer.FormCustomOnClickListener) : BaseFormHolder(v) {
    var mButtonValue: Button? = null

    init {
        mButtonValue = v.findViewById(R.id.formElementValue) as Button

        if (mButtonValue != null)
            mButtonValue!!.setOnClickListener(mFormCustomOnClickListener)
    }
}