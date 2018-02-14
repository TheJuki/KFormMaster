package com.thejuki.kformmaster.holder

import android.support.v7.widget.AppCompatEditText
import android.view.View

import com.thejuki.kformmaster.R
import com.thejuki.kformmaster.renderer.FormEditTextRenderer

/**
 * Form EditText Holder
 *
 * View Holder for AppCompatEditText
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormEditTextHolder(v: View, var mFormCustomEditTextListener: FormEditTextRenderer.FormCustomEditTextListener) : BaseFormHolder(v) {
    var mEditTextValue: AppCompatEditText? = null

    init {
        mEditTextValue = v.findViewById(R.id.formElementValue) as AppCompatEditText
        mFormCustomEditTextListener.updateViewHolder(this)

        if (mEditTextValue != null)
            mEditTextValue!!.addTextChangedListener(mFormCustomEditTextListener)
    }
}