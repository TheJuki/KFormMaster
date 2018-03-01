package com.thejuki.kformmaster

import android.R
import android.widget.ArrayAdapter
import com.thejuki.kformmaster.model.*
import io.kotlintest.mock.mock
import io.kotlintest.properties.Gen

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
                return FormHeader.createInstance(Gen.string().generate())
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
            override fun generate() =
                    generateBaseFields(FormEmailEditTextElement()) as FormEmailEditTextElement
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
                return FormTextViewElement()
                        .setTitle(Gen.string().generate())
                        .setValue(Gen.string().generate()) as FormTextViewElement
            }
        }

        /**
         * Generates a FormPickerDropDownElement
         */
        fun formPickerDropDownElement() = object : Gen<FormPickerDropDownElement<String>> {
            override fun generate(): FormPickerDropDownElement<String> {
                val element = generateBaseFields(FormPickerDropDownElement()) as FormPickerDropDownElement<String>
                element.dialogTitle = Gen.string().generate()
                element.arrayAdapter = null // Use .setItems(options)
                return element
            }
        }

        /**
         * Generates a FormPickerMultiCheckBoxElement
         */
        fun formPickerMultiCheckBoxElement() = object : Gen<FormPickerMultiCheckBoxElement<String>> {
            override fun generate(): FormPickerMultiCheckBoxElement<String> {
                val element = generateBaseFields(FormPickerMultiCheckBoxElement()) as FormPickerMultiCheckBoxElement<String>
                element.dialogTitle = Gen.string().generate()
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
                element.arrayAdapter = ArrayAdapter(mock(), R.layout.simple_list_item_1, element.options)
                return element
            }
        }

        /**
         * Generates a FormTokenAutoCompleteElement
         */
        fun formTokenAutoCompleteElement() = object : Gen<FormTokenAutoCompleteElement<String>> {
            override fun generate(): FormTokenAutoCompleteElement<String> {
                val element = generateBaseFields(FormTokenAutoCompleteElement()) as FormTokenAutoCompleteElement<String>
                element.dropdownWidth = Gen.int().generate()
                element.arrayAdapter = ArrayAdapter(mock(), R.layout.simple_list_item_1, element.options)
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
                element.min = Gen.choose(0, element.max).generate()
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
                element.value = FormPickerDateElement.DateHolder()
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
                element.value = FormPickerTimeElement.TimeHolder()
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
                element.value = FormPickerDateTimeElement.DateTimeHolder()
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
                element.setTitle(Gen.string().generate())
                        .setValue(Gen.string().generate())
                        .setTag(Gen.int().generate())
                        .setHint(Gen.string().generate())
                        .setRequired(Gen.bool().generate())
                        .setVisible(Gen.bool().generate())
                        .setError(if (Gen.bool().generate()) Gen.string().generate() else null)
                        .setOptions(Gen.list(Gen.string()).generate())
                        .setOptionsSelected(Gen.list(Gen.string()).generate())
                        .addValueObserver({ newValue, elementRef -> println("New Value = $newValue {$elementRef}") })

        /**
         * Verifies some base form fields
         */
        fun verifyBaseFormElement(element: BaseFormElement<*>) =
                !element.isHeader and
                        (element.value != null) and
                        (element.hint != null) and
                        !element.valueObservers.isEmpty() and
                        !(element.options?.isEmpty() ?: false) and
                        !(element.optionsSelected?.isEmpty() ?: false)
    }
}
