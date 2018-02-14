package com.thejuki.kformmaster.helper

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.widget.ArrayAdapter
import com.thejuki.kformmaster.listener.OnFormElementValueChangedListener
import com.thejuki.kformmaster.model.*
import java.io.Serializable
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * Form Builder
 *
 * Used for Kotlin DSL to create the FormBuildHelper
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */

/** Type-safe builder method to initialize the form */
fun form(context: Context,
         recyclerView: RecyclerView,
         listener: OnFormElementValueChangedListener? = null,
         init: FormBuildHelper.() -> Unit): FormBuildHelper {
    val form = FormBuildHelper(
            context = context,
            listener = listener,
            recyclerView = recyclerView
    )
    form.init()
    form.setItems()
    form.refreshView()
    return form
}

interface FieldBuilder {
    fun build(): BaseFormElement<*>
}

/** Type-safe builder method to add a header */
class HeaderBuilder() : FieldBuilder {
    var title: String = ""
    override fun build() =
            FormHeader.createInstance(title)
}
fun FormBuildHelper.header(init: HeaderBuilder.() -> Unit): FormHeader {
    val fieldModel = HeaderBuilder().apply(init).build()
    mElements.add(fieldModel)
    return fieldModel
}

/** Builder method to add a BaseFormElement */
abstract class BaseElementBuilder<T: Serializable>(protected val tag: Int) : FieldBuilder {
    var title: String? = null // title for the form element
    var type: Int = 0 // type for the form element
    var value: T? = null // value to be shown on right
    var options: List<T>? = null // list of options for single and multi picker
        get() = field ?: ArrayList()
    var optionsSelected: List<T>? = null // list of selected options for single and multi picker
        get() = field ?: ArrayList()
    var hint: String? = null // value to be shown if mValue is null
    var error: String? = null
    var required: Boolean = false // value to set is the field is required
    var visible: Boolean = true
    var valueChanged: OnFormElementValueChangedListener? = null
}

/** Builder method to add a FormEditTextElement */
class EditTextBuilder<T: Serializable>(tag: Int) : BaseElementBuilder<T>(tag) {
    override fun build() =
            FormEditTextElement<T>(tag)
                    .setType(type)
                    .setTitle(title.orEmpty())
                    .setValue(value)
                    .setHint(hint)
                    .setError(error)
                    .setRequired(required)
                    .setVisible(visible)
                    .setValueChanged(valueChanged)
                    as FormEditTextElement<T>
}
fun <T: Serializable> FormBuildHelper.editText(tag: Int, init: EditTextBuilder<T>.() -> Unit): FormEditTextElement<T> {
    val element = EditTextBuilder<T>(tag).apply(init).build()
    mElements.add(element)
    return element
}

/** Builder method to add a FormAutoCompleteElement */
class AutoCompleteBuilder<T: Serializable>(tag: Int) : BaseElementBuilder<T>(tag) {
    var arrayAdapter: ArrayAdapter<*>? = null
    var dropdownWidth: Int? = null
    override fun build() =
            (FormAutoCompleteElement<T>(tag)
                    .setTitle(title.orEmpty())
                    .setValue(value)
                    .setHint(hint)
                    .setError(error)
                    .setRequired(required)
                    .setVisible(visible)
                    .setValueChanged(valueChanged)
                    as FormAutoCompleteElement<T>)
                    .setArrayAdapter(arrayAdapter)
                    .setDropdownWidth(dropdownWidth)
}
fun <T: Serializable> FormBuildHelper.autoComplete(tag: Int, init: AutoCompleteBuilder<T>.() -> Unit): FormAutoCompleteElement<T> {
    val element = AutoCompleteBuilder<T>(tag).apply(init).build()
    mElements.add(element)
    return element
}

