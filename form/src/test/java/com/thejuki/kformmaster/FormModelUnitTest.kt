package com.thejuki.kformmaster

import com.thejuki.kformmaster.helper.CircleTransform
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.kotlintest.specs.ShouldSpec
import java.nio.file.Files

/**
 * Form Model Unit Test
 *
 * The Great Form Model Unit Test
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormModelUnitTest : ShouldSpec() {
    init {
        "FormLayouts" {
            should("have form layout Ids") {
                val formLayouts = CustomGen.formLayouts().random().first()

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
                formLayouts.progress shouldNotBe null
                formLayouts.image shouldNotBe null
                formLayouts.inlineDateTimePicker shouldNotBe null
            }
        }

        "BaseFormElement" {
            should("have observe changes to value") {
                val element = CustomGen.baseFormElement().random().first()
                val newValue = "Test Value Observer"
                element.addValueObserver { value, _ ->
                    value shouldBe newValue
                }
                element.value = newValue
            }
        }

        "Header" {
            should("have valid formHeader") {
                CustomGen.formHeader().random().first().title shouldNotBe null
            }
        }

        "Label" {
            should("have valid label") {
                CustomGen.formLabelElement().random().first().title shouldNotBe null
            }
        }

        "TextView" {
            should("have valid formTextViewElement") {
                val element = CustomGen.formTextViewElement().random().first()
                element.title shouldNotBe null
                element.value shouldNotBe null
            }
        }

        "DateAndTime" {
            should("have valid formPickerDateElement") {
                val element = CustomGen.formPickerDateElement().random().first()
                element.value?.getTime() shouldNotBe null
                element.minimumDate shouldNotBe null
                element.maximumDate shouldNotBe null
            }
            should("have valid formPickerTimeElement") {
                CustomGen.formPickerTimeElement().random().first().value?.getTime() shouldNotBe null
            }
            should("have valid formPickerDateTimeElement") {
                val element = CustomGen.formPickerDateTimeElement().random().first()
                element.value?.getTime() shouldNotBe null
                element.minimumDate shouldNotBe null
                element.maximumDate shouldNotBe null
            }
            should("have valid formInlineDatePickerElement") {
                val element = CustomGen.formInlineDatePickerElement().random().first()
                element.value shouldNotBe null
                element.dateTimeFormatter shouldNotBe null
                element.dateTimePickerFormatter shouldNotBe null
                element.linkedPicker shouldNotBe null
            }
        }

        "MarkComplete" {
            should("have valid formSegmentedElement") {
                val element = CustomGen.formSegmentedElement().random().first()
                CustomGen.verifyBaseFormElement(element) shouldBe true
            }
            should("have valid formCheckBoxElement") {
                val element = CustomGen.formCheckBoxElement().random().first()
                (element.checkedValue == element.value) shouldBe element.isChecked()
            }
            should("have valid formSwitchElement") {
                val element = CustomGen.formSwitchElement().random().first()
                (element.onValue == element.value) shouldBe element.isOn()
            }
            should("have valid formSliderElement") {
                val element = CustomGen.formSliderElement().random().first()
                (element.min < element.max) shouldBe true
                ((element.value ?: 0 <= element.max) and (element.value ?: 0 >= element.min)) shouldBe true
            }
            should("have valid formProgressElement") {
                val element = CustomGen.formProgressElement().random().first()
                (element.min < element.max) shouldBe true
                ((element.progress ?: 0 <= element.max) and (element.progress ?: 0 >= element.min)) shouldBe true
            }
            should("have valid formButtonElement") {
                CustomGen.formButtonElement().random().first().value shouldNotBe null
            }
        }

        "Pickers" {
            should("have valid formImageElement") {
                val element = CustomGen.formImageElement().random().first()
                element.value shouldNotBe null
                element.defaultImage shouldNotBe null
                (element.imageTransformation is CircleTransform) shouldBe true
                val newImage = Files.createTempFile("test", ".png").toFile()
                newImage.isFile shouldBe true
                element.onSelectImage?.invoke(newImage)
            }
            should("have valid formPickerDropDownElement") {
                val element = CustomGen.formPickerDropDownElement().random().first()
                CustomGen.verifyBaseFormElement(element) shouldBe true
                element.dialogTitle shouldNotBe null
                element.arrayAdapter shouldBe null
                element.displayValueFor(element.value) shouldBe (element.value ?: "")
            }
            should("have valid formPickerMultiCheckBoxElement") {
                val element = CustomGen.formPickerMultiCheckBoxElement().random().first()
                CustomGen.verifyBaseFormElement(element) shouldBe true
                element.dialogTitle shouldNotBe null
            }
        }

        "AutoComplete" {
            should("have valid formPickerDropDownElement") {
                val element = CustomGen.formAutoCompleteElement().random().first()
                CustomGen.verifyBaseFormElement(element) shouldBe true
                element.typedString shouldNotBe null
                element.arrayAdapter shouldNotBe null
                element.dropdownWidth shouldNotBe null
            }
            should("have valid formTokenAutoCompleteElement") {
                val element = CustomGen.formTokenAutoCompleteElement().random().first()
                CustomGen.verifyBaseFormElement(element) shouldBe true
                element.arrayAdapter shouldNotBe null
                element.dropdownWidth shouldNotBe null
            }
        }

        "EditTextElement" {
            should("have valid formSingleLineEditTextElement") {
                CustomGen.verifyBaseFormElement(CustomGen.formSingleLineEditTextElement().random().first()) shouldBe true
            }
            should("have valid formMultiLineEditTextElement") {
                CustomGen.verifyBaseFormElement(CustomGen.formMultiLineEditTextElement().random().first()) shouldBe true
            }
            should("have valid formEmailEditTextElement") {
                val element = CustomGen.formEmailEditTextElement().random().first()
                CustomGen.verifyBaseFormElement(element) shouldBe true
                element.isValid shouldBe true
                element.value = "bad.email@"
                element.isValid shouldBe false
            }
            should("have valid formPasswordEditTextElement") {
                CustomGen.verifyBaseFormElement(CustomGen.formPasswordEditTextElement().random().first()) shouldBe true
            }
            should("have valid formPhoneEditTextElement") {
                CustomGen.verifyBaseFormElement(CustomGen.formPhoneEditTextElement().random().first()) shouldBe true
            }
            should("have valid formNumberEditTextElement") {
                CustomGen.verifyBaseFormElement(CustomGen.formNumberEditTextElement().random().first()) shouldBe true
            }
        }
    }
}
