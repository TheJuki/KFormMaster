package com.thejuki.kformmasterexample.custom.helper

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

/** FormBuildHelper extension to add a FormCustomElement */
fun FormBuildHelper.customEx(tag: Int = -1, init: FormCustomElement.() -> Unit): FormCustomElement {
    return addFormElement(FormCustomElement(tag).apply(init))
}
