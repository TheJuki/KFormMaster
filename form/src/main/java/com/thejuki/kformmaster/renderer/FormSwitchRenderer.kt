package com.thejuki.kformmaster.renderer

import android.content.Context
import android.view.ViewGroup
import android.widget.CompoundButton
import com.thejuki.kformmaster.R
import com.thejuki.kformmaster.helper.FormBuildHelper
import com.thejuki.kformmaster.holder.FormSwitchHolder
import com.thejuki.kformmaster.model.FormSwitchElement

/**
 * Form Switch Renderer
 *
 * Renderer for FormSwitchHolder
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormSwitchRenderer(type: Int, context: Context, private val formBuilder: FormBuildHelper) :
        BaseFormRenderer<FormSwitchElement<*>, FormSwitchHolder>(type, context) {

    override fun bindView(formElement: FormSwitchElement<*>, holder: FormSwitchHolder) {
        super.bindView(formElement, holder)

        setSwitchFocusEnabled(holder)

        holder.mFormOnCheckedChangeListener.updateTag(formElement.getTag())
        holder.mSwitchValue!!.isChecked = formElement.isOn()
    }

    override fun createViewHolder(parent: ViewGroup?): FormSwitchHolder {
        val listener = FormOnCheckedChangeListener()

        return FormSwitchHolder(inflate(R.layout.form_element_switch, parent), listener)
    }

    /**
     * brings focus when clicked on the whole container
     *
     * @param holder
     */
    private fun setSwitchFocusEnabled(holder: FormSwitchHolder) {
        holder.itemView.setOnClickListener {
            holder.mSwitchValue!!.isChecked = !holder.mSwitchValue!!.isChecked
        }
    }

    inner class FormOnCheckedChangeListener : CompoundButton.OnCheckedChangeListener {

        override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
            val formElement = formBuilder.getFormElement(tag)

            if (isChecked) {
                formElement!!.setValue((formElement as FormSwitchElement).mOnValue)
            } else {
                formElement!!.setValue((formElement as FormSwitchElement).mOffValue)
            }

            formBuilder.onValueChanged(formElement)
        }

        private var tag: Int = 0

        fun updateTag(tag: Int) {
            this.tag = tag
        }
    }
}