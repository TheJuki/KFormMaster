package com.thejuki.kformmaster.holder

import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.RecyclerView
import android.view.View

import com.thejuki.kformmaster.R

/**
 * Base Form Holder
 *
 * View Holder for Title and Error
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
open class BaseFormHolder(v: View) : RecyclerView.ViewHolder(v) {
    val mTextViewTitle: AppCompatTextView? = v.findViewById(R.id.formElementTitle) as? AppCompatTextView
    val mTextViewError: AppCompatTextView? = v.findViewById(R.id.formElementError) as? AppCompatTextView

    companion object {

        fun createInstance(v: View): BaseFormHolder {
            return BaseFormHolder(v)
        }
    }
}