/** Builder method to add a FormTokenAutoCompleteElement */
class AutoCompleteTokenBuilder<T: Serializable>(tag: Int) : BaseElementBuilder<T>(tag) {
    var arrayAdapter: ArrayAdapter<*>? = null
    var dropdownWidth: Int? = null
    override fun build() =
            (FormTokenAutoCompleteElement<T>(tag)
                    .setTitle(title.orEmpty())
                    .setValue(value)
                    .setHint(hint)
                    .setError(error)
                    .setRequired(required)
                    .setVisible(visible)
                    .setValueChanged(valueChanged)
                    as FormTokenAutoCompleteElement<T>)
                    .setArrayAdapter(arrayAdapter)
                    .setDropdownWidth(dropdownWidth)
}
fun <T: Serializable> FormBuildHelper.autoCompleteToken(tag: Int, init: AutoCompleteTokenBuilder<T>.() -> Unit): FormTokenAutoCompleteElement<T> {
    val element = AutoCompleteTokenBuilder<T>(tag).apply(init).build()
    mElements.add(element)
    return element
}

/** Builder method to add a FormButtonElement */
class ButtonBuilder<T: Serializable>(tag: Int) : BaseElementBuilder<T>(tag) {
    override fun build() =
            (FormButtonElement<T>(tag)
                    .setTitle(title.orEmpty())
                    .setValue(value)
                    .setHint(hint)
                    .setError(error)
                    .setRequired(required)
                    .setVisible(visible)
                    .setValueChanged(valueChanged)
                    as FormButtonElement<T>)
}
fun <T: Serializable> FormBuildHelper.button(tag: Int, init: ButtonBuilder<T>.() -> Unit): FormButtonElement<T> {
    val element = ButtonBuilder<T>(tag).apply(init).build()
    mElements.add(element)
    return element
}

/** Builder method to add a FormPickerDateElement */
class DateBuilder(tag: Int) : BaseElementBuilder<FormPickerDateElement.DateHolder>(tag) {
    var dateFormat: DateFormat = SimpleDateFormat.getDateInstance()
    var dateValue: Date? = null
    override fun build() =
            (FormPickerDateElement(tag)
                    .setTitle(title.orEmpty())
                    .setValue(value?: FormPickerDateElement.DateHolder(dateValue, dateFormat))
                    .setHint(hint)
                    .setError(error)
                    .setRequired(required)
                    .setVisible(visible)
                    .setValueChanged(valueChanged)
                    as FormPickerDateElement)
}
fun FormBuildHelper.date(tag: Int, init: DateBuilder.() -> Unit): FormPickerDateElement {
    val element = DateBuilder(tag).apply(init).build()
    mElements.add(element)
    return element
}

/** Builder method to add a FormPickerTimeElement */
class TimeBuilder(tag: Int) : BaseElementBuilder<FormPickerDateElement.DateHolder>(tag) {
    var dateFormat: DateFormat = SimpleDateFormat.getDateInstance()
    var dateValue: Date? = null
    override fun build() =
            (FormPickerTimeElement(tag)
                    .setTitle(title.orEmpty())
                    .setValue(value?: FormPickerTimeElement.TimeHolder(dateValue, dateFormat))
                    .setHint(hint)
                    .setError(error)
                    .setRequired(required)
                    .setVisible(visible)
                    .setValueChanged(valueChanged)
                    as FormPickerTimeElement)
}
fun FormBuildHelper.time(tag: Int, init: TimeBuilder.() -> Unit): FormPickerTimeElement {
    val element = TimeBuilder(tag).apply(init).build()
    mElements.add(element)
    return element
}

/** Builder method to add a FormButtonElement */
class DateTimeBuilder(tag: Int) : BaseElementBuilder<FormPickerDateElement.DateHolder>(tag) {
    var dateFormat: DateFormat = SimpleDateFormat.getDateInstance()
    var dateValue: Date? = null
    override fun build() =
            (FormPickerDateTimeElement(tag)
                    .setTitle(title.orEmpty())
                    .setValue(value?: FormPickerDateTimeElement.DateTimeHolder(dateValue, dateFormat))
                    .setHint(hint)
                    .setError(error)
                    .setRequired(required)
                    .setVisible(visible)
                    .setValueChanged(valueChanged)
                    as FormPickerDateTimeElement)
}
fun FormBuildHelper.dateTime(tag: Int, init: DateTimeBuilder.() -> Unit): FormPickerDateTimeElement {
    val element = DateTimeBuilder(tag).apply(init).build()
    mElements.add(element)
    return element
}

