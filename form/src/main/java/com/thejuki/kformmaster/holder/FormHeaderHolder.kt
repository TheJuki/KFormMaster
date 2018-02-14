package com.thejuki.kformmaster.holder

import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.RecyclerView
import android.view.View

import com.thejuki.kformmaster.R

/**
 * Form EditText Holder
 *
 * View Holder for Header TextView
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormHeaderHolder(v: View) : RecyclerView.ViewHolder(v) {
    var mTextViewTitle: AppCompatTextView = v.findViewById(R.id.formElementTitle) as AppCompatTextView

    companion object {

        fun createInstance(v: View): FormHeaderHolder {
            return FormHeaderHolder(v)
        }
    }
}