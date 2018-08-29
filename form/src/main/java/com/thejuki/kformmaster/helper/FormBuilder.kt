package com.thejuki.kformmaster.helper

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.InputType
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import com.thejuki.kformmaster.listener.OnFormElementValueChangedListener
import com.thejuki.kformmaster.model.*
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

/** FieldBuilder interface */
@FormDsl
interface FieldBuilder {
    fun build(): BaseFormElement<*>
}

/** Builder method to add a FormHeader */
class HeaderBuilder(var title: String = "") : FieldBuilder {
    var collapsible: Boolean = false
    var tag: Int = -1
    override fun build() =
            FormHeader(tag).apply {
                this@HeaderBuilder.let {
                    title = it.title
                    collapsible = it.collapsible
                }
            }
}

/** FormBuildHelper extension to add a FormHeader */
fun FormBuildHelper.header(init: HeaderBuilder.() -> Unit): FormHeader {
    return addFormElement(HeaderBuilder().apply(init).build())
}

/** Builder method to add a BaseFormElement */
@FormDsl
abstract class BaseElementBuilder<T>(protected val tag: Int = -1, var title: String? = null) : FieldBuilder {
    /**
     * Form Element Value
     */
    var value: T? = null

    /**
     * Form Element Hint
     */
    var hint: String? = null

    /**
     * Form Element RTL
     */
    var rightToLeft: Boolean = true

    /**
     * Form Element Max Lines
     */
    var maxLines: Int = 1

    /**
     * Form Element Error
     */
    var error: String? = null

    /**
     * Form Element Required
     */
    var required: Boolean = false

    /**
     * Form Element Clearable
     * Setting this to true will display a clear button (X) to set the value to null.
     */
    var clearable: Boolean = false

    /**
     * Form Element Confirm Edit dialog should be shown before editing an element
     */
    var confirmEdit: Boolean = false

    /**
     * Form Element Confirm Edit dialog title
     */
    var confirmTitle: String? = null

    /**
     * Form Element Confirm Edit dialog message
     */
    var confirmMessage: String? = null

    /**
     * Form Element Visibility
     */
    var visible: Boolean = true

    /**
     * Form Element Enabled
     */
    var enabled: Boolean = true

    /**
     * Form Element [InputType]
     */
    var inputType: Int? = null

    /**
     * Form Element [EditorInfo] imeOptions
     */
    var imeOptions: Int? = null

    /**
     * Form Element Update EditText value when focus is lost
     * By default, an EditText will update the form value as characters are typed.
     * Setting this to true will only update the value when focus is lost.
     */
    var updateOnFocusChange: Boolean = false

    /**
     * Form Element Value Observers
     */
    val valueObservers = mutableListOf<(value: T?, element: BaseFormElement<T>) -> Unit>()
}

/** Builder method to add a FormSingleLineEditTextElement */
class SingleLineEditTextBuilder(tag: Int = -1) : BaseElementBuilder<String>(tag) {
    override fun build() =
            FormSingleLineEditTextElement(tag).apply {
                this@SingleLineEditTextBuilder.let {
                    title = it.title.orEmpty()
                    value = it.value
                    hint = it.hint
                    rightToLeft = it.rightToLeft
                    maxLines = it.maxLines
                    error = it.error
                    required = it.required
                    enabled = it.enabled
                    visible = it.visible
                    inputType = it.inputType
                    imeOptions = it.imeOptions
                    clearable = it.clearable
                    updateOnFocusChange = it.updateOnFocusChange
                    valueObservers.addAll(it.valueObservers)
                }
            }
}

/** FormBuildHelper extension to add a FormSingleLineEditTextElement */
fun FormBuildHelper.text(tag: Int = -1, init: SingleLineEditTextBuilder.() -> Unit): FormSingleLineEditTextElement {
    return addFormElement(SingleLineEditTextBuilder(tag).apply(init).build())
}

