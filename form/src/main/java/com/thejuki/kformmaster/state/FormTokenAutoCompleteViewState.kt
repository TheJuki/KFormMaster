package com.thejuki.kformmaster.state

import com.github.vivchar.rendererrecyclerviewadapter.ViewHolder
import com.thejuki.kformmaster.R
import com.thejuki.kformmaster.model.FormTokenAutoCompleteElement
import com.thejuki.kformmaster.token.ItemsCompletionView
import java.util.*

/**
 * Form Token AutoComplete ViewState
 *
 * View State for [FormTokenAutoCompleteElement]
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormTokenAutoCompleteViewState(holder: ViewHolder) : BaseFormViewState(holder) {
    private var value: List<*>? = null

    init {
        val itemView = holder.viewFinder.find(R.id.formElementValue) as ItemsCompletionView
        value = itemView.objects
    }

    override fun restore(holder: ViewHolder) {
        super.restore(holder)
        val itemView = holder.viewFinder.find(R.id.formElementValue) as ItemsCompletionView
        itemView.objects?.clear()
        itemView.objects?.addAll(value ?: ArrayList())
    }
}