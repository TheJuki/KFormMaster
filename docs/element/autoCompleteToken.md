# AutoCompleteToken

The AutoCompleteToken form element is used for an AutoComplete row with multiple values represented as tokens.

![Example](../images/TokenAutocomplete1.PNG)

![Example](../images/TokenAutocomplete2.PNG)

## Array Adapter

Set your custom AutoComplete adapter. It is up to you if you want to retrieve items through an API call.

!!! note "NOTICE"

    The ContactItem and EmailAutoCompleteAdapter classes are provided in the example app. Your custom class just needs to override toString() to display in the dropdown.

```kotlin
autoCompleteToken<List<ContactItem>>(1) {
    arrayAdapter = EmailAutoCompleteAdapter(this@FormActivity,
                        android.R.layout.simple_list_item_1)
}
```

## Dropdown Width

This is the width of the dropdown list. ViewGroup.LayoutParams.MATCH_PARENT will use the form's width.

```kotlin
autoCompleteToken<List<ContactItem>>(1) {
    dropdownWidth = ViewGroup.LayoutParams.MATCH_PARENT
}
```

## Example

### Kotlin

```kotlin
autoCompleteToken<List<ContactItem>>(1) {
    arrayAdapter = EmailAutoCompleteAdapter(this@FormActivity,
                        android.R.layout.simple_list_item_1)
    dropdownWidth = ViewGroup.LayoutParams.MATCH_PARENT
    value = arrayListOf(ContactItem(id = 1, value = "John.Smith@mail.com", label = "John Smith (Tester)"))
}
```

### Java

```java
List<BaseFormElement<?>> elements = new ArrayList<>();
FormTokenAutoCompleteElement<List<ContactItem>> autoCompleteToken = new FormTokenAutoCompleteElement<>(1);
autoCompleteToken.setArrayAdapter(new EmailAutoCompleteAdapter(this,
    android.R.layout.simple_list_item_1));
autoCompleteToken.setDropdownWidth(ViewGroup.LayoutParams.MATCH_PARENT);
elements.add(autoCompleteToken);
```