/** Builder method to add a FormMultiLineEditTextElement */
class MultiLineEditTextBuilder(tag: Int = -1) : BaseElementBuilder<String>(tag) {
    override fun build() =
            FormMultiLineEditTextElement(tag).apply {
                this@MultiLineEditTextBuilder.let {
                    title = it.title.orEmpty()
                    value = it.value
                    hint = it.hint
                    rightToLeft = it.rightToLeft
                    maxLines = it.maxLines
                    error = it.error
                    required = it.required
                    enabled = it.enabled
                    visible = it.visible
                    inputType = it.inputType
                    imeOptions = it.imeOptions
                    clearable = it.clearable
                    updateOnFocusChange = it.updateOnFocusChange
                    valueObservers.addAll(it.valueObservers)
                }
            }
}

/** FormBuildHelper extension to add a FormMultiLineEditTextElement */
fun FormBuildHelper.textArea(tag: Int = -1, init: MultiLineEditTextBuilder.() -> Unit): FormMultiLineEditTextElement {
    return addFormElement(MultiLineEditTextBuilder(tag).apply(init).build())
}

/** Builder method to add a FormNumberEditTextElement */
class NumberEditTextBuilder(tag: Int = -1) : BaseElementBuilder<String>(tag) {
    var numbersOnly: Boolean = false
    override fun build() =
            FormNumberEditTextElement(tag).apply {
                this@NumberEditTextBuilder.let {
                    title = it.title.orEmpty()
                    value = it.value
                    hint = it.hint
                    rightToLeft = it.rightToLeft
                    maxLines = it.maxLines
                    error = it.error
                    required = it.required
                    enabled = it.enabled
                    visible = it.visible
                    numbersOnly = it.numbersOnly
                    inputType = it.inputType
                    imeOptions = it.imeOptions
                    clearable = it.clearable
                    updateOnFocusChange = it.updateOnFocusChange
                    valueObservers.addAll(it.valueObservers)
                }
            }
}

/** FormBuildHelper extension to add a FormNumberEditTextElement */
fun FormBuildHelper.number(tag: Int = -1, init: NumberEditTextBuilder.() -> Unit): FormNumberEditTextElement {
    return addFormElement(NumberEditTextBuilder(tag).apply(init).build())
}

/** Builder method to add a FormEmailEditTextElement */
class EmailEditTextBuilder(tag: Int = -1) : BaseElementBuilder<String>(tag) {
    override fun build() =
            FormEmailEditTextElement(tag).apply {
                this@EmailEditTextBuilder.let {
                    title = it.title.orEmpty()
                    value = it.value
                    hint = it.hint
                    rightToLeft = it.rightToLeft
                    maxLines = it.maxLines
                    error = it.error
                    required = it.required
                    enabled = it.enabled
                    visible = it.visible
                    inputType = it.inputType
                    imeOptions = it.imeOptions
                    clearable = it.clearable
                    updateOnFocusChange = it.updateOnFocusChange
                    valueObservers.addAll(it.valueObservers)
                }
            }
}

/** FormBuildHelper extension to add a FormEmailEditTextElement */
fun FormBuildHelper.email(tag: Int = -1, init: EmailEditTextBuilder.() -> Unit): FormEmailEditTextElement {
    return addFormElement(EmailEditTextBuilder(tag).apply(init).build())
}

/** Builder method to add a FormPhoneEditTextElement */
class PasswordEditTextBuilder(tag: Int = -1) : BaseElementBuilder<String>(tag) {
    override fun build() =
            FormPasswordEditTextElement(tag).apply {
                this@PasswordEditTextBuilder.let {
                    title = it.title.orEmpty()
                    value = it.value
                    hint = it.hint
                    rightToLeft = it.rightToLeft
                    maxLines = it.maxLines
                    error = it.error
                    required = it.required
                    enabled = it.enabled
                    visible = it.visible
                    inputType = it.inputType
                    imeOptions = it.imeOptions
                    clearable = it.clearable
                    updateOnFocusChange = it.updateOnFocusChange
                    valueObservers.addAll(it.valueObservers)
                }
            }
}

/** FormBuildHelper extension to add a PasswordEditTextBuilder */
fun FormBuildHelper.password(tag: Int = -1, init: PasswordEditTextBuilder.() -> Unit): FormPasswordEditTextElement {
    return addFormElement(PasswordEditTextBuilder(tag).apply(init).build())
}

