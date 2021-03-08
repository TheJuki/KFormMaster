package com.thejuki.kformmaster

import android.R
import android.graphics.Color
import android.widget.ArrayAdapter
import com.thejuki.kformmaster.helper.CircleTransform
import com.thejuki.kformmaster.helper.FormLayouts
import com.thejuki.kformmaster.model.*
import io.kotlintest.properties.Gen
import io.mockk.mockk
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter
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
         * Generates a FormLayouts object
         */
        fun formLayouts() = object : Gen<FormLayouts> {
            override fun constants() = emptyList<FormLayouts>()
            override fun random() = generateSequence {
                FormLayouts(
                        Gen.choose(-100, 100).random().first() // header
                        , Gen.choose(-100, 100).random().first() // text
                        , Gen.choose(-100, 100).random().first() // textArea
                        , Gen.choose(-100, 100).random().first() // number
                        , Gen.choose(-100, 100).random().first() // email
                        , Gen.choose(-100, 100).random().first() // password
                        , Gen.choose(-100, 100).random().first() // phone
                        , Gen.choose(-100, 100).random().first() // autoComplete
                        , Gen.choose(-100, 100).random().first() // autoCompleteToken
                        , Gen.choose(-100, 100).random().first() // button
                        , Gen.choose(-100, 100).random().first() // date
                        , Gen.choose(-100, 100).random().first() // time
                        , Gen.choose(-100, 100).random().first() // dateTime
                        , Gen.choose(-100, 100).random().first() // dropDown
                        , Gen.choose(-100, 100).random().first() // multiCheckBox
                        , Gen.choose(-100, 100).random().first() // switch
                        , Gen.choose(-100, 100).random().first() // checkBox
                        , Gen.choose(-100, 100).random().first() // slider
                        , Gen.choose(-100, 100).random().first() // label
                        , Gen.choose(-100, 100).random().first() // textView
                        , Gen.choose(-100, 100).random().first() // segmented
                        , Gen.choose(-100, 100).random().first() // segmentedInlineTitle
                        , Gen.choose(-100, 100).random().first() // progress
                        , Gen.choose(-100, 100).random().first() // image
                        , Gen.choose(-100, 100).random().first() // inlineDateTimePicker
                )
            }
        }

        /**
         * Generates a BaseFormElement
         */
        fun baseFormElement() = object : Gen<BaseFormElement<String>> {
            override fun constants() = emptyList<BaseFormElement<String>>()
            override fun random() = generateSequence {
                generateBaseFields(BaseFormElement())
            }
        }

        /**
         * Generates a FormHeader
         */
        fun formHeader() = object : Gen<FormHeader> {
            override fun constants() = emptyList<FormHeader>()
            override fun random() = generateSequence {
                FormHeader().apply { title = Gen.string().random().first() }
            }
        }

        /**
         * Generates a FormSingleLineEditTextElement
         */
        fun formSingleLineEditTextElement() = object : Gen<FormSingleLineEditTextElement> {
            override fun constants() = emptyList<FormSingleLineEditTextElement>()
            override fun random() = generateSequence {
                generateBaseFields(FormSingleLineEditTextElement()) as FormSingleLineEditTextElement
            }
        }

        /**
         * Generates a FormMultiLineEditTextElement
         */
        fun formMultiLineEditTextElement() = object : Gen<FormMultiLineEditTextElement> {
            override fun constants() = emptyList<FormMultiLineEditTextElement>()
            override fun random() = generateSequence {
                generateBaseFields(FormMultiLineEditTextElement()) as FormMultiLineEditTextElement
            }
        }

        /**
         * Generates a FormEmailEditTextElement
         */
        fun formEmailEditTextElement() = object : Gen<FormEmailEditTextElement> {
            override fun constants() = emptyList<FormEmailEditTextElement>()
            override fun random() = generateSequence {
                val element = generateBaseFields(FormEmailEditTextElement()) as FormEmailEditTextElement
                element.value = Gen.from(listOf("test@example.com", "test.tester@example2.org", "email@example.test.edu")).random().first()
                element.validityCheck = {
                    ("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                            "\\@" +
                            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                            "(" +
                            "\\." +
                            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                            ")+").toRegex().matches(element.value ?: "")
                }
                element
            }
        }

        /**
         * Generates a FormPasswordEditTextElement
         */
        fun formPasswordEditTextElement() = object : Gen<FormPasswordEditTextElement> {
            override fun constants() = emptyList<FormPasswordEditTextElement>()
            override fun random() = generateSequence {
                generateBaseFields(FormPasswordEditTextElement()) as FormPasswordEditTextElement
            }
        }

        /**
         * Generates a FormPhoneEditTextElement
         */
        fun formPhoneEditTextElement() = object : Gen<FormPhoneEditTextElement> {
            override fun constants() = emptyList<FormPhoneEditTextElement>()
            override fun random() = generateSequence {
                generateBaseFields(FormPhoneEditTextElement()) as FormPhoneEditTextElement
            }
        }

        /**
         * Generates a FormNumberEditTextElement
         */
        fun formNumberEditTextElement() = object : Gen<FormNumberEditTextElement> {
            override fun constants() = emptyList<FormNumberEditTextElement>()
            override fun random() = generateSequence {
                generateBaseFields(FormNumberEditTextElement()) as FormNumberEditTextElement
            }
        }

        /**
         * Generates a FormTextViewElement
         */
        fun formTextViewElement() = object : Gen<FormTextViewElement> {
            override fun constants() = emptyList<FormTextViewElement>()
            override fun random() = generateSequence {
                FormTextViewElement().apply {
                    title = Gen.string().random().first()
                    value = Gen.string().random().first()
                }
            }
        }

        /**
         * Generates a formLabelElement
         */
        fun formLabelElement() = object : Gen<FormLabelElement> {
            override fun constants() = emptyList<FormLabelElement>()
            override fun random() = generateSequence {
                FormLabelElement().apply {
                    title = Gen.string().random().first()
                }
            }
        }

        /**
         * Generates a FormSegmentedElement
         */
        fun formSegmentedElement() = object : Gen<FormSegmentedElement<String>> {
            override fun constants() = emptyList<FormSegmentedElement<String>>()
            override fun random() = generateSequence {
                val element = generateBaseFields(FormSegmentedElement()) as FormSegmentedElement<String>
                element.horizontal = Gen.bool().random().first()
                element.options = Gen.list(Gen.string()).random().first()
                element.drawableDirection = Gen.from(FormSegmentedElement.DrawableDirection.values().asList()).random().first()
                element
            }
        }

        /**
         * Generates a FormPickerDropDownElement
         */
        fun formPickerDropDownElement() = object : Gen<FormPickerDropDownElement<String>> {
            override fun constants() = emptyList<FormPickerDropDownElement<String>>()
            override fun random() = generateSequence {
                val element = generateBaseFields(FormPickerDropDownElement()) as FormPickerDropDownElement<String>
                element.dialogTitle = Gen.string().random().first()
                element.arrayAdapter = null
                element.options = Gen.list(Gen.string()).random().first()
                element.theme = Random().nextInt(100)
                element.displayValueFor = {
                    element.value ?: ""
                }
                element
            }
        }

        /**
         * Generates a FormPickerMultiCheckBoxElement
         */
        fun formPickerMultiCheckBoxElement() = object : Gen<FormPickerMultiCheckBoxElement<String, List<String>>> {
            override fun constants() = emptyList<FormPickerMultiCheckBoxElement<String, List<String>>>()
            override fun random() = generateSequence {
                @Suppress("UNCHECKED_CAST")
                val element = generateBaseFieldsWithList(FormPickerMultiCheckBoxElement()) as FormPickerMultiCheckBoxElement<String, List<String>>
                element.dialogTitle = Gen.string().random().first()
                element.options = Gen.list(Gen.string()).random().first()
                element.theme = Random().nextInt(100)
                element
            }
        }

        /**
         * Generates a FormAutoCompleteElement
         */
        fun formAutoCompleteElement() = object : Gen<FormAutoCompleteElement<String>> {
            override fun constants() = emptyList<FormAutoCompleteElement<String>>()
            override fun random() = generateSequence {
                val element = generateBaseFields(FormAutoCompleteElement()) as FormAutoCompleteElement<String>
                element.typedString = element.valueAsString
                element.dropdownWidth = Gen.int().random().first()
                val listOfOptions = Gen.list(Gen.string()).random().first()
                element.options = listOfOptions
                element.arrayAdapter = ArrayAdapter(mockk(), R.layout.simple_list_item_1, listOfOptions)
                element
            }
        }

        /**
         * Generates a FormTokenAutoCompleteElement
         */
        fun formTokenAutoCompleteElement() = object : Gen<FormTokenAutoCompleteElement<List<String>>> {
            override fun constants() = emptyList<FormTokenAutoCompleteElement<List<String>>>()
            override fun random() = generateSequence {
                val element = generateBaseFieldsWithList(FormTokenAutoCompleteElement()) as FormTokenAutoCompleteElement<List<String>>
                element.dropdownWidth = Gen.int().random().first()
                val listOfOptions = Gen.list(Gen.string()).random().first()
                element.options = listOfOptions
                element.arrayAdapter = ArrayAdapter(mockk(), R.layout.simple_list_item_1, listOfOptions)
                element
            }
        }

        /**
         * Generates a FormCheckBoxElement
         */
        fun formCheckBoxElement() = object : Gen<FormCheckBoxElement<String>> {
            override fun constants() = emptyList<FormCheckBoxElement<String>>()
            override fun random() = generateSequence {
                val element = generateBaseFields(FormCheckBoxElement()) as FormCheckBoxElement<String>
                element.checkedValue = element.valueAsString
                element.unCheckedValue = Gen.string().random().first()
                element
            }
        }

        /**
         * Generates a FormSwitchElement
         */
        fun formSwitchElement() = object : Gen<FormSwitchElement<String>> {
            override fun constants() = emptyList<FormSwitchElement<String>>()
            override fun random() = generateSequence {
                val element = generateBaseFields(FormSwitchElement()) as FormSwitchElement<String>
                element.onValue = element.valueAsString
                element.offValue = Gen.string().random().first()
                element
            }
        }

        /**
         * Generates a FormSliderElement
         */
        fun formSliderElement() = object : Gen<FormSliderElement> {
            override fun constants() = emptyList<FormSliderElement>()
            override fun random() = generateSequence {
                val element = FormSliderElement()
                element.title = Gen.string().random().first()
                element.max = Random().nextInt(100)
                element.min = Random().nextInt(element.max)
                element.value = Random().nextInt(element.max - element.min) + element.min
                element.steps = Random().nextInt(element.max - element.min + 1) + element.min
                element
            }
        }

        /**
         * Generates a FormProgressElement
         */
        fun formProgressElement() = object : Gen<FormProgressElement> {
            override fun constants() = emptyList<FormProgressElement>()
            override fun random() = generateSequence {
                val element = FormProgressElement()
                element.title = Gen.string().random().first()
                element.max = Random().nextInt(100)
                element.min = Random().nextInt(element.max)
                element.progress = Random().nextInt(element.max - element.min) + element.min
                element.secondaryProgress = Random().nextInt(element.max - element.min) + element.min
                element.indeterminate = Gen.bool().random().first()
                element.progressBarStyle = Gen.from(FormProgressElement.ProgressBarStyle.values().asList()).random().first()
                element
            }
        }

        /**
         * Generates a FormImageElement
         */
        fun formImageElement() = object : Gen<FormImageElement> {
            override fun constants() = emptyList<FormImageElement>()
            override fun random() = generateSequence {
                val element = FormImageElement()
                element.value = "https://example.com/image.jpg"
                element.imageTransformation = CircleTransform(borderColor = Color.WHITE, borderRadius = 3) //Default value for this is CircleTransform(null) so it makes image round without borders
                element.theme = Random().nextInt(100)
                element.defaultImage = Random().nextInt(100)
                element.imagePickerOptions = {
                    it.cropX = Random().nextFloat()
                    it.cropY = Random().nextFloat()
                    it.maxWidth = Random().nextInt(100)
                    it.maxHeight = Random().nextInt(100)
                    it.maxSize = Random().nextInt(100)
                }
                element.onSelectImage = { file ->
                    println("\nNew Image = ${file?.name}")
                }
                element
            }
        }

        /**
         * Generates a FormPickerDateElement
         */
        fun formPickerDateElement() = object : Gen<FormPickerDateElement> {
            override fun constants() = emptyList<FormPickerDateElement>()
            override fun random() = generateSequence {
                val dateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.US)
                val element = FormPickerDateElement()
                element.title = Gen.string().random().first()
                element.hint = Gen.string().random().first()
                element.value = FormPickerDateElement.DateHolder(Date(), dateFormat)
                element.minimumDate = dateFormat.parse("01/01/2018")
                element.maximumDate = dateFormat.parse("12/15/2025")
                element.theme = Random().nextInt(100)
                element
            }
        }

        /**
         * Generates a FormPickerTimeElement
         */
        fun formPickerTimeElement() = object : Gen<FormPickerTimeElement> {
            override fun constants() = emptyList<FormPickerTimeElement>()
            override fun random() = generateSequence {
                val dateFormat = SimpleDateFormat("hh:mm a", Locale.US)
                val element = FormPickerTimeElement()
                element.title = Gen.string().random().first()
                element.hint = Gen.string().random().first()
                element.value = FormPickerTimeElement.TimeHolder(Date(), dateFormat)
                element.theme = Random().nextInt(100)
                element
            }
        }

        /**
         * Generates a FormPickerDateTimeElement
         */
        fun formPickerDateTimeElement() = object : Gen<FormPickerDateTimeElement> {
            override fun constants() = emptyList<FormPickerDateTimeElement>()
            override fun random() = generateSequence {
                val dateFormat = SimpleDateFormat("MM/dd/yyyy hh:mm a", Locale.US)
                val element = FormPickerDateTimeElement()
                element.title = Gen.string().random().first()
                element.hint = Gen.string().random().first()
                element.value = FormPickerDateTimeElement.DateTimeHolder(Date(), dateFormat)
                element.minimumDate = dateFormat.parse("01/01/2018 12:00 AM")
                element.maximumDate = dateFormat.parse("12/15/2025 12:00 PM")
                element.theme = Random().nextInt(100)
                element
            }
        }

        /**
         * Generates a FormInlineDatePickerElement
         */
        fun formInlineDatePickerElement() = object : Gen<FormInlineDatePickerElement> {
            override fun constants() = emptyList<FormInlineDatePickerElement>()
            override fun random() = generateSequence {
                val element = FormInlineDatePickerElement()
                element.title = Gen.string().random().first()
                element.hint = Gen.string().random().first()
                element.value = org.threeten.bp.LocalDateTime.now(ZoneId.of("UTC"))
                element.dateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME
                element.dateTimePickerFormatter = DateTimeFormatter.ISO_DATE
                element.startDate = org.threeten.bp.LocalDateTime.now(ZoneId.of("UTC")).toLocalDate()
                element.allDay = Gen.bool().random().first()
                element.pickerType = Gen.from(FormInlineDatePickerElement.PickerType.values().asList()).random().first()
                element.linkedPicker = FormInlineDatePickerElement()
                element
            }
        }

        /**
         * Generates a FormButtonElement
         */
        fun formButtonElement() = object : Gen<FormButtonElement> {
            override fun constants() = emptyList<FormButtonElement>()
            override fun random() = generateSequence {
                FormButtonElement().setValue(Gen.string().random().first()) as FormButtonElement
            }
        }

        /**
         * Generates base form field values
         */
        fun generateBaseFields(element: BaseFormElement<String>) =
                element.apply {
                    title = Gen.string().random().first()
                    value = Gen.string().random().first()
                    tag = Gen.int().random().first()
                    hint = Gen.string().random().first()
                    visible = Gen.bool().random().first()
                    enabled = Gen.bool().random().first()
                    editViewGravity = Gen.int().random().first()
                    maxLines = Gen.choose(1, 100).random().first()
                    error = if (Gen.bool().random().first()) Gen.string().random().first() else null
                    valueObservers.add { newValue, elementRef -> println("New Value = $newValue {$elementRef}") }
                }

        fun generateBaseFieldsWithList(element: BaseFormElement<List<String>>) =
                element.apply {
                    title = Gen.string().random().first()
                    value = Gen.list(Gen.string()).random().first()
                    tag = Gen.int().random().first()
                    hint = Gen.string().random().first()
                    visible = Gen.bool().random().first()
                    enabled = Gen.bool().random().first()
                    editViewGravity = Gen.int().random().first()
                    maxLines = Gen.choose(1, 100).random().first()
                    error = if (Gen.bool().random().first()) Gen.string().random().first() else null
                    valueObservers.add { newValue, elementRef -> println("\nNew Value = $newValue {$elementRef}") }
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
