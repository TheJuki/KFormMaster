# Change Log

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
