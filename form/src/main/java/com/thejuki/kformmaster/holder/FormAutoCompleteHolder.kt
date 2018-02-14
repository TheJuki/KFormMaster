package com.thejuki.kformmaster.holder

import android.support.v7.widget.AppCompatAutoCompleteTextView
import android.view.View

import com.thejuki.kformmaster.R
import com.thejuki.kformmaster.renderer.FormAutoCompleteRenderer

/**
 * Form Auto Complete Holder
 *
 * View Holder for AppCompatAutoCompleteTextView
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormAutoCompleteHolder(v: View, var mFormCustomEditTextListener: FormAutoCompleteRenderer.FormCustomEditTextListener) : BaseFormHolder(v) {
    var mEditTextValue: AppCompatAutoCompleteTextView? = null

    init {
        mEditTextValue = v.findViewById(R.id.formElementValue) as AppCompatAutoCompleteTextView
        mFormCustomEditTextListener.updateViewHolder(this)

        if (mEditTextValue != null)
            mEditTextValue!!.addTextChangedListener(mFormCustomEditTextListener)
    }
}