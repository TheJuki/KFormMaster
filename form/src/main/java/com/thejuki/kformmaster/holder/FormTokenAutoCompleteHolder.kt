package com.thejuki.kformmaster.holder

import android.view.View
import com.thejuki.kformmaster.R
import com.thejuki.kformmaster.token.ItemsCompletionView

/**
 * Form TokenAutoComplete Holder
 *
 * View Holder for ItemsCompletionView
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormTokenAutoCompleteHolder(v: View) : BaseFormHolder(v) {
    var mEditTextValue: ItemsCompletionView? = null

    init {
        mEditTextValue = v.findViewById(R.id.formElementValue) as ItemsCompletionView
    }
}