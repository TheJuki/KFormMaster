# Change Log

## [4.0.1](https://github.com/TheJuki/KFormMaster/releases/tag/4.0.1)
- Calling reInitDialog() is no longer needed after changing the dropDown or multiCheckBox options. This also allows clear() to clear the selected options for multiCheckBox.

## [4.0.0](https://github.com/TheJuki/KFormMaster/releases/tag/4.0.0)
- Removed Parcelable from form elements
- Removed Serializable from form element
- Removed optionsSelected from BaseFormElement. FormPickerMultiCheckBoxElement now uses 'value' instead of 'optionsSelected'. This fixes valueObservers for the FormPickerMultiCheckBoxElement.
- FormPickerMultiCheckBoxElement and FormTokenAutoCompleteElement require a list of objects through <T: List<*>>

## [3.2.1](https://github.com/TheJuki/KFormMaster/releases/tag/3.2.1)
- Fixed Autocomplete text not showing up when initialized with a value. 
- Added addFormElement function to FormBuildHelper and use apply/let to reduce code in FormBuilder. 
- Completed document on how to create a custom form element.

## [3.2.0](https://github.com/TheJuki/KFormMaster/releases/tag/3.2.0)
- Added maxLines to form models. Set to a number greater than 1 to increase the number of visible lines.
- Merged PR #22 to fix the DSL Form Builder.

## [3.1.3](https://github.com/TheJuki/KFormMaster/releases/tag/3.1.3)
- Added rightToLeft (RTL) to form models. Set to false to set the edit text fields to display left to right.

## [3.1.2](https://github.com/TheJuki/KFormMaster/releases/tag/3.1.2)
- Fixes the month number used by the Date and DateTime elements

## [3.1.1](https://github.com/TheJuki/KFormMaster/releases/tag/3.1.1)
- Add autoMeasureEnabled to FormBuildHelper constructor to fix a recylerview that uses wrap_content

## [3.1.0](https://github.com/TheJuki/KFormMaster/releases/tag/3.1.0)
- Removed all uses of "!!" and replaced with "?" or "?.let" for safer code
- reInitDialog() function added to DropDown and MultiCheckBox elements so that the options list can be changed dynamically. Resolves issue #17
- Add "enabled" to form elements. Dynamically enable/disable any form element. Resolves issue #19.

## [3.0.0](https://github.com/TheJuki/KFormMaster/releases/tag/3.0.0)
- The title, edit, and error views are now accessible through the model classes
- The refreshView() function has been removed (No longer needed)
- The getFormElement() function now requires a type and returns a non-optional element
- The form is now dynamic. The title, hint, value, error, visible, required fields can be changed at any time without refreshing the form manually.
- The clear() function has been added to all form elements
- The header can now collapse "child" elements when tapped (Set collapsible to true)
- The FullscreenFormActivity example has been updated to test the new dynamic features

## [2.1.3](https://github.com/TheJuki/KFormMaster/releases/tag/2.1.3)
- The isValidForm function now works correctly
- The numbersOnly field has been added to FormNumberEditTextElement to prevent symbols from being entered. By default numbers and symbols are allowed.

## [2.1.2](https://github.com/TheJuki/KFormMaster/releases/tag/2.1.2)
- Added ability to add your own custom form elements in your project
- Added example to show how to add a custom form element

## [2.1.1](https://github.com/TheJuki/KFormMaster/releases/tag/2.1.1)
- Added cacheForm to FormBuildHelper. When set to true, the RecyclerView will cache all form element views instead of recycling them.

## [2.1.0](https://github.com/TheJuki/KFormMaster/releases/tag/2.1.0)
- Removed valueChanged in BaseFormElement
- Added even more comments
- Added Unit Test

## [2.0.1](https://github.com/TheJuki/KFormMaster/releases/tag/2.0.1)
- Add better comments
- Remove redundant View States
- Add Instrumented Tests

## [2.0.0](https://github.com/TheJuki/KFormMaster/releases/tag/2.0.0)
- Added CheckBox element
- Element tags are now optional
- The email, password, phone, text, textArea, number, textView, and button elements no longer take a <T: Serializable> as they only deal with Strings
- Deprecated valueChanged in BaseFormElement
- Added valueObservers in BaseFormElement to replace valueChanged with a list of Observers when the element value changes (See updated Examples)

## [1.2.3](https://github.com/TheJuki/KFormMaster/releases/tag/1.2.3)
- Fixed issue with tokenautocomplete

## [1.2.2](https://github.com/TheJuki/KFormMaster/releases/tag/1.2.2)
- Add View States to View Binders to save and restore their states
- Add missing model.valueChanged?.onValueChanged(model) to View Binders

## 1.2.1
- Resolve issue with RendererRecyclerViewAdapter library

## 1.2.0
- Update RendererRecyclerViewAdapter library
- Remove type from BaseFormElement as it is no longer needed
- Remove generic FormEditTextElement
- Add FormSingleLineEditTextElement (text), 
    FormMultiLineEditTextElement (textArea), 
    FormNumberEditTextElement (number), 
    FormPasswordEditTextElement (password), 
    and FormPhoneEditTextElement (phone)

## 1.1.0
- Add Kotlin DSL form builder
- Update examples to use new Kotlin DSL form builder

## 1.0.0
- Just released!
- Converted to Kotlin using the fork by [shaymargolis](https://github.com/shaymargolis/FormMaster).
- Added DateTime, Button, Switch, Slider, and Token AutoComplete using [TokenAutoComplete](https://github.com/splitwise/TokenAutoComplete).
