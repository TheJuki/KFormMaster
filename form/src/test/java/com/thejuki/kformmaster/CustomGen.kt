package com.thejuki.kformmaster

import android.R
import android.widget.ArrayAdapter
import com.thejuki.kformmaster.model.*
import io.kotlintest.mock.mock
import io.kotlintest.properties.Gen
import java.text.SimpleDateFormat
import java.util.*

/**
 * Custom Gen
 *
 * Custom Generators for the Great Form Unit Tests
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
interface CustomGen {
    companion object {

        /**
         * Generates a BaseFormElement
         */
        fun baseFormElement() = object : Gen<BaseFormElement<String>> {
            override fun generate() = generateBaseFields(BaseFormElement())

        }

        /**
         * Generates a FormHeader
         */
        fun formHeader() = object : Gen<FormHeader> {
            override fun generate(): FormHeader {
                return FormHeader().apply { title = Gen.string().generate() }
            }
        }

        /**
         * Generates a FormSingleLineEditTextElement
         */
        fun formSingleLineEditTextElement() = object : Gen<FormSingleLineEditTextElement> {
            override fun generate() =
                    generateBaseFields(FormSingleLineEditTextElement()) as FormSingleLineEditTextElement
        }

        /**
         * Generates a FormMultiLineEditTextElement
         */
        fun formMultiLineEditTextElement() = object : Gen<FormMultiLineEditTextElement> {
            override fun generate() =
                    generateBaseFields(FormMultiLineEditTextElement()) as FormMultiLineEditTextElement
        }

        /**
         * Generates a FormEmailEditTextElement
         */
        fun formEmailEditTextElement() = object : Gen<FormEmailEditTextElement> {
            override fun generate(): FormEmailEditTextElement {
                val element = generateBaseFields(FormEmailEditTextElement()) as FormEmailEditTextElement
                element.value = Gen.oneOf(listOf("test@example.com", "test.tester@example2.org", "email@example.test.edu")).generate()
                element.validityCheck = {
                    ("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                            "\\@" +
                            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                            "(" +
                            "\\." +
                            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                            ")+").toRegex().matches(element.value ?: "")
                }
                return element
            }
        }

        /**
         * Generates a FormPasswordEditTextElement
         */
        fun formPasswordEditTextElement() = object : Gen<FormPasswordEditTextElement> {
            override fun generate() =
                    generateBaseFields(FormPasswordEditTextElement()) as FormPasswordEditTextElement
        }

        /**
         * Generates a FormPhoneEditTextElement
         */
        fun formPhoneEditTextElement() = object : Gen<FormPhoneEditTextElement> {
            override fun generate() =
                    generateBaseFields(FormPhoneEditTextElement()) as FormPhoneEditTextElement
        }

        /**
         * Generates a FormNumberEditTextElement
         */
        fun formNumberEditTextElement() = object : Gen<FormNumberEditTextElement> {
            override fun generate() =
                    generateBaseFields(FormNumberEditTextElement()) as FormNumberEditTextElement
        }

        /**
         * Generates a FormTextViewElement
         */
        fun formTextViewElement() = object : Gen<FormTextViewElement> {
            override fun generate(): FormTextViewElement {
                return FormTextViewElement().apply {
                    title = Gen.string().generate()
                    value = Gen.string().generate()
                }
            }
        }

        /**
         * Generates a formLabelElement
         */
        fun formLabelElement() = object : Gen<FormLabelElement> {
            override fun generate(): FormLabelElement {
                return FormLabelElement().apply {
                    title = Gen.string().generate()
                }
            }
        }

        /**
         * Generates a FormSegmentedElement
         */
        fun formSegmentedElement() = object : Gen<FormSegmentedElement<String>> {
            override fun generate(): FormSegmentedElement<String> {
                val element = generateBaseFields(FormSegmentedElement()) as FormSegmentedElement<String>
                element.horizontal = Gen.bool().generate()
                element.options = Gen.list(Gen.string()).generate()
                return element
            }
        }

        /**
         * Generates a FormPickerDropDownElement
         */
        fun formPickerDropDownElement() = object : Gen<FormPickerDropDownElement<String>> {
            override fun generate(): FormPickerDropDownElement<String> {
                val element = generateBaseFields(FormPickerDropDownElement()) as FormPickerDropDownElement<String>
                element.dialogTitle = Gen.string().generate()
                element.arrayAdapter = null
                element.options = Gen.list(Gen.string()).generate()
                return element
            }
        }

        /**
         * Generates a FormPickerMultiCheckBoxElement
         */
        fun formPickerMultiCheckBoxElement() = object : Gen<FormPickerMultiCheckBoxElement<List<String>>> {
            override fun generate(): FormPickerMultiCheckBoxElement<List<String>> {
                val element = generateBaseFieldsWithList(FormPickerMultiCheckBoxElement()) as FormPickerMultiCheckBoxElement<List<String>>
                element.dialogTitle = Gen.string().generate()
                element.options = Gen.list(Gen.string()).generate()
                return element
            }
        }

        /**
         * Generates a FormAutoCompleteElement
         */
        fun formAutoCompleteElement() = object : Gen<FormAutoCompleteElement<String>> {
            override fun generate(): FormAutoCompleteElement<String> {
                val element = generateBaseFields(FormAutoCompleteElement()) as FormAutoCompleteElement<String>
                element.typedString = element.valueAsString
                element.dropdownWidth = Gen.int().generate()
                val listOfOptions = Gen.list(Gen.string()).generate()
                element.options = listOfOptions
                element.arrayAdapter = ArrayAdapter(mock(), R.layout.simple_list_item_1, listOfOptions)
                return element
            }
        }

        /**
         * Generates a FormTokenAutoCompleteElement
         */
        fun formTokenAutoCompleteElement() = object : Gen<FormTokenAutoCompleteElement<List<String>>> {
            override fun generate(): FormTokenAutoCompleteElement<List<String>> {
                val element = generateBaseFieldsWithList(FormTokenAutoCompleteElement()) as FormTokenAutoCompleteElement<List<String>>
                element.dropdownWidth = Gen.int().generate()
                val listOfOptions = Gen.list(Gen.string()).generate()
                element.options = listOfOptions
                element.arrayAdapter = ArrayAdapter(mock(), R.layout.simple_list_item_1, listOfOptions)
                return element
            }
        }

        /**
         * Generates a FormCheckBoxElement
         */
        fun formCheckBoxElement() = object : Gen<FormCheckBoxElement<String>> {
            override fun generate(): FormCheckBoxElement<String> {
                val element = generateBaseFields(FormCheckBoxElement()) as FormCheckBoxElement<String>
                element.checkedValue = element.valueAsString
                element.unCheckedValue = Gen.string().generate()
                return element
            }
        }

        /**
         * Generates a FormSwitchElement
         */
        fun formSwitchElement() = object : Gen<FormSwitchElement<String>> {
            override fun generate(): FormSwitchElement<String> {
                val element = generateBaseFields(FormSwitchElement()) as FormSwitchElement<String>
                element.onValue = element.valueAsString
                element.offValue = Gen.string().generate()
                return element
            }
        }

        /**
         * Generates a FormSliderElement
         */
        fun formSliderElement() = object : Gen<FormSliderElement> {
            override fun generate(): FormSliderElement {
                val element = FormSliderElement()
                element.title = Gen.string().generate()
                element.max = Gen.choose(1, 100).generate()
                element.min = Gen.choose(0, element.max - 1).generate()
                element.value = Gen.choose(element.min, element.max).generate()
                element.steps = Gen.choose(1, element.max).generate()
                return element
            }
        }

        /**
         * Generates a FormPickerDateElement
         */
        fun formPickerDateElement() = object : Gen<FormPickerDateElement> {
            override fun generate(): FormPickerDateElement {
                val element = FormPickerDateElement()
                element.title = Gen.string().generate()
                element.hint = Gen.string().generate()
                element.value = FormPickerDateElement.DateHolder(Date(), SimpleDateFormat("MM/dd/yyyy", Locale.US))
                return element
            }
        }

        /**
         * Generates a FormPickerTimeElement
         */
        fun formPickerTimeElement() = object : Gen<FormPickerTimeElement> {
            override fun generate(): FormPickerTimeElement {
                val element = FormPickerTimeElement()
                element.title = Gen.string().generate()
                element.hint = Gen.string().generate()
                element.value = FormPickerTimeElement.TimeHolder(Date(), SimpleDateFormat("hh:mm a", Locale.US))
                return element
            }
        }

        /**
         * Generates a FormPickerDateTimeElement
         */
        fun formPickerDateTimeElement() = object : Gen<FormPickerDateTimeElement> {
            override fun generate(): FormPickerDateTimeElement {
                val element = FormPickerDateTimeElement()
                element.title = Gen.string().generate()
                element.hint = Gen.string().generate()
                element.value = FormPickerDateTimeElement.DateTimeHolder(Date(), SimpleDateFormat("MM/dd/yyyy hh:mm a", Locale.US))
                return element
            }
        }

        /**
         * Generates a FormButtonElement
         */
        fun formButtonElement() = object : Gen<FormButtonElement> {
            override fun generate(): FormButtonElement {
                return FormButtonElement().setValue(Gen.string().generate()) as FormButtonElement
            }
        }

        /**
         * Generates base form field values
         */
        fun generateBaseFields(element: BaseFormElement<String>) =
                element.apply {
                    title = Gen.string().generate()
                    value = Gen.string().generate()
                    tag = Gen.int().generate()
                    hint = Gen.string().generate()
                    visible = Gen.bool().generate()
                    enabled = Gen.bool().generate()
                    rightToLeft = Gen.bool().generate()
                    maxLines = Gen.choose(1, 100).generate()
                    error = if (Gen.bool().generate()) Gen.string().generate() else null
                    valueObservers.add { newValue, elementRef -> println("New Value = $newValue {$elementRef}") }
                }

        fun generateBaseFieldsWithList(element: BaseFormElement<List<String>>) =
                element.apply {
                    title = Gen.string().generate()
                    value = Gen.list(Gen.string()).generate()
                    tag = Gen.int().generate()
                    hint = Gen.string().generate()
                    visible = Gen.bool().generate()
                    enabled = Gen.bool().generate()
                    rightToLeft = Gen.bool().generate()
                    maxLines = Gen.choose(1, 100).generate()
                    error = if (Gen.bool().generate()) Gen.string().generate() else null
                    valueObservers.add { newValue, elementRef -> println("New Value = $newValue {$elementRef}") }
                }

        /**
         * Verifies some base form fields
         */
        fun verifyBaseFormElement(element: BaseFormElement<*>) =
                (element.value != null) &&
                        (element.hint != null) &&
                        !element.valueObservers.isEmpty()
    }
}
