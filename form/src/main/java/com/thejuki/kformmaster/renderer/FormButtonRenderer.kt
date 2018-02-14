package com.thejuki.kformmaster.renderer

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.thejuki.kformmaster.R
import com.thejuki.kformmaster.helper.FormBuildHelper
import com.thejuki.kformmaster.holder.FormButtonHolder
import com.thejuki.kformmaster.model.FormButtonElement

/**
 * Form Button Renderer
 *
 * Renderer for FormButtonElement
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormButtonRenderer(type: Int, context: Context, private val formBuilder: FormBuildHelper) : BaseFormRenderer<FormButtonElement<*>, FormButtonHolder>(type, context) {

    override fun bindView(formElement: FormButtonElement<*>, holder: FormButtonHolder) {
        super.bindView(formElement, holder)

        holder.mFormCustomOnClickListener.updateTag(formElement.getTag())

        holder.mButtonValue!!.text = formElement.valueAsString
    }

    override fun createViewHolder(parent: ViewGroup?): FormButtonHolder {
        val listener = FormCustomOnClickListener()

        return FormButtonHolder(inflate(R.layout.form_element_button, parent), listener)
    }

    inner class FormCustomOnClickListener : View.OnClickListener {
        private var tag: Int = 0

        fun updateTag(tag: Int) {
            this.tag = tag
        }

        override fun onClick(view: View) {
            val formElement = formBuilder.getFormElement(tag)
            formElement!!.mValueChanged?.onValueChanged(formElement)
            formBuilder.onValueChanged(formElement)
        }
    }
}
