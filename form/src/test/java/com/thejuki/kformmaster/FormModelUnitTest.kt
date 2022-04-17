package com.thejuki.kformmaster

import android.net.Uri
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.property.arbitrary.next
import java.nio.file.Files

/**
 * Form Model Unit Test
 *
 * The Great Form Model Unit Test
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
@Suppress("BlockingMethodInNonBlockingContext")
class FormModelUnitTest : ShouldSpec({
    context("FormLayouts") {
        should("have form layout Ids") {
            val formLayouts = CustomGen.formLayouts.next()

            formLayouts.header shouldNotBe null
            formLayouts.text shouldNotBe null
            formLayouts.textArea shouldNotBe null
            formLayouts.number shouldNotBe null
            formLayouts.email shouldNotBe null
            formLayouts.password shouldNotBe null
            formLayouts.phone shouldNotBe null
            formLayouts.autoComplete shouldNotBe null
            formLayouts.autoCompleteToken shouldNotBe null
            formLayouts.button shouldNotBe null
            formLayouts.date shouldNotBe null
            formLayouts.time shouldNotBe null
            formLayouts.dateTime shouldNotBe null
            formLayouts.dropDown shouldNotBe null
            formLayouts.multiCheckBox shouldNotBe null
            formLayouts.switch shouldNotBe null
            formLayouts.checkBox shouldNotBe null
            formLayouts.slider shouldNotBe null
            formLayouts.label shouldNotBe null
            formLayouts.textView shouldNotBe null
            formLayouts.segmented shouldNotBe null
            formLayouts.segmentedInlineTitle shouldNotBe null
            formLayouts.progress shouldNotBe null
            formLayouts.image shouldNotBe null
            formLayouts.inlineDateTimePicker shouldNotBe null
        }
    }

    context("BaseFormElement") {
        should("have observe changes to value") {
            val element = CustomGen.baseFormElement.next()
            val newValue = "Test Value Observer"
            element.addValueObserver { value, _ ->
                value shouldBe newValue
            }
            element.value = newValue
        }
    }

    context("Header") {
        should("have valid formHeader") {
            CustomGen.formHeader.next().title shouldNotBe null
        }
    }

    context("Label") {
        should("have valid label") {
            CustomGen.formLabelElement.next().title shouldNotBe null
        }
    }

    context("TextView") {
        should("have valid formTextViewElement") {
            val element = CustomGen.formTextViewElement.next()
            element.title shouldNotBe null
            element.value shouldNotBe null
        }
    }

    context("DateAndTime") {
        should("have valid formPickerDateElement") {
            val element = CustomGen.formPickerDateElement.next()
            element.value?.getTime() shouldNotBe null
            element.minimumDate shouldNotBe null
            element.maximumDate shouldNotBe null
        }
        should("have valid formPickerTimeElement") {
            CustomGen.formPickerTimeElement.next().value?.getTime() shouldNotBe null
        }
        should("have valid formPickerDateTimeElement") {
            val element = CustomGen.formPickerDateTimeElement.next()
            element.value?.getTime() shouldNotBe null
            element.minimumDate shouldNotBe null
            element.maximumDate shouldNotBe null
        }
        should("have valid formInlineDatePickerElement") {
            val element = CustomGen.formInlineDatePickerElement.next()
            element.value shouldNotBe null
            element.dateTimeFormatter shouldNotBe null
            element.dateTimePickerFormatter shouldNotBe null
            element.linkedPicker shouldNotBe null
        }
    }

    context("MarkComplete") {
        should("have valid formSegmentedElement") {
            val element = CustomGen.formSegmentedElement.next()
            CustomGen.verifyBaseFormElement(element) shouldBe true
        }
        should("have valid formCheckBoxElement") {
            val element = CustomGen.formCheckBoxElement.next()
            (element.checkedValue == element.value) shouldBe element.isChecked()
        }
        should("have valid formSwitchElement") {
            val element = CustomGen.formSwitchElement.next()
            (element.onValue == element.value) shouldBe element.isOn()
        }
        should("have valid formSliderElement") {
            val element = CustomGen.formSliderElement.next()
            (element.min < element.max) shouldBe true
            ((element.value ?: 0 <= element.max) and (element.value ?: 0 >= element.min)) shouldBe true
        }
        should("have valid formProgressElement") {
            val element = CustomGen.formProgressElement.next()
            (element.min < element.max) shouldBe true
            ((element.progress <= element.max) and (element.progress >= element.min)) shouldBe true
        }
        should("have valid formButtonElement") {
            CustomGen.formButtonElement.next().value shouldNotBe null
        }
    }

    context("Pickers") {
        should("have valid formImageElement") {
            val element = CustomGen.formImageElement.next()
            element.value shouldNotBe null
            element.defaultImage shouldNotBe null
            element.applyCircleCrop shouldBe false
            val newImage = Files.createTempFile("test", ".png").toFile()
            newImage.isFile shouldBe true
            element.onSelectImage?.invoke(Uri.fromFile(newImage), null)
        }
        should("have valid formPickerDropDownElement") {
            val element = CustomGen.formPickerDropDownElement.next()
            CustomGen.verifyBaseFormElement(element) shouldBe true
            element.dialogTitle shouldNotBe null
            element.arrayAdapter shouldBe null
            element.displayValueFor(element.value) shouldBe (element.value ?: "")
        }
        should("have valid formPickerMultiCheckBoxElement") {
            val element = CustomGen.formPickerMultiCheckBoxElement.next()
            CustomGen.verifyBaseFormElement(element) shouldBe true
            element.dialogTitle shouldNotBe null
        }
    }

    context("AutoComplete") {
        should("have valid formPickerDropDownElement") {
            val element = CustomGen.formAutoCompleteElement.next()
            CustomGen.verifyBaseFormElement(element) shouldBe true
            element.typedString shouldNotBe null
            element.arrayAdapter shouldNotBe null
            element.dropdownWidth shouldNotBe null
        }
        should("have valid formTokenAutoCompleteElement") {
            val element = CustomGen.formTokenAutoCompleteElement.next()
            CustomGen.verifyBaseFormElement(element) shouldBe true
            element.arrayAdapter shouldNotBe null
            element.dropdownWidth shouldNotBe null
        }
    }

    context("EditTextElement") {
        should("have valid formSingleLineEditTextElement") {
            CustomGen.verifyBaseFormElement(
                CustomGen.formSingleLineEditTextElement.next()
            ) shouldBe true
        }
        should("have valid formMultiLineEditTextElement") {
            CustomGen.verifyBaseFormElement(
                CustomGen.formMultiLineEditTextElement.next()
            ) shouldBe true
        }
        should("have valid formEmailEditTextElement") {
            val element = CustomGen.formEmailEditTextElement.next()
            CustomGen.verifyBaseFormElement(element) shouldBe true
            element.isValid shouldBe true
            element.value = "bad.email@"
            element.isValid shouldBe false
        }
        should("have valid formPasswordEditTextElement") {
            CustomGen.verifyBaseFormElement(
                CustomGen.formPasswordEditTextElement.next()
            ) shouldBe true
        }
        should("have valid formPhoneEditTextElement") {
            CustomGen.verifyBaseFormElement(
                CustomGen.formPhoneEditTextElement.next()
            ) shouldBe true
        }
        should("have valid formNumberEditTextElement") {
            CustomGen.verifyBaseFormElement(
                CustomGen.formNumberEditTextElement.next()
            ) shouldBe true
        }
    }
})