/** Builder method to add a FormEmailEditTextElement */
class PhoneEditTextBuilder(tag: Int = -1) : BaseElementBuilder<String>(tag) {
    override fun build() =
            FormPhoneEditTextElement(tag).apply {
                this@PhoneEditTextBuilder.let {
                    title = it.title.orEmpty()
                    value = it.value
                    hint = it.hint
                    rightToLeft = it.rightToLeft
                    maxLines = it.maxLines
                    error = it.error
                    required = it.required
                    enabled = it.enabled
                    visible = it.visible
                    inputType = it.inputType
                    imeOptions = it.imeOptions
                    clearable = it.clearable
                    updateOnFocusChange = it.updateOnFocusChange
                    valueObservers.addAll(it.valueObservers)
                }
            }
}

/** FormBuildHelper extension to add a FormPhoneEditTextElement */
fun FormBuildHelper.phone(tag: Int = -1, init: PhoneEditTextBuilder.() -> Unit): FormPhoneEditTextElement {
    return addFormElement(PhoneEditTextBuilder(tag).apply(init).build())
}

/** Builder method to add a FormAutoCompleteElement */
class AutoCompleteBuilder<T>(tag: Int = -1) : BaseElementBuilder<T>(tag) {
    var arrayAdapter: ArrayAdapter<*>? = null
    var dropdownWidth: Int? = null
    var options: List<T>? = null
    override fun build() =
            FormAutoCompleteElement<T>(tag).apply {
                this@AutoCompleteBuilder.let {
                    title = it.title.orEmpty()
                    value = it.value
                    hint = it.hint
                    rightToLeft = it.rightToLeft
                    maxLines = it.maxLines
                    error = it.error
                    required = it.required
                    enabled = it.enabled
                    visible = it.visible
                    arrayAdapter = it.arrayAdapter
                    dropdownWidth = it.dropdownWidth
                    options = it.options
                    valueObservers.addAll(it.valueObservers)
                }
            }
}

/** FormBuildHelper extension to add a FormAutoCompleteElement */
fun <T> FormBuildHelper.autoComplete(tag: Int = -1, init: AutoCompleteBuilder<T>.() -> Unit): FormAutoCompleteElement<T> {
    return addFormElement(AutoCompleteBuilder<T>(tag).apply(init).build())
}

/** Builder method to add a FormTokenAutoCompleteElement */
class AutoCompleteTokenBuilder<T : List<*>>(tag: Int = -1) : BaseElementBuilder<T>(tag) {
    var arrayAdapter: ArrayAdapter<*>? = null
    var dropdownWidth: Int? = null
    var options: T? = null
    override fun build() =
            FormTokenAutoCompleteElement<T>(tag).apply {
                this@AutoCompleteTokenBuilder.let {
                    title = it.title.orEmpty()
                    value = it.value
                    hint = it.hint
                    rightToLeft = it.rightToLeft
                    maxLines = it.maxLines
                    error = it.error
                    required = it.required
                    enabled = it.enabled
                    visible = it.visible
                    arrayAdapter = it.arrayAdapter
                    dropdownWidth = it.dropdownWidth
                    options = it.options
                    valueObservers.addAll(it.valueObservers)
                }
            }
}

/** FormBuildHelper extension to add a FormTokenAutoCompleteElement */
fun <T : List<*>> FormBuildHelper.autoCompleteToken(tag: Int = -1, init: AutoCompleteTokenBuilder<T>.() -> Unit): FormTokenAutoCompleteElement<T> {
    return addFormElement(AutoCompleteTokenBuilder<T>(tag).apply(init).build())
}

/** Builder method to add a FormButtonElement */
class ButtonBuilder(val tag: Int = -1) : FieldBuilder {
    var value: String? = null
    var visible: Boolean = true
    var enabled: Boolean = true
    val valueObservers = mutableListOf<(value: String?, element: BaseFormElement<String>) -> Unit>()
    override fun build() =
            FormButtonElement(tag).apply {
                this@ButtonBuilder.let {
                    value = it.value
                    enabled = it.enabled
                    visible = it.visible
                    valueObservers.addAll(it.valueObservers)
                }
            }
}

