package com.thejuki.kformmaster.state

import android.support.v7.widget.AppCompatTextView
import com.github.vivchar.rendererrecyclerviewadapter.ViewHolder
import com.github.vivchar.rendererrecyclerviewadapter.ViewState
import com.thejuki.kformmaster.R

/**
 * Base ViewState
 *
 * Base View State to save error and title
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
abstract class BaseFormViewState(holder: ViewHolder) : ViewState<ViewHolder> {
    private var title: String? = null
    private var error: String? = null

    init {
        val textViewTitle = holder.viewFinder.find(R.id.formElementTitle) as AppCompatTextView?
        val textViewError = holder.viewFinder.find(R.id.formElementError) as AppCompatTextView?

        if (textViewTitle != null) {
            title = textViewTitle.text.toString()
        }
        if (textViewError != null) {
            error = textViewError.text.toString()
        }
    }

    override fun restore(holder: ViewHolder) {
        val textViewTitle = holder.viewFinder.find(R.id.formElementTitle) as AppCompatTextView?
        val textViewError = holder.viewFinder.find(R.id.formElementError) as AppCompatTextView?

        if (textViewTitle != null) {
            textViewTitle.text = title
        }
        if (textViewError != null) {
            textViewError.text = error
        }
    }
}