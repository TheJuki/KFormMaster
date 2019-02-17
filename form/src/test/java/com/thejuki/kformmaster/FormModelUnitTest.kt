package com.thejuki.kformmaster

import io.kotlintest.KTestJUnitRunner
import io.kotlintest.matchers.shouldBe
import io.kotlintest.matchers.shouldNotBe
import io.kotlintest.specs.ShouldSpec
import org.junit.runner.RunWith

/**
 * Form Model Unit Test
 *
 * The Great Form Model Unit Test
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
@RunWith(KTestJUnitRunner::class)
class FormModelUnitTest : ShouldSpec() {
    init {
        "BaseFormElement" {
            should("have observe changes to value") {
                val element = CustomGen.baseFormElement().generate()
                val newValue = "Test Value Observer"
                element.addValueObserver { value, _ ->
                    value shouldBe newValue
                }
                element.value = newValue
            }
        }

        "Header" {
            should("have valid formHeader") {
                CustomGen.formHeader().generate().title shouldNotBe null
            }
        }

        "Label" {
            should("have valid formHeader") {
                CustomGen.formLabelElement().generate().title shouldNotBe null
            }
        }

        "TextView" {
            should("have valid formTextViewElement") {
                val element = CustomGen.formTextViewElement().generate()
                element.title shouldNotBe null
                element.value shouldNotBe null
            }
        }

        "DateAndTime" {
            should("have valid formPickerDateElement") {
                CustomGen.formPickerDateElement().generate().value?.getTime() shouldNotBe null
            }
            should("have valid formPickerTimeElement") {
                CustomGen.formPickerTimeElement().generate().value?.getTime() shouldNotBe null
            }
            should("have valid formPickerDateTimeElement") {
                CustomGen.formPickerDateTimeElement().generate().value?.getTime() shouldNotBe null
            }
        }

        "MarkComplete" {
            should("have valid formSegmentedElement") {
                val element = CustomGen.formSegmentedElement().generate()
                CustomGen.verifyBaseFormElement(element) shouldBe true
            }
            should("have valid formCheckBoxElement") {
                val element = CustomGen.formCheckBoxElement().generate()
                (element.checkedValue == element.value) shouldBe element.isChecked()
            }
            should("have valid formSwitchElement") {
                val element = CustomGen.formSwitchElement().generate()
                (element.onValue == element.value) shouldBe element.isOn()
            }
            should("have valid formSliderElement") {
                val element = CustomGen.formSliderElement().generate()
                (element.min < element.max) shouldBe true
                ((element.value ?: 0 <= element.max) and (element.value ?: 0 >= element.min)) shouldBe true
            }
            should("have valid formProgressElement") {
                val element = CustomGen.formProgressElement().generate()
                (element.min < element.max) shouldBe true
                ((element.progress ?: 0 <= element.max) and (element.progress ?: 0 >= element.min)) shouldBe true
            }
            should("have valid formButtonElement") {
                CustomGen.formButtonElement().generate().value shouldNotBe null
            }
        }

        "Pickers" {
            should("have valid formPickerDropDownElement") {
                val element = CustomGen.formPickerDropDownElement().generate()
                CustomGen.verifyBaseFormElement(element) shouldBe true
                element.dialogTitle shouldNotBe null
                element.arrayAdapter shouldBe null
            }
            should("have valid formPickerMultiCheckBoxElement") {
                val element = CustomGen.formPickerMultiCheckBoxElement().generate()
                CustomGen.verifyBaseFormElement(element) shouldBe true
                element.dialogTitle shouldNotBe null
            }
        }

        "AutoComplete" {
            should("have valid formPickerDropDownElement") {
                val element = CustomGen.formAutoCompleteElement().generate()
                CustomGen.verifyBaseFormElement(element) shouldBe true
                element.typedString shouldNotBe null
                element.arrayAdapter shouldNotBe null
                element.dropdownWidth shouldNotBe null
            }
            should("have valid formTokenAutoCompleteElement") {
                val element = CustomGen.formTokenAutoCompleteElement().generate()
                CustomGen.verifyBaseFormElement(element) shouldBe true
                element.arrayAdapter shouldNotBe null
                element.dropdownWidth shouldNotBe null
            }
        }

        "EditTextElement" {
            should("have valid formSingleLineEditTextElement") {
                CustomGen.verifyBaseFormElement(CustomGen.formSingleLineEditTextElement().generate()) shouldBe true
            }
            should("have valid formMultiLineEditTextElement") {
                CustomGen.verifyBaseFormElement(CustomGen.formMultiLineEditTextElement().generate()) shouldBe true
            }
            should("have valid formEmailEditTextElement") {
                val element = CustomGen.formEmailEditTextElement().generate()
                CustomGen.verifyBaseFormElement(element) shouldBe true
                element.isValid shouldBe true
                element.value = "bad.email@"
                element.isValid shouldBe false
            }
            should("have valid formPasswordEditTextElement") {
                CustomGen.verifyBaseFormElement(CustomGen.formPasswordEditTextElement().generate()) shouldBe true
            }
            should("have valid formPhoneEditTextElement") {
                CustomGen.verifyBaseFormElement(CustomGen.formPhoneEditTextElement().generate()) shouldBe true
            }
            should("have valid formNumberEditTextElement") {
                CustomGen.verifyBaseFormElement(CustomGen.formNumberEditTextElement().generate()) shouldBe true
            }
        }
    }
}
