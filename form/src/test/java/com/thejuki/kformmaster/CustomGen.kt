package com.thejuki.kformmaster

import android.R
import android.widget.ArrayAdapter
import com.thejuki.kformmaster.helper.FormLayouts
import com.thejuki.kformmaster.model.*
import io.kotest.property.Arb
import io.kotest.property.arbitrary.*
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
        val formLayouts = arbitrary {
            FormLayouts(
                Arb.int(-100, 100).next() // header
                , Arb.int(-100, 100).next() // text
                , Arb.int(-100, 100).next() // textArea
                , Arb.int(-100, 100).next() // number
                , Arb.int(-100, 100).next() // email
                , Arb.int(-100, 100).next() // password
                , Arb.int(-100, 100).next() // phone
                , Arb.int(-100, 100).next() // autoComplete
                , Arb.int(-100, 100).next() // autoCompleteToken
                , Arb.int(-100, 100).next() // button
                , Arb.int(-100, 100).next() // date
                , Arb.int(-100, 100).next() // time
                , Arb.int(-100, 100).next() // dateTime
                , Arb.int(-100, 100).next() // dropDown
                , Arb.int(-100, 100).next() // multiCheckBox
                , Arb.int(-100, 100).next() // switch
                , Arb.int(-100, 100).next() // checkBox
                , Arb.int(-100, 100).next() // slider
                , Arb.int(-100, 100).next() // label
                , Arb.int(-100, 100).next() // textView
                , Arb.int(-100, 100).next() // segmented
                , Arb.int(-100, 100).next() // segmentedInlineTitle
                , Arb.int(-100, 100).next() // progress
                , Arb.int(-100, 100).next() // image
                , Arb.int(-100, 100).next() // inlineDateTimePicker
            )
        }

        /**
         * Generates a BaseFormElement
         */
        val baseFormElement = arbitrary {
            generateBaseFields(BaseFormElement())
        }

        /**
         * Generates a FormHeader
         */
        val formHeader = arbitrary {
            FormHeader().apply { title = Arb.string().next() }
        }

        /**
         * Generates a FormSingleLineEditTextElement
         */
        val formSingleLineEditTextElement = arbitrary {
            generateBaseFields(FormSingleLineEditTextElement()) as FormSingleLineEditTextElement
        }

        /**
         * Generates a FormMultiLineEditTextElement
         */
        val formMultiLineEditTextElement = arbitrary {
            generateBaseFields(FormMultiLineEditTextElement()) as FormMultiLineEditTextElement
        }

        /**
         * Generates a FormEmailEditTextElement
         */
        val formEmailEditTextElement = arbitrary {
            val element = generateBaseFields(FormEmailEditTextElement()) as FormEmailEditTextElement
            element.value = Arb.shuffle(
                listOf(
                    "test@example.com",
                    "test.tester@example2.org",
                    "email@example.test.edu"
                )
            ).next()[0]
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

        /**
         * Generates a FormPasswordEditTextElement
         */
        val formPasswordEditTextElement = arbitrary {
            generateBaseFields(FormPasswordEditTextElement()) as FormPasswordEditTextElement
        }

        /**
         * Generates a FormPhoneEditTextElement
         */
        val formPhoneEditTextElement = arbitrary {
            generateBaseFields(FormPhoneEditTextElement()) as FormPhoneEditTextElement
        }

        /**
         * Generates a FormNumberEditTextElement
         */
        val formNumberEditTextElement = arbitrary {
            generateBaseFields(FormNumberEditTextElement()) as FormNumberEditTextElement
        }

        /**
         * Generates a FormTextViewElement
         */
        val formTextViewElement = arbitrary {
            FormTextViewElement().apply {
                title = Arb.string().next()
                value = Arb.string().next()
            }
        }

        /**
         * Generates a formLabelElement
         */
        val formLabelElement = arbitrary {
            FormLabelElement().apply {
                title = Arb.string().next()
            }
        }

        /**
         * Generates a FormSegmentedElement
         */
        val formSegmentedElement = arbitrary {
            val element = generateBaseFields(FormSegmentedElement()) as FormSegmentedElement<String>
            element.horizontal = Arb.boolean().next()
            element.options = Arb.list(Arb.string()).next()
            element.drawableDirection =
                Arb.shuffle(FormSegmentedElement.DrawableDirection.values().asList()).next()[0]
            element
        }

        /**
         * Generates a FormPickerDropDownElement
         */
        val formPickerDropDownElement = arbitrary {
            val element =
                generateBaseFields(FormPickerDropDownElement()) as FormPickerDropDownElement<String>
            element.dialogTitle = Arb.string().next()
            element.arrayAdapter = null
            element.options = Arb.list(Arb.string()).next()
            element.theme = Random().nextInt(100)
            element.displayValueFor = {
                element.value ?: ""
            }
            element
        }

        /**
         * Generates a FormPickerMultiCheckBoxElement
         */
        val formPickerMultiCheckBoxElement = arbitrary {
            @Suppress("UNCHECKED_CAST")
            val element =
                generateBaseFieldsWithList(FormPickerMultiCheckBoxElement()) as FormPickerMultiCheckBoxElement<String, List<String>>
            element.dialogTitle = Arb.string().next()
            element.options = Arb.list(Arb.string()).next()
            element.theme = Random().nextInt(100)
            element
        }

        /**
         * Generates a FormAutoCompleteElement
         */
        val formAutoCompleteElement = arbitrary {
            val element =
                generateBaseFields(FormAutoCompleteElement()) as FormAutoCompleteElement<String>
            element.typedString = element.valueAsString
            element.dropdownWidth = Arb.int().next()
            val listOfOptions = Arb.list(Arb.string()).next()
            element.options = listOfOptions
            element.arrayAdapter = ArrayAdapter(mockk(), R.layout.simple_list_item_1, listOfOptions)
            element
        }

        /**
         * Generates a FormTokenAutoCompleteElement
         */
        val formTokenAutoCompleteElement = arbitrary {
            val element =
                generateBaseFieldsWithList(FormTokenAutoCompleteElement()) as FormTokenAutoCompleteElement<List<String>>
            element.dropdownWidth = Arb.int().next()
            val listOfOptions = Arb.list(Arb.string()).next()
            element.options = listOfOptions
            element.arrayAdapter = ArrayAdapter(mockk(), R.layout.simple_list_item_1, listOfOptions)
            element
        }

        /**
         * Generates a FormCheckBoxElement
         */
        val formCheckBoxElement = arbitrary {
            val element = generateBaseFields(FormCheckBoxElement()) as FormCheckBoxElement<String>
            element.checkedValue = element.valueAsString
            element.unCheckedValue = Arb.string().next()
            element
        }

        /**
         * Generates a FormSwitchElement
         */
        val formSwitchElement = arbitrary {
            val element = generateBaseFields(FormSwitchElement()) as FormSwitchElement<String>
            element.onValue = element.valueAsString
            element.offValue = Arb.string().next()
            element
        }

        /**
         * Generates a FormSliderElement
         */
        val formSliderElement = arbitrary {
            val element = FormSliderElement()
            element.title = Arb.string().next()
            element.max = Random().nextInt(100)
            element.min = Random().nextInt(element.max)
            element.value = Random().nextInt(element.max - element.min) + element.min
            element.steps = Random().nextInt(element.max - element.min + 1) + element.min
            element
        }

        /**
         * Generates a FormProgressElement
         */
        val formProgressElement = arbitrary {
            val element = FormProgressElement()
            element.title = Arb.string().next()
            element.max = Random().nextInt(100)
            element.min = Random().nextInt(element.max)
            element.progress = Random().nextInt(element.max - element.min) + element.min
            element.secondaryProgress = Random().nextInt(element.max - element.min) + element.min
            element.indeterminate = Arb.boolean().next()
            element.progressBarStyle =
                Arb.shuffle(FormProgressElement.ProgressBarStyle.values().asList()).next()[0]
            element
        }

        /**
         * Generates a FormImageElement
         */
        val formImageElement = arbitrary {
            val element = FormImageElement()
            element.value = "https://example.com/image.jpg"
            element.applyCircleCrop = false
            element.theme = Random().nextInt(100)
            element.defaultImage = Random().nextInt(100)
            element.imagePickerOptions = {
                it.cropX = Random().nextFloat()
                it.cropY = Random().nextFloat()
                it.maxWidth = Random().nextInt(100)
                it.maxHeight = Random().nextInt(100)
                it.maxSize = Random().nextInt(100)
            }
            element.onSelectImage = { uri, _ ->
                println("\nNew Image = ${uri?.path}")
            }
            element
        }

        /**
         * Generates a FormPickerDateElement
         */
        val formPickerDateElement = arbitrary {
            val dateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.US)
            val element = FormPickerDateElement()
            element.title = Arb.string().next()
            element.hint = Arb.string().next()
            element.value = FormPickerDateElement.DateHolder(Date(), dateFormat)
            element.minimumDate = dateFormat.parse("01/01/2018")
            element.maximumDate = dateFormat.parse("12/15/2025")
            element.theme = Random().nextInt(100)
            element
        }

        /**
         * Generates a FormPickerTimeElement
         */
        val formPickerTimeElement = arbitrary {
            val dateFormat = SimpleDateFormat("hh:mm a", Locale.US)
            val element = FormPickerTimeElement()
            element.title = Arb.string().next()
            element.hint = Arb.string().next()
            element.value = FormPickerTimeElement.TimeHolder(Date(), dateFormat)
            element.theme = Random().nextInt(100)
            element
        }

        /**
         * Generates a FormPickerDateTimeElement
         */
        val formPickerDateTimeElement = arbitrary {
            val dateFormat = SimpleDateFormat("MM/dd/yyyy hh:mm a", Locale.US)
            val element = FormPickerDateTimeElement()
            element.title = Arb.string().next()
            element.hint = Arb.string().next()
            element.value = FormPickerDateTimeElement.DateTimeHolder(Date(), dateFormat)
            element.minimumDate = dateFormat.parse("01/01/2018 12:00 AM")
            element.maximumDate = dateFormat.parse("12/15/2025 12:00 PM")
            element.theme = Random().nextInt(100)
            element
        }

        /**
         * Generates a FormInlineDatePickerElement
         */
        val formInlineDatePickerElement = arbitrary {
            val element = FormInlineDatePickerElement()
            element.title = Arb.string().next()
            element.hint = Arb.string().next()
            element.value = org.threeten.bp.LocalDateTime.now(ZoneId.of("UTC"))
            element.dateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME
            element.dateTimePickerFormatter = DateTimeFormatter.ISO_DATE
            element.startDate = org.threeten.bp.LocalDateTime.now(ZoneId.of("UTC")).toLocalDate()
            element.allDay = Arb.boolean().next()
            element.pickerType =
                Arb.shuffle(FormInlineDatePickerElement.PickerType.values().asList()).next()[0]
            element.linkedPicker = FormInlineDatePickerElement()
            element
        }

        /**
         * Generates a FormButtonElement
         */
        val formButtonElement = arbitrary {
            FormButtonElement().setValue(Arb.string().next()) as FormButtonElement
        }

        /**
         * Generates base form field values
         */
        private fun generateBaseFields(element: BaseFormElement<String>) =
            element.apply {
                title = Arb.string().next()
                value = Arb.string().next()
                tag = Arb.int().next()
                hint = Arb.string().next()
                visible = Arb.boolean().next()
                enabled = Arb.boolean().next()
                editViewGravity = Arb.int().next()
                maxLines = Arb.int(1, 100).next()
                error = if (Arb.boolean().next()) Arb.string().next() else null
                valueObservers.add { newValue, elementRef -> println("New Value = $newValue {$elementRef}") }
            }

        private fun generateBaseFieldsWithList(element: BaseFormElement<List<String>>) =
            element.apply {
                title = Arb.string().next()
                value = Arb.list(Arb.string()).next()
                tag = Arb.int().next()
                hint = Arb.string().next()
                visible = Arb.boolean().next()
                enabled = Arb.boolean().next()
                editViewGravity = Arb.int().next()
                maxLines = Arb.int(1, 100).next()
                error = if (Arb.boolean().next()) Arb.string().next() else null
                valueObservers.add { newValue, elementRef -> println("\nNew Value = $newValue {$elementRef}") }
            }

        /**
         * Verifies some base form fields
         */
        fun verifyBaseFormElement(element: BaseFormElement<*>) =
            (element.value != null) &&
                    (element.hint != null) &&
                    element.valueObservers.isNotEmpty()
    }
}
