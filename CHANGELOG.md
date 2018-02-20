# Change Log

## [1.2.2](https://github.com/TheJuki/KFormMaster/releases/tag/1.2.2)
1. Add View States to View Binders to save and restore their states
2. Add missing model.valueChanged?.onValueChanged(model) to View Binders

## 1.2.1
1. Resolve issue with RendererRecyclerViewAdapter library

## 1.2.0
1. Update RendererRecyclerViewAdapter library
2. Remove type from BaseFormElement as it is no longer needed
3. Remove generic FormEditTextElement
4. Add FormSingleLineEditTextElement (text), 
    FormMultiLineEditTextElement (textArea), 
    FormNumberEditTextElement (number), 
    FormPasswordEditTextElement (password), 
    and FormPhoneEditTextElement (phone)

## 1.1.0
1. Add Kotlin DSL form builder
2. Update examples to use new Kotlin DSL form builder

## 1.0.0
1. Just released!
2. Converted to Kotlin using the fork by [shaymargolis](https://github.com/shaymargolis/FormMaster).
3. Added DateTime, Button, Switch, Slider, and Token AutoComplete using [TokenAutoComplete](https://github.com/splitwise/TokenAutoComplete).