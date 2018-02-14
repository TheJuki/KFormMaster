package com.thejuki.kformmaster.holder

import android.support.v7.widget.AppCompatEditText
import android.view.View

import com.thejuki.kformmaster.R

/**
 * Form EditText Holder
 *
 * View Holder for AppCompatEditText (which on click opens a dialog)
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormPickerHolder(v: View) : BaseFormHolder(v) {
    var mEditTextValue: AppCompatEditText = v.findViewById(R.id.formElementValue) as AppCompatEditText

}