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
            FormCustomElement(tag)
                    .setTitle(title.orEmpty())
                    .setValue(value)
                    .setHint(hint)
                    .setError(error)
                    .setRequired(required)
                    .setVisible(visible)
                    .addAllValueObservers(valueObservers)
                    as FormCustomElement
}

/** FormBuildHelper extension to add a CustomElement */
fun FormBuildHelper.customEx(tag: Int = -1, init: CustomElementBuilder.() -> Unit): FormCustomElement {
    val element = CustomElementBuilder(tag).apply(init).build()
    element.id = ++lastId
    elements.add(element)
    return element
}
