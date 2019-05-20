package com.thejuki.kformmaster.state

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.github.vivchar.rendererrecyclerviewadapter.ViewHolder
import com.thejuki.kformmaster.R

/**
 * Form Image ViewState
 *
 * View State for [Drawable]
 *
 * @author **soareseneves** ([GitHub](https://github.com/soareseneves))
 * @version 1.0
 */
class FormImageViewState(holder: ViewHolder) : BaseFormViewState(holder) {
    private var value: Drawable? = null

    init {
        val imageView = holder.viewFinder.find(R.id.formElementValue) as ImageView
        value = imageView.drawable
    }

    override fun restore(holder: ViewHolder) {
        super.restore(holder)
        holder.viewFinder.setImageDrawable(R.id.formElementValue, value)
    }
}