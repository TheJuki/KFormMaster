# Migrate from Builders

## Why remove the builders?

- Builders serves more of a purpose in the original FormMaster repository to quickly build form elements by chaining setters
- Setters in KFormMaster were kept to provide some compatibility with Java and limit the properties in the Kotlin Form DSL
- Compatibility with Java was fixed in a recent update by removing the original builder methods in the Form Element classes
- Now, the only purpose of the builder classes was to build form elements in the Form DSL
- The builder classes create too much redundancy in the code and cause issues with the Dex file that is created

## Migrate to Form Element Classes

1. Remove builder imports
2. Replace ".build()" with ""
3. Replace Builder Classes with Form Element Classes

| Builder Class             | Form Element Class             |
|---------------------------|--------------------------------|
| HeaderBuilder             | FormHeader                     |
| EmailEditTextBuilder      | FormEmailEditTextElement       |
| PasswordEditTextBuilder   | FormPasswordEditTextElement    |
| PhoneEditTextBuilder      | FormPhoneEditTextElement       |
| SingleLineEditTextBuilder | FormSingleLineEditTextElement  |
| MultiLineEditTextBuilder  | FormMultiLineEditTextElement   |
| NumberEditTextBuilder     | FormNumberEditTextElement      |
| DateBuilder               | FormPickerDateElement          |
| TimeBuilder               | FormPickerTimeElement          |
| DateTimeBuilder           | FormPickerDateTimeElement      |
| DropDownBuilder           | FormPickerDropDownElement      |
| MultiCheckBoxBuilder      | FormPickerMultiCheckBoxElement |
| AutoCompleteBuilder       | FormAutoCompleteElement        |
| AutoCompleteTokenBuilder  | FormTokenAutoCompleteElement   |
| TextViewBuilder           | FormTextViewElement            |
| LabelBuilder              | FormLabelElement               |
| SwitchBuilder             | FormSwitchElement              |
| SliderBuilder             | FormSliderElement              |
| CheckBoxBuilder           | FormCheckBoxElement            |
| SegmentedBuilder          | FormSegmentedElement           |
| ButtonBuilder             | FormButtonElement              |