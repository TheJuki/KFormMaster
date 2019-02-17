package com.thejuki.kformmaster.helper

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.thejuki.kformmaster.listener.OnFormElementValueChangedListener
import com.thejuki.kformmaster.model.*

/**
 * Form Builder
 *
 * Used for Kotlin DSL to create the FormBuildHelper
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */

@DslMarker
annotation class FormDsl

/** Type-safe builder method to initialize the form */
fun form(context: Context,
         recyclerView: RecyclerView,
         listener: OnFormElementValueChangedListener? = null,
         cacheForm: Boolean = true,
         formLayouts: FormLayouts? = null,
         init: FormBuildHelper.() -> Unit): FormBuildHelper {
    val form = FormBuildHelper(
            context = context,
            listener = listener,
            recyclerView = recyclerView,
            cacheForm = cacheForm,
            formLayouts = formLayouts
    )
    form.init()
    form.setItems()
    return form
}

/** FormBuildHelper extension to add a FormHeader */
fun FormBuildHelper.header(init: FormHeader.() -> Unit): FormHeader {
    return addFormElement(FormHeader().apply(init))
}

/** FormBuildHelper extension to add a FormSingleLineEditTextElement */
fun FormBuildHelper.text(tag: Int = -1, init: FormSingleLineEditTextElement.() -> Unit): FormSingleLineEditTextElement {
    return addFormElement(FormSingleLineEditTextElement(tag).apply(init))
}

/** FormBuildHelper extension to add a FormMultiLineEditTextElement */
fun FormBuildHelper.textArea(tag: Int = -1, init: FormMultiLineEditTextElement.() -> Unit): FormMultiLineEditTextElement {
    return addFormElement(FormMultiLineEditTextElement(tag).apply(init))
}

/** FormBuildHelper extension to add a FormNumberEditTextElement */
fun FormBuildHelper.number(tag: Int = -1, init: FormNumberEditTextElement.() -> Unit): FormNumberEditTextElement {
    return addFormElement(FormNumberEditTextElement(tag).apply(init))
}

/** FormBuildHelper extension to add a FormEmailEditTextElement */
fun FormBuildHelper.email(tag: Int = -1, init: FormEmailEditTextElement.() -> Unit): FormEmailEditTextElement {
    return addFormElement(FormEmailEditTextElement(tag).apply(init))
}

/** FormBuildHelper extension to add a FormPasswordEditTextElement */
fun FormBuildHelper.password(tag: Int = -1, init: FormPasswordEditTextElement.() -> Unit): FormPasswordEditTextElement {
    return addFormElement(FormPasswordEditTextElement(tag).apply(init))
}

/** FormBuildHelper extension to add a FormPhoneEditTextElement */
fun FormBuildHelper.phone(tag: Int = -1, init: FormPhoneEditTextElement.() -> Unit): FormPhoneEditTextElement {
    return addFormElement(FormPhoneEditTextElement(tag).apply(init))
}

/** FormBuildHelper extension to add a FormAutoCompleteElement */
fun <T> FormBuildHelper.autoComplete(tag: Int = -1, init: FormAutoCompleteElement<T>.() -> Unit): FormAutoCompleteElement<T> {
    return addFormElement(FormAutoCompleteElement<T>(tag).apply(init))
}

/** FormBuildHelper extension to add a FormTokenAutoCompleteElement */
fun <T : List<*>> FormBuildHelper.autoCompleteToken(tag: Int = -1, init: FormTokenAutoCompleteElement<T>.() -> Unit): FormTokenAutoCompleteElement<T> {
    return addFormElement(FormTokenAutoCompleteElement<T>(tag).apply(init))
}

/** FormBuildHelper extension to add a FormButtonElement */
fun FormBuildHelper.button(tag: Int = -1, init: FormButtonElement.() -> Unit): FormButtonElement {
    return addFormElement(FormButtonElement(tag).apply(init))
}

/** FormBuildHelper extension to add a FormPickerDateElement */
fun FormBuildHelper.date(tag: Int = -1, init: FormPickerDateElement.() -> Unit): FormPickerDateElement {
    return addFormElement(FormPickerDateElement(tag).apply(init))
}

/** FormBuildHelper extension to add a FormPickerTimeElement */
fun FormBuildHelper.time(tag: Int = -1, init: FormPickerTimeElement.() -> Unit): FormPickerTimeElement {
    return addFormElement(FormPickerTimeElement(tag).apply(init))
}

/** FormBuildHelper extension to add a FormPickerDateTimeElement */
fun FormBuildHelper.dateTime(tag: Int = -1, init: FormPickerDateTimeElement.() -> Unit): FormPickerDateTimeElement {
    return addFormElement(FormPickerDateTimeElement(tag).apply(init))
}

/** FormBuildHelper extension to add a FormPickerDropDownElement */
fun <T> FormBuildHelper.dropDown(tag: Int = -1, init: FormPickerDropDownElement<T>.() -> Unit): FormPickerDropDownElement<T> {
    return addFormElement(FormPickerDropDownElement<T>(tag).apply(init))
}

/** FormBuildHelper extension to add a FormPickerMultiCheckBoxElement */
fun <T : List<*>> FormBuildHelper.multiCheckBox(tag: Int = -1, init: FormPickerMultiCheckBoxElement<T>.() -> Unit): FormPickerMultiCheckBoxElement<T> {
    return addFormElement(FormPickerMultiCheckBoxElement<T>(tag).apply(init))
}

/** FormBuildHelper extension to add a FormSegmentedElement */
fun <T> FormBuildHelper.segmented(tag: Int = -1, init: FormSegmentedElement<T>.() -> Unit): FormSegmentedElement<T> {
    return addFormElement(FormSegmentedElement<T>(tag).apply(init))
}

/** FormBuildHelper extension to add a FormSwitchElement */
fun <T> FormBuildHelper.switch(tag: Int = -1, init: FormSwitchElement<T>.() -> Unit): FormSwitchElement<T> {
    return addFormElement(FormSwitchElement<T>(tag).apply(init))
}

/** FormBuildHelper extension to add a FormCheckBoxElement */
fun <T> FormBuildHelper.checkBox(tag: Int = -1, init: FormCheckBoxElement<T>.() -> Unit): FormCheckBoxElement<T> {
    return addFormElement(FormCheckBoxElement<T>(tag).apply(init))
}

/** FormBuildHelper extension to add a FormSliderElement */
fun FormBuildHelper.slider(tag: Int = -1, init: FormSliderElement.() -> Unit): FormSliderElement {
    return addFormElement(FormSliderElement(tag).apply(init))
}

/** FormBuildHelper extension to add a FormProgressElement */
fun FormBuildHelper.progress(tag: Int = -1, init: FormProgressElement.() -> Unit): FormProgressElement {
    return addFormElement(FormProgressElement(tag).apply(init))
}

/** FormBuildHelper extension to add a FormLabelElement */
fun FormBuildHelper.label(tag: Int = -1, init: FormLabelElement.() -> Unit): FormLabelElement {
    return addFormElement(FormLabelElement(tag).apply(init))
}

/** FormBuildHelper extension to add a FormTextViewElement */
fun FormBuildHelper.textView(tag: Int = -1, init: FormTextViewElement.() -> Unit): FormTextViewElement {
    return addFormElement(FormTextViewElement(tag).apply(init))
}