/** Builder method to add a FormPickerDropDownElement */
class DropDownBuilder<T: Serializable>(tag: Int) : BaseElementBuilder<T>(tag) {
    var dialogTitle: String? = null
    var arrayAdapter: ArrayAdapter<*>? = null
    override fun build() =
            (FormPickerDropDownElement<T>(tag)
                    .setTitle(title.orEmpty())
                    .setValue(value)
                    .setHint(hint)
                    .setError(error)
                    .setRequired(required)
                    .setVisible(visible)
                    .setOptions(options?: ArrayList())
                    .setValueChanged(valueChanged)
                    as FormPickerDropDownElement<T>)
                    .setDialogTitle(dialogTitle)
                    .setArrayAdapter(arrayAdapter)
}
fun <T: Serializable> FormBuildHelper.dropDown(tag: Int, init: DropDownBuilder<T>.() -> Unit): FormPickerDropDownElement<T> {
    val element = DropDownBuilder<T>(tag).apply(init).build()
    mElements.add(element)
    return element
}

/** Builder method to add a FormPickerMultiCheckBoxElement */
class MultiCheckBoxBuilder<T: Serializable>(tag: Int) : BaseElementBuilder<T>(tag) {
    var dialogTitle: String? = null
    override fun build() =
            (FormPickerMultiCheckBoxElement<T>(tag)
                    .setTitle(title.orEmpty())
                    .setValue(value)
                    .setHint(hint)
                    .setError(error)
                    .setRequired(required)
                    .setVisible(visible)
                    .setOptions(options?: ArrayList())
                    .setOptionsSelected(optionsSelected?: ArrayList())
                    .setValueChanged(valueChanged)
                    as FormPickerMultiCheckBoxElement<T>)
                    .setDialogTitle(dialogTitle)
}
fun <T: Serializable> FormBuildHelper.multiCheckBox(tag: Int, init: MultiCheckBoxBuilder<T>.() -> Unit): FormPickerMultiCheckBoxElement<T> {
    val element = MultiCheckBoxBuilder<T>(tag).apply(init).build()
    mElements.add(element)
    return element
}

/** Builder method to add a FormSwitchElement */
class SwitchBuilder<T: Serializable>(tag: Int) : BaseElementBuilder<T>(tag) {
    var onValue: T? = null
    var offValue: T? = null
    override fun build() =
            (FormSwitchElement<T>(tag)
                    .setTitle(title.orEmpty())
                    .setValue(value)
                    .setHint(hint)
                    .setError(error)
                    .setRequired(required)
                    .setVisible(visible)
                    .setValueChanged(valueChanged)
                    as FormSwitchElement<T>)
                    .setOnValue(onValue)
                    .setOffValue(offValue)
}
fun <T: Serializable> FormBuildHelper.switch(tag: Int, init: SwitchBuilder<T>.() -> Unit): FormSwitchElement<T> {
    val element = SwitchBuilder<T>(tag).apply(init).build()
    mElements.add(element)
    return element
}

/** Builder method to add a FormSwitchElement */
class SliderBuilder(tag: Int) : BaseElementBuilder<Int>(tag) {
    var max: Int = 100
    var min: Int = 0
    var steps: Int = 1
    override fun build() =
            (FormSliderElement(tag)
                    .setTitle(title.orEmpty())
                    .setValue(value)
                    .setHint(hint)
                    .setError(error)
                    .setRequired(required)
                    .setVisible(visible)
                    .setValueChanged(valueChanged)
                    as FormSliderElement)
                    .setMax(max)
                    .setMin(min)
                    .setSteps(steps)
}
fun FormBuildHelper.slider(tag: Int, init: SliderBuilder.() -> Unit): FormSliderElement {
    val element = SliderBuilder(tag).apply(init).build()
    mElements.add(element)
    return element
}

/** Builder method to add a FormTextViewElement */
class TextViewBuilder<T: Serializable>(tag: Int) : BaseElementBuilder<T>(tag) {
    override fun build() =
            (FormTextViewElement<T>(tag)
                    .setTitle(title.orEmpty())
                    .setValue(value)
                    .setVisible(visible)
                    as FormTextViewElement<T>)
}
fun <T: Serializable> FormBuildHelper.textView(tag: Int, init: TextViewBuilder<T>.() -> Unit): FormTextViewElement<T> {
    val element = TextViewBuilder<T>(tag).apply(init).build()
    mElements.add(element)
    return element
}


