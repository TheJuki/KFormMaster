package com.thejuki.kformmaster.renderer

import android.content.Context
import android.view.View
import android.view.ViewGroup

import com.github.vivchar.rendererrecyclerviewadapter.ViewRenderer

import com.thejuki.kformmaster.holder.BaseFormHolder
import com.thejuki.kformmaster.model.BaseFormElement

/**
 * Base Form Renderer
 *
 * Renderer for title and error
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
abstract class BaseFormRenderer<M : BaseFormElement<*>, VH : BaseFormHolder>(type: Int, context: Context) : ViewRenderer<M, VH>(type, context) {

    override fun bindView(formElement: M, holder: VH) {
        // Setup title and error if present
        if (holder.mTextViewTitle != null) {
            holder.mTextViewTitle.text = formElement.getTitle()
        }

        if (holder.mTextViewError != null) {
            setError(holder, formElement.getError())
        }

        if (formElement.isVisible()) {
            holder.itemView.visibility = View.VISIBLE
            holder.itemView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        } else {
            holder.itemView.visibility = View.GONE
            holder.itemView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0)
        }
    }

    /**
     * Sets mTextViewError visibility according to error
     *
     * @param holder
     * @param error
     */
    fun setError(holder: VH, error: String?) {
        if (error == null || error.isEmpty()) {
            holder.mTextViewError?.visibility = View.GONE
            return
        }

        holder.mTextViewError?.text = error
        holder.mTextViewError?.visibility = View.VISIBLE
    }
}