/** FormBuildHelper extension to add a FormButtonElement */
fun FormBuildHelper.button(tag: Int = -1, init: ButtonBuilder.() -> Unit): FormButtonElement {
    return addFormElement(ButtonBuilder(tag).apply(init).build())
}

/** Builder method to add a FormPickerDateElement */
class DateBuilder(tag: Int = -1) : BaseElementBuilder<FormPickerDateElement.DateHolder>(tag) {
    var dateFormat: DateFormat = SimpleDateFormat.getDateInstance()
    var dateValue: Date? = null
    override fun build() =
            FormPickerDateElement(tag).apply {
                this@DateBuilder.let {
                    title = it.title.orEmpty()
                    value = it.value ?: FormPickerDateElement.DateHolder(it.dateValue, it.dateFormat)
                    hint = it.hint
                    rightToLeft = it.rightToLeft
                    maxLines = it.maxLines
                    error = it.error
                    confirmEdit = it.confirmEdit
                    confirmTitle = it.confirmTitle
                    confirmMessage = it.confirmMessage
                    required = it.required
                    enabled = it.enabled
                    visible = it.visible
                    clearable = it.clearable
                    valueObservers.addAll(it.valueObservers)
                }
            }
}

/** FormBuildHelper extension to add a FormPickerDateElement */
fun FormBuildHelper.date(tag: Int = -1, init: DateBuilder.() -> Unit): FormPickerDateElement {
    return addFormElement(DateBuilder(tag).apply(init).build())
}

/** Builder method to add a FormPickerTimeElement */
class TimeBuilder(tag: Int = -1) : BaseElementBuilder<FormPickerTimeElement.TimeHolder>(tag) {
    var dateFormat: DateFormat = SimpleDateFormat.getDateInstance()
    var dateValue: Date? = null
    override fun build() =
            FormPickerTimeElement(tag).apply {
                this@TimeBuilder.let {
                    title = it.title.orEmpty()
                    value = it.value ?: FormPickerTimeElement.TimeHolder(it.dateValue, it.dateFormat)
                    hint = it.hint
                    rightToLeft = it.rightToLeft
                    maxLines = it.maxLines
                    confirmEdit = it.confirmEdit
                    confirmTitle = it.confirmTitle
                    confirmMessage = it.confirmMessage
                    error = it.error
                    required = it.required
                    enabled = it.enabled
                    visible = it.visible
                    clearable = it.clearable
                    valueObservers.addAll(it.valueObservers)
                }
            }
}

/** FormBuildHelper extension to add a FormPickerTimeElement */
fun FormBuildHelper.time(tag: Int = -1, init: TimeBuilder.() -> Unit): FormPickerTimeElement {
    return addFormElement(TimeBuilder(tag).apply(init).build())
}

/** Builder method to add a FormButtonElement */
class DateTimeBuilder(tag: Int = -1) : BaseElementBuilder<FormPickerDateTimeElement.DateTimeHolder>(tag) {
    var dateFormat: DateFormat = SimpleDateFormat.getDateInstance()
    var dateValue: Date? = null
    override fun build() =
            FormPickerDateTimeElement(tag).apply {
                this@DateTimeBuilder.let {
                    title = it.title.orEmpty()
                    value = it.value ?: FormPickerDateTimeElement.DateTimeHolder(it.dateValue, it.dateFormat)
                    hint = it.hint
                    rightToLeft = it.rightToLeft
                    maxLines = it.maxLines
                    confirmEdit = it.confirmEdit
                    confirmTitle = it.confirmTitle
                    confirmMessage = it.confirmMessage
                    error = it.error
                    required = it.required
                    enabled = it.enabled
                    visible = it.visible
                    clearable = it.clearable
                    valueObservers.addAll(it.valueObservers)
                }
            }
}

/** FormBuildHelper extension to add a FormPickerDateTimeElement */
fun FormBuildHelper.dateTime(tag: Int = -1, init: DateTimeBuilder.() -> Unit): FormPickerDateTimeElement {
    return addFormElement(DateTimeBuilder(tag).apply(init).build())
}

