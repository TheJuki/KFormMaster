package com.thejuki.kformmasterexample.custom.helper

import com.thejuki.kformmaster.helper.BaseElementBuilder
import com.thejuki.kformmaster.helper.FormBuildHelper
import com.thejuki.kformmasterexample.custom.model.FormCustomElement

/**
 * Form Builder Extensions
 *
 * Used for Kotlin DSL to create the FormBuildHelper
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */

/** Builder method to add a CustomElement */
class CustomElementBuilder(tag: Int = -1) : BaseElementBuilder<String>(tag) {
    override fun build() =
            FormCustomElement(tag).apply {
                this@CustomElementBuilder.let {
                    title = it.title.orEmpty()
                    value = it.value
                    hint = it.hint
                    rightToLeft = it.rightToLeft
                    maxLines = it.maxLines
                    error = it.error
                    required = it.required
                    enabled = it.enabled
                    visible = it.visible
                    valueObservers.addAll(it.valueObservers)
                    // Colors
                    backgroundColor = it.backgroundColor
                    hintTextColor = it.hintTextColor
                    titleTextColor = it.titleTextColor
                    titleFocusedTextColor = it.titleFocusedTextColor
                    valueTextColor = it.valueTextColor
                    errorTextColor = it.errorTextColor
                }
            }
}

/** FormBuildHelper extension to add a CustomElement */
fun FormBuildHelper.customEx(tag: Int = -1, init: CustomElementBuilder.() -> Unit): FormCustomElement {
    return addFormElement(CustomElementBuilder(tag).apply(init).build())
}
