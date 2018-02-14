package com.thejuki.kformmaster.renderer

import android.content.Context
import android.view.ViewGroup

import com.github.vivchar.rendererrecyclerviewadapter.ViewRenderer

import com.thejuki.kformmaster.R
import com.thejuki.kformmaster.holder.FormHeaderHolder
import com.thejuki.kformmaster.model.FormHeader

/**
 * Form Header Renderer
 *
 * Renderer for FormHeader
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormHeaderRenderer(type: Int, context: Context) : ViewRenderer<FormHeader, FormHeaderHolder>(type, context) {

    override fun bindView(formElement: FormHeader, holder: FormHeaderHolder) {
        holder.mTextViewTitle.text = formElement.getTitle()
    }

    override fun createViewHolder(parent: ViewGroup?): FormHeaderHolder {
        return FormHeaderHolder.createInstance(inflate(R.layout.form_element_header, parent))
    }
}