/** Builder method to add a FormPickerDropDownElement */
class DropDownBuilder<T>(tag: Int = -1) : BaseElementBuilder<T>(tag) {
    var dialogTitle: String? = null
    var dialogEmptyMessage: String? = null
    var arrayAdapter: ArrayAdapter<*>? = null
    var options: List<T>? = null
    override fun build() =
            FormPickerDropDownElement<T>(tag).apply {
                this@DropDownBuilder.let {
                    title = it.title.orEmpty()
                    value = it.value
                    hint = it.hint
                    rightToLeft = it.rightToLeft
                    maxLines = it.maxLines
                    error = it.error
                    required = it.required
                    confirmEdit = it.confirmEdit
                    confirmTitle = it.confirmTitle
                    confirmMessage = it.confirmMessage
                    enabled = it.enabled
                    visible = it.visible
                    options = it.options ?: ArrayList()
                    dialogTitle = it.dialogTitle
                    dialogEmptyMessage = it.dialogEmptyMessage
                    arrayAdapter = it.arrayAdapter
                    clearable = it.clearable
                    valueObservers.addAll(it.valueObservers)
                }
            }
}

/** FormBuildHelper extension to add a FormPickerDropDownElement */
fun <T> FormBuildHelper.dropDown(tag: Int = -1, init: DropDownBuilder<T>.() -> Unit): FormPickerDropDownElement<T> {
    return addFormElement(DropDownBuilder<T>(tag).apply(init).build())
}

/** Builder method to add a FormPickerMultiCheckBoxElement */
class MultiCheckBoxBuilder<T : List<*>>(tag: Int = -1) : BaseElementBuilder<T>(tag) {
    var dialogTitle: String? = null
    var dialogEmptyMessage: String? = null
    var options: T? = null
    override fun build() =
            FormPickerMultiCheckBoxElement<T>(tag).apply {
                this@MultiCheckBoxBuilder.let {
                    title = it.title.orEmpty()
                    value = it.value
                    hint = it.hint
                    rightToLeft = it.rightToLeft
                    maxLines = it.maxLines
                    error = it.error
                    required = it.required
                    confirmEdit = it.confirmEdit
                    confirmTitle = it.confirmTitle
                    confirmMessage = it.confirmMessage
                    enabled = it.enabled
                    visible = it.visible
                    options = it.options
                    dialogTitle = it.dialogTitle
                    clearable = it.clearable
                    dialogEmptyMessage = it.dialogEmptyMessage
                    valueObservers.addAll(it.valueObservers)
                }
            }
}

/** FormBuildHelper extension to add a FormPickerMultiCheckBoxElement */
fun <T : List<*>> FormBuildHelper.multiCheckBox(tag: Int = -1, init: MultiCheckBoxBuilder<T>.() -> Unit): FormPickerMultiCheckBoxElement<T> {
    return addFormElement(MultiCheckBoxBuilder<T>(tag).apply(init).build())
}

/** Builder method to add a FormSegmentedElement */
class SegmentedBuilder<T>(tag: Int = -1) : BaseElementBuilder<T>(tag) {
    var options: List<T>? = null
    var horizontal: Boolean = true
    var fillSpace: Boolean = false
    override fun build() =
            FormSegmentedElement<T>(tag).apply {
                this@SegmentedBuilder.let {
                    title = it.title.orEmpty()
                    value = it.value
                    rightToLeft = it.rightToLeft
                    error = it.error
                    required = it.required
                    enabled = it.enabled
                    visible = it.visible
                    horizontal = it.horizontal
                    fillSpace = it.fillSpace
                    options = it.options ?: ArrayList()
                    valueObservers.addAll(it.valueObservers)
                }
            }
}

/** FormBuildHelper extension to add a FormSegmentedElement */
fun <T> FormBuildHelper.segmented(tag: Int = -1, init: SegmentedBuilder<T>.() -> Unit): FormSegmentedElement<T> {
    return addFormElement(SegmentedBuilder<T>(tag).apply(init).build())
}

/** Builder method to add a FormSwitchElement */
class SwitchBuilder<T>(tag: Int = -1) : BaseElementBuilder<T>(tag) {
    var onValue: T? = null
    var offValue: T? = null
    override fun build() =
            FormSwitchElement<T>(tag).apply {
                this@SwitchBuilder.let {
                    title = it.title.orEmpty()
                    value = it.value
                    error = it.error
                    required = it.required
                    enabled = it.enabled
                    visible = it.visible
                    onValue = it.onValue
                    offValue = it.offValue
                    valueObservers.addAll(it.valueObservers)
                }
            }
}

/** FormBuildHelper extension to add a FormSwitchElement */
fun <T> FormBuildHelper.switch(tag: Int = -1, init: SwitchBuilder<T>.() -> Unit): FormSwitchElement<T> {
    return addFormElement(SwitchBuilder<T>(tag).apply(init).build())
}

/** Builder method to add a FormCheckBoxElement */
class CheckBoxBuilder<T>(tag: Int = -1) : BaseElementBuilder<T>(tag) {
    var checkedValue: T? = null
    var unCheckedValue: T? = null
    override fun build() =
            FormCheckBoxElement<T>(tag).apply {
                this@CheckBoxBuilder.let {
                    title = it.title.orEmpty()
                    value = it.value
                    error = it.error
                    required = it.required
                    enabled = it.enabled
                    visible = it.visible
                    checkedValue = it.checkedValue
                    unCheckedValue = it.unCheckedValue
                    valueObservers.addAll(it.valueObservers)
                }
            }
}

/** FormBuildHelper extension to add a FormCheckBoxElement */
fun <T> FormBuildHelper.checkBox(tag: Int = -1, init: CheckBoxBuilder<T>.() -> Unit): FormCheckBoxElement<T> {
    return addFormElement(CheckBoxBuilder<T>(tag).apply(init).build())
}

/** Builder method to add a FormSliderElement */
class SliderBuilder(tag: Int = -1) : BaseElementBuilder<Int>(tag) {
    var max: Int = 100
    var min: Int = 0
    var steps: Int? = null
    var incrementBy: Int? = null
    override fun build() =
            FormSliderElement(tag).apply {
                this@SliderBuilder.let {
                    title = it.title.orEmpty()
                    value = it.value
                    error = it.error
                    required = it.required
                    enabled = it.enabled
                    visible = it.visible
                    max = it.max
                    min = it.min
                    steps = it.steps
                    incrementBy = it.incrementBy
                    valueObservers.addAll(it.valueObservers)
                }
            }
}

/** FormBuildHelper extension to add a FormSliderElement */
fun FormBuildHelper.slider(tag: Int = -1, init: SliderBuilder.() -> Unit): FormSliderElement {
    return addFormElement(SliderBuilder(tag).apply(init).build())
}

class LabelBuilder(val tag: Int = -1) : FieldBuilder {
    var title: String? = null
    var visible: Boolean = true
    var rightToLeft: Boolean = true
    override fun build() =
            FormLabelElement(tag).apply {
                this@LabelBuilder.let {
                    title = it.title.orEmpty()
                    visible = it.visible
                    rightToLeft = it.rightToLeft
                }
            }
}

/** FormBuildHelper extension to add a FormLabelElement */
fun FormBuildHelper.label(tag: Int = -1, init: LabelBuilder.() -> Unit): FormLabelElement {
    return addFormElement(LabelBuilder(tag).apply(init).build())
}

class TextViewBuilder(val tag: Int = -1) : FieldBuilder {
    var title: String? = null
    var value: String? = null
    var visible: Boolean = true
    var rightToLeft: Boolean = true
    var maxLines: Int = 1
    val valueObservers = mutableListOf<(value: String?, element: BaseFormElement<String>) -> Unit>()
    override fun build() =
            FormTextViewElement(tag).apply {
                this@TextViewBuilder.let {
                    title = it.title.orEmpty()
                    value = it.value
                    visible = it.visible
                    rightToLeft = it.rightToLeft
                    maxLines = it.maxLines
                    valueObservers.addAll(it.valueObservers)
                }
            }
}

/** FormBuildHelper extension to add a FormTextViewElement */
fun FormBuildHelper.textView(tag: Int = -1, init: TextViewBuilder.() -> Unit): FormTextViewElement {
    return addFormElement(TextViewBuilder(tag).apply(init).build())
}